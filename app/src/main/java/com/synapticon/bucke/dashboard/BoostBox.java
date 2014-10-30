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

public class BoostBox extends View {

    private Paint mPaint;

    private boolean mDriving;

    private int mBoost = 100, mBoostPrevious = 100;

    private Bitmap mGreenFrameBitmap;
    private Bitmap mGreenBackgroundBitmap;
    private Bitmap mOrangeFrameBitmap;
    private Bitmap mOrangeBackgroundBitmap;
    private Bitmap mRedFrameBitmap;
    private Bitmap mRedBackgroundBitmap;

    private TextPaint mBoostTextPaint;

    public BoostBox(Context context) {
        super(context);
        init();
    }

    public BoostBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoostBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mBoostTextPaint = new TextPaint();
        mBoostTextPaint.setARGB(255, 255, 255, 255);
        mBoostTextPaint.setTextSize(51.43f);
        mBoostTextPaint.setAntiAlias(true);
        mBoostTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Th.otf"));

        // Frames and backgrounds
        mGreenFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boost_box_green_frame);
        mGreenBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boost_box_green_background);

        mOrangeFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boost_box_orange_frame);
        mOrangeBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boost_box_orange_background);

        mRedFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boost_box_red_frame);
        mRedBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boost_box_red_background);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Initially green
        Bitmap frameBitmap = mGreenFrameBitmap;
        Bitmap backgroundBitmap = mGreenBackgroundBitmap;

        // Show red background while charging the boost
        if (isCharging()) {
            frameBitmap = mRedFrameBitmap;
            backgroundBitmap = mRedBackgroundBitmap;
        } else {
            if (mBoost <= 20) {
                frameBitmap = mRedFrameBitmap;
                backgroundBitmap = mRedBackgroundBitmap;
            } else if (mBoost <= 35) {
                frameBitmap = mOrangeFrameBitmap;
                backgroundBitmap = mOrangeBackgroundBitmap;
            }
        }

        canvas.save();

        // Clip progress based on the boost (percentage)
        canvas.clipRect(0, progressBarHeight(getHeight(), mBoost), getWidth(), getHeight());

        // Draw rounded rectangle to be clipped
        canvas.drawBitmap(backgroundBitmap, 0, 0, mPaint);

        // Restore canvas so that clipping is not applied to the frame bitmap
        canvas.restore();

        canvas.drawBitmap(frameBitmap, 0, 0, mPaint);

        if (!isDriving()) {
            if (mBoost == 100) {
                canvas.translate(0, 165);
                new StaticLayout("max\nboost", mBoostTextPaint, 153, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
            } else {
                canvas.translate(0, 190);
                new StaticLayout("boost", mBoostTextPaint, 153, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
            }
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

    public int getBoost() {
        return mBoost;
    }

    public void setBoost(int mBoost) {
        // On 100% boost and previous boost should have the same value (100),
        // so that the green background is displayed indicating that boost can be used again.
        mBoostPrevious = mBoost == 100 ? mBoost : this.mBoost;

        this.mBoost = mBoost;

        invalidate();
    }

    public boolean isDriving() {
        return mDriving;
    }

    public void setDriving(boolean driving) {
        this.mDriving = driving;
        invalidate();
    }

    /**
     * Determines if the boost is charging or discharging
     * based on the current and previous boost values.
     *
     * @return
     */
    public boolean isCharging() {
        return mBoostPrevious < mBoost ? true : false;
    }
}
