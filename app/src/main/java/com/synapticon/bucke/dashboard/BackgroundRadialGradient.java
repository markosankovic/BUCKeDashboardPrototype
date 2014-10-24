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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);
        mPaint.setShader(new RadialGradient(getWidth() / 2, getHeight() / 2, Math.round(getWidth() - (getWidth() * 0.4)), Color.HSVToColor(SpeedHSV.getInstance().hsv(mSpeed)), Color.TRANSPARENT, Shader.TileMode.MIRROR));

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
    }

    public void setSpeed(float speed) {
        this.mSpeed = speed;
        invalidate();
    }
}
