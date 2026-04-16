package com.voice.changer.sound.effects.recorder.presentation.savedrecordings;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.voice.changer.sound.effects.recorder.R;
import com.voice.changer.sound.effects.recorder.VoiceChangerApp;
import com.voice.changer.sound.effects.recorder.core.ads.BannerAdHelper;
import com.voice.changer.sound.effects.recorder.data.db.entity.RecordingEntity;
import com.voice.changer.sound.effects.recorder.data.repository.RecordingRepository;
import com.voice.changer.sound.effects.recorder.domain.usecase.GetRecordingsUseCase;

import java.util.List;

import javax.inject.Inject;

public class SavedRecordingsActivity extends AppCompatActivity implements SavedRecordingsContract.View {
    @Inject RecordingRepository recordingRepository;

    private SavedRecordingsContract.Presenter presenter;
    private RecordingAdapter adapter;
    private LinearLayout layoutEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recordings);
        ((VoiceChangerApp) getApplication()).getAppComponent().inject(this);

        RecyclerView recyclerView = findViewById(R.id.rvSaved);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        Toolbar toolbar = findViewById(R.id.toolbarSaved);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        adapter = new RecordingAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter = new SavedRecordingsPresenter(this, new GetRecordingsUseCase(recordingRepository));
        presenter.onViewCreated();

        BannerAdHelper.loadBanner(this, findViewById(R.id.adContainer));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showRecordings(List<RecordingEntity> recordings) {
        runOnUiThread(() -> adapter.submit(recordings));
    }

    @Override
    public void showEmptyState() {
        runOnUiThread(() -> layoutEmpty.setVisibility(android.view.View.VISIBLE));
    }

    @Override
    public void hideEmptyState() {
        runOnUiThread(() -> layoutEmpty.setVisibility(android.view.View.GONE));
    }

    @Override
    public void showError(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_saved_recordings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.action_sort_duration) {
            presenter.sortRecordings("duration");
            return true;
        }
        if (id == R.id.action_sort_name) {
            presenter.sortRecordings("name");
            return true;
        }
        if (id == R.id.action_sort_date) {
            presenter.sortRecordings("date");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
