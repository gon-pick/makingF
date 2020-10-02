package com.example.making_f.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.making_f.Activity.AddPhotoActivity
import com.example.making_f.Activity.ProfileEditActivity
import com.example.making_f.R
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_post, container, false)

        view.board_write.setOnClickListener {
            Log.d("board_write move", "board_write move")
            var intent = Intent(getActivity(), AddPhotoActivity::class.java)
            startActivity(intent)
            getActivity()?.finish()
        }
        return view
    }
}