package com.voice.changer.sound.effects.recorder.audio.effects;

import java.io.IOException;

public class ZombieEffect extends VoiceEffect {
    @Override public String getName() { return "Zombie"; }
    @Override public String getEmoji() { return "*"; }
    @Override public EffectCategory getCategory() { return EffectCategory.SCARY; }
    @Override public String process(String inputPath, String outputDir) throws IOException {
        return copyAs(inputPath, outputDir, "zombie");
    }
}
