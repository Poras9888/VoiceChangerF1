package com.voice.changer.sound.effects.recorder.audio.effects;

public enum EffectCategory {
    ALL,
    SCARY,
    OTHER;

    public String getDisplayName() {
        switch (this) {
            case SCARY:
                return "Scary Effects";
            case OTHER:
                return "Other Effects";
            case ALL:
            default:
                return "All Effects";
        }
    }
}
