package com.voice.changer.sound.effects.recorder.presentation.switchvoice;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.voice.changer.sound.effects.recorder.R;

public class SwitchVoiceActivity extends AppCompatActivity implements SwitchVoiceContract.View {
    private SwitchVoiceContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_voice);
        presenter = new SwitchVoicePresenter(this);
        presenter.onViewCreated();

        EditText etSource = findViewById(R.id.etSourceVoice);
        EditText etTarget = findViewById(R.id.etTargetVoice);
        findViewById(R.id.btnSwitchNow).setOnClickListener(v -> {
            String source = String.valueOf(etSource.getText()).trim();
            String target = String.valueOf(etTarget.getText()).trim();
            if (source.isEmpty() || target.isEmpty()) {
                showError("Fill source and target voice first");
                return;
            }
            Toast.makeText(this, "Voice switch started", Toast.LENGTH_SHORT).show();
        });
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
