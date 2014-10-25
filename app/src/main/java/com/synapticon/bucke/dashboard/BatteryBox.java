package com.synapticon.bucke.dashboard;

import android.content.Context;
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

public class BatteryBox extends View {

    private Paint mPaint;
    private TextPaint mBatteryTextPaint;
    private int mBattery = 100;

    public BatteryBox(Context context) {
        super(context);
        init();
    }

    public BatteryBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BatteryBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FF29B866"));

        mBatteryTextPaint = new TextPaint();
        mBatteryTextPaint.setARGB(255, 255, 255, 255);
        mBatteryTextPaint.setTextSize(51.43f);
        mBatteryTextPaint.setAntiAlias(true);
        mBatteryTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Th.otf"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw rounded rectangle
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, 22, 22, mPaint);

        canvas.translate(0, 130);
        new StaticLayout(String.valueOf(mBattery), mBatteryTextPaint, 120, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
        canvas.restore();
    }

    public void setBattery(int battery) {
        this.mBattery = battery;
        invalidate();
    }
}
