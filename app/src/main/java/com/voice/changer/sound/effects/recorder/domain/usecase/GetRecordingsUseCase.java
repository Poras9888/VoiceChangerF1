package com.voice.changer.sound.effects.recorder.domain.usecase;

import androidx.lifecycle.LiveData;

import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;
import com.voice.changer.sound.effects.recorder.data.repository.RecordingRepository;

import java.util.List;

public class GetRecordingsUseCase {
    private final RecordingRepository recordingRepository;

    public GetRecordingsUseCase(RecordingRepository recordingRepository) {
        this.recordingRepository = recordingRepository;
    }

    public LiveData<List<RecordingEntity>> execute() {
        return recordingRepository.getAllRecordings();
    }

    public List<RecordingEntity> executeSync() {
        return recordingRepository.getAllRecordingsSync();
    }
}
