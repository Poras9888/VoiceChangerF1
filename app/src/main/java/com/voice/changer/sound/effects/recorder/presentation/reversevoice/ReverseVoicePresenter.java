package com.voice.changer.sound.effects.recorder.presentation.reversevoice;

public class ReverseVoicePresenter implements ReverseVoiceContract.Presenter {
    private final ReverseVoiceContract.View view;

    public ReverseVoicePresenter(ReverseVoiceContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewCreated() {
        // View initialization point.
    }

    @Override
    public void onDestroy() {
        // Cleanup point.
    }
}
