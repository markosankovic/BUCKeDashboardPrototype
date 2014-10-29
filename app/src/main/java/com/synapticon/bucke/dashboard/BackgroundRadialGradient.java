package com.synapticon.bucke.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class BackgroundRadialGradient extends View {

    private Paint mPaint;
    private float mSpeed;

    private int[] mColors;
    private float[] mPositions;

    public BackgroundRadialGradient(Context context) {
        super(context);
        init();
    }

    public BackgroundRadialGradient(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackgroundRadialGradient(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mColors = new int[]{Color.parseColor("#FF2980B9"), Color.parseColor("#FF2980B9"), Color.parseColor("#FF1D1D1B")};
        mPositions = new float[]{0f, 0.4f, 1f};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);

        int speedColor = Color.HSVToColor(SpeedHSV.getInstance().hsv(mSpeed));
        mColors[0] = mColors[1] = speedColor;
        mPaint.setShader(new RadialGradient(750, getHeight() / 2, 520, mColors, mPositions, Shader.TileMode.CLAMP));

        mPaint.setAlpha(204);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    public void setSpeed(float speed) {
        this.mSpeed = speed;
        invalidate();
    }
}
