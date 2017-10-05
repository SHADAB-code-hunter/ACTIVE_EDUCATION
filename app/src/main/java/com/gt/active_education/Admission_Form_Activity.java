package com.gt.active_education;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import adapter.Admission_Form_Adapter;
import callbacks.Pager_Change_listener;
import callbacks.SignUp_Pager_Swape_Listener;
import horizontal_step_inditor.HorizontalStepView;
import horizontal_step_inditor.StepBean;
import nav_tabbar.NavigationTabBar;
import utilities.NonSwipeableViewPager;

/**
 * Created by GT on 8/24/2017.
 */

public class Admission_Form_Activity extends AppCompatActivity implements Pager_Change_listener{

    private HorizontalStepView stepView;
    private ViewPager main_viewPager;
    private Bundle bundle=new Bundle();
    private  Admission_Form_Adapter newAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_form);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(!getIntent().equals(null))
        {
            bundle = getIntent().getExtras();
        }
        main_viewPager=(ViewPager)findViewById(R.id.id_viewpager_quiz_question);
        //   set_progress_indicator();

         if(getIntent().equals(null))
        {
             newAdapter = new Admission_Form_Adapter(getSupportFragmentManager(), Admission_Form_Activity.this);
        }else if(!getIntent().equals(null)) {
            newAdapter = new Admission_Form_Adapter(getSupportFragmentManager(), Admission_Form_Activity.this, bundle);
        }

        main_viewPager.setAdapter(newAdapter);
        main_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               // ((SignUp_Pager_Swape_Listener)Admission_Form_Activity.this).onPager_swap_method();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_edt_profile),
                        Color.parseColor(colors[4]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Personal detail")
                        // .badgeTitle("777")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_educational),
                        Color.parseColor(colors[2]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Educational Detail")
                        //  .badgeTitle("state")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_card_pay),
                        Color.parseColor(colors[0]))
                        //   .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Make Payment")
                        // .badgeTitle("NTB")
                        .build()
        );


        navigationTabBar.setModels(models);

        navigationTabBar.setViewPager(main_viewPager, 0);
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

    }

    @Override
    public void on_pager_change(int i) {
        //main_viewPager.getCurrentItem()
        Log.d("hdjhf",""+main_viewPager.getCurrentItem());


        main_viewPager.setCurrentItem((main_viewPager.getCurrentItem())+1);
    }
}
