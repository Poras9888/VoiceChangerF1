package com.voice.changer.sound.effects.recorder.presentation.home;

import com.voice.changer.sound.effects.recorder.audio.effects.VoiceEffect;
import com.voice.changer.sound.effects.recorder.data.model.Feature;

import java.util.List;

public interface HomeContract {
    interface View {
        void showFeatureGrid(List<Feature> features);
        void showVoiceEffects(List<VoiceEffect> effects);
        void navigateToRecording();
        void navigateToPrankSound();
        void navigateToTextToAudio();
        void navigateToReverseVoice();
        void navigateToSwitchVoice();
        void showError(String message);
    }

    interface Presenter {
        void onViewCreated();
        void onFeatureCardClicked(String featureKey);
        void onEffectClicked(VoiceEffect effect);
        void onDestroy();
    }
}
