package com.example.alexdriedger.mypetrock;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    public int muscleCount = 0;
    public int smileCount = 0;
    public int moneyCount = 0;
    public boolean hasCreatine = false;

    private int MAX_COUNT = 5;


    public void render() {
        final ImageView mainImage = (ImageView) findViewById(R.id.main_image);
        int state = muscleCount * 100 + smileCount * 10 + moneyCount;
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
                        .load(R.drawable.state_310)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .crossFade(100)
                        .into(mainImage);
                break;
            case 400:
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
                }
                startCountdown();
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


        Glide.with(mainImage.getContext()).load(R.drawable.state_111).into(mainImage);
        Glide.with(mainImage.getContext()).load(R.drawable.background_basic).into(mainBackground);
        // Load Icons
        Glide.with(mainImage.getContext()).load(R.drawable.nav_dumbbell_icon_yellow).into(bActionWorkout);
        Glide.with(mainImage.getContext()).load(R.drawable.nav_protein_icon_yellow).into(bActionEat);
        Glide.with(mainImage.getContext()).load(R.drawable.nav_toothbrush_icon_yellow).into(bActionToothBrush);
        Glide.with(mainImage.getContext()).load(R.drawable.nav_quote_icon_yellow).into(bActionQuote);

        startCountdown();

        bActionWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCreatine && muscleCount != MAX_COUNT) {
                    hasCreatine = false;
                    muscleCount++;
                    //bActionWorkout.setVisibility(View.INVISIBLE);
                    //Toast.makeText(getApplicationContext(), "ROCK WORKOUT, NOW LEVEL: " + muscleCount, Toast.LENGTH_SHORT).show();

                    // Start Gif Activity
                    Intent gifIntent = new Intent(MainActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "animation_lifting");
                    gifIntent.putExtra("GIF_LENGTH", 4000);
                    startActivity(gifIntent);

                    render();
                } else if(!hasCreatine) {
                    Toast.makeText(getApplicationContext(), "ROCK NEED CREATINE TO WORKOUT", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ROCK ALREADY TOO STRONG, TAKE BREAK", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bActionEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasCreatine && moneyCount != 0) {
                    hasCreatine = true;
                    moneyCount--;
                    //bActionWorkout.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "ROCK EAT FOOD", Toast.LENGTH_SHORT).show();

                    // Start Gif Activity
                    Intent gifIntent = new Intent(MainActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "animation_lifting");
                    gifIntent.putExtra("GIF_LENGTH", 4000);
                    startActivity(gifIntent);

                    render();
                } else if(moneyCount == 0) {
                    Toast.makeText(getApplicationContext(), "ROCK NEED MONEY", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ROCK ALREADY FULL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bActionToothBrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smileCount != MAX_COUNT) {
                    smileCount++;
                    //Toast.makeText(getApplicationContext(), "ROCK SMILE ROCK HAVE: " + smileCount, Toast.LENGTH_SHORT).show();

                    // Start Gif Activity
                    Intent gifIntent = new Intent(MainActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "animation_teethbrushing");
                    gifIntent.putExtra("GIF_LENGTH", 4000);
                    startActivity(gifIntent);

                    render();
                } else {
                    Toast.makeText(getApplicationContext(), "ROCK SMILE TOO MUCH, TAKE BREAK", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bActionQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moneyCount != MAX_COUNT && smileCount != 0) {
                    moneyCount++;
                    smileCount--;
                    //Toast.makeText(getApplicationContext(), "ROCK SAY NICE THINGS, ROCK HAVE: $" + moneyCount, Toast.LENGTH_SHORT).show();

                    // Start Gif Activity
                    Intent gifIntent = new Intent(MainActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "animation_money");
                    gifIntent.putExtra("GIF_LENGTH", 4000);
                    startActivity(gifIntent);

                    render();
                } else if(smileCount == 0) {
                    Toast.makeText(getApplicationContext(), "ROCK NOT PRETTY ENOUGH, NEED BRUSH TEETH", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ROCK MUCH BLING BLING, TAKE BREAK", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
