package com.practice.remoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.practice.remoteapp.IrCodes.Samsung_Codes;

public class MainActivity extends AppCompatActivity {

//    private String TAG = "MainActivity";

    private ConsumerIrManager irManager;
//    private Vibrator vibe;
//    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        irManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
//        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        mp = MediaPlayer.create(this, R.raw.beep);

        Intent intent = getIntent();
        int deviceNameNo = intent.getIntExtra("devicePosition", -1);
        Log.d("Position", "onItemClick: "+ deviceNameNo);

        if(deviceNameNo == 0){      // Samsung Codes

            findViewById(R.id.powerImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvPower())));
//        findViewById(R.id.tvsource).setOnClickListener(new ClickListener(hex2ir(CMD_TV_SOURCE)));
            findViewById(R.id.channelIncreaseImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvChNext())));
            findViewById(R.id.channelDecreaseImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvChPrev())));
            findViewById(R.id.backImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvBack())));
            findViewById(R.id.leftArrowImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvLeft())));
            findViewById(R.id.rightArrowImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvRight())));
            findViewById(R.id.okButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvEnter())));

            findViewById(R.id.upArrowImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvUp())));
            findViewById(R.id.downArrowImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvDown())));
            findViewById(R.id.muteImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdTvMute())));

            findViewById(R.id.volumeIncreaseImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdVolup())));
            findViewById(R.id.volumeDecreaseImageButton).setOnClickListener(new ClickListener(hex2ir(Samsung_Codes.getCmdVoldown())));
        }else if(deviceNameNo == 1){  // LG Codes

        }else if (deviceNameNo == -1){  // just to check the error
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private IRCommand hex2ir(final String irData){
        List<String> list = new ArrayList<String>(Arrays.asList(irData.split(" ")));
        list.remove(0); // dummy
        int frequency = Integer.parseInt(list.remove(0), 16); // frequency
        list.remove(0); // seq1
        list.remove(0); // seq2

        frequency = (int) (1000000 / (frequency * 0.241246));
        int pulses = 1000000 / frequency;
        int count;

        int[] pattern = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            count = Integer.parseInt(list.get(i), 16);
            pattern[i] = count * pulses;
        }

        return new IRCommand(frequency, pattern);
    }

    private class ClickListener implements View.OnClickListener {
        private final IRCommand cmd;

        public ClickListener(final IRCommand cmd) {
            this.cmd = cmd;
        }

        @Override
        public void onClick(final View view) {

//            mp.start();

//            ToneGenerator toneGenerator = new ToneGenerator(1, 100);
//            toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200); //200 is duration in ms
/*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibe.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibe.vibrate(150);
            }
*/
            try {
                android.util.Log.d("Remote", "frequency: " + cmd.freq);
                android.util.Log.d("Remote", "pattern: " + Arrays.toString(cmd.pattern));
                irManager.transmit(cmd.freq, cmd.pattern);

            } catch (Exception e) {
                Log.e("TAG", e.getMessage());
            }
        }
    }

    private class IRCommand {
        private final int freq;
        private final int[] pattern;

        private IRCommand(int freq, int[] pattern) {
            this.freq = freq;
            this.pattern = pattern;
        }
    }
}
