package com.voice.changer.sound.effects.recorder;

import android.app.Application;

import com.voice.changer.sound.effects.recorder.di.AppComponent;
import com.voice.changer.sound.effects.recorder.di.AppModule;
import com.voice.changer.sound.effects.recorder.di.AudioModule;
import com.voice.changer.sound.effects.recorder.di.DaggerAppComponent;
import com.voice.changer.sound.effects.recorder.di.DatabaseModule;

public class VoiceChangerApp extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .databaseModule(new DatabaseModule())
                .audioModule(new AudioModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
