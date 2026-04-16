package com.voice.changer.sound.effects.recorder.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.voice.changer.sound.effects.recorder.data.db.dao.EffectPresetDao;
import com.voice.changer.sound.effects.recorder.data.db.dao.RecordingDao;
import com.voice.changer.sound.effects.recorder.data.db.entity.EffectPresetEntity;
import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;

@Database(entities = {RecordingEntity.class, EffectPresetEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract RecordingDao recordingDao();
    public abstract EffectPresetDao effectPresetDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "voice_changer_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
