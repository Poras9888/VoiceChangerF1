package com.voice.changer.sound.effects.recorder.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class PcmIo {
    private PcmIo() {
    }

    public static short[] readPcm16Mono(String inputPath) throws IOException {
        File file = new File(inputPath);
        byte[] bytes = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            int offset = 0;
            while (offset < bytes.length) {
                int read = fis.read(bytes, offset, bytes.length - offset);
                if (read < 0) {
                    break;
                }
                offset += read;
            }
        }

        int samples = bytes.length / 2;
        short[] pcm = new short[samples];
        for (int i = 0; i < samples; i++) {
            int lo = bytes[i * 2] & 0xff;
            int hi = bytes[i * 2 + 1] << 8;
            pcm[i] = (short) (hi | lo);
        }
        return pcm;
    }

    public static String writePcm16Mono(short[] pcm, String outputPath) throws IOException {
        byte[] bytes = new byte[pcm.length * 2];
        for (int i = 0; i < pcm.length; i++) {
            short s = pcm[i];
            bytes[i * 2] = (byte) (s & 0xff);
            bytes[i * 2 + 1] = (byte) ((s >> 8) & 0xff);
        }
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(bytes);
            fos.flush();
        }
        return outputPath;
    }
}
