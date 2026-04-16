package com.voice.changer.sound.effects.recorder.presentation.addeffect;

import com.voice.changer.sound.effects.recorder.audio.effects.EffectCategory;
import com.voice.changer.sound.effects.recorder.audio.effects.EffectFactory;
import com.voice.changer.sound.effects.recorder.audio.effects.VoiceEffect;
import com.voice.changer.sound.effects.recorder.domain.usecase.ApplyEffectUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddEffectPresenter implements AddEffectContract.Presenter {
    private final AddEffectContract.View view;
    private final ApplyEffectUseCase applyEffectUseCase;
    private final String inputPath;
    private final String outputDir;
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    private final List<VoiceEffect> allEffects = EffectFactory.createAll();
    private VoiceEffect selected;

    public AddEffectPresenter(AddEffectContract.View view,
                              ApplyEffectUseCase applyEffectUseCase,
                              String inputPath,
                              String outputDir) {
        this.view = view;
        this.applyEffectUseCase = applyEffectUseCase;
        this.inputPath = inputPath;
        this.outputDir = outputDir;
    }

    @Override
    public void onViewCreated() {
        selected = allEffects.get(0);
        view.showEffects(allEffects);
        view.selectEffect(selected);
    }

    @Override
    public void filterByCategory(EffectCategory category) {
        if (category == EffectCategory.ALL) {
            view.showEffects(allEffects);
            return;
        }
        List<VoiceEffect> filtered = new ArrayList<>();
        for (VoiceEffect effect : allEffects) {
            if (effect.getCategory() == category) {
                filtered.add(effect);
            }
        }
        view.showEffects(filtered);
    }

    public void onEffectSelected(VoiceEffect effect) {
        selected = effect;
        view.selectEffect(effect);
    }

    @Override
    public void applyEffect() {
        if (selected == null) {
            view.showError("Select effect first");
            return;
        }
        view.showProcessingDialog();
        io.execute(() -> {
            try {
                String out = applyEffectUseCase.execute(inputPath, selected, outputDir);
                view.hideProcessingDialog();
                view.onEffectApplied(out);
            } catch (Exception ex) {
                view.hideProcessingDialog();
                view.showError("Processing failed: " + ex.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        io.shutdownNow();
    }
}
