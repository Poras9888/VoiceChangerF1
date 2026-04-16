package com.voice.changer.sound.effects.recorder.data.repository;

import androidx.lifecycle.LiveData;

import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;

import java.util.List;

public interface RecordingRepository {
    LiveData<List<RecordingEntity>> getAllRecordings();
    List<RecordingEntity> getAllRecordingsSync();
    void insert(RecordingEntity recording);
    void delete(RecordingEntity recording);
    void deleteAll();
}
