package com.voice.changer.sound.effects.recorder.audio;

public final class WaveformAnalyzer {
    private WaveformAnalyzer() {
    }

    public static float calculateAmplitude(byte[] data, int read) {
        if (data == null || read <= 0) {
            return 0f;
        }
        long sum = 0L;
        for (int i = 0; i + 1 < read; i += 2) {
            short sample = (short) ((data[i + 1] << 8) | (data[i] & 0xff));
            sum += Math.abs(sample);
        }
        float avg = sum / (read / 2f);
        return Math.min(1f, avg / Short.MAX_VALUE);
    }
}
