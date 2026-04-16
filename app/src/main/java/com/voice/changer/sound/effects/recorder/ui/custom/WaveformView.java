package com.voice.changer.sound.effects.recorder.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class WaveformView extends View {
    private static final int BAR_COUNT = 40;
    private final float[] amplitudes = new float[BAR_COUNT];
    private final Paint barPaint;

    public WaveformView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setColor(Color.WHITE);
        barPaint.setAlpha(217);
    }

    public void updateAmplitude(float amplitude) {
        System.arraycopy(amplitudes, 1, amplitudes, 0, BAR_COUNT - 1);
        amplitudes[BAR_COUNT - 1] = lerp(amplitudes[BAR_COUNT - 1], amplitude, 0.3f);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float barWidth = getWidth() / (BAR_COUNT * 2f);
        float maxHeight = getHeight() * 0.8f;
        float centerY = getHeight() / 2f;

        for (int i = 0; i < BAR_COUNT; i++) {
            float x = i * (barWidth * 2) + barWidth / 2f;
            float barHeight = Math.max(4f, amplitudes[i] * maxHeight);
            canvas.drawRect(x, centerY - barHeight / 2f, x + barWidth, centerY + barHeight / 2f, barPaint);
        }
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }
}
