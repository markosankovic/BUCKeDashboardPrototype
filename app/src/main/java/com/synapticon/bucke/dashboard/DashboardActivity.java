package com.synapticon.bucke.dashboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.ToggleButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends Activity {

    private static final String TAG = DashboardActivity.class.getSimpleName();

    private boolean mShowToggleButtons = true;

    private ToggleButton mUiToggle;

    private SeekBar mSpeedBar;
    private SeekBar mBatteryBar;
    private SeekBar mBoostBar;

    private SpeedBox mSpeedBox;
    private BatteryBox mBatteryBox;
    private BoostBox mBoostBox;

    private BackgroundRadialGradient mBackgroundRadialGradient;

    private LeftBlinker mLeftBlinker;
    private ToggleButton mLeftBlinkerToggle;

    private ToggleButton mModeToggle;

    private RightBlinker mRightBlinker;
    private ToggleButton mRightBlinkerToggle;

    private HighBeamCard mHighBeamCard;
    private ImageView mHighBeamIcon;
    private ToggleButton mHighBeamToggle;

    private CameraCard mCameraCard;
    private ImageView mCameraIcon;
    private ToggleButton mCameraToggle;

    private SoundCard mSoundCard;
    private ImageView mSoundIcon;
    private ToggleButton mSoundToggle;

    private ReducedPowerCard mReducedPowerCard;
    private ToggleButton mReducedPowerToggle;

    private InfoBox mInfoBox;

    private TextView mBatteryChargeText;

    private TextClock mTextClockStandstill;
    private TextClock mTextClockDriving;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mSpeedBox = (SpeedBox) findViewById(R.id.speed_box);
        mBatteryBox = (BatteryBox) findViewById(R.id.battery_box);
        mBoostBox = (BoostBox) findViewById(R.id.boost_box);

        mBackgroundRadialGradient = (BackgroundRadialGradient) findViewById(R.id.color_gradient_circle_button);

        mInfoBox = (InfoBox) findViewById(R.id.info_box);

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

        mTextClockStandstill = (TextClock) findViewById(R.id.text_clock_standstill);
        mTextClockDriving = (TextClock) findViewById(R.id.text_clock_driving);

        mModeToggle = (ToggleButton) findViewById(R.id.mode_toggle);
        mModeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mBatteryBox.setDriving(b);
                mSpeedBox.setDriving(b);
                mInfoBox.setDriving(b);

                if (b) {
                    mTextClockStandstill.setVisibility(View.GONE);
                    mTextClockDriving.setVisibility(View.VISIBLE);
                } else {
                    mTextClockStandstill.setVisibility(View.VISIBLE);
                    mTextClockDriving.setVisibility(View.GONE);
                }
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

        mSoundCard = (SoundCard) findViewById(R.id.sound_card);
        mSoundIcon = (ImageView) findViewById(R.id.sound_icon);
        mSoundToggle = (ToggleButton) findViewById(R.id.sound_toggle);
        mSoundToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mSoundCard.setOn(b);
                toggleDrawableState(mSoundIcon.getDrawable(), b);
            }
        });

        mReducedPowerCard = (ReducedPowerCard) findViewById(R.id.reduced_power_card);
        mReducedPowerToggle = (ToggleButton) findViewById(R.id.reduced_power_toggle);
        mReducedPowerToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mReducedPowerCard.setOn(b);
            }
        });

        mSpeedBar = (SeekBar) findViewById(R.id.speed_bar);
        mSpeedBar.setOnSeekBarChangeListener(onSpeedBarChangeListener);

        mBatteryBar = (SeekBar) findViewById(R.id.battery_bar);
        mBatteryBar.setOnSeekBarChangeListener(onBatteryBarChangeListener);

        mBoostBar = (SeekBar) findViewById(R.id.boost_bar);
        mBoostBar.setOnSeekBarChangeListener(onBoostBarChangeListener);

        mBatteryChargeText = (TextView) findViewById(R.id.battery_charge_text);
        mBatteryChargeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                try {
                    int n = Integer.valueOf(charSequence.toString());
                    mBatteryBox.setBatteryCharge(n);
                } catch (NumberFormatException e) {
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mUiToggle = (ToggleButton) findViewById(R.id.ui_toggle);
        mUiToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mShowToggleButtons = b;
                actualizeToggleButtons();
            }
        });

        actualizeToggleButtons();
    }

    private void actualizeToggleButtons() {
        if (mShowToggleButtons) {
            mLeftBlinkerToggle.setVisibility(View.VISIBLE);
            mHighBeamToggle.setVisibility(View.VISIBLE);
            mCameraToggle.setVisibility(View.VISIBLE);
            mSoundToggle.setVisibility(View.VISIBLE);
            mReducedPowerToggle.setVisibility(View.VISIBLE);
            mModeToggle.setVisibility(View.VISIBLE);
            mRightBlinkerToggle.setVisibility(View.VISIBLE);
            mSpeedBar.setVisibility(View.VISIBLE);
            mBatteryBar.setVisibility(View.VISIBLE);
            mBoostBar.setVisibility(View.VISIBLE);
            mBatteryChargeText.setVisibility(View.VISIBLE);
        } else {
            mLeftBlinkerToggle.setVisibility(View.GONE);
            mHighBeamToggle.setVisibility(View.GONE);
            mCameraToggle.setVisibility(View.GONE);
            mSoundToggle.setVisibility(View.GONE);
            mReducedPowerToggle.setVisibility(View.GONE);
            mModeToggle.setVisibility(View.GONE);
            mRightBlinkerToggle.setVisibility(View.GONE);
            mSpeedBar.setVisibility(View.GONE);
            mBatteryBar.setVisibility(View.GONE);
            mBoostBar.setVisibility(View.GONE);
            mBatteryChargeText.setVisibility(View.GONE);
        }
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

    private SeekBar.OnSeekBarChangeListener onBatteryBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mBatteryBox.setBatteryStateOfCharge(i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private SeekBar.OnSeekBarChangeListener onBoostBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            mBoostBox.setBoost(i);
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
