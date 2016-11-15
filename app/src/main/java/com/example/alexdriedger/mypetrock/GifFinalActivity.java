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
import android.widget.LinearLayout;

import java.io.InputStream;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * Used to for displaying Gifs
 */

public class GifFinalActivity extends AppCompatActivity {

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
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_gif);

        final LinearLayout parentView = (LinearLayout) findViewById(R.id.linlay_gif);

        final View gifView = new GIFView(getApplicationContext()) {
            @Override
            public void initializeView() {
                super.initializeView();
                InputStream is = getContext().getResources().openRawResource(R.raw.animation_finalstage);
                mMovie = Movie.decodeStream(is);
            }
        };
        gifView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        parentView.addView(gifView);

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
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.animation_finalstage_1);

            //Audio starts playing
            mMediaPlayer.start();

            //An OnCompletionLister is created for the Media Player object that was
            //just created
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);

            //Toast.makeText(getApplicationContext(), mRockClicks, Toast.LENGTH_SHORT).show();
        }
    }
}
