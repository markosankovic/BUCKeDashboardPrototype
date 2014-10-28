package com.synapticon.bucke.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class BatteryBox extends View {

    public static final String TAG = BatteryBox.class.getSimpleName();

    private int[] colors = new int[]{Color.parseColor("#FF29B866"), Color.parseColor("#FFF39C12"), Color.parseColor("#FFE74C3C")};

    private Paint mPaint;
    private TextPaint mBatteryTextPaint;

    private int mBatteryStateOfCharge = 100;

    private Bitmap mFrameBitmap;

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

        mBatteryTextPaint = new TextPaint();
        mBatteryTextPaint.setARGB(255, 255, 255, 255);
        mBatteryTextPaint.setTextSize(51.43f);
        mBatteryTextPaint.setAntiAlias(true);
        mBatteryTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Th.otf"));

        mFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_frame);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Set color to one of: green, orange or red depending on the percentage
        int color = progressBarColor(mBatteryStateOfCharge);
        mPaint.setColor(color);

        canvas.save();

        // Clip progress based on the battery state of charge (percentage)
        canvas.clipRect(0, progressBarHeight(getHeight(), mBatteryStateOfCharge), getWidth(), getHeight());

        // Draw rounded rectangle to be clipped
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF, 22, 22, mPaint);

        // Restore canvas so that clipping is not applied to the frame bitmap
        canvas.restore();

        Paint p = new Paint(color);
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);

        canvas.drawBitmap(mFrameBitmap, 0, 0, p);

        canvas.translate(0, 130);
        new StaticLayout(String.valueOf(mBatteryStateOfCharge), mBatteryTextPaint, 120, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
        canvas.restore();
    }

    int progressBarColor(int percentage) {
        if (percentage <= 20) {
            return colors[2];
        } else if (percentage <= 35) {
            return colors[1];
        } else {
            return colors[0];
        }
    }

    /**
     * Calculate progress bar height based on the supplied full height and percentage.
     * <p/>
     * Consider the frame border when doing the calculation.
     *
     * @param height
     * @return
     */
    private float progressBarHeight(int height, int percentage) {
        // 7f is height of the bottom frame border, start clipping from the inner edge of the frame
        return height - ((height - 14) * (percentage / 100f) + 7f);
    }

    public int getBatteryStateOfCharge() {
        return mBatteryStateOfCharge;
    }

    public void setBatteryStateOfCharge(int batteryStateOfCharge) {
        this.mBatteryStateOfCharge = batteryStateOfCharge;
        invalidate();
    }
}
