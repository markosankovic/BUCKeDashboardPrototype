package com.synapticon.bucke.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

public class ReducedPowerCard extends BaseCard {

    public ReducedPowerCard(Context context) {
        super(context);
    }

    public ReducedPowerCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReducedPowerCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        getLabelTextPaint().setTextSize(48.0f);
    }

    @Override
    protected Bitmap getOnBitmap() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ca_reduced_power_on);
    }

    @Override
    protected Bitmap getOffBitmap() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ca_reduced_power_on);
    }

    @Override
    protected String getOnText() {
        return "reduced power";
    }

    @Override
    protected String getOffText() {
        return "reduced power";
    }

    @Override
    protected int getLabelY() {
        return 254;
    }
}
