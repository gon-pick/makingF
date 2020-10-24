package com.example.making_f.Activity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.making_f.R
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager
import com.google.firebase.ml.custom.*
import kotlinx.android.synthetic.main.activity_test.*

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
        var bestMatchIndex = 0
        btn_popup_result.setOnClickListener { view ->
            sek1 = seek1.progress
            sek2 = seek2.progress
            sek3 = seek3.progress
            sek4 = seek4.progress
            sek5 = seek5.progress
            sek6 = seek6.progress
            sek7 = seek7.progress
            val builder = AlertDialog.Builder(this)
            val dialogView1 = layoutInflater.inflate(R.layout.sub_popup1, null)
            val dialogView2 = layoutInflater.inflate(R.layout.sub_popup2, null)
            val dialogView3 = layoutInflater.inflate(R.layout.sub_popup3, null)
            val dialogView4 = layoutInflater.inflate(R.layout.sub_popup4, null)
            val dialogView5 = layoutInflater.inflate(R.layout.sub_popup5, null)

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

                    for (i in probabilities.indices){
                        if(probabilities[i]>bestMatch){
                            bestMatch = probabilities[i]
                            bestMatchIndex = i
                        }
                    }
                    if(bestMatchIndex == 0) {
                        //시스템 엔지니어
                        builder.setView(dialogView1)
                            .show()
                    }
                    else if(bestMatchIndex == 1) {
                        //시스템 소프트웨어 개발자
                        builder.setView(dialogView2)
                            .show()
                    }
                    else if(bestMatchIndex == 2) {
                        //네트워크 엔지니어
                        builder.setView(dialogView3)
                            .show()
                    }
                    else if(bestMatchIndex == 3) {
                        //데이터베이스 관리자
                        builder.setView(dialogView4)
                            .show()
                    }
                    else if(bestMatchIndex ==4) {
                        //응용 소프트웨어 개발자자
                        builder.setView(dialogView5)
                            .show()
                    }

                    //Toast.makeText(this, "${tmp}", Toast.LENGTH_SHORT).show()
                    //Toast.makeText(this, "${tmp}, ${bestMatchIndex}", Toast.LENGTH_LONG).show()

                }
                ?.addOnFailureListener {e ->
                    Log.d("HUSKY2", "GGWP :(${e.toString()}")
                }

        }
    }


}
