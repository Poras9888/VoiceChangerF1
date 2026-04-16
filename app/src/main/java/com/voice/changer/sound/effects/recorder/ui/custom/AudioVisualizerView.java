package com.voice.changer.sound.effects.recorder.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class AudioVisualizerView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public AudioVisualizerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() / 20f;
        for (int i = 0; i < 20; i++) {
            float h = (float) (Math.sin(i) * getHeight() * 0.3f + getHeight() * 0.5f);
            canvas.drawRect(i * width, getHeight() - h, i * width + width * 0.7f, getHeight(), paint);
        }
    }
}
