package com.voice.changer.sound.effects.recorder.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recordings")
public class RecordingEntity {
    @PrimaryKey
    @NonNull
    public String id;
    public String fileName;
    public String filePath;
    public int durationMs;
    public String effectName;
    public long createdAt;
    public boolean isFavorite;
}
