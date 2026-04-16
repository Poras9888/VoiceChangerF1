package com.voice.changer.sound.effects.recorder.presentation.switchvoice;

public class SwitchVoicePresenter implements SwitchVoiceContract.Presenter {
    private final SwitchVoiceContract.View view;

    public SwitchVoicePresenter(SwitchVoiceContract.View view) {
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
