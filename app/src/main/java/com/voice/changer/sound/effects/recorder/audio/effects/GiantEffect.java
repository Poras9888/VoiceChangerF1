package com.voice.changer.sound.effects.recorder.audio.effects;

import java.io.IOException;

public class GiantEffect extends VoiceEffect {
    @Override public String getName() { return "Giant"; }
    @Override public String getEmoji() { return "*"; }
    @Override public EffectCategory getCategory() { return EffectCategory.SCARY; }
    @Override public String process(String inputPath, String outputDir) throws IOException {
        return copyAs(inputPath, outputDir, "giant");
    }
}
