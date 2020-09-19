package com.example.making_f

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var sek1 = 1
        var sek2 = 0
        var sek3 = 0
        var sek4 = 0
        var sek5 = 0
        var sek6 = 0
        var sek7 = 0

        seek1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                TODO("Not yet implemented")
                sek1 = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

        })

        btn_popup_result.setOnClickListener { view ->
            Toast.makeText(this, "${sek1}", Toast.LENGTH_SHORT).show()
        }
    }
}