package com.synapticon.bucke.dashboard;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public abstract class BaseCard extends View implements ValueAnimator.AnimatorUpdateListener {

    private Paint mRectPaint;
    private TextPaint mLabelTextPaint;

    private boolean on;

    private ValueAnimator mValueAnimator;
    private Handler mValueAnimatorReverseHandler;
    private float mTranslateValue;

    public BaseCard(Context context) {
        super(context);
        init();
    }

    public BaseCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setColor(Color.parseColor("#FFFFFFFF"));

        mLabelTextPaint = new TextPaint();
        mLabelTextPaint.setColor(Color.parseColor("#FF3C3C3B"));
        mLabelTextPaint.setTextSize(80.0f);
        mLabelTextPaint.setAntiAlias(true);
        mLabelTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Th.otf"));

        mValueAnimatorReverseHandler = new Handler();
    }

    public void setOn(boolean on) {
        this.on = on;

        mValueAnimatorReverseHandler.removeCallbacks(mValueAnimatorReverseRunnable);

        if (mValueAnimator != null) {
            mValueAnimator.end();
        }

        mValueAnimator = ValueAnimator.ofFloat(0f, getWidth());
        mValueAnimator.setDuration(400);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.start();

        mValueAnimatorReverseHandler.postDelayed(mValueAnimatorReverseRunnable, 2800);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mTranslateValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    Runnable mValueAnimatorReverseRunnable = new Runnable() {
        @Override
        public void run() {
            mValueAnimator.reverse();
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw rounded rectangle
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.translate(getWidth() - mTranslateValue, 0);
        canvas.drawRoundRect(rectF, 33, 33, mRectPaint);

        if (on) {
            canvas.drawBitmap(getOnBitmap(), 60, 60, new Paint());
            canvas.translate(0, getLabelY());
            new StaticLayout(getOnText(), mLabelTextPaint, 280, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
        } else {
            canvas.drawBitmap(getOffBitmap(), 60, 60, new Paint());
            canvas.translate(0, getLabelY());
            new StaticLayout(getOffText(), mLabelTextPaint, 280, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
        }

        canvas.restore();
    }

    public boolean isOn() {
        return on;
    }

    protected abstract Bitmap getOnBitmap();

    protected abstract Bitmap getOffBitmap();

    protected TextPaint getLabelTextPaint() {
        return mLabelTextPaint;
    }

    protected String getOnText() {
        return "ON";
    }

    protected String getOffText() {
        return "OFF";
    }

    protected int getLabelY() {
        return 264;
    }
}
