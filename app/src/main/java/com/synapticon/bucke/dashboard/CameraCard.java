package com.synapticon.bucke.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

public class CameraCard extends BaseCard {

    public CameraCard(Context context) {
        super(context);
    }

    public CameraCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Bitmap getOnBitmap() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ca_camera_on);
    }

    @Override
    protected Bitmap getOffBitmap() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ca_camera_off);
    }
}
