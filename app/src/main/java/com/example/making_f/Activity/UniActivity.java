package com.example.making_f.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.making_f.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class UniActivity extends AppCompatActivity {

    private ArrayList<UniSample> UniSamples = new ArrayList<>();
    private UniAdapter uniAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    String[] items = {"대학구분","지역","학교","학과"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        readWeatherData();

        recyclerView = (RecyclerView)findViewById(R.id.universityActivity_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        uniAdapter = new UniAdapter(UniSamples);
        recyclerView.setAdapter(uniAdapter);

    }

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
                UniSample sample = new UniSample();
                sample.setUniSeparators(tokens[0]);
                sample.setLocation(tokens[1]);
                sample.setUniversity(tokens[2]);
                sample.setDepartment(tokens[3]);

                UniSamples.add(sample);
                Log.d("UniActivity","UniDepart " + sample.getDepartment());
            }

        } catch(IOException e){
            Log.wtf("MyActivity","Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }
}
