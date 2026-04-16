package com.voice.changer.sound.effects.recorder.audio.effects;

import java.io.IOException;

public class AlienEffect extends VoiceEffect {
    @Override public String getName() { return "Alien"; }
    @Override public String getEmoji() { return "*"; }
    @Override public EffectCategory getCategory() { return EffectCategory.OTHER; }
    @Override public String process(String inputPath, String outputDir) throws IOException {
        return copyAs(inputPath, outputDir, "alien");
    }
}
