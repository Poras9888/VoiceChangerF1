package com.voice.changer.sound.effects.recorder.audio;

public final class SoundTouchBridge {
    private static boolean loaded;

    static {
        try {
            System.loadLibrary("soundtouch_jni");
            loaded = true;
        } catch (UnsatisfiedLinkError error) {
            loaded = false;
        }
    }

    private SoundTouchBridge() {
    }

    public static short[] process(short[] pcm, float pitchSemiTones, float tempo) {
        if (!loaded || pcm == null || pcm.length == 0) {
            return pcm;
        }
        return nativeProcess(pcm, pitchSemiTones, tempo);
    }

    private static native short[] nativeProcess(short[] pcm, float pitchSemiTones, float tempo);
}
