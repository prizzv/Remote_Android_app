package com.practice.remoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Devices_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);
    }

    public void nextActivity(View view){
        ImageButton tapped =(ImageButton) view;

        int tag = Integer.parseInt(tapped.getTag().toString());

        Log.d("TAG", "nextActivity: ");

        Intent intent = new Intent(getApplicationContext(), Appliances_List.class);

        if(tag == 1){
            intent.putExtra("activityTag", 1);
            Log.d("Intent", "Tv lists started");
        }else if(tag == 2){
            intent.putExtra("activityTag", 2);
            Log.d("Intent", "AC lists started");
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        startActivity(intent);
    }
}