package com.example.making_f.navigation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.making_f.R
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.sub_popup.*
import kotlinx.android.synthetic.main.sub_popup.view.*

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
        return view
    }

    private fun showSettingPopup() {
        val inflater = getActivity()!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view1 = inflater.inflate(R.layout.sub_popup, null)
        var sek1 = 1
        var sek2 = 0
        var sek3 = 0
        var sek4 = 0
        var sek5 = 0
        var sek6 = 0
        var sek7 = 0

        val alertDialog = AlertDialog.Builder(getActivity()!!)
            .setTitle("관심있는 과목")
            .create()

        alertDialog.setView(view1)
        alertDialog.show()

        val seekbar1 : SeekBar = view1.findViewById(R.id.seek1)
        seekbar1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
                sek1 = seekbar1!!.progress
            }
        })

        view1.btn_popup_result.setOnClickListener { view1 ->
            Log.d("btn_popup_result", "Selected")
            Toast.makeText(getActivity()!!, "클릭되었읍니다. ${sek1}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(getActivity()!!, TestActivity::class.java))
            alertDialog.dismiss()
        }
    }

}