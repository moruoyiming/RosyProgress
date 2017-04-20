package com.calypso.rosyprogresssample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.calypso.rosyprogress.RosyProgress;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RosyProgress rosyProgress;
    private SeekBar mSeekBarRadius;
    private SeekBar mSeekBarWidth;
    private SeekBar mSeekBarZ;
    private SeekBar mSeekBarTime;
    private Button mStart;
    private Switch mSwitchShowHide;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        rosyProgress = (RosyProgress) findViewById(R.id.rosyProgress);
        rosyProgress.setValue(80f);
        mSeekBarRadius = (SeekBar) findViewById(R.id.seekBarRadius);
        mSeekBarWidth = (SeekBar) findViewById(R.id.seekBarWidth);
        mSeekBarZ = (SeekBar) findViewById(R.id.seekBarZ);
        mSeekBarTime = (SeekBar) findViewById(R.id.seekBarTime);
        mStart = (Button) findViewById(R.id.start);
        mSwitchShowHide = (Switch) findViewById(R.id.switchShowHide);
        mSeekBarRadius.setProgress(0);
        mSeekBarZ.setProgress(0);
        initLinstener();
    }

    private void initLinstener() {
        /**
         * 设置旋转事件间隔
         */
        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                long time = (long) (1.0f * progress / seekBar.getMax() * 10000 + 1000);
                if (time <= 1000)
                    time = 1000;
                rosyProgress.setmAnimatorTime(time);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        /**
         * 设置子半径R
         */
        mSeekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float r = progress * 30 / seekBar.getMax();
                Log.i("MainActivity", "progress   " + progress + "  r    " + "    " + progress / seekBar.getMax() + "   " + seekBar.getMax());
                rosyProgress.setmCircleRadius(160 + (int) r);
                rosyProgress.postInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mSeekBarWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rosyProgress.setmCircleStrokeWidth(progress * 10 / 100);
                rosyProgress.postInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        /**
         * Z轴旋转
         */
        mSeekBarZ.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                carrousel.setRotationZ(progress - seekBar.getMax() / 2);
//                carrousel.refreshLayout();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        /**
         * 设置是否自动旋转
         */
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random(100);
                rosyProgress.setValue(80f);
            }
        });
        /**
         * 设置向左还是向右自动旋转
         */
        mSwitchShowHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rosyProgress.setmIsShowSmall(isChecked);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
