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

    private int mRemainingBoost = 100;

    private Bitmap mGreenFrameBitmap;
    private Bitmap mGreenBackgroundBitmap;
    private Bitmap mOrangeFrameBitmap;
    private Bitmap mOrangeBackgroundBitmap;
    private Bitmap mRedFrameBitmap;
    private Bitmap mRedBackgroundBitmap;

    private TextPaint mRemainingBoostTextPaint;

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

        mRemainingBoostTextPaint = new TextPaint();
        mRemainingBoostTextPaint.setARGB(255, 255, 255, 255);
        mRemainingBoostTextPaint.setTextSize(51.43f);
        mRemainingBoostTextPaint.setAntiAlias(true);
        mRemainingBoostTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd-Th.otf"));

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

        if (mRemainingBoost <= 20) {
            frameBitmap = mRedFrameBitmap;
            backgroundBitmap = mRedBackgroundBitmap;
        } else if (mRemainingBoost <= 35) {
            frameBitmap = mOrangeFrameBitmap;
            backgroundBitmap = mOrangeBackgroundBitmap;
        }

        canvas.save();

        // Clip progress based on the boost (percentage)
        canvas.clipRect(0, progressBarHeight(getHeight(), mRemainingBoost), getWidth(), getHeight());

        // Draw rounded rectangle to be clipped
        canvas.drawBitmap(backgroundBitmap, 0, 0, mPaint);

        // Restore canvas so that clipping is not applied to the frame bitmap
        canvas.restore();

        canvas.drawBitmap(frameBitmap, 0, 0, mPaint);

        if (!isDriving()) {
            if (mRemainingBoost == 100) {
                canvas.translate(0, 165);
                new StaticLayout("max\nboost", mRemainingBoostTextPaint, 153, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
            } else {
                canvas.translate(0, 190);
                new StaticLayout("boost", mRemainingBoostTextPaint, 153, Layout.Alignment.ALIGN_CENTER, 1f, 0f, true).draw(canvas);
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

    public int getRemainingBoost() {
        return mRemainingBoost;
    }

    public void setRemainingBoost(int remainingBoost) {
        this.mRemainingBoost = remainingBoost;
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
