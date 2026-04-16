package com.voice.changer.sound.effects.recorder.presentation.reversevoice;

public interface ReverseVoiceContract {
    interface View {
        void showError(String message);
    }

    interface Presenter {
        void onViewCreated();
        void onDestroy();
    }
}
