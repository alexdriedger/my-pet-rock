package com.example.alexdriedger.mypetrock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * Opening Activity for app. Begins with a pet rock.
 * Once the rock is watered twice, the rock is sent to {@link GifActivity}
 */

public class LaunchActivity extends AppCompatActivity {

    // Number of times the rock has been watered
    private int mRockClicks;
    private MediaPlayer mMediaPlayer = null;
    private AudioManager mAudioManager;

    //Media Player OnCompletionLister to clean up resources once the audio is finished playing
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS ||
                            focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Stop playback
                        releaseMediaPlayer();
                    }
                }};

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_launch);
        mRockClicks = 0;

        // Find Views
        ImageView mainImage = (ImageView) findViewById(R.id.launch_image_view);
        ImageView backgroundImage = (ImageView) findViewById(R.id.launch_background);
        RelativeLayout waterCanButton = (RelativeLayout) findViewById(R.id.water_can_button);
        ImageView waterCanImage = (ImageView) findViewById(R.id.water_can_image);

        // Load images
        Glide.with(this).load(R.drawable.background_basic).into(backgroundImage);
        Glide.with(this).load(R.drawable.state_opening_rock).into(mainImage);
        Glide.with(this).load(R.drawable.nav_water_icon).into(waterCanImage);

        // AudioManager to handle playing audio
        Context mContext = getApplicationContext();
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        waterCanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRockClicks < 2) {
                    mRockClicks++;

                    int audioFocusResult = mAudioManager.requestAudioFocus(afChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    //Only play audio if audio focus is granted
                    //mMediaPlayer is initialized to null and reset to null after every use.
                    //mMediaPlayer is only called to play a new file iff nothing is currently playing
                    if (audioFocusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED && mMediaPlayer == null) {
                        //A Media Player object is created with the Audio Resource Id of the current
                        //word
                        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oh_yeah_sound);

                        //Audio starts playing
                        mMediaPlayer.start();

                        //An OnCompletionLister is created for the Media Player object that was
                        //just created
                        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);

                        //Toast.makeText(getApplicationContext(), mRockClicks, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Intent gifIntent = new Intent(LaunchActivity.this, GifActivity.class);
                    gifIntent.putExtra("GIF_TO_PLAY", "rock_cracking_gif_small");
                    gifIntent.putExtra("GIF_LENGTH", 8000);
                    startActivity(gifIntent);
                }
            }
        });
    }
}
