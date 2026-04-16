package com.voice.changer.sound.effects.recorder.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.voice.changer.sound.effects.recorder.data.db.entity.EffectPresetEntity;

import java.util.List;

@Dao
public interface EffectPresetDao {
    @Query("SELECT * FROM effect_presets")
    List<EffectPresetEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EffectPresetEntity> presets);
}
