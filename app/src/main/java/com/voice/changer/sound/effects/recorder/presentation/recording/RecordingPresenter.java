package com.voice.changer.sound.effects.recorder.presentation.recording;

import com.voice.changer.sound.effects.recorder.audio.AudioRecorderManager;
import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;
import com.voice.changer.sound.effects.recorder.domain.usecase.SaveRecordingUseCase;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecordingPresenter implements RecordingContract.Presenter {
    private final RecordingContract.View view;
    private final AudioRecorderManager recorderManager;
    private final SaveRecordingUseCase saveRecordingUseCase;
    private final File outputDir;
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    private long startedAt;
    private String lastRecordingPath;

    public RecordingPresenter(RecordingContract.View view,
                              AudioRecorderManager recorderManager,
                              SaveRecordingUseCase saveRecordingUseCase,
                              File outputDir) {
        this.view = view;
        this.recorderManager = recorderManager;
        this.saveRecordingUseCase = saveRecordingUseCase;
        this.outputDir = outputDir;
    }

    @Override
    public void onViewCreated() {
        startedAt = System.currentTimeMillis();
        recorderManager.startRecording(outputDir, amplitude -> view.updateAmplitude(amplitude));
    }

    @Override
    public void onStopClicked() {
        if (recorderManager.isRecording()) {
            lastRecordingPath = recorderManager.stopRecording();
        }
    }

    @Override
    public void onSaveClicked() {
        if (lastRecordingPath == null && recorderManager.isRecording()) {
            lastRecordingPath = recorderManager.stopRecording();
        }
        if (lastRecordingPath == null) {
            view.showError("No recording available");
            return;
        }
        String finalPath = lastRecordingPath;
        io.execute(() -> {
            RecordingEntity entity = new RecordingEntity();
            entity.id = UUID.randomUUID().toString();
            entity.filePath = finalPath;
            entity.fileName = new File(finalPath).getName();
            entity.createdAt = System.currentTimeMillis();
            entity.durationMs = (int) (entity.createdAt - startedAt);
            entity.effectName = "Normal";
            entity.isFavorite = false;
            saveRecordingUseCase.execute(entity);
            view.onRecordingSaved(finalPath);
        });
    }

    @Override
    public void onDestroy() {
        if (recorderManager.isRecording()) {
            recorderManager.stopRecording();
        }
        io.shutdownNow();
    }
}
