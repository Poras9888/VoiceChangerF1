package com.voice.changer.sound.effects.recorder.presentation.addeffect;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.voice.changer.sound.effects.recorder.R;
import com.voice.changer.sound.effects.recorder.VoiceChangerApp;
import com.voice.changer.sound.effects.recorder.audio.AudioProcessor;
import com.voice.changer.sound.effects.recorder.audio.effects.EffectCategory;
import com.voice.changer.sound.effects.recorder.audio.effects.VoiceEffect;
import com.voice.changer.sound.effects.recorder.core.ads.InterstitialAdManager;
import com.voice.changer.sound.effects.recorder.domain.usecase.ApplyEffectUseCase;
import com.voice.changer.sound.effects.recorder.ui.adapter.EffectGridAdapter;

import java.util.List;
import java.io.File;

import javax.inject.Inject;

public class AddEffectActivity extends AppCompatActivity implements AddEffectContract.View {
    public static final String EXTRA_RECORDING_PATH = "recording_path";
    public static final String EXTRA_EFFECT_NAME = "effect_name";

    @Inject AudioProcessor audioProcessor;

    private AddEffectPresenter presenter;
    private EffectGridAdapter adapter;
    private TextView tvPlayerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_effect);
        ((VoiceChangerApp) getApplication()).getAppComponent().inject(this);

        String inputPath = getIntent().getStringExtra(EXTRA_RECORDING_PATH);
        if (inputPath == null) {
            finish();
            return;
        }

        presenter = new AddEffectPresenter(
                this,
                new ApplyEffectUseCase(audioProcessor),
                inputPath,
            resolveOutputDir().getAbsolutePath()
        );

        RecyclerView rv = findViewById(R.id.rvEffectsGrid);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new EffectGridAdapter(effect -> presenter.onEffectSelected(effect));
        rv.setAdapter(adapter);

        tvPlayerTime = findViewById(R.id.tvPlayerTime);

        Chip chipAll = findViewById(R.id.chipAll);
        Chip chipScary = findViewById(R.id.chipScary);
        Chip chipOther = findViewById(R.id.chipOther);
        chipAll.setOnClickListener(v -> presenter.filterByCategory(EffectCategory.ALL));
        chipScary.setOnClickListener(v -> presenter.filterByCategory(EffectCategory.SCARY));
        chipOther.setOnClickListener(v -> presenter.filterByCategory(EffectCategory.OTHER));

        SeekBar seekPlayer = findViewById(R.id.seekPlayer);
        seekPlayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updatePlayerTime(String.format(java.util.Locale.US, "00:%02d", progress));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        MaterialButton btnApply = findViewById(R.id.btnApplyEffect);
        btnApply.setOnClickListener(v -> presenter.applyEffect());

        presenter.onViewCreated();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showEffects(List<VoiceEffect> effects) {
        runOnUiThread(() -> adapter.submit(effects));
    }

    @Override
    public void selectEffect(VoiceEffect effect) {
        runOnUiThread(() -> adapter.setSelected(effect));
    }

    @Override
    public void updatePlayerTime(String formattedTime) {
        runOnUiThread(() -> tvPlayerTime.setText(formattedTime));
    }

    @Override
    public void showProcessingDialog() {
        runOnUiThread(() -> Toast.makeText(this, "Processing...", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void hideProcessingDialog() {
    }

    @Override
    public void onEffectApplied(String outputPath) {
        runOnUiThread(() -> {
            Toast.makeText(this, "Saved: " + outputPath, Toast.LENGTH_SHORT).show();
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
        return new File(getFilesDir(), "effects");
    }
}
