package com.gt.active_education;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import tab_bar.AwesomeTabBar;
import tab_bar.My_Prpfile_VP_Adapter;

/**
 * Created by GT on 8/5/2017.
 */

public class MyProfile_Activity  extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        AwesomeTabBar tabBar=(AwesomeTabBar)findViewById(R.id.tabBar);
        ViewPager viewpager=(ViewPager)findViewById(R.id.pager);
        viewpager.setAdapter(new My_Prpfile_VP_Adapter(getSupportFragmentManager()));
        tabBar.setupWithViewPager(viewpager);
    }
}
