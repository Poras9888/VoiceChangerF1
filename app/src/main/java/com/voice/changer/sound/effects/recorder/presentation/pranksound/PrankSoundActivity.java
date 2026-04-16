package com.voice.changer.sound.effects.recorder.presentation.pranksound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.voice.changer.sound.effects.recorder.R;
import com.voice.changer.sound.effects.recorder.VoiceChangerApp;
import com.voice.changer.sound.effects.recorder.core.ads.BannerAdHelper;
import com.voice.changer.sound.effects.recorder.core.ads.InterstitialAdManager;
import com.voice.changer.sound.effects.recorder.service.PrankSoundService;
import com.voice.changer.sound.effects.recorder.ui.adapter.PrankSoundAdapter;

public class PrankSoundActivity extends AppCompatActivity implements PrankSoundContract.View {
    private PrankSoundContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prank_sound);
        ((VoiceChangerApp) getApplication()).getAppComponent().inject(this);

        presenter = new PrankSoundPresenter(this);
        presenter.onViewCreated();

        RecyclerView rv = findViewById(R.id.rvPrankSounds);
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(new PrankSoundAdapter());

        SeekBar seekVolume = findViewById(R.id.seekVolume);
        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { presenter.setVolume(progress); }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        SwitchMaterial switchLoop = findViewById(R.id.switchLoop);
        switchLoop.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.setLoopMode(isChecked));

        Spinner spinner = findViewById(R.id.spinnerDelay);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[] {"0s", "5s", "10s", "30s"}));

        findViewById(R.id.btnHiddenMode).setOnClickListener(v -> {
            startService(new Intent(this, PrankSoundService.class));
            Toast.makeText(this, "Hidden mode enabled", Toast.LENGTH_SHORT).show();
            InterstitialAdManager.getInstance().showIfReady(this);
        });

        BannerAdHelper.loadBanner(this, findViewById(R.id.adContainer));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
