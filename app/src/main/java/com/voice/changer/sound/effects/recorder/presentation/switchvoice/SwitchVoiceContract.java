package com.voice.changer.sound.effects.recorder.presentation.switchvoice;

public interface SwitchVoiceContract {
    interface View {
        void showError(String message);
    }

    interface Presenter {
        void onViewCreated();
        void onDestroy();
    }
}
