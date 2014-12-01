package com.synapticon.bucke.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class InfoBox extends View {

    private Paint mPaint;
    private TextPaint mPassTextPaint;
    private TextPaint mLeftTextPaint;

    private boolean mDriving;
    private int mRemainingDistance;
    private int mTotalDistance;

    private Typeface mTypefaceTh;
    private Typeface mTypefaceRoman;

    private int[] mColors;
    private float[] mPositions;

    public InfoBox(Context context) {
        super(context);
        init();
    }

    public InfoBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InfoBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mColors = new int[]{Color.parseColor("#FF3B5369"), Color.parseColor("#FF32475A")};
        mPositions = new float[]{0f, 1f};

        mTypefaceTh = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Th.otf");
        mTypefaceRoman = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Roman.otf");

        mPassTextPaint = new TextPaint();
        mPassTextPaint.setARGB(255, 243, 203, 11);
        mPassTextPaint.setTextSize(51.43f);
        mPassTextPaint.setAntiAlias(true);
        mPassTextPaint.setTypeface(mTypefaceTh);

        mLeftTextPaint = new TextPaint();
        mLeftTextPaint.setARGB(255, 52, 210, 124);
        mLeftTextPaint.setTextSize(51.43f);
        mLeftTextPaint.setAntiAlias(true);
        mLeftTextPaint.setTypeface(mTypefaceTh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShader(new RadialGradient(getWidth() / 2, getHeight() / 2, getHeight() / 2, mColors, mPositions, Shader.TileMode.CLAMP));

        // Draw rounded rectangle
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, 33, 33, mPaint);

        int textWidth = getWidth() - 40;

        if (isDriving()) {
            // Set typefaces
            mPassTextPaint.setTextSize(120f - (String.valueOf(mTotalDistance).length() - 1) * 12);
            mPassTextPaint.setTypeface(mTypefaceRoman);
            mLeftTextPaint.setTextSize(120f);
            mLeftTextPaint.setTypeface(mTypefaceRoman);
            // Draw PASS text
            canvas.translate(40, 20);
            new StaticLayout(String.valueOf(mTotalDistance), mPassTextPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true).draw(canvas);
            // Draw LEFT text
            canvas.translate(0, 220);
            new StaticLayout(String.valueOf(mRemainingDistance), mLeftTextPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true).draw(canvas);
        } else {
            // Set typefaces
            mPassTextPaint.setTextSize(51.43f);
            mPassTextPaint.setTypeface(mTypefaceTh);
            mLeftTextPaint.setTextSize(51.43f);
            mLeftTextPaint.setTypeface(mTypefaceTh);
            // Draw PASS text
            canvas.translate(40, 40);
            new StaticLayout(String.format("%d km\npass", mTotalDistance), mPassTextPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true).draw(canvas);
            // Draw LEFT text
            canvas.translate(0, 220);
            new StaticLayout(String.format("left\n%d km", mRemainingDistance), mLeftTextPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true).draw(canvas);
        }

        canvas.restore();
    }

    public void setDriving(boolean driving) {
        this.mDriving = driving;
        invalidate();
    }

    public boolean isDriving() {
        return mDriving;
    }

    public boolean isStandstill() {
        return !isDriving();
    }

    public int getRemainingDistance() {
        return mRemainingDistance;
    }

    public void setRemainingDistance(int remainingDistance) {
        this.mRemainingDistance = remainingDistance;
    }

    public int getTotalDistance() {
        return mTotalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.mTotalDistance = totalDistance;
    }
}
