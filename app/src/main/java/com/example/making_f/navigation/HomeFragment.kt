package com.example.making_f.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.making_f.Activity.CommentActivity
import com.example.making_f.Activity.TestActivity
import com.example.making_f.R
import com.example.making_f.navigation.model.ContentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_detail.view.*

class HomeFragment : Fragment() {
    var firestore : FirebaseFirestore? = null
    var uid : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_home, container, false)
        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid

        view.home_recyclerview.adapter = HomeVeiwRecyclerViewAdapter()
        view.home_recyclerview.layoutManager = LinearLayoutManager(activity)

        view.manual.setOnClickListener{ view ->
            Log.d("from Home", "to future job")
            startActivity(Intent(getActivity()!!, TestActivity::class.java))
        }

        return view
    }
    inner class HomeVeiwRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var contentDTOs : ArrayList<ContentDTO> = arrayListOf()
        var contentUidList : ArrayList<String> = arrayListOf()

        init {
            firestore?.collection("images")?.orderBy("favoriteCount",Query.Direction.DESCENDING)?.addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                contentDTOs.clear()
                contentUidList.clear()
                //Sometimes, This code return null of querySnapshot when it sign out
                if(querySnapshot == null) return@addSnapshotListener

                for(snapshot in querySnapshot!!.documents){
                    var item = snapshot.toObject(ContentDTO::class.java)
                    contentDTOs.add(item!!)
                    contentUidList.add(snapshot.id)
                }
                notifyDataSetChanged()
            }
        }


        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(p0.context).inflate(R.layout.item_detail,p0,false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun getItemCount(): Int {
            return contentDTOs.size
        }

        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
            var viewholder = (p0 as CustomViewHolder).itemView

            //UserId
            viewholder.detailviewitem_profile_textview.text = contentDTOs!![p1].userId

            //Title
            viewholder.detailviewitem_profile_title.text = contentDTOs!![p1].title

            //Image
            Glide.with(p0.itemView.context).load(contentDTOs!![p1].imageUrl).into(viewholder.detailviewitem_imageView_content)

            //Explain of content
            viewholder.detailviewitem_explain_textview.text = contentDTOs!![p1].explain

            //likes
            viewholder.detailviewitem_favoritecounter_textview.text = "Likes " + contentDTOs!![p1].favoriteCount

            //This code is when the button is clicked
            viewholder.detailviewitem_favorite_imageview.setOnClickListener {
                favoriteEvent(p1)
            }

            //This code is when the page is loaded
            if(contentDTOs!![p1].favorites.containsKey(uid)){
                //This is like status
                viewholder.detailviewitem_favorite_imageview.setImageResource(R.drawable.fillheart)
            }else{
                //This is unlike status
                viewholder.detailviewitem_favorite_imageview.setImageResource(R.drawable.heart)
            }
            viewholder.detailviewitem_comment_imageview.setOnClickListener {v->
                var intent = Intent(v.context, CommentActivity::class.java)
                intent.putExtra("contentUid",contentUidList[p1])
                startActivity(intent)
            }
        }
        fun favoriteEvent(position: Int){
            var tsDoc = firestore?.collection("images")?.document(contentUidList[position])
            firestore?.runTransaction { transaction ->

                var contentDTO = transaction.get(tsDoc!!).toObject(ContentDTO::class.java)

                if(contentDTO!!.favorites.containsKey(uid)){
                    //When the button is clicked
                    contentDTO?.favoriteCount = contentDTO?.favoriteCount -1
                    contentDTO?.favorites.remove(uid)
                }else{
                    //When the button is not clicked
                    contentDTO?.favoriteCount = contentDTO?.favoriteCount +1
                    contentDTO?.favorites[uid!!] = true
                }
                transaction.set(tsDoc,contentDTO)
            }
        }

    }

}