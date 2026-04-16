package com.voice.changer.sound.effects.recorder.domain.usecase;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class ConvertTextToSpeechUseCase {
    public TextToSpeech execute(Context context, TextToSpeech.OnInitListener onInitListener, Locale locale) {
        TextToSpeech tts = new TextToSpeech(context, status -> {
            onInitListener.onInit(status);
            if (status == TextToSpeech.SUCCESS) {
                ttsLanguageSafe(tts, locale);
            }
        });
        return tts;
    }

    private void ttsLanguageSafe(TextToSpeech tts, Locale locale) {
        if (tts != null && locale != null) {
            tts.setLanguage(locale);
        }
    }
}
