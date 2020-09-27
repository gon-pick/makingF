package com.example.making_f.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.making_f.Activity.LoginActivity
import com.example.making_f.Activity.ProfileEditActivity
import com.example.making_f.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user.view.*

class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_user, container, false)

        view.mypage_imgbutton.setOnClickListener { view ->
            Log.d("mypage_imgbutton", "Selected")
            var intent = Intent(getActivity(), ProfileEditActivity::class.java)
            startActivity(intent)
        }

        view.txt_mypage_logout.setOnClickListener { view ->
            FirebaseAuth.getInstance().signOut()
            getActivity()?.finish()
            var intent = Intent(getActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}