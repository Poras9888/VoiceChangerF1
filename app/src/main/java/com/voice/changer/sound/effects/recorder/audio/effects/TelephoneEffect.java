package com.voice.changer.sound.effects.recorder.audio.effects;

import java.io.IOException;

public class TelephoneEffect extends VoiceEffect {
    @Override public String getName() { return "Telephone"; }
    @Override public String getEmoji() { return "*"; }
    @Override public EffectCategory getCategory() { return EffectCategory.OTHER; }
    @Override public String process(String inputPath, String outputDir) throws IOException {
        return copyAs(inputPath, outputDir, "telephone");
    }
}
