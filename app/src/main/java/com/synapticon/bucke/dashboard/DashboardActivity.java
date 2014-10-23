package com.synapticon.bucke.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DashboardActivity extends Activity {

    private static final String TAG = DashboardActivity.class.getSimpleName();

    private SeekBar mSpeedBar;
    private BackgroundRadialGradient mBackgroundRadialGradient;
    private SpeedRectangle mSpeedRectangle;
    private TextView mSpeedText;

    private Blinker mLeftBlinker;
    private ToggleButton mLeftBlinkerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mBackgroundRadialGradient = (BackgroundRadialGradient) findViewById(R.id.color_gradient_circle_button);
        mSpeedRectangle = (SpeedRectangle) findViewById(R.id.speed_rectangle);
        mSpeedText = (TextView) findViewById(R.id.speed_text);

        mLeftBlinker = (Blinker) findViewById(R.id.left_blinker);
        mLeftBlinkerToggle = (ToggleButton) findViewById(R.id.left_blinker_toggle);
        mLeftBlinkerToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mLeftBlinker.setBlink(b);
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
            mSpeedRectangle.setSpeed(i);
            mSpeedText.setText(String.valueOf(i));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
}
