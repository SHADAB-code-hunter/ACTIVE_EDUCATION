package com.gt.active_education;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import callbacks.SignUp_Pager_Swape_Listener;

/**
 * Created by GT on 7/7/2017.
 */

public class Num_Verify_Activity extends AppCompatActivity implements View.OnClickListener, SignUp_Pager_Swape_Listener {
    private Button  verify_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_verify);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button verify_btn=(Button)findViewById(R.id.id_verify_btn);
        verify_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.id_verify_btn:

                startActivity(new Intent(getApplicationContext(),OTP_Verification_Activity.class));
                break;
        }
    }

    @Override
    public void onPager_swap_method(String login_page) {
        Log.d("onPage_swap_method","onPage_swap_method");
    }
}
