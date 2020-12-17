package com.example.making_f.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.making_f.Activity.AddPhotoActivity
import com.example.making_f.Activity.CommentActivity
import com.example.making_f.R
import com.example.making_f.navigation.model.ContentDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import kotlinx.android.synthetic.main.item_detail.view.*

class PostFragment : Fragment() {
    var firestore : FirebaseFirestore? = null
    var uid : String? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_post, container, false)
        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid

        // 검색 옵션 변수
        var searchOption = "title"

        // 스피너 옵션에 따른 동작
        view.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (spinner.getItemAtPosition(position)) {
                    "제목" -> {
                        searchOption = "title"
                    }
                    "닉네임" -> {
                        searchOption = "userId"
                    }
                }
            }
        }


        view.detailviewfragment_recyclerview.adapter = DetailVeiwRecyclerViewAdapter()
        view.detailviewfragment_recyclerview.layoutManager = LinearLayoutManager(activity)

        view.board_write.setOnClickListener {
            Log.d("board_write move", "board_write move")
            var intent = Intent(getActivity(), AddPhotoActivity::class.java)
            startActivity(intent)
            getActivity()?.finish()
        }

        view.searchBtn.setOnClickListener {
            (view.detailviewfragment_recyclerview.adapter as DetailVeiwRecyclerViewAdapter).search(searchWord.text.toString(),searchOption)
        }

        return view
    }
    inner class DetailVeiwRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var contentDTOs : ArrayList<ContentDTO> = arrayListOf()
        var contentUidList : ArrayList<String> = arrayListOf()

        init {
            firestore?.collection("images")?.orderBy("timestamp", Query.Direction.DESCENDING)?.addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
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

        // 파이어스토어에서 데이터를 불러와서 검색어가 있는지 판단
        fun search(serachWord : String, option : String) {
            firestore?.collection("images")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                contentDTOs.clear()

                //Sometimes, This code return null of querySnapshot when it sign out
                if(querySnapshot == null) return@addSnapshotListener

                for(snapshot in querySnapshot!!.documents) {
                    if(snapshot.getString(option) == null) return@addSnapshotListener
                    else if (snapshot.getString(option)!!.contains(serachWord)) {
                        var item = snapshot.toObject(ContentDTO::class.java)
                        contentDTOs.add(item!!)
                    }
                }
                notifyDataSetChanged()
            }
        }
    }
}