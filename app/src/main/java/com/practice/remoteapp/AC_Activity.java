package com.practice.remoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.practice.remoteapp.IrCodes.Mitsubishi_Codes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AC_Activity extends AppCompatActivity {

    private ConsumerIrManager irManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_activity);
        irManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);
//        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        mp = MediaPlayer.create(this, R.raw.beep);

        Intent intent = getIntent();
        int deviceNameNo = intent.getIntExtra("devicePosition", -1);
        Log.d("Position", "onItemClick: " + deviceNameNo);

        if(deviceNameNo == 0){      // Hitachi
            findViewById(R.id.acPowerImageButton).setOnClickListener(new AC_Activity.ClickListener(hex2ir(Mitsubishi_Codes.getCmdPowerOff())));

            findViewById(R.id.autoCoolButton).setOnClickListener(new ClickListener(hex2ir(Mitsubishi_Codes.getCmdAutoCool())));
            findViewById(R.id.tempIncreaseButton).setOnClickListener(new AC_Activity.ClickListener(hex2ir(Mitsubishi_Codes.getCMD_Cool_HIGH())));
            findViewById(R.id.tempDecreaseButton).setOnClickListener(new AC_Activity.ClickListener(hex2ir(Mitsubishi_Codes.getCmdCoolLow())));

            findViewById(R.id.fanAutoButton).setOnClickListener(new AC_Activity.ClickListener(hex2ir(Mitsubishi_Codes.getCmdFanAuto())));
            findViewById(R.id.fanSpeedIncreaseButton).setOnClickListener(new AC_Activity.ClickListener(hex2ir(Mitsubishi_Codes.getCmdFanHigh())));
            findViewById(R.id.fanSpeedDecreaseButton).setOnClickListener(new AC_Activity.ClickListener(hex2ir(Mitsubishi_Codes.getCmdFanLow())));

            findViewById(R.id.dryModeButton).setOnClickListener(new AC_Activity.ClickListener(hex2ir(Mitsubishi_Codes.getCmdDryMode())));

        }else if (deviceNameNo == -1){  // just to check the error
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private AC_Activity.IRCommand hex2ir(final String irData){
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

        return new AC_Activity.IRCommand(frequency, pattern);
    }

    private class ClickListener implements View.OnClickListener {
        private final AC_Activity.IRCommand cmd;

        public ClickListener(final AC_Activity.IRCommand cmd) {
            this.cmd = cmd;
        }

        @Override
        public void onClick(final View view) {

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