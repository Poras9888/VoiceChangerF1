package com.voice.changer.sound.effects.recorder.audio.effects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public abstract class VoiceEffect {
    public abstract String getName();
    public abstract String getEmoji();
    public abstract EffectCategory getCategory();

    public abstract String process(String inputPath, String outputDir) throws IOException;

    protected String copyAs(String inputPath, String outputDir, String suffix) throws IOException {
        File in = new File(inputPath);
        String outName = in.getName().replace(".pcm", "") + "_" + suffix + ".pcm";
        File out = new File(outputDir, outName);
        Files.copy(in.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return out.getAbsolutePath();
    }
}
