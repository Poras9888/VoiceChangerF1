package com.voice.changer.sound.effects.recorder.presentation.recording;

public interface RecordingContract {
    interface View {
        void updateTimer(String text);
        void updateAmplitude(float normalized);
        void onRecordingSaved(String recordingPath);
        void showError(String message);
    }

    interface Presenter {
        void onViewCreated();
        void onStopClicked();
        void onSaveClicked();
        void onDestroy();
    }
}
