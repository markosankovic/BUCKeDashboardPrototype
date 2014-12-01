package com.synapticon.bucke.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

public class SoundCard extends BaseCard {

    public SoundCard(Context context) {
        super(context);
    }

    public SoundCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SoundCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Bitmap getOnBitmap() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ca_sound_on);
    }

    @Override
    protected Bitmap getOffBitmap() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ca_sound_off);
    }
}
