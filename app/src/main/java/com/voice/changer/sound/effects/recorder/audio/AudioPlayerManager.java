package com.voice.changer.sound.effects.recorder.audio;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayerManager {
    private MediaPlayer mediaPlayer;

    public void play(Context context, String path, boolean loop) {
        stop();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setLooping(loop);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            stop();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
