package com.voice.changer.sound.effects.recorder.audio.effects;

import java.io.IOException;

public class GhostEffect extends VoiceEffect {
    @Override public String getName() { return "Ghost"; }
    @Override public String getEmoji() { return "*"; }
    @Override public EffectCategory getCategory() { return EffectCategory.SCARY; }
    @Override public String process(String inputPath, String outputDir) throws IOException {
        return copyAs(inputPath, outputDir, "ghost");
    }
}
