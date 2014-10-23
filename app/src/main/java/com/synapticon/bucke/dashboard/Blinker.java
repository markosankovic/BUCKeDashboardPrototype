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
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

public class Blinker extends View {

    private static final String TAG = Blinker.class.getSimpleName();

    private Paint mPaint;
    private int width, height;

    private boolean mBlink;

    private ValueAnimator mValueAnimator;
    private float mBlinkWidth;

    public Blinker(Context context) {
        super(context);
        init();
    }

    public Blinker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Blinker(Context context, AttributeSet attrs, int defStyle) {
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
            width = getWidth();
            height = getHeight();

            mPaint.setAntiAlias(true);
            //Log.i(TAG, String.valueOf(mBlinkWidth));
            mPaint.setShader(new LinearGradient(0, 0, mBlinkWidth, 0, Color.parseColor("#FFF1C40E"), Color.TRANSPARENT, Shader.TileMode.MIRROR));

            RectF rectF = new RectF(0, 0, mBlinkWidth, height);
            canvas.drawRect(rectF, mPaint);
        }
    }

    public void setBlink(boolean blink) {
        this.mBlink = blink;
        if (blink) {
            mValueAnimator = ValueAnimator.ofFloat(50f, 600f, 50f);
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
