package com.synapticon.bucke.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    private Bitmap mGreenFrameBitmap;
    private Bitmap mGreenBackgroundBitmap;
    private Bitmap mOrangeFrameBitmap;
    private Bitmap mOrangeBackgroundBitmap;
    private Bitmap mRedFrameBitmap;
    private Bitmap mRedBackgroundBitmap;

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

        mGreenFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_green_frame);
        mGreenBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_green_background);
        mOrangeFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_orange_frame);
        mOrangeBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_orange_background);
        mRedFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_red_frame);
        mRedBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_red_background);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap frameBitmap = mGreenFrameBitmap;
        Bitmap backgroundBitmap = mGreenBackgroundBitmap;

        if (mBatteryStateOfCharge <= 20) {
            frameBitmap = mRedFrameBitmap;
            backgroundBitmap = mRedBackgroundBitmap;
        } else if (mBatteryStateOfCharge <= 35) {
            frameBitmap = mOrangeFrameBitmap;
            backgroundBitmap = mOrangeBackgroundBitmap;
        }

        canvas.save();

        // Clip progress based on the battery state of charge (percentage)
        canvas.clipRect(0, progressBarHeight(getHeight(), mBatteryStateOfCharge), getWidth(), getHeight());

        // Draw rounded rectangle to be clipped
        canvas.drawBitmap(backgroundBitmap, 0, 0, mPaint);

        // Restore canvas so that clipping is not applied to the frame bitmap
        canvas.restore();

        canvas.drawBitmap(frameBitmap, 0, 0, mPaint);

        canvas.translate(0, 130);
        new StaticLayout(String.valueOf(mBatteryStateOfCharge), mBatteryTextPaint, 120, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
        canvas.restore();
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
