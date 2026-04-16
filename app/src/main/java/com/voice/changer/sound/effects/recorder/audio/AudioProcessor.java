package com.voice.changer.sound.effects.recorder.audio;

import com.voice.changer.sound.effects.recorder.audio.effects.VoiceEffect;

import java.io.File;
import java.io.IOException;

public class AudioProcessor {
    private static final float TWO_PI = (float) (Math.PI * 2.0);

    public String applyEffect(String inputPath, VoiceEffect effect, String outputDir) throws IOException {
        File outDir = new File(outputDir);
        if (!outDir.exists() && !outDir.mkdirs()) {
            throw new IOException("Unable to create output directory");
        }

        String name = effect.getName();
        short[] pcm = PcmIo.readPcm16Mono(inputPath);

        float pitch = 0f;
        float tempo = 1f;
        float reverb = 0f;
        float distortion = 0f;
        float ringModHz = 0f;
        float ringMix = 0f;
        int lowPassHz = 0;
        boolean reverse = false;

        if ("Robot".equals(name)) {
            ringModHz = 50f;
            ringMix = 0.8f;
        } else if ("Monster".equals(name)) {
            pitch = -8f;
            distortion = 0.3f;
        } else if ("Cave".equals(name)) {
            reverb = 0.6f;
        } else if ("Alien".equals(name)) {
            pitch = 4f;
            reverb = 0.2f;
        } else if ("Nervous".equals(name)) {
            pitch = 1f;
            ringModHz = 8f;
            ringMix = 0.25f;
        } else if ("Death".equals(name)) {
            lowPassHz = 1200;
            reverb = 0.25f;
        } else if ("Drunk".equals(name)) {
            pitch = -2f;
            reverb = 0.2f;
        } else if ("Underwater".equals(name)) {
            lowPassHz = 800;
            ringModHz = 2f;
            ringMix = 0.3f;
        } else if ("BigRobot".equals(name)) {
            ringModHz = 50f;
            ringMix = 0.6f;
            distortion = 0.15f;
        } else if ("Zombie".equals(name)) {
            pitch = -5f;
            distortion = 0.4f;
            reverb = 0.2f;
        } else if ("Hexafluoride".equals(name)) {
            pitch = 8f;
            tempo = 1.3f;
        } else if ("SmallAlien".equals(name)) {
            pitch = 12f;
        } else if ("Telephone".equals(name)) {
            lowPassHz = 3400;
            distortion = 0.1f;
        } else if ("Helium".equals(name)) {
            pitch = 10f;
        } else if ("Giant".equals(name)) {
            pitch = -12f;
            tempo = 0.8f;
        } else if ("Chipmunk".equals(name)) {
            pitch = 8f;
            tempo = 2.0f;
        } else if ("Ghost".equals(name)) {
            reverb = 0.7f;
            ringModHz = 5f;
            ringMix = 0.2f;
        } else if ("DarthVader".equals(name)) {
            pitch = -10f;
            distortion = 0.2f;
        } else if ("Reverse".equals(name)) {
            reverse = true;
        }

        if (pitch != 0f || tempo != 1f) {
            pcm = SoundTouchBridge.process(pcm, pitch, tempo);
        }

        if (ringModHz > 0f) {
            applyRingMod(pcm, ringModHz, ringMix);
        }
        if (lowPassHz > 0) {
            applyLowPass(pcm, lowPassHz, 44100);
        }
        if (distortion > 0f) {
            applySoftClip(pcm, distortion);
        }
        if (reverb > 0f) {
            applySimpleReverb(pcm, reverb, 44100);
        }
        if (reverse) {
            reverse(pcm);
        }

        String outName = new File(inputPath).getName().replace(".pcm", "") + "_" + name.toLowerCase() + ".pcm";
        return PcmIo.writePcm16Mono(pcm, new File(outDir, outName).getAbsolutePath());
    }

    private void applyRingMod(short[] pcm, float hz, float mix) {
        float phase = 0f;
        float delta = TWO_PI * hz / 44100f;
        for (int i = 0; i < pcm.length; i++) {
            float dry = pcm[i] / 32768f;
            float mod = dry * (float) Math.sin(phase);
            float wet = dry * (1f - mix) + mod * mix;
            pcm[i] = clamp(wet);
            phase += delta;
            if (phase > TWO_PI) {
                phase -= TWO_PI;
            }
        }
    }

    private void applySoftClip(short[] pcm, float amount) {
        float gain = 1f + amount * 4f;
        for (int i = 0; i < pcm.length; i++) {
            float x = (pcm[i] / 32768f) * gain;
            float y = x / (1f + Math.abs(x));
            pcm[i] = clamp(y);
        }
    }

    private void applyLowPass(short[] pcm, int cutoffHz, int sampleRate) {
        float rc = 1f / (2f * (float) Math.PI * cutoffHz);
        float dt = 1f / sampleRate;
        float alpha = dt / (rc + dt);
        float y = 0f;
        for (int i = 0; i < pcm.length; i++) {
            float x = pcm[i] / 32768f;
            y = y + alpha * (x - y);
            pcm[i] = clamp(y);
        }
    }

    private void applySimpleReverb(short[] pcm, float amount, int sampleRate) {
        int delay = (int) (0.12f * sampleRate);
        if (delay <= 0 || delay >= pcm.length) {
            return;
        }
        float feedback = 0.35f + amount * 0.35f;
        for (int i = delay; i < pcm.length; i++) {
            float dry = pcm[i] / 32768f;
            float delayed = pcm[i - delay] / 32768f;
            pcm[i] = clamp(dry + delayed * feedback);
        }
    }

    private void reverse(short[] pcm) {
        int l = 0;
        int r = pcm.length - 1;
        while (l < r) {
            short tmp = pcm[l];
            pcm[l] = pcm[r];
            pcm[r] = tmp;
            l++;
            r--;
        }
    }

    private short clamp(float value) {
        float v = Math.max(-1f, Math.min(1f, value));
        return (short) (v * 32767f);
    }
}
