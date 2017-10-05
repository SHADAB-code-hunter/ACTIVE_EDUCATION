package com.gt.active_education;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by GT on 7/7/2017.
 */

public class SignUp_Succesfull_Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageView verify_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_success);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        verify_btn=(ImageView)findViewById(R.id.button_check);
        verify_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.button_check:

                startActivity(new Intent(getApplicationContext(),DashBoard_Activity.class));


                break;
        }
    }
}
