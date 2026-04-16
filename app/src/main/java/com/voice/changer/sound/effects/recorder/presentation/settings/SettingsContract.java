package com.voice.changer.sound.effects.recorder.presentation.settings;

public interface SettingsContract {
    interface View {
        void showError(String message);
    }

    interface Presenter {
        void onViewCreated();
        void onDestroy();
    }
}
