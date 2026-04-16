package com.voice.changer.sound.effects.recorder.di;

import android.app.Application;

import com.voice.changer.sound.effects.recorder.data.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    public AppDatabase provideDatabase(Application app) {
        return AppDatabase.getInstance(app);
    }
}
