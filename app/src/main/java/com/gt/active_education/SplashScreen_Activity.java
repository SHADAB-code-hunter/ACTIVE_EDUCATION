package com.gt.active_education;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import adapter.Splash_Banner_Adapter;
import rubber_indicator.RubberIndicator;
import utilities.ConnectionCheck;
import utilities.MyApplication;
import utilities.UpdateValues;
/*
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import utilities.ConnectionCheck;
import utilities.UpdateValues;*/

/**
 * Created by GT on 3/24/2017.
 */

public class SplashScreen_Activity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 0;
    private SharedPreferences sharedPref;
    private ConnectionCheck cCheck;
    private MediaPlayer mPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Fabric.with(this, new Crashlytics());
        //   setContentView(R.layout.activity_splash);

       /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription("AppName", Bitmap.createBitmap(R.drawable.ic_new_logo) ), ContextCompat.getColor(getApplicationContext()., R.color.colorPrimaryDark));
        }*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        boolean hasMenuKey = ViewConfiguration.get(getApplicationContext()).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if(!hasMenuKey && !hasBackKey) {
            // Do whatever you need to do, this device has a navigation bar
            // Toast.makeText(this, "yes!!!!!", Toast.LENGTH_SHORT).show();
            SplashScreen_Activity.this.setTheme(R.style.SplashTheme_Full);
            WindowManager.LayoutParams params = this.getWindow().getAttributes();
            // params.y = 100;
            params.verticalMargin=100;
            this.getWindow().setAttributes(params);
        }else {
            // Toast.makeText(this, "No!!!!!", Toast.LENGTH_SHORT).show();
            SplashScreen_Activity.this.setTheme(R.style.SplashTheme_Full);
        }
        cCheck = new ConnectionCheck(getApplicationContext());
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreen_Activity.this, DashBoard_Activity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
   /* private void swap_page_audio_call() {
        mPlay = MediaPlayer.create(SplashScreen_Activity.this, R.raw.swap_page);
        mPlay.start();
    }
    private void common_click_sound() {

        mPlay = MediaPlayer.create(SplashScreen_Activity.this, R.raw.dialog_aud);
        mPlay.start();
    }*/
}