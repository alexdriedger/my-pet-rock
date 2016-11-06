package com.example.alexdriedger.mypetrock;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.bumptech.glide.Glide.with;

public class MainActivity extends AppCompatActivity {

    public int muscleCount = 1;
    public int smileCount = 1;
    public int moneyCount = 1;
    public boolean hasCreatine = false;

    private int MAX_COUNT = 5;


    public void render() {
        final ImageView mainImage = (ImageView) findViewById(R.id.main_image);
        int state = muscleCount * 100;
        switch (state) {
            case 000:
            case 001:
                Glide.with(mainImage.getContext())
                        .load(R.drawable.state_000)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .crossFade(100)
                        .into(mainImage);
                break;
            case 010:
            case 011:
            case 111:
                Glide.with(mainImage.getContext())
                        .load(R.drawable.state_111)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .crossFade(100)
                        .into(mainImage);
                break;
            case 002:
            case 100:
            case 101:
            case 104:
            case 103:
                Glide.with(mainImage.getContext())
                        .load(R.drawable.state_103)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .crossFade(100)
                        .into(mainImage);
                break;
            case 200:
                Glide.with(mainImage.getContext())
                        .load(R.drawable.state_200)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .crossFade(100)
                        .into(mainImage);
                break;
            case 300:
                Glide.with(mainImage.getContext())
                        .load(R.drawable.state_400)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .crossFade(100)
                        .into(mainImage);
                break;
        }
    }

    public void startCountdown() {

        new CountDownTimer(30000, 10000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if(muscleCount!= 0) {
                    muscleCount--;
                    render();
                    startCountdown();
                }
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        final ImageView mainImage = (ImageView) findViewById(R.id.main_image);
        final ImageView mainBackground = (ImageView) findViewById(R.id.main_background);
        final ImageView bActionWorkout = (ImageView) findViewById(R.id.muscle_button);
        final ImageView bActionEat = (ImageView) findViewById(R.id.food_button);
        final ImageView bActionToothBrush = (ImageView) findViewById(R.id.toothbrush_button);
        final ImageView bActionQuote = (ImageView) findViewById(R.id.quote_button);

//        bActionWorkout.setVisibility(View.INVISIBLE);


        //Glide.with(mainImage.getContext()).load(R.drawable.state_111).into(mainImage);
        Glide.with(mainImage.getContext()).load(R.drawable.background_basic).into(mainBackground);
        // Load Icons
        Glide.with(mainImage.getContext()).load(R.drawable.nav_dumbbell_icon_yellow).into(bActionWorkout);
        Glide.with(mainImage.getContext()).load(R.drawable.nav_protein_icon_yellow).into(bActionEat);
        Glide.with(mainImage.getContext()).load(R.drawable.nav_toothbrush_icon_yellow).into(bActionToothBrush);
        Glide.with(mainImage.getContext()).load(R.drawable.nav_quote_icon_yellow).into(bActionQuote);

        Bundle extras = getIntent().getExtras();

        this.hasCreatine = extras.getBoolean("hasCreatine", false);
        this.muscleCount = extras.getInt("muscleCount", 1);
        this.moneyCount = extras.getInt("moneyCount", 1);
        this.smileCount = extras.getInt("smileCount", 1);

        render();

        if(muscleCount != 0) {
            startCountdown();
        }

        bActionWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCreatine && muscleCount != MAX_COUNT) {
                    hasCreatine = false;
                    muscleCount++;

                    // Start Gif Activity
                    Intent gifIntent = new Intent(MainActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "animation_lifting");
                    gifIntent.putExtra("GIF_LENGTH", 4000);
                    gifIntent.putExtra("hasCreatine", hasCreatine);
                    gifIntent.putExtra("muscleCount", muscleCount);
                    gifIntent.putExtra("moneyCount", moneyCount);
                    gifIntent.putExtra("smileCount", smileCount);
                    startActivity(gifIntent);
                }
            }
        });

        bActionEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasCreatine && moneyCount != 0) {
                    hasCreatine = true;
                    moneyCount--;

                    // Start Gif Activity
                    Intent gifIntent = new Intent(MainActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "animation_lifting");
                    gifIntent.putExtra("GIF_LENGTH", 4000);
                    gifIntent.putExtra("hasCreatine", hasCreatine);
                    gifIntent.putExtra("muscleCount", muscleCount);
                    gifIntent.putExtra("moneyCount", moneyCount);
                    gifIntent.putExtra("smileCount", smileCount);
                    startActivity(gifIntent);
                }
            }
        });

        bActionToothBrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smileCount != MAX_COUNT) {
                    smileCount++;

                    // Start Gif Activity
                    Intent gifIntent = new Intent(MainActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "animation_teethbrushing");
                    gifIntent.putExtra("GIF_LENGTH", 4000);
                    gifIntent.putExtra("hasCreatine", hasCreatine);
                    gifIntent.putExtra("muscleCount", muscleCount);
                    gifIntent.putExtra("moneyCount", moneyCount);
                    gifIntent.putExtra("smileCount", smileCount);
                    startActivity(gifIntent);
                }
            }
        });


        bActionQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moneyCount != MAX_COUNT && smileCount != 0) {
                    moneyCount++;
                    smileCount--;

                    // Start Gif Activity
                    Intent gifIntent = new Intent(MainActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "animation_money");
                    gifIntent.putExtra("GIF_LENGTH", 4000);
                    gifIntent.putExtra("hasCreatine", hasCreatine);
                    gifIntent.putExtra("muscleCount", muscleCount);
                    gifIntent.putExtra("moneyCount", moneyCount);
                    gifIntent.putExtra("smileCount", smileCount);
                    startActivity(gifIntent);
                }
            }
        });
    }
}
