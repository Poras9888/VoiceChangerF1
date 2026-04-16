package com.voice.changer.sound.effects.recorder.presentation.texttospeech;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.voice.changer.sound.effects.recorder.R;
import com.voice.changer.sound.effects.recorder.VoiceChangerApp;
import com.voice.changer.sound.effects.recorder.core.ads.InterstitialAdManager;
import com.voice.changer.sound.effects.recorder.data.repository.RecordingRepository;
import com.voice.changer.sound.effects.recorder.domain.usecase.SaveRecordingUseCase;
import com.voice.changer.sound.effects.recorder.ui.custom.WaveformView;

import java.io.File;

import javax.inject.Inject;

public class TextToAudioActivity extends AppCompatActivity implements TextToAudioContract.View {
    public static final String EXTRA_GENERATED_PATH = "generated_path";

    @Inject RecordingRepository recordingRepository;

    private TextToAudioContract.Presenter presenter;
    private TextInputEditText etInput;
    private TextView tvCharCounter;
    private TextView tvSpeedValue;
    private TextView tvPitchValue;
    private SeekBar seekSpeed;
    private SeekBar seekPitch;
    private WaveformView waveform;
    private String generatedPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_audio);
        ((VoiceChangerApp) getApplication()).getAppComponent().inject(this);

        etInput = findViewById(R.id.etInput);
        tvCharCounter = findViewById(R.id.tvCharCounter);
        tvSpeedValue = findViewById(R.id.tvSpeedValue);
        tvPitchValue = findViewById(R.id.tvPitchValue);
        seekSpeed = findViewById(R.id.seekSpeed);
        seekPitch = findViewById(R.id.seekPitch);
        waveform = findViewById(R.id.waveformPreview);

        presenter = new TextToAudioPresenter(
                this,
            new SaveRecordingUseCase(recordingRepository),
            getApplicationContext(),
            resolveOutputDir()
        );

        MaterialButton btnConvert = findViewById(R.id.btnConvert);
        MaterialButton btnPlay = findViewById(R.id.btnPlay);
        MaterialButton btnSave = findViewById(R.id.btnSave);
        MaterialButton btnShare = findViewById(R.id.btnShare);
        TextView tvLanguage = findViewById(R.id.tvLanguage);

        String[] languages = new String[] {
                "English 🇬🇧", "Spanish 🇪🇸", "French 🇫🇷", "German 🇩🇪", "Italian 🇮🇹",
                "Portuguese 🇵🇹", "Russian 🇷🇺", "Chinese 🇨🇳", "Japanese 🇯🇵", "Korean 🇰🇷"
        };

        tvLanguage.setOnClickListener(v -> new android.app.AlertDialog.Builder(this)
                .setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, languages),
                        (dialog, which) -> tvLanguage.setText(languages[which]))
                .show());

        etInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCharCounter.setText(s.length() + "/200");
            }
            @Override public void afterTextChanged(android.text.Editable s) { }
        });

        seekSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float speed = 0.5f + (progress / 15f) * 1.5f;
                tvSpeedValue.setText(String.format(java.util.Locale.US, "%.1fx", speed));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        seekPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int pitch = progress - 5;
                tvPitchValue.setText(String.valueOf(pitch));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        btnConvert.setOnClickListener(v -> {
            float speed = 0.5f + (seekSpeed.getProgress() / 15f) * 1.5f;
            float pitch = seekPitch.getProgress() - 5f;
            presenter.convertToSpeech(String.valueOf(etInput.getText()), speed, pitch);
        });

        btnPlay.setOnClickListener(v -> {
            if (generatedPath == null) {
                showError("Convert first");
                return;
            }
            Toast.makeText(this, "Preview ready: " + new File(generatedPath).getName(), Toast.LENGTH_SHORT).show();
        });

        btnSave.setOnClickListener(v -> {
            presenter.saveToLibrary();
            InterstitialAdManager.getInstance().showIfReady(this);
        });

        btnShare.setOnClickListener(v -> {
            if (generatedPath == null) {
                showError("Convert first");
                return;
            }
            File sharedFile = new File(generatedPath);
            if (!sharedFile.exists()) {
                showError("Generated audio file is missing");
                return;
            }

            Uri fileUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    sharedFile
            );

            Intent shareIntent = new Intent(Intent.ACTION_SEND)
                    .setType("audio/*")
                    .putExtra(Intent.EXTRA_STREAM, fileUri)
                    .putExtra(Intent.EXTRA_TEXT, "Voice generated with Voice Changer")
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setClipData(ClipData.newRawUri("generated_audio", fileUri));

            startActivity(Intent.createChooser(shareIntent, getString(R.string.share)));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onConverted(String outputPath) {
        runOnUiThread(() -> {
            generatedPath = outputPath;
            waveform.setVisibility(android.view.View.VISIBLE);
        });
    }

    private File resolveOutputDir() {
        File externalMusic = getExternalFilesDir(android.os.Environment.DIRECTORY_MUSIC);
        if (externalMusic != null) {
            return externalMusic;
        }
        return new File(getFilesDir(), "tts");
    }

    @Override
    public void showError(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }
}
