package com.voice.changer.sound.effects.recorder.presentation.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import com.voice.changer.sound.effects.recorder.R;
import com.voice.changer.sound.effects.recorder.VoiceChangerApp;
import com.voice.changer.sound.effects.recorder.core.ads.BannerAdHelper;
import com.voice.changer.sound.effects.recorder.core.ads.ConsentManager;

public class SettingsActivity extends AppCompatActivity implements SettingsContract.View {
    private SettingsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ((VoiceChangerApp) getApplication()).getAppComponent().inject(this);

        presenter = new SettingsPresenter(this);
        presenter.onViewCreated();

        findViewById(R.id.btnAdPrefs).setOnClickListener(v -> ConsentManager.showPrivacyOptionsForm(this));
        findViewById(R.id.btnPrivacy).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://example.com/privacy"))));
        findViewById(R.id.btnRate).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()))));
        findViewById(R.id.btnShareApp).setOnClickListener(v -> ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setText("https://play.google.com/store/apps/details?id=" + getPackageName())
                .startChooser());

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
