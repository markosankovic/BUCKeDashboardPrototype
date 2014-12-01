package com.synapticon.bucke.dashboard;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class HeadlightCard extends BaseCard {

    public HeadlightCard(Context context) {
        super(context);
    }

    public HeadlightCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadlightCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Bitmap getOnBitmap() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ca_high_beam_on);
    }

    @Override
    protected Bitmap getOffBitmap() {
        return BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ca_high_beam_off);
    }
}
