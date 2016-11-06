package com.example.alexdriedger.mypetrock;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.bumptech.glide.Glide.with;

public class MainActivity extends AppCompatActivity {

    public int muscleCount = 0;
    public int smileCount = 0;
    public int moneyCount = 0;
    public boolean hasCreatine = false;

    private int MAX_COUNT = 3;

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

        bActionWorkout.setVisibility(View.INVISIBLE);


        Glide.with(mainImage.getContext()).load(R.drawable.stage1_rock_img).into(mainImage);
        Glide.with(mainImage.getContext()).load(R.drawable.background_basic).into(mainBackground);

        bActionWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasCreatine && muscleCount != MAX_COUNT) {
                    hasCreatine = false;
                    muscleCount++;
                    bActionWorkout.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "ROCK WORKOUT, NOW LEVEL: " + muscleCount, Toast.LENGTH_SHORT).show();
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
                    bActionWorkout.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "ROCK EAT FOOD", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "ROCK SMILE ROCK HAVE: " + smileCount, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "ROCK SAY NICE THINGS, ROCK HAVE: $" + moneyCount, Toast.LENGTH_SHORT).show();
                } else if(smileCount == 0) {
                    Toast.makeText(getApplicationContext(), "ROCK NOT PRETTY ENOUGH, NEED BRUSH TEETH", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ROCK MUCH BLING BLING, TAKE BREAK", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void render() {

    }
}
