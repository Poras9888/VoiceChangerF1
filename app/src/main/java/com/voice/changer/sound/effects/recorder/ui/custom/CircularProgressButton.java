package com.voice.changer.sound.effects.recorder.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class CircularProgressButton extends FrameLayout {
    private final Paint arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float progress;

    public CircularProgressButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(8f);
    }

    public void setProgress(float value) {
        progress = Math.max(0f, Math.min(1f, value));
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        RectF rectF = new RectF(4, 4, getWidth() - 4f, getHeight() - 4f);
        canvas.drawArc(rectF, -90f, 360f * progress, false, arcPaint);
    }
}
