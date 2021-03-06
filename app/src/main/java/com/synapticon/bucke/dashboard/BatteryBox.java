package com.synapticon.bucke.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class BatteryBox extends View {

    public static final String TAG = BatteryBox.class.getSimpleName();

    private Paint mPaint;
    private TextPaint mBatteryTextPaintStandstill;
    private TextPaint mBatteryTextPaintDriving;

    private boolean mDriving;
    private int mBatteryStateOfCharge = 100;
    private int mBatteryPower = 0;

    private Bitmap mGreenFrameBitmap;
    private Bitmap mGreenBackgroundBitmap;
    private Bitmap mOrangeFrameBitmap;
    private Bitmap mOrangeBackgroundBitmap;
    private Bitmap mRedFrameBitmap;

    private Bitmap mRedBackgroundBitmap;
    private Bitmap mArrowsGreenUpward;
    private Bitmap mArrowsGreenDownward;
    private Bitmap mArrowsOrangeUpward;
    private Bitmap mArrowsOrangeDownward;
    private Bitmap mArrowsRedUpward;
    private Bitmap mArrowsRedDownward;

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

        mBatteryTextPaintStandstill = new TextPaint();
        mBatteryTextPaintStandstill.setARGB(255, 255, 255, 255);
        mBatteryTextPaintStandstill.setTextSize(51.43f);
        mBatteryTextPaintStandstill.setAntiAlias(true);
        mBatteryTextPaintStandstill.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Th.otf"));

        mBatteryTextPaintDriving = new TextPaint();
        mBatteryTextPaintDriving.setARGB(255, 255, 255, 255);
        mBatteryTextPaintDriving.setTextSize(51.43f);
        mBatteryTextPaintDriving.setAntiAlias(true);
        mBatteryTextPaintDriving.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Roman.otf"));

        // Frames and backgrounds
        mGreenFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_green_frame);
        mGreenBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_green_background);

        mOrangeFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_orange_frame);
        mOrangeBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_orange_background);

        mRedFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_red_frame);
        mRedBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_red_background);

        // Arrows
        mArrowsGreenUpward = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_arrows_white_upward);
        mArrowsGreenDownward = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_arrows_white_downward);

        mArrowsOrangeUpward = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_arrows_white_upward);
        mArrowsOrangeDownward = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_arrows_white_downward);

        mArrowsRedUpward = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_arrows_white_upward);
        mArrowsRedDownward = BitmapFactory.decodeResource(getResources(), R.drawable.battery_box_arrows_white_downward);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap frameBitmap = mGreenFrameBitmap;
        Bitmap backgroundBitmap = mGreenBackgroundBitmap;

        Bitmap arrowsBitmap = null;

        if (mBatteryPower < 0) {
            arrowsBitmap = mArrowsGreenDownward;
        } else if (mBatteryPower > 0) {
            arrowsBitmap = mArrowsGreenUpward;
        }

        if (mBatteryStateOfCharge <= 20) {
            frameBitmap = mRedFrameBitmap;
            backgroundBitmap = mRedBackgroundBitmap;
            if (mBatteryPower < 0) {
                arrowsBitmap = mArrowsRedDownward;
            } else if (mBatteryPower > 0) {
                arrowsBitmap = mArrowsRedUpward;
            }
        } else if (mBatteryStateOfCharge <= 35) {
            frameBitmap = mOrangeFrameBitmap;
            backgroundBitmap = mOrangeBackgroundBitmap;
            if (mBatteryPower < 0) {
                arrowsBitmap = mArrowsOrangeDownward;
            } else if (mBatteryPower > 0) {
                arrowsBitmap = mArrowsOrangeUpward;
            }
        }

        canvas.save();

        // Clip progress based on the battery state of charge (percentage)
        canvas.clipRect(0, progressBarHeight(getHeight(), mBatteryStateOfCharge), getWidth(), getHeight());

        // Draw rounded rectangle to be clipped
        canvas.drawBitmap(backgroundBitmap, 0, 0, mPaint);

        // Restore canvas so that clipping is not applied to the frame bitmap
        canvas.restore();

        canvas.drawBitmap(frameBitmap, 0, 0, mPaint);

        if (arrowsBitmap != null) {
            canvas.drawBitmap(arrowsBitmap, 23, 30, mPaint);
        }

        if (!isDriving() && mBatteryPower == 0) {
            canvas.translate(0, 117);
            new StaticLayout(String.valueOf(mBatteryStateOfCharge) + "\n%", mBatteryTextPaintStandstill, 120, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
        } else {
            canvas.translate(0, 130);
            new StaticLayout(String.valueOf(mBatteryStateOfCharge), mBatteryTextPaintDriving, 120, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
        }

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
        return height - ((height - 12) * (percentage / 100f) + 6f);
    }

    public int getBatteryStateOfCharge() {
        return mBatteryStateOfCharge;
    }

    public void setBatteryStateOfCharge(int batteryStateOfCharge) {
        this.mBatteryStateOfCharge = batteryStateOfCharge;
        invalidate();
    }

    public int getBatteryPower() {
        return mBatteryPower;
    }

    public void setBatteryPower(int batteryPower) {
        this.mBatteryPower = batteryPower;
        invalidate();
    }

    public boolean isDriving() {
        return mDriving;
    }

    public void setDriving(boolean driving) {
        this.mDriving = driving;
        invalidate();
    }
}
