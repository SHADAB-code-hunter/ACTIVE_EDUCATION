package com.gt.active_education;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gt.active_education.R;

import Agent_Profile.HomeFragment;

public class HomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main_tab_layout);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		FragmentManager fragmentManager=getSupportFragmentManager();
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.realtabcontent,new HomeFragment()).commit();
	}

}
