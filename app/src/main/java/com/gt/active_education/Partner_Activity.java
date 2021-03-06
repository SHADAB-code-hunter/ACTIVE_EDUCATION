package com.gt.active_education;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import adapter.Partner_VP_Adpter;
import nav_tabbar.NavigationTabBar;

public class Partner_Activity extends AppCompatActivity {//implements Upcoming_List_LoadedListener {
    private RecyclerView id_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        set_partner_layout_apger();

    }

    private void set_partner_layout_apger() {

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        Partner_VP_Adpter agentProfileVpAdpter=new Partner_VP_Adpter(getSupportFragmentManager());
        viewPager.setAdapter(agentProfileVpAdpter);
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_edt_profile),
                        Color.parseColor(colors[4]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Profile")
                        // .badgeTitle("777")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_target),
                        Color.parseColor(colors[0]))
                        //   .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Target")
                        // .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_calculator),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Account")
                        //  .badgeTitle("with")
                        .build()
        );


        navigationTabBar.setModels(models);

        navigationTabBar.setViewPager(viewPager, 0);
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

}
