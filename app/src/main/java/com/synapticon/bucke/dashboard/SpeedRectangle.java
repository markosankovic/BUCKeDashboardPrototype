package com.synapticon.bucke.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class SpeedRectangle extends View {

    private static final String TAG = SpeedRectangle.class.getSimpleName();

    private Paint mPaint;
    private int width, height;
    private float mSpeed;

    public SpeedRectangle(Context context) {
        super(context);
        init();
    }

    public SpeedRectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpeedRectangle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.HSVToColor(SpeedHSV.getInstance().hsv(mSpeed)));

        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, 20, 20, mPaint);
    }

    public void setSpeed(float speed) {
        this.mSpeed = speed;
        invalidate();
    }
}
