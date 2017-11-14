package com.gt.active_education;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import callbacks.Log_Out_Listener;
import callbacks.Profile_Image_Listener;
import nav_tabbar.NavigationTabBar;
import tab_bar.User_Prfl_VP_Adapter;
import utilities.App_Static_Method;

/**
 * Created by GT on 8/5/2017.
 */

public class User_Profile_Activity extends AppCompatActivity implements View.OnClickListener, Profile_Image_Listener , Log_Out_Listener {

    TextView tv_back,toolbar_skip;
    private ImageView id_image_profile;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        ViewPager viewpager=(ViewPager)findViewById(R.id.pager);
        tv_back=(TextView)findViewById(R.id.id_back_btn_quiz);tv_back.setOnClickListener(this);
        toolbar_skip=(TextView)findViewById(R.id.toolbar_skip);toolbar_skip.setOnClickListener(this);
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_booking),
                        Color.parseColor(colors[2]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("My Booking")
                        //  .badgeTitle("state")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_edt_profile),
                        Color.parseColor(colors[4]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("EDIT PROFILE")
                        // .badgeTitle("777")
                        .build()
        );
       /* models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_bus_acc),
                        Color.parseColor(colors[0]))
                        //   .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Business Account")
                        // .badgeTitle("NTB")
                        .build()
        );*/


        viewpager.setAdapter(new User_Prfl_VP_Adapter(getSupportFragmentManager()));
        //tabBar.setupWithViewPager(viewpager);
        navigationTabBar.setModels(models);

        navigationTabBar.setViewPager(viewpager, 1);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);


        id_image_profile=(ImageView)findViewById(R.id.id_image_profile);
        App_Static_Method.set_profile_img(User_Profile_Activity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_back_btn_quiz:
                finish();
                break;

          /*  case R.id.toolbar_skip:
                progressDialog = new ProgressDialog(this);
                progressDialog.setCancelable(true);
                progressDialog.show();
                progressDialog.setMessage(getString(R.string.LogOut));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);

                            App_Static_Method.logout(User_Profile_Activity.this);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;*/

        }
    }
    @Override
    public void on_profile_image_listener(String s) {

        if(s!=null) {
            Log.d("url_image", "" + s);
            Glide.with(User_Profile_Activity.this).load(s)
                    .thumbnail(0.5f)
                    .into(id_image_profile);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(progressDialog!=null)
        progressDialog.cancel();
    }

    @Override
    public void on_logout(boolean bl) {
        if(bl)
        {
            progressDialog.cancel();
            finish();
        }else if(!bl){
            Log.d("notlogout","dffffff");
            progressDialog.cancel();
        }
    }
}
