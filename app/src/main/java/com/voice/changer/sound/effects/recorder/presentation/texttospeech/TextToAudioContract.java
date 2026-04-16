package com.voice.changer.sound.effects.recorder.presentation.texttospeech;

public interface TextToAudioContract {
    interface View {
        void onConverted(String outputPath);
        void showError(String message);
    }

    interface Presenter {
        void convertToSpeech(String text, float speed, float pitch);
        void saveToLibrary();
        void onDestroy();
    }
}
