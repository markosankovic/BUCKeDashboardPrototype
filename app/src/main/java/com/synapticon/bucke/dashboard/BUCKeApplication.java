package com.synapticon.bucke.dashboard;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BUCKeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * @see https://github.com/chrisjenx/Calligraphy#configuration
         */
        CalligraphyConfig.initDefault("fonts/HelveticaNeueLTStd-Lt.otf", R.attr.fontPath);
    }
}
