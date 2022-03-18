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
        deviceName.add("Samsung Tv");
        deviceName.add("LG Tv");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceName);
        devicesListView.setAdapter(arrayAdapter);

        devicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("devicePosition", position);
                startActivity(intent);
                Log.d("Position", "onItemClick: " + position);
            }
        });
    }
}