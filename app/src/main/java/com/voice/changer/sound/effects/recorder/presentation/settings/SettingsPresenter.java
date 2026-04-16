package com.voice.changer.sound.effects.recorder.presentation.settings;

public class SettingsPresenter implements SettingsContract.Presenter {
    private final SettingsContract.View view;

    public SettingsPresenter(SettingsContract.View view) {
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
