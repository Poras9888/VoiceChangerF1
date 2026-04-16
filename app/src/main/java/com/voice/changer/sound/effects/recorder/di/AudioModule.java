package com.voice.changer.sound.effects.recorder.di;

import com.voice.changer.sound.effects.recorder.audio.AudioPlayerManager;
import com.voice.changer.sound.effects.recorder.audio.AudioProcessor;
import com.voice.changer.sound.effects.recorder.audio.AudioRecorderManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AudioModule {
    @Provides @Singleton
    public AudioRecorderManager provideRecorderManager() {
        return new AudioRecorderManager();
    }

    @Provides @Singleton
    public AudioPlayerManager providePlayerManager() {
        return new AudioPlayerManager();
    }

    @Provides @Singleton
    public AudioProcessor provideAudioProcessor() {
        return new AudioProcessor();
    }
}
