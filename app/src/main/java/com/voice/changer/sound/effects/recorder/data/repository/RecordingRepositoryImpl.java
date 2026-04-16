package com.voice.changer.sound.effects.recorder.data.repository;

import androidx.lifecycle.LiveData;

import com.voice.changer.sound.effects.recorder.data.db.dao.RecordingDao;
import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;

import java.util.List;

public class RecordingRepositoryImpl implements RecordingRepository {
    private final RecordingDao recordingDao;

    public RecordingRepositoryImpl(RecordingDao recordingDao) {
        this.recordingDao = recordingDao;
    }

    @Override
    public LiveData<List<RecordingEntity>> getAllRecordings() {
        return recordingDao.getAllRecordings();
    }

    @Override
    public List<RecordingEntity> getAllRecordingsSync() {
        return recordingDao.getAllRecordingsSync();
    }

    @Override
    public void insert(RecordingEntity recording) {
        recordingDao.insert(recording);
    }

    @Override
    public void delete(RecordingEntity recording) {
        recordingDao.delete(recording);
    }

    @Override
    public void deleteAll() {
        recordingDao.deleteAll();
    }
}
