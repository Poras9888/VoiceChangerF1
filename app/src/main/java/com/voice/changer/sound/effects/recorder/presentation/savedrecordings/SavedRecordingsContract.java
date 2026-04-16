package com.voice.changer.sound.effects.recorder.presentation.savedrecordings;

import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;

import java.util.List;

public interface SavedRecordingsContract {
    interface View {
        void showRecordings(List<RecordingEntity> recordings);
        void showEmptyState();
        void hideEmptyState();
        void showError(String message);
    }

    interface Presenter {
        void onViewCreated();
        void sortRecordings(String sortType);
        void onDestroy();
    }
}
