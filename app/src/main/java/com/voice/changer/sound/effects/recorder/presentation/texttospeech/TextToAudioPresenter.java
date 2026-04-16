package com.voice.changer.sound.effects.recorder.presentation.texttospeech;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;
import com.voice.changer.sound.effects.recorder.domain.usecase.SaveRecordingUseCase;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TextToAudioPresenter implements TextToAudioContract.Presenter {
    private final TextToAudioContract.View view;
    private final SaveRecordingUseCase saveRecordingUseCase;
    private final File outputDir;
    private final ExecutorService io = Executors.newSingleThreadExecutor();
    private final Map<String, String> utteranceToPath = new ConcurrentHashMap<>();

    private TextToSpeech textToSpeech;
    private volatile boolean ttsReady;

    private String generatedPath;

    public TextToAudioPresenter(TextToAudioContract.View view,
                                SaveRecordingUseCase saveRecordingUseCase,
                                Context appContext,
                                File outputDir) {
        this.view = view;
        this.saveRecordingUseCase = saveRecordingUseCase;
        this.outputDir = outputDir;

        textToSpeech = new TextToSpeech(appContext.getApplicationContext(), status -> {
            if (status != TextToSpeech.SUCCESS) {
                view.showError("TTS initialization failed");
                return;
            }

            int languageResult = textToSpeech.setLanguage(Locale.getDefault());
            if (languageResult == TextToSpeech.LANG_MISSING_DATA
                    || languageResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                view.showError("Current language is not supported for TTS");
                return;
            }

            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                }

                @Override
                public void onDone(String utteranceId) {
                    String path = utteranceToPath.remove(utteranceId);
                    if (path != null) {
                        generatedPath = path;
                        view.onConverted(path);
                    }
                }

                @Override
                public void onError(String utteranceId) {
                    utteranceToPath.remove(utteranceId);
                    view.showError("TTS synthesis failed");
                }
            });
            ttsReady = true;
        });
    }

    @Override
    public void convertToSpeech(String text, float speed, float pitch) {
        if (text == null || text.trim().isEmpty()) {
            view.showError("Type text first");
            return;
        }
        if (!ttsReady || textToSpeech == null) {
            view.showError("TTS is still initializing");
            return;
        }

        if (!outputDir.exists() && !outputDir.mkdirs()) {
            view.showError("Cannot create output directory");
            return;
        }

        String utteranceId = "tts_" + System.currentTimeMillis();
        File out = new File(outputDir, utteranceId + ".wav");

        float clampedSpeed = Math.max(0.5f, Math.min(2.0f, speed));
        float pitchMultiplier = (float) Math.pow(2.0, pitch / 12.0);
        float clampedPitch = Math.max(0.5f, Math.min(2.0f, pitchMultiplier));

        textToSpeech.setSpeechRate(clampedSpeed);
        textToSpeech.setPitch(clampedPitch);

        utteranceToPath.put(utteranceId, out.getAbsolutePath());
        int result = textToSpeech.synthesizeToFile(text, new Bundle(), out, utteranceId);
        if (result != TextToSpeech.SUCCESS) {
            utteranceToPath.remove(utteranceId);
            view.showError("TTS synthesis request failed");
        }
    }

    @Override
    public void saveToLibrary() {
        if (generatedPath == null) {
            view.showError("Convert text first");
            return;
        }
        io.execute(() -> {
            RecordingEntity entity = new RecordingEntity();
            entity.id = UUID.randomUUID().toString();
            entity.filePath = generatedPath;
            entity.fileName = new File(generatedPath).getName();
            entity.effectName = "TTS";
            entity.durationMs = 0;
            entity.createdAt = System.currentTimeMillis();
            entity.isFavorite = false;
            saveRecordingUseCase.execute(entity);
            view.onConverted(generatedPath);
        });
    }

    @Override
    public void onDestroy() {
        io.shutdownNow();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }
}
