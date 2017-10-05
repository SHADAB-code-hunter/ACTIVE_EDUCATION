package com.gt.active_education;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import utilities.BatteryProgressView;

/**
 * Created by GT on 10/5/2017.
 */

public class Target_Circle_Activity extends AppCompatActivity {


    private BatteryProgressView progress1,progress2,progress3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_circle_layout);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progress1= (BatteryProgressView) findViewById(R.id.progress1);
        progress2= (BatteryProgressView) findViewById(R.id.progress2);
        progress3= (BatteryProgressView) findViewById(R.id.progress3);

        set_progress(progress1,R.color.circle_progress_color1);
        set_progress(progress2,R.color.circle_progress_color2);
        set_progress(progress3,R.color.circle_progress_color3);

    }

    private void set_progress(BatteryProgressView progress, int circle_progress_color1) {
        Handler handler;
        progress.setProgress(66,circle_progress_color1);//progress.setProgressColor(circle_progress_color1);
        handler=new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setProgress(63,circle_progress_color1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.setProgress(77, circle_progress_color1);

                    }
                },1000);
            }
        },2000);
    }
}
