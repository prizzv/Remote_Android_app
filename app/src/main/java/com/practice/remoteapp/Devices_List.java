package com.practice.remoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Devices_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);

        SharedPreferences appSettingsPreferences = getSharedPreferences("AppSettingPrefs", 0);
        boolean isNightModeOn = appSettingsPreferences.getBoolean("NightMode", false);

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
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

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  // this function is to show the menu

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);  // this is how to access menus

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            // set Dark mode on or off
            case R.id.dark_mode_switch:
                Log.i("Item Selected", "Dark Mode");

                SharedPreferences appSettingsPreferences = getSharedPreferences("AppSettingPrefs", 0);
                SharedPreferences.Editor sharedPrefsEdit = appSettingsPreferences.edit();
                boolean isNightModeOn = appSettingsPreferences.getBoolean("NightMode", false);

                if(isNightModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPrefsEdit.putBoolean("NightMode", false);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPrefsEdit.putBoolean("NightMode", true);
                }
                sharedPrefsEdit.apply();

                return true;
            default:
                return false;
        }
    }
}