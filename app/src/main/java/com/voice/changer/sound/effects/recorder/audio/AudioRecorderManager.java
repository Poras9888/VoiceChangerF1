package com.voice.changer.sound.effects.recorder.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.HandlerThread;

import com.voice.changer.sound.effects.recorder.core.constants.AppConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioRecorderManager {
    private static final int SAMPLE_RATE = AppConstants.SAMPLE_RATE;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord audioRecord;
    private HandlerThread recordingThread;
    private Handler recordingHandler;
    private volatile boolean isRecording;
    private File outputFile;

    public interface AmplitudeCallback {
        void onAmplitude(float normalizedAmplitude);
    }

    public void startRecording(File outputDir, AmplitudeCallback callback) {
        int minBuffer = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
        if (minBuffer <= 0) {
            throw new IllegalStateException("Invalid audio buffer size");
        }

        recordingThread = new HandlerThread("audio-recorder-thread");
        recordingThread.start();
        recordingHandler = new Handler(recordingThread.getLooper());

        outputFile = new File(outputDir, "recording_" + System.currentTimeMillis() + ".pcm");
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, minBuffer);
        audioRecord.startRecording();
        isRecording = true;

        recordingHandler.post(() -> {
            byte[] buffer = new byte[minBuffer];
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                while (isRecording) {
                    int read = audioRecord.read(buffer, 0, buffer.length);
                    if (read > 0) {
                        fos.write(buffer, 0, read);
                        if (callback != null) {
                            callback.onAmplitude(WaveformAnalyzer.calculateAmplitude(buffer, read));
                        }
                    }
                }
                fos.flush();
            } catch (IOException ignored) {
            }
        });
    }

    public String stopRecording() {
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
        if (recordingThread != null) {
            recordingThread.quitSafely();
            recordingThread = null;
        }
        return outputFile != null ? outputFile.getAbsolutePath() : null;
    }

    public boolean isRecording() {
        return isRecording;
    }
}
