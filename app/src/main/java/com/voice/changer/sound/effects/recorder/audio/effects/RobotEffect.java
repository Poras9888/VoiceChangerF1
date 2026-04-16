package com.voice.changer.sound.effects.recorder.audio.effects;

import java.io.IOException;

public class RobotEffect extends VoiceEffect {
    @Override public String getName() { return "Robot"; }
    @Override public String getEmoji() { return "*"; }
    @Override public EffectCategory getCategory() { return EffectCategory.ALL; }
    @Override public String process(String inputPath, String outputDir) throws IOException {
        return copyAs(inputPath, outputDir, "robot");
    }
}
