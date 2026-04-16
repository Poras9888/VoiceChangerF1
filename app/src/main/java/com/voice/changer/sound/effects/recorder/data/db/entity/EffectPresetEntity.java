package com.voice.changer.sound.effects.recorder.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "effect_presets")
public class EffectPresetEntity {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String category;
    public float pitchShift;
    public float speed;
    public float reverb;
    public float distortion;
    public boolean isUnlocked;
}
