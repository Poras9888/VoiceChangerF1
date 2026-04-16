package com.voice.changer.sound.effects.recorder.presentation.recording;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.voice.changer.sound.effects.recorder.R;
import com.voice.changer.sound.effects.recorder.VoiceChangerApp;
import com.voice.changer.sound.effects.recorder.audio.AudioRecorderManager;
import com.voice.changer.sound.effects.recorder.core.ads.InterstitialAdManager;
import com.voice.changer.sound.effects.recorder.data.repository.RecordingRepository;
import com.voice.changer.sound.effects.recorder.domain.usecase.SaveRecordingUseCase;
import com.voice.changer.sound.effects.recorder.presentation.addeffect.AddEffectActivity;
import com.voice.changer.sound.effects.recorder.ui.custom.WaveformView;

import java.io.File;
import java.util.Locale;

import javax.inject.Inject;

public class RecordingActivity extends AppCompatActivity implements RecordingContract.View {
    public static final String EXTRA_AUDIO_PATH = "audio_path";

    @Inject AudioRecorderManager recorderManager;
    @Inject RecordingRepository recordingRepository;

    private RecordingContract.Presenter presenter;
    private WaveformView waveformView;
    private TextView tvTimer;
    private final Handler timerHandler = new Handler(Looper.getMainLooper());
    private long startMs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        ((VoiceChangerApp) getApplication()).getAppComponent().inject(this);

        waveformView = findViewById(R.id.waveformView);
        tvTimer = findViewById(R.id.tvTimer);
        FloatingActionButton fabStop = findViewById(R.id.fabStop);
        FloatingActionButton fabSave = findViewById(R.id.fabSave);

        presenter = new RecordingPresenter(
                this,
                recorderManager,
            new SaveRecordingUseCase(recordingRepository),
            resolveOutputDir()
        );
        presenter.onViewCreated();

        fabStop.setOnClickListener(v -> presenter.onStopClicked());
        fabSave.setOnClickListener(v -> presenter.onSaveClicked());

        startMs = System.currentTimeMillis();
        timerHandler.post(timerTick);
    }

    private final Runnable timerTick = new Runnable() {
        @Override
        public void run() {
            long elapsed = System.currentTimeMillis() - startMs;
            int minutes = (int) (elapsed / 60000L);
            int seconds = (int) ((elapsed % 60000L) / 1000L);
            int ms = (int) (elapsed % 1000L) / 10;
            updateTimer(String.format(Locale.US, "%02d:%02d:%02d", minutes, seconds, ms));
            timerHandler.postDelayed(this, 16L);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandler.removeCallbacks(timerTick);
        presenter.onDestroy();
    }

    @Override
    public void updateTimer(String text) {
        runOnUiThread(() -> tvTimer.setText(text));
    }

    @Override
    public void updateAmplitude(float normalized) {
        waveformView.updateAmplitude(normalized);
    }

    @Override
    public void onRecordingSaved(String recordingPath) {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, AddEffectActivity.class);
            intent.putExtra(AddEffectActivity.EXTRA_RECORDING_PATH, recordingPath);
            intent.putExtra(AddEffectActivity.EXTRA_EFFECT_NAME, "Normal");
            startActivity(intent);
            finish();
            InterstitialAdManager.getInstance().showIfReady(this);
        });
    }

    @Override
    public void showError(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    private File resolveOutputDir() {
        File externalMusic = getExternalFilesDir(android.os.Environment.DIRECTORY_MUSIC);
        if (externalMusic != null) {
            return externalMusic;
        }
        return new File(getFilesDir(), "recordings");
    }
}
