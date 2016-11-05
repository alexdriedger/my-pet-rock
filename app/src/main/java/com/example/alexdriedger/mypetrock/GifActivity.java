package com.example.alexdriedger.mypetrock;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.TimerTask;

/**
 * Used to for displaying Gifs
 */

public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_gif);

        new CountDownTimer(800, 600) {

            public void onTick(long millisUntilFinished) {
                // Do Nothing
            }

            public void onFinish() {
                Intent backToMain = new Intent(GifActivity.this, MainActivity.class);
                startActivity(backToMain);
            }
        }.start();
    }
}
