package com.voice.changer.sound.effects.recorder.data.repository;

import com.voice.changer.sound.effects.recorder.data.db.dao.EffectPresetDao;
import com.voice.changer.sound.effects.recorder.data.db.entity.EffectPresetEntity;

import java.util.List;

public class EffectRepositoryImpl implements EffectRepository {
    private final EffectPresetDao effectPresetDao;

    public EffectRepositoryImpl(EffectPresetDao effectPresetDao) {
        this.effectPresetDao = effectPresetDao;
    }

    @Override
    public List<EffectPresetEntity> getAllPresets() {
        return effectPresetDao.getAll();
    }

    @Override
    public void saveAll(List<EffectPresetEntity> presets) {
        effectPresetDao.insertAll(presets);
    }
}
