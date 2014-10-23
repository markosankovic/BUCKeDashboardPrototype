package com.synapticon.bucke.dashboard;

import android.graphics.Color;
import android.util.Log;

public class SpeedHSV {

    private static SpeedHSV instance = null;

    private static final String TAG = SpeedHSV.class.getSimpleName();

    private int colorStop1 = Color.parseColor("#FF3293D4");
    private float[] colorStop1HSV = new float[3];
    private int colorStop2 = Color.parseColor("#FF19AF91");
    private float[] colorStop2HSV = new float[3];
    private int colorStop3 = Color.parseColor("#FFEC8D1A");
    private float[] colorStop3HSV = new float[3];
    private int colorStop4 = Color.parseColor("#FFDC4637");
    private float[] colorStop4HSV = new float[3];

    protected SpeedHSV() {
        // no instantiation
    }

    public static SpeedHSV getInstance() {
        if (instance == null) {
            instance = new SpeedHSV();
            instance.init();
        }
        return instance;
    }

    private void init() {
        Color.colorToHSV(colorStop1, colorStop1HSV);
        Color.colorToHSV(colorStop2, colorStop2HSV);
        Color.colorToHSV(colorStop3, colorStop3HSV);
        Color.colorToHSV(colorStop4, colorStop4HSV);
    }

    public float[] hsv(float speed) {
        final float[] hsv = new float[3];

        if (speed < 14) {
            float fract = speed / 14;
            Log.i(TAG, String.valueOf(fract));
            hsv[0] = colorStop1HSV[0] + (colorStop2HSV[0] - colorStop1HSV[0]) * fract;
            hsv[1] = colorStop1HSV[1] + (colorStop2HSV[1] - colorStop1HSV[1]) * fract;
            hsv[2] = colorStop1HSV[2] + (colorStop2HSV[2] - colorStop1HSV[2]) * fract;
        } else if (speed >= 14 && speed < 32) {
            float fract = (speed - 14) / 18;
            Log.i(TAG, String.valueOf(fract));
            hsv[0] = colorStop2HSV[0] + (colorStop3HSV[0] - colorStop2HSV[0]) * fract;
            hsv[1] = colorStop2HSV[1] + (colorStop3HSV[1] - colorStop2HSV[1]) * fract;
            hsv[2] = colorStop2HSV[2] + (colorStop3HSV[2] - colorStop2HSV[2]) * fract;
        } else {
            float fract = (speed - 32) / 18;
            Log.i(TAG, String.valueOf(fract));
            hsv[0] = colorStop3HSV[0] + (colorStop4HSV[0] - colorStop3HSV[0]) * fract;
            hsv[1] = colorStop3HSV[1] + (colorStop4HSV[1] - colorStop3HSV[1]) * fract;
            hsv[2] = colorStop3HSV[2] + (colorStop4HSV[2] - colorStop3HSV[2]) * fract;
        }

        return hsv;
    }
}
