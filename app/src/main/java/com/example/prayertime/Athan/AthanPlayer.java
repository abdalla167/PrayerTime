package com.example.prayertime.Athan;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

public class AthanPlayer {

    private static AthanPlayer sInstance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public AthanPlayer(Context context) {
        mContext = context;
    }

    public static AthanPlayer getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AthanPlayer(context);
        }
        return sInstance;
    }

    public void playAthan(Uri uri) {
        stopAthan();
       if( ! isSilentMode( mContext)){
        mMediaPlayer = MediaPlayer.create(mContext, uri);
        mMediaPlayer.start();
       }
    }

    public void stopAthan() {
        if(mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.seekTo(0);
        }
    }
    public static boolean isSilentMode(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int ringerMode = audioManager.getRingerMode();

        return ringerMode == AudioManager.RINGER_MODE_SILENT
                || ringerMode == AudioManager.RINGER_MODE_VIBRATE;
    }

}
