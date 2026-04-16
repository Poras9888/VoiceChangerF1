package com.voice.changer.sound.effects.recorder.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

import com.voice.changer.sound.effects.recorder.R;
import com.voice.changer.sound.effects.recorder.VoiceChangerApp;
import com.voice.changer.sound.effects.recorder.audio.effects.VoiceEffect;
import com.voice.changer.sound.effects.recorder.core.ads.BannerAdHelper;
import com.voice.changer.sound.effects.recorder.core.ads.ConsentManager;
import com.voice.changer.sound.effects.recorder.core.ads.InterstitialAdManager;
import com.voice.changer.sound.effects.recorder.data.model.Feature;
import com.voice.changer.sound.effects.recorder.presentation.pranksound.PrankSoundActivity;
import com.voice.changer.sound.effects.recorder.presentation.recording.RecordingActivity;
import com.voice.changer.sound.effects.recorder.presentation.reversevoice.ReverseVoiceActivity;
import com.voice.changer.sound.effects.recorder.presentation.savedrecordings.SavedRecordingsActivity;
import com.voice.changer.sound.effects.recorder.presentation.settings.SettingsActivity;
import com.voice.changer.sound.effects.recorder.presentation.switchvoice.SwitchVoiceActivity;
import com.voice.changer.sound.effects.recorder.presentation.texttospeech.TextToAudioActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    private HomeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((VoiceChangerApp) getApplication()).getAppComponent().inject(this);
        presenter = new HomePresenter(this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.cardRecord).setOnClickListener(v -> {
            presenter.onFeatureCardClicked("record");
            InterstitialAdManager.getInstance().showIfReady(this);
        });
        findViewById(R.id.cardTts).setOnClickListener(v -> {
            presenter.onFeatureCardClicked("tts");
            InterstitialAdManager.getInstance().showIfReady(this);
        });
        findViewById(R.id.cardReverse).setOnClickListener(v -> {
            presenter.onFeatureCardClicked("reverse");
            InterstitialAdManager.getInstance().showIfReady(this);
        });
        findViewById(R.id.cardSwitch).setOnClickListener(v -> {
            presenter.onFeatureCardClicked("switch");
            InterstitialAdManager.getInstance().showIfReady(this);
        });
        findViewById(R.id.btnHeroStart).setOnClickListener(v -> {
            presenter.onFeatureCardClicked("prank");
            InterstitialAdManager.getInstance().showIfReady(this);
        });

        FrameLayout adContainer = findViewById(R.id.adContainer);
        ConsentManager.gatherConsent(this, () -> BannerAdHelper.loadBanner(this, adContainer));
        presenter.onViewCreated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_saved_recordings) {
            startActivity(new Intent(this, SavedRecordingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void showFeatureGrid(List<Feature> features) { }
    @Override public void showVoiceEffects(List<VoiceEffect> effects) { }
    @Override public void navigateToRecording() { startActivity(new Intent(this, RecordingActivity.class)); }
    @Override public void navigateToPrankSound() { startActivity(new Intent(this, PrankSoundActivity.class)); }
    @Override public void navigateToTextToAudio() { startActivity(new Intent(this, TextToAudioActivity.class)); }
    @Override public void navigateToReverseVoice() { startActivity(new Intent(this, ReverseVoiceActivity.class)); }
    @Override public void navigateToSwitchVoice() { startActivity(new Intent(this, SwitchVoiceActivity.class)); }
    @Override public void showError(String message) { Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); }
}
