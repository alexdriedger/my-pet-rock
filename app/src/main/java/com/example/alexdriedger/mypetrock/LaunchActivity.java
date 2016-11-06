package com.example.alexdriedger.mypetrock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by Alex Driedger on 2016-11-05.
 */

public class LaunchActivity extends AppCompatActivity {

    private int mRockClicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_launch);
        mRockClicks = 0;

        ImageView imageView = (ImageView) findViewById(R.id.launch_image_view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRockClicks < 2) {
                    mRockClicks++;
                    //Toast.makeText(getApplicationContext(), mRockClicks, Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent gifIntent = new Intent(LaunchActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "rock_cracking_gif_small");
                    startActivity(gifIntent);
                }
            }
        });
    }
}
