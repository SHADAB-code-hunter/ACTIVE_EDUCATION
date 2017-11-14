package com.gt.active_education;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import callbacks.Frg_Act_Listener;
import fragment.Partner_Gallery;
import fragment.Partner_Regisration_Frg;
import fragment.Partner_Seat_Submission;
import fragment.Target_fragment;
import utilities.App_Static_Method;
import utilities.UrlEndpoints;

/**
 * Created by GT on 10/5/2017.
 */

public class Target_Circle_Activity extends AppCompatActivity implements View.OnClickListener, Frg_Act_Listener {


    private TextView id_profile;
    private ExpandableRelativeLayout layout1;
    private LinearLayout id_menu_btn;
    private LinearLayout id_linear_abtus_menu,id_linear_gallery_menu,id_linear_prfl_menu,id_linear_acc_menu,id_linear_seat_menu,id_linear_trgt_menu;
    private FrameLayout id_fragment_partner;
    private TextView id_fragment_title;
    private TextView id_clg_name;
    private ImageView id_img_college;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_circle_layout);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  id_profile=(TextView)findViewById(R.id.id_profile); expandableLayout1
        id_fragment_title=(TextView)findViewById(R.id.id_fragment_title);
        id_img_college=(ImageView)findViewById(R.id.id_img_college);
        id_menu_btn=(LinearLayout)findViewById(R.id.id_menu_btn);
        layout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);layout1.collapse();

       //  id_profile.setOnClickListener(this);
        id_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.toggle();
              /*  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/
            }
        });
        id_fragment_partner=(FrameLayout)findViewById(R.id.id_fragment_partner);
        id_linear_trgt_menu=(LinearLayout)findViewById(R.id.id_linear_trgt_menu);id_linear_trgt_menu.setOnClickListener(this);
        id_linear_abtus_menu=(LinearLayout)findViewById(R.id.id_linear_logout_menu);id_linear_abtus_menu.setOnClickListener(this);
        id_linear_gallery_menu=(LinearLayout)findViewById(R.id.id_linear_gallery_menu);id_linear_gallery_menu.setOnClickListener(this);
        id_linear_prfl_menu=(LinearLayout)findViewById(R.id.id_linear_prfl_menu);id_linear_prfl_menu.setOnClickListener(this);
        id_linear_acc_menu=(LinearLayout)findViewById(R.id.id_linear_acc_menu);id_linear_acc_menu.setOnClickListener(this);
        id_linear_seat_menu=(LinearLayout)findViewById(R.id.id_linear_seat_menu);id_linear_seat_menu.setOnClickListener(this);
        id_clg_name=(TextView)findViewById(R.id.id_clg_name);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        id_fragment_title.setText("Target");
        ft.replace(R.id.id_fragment_partner, new Target_fragment());ft.commit();

        id_clg_name.setText(App_Static_Method.get_agent_session_data("name"));
        Glide.with(Target_Circle_Activity.this)
                .load(UrlEndpoints.SEAT_PROVIDER_PRFL_IMG+App_Static_Method.get_agent_session_data("image"))
                .into(id_img_college);

    }


    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (v.getId())
        {
            case R.id.id_linear_logout_menu:
                layout1.toggle();
                set_logout();
               // id_fragment_title.setText("Log Us");
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

    private void set_logout() {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void on_frg_act_linking() {

    }
}
