package com.voice.changer.sound.effects.recorder.audio.effects;

import java.io.IOException;

public class BigRobotEffect extends VoiceEffect {
    @Override public String getName() { return "BigRobot"; }
    @Override public String getEmoji() { return "*"; }
    @Override public EffectCategory getCategory() { return EffectCategory.ALL; }
    @Override public String process(String inputPath, String outputDir) throws IOException {
        return copyAs(inputPath, outputDir, "bigrobot");
    }
}
