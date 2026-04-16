package com.voice.changer.sound.effects.recorder.data.repository;

import com.voice.changer.sound.effects.recorder.data.db.entity.EffectPresetEntity;

import java.util.List;

public interface EffectRepository {
    List<EffectPresetEntity> getAllPresets();
    void saveAll(List<EffectPresetEntity> presets);
}
