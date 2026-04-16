package com.voice.changer.sound.effects.recorder.domain.usecase;

import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;
import com.voice.changer.sound.effects.recorder.data.repository.RecordingRepository;

public class SaveRecordingUseCase {
    private final RecordingRepository recordingRepository;

    public SaveRecordingUseCase(RecordingRepository recordingRepository) {
        this.recordingRepository = recordingRepository;
    }

    public void execute(RecordingEntity recording) {
        recordingRepository.insert(recording);
    }
}
