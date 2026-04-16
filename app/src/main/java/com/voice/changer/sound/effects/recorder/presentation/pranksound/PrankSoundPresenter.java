package com.voice.changer.sound.effects.recorder.presentation.pranksound;

public class PrankSoundPresenter implements PrankSoundContract.Presenter {
    private final PrankSoundContract.View view;
    private int volume = 60;
    private boolean loop;

    public PrankSoundPresenter(PrankSoundContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewCreated() {
    }

    @Override
    public void setVolume(int value) {
        volume = value;
    }

    @Override
    public void setLoopMode(boolean loop) {
        this.loop = loop;
    }

    @Override
    public void onDestroy() {
    }
}
