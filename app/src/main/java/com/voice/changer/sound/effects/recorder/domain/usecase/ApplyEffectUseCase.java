package com.voice.changer.sound.effects.recorder.domain.usecase;

import com.voice.changer.sound.effects.recorder.audio.AudioProcessor;
import com.voice.changer.sound.effects.recorder.audio.effects.VoiceEffect;

import java.io.IOException;

public class ApplyEffectUseCase {
    private final AudioProcessor audioProcessor;

    public ApplyEffectUseCase(AudioProcessor audioProcessor) {
        this.audioProcessor = audioProcessor;
    }

    public String execute(String inputPath, VoiceEffect effect, String outputDir) throws IOException {
        return audioProcessor.applyEffect(inputPath, effect, outputDir);
    }
}
