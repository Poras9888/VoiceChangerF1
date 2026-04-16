package com.voice.changer.sound.effects.recorder.presentation.addeffect;

import com.voice.changer.sound.effects.recorder.audio.effects.EffectCategory;
import com.voice.changer.sound.effects.recorder.audio.effects.VoiceEffect;

import java.util.List;

public interface AddEffectContract {
    interface View {
        void showEffects(List<VoiceEffect> effects);
        void selectEffect(VoiceEffect effect);
        void updatePlayerTime(String formattedTime);
        void showProcessingDialog();
        void hideProcessingDialog();
        void onEffectApplied(String outputPath);
        void showError(String message);
    }

    interface Presenter {
        void onViewCreated();
        void filterByCategory(EffectCategory category);
        void applyEffect();
        void onDestroy();
    }
}
