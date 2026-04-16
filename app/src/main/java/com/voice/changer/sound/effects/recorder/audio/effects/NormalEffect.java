package com.voice.changer.sound.effects.recorder.audio.effects;

import java.io.IOException;

public class NormalEffect extends VoiceEffect {
    @Override public String getName() { return "Normal"; }
    @Override public String getEmoji() { return "O"; }
    @Override public EffectCategory getCategory() { return EffectCategory.ALL; }
    @Override public String process(String inputPath, String outputDir) throws IOException {
        return copyAs(inputPath, outputDir, "normal");
    }
}
