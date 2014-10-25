package com.synapticon.bucke.dashboard;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class HighBeamCard extends View {

    private static final String TAG = HighBeamCard.class.getSimpleName();

    private Paint mPaint;

    private boolean on;

    private ValueAnimator mValueAnimator;
    private float mTranslateValue;

    private Bitmap mOnBitmap;
    private Bitmap mOffBitmap;

    private TextPaint mLabelTextPaint;

    public HighBeamCard(Context context) {
        super(context);
        init();
    }

    public HighBeamCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HighBeamCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#E6FFFFFF"));

        mOnBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.high_beam_on);
        mOffBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.high_beam_off);

        mLabelTextPaint = new TextPaint();
        mLabelTextPaint.setColor(Color.parseColor("#FF3C3C3B"));
        mLabelTextPaint.setTextSize(48.0f);
        mLabelTextPaint.setAntiAlias(true);
        mLabelTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Th.otf"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw rounded rectangle
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.translate(getWidth() - mTranslateValue, 0);
        canvas.drawRoundRect(rectF, 33, 33, mPaint);

        if (on) {
            canvas.drawBitmap(mOnBitmap, 60, 60, new Paint());
            canvas.translate(-30, 280);
            new StaticLayout("ON", mLabelTextPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
        } else {
            canvas.drawBitmap(mOffBitmap, 60, 60, new Paint());
            canvas.translate(-30, 280);
            new StaticLayout("OFF", mLabelTextPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
        }

        canvas.restore();
    }

    public void setOn(boolean on) {
        this.on = on;

        if (mValueAnimator != null) {
            mValueAnimator.end();
        }

        mValueAnimator = ValueAnimator.ofFloat(0f, getWidth(), getWidth(), getWidth(), getWidth(), getWidth(), getWidth(), getWidth(), getWidth(), getWidth(), getWidth(), getWidth(), getWidth(), getWidth(), 0f);
        mValueAnimator.setDuration(3000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTranslateValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                invalidate();
            }
        });
        mValueAnimator.start();

        invalidate();
    }
}
