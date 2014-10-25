package com.synapticon.bucke.dashboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends Activity {

    private static final String TAG = DashboardActivity.class.getSimpleName();

    private SeekBar mSpeedBar;
    private BackgroundRadialGradient mBackgroundRadialGradient;
    private SpeedBox mSpeedBox;

    private LeftBlinker mLeftBlinker;
    private ToggleButton mLeftBlinkerToggle;

    private RightBlinker mRightBlinker;
    private ToggleButton mRightBlinkerToggle;

    private HighBeamCard mHighBeamCard;
    private ImageView mHighBeamIcon;
    private ToggleButton mHighBeamToggle;

    private CameraCard mCameraCard;
    private ImageView mCameraIcon;
    private ToggleButton mCameraToggle;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mBackgroundRadialGradient = (BackgroundRadialGradient) findViewById(R.id.color_gradient_circle_button);
        mSpeedBox = (SpeedBox) findViewById(R.id.speed_rectangle);

        mLeftBlinker = (LeftBlinker) findViewById(R.id.left_blinker);
        mLeftBlinkerToggle = (ToggleButton) findViewById(R.id.left_blinker_toggle);
        mLeftBlinkerToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mLeftBlinker.setBlink(b);
            }
        });

        mRightBlinker = (RightBlinker) findViewById(R.id.right_blinker);
        mRightBlinkerToggle = (ToggleButton) findViewById(R.id.right_blinker_toggle);
        mRightBlinkerToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mRightBlinker.setBlink(b);
            }
        });

        mHighBeamCard = (HighBeamCard) findViewById(R.id.high_beam_card);
        mHighBeamIcon = (ImageView) findViewById(R.id.high_beam_icon);
        mHighBeamToggle = (ToggleButton) findViewById(R.id.high_beam_toggle);
        mHighBeamToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mHighBeamCard.setOn(b);
                toggleDrawableState(mHighBeamIcon.getDrawable(), b);
            }
        });

        mCameraCard = (CameraCard) findViewById(R.id.camera_card);
        mCameraIcon = (ImageView) findViewById(R.id.camera_icon);
        mCameraToggle = (ToggleButton) findViewById(R.id.camera_toggle);
        mCameraToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCameraCard.setOn(b);
                toggleDrawableState(mCameraIcon.getDrawable(), b);
            }
        });

        mSpeedBar = (SeekBar) findViewById(R.id.speed_bar);
        mSpeedBar.setOnSeekBarChangeListener(onSpeedBarChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private SeekBar.OnSeekBarChangeListener onSpeedBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mBackgroundRadialGradient.setSpeed(i);
            mSpeedBox.setSpeed(i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    /**
     * Animate drawable state: high beam, action camera and sounds
     *
     * @param drawable
     * @param state
     */
    void toggleDrawableState(Drawable drawable, boolean state) {
        TransitionDrawable transitionDrawable = (TransitionDrawable) drawable;
        if (state) {
            transitionDrawable.startTransition(600);
        } else {
            transitionDrawable.reverseTransition(600);
        }
    }
}
