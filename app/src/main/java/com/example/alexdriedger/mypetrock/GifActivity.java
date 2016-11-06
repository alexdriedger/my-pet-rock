package com.example.alexdriedger.mypetrock;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.TimerTask;

/**
 * Used to for displaying Gifs
 */

public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_gif);

        Bundle extras = getIntent().getExtras();
        final String gifResource = extras.getString("GIF_TO_PLAY");

        Resources r = getResources();
        final int gifID = r.getIdentifier(gifResource, "raw", "com.example.alexdriedger.mypetrock");

        final LinearLayout parentView = (LinearLayout) findViewById(R.id.linlay_gif);

        final View gifView = new GIFView(getApplicationContext()) {
            @Override
            public void initializeView() {
                super.initializeView();
                InputStream is = getContext().getResources().openRawResource(gifID);
                mMovie = Movie.decodeStream(is);
            }
        };
        gifView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        parentView.addView(gifView);

        new CountDownTimer(4000, 600) {

            public void onTick(long millisUntilFinished) {
                // Do Nothing
            }

            public void onFinish() {
                parentView.removeView(gifView);
                Intent backToMain = new Intent(GifActivity.this, MainActivity.class);
                startActivity(backToMain);
            }
        }.start();
    }
}
