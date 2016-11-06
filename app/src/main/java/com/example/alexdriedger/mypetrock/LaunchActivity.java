package com.example.alexdriedger.mypetrock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

        ImageView mainImage = (ImageView) findViewById(R.id.launch_image_view);
        ImageView backgroundImage = (ImageView) findViewById(R.id.launch_background);
        RelativeLayout waterCanButton = (RelativeLayout) findViewById(R.id.water_can_button);
        ImageView waterCanImage = (ImageView) findViewById(R.id.water_can_image);

        Glide.with(this).load(R.drawable.background_basic).into(backgroundImage);
        Glide.with(this).load(R.drawable.stage1_rock_img).into(mainImage);
        Glide.with(this).load(R.drawable.nav_water_icon).into(waterCanImage);

        waterCanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRockClicks < 2) {
                    mRockClicks++;
                    //Toast.makeText(getApplicationContext(), mRockClicks, Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent gifIntent = new Intent(LaunchActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "rock_cracking_gif_small");
                    gifIntent.putExtra("GIF_LENGTH", 10000);
                    startActivity(gifIntent);
                }
            }
        });
    }
}
