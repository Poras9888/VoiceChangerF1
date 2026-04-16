package com.voice.changer.sound.effects.recorder.presentation.reversevoice;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.voice.changer.sound.effects.recorder.R;

public class ReverseVoiceActivity extends AppCompatActivity implements ReverseVoiceContract.View {
    private ReverseVoiceContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_voice);
        presenter = new ReverseVoicePresenter(this);
        presenter.onViewCreated();

        findViewById(R.id.btnPickAudio).setOnClickListener(v ->
            Toast.makeText(this, "Audio picker coming soon", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnReverseNow).setOnClickListener(v ->
            Toast.makeText(this, "Reverse processing started", Toast.LENGTH_SHORT).show());
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
