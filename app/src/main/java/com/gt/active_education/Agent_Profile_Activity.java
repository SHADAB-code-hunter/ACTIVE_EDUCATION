package com.gt.active_education;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.Agent_Profile_VP_Adpter;
import callbacks.Log_Out_Listener;
import nav_tabbar.NavigationTabBar;
import utilities.App_Static_Method;

/**
 * Created by GT on 8/12/2017.
 */

public class Agent_Profile_Activity extends AppCompatActivity implements Log_Out_Listener, View.OnClickListener {
    TextView tv_logout,id_back_btn_quiz;
    ProgressDialog progressDialog;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_agent_profile);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            id_back_btn_quiz=(TextView)findViewById(R.id.id_back_btn_quiz);id_back_btn_quiz.setOnClickListener(this);
             tv_logout=(TextView)findViewById(R.id.tv_logout);
             tv_logout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     progressDialog = new ProgressDialog(Agent_Profile_Activity.this);
                     progressDialog.setCancelable(true);
                     progressDialog.show();
                     progressDialog.setMessage(getString(R.string.LogOut));
                     new Thread(new Runnable() {
                         @Override
                         public void run() {
                             try {
                                 Thread.sleep(1500);

                                 App_Static_Method.logout(Agent_Profile_Activity.this);

                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                         }
                     }).start();
                 }
             });
            initUI();
        }

    private void initUI() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);

        Agent_Profile_VP_Adpter agentProfileVpAdpter=new Agent_Profile_VP_Adpter(getSupportFragmentManager());

        viewPager.setAdapter(agentProfileVpAdpter);
       /* final WrapContentViewPager wrapContentViewPager = (WrapContentViewPager) findViewById(R.id.vp_horizontal_ntb);

        wrapContentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                wrapContentViewPager.reMeasureCurrentPage(wrapContentViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Agent_Profile_VP_Adpter agentProfileVpAdpter=new Agent_Profile_VP_Adpter(getSupportFragmentManager());
        wrapContentViewPager.setAdapter(agentProfileVpAdpter);*/
/*
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(
                        getBaseContext()).inflate(R.layout.item_vp, null, false);

                final TextView txtPage = (TextView) view.findViewById(R.id.txt_vp_item_page);
                txtPage.setText(String.format("Page #%d", position));

                container.addView(view);
                return container;
            }
        });
*/

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_deal),
                        Color.parseColor(colors[2]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Deal")
                        //  .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_booking),
                        Color.parseColor(colors[5]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Seat Book")
                        // .badgeTitle("777")
                        .build()
        );
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

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_progress),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Progress")
                        //  .badgeTitle("icon")
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

    @Override
    public void on_logout(boolean bl) {
        if(bl)
        {
            progressDialog.cancel();

            this.finish();
        }else if(!bl){
            progressDialog.cancel();
            Toast.makeText(this, "Logout UnSuccessFull", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_back_btn_quiz:
                finish();
                break;
        }
    }
}
