package com.gt.active_education;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class About_Us_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String phoneNumber = "01294014664";

        findViewById(R.id.id_floating_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    String dial = "tel:" + phoneNumber;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(About_Us_Activity.this, "Permission Phone Call denied", Toast.LENGTH_SHORT).show();
                }
            }
        });

       findViewById(R.id.id_back_btn_quiz).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               finish();
           }
       });
    }
    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
