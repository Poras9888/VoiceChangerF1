package com.voice.changer.sound.effects.recorder.di;

import android.app.Application;

import com.voice.changer.sound.effects.recorder.core.ads.InterstitialAdManager;
import com.voice.changer.sound.effects.recorder.data.db.AppDatabase;
import com.voice.changer.sound.effects.recorder.data.repository.EffectRepository;
import com.voice.changer.sound.effects.recorder.data.repository.EffectRepositoryImpl;
import com.voice.changer.sound.effects.recorder.data.repository.RecordingRepository;
import com.voice.changer.sound.effects.recorder.data.repository.RecordingRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    public RecordingRepository provideRecordingRepository(AppDatabase db) {
        return new RecordingRepositoryImpl(db.recordingDao());
    }

    @Provides
    @Singleton
    public EffectRepository provideEffectRepository(AppDatabase db) {
        return new EffectRepositoryImpl(db.effectPresetDao());
    }

    @Provides
    @Singleton
    public InterstitialAdManager provideInterstitialAdManager() {
        return InterstitialAdManager.getInstance();
    }
}
