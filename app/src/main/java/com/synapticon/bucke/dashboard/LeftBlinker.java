package com.synapticon.bucke.dashboard;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class LeftBlinker extends View {

    private Paint mPaint;

    private boolean mBlink;

    private ValueAnimator mValueAnimator;
    private float mBlinkWidth;

    public LeftBlinker(Context context) {
        super(context);
        init();
    }

    public LeftBlinker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeftBlinker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBlink) {
            mPaint.setShader(new LinearGradient(0, 0, mBlinkWidth, 0, Color.parseColor("#FFF1C40E"), Color.TRANSPARENT, Shader.TileMode.CLAMP));
            RectF rectF = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawRect(rectF, mPaint);
        }
    }

    public void setBlink(boolean blink) {
        this.mBlink = blink;
        if (blink) {
            mValueAnimator = ValueAnimator.ofFloat(80f, 400f, 80f);
            mValueAnimator.setDuration(800);
            mValueAnimator.setRepeatCount(9999);
            mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mBlinkWidth = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    invalidate();
                }
            });
            mValueAnimator.start();
        } else {
            mValueAnimator.end();
        }
        invalidate();
    }
}
