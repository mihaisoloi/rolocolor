package co.techsylvania.rolocolor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import co.techsylvania.rolocolor.R;
import jp.epson.moverio.bt200.DisplayControl;
import jp.epson.moverio.bt200.SensorControl;

/**
 * Created by tavi on 22/05/16.
 */
public class SettingsActivity extends ActivityBase {
    public static final String tag = SettingsActivity.class.getSimpleName();
    private SeekBar mSeekBar_backlight;
    private SensorControl mSensorControl;
    private DisplayControl mDisplayControl;
    private ToggleButton mToggleButton_sensor;
    private TextView tvColorBlindnessAssist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mSensorControl = new SensorControl(this);
        mDisplayControl = new DisplayControl(this);

        mSeekBar_backlight = (SeekBar) findViewById(R.id.seekBrightness);
        mSeekBar_backlight.setMax(20);
        mSeekBar_backlight.setProgress(mDisplayControl.getBacklight());
        mSeekBar_backlight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                Log.d(tag, "Set LCD back-light level:" + progress);
                mDisplayControl.setBacklight(progress);
            }
        });
        mToggleButton_sensor = (ToggleButton) findViewById(R.id.toggle);
        if (SensorControl.SENSOR_MODE_CONTROLLER == mSensorControl.getMode()) {
            mToggleButton_sensor.setChecked(true);
        } else {
            mToggleButton_sensor.setChecked(false);
        }
        mToggleButton_sensor.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                if (arg1) {
                    Log.d(tag, "set sensor of controller.");
                    mSensorControl.setMode(SensorControl.SENSOR_MODE_CONTROLLER);
                } else {
                    Log.d(tag, "set sensor of headset.");
                    mSensorControl.setMode(SensorControl.SENSOR_MODE_HEADSET);
                }
            }
        });

        tvColorBlindnessAssist = (TextView) findViewById(R.id.tvColorBlindnessAssist);
        tvColorBlindnessAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityAssist = new Intent(getApplicationContext(), ActivityAssist.class);
            }
        });
    }
}
