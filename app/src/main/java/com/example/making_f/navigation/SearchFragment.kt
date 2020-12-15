package com.example.making_f.navigation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.making_f.Activity.ProfileEditActivity
import com.example.making_f.Activity.TestActivity
import com.example.making_f.Activity.UniversityActivity
import com.example.making_f.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_user.view.*

class SearchFragment : Fragment() {

    var collect: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_search, container, false)

        view.btn_jobrank.setOnClickListener{view ->
            Log.d("btn_jobrank", "Selected")
            startActivity(Intent(getActivity()!!, TestActivity::class.java))
        }

        view.toUniversity.setOnClickListener { view ->
            Log.d("btn_jobrank", "Selected")
            startActivity(Intent(getActivity()!!, UniversityActivity::class.java))
        }

        return view
    }

    private fun showSettingPopup() {
        val inflater = getActivity()!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view1 = inflater.inflate(R.layout.sub_popup1, null)

        val alertDialog = AlertDialog.Builder(getActivity()!!)
            .setTitle("관심있는 과목")
            .create()

        alertDialog.setView(view1)
        alertDialog.show()
    }
}