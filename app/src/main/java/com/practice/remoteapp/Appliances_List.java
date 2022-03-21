package com.practice.remoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Appliances_List extends AppCompatActivity {

    ListView devicesListView;
    ArrayList<String> deviceName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances_list);

        devicesListView = findViewById(R.id.devices_List_View);
        Intent intentNo = getIntent();
        int tagNo = intentNo.getIntExtra("activityTag", -1);

        if(tagNo == 1){
            deviceName.add("Samsung Tv");
            deviceName.add("LG Tv");

            Class<MainActivity> activityClass = MainActivity.class;

            devicesListOnClick(activityClass);

        }else if(tagNo == 2){
            deviceName.add("Mitsubishi");

            Class<AC_Activity> activityClass = AC_Activity.class;

            devicesListOnClick(activityClass);

        }else{
            Log.d("Error", "onCreate: ");

        }
    }

    public void devicesListOnClick(Class xyz){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceName);
        devicesListView.setAdapter(arrayAdapter);

        devicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplication(), xyz);
                intent.putExtra("devicePosition", position);

                Log.d("Position", "onItemClick: " + position);
                startActivity(intent);
            }
        });

    }
}