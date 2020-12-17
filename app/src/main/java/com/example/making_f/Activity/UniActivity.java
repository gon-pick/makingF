package com.example.making_f.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.making_f.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class UniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);

        readWeatherData();
    }

    private List<WeatherSample> weatherSamples= new ArrayList<>();
    private void readWeatherData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("EUC-KR"))
        );

        String line="";
        try{
            while((line = reader.readLine())!=null) {
                //Split by ','
                Log.d("UniActivity","Line: " + line);
                String[] tokens = line.split(",");


                //Read the data
                WeatherSample sample = new WeatherSample();
                sample.setUniSeparators(tokens[0]);
                sample.setLocation(tokens[1]);
                sample.setUniversity(tokens[2]);
                sample.setDepartment(tokens[3]);

                weatherSamples.add(sample);
                Log.d("UniActivity","UniDepart " + sample.getDepartment());
            }

        } catch(IOException e){
            Log.wtf("MyActivity","Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }
}
