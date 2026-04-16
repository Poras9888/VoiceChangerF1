package com.voice.changer.sound.effects.recorder.di;

import com.voice.changer.sound.effects.recorder.presentation.addeffect.AddEffectActivity;
import com.voice.changer.sound.effects.recorder.presentation.home.HomeActivity;
import com.voice.changer.sound.effects.recorder.presentation.pranksound.PrankSoundActivity;
import com.voice.changer.sound.effects.recorder.presentation.recording.RecordingActivity;
import com.voice.changer.sound.effects.recorder.presentation.savedrecordings.SavedRecordingsActivity;
import com.voice.changer.sound.effects.recorder.presentation.settings.SettingsActivity;
import com.voice.changer.sound.effects.recorder.presentation.texttospeech.TextToAudioActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class, AudioModule.class})
public interface AppComponent {
    void inject(HomeActivity activity);
    void inject(RecordingActivity activity);
    void inject(AddEffectActivity activity);
    void inject(PrankSoundActivity activity);
    void inject(TextToAudioActivity activity);
    void inject(SavedRecordingsActivity activity);
    void inject(SettingsActivity activity);
}
