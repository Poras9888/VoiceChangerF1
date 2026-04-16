package com.voice.changer.sound.effects.recorder.presentation.pranksound;

public interface PrankSoundContract {
    interface View {
        void showError(String message);
    }

    interface Presenter {
        void onViewCreated();
        void setVolume(int value);
        void setLoopMode(boolean loop);
        void onDestroy();
    }
}
