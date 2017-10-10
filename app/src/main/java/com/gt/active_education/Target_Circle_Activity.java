package com.gt.active_education;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import fragment.Partner_Detail_Frag;
import fragment.Partner_Gallery;
import fragment.Partner_Regisration_Frg;
import fragment.Partner_Seat_Submission;
import fragment.Target_fragment;
import utilities.BatteryProgressView;

/**
 * Created by GT on 10/5/2017.
 */

public class Target_Circle_Activity extends AppCompatActivity implements View.OnClickListener {


    private TextView id_profile;
    private ExpandableRelativeLayout layout1;
    private LinearLayout id_menu_btn;
    private LinearLayout id_linear_abtus_menu,id_linear_gallery_menu,id_linear_prfl_menu,id_linear_acc_menu,id_linear_seat_menu,id_linear_trgt_menu;
    private FrameLayout id_fragment_partner;
    private TextView id_fragment_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_circle_layout);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  id_profile=(TextView)findViewById(R.id.id_profile); expandableLayout1
        id_fragment_title=(TextView)findViewById(R.id.id_fragment_title);
        id_menu_btn=(LinearLayout)findViewById(R.id.id_menu_btn);
        layout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);layout1.collapse();

       //  id_profile.setOnClickListener(this);
        id_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.toggle();
            }
        });
        id_fragment_partner=(FrameLayout)findViewById(R.id.id_fragment_partner);
        id_linear_trgt_menu=(LinearLayout)findViewById(R.id.id_linear_trgt_menu);id_linear_trgt_menu.setOnClickListener(this);
        id_linear_abtus_menu=(LinearLayout)findViewById(R.id.id_linear_abtus_menu);id_linear_abtus_menu.setOnClickListener(this);
        id_linear_gallery_menu=(LinearLayout)findViewById(R.id.id_linear_gallery_menu);id_linear_gallery_menu.setOnClickListener(this);
        id_linear_prfl_menu=(LinearLayout)findViewById(R.id.id_linear_prfl_menu);id_linear_prfl_menu.setOnClickListener(this);
        id_linear_acc_menu=(LinearLayout)findViewById(R.id.id_linear_acc_menu);id_linear_acc_menu.setOnClickListener(this);
        id_linear_seat_menu=(LinearLayout)findViewById(R.id.id_linear_seat_menu);id_linear_seat_menu.setOnClickListener(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        id_fragment_title.setText("Target");
        ft.replace(R.id.id_fragment_partner, new Target_fragment());
        ft.commit();

    }


    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (v.getId())
        {
            case R.id.id_linear_abtus_menu:
                layout1.toggle();
                id_fragment_title.setText("About Us");
                ft.replace(R.id.id_fragment_partner, new Target_fragment());
                ft.commit();
                break;
            case R.id.id_linear_trgt_menu:
                layout1.toggle();
                id_fragment_title.setText("Target");
                ft.replace(R.id.id_fragment_partner, new Target_fragment());
                ft.commit();
                break;
            case R.id.id_linear_gallery_menu:
                id_fragment_title.setText("Gallery");
                layout1.toggle();
                ft.replace(R.id.id_fragment_partner, new Partner_Gallery());
                ft.commit();
                break;
            case R.id.id_linear_prfl_menu:
                id_fragment_title.setText("Profile");layout1.toggle();
                ft.replace(R.id.id_fragment_partner, new Partner_Regisration_Frg());
                ft.commit();
                break;
            case R.id.id_linear_acc_menu:
                id_fragment_title.setText("Account");layout1.toggle();
                ft.replace(R.id.id_fragment_partner, new Target_fragment());
                ft.commit();
                break;
            case R.id.id_linear_seat_menu:
                id_fragment_title.setText("Seat Submission");layout1.toggle();
                ft.replace(R.id.id_fragment_partner, new Partner_Seat_Submission());
                ft.commit();
                break;
        }
    }
}
