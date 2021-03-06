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

public class SpeedBox extends View {

    private static final String TAG = SpeedBox.class.getSimpleName();

    private Paint mPaint;
    private TextPaint mSpeedTextPaint;
    private TextPaint mLabelTextPaint;

    private boolean driving;
    private int mSpeed;

    private int[] mColors;
    private float[] mPositions;

    public SpeedBox(Context context) {
        super(context);
        init();
    }

    public SpeedBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpeedBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mColors = new int[2];
        mPositions = new float[]{0f, 1f};

        mSpeedTextPaint = new TextPaint();
        mSpeedTextPaint.setARGB(255, 255, 255, 255);
        mSpeedTextPaint.setTextSize(413.33f);
        mSpeedTextPaint.setAntiAlias(true);
        mSpeedTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Roman.otf"));

        mLabelTextPaint = new TextPaint();
        mLabelTextPaint.setARGB(255, 255, 255, 255);
        mLabelTextPaint.setTextSize(51.42f);
        mLabelTextPaint.setAntiAlias(true);
        mLabelTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Th.otf"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw rounded rectangle with color matching the speed
        mColors[0] = Color.HSVToColor(SpeedHSV.getInstance().hsv(mSpeed));
        mColors[1] = darken(mColors[0], 0.8);
        mPaint.setShader(new RadialGradient(getWidth() / 2, getHeight() / 2, getWidth() / 2, mColors, mPositions, Shader.TileMode.CLAMP));
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, 42, 42, mPaint);

        // Draw speed text
        canvas.translate(-45, 32);
        new StaticLayout(String.valueOf((int) mSpeed), mSpeedTextPaint, getWidth(), Layout.Alignment.ALIGN_OPPOSITE, 1f, 0f, true).draw(canvas);

        // Draw km/h label only when vehicle is in standstill mode
        if (isStandstill()) {
            canvas.translate(-20, 428);
            new StaticLayout("km/h", mLabelTextPaint, getWidth(), Layout.Alignment.ALIGN_OPPOSITE, 1f, 0f, true).draw(canvas);
        }

        canvas.restore();
    }

    public static int darken(int color, double fraction) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= fraction;
        return Color.HSVToColor(hsv);
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        this.mSpeed = speed;
        invalidate();
    }

    public void setDriving(boolean driving) {
        this.driving = driving;
        invalidate();
    }

    public boolean isDriving() {
        return driving;
    }

    public boolean isStandstill() {
        return !isDriving();
    }
}
