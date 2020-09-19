package com.example.making_f.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.making_f.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class gitHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_home, container, false)

        view.manual.setOnClickListener{ view ->
            Log.d("manual", "Selected")
            Toast.makeText(getActivity()!!, "네비게이션 바 클릭", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}