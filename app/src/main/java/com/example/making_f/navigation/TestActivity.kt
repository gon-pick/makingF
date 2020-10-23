package com.example.making_f.navigation

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.making_f.R
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.FirebaseCustomLocalModel
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel
import com.google.firebase.ml.custom.FirebaseModelInterpreter
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val localModel = FirebaseCustomLocalModel.Builder()
            .setAssetFilePath("model.tflite")
            .build()

        val options = FirebaseModelInterpreterOptions.Builder(localModel).build()
        val interpreter = FirebaseModelInterpreter.getInstance(options)

        var sek1: Int
        var sek2 = 0
        var sek3 = 0
        var sek4 = 0
        var sek5 = 0
        var sek6 = 0
        var sek7 = 0

        btn_popup_result.setOnClickListener { view ->
            sek1 = seek1.progress
            sek2 = seek2.progress
            sek3 = seek3.progress
            sek4 = seek4.progress
            sek5 = seek5.progress
            sek6 = seek6.progress
            sek7 = seek7.progress
            Toast.makeText(this, "${sek1}, ${sek2}, ${sek3}, ${sek4}, ${sek5}, ${sek6}, ${sek7}", Toast.LENGTH_SHORT).show()
        }
    }
}