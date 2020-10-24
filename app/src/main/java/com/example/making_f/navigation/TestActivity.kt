package com.example.making_f.navigation

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.making_f.R
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.*
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val remoteModel = FirebaseCustomRemoteModel.Builder("softmax").build()

        val conditions = FirebaseModelDownloadConditions.Builder()
            .requireWifi()
            .build()
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
            .addOnCompleteListener {
                //success
            }

        val localModel = FirebaseCustomLocalModel.Builder()
            .setAssetFilePath("model.tflite")
            .build()

        var sek1: Int
        var sek2 = 0
        var sek3 = 0
        var sek4 = 0
        var sek5 = 0
        var sek6 = 0
        var sek7 = 0
        var input = arrayOf(0,0,0,0,0,0,0)

        btn_popup_result.setOnClickListener { view ->
            sek1 = seek1.progress
            sek2 = seek2.progress
            sek3 = seek3.progress
            sek4 = seek4.progress
            sek5 = seek5.progress
            sek6 = seek6.progress
            sek7 = seek7.progress

            val input = Array(1){FloatArray(7)}

            input[0][0] = sek1.toFloat()
            input[0][1] = sek2.toFloat()
            input[0][2] = sek3.toFloat()
            input[0][3] = sek4.toFloat()
            input[0][4] = sek5.toFloat()
            input[0][5] = sek6.toFloat()
            input[0][6] = sek7.toFloat()

            val options = FirebaseModelInterpreterOptions.Builder(localModel).build()

            val interpreter = FirebaseModelInterpreter.getInstance(options)
            val inputOutputOptions = FirebaseModelInputOutputOptions.Builder()
                .setInputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 7))
                .setOutputFormat(0, FirebaseModelDataType.FLOAT32, intArrayOf(1, 5))
                .build()
            val inputs = FirebaseModelInputs.Builder()
                .add(input)
                .build()
            interpreter?.run(inputs, inputOutputOptions)
                ?.addOnSuccessListener { result ->
                    var output = result.getOutput<Array<FloatArray>>(0)
                    val probabilities = output[0]
                    var bestMatch = 0f
                    var bestMatchIndex = 0
                    for (i in probabilities.indices){
                        if(probabilities[i]>bestMatch){
                            bestMatch = probabilities[i]
                            bestMatchIndex = i
                        }
                    }

                    var tmp = Arrays.toString(output[0])
                    Log.d("HUSKY2", "${output}")
                    //Toast.makeText(this, "${tmp}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "${tmp}, ${bestMatchIndex}", Toast.LENGTH_LONG).show()
                }
                ?.addOnFailureListener {e ->
                    Log.d("HUSKY2", "GGWP :(${e.toString()}")
                }
        }
    }
}
