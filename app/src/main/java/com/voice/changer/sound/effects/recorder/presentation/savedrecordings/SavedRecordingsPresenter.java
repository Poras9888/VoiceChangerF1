package com.voice.changer.sound.effects.recorder.presentation.savedrecordings;

import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;
import com.voice.changer.sound.effects.recorder.domain.usecase.GetRecordingsUseCase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SavedRecordingsPresenter implements SavedRecordingsContract.Presenter {
    private final SavedRecordingsContract.View view;
    private final GetRecordingsUseCase getRecordingsUseCase;
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    private List<RecordingEntity> current = new ArrayList<>();

    public SavedRecordingsPresenter(SavedRecordingsContract.View view, GetRecordingsUseCase getRecordingsUseCase) {
        this.view = view;
        this.getRecordingsUseCase = getRecordingsUseCase;
    }

    @Override
    public void onViewCreated() {
        io.execute(() -> {
            current = getRecordingsUseCase.executeSync();
            if (current == null || current.isEmpty()) {
                view.showEmptyState();
            } else {
                view.hideEmptyState();
                view.showRecordings(current);
            }
        });
    }

    @Override
    public void sortRecordings(String sortType) {
        if (current == null) {
            return;
        }
        List<RecordingEntity> sorted = new ArrayList<>(current);
        if ("duration".equals(sortType)) {
            sorted.sort(Comparator.comparingInt(r -> r.durationMs));
        } else if ("name".equals(sortType)) {
            sorted.sort(Comparator.comparing(r -> r.fileName == null ? "" : r.fileName));
        } else {
            sorted.sort((a, b) -> Long.compare(b.createdAt, a.createdAt));
        }
        view.showRecordings(sorted);
    }

    @Override
    public void onDestroy() {
        io.shutdownNow();
    }
}
