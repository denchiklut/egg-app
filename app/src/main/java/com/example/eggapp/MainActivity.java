package com.example.eggapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controlButton;
    Boolean isActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        isActive = false;
        countDownTimer.cancel();
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        timerTextView.setText("0:30");
        controlButton.setText("Go");
    }

    public void updateTime(int timesLeft){
        int minutes = timesLeft / 60;
        int seconds = timesLeft - minutes * 60;
        String strSeconds;

        if (seconds <= 9){
            strSeconds = "0" + seconds;
        } else {
            strSeconds = Integer.toString(seconds);
        }
        timerTextView.setText(Integer.toString(minutes) + ":" + strSeconds);
    }

    public void timerControl(View view) {
        if (!isActive) {
            isActive = true;
            controlButton.setText("Stop");
            timerSeekBar.setEnabled(false);
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000 + 100, 1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTime((int)millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    player.start();
                    resetTimer();
                }
            }.start();
        } else {
            resetTimer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timeTextView);
        controlButton = findViewById(R.id.controlButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerTextView.setText("0:30");

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
