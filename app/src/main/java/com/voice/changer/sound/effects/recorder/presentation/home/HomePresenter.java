package com.voice.changer.sound.effects.recorder.presentation.home;

import com.voice.changer.sound.effects.recorder.audio.effects.EffectFactory;
import com.voice.changer.sound.effects.recorder.audio.effects.VoiceEffect;
import com.voice.changer.sound.effects.recorder.data.model.Feature;

import java.util.Arrays;

public class HomePresenter implements HomeContract.Presenter {
    private final HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewCreated() {
        view.showFeatureGrid(Arrays.asList(
                new Feature("record", "Record & Change"),
                new Feature("tts", "Text To Audio"),
                new Feature("reverse", "Reverse Voice"),
                new Feature("switch", "Switch Voice")
        ));
        view.showVoiceEffects(EffectFactory.createAll());
    }

    @Override
    public void onFeatureCardClicked(String featureKey) {
        switch (featureKey) {
            case "record":
                view.navigateToRecording();
                break;
            case "prank":
                view.navigateToPrankSound();
                break;
            case "tts":
                view.navigateToTextToAudio();
                break;
            case "reverse":
                view.navigateToReverseVoice();
                break;
            case "switch":
                view.navigateToSwitchVoice();
                break;
            default:
                view.showError("Unknown feature: " + featureKey);
        }
    }

    @Override
    public void onEffectClicked(VoiceEffect effect) {
        view.navigateToRecording();
    }

    @Override
    public void onDestroy() {
        // No-op for now.
    }
}
