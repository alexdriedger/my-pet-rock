package com.example.alexdriedger.mypetrock;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Movie;
import android.media.AudioManager;
import android.media.MediaPlayer;
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

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;
import static android.view.KeyCharacterMap.load;
import static java.security.AccessController.getContext;

/**
 * Used to for displaying Gifs
 */

public class GifActivity extends AppCompatActivity {

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
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();

        Glide.clear(findViewById(R.id.gif_view));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_gif);

        final Bundle extras = getIntent().getExtras();
        final String gifResource = extras.getString("GIF_TO_PLAY");
        final String audioResource = extras.getString("GIF_TO_PLAY") + "_1";
        int gifLength = extras.getInt("GIF_LENGTH");

        Resources r = getResources();
        final int gifID = r.getIdentifier(gifResource, "raw", "com.example.alexdriedger.mypetrock");
        final int audioID = r.getIdentifier(audioResource, "raw", "com.example.alexdriedger.mypetrock");

//        final LinearLayout parentView = (LinearLayout) findViewById(R.id.linlay_gif);

        final ImageView gifView = (ImageView) findViewById(R.id.gif_view);

        Glide.with(getApplicationContext()).load(gifID).into(gifView);

//        final View gifView = new GIFView(getApplicationContext()) {
//            @Override
//            public void initializeView() {
//                super.initializeView();
//                InputStream is = getContext().getResources().openRawResource(gifID);
//                mMovie = Movie.decodeStream(is);
//            }
//        };
//        gifView.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//
//        parentView.addView(gifView);

        new CountDownTimer(gifLength, 600) {

            public void onTick(long millisUntilFinished) {
                // Do Nothing
            }

            public void onFinish() {
//                parentView.removeView(gifView);
                Intent backToMain = new Intent(GifActivity.this, MainActivity.class);

                if(extras.size() == 2) {
                    backToMain.putExtra("hasCreatine", false);
                    backToMain.putExtra("muscleCount", 1);
                    backToMain.putExtra("moneyCount", 1);
                    backToMain.putExtra("smileCount", 0);
                } else {
                    backToMain.putExtra("hasCreatine", extras.getBoolean("hasCreatine"));
                    backToMain.putExtra("muscleCount", extras.getInt("muscleCount"));
                    backToMain.putExtra("moneyCount", extras.getInt("moneyCount"));
                    backToMain.putExtra("smileCount", extras.getInt("smileCount"));
                }
                releaseMediaPlayer();

                startActivity(backToMain);
            }
        }.start();

        // AudioManager to handle playing audio
        Context mContext = getApplicationContext();
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        int audioFocusResult = mAudioManager.requestAudioFocus(afChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        //Only play audio if audio focus is granted
        //mMediaPlayer is initialized to null and reset to null after every use.
        //mMediaPlayer is only called to play a new file iff nothing is currently playing
        if (audioFocusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED && mMediaPlayer == null) {
            //A Media Player object is created with the Audio Resource Id of the current
            //word
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), audioID);

            //Audio starts playing
            mMediaPlayer.start();

            //An OnCompletionLister is created for the Media Player object that was
            //just created
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);

            //Toast.makeText(getApplicationContext(), mRockClicks, Toast.LENGTH_SHORT).show();
        }
    }
}
