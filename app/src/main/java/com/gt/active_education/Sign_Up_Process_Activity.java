package com.gt.active_education;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import adapter.SignUp_Pager_Adapter;
import callbacks.SignUp_Pager_Swape_Listener;
import horizontal_step_inditor.HorizontalStepView;
import horizontal_step_inditor.StepBean;
import utilities.MyApplication;
import utilities.NonSwipeableViewPager;
import utilities.UpdateValues;

/**
 * Created by GT on 7/8/2017.
 */

public class Sign_Up_Process_Activity extends AppCompatActivity implements View.OnClickListener , SignUp_Pager_Swape_Listener{
    private NonSwipeableViewPager main_viewPager;
    private SignUp_Pager_Adapter newAdapter;
    private HorizontalStepView stepView;
    private String str_status;
//onPager_swap_method("Login_page");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_process);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        main_viewPager=(NonSwipeableViewPager)findViewById(R.id.id_viewpager_quiz_question);
        /*button_verify=(Button)findViewById(R.id.button_verify);
        button_verify.setOnClickListener(this);*/
        if (!getIntent().equals(null))
        {
            str_status=getIntent().getStringExtra("Status");
        }
        newAdapter=new SignUp_Pager_Adapter(getSupportFragmentManager(),Sign_Up_Process_Activity.this,str_status);
        main_viewPager.setAdapter(newAdapter);
        main_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               /* if(position==mDailyQuizList.size()-1)
                {
                    //Log.d("thraed_q",""+position+"  "+mDailyQuizList.size());
                    Q_customHandler.removeCallbacks(Q_updateTimerThread, 0);
                    Q_startTime = 0;
                    Q_timeInMilliseconds = 0;
                    Q_updatedTime = 0;
                    Q_timeSwapBuff = 0;
                    Q_updateTimerThread=null;
                    start_PQ_Timer();
                }else if(position>0 && position<mDailyQuizList.size()-1) {
                    //Log.d("thraed_sd",""+position+"  "+mDailyQuizList.size());
                    Q_customHandler.removeCallbacks(Q_updateTimerThread, 0);
                    Q_startTime = 0;
                    Q_timeInMilliseconds = 0;
                    Q_updatedTime = 0;
                    Q_timeSwapBuff = 0;
                    start_PQ_Timer();
                }*/
                //  id_tv_q_no.setText(String.valueOf(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                //Log.d("staetpage",""+state);

            }
        });


     //   try {
            set_progress_indicator();
       /* }catch (Exception e)
        {
            Log.d("exception_detail",e.getMessage());
        }*/
    }

    private void set_progress_indicator() {

        try {
            stepView = (HorizontalStepView) findViewById(R.id.stepview);

        List<StepBean> stepsBeanList = new ArrayList<>();
     /* StepBean stepBean0 = new StepBean("1",0);
        StepBean stepBean1 = new StepBean("2",-1);
        StepBean stepBean2 = new StepBean("3",-1);
        StepBean stepBean3 = new StepBean("4",-1);
*/
            StepBean stepBean0 = new StepBean("1", 0);
            StepBean stepBean1 = new StepBean("2", -1);
            StepBean stepBean2 = new StepBean("3", -1);

            stepsBeanList.add(stepBean0);
            stepsBeanList.add(stepBean1);
            stepsBeanList.add(stepBean2);

            stepView.setStepViewTexts(stepsBeanList)
                    .setTextSize(10)//set textSize
                    .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                    .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                    .setStepViewComplectedTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsView text完成线的颜色
                    .setStepViewUnComplectedTextColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                    .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                    .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                    .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon
        }catch (Exception e)
        {
            Log.d("dexe",""+e.getMessage());
        }

    }
    public  void hide_input(){

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @Override
    public void onClick(View v) {
       /* switch (v.getId())
        {
            case R.id.button_verify:
                startActivity(new Intent(getApplicationContext(),SignUp_Succesfull_Activity.class));

                break;
        }*/
    }

    @Override
    public void onPager_swap_method(String login_page) {
        int i= main_viewPager.getCurrentItem();
        if(i>0){

        SharedPreferences sharedPreferences2 = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE, 0);
        Log.d("map_tostring",sharedPreferences2.getString("type","NA"));

        if(!sharedPreferences2.getString("type","NA").equals("NA")) {

            switch (sharedPreferences2.getString("type", "NA")) {

                case "agent":
                    startActivity(new Intent(Sign_Up_Process_Activity.this,Agent_login_Activity.class));
                    finish();
                    break;
                case "user":
                    startActivity(new Intent(Sign_Up_Process_Activity.this,Agent_login_Activity.class));
                    finish();
                    break;
                case "seater":
                    startActivity(new Intent(Sign_Up_Process_Activity.this, Target_Circle_Activity.class));
                    finish();
                    break;

            }
        }

        }else {

            Log.d("itemede",""+i);
            main_viewPager.setCurrentItem((i+1));

            pager_indicator_method((i+1));

        }


    }

    private void pager_indicator_method(int i) {

            List<StepBean> stepsBeanList = new ArrayList<>();
            StepBean stepBean0,stepBean1,stepBean2,stepBean3,stepBean4,stepBean5;
           /* int count =i;

            count++;*/
            Log.d("countde",""+i);
            switch (i)
            {
              /*  case 0:
                    stepBean0 = new StepBean("1",0);
                    stepBean1 = new StepBean("2",-1);
                    stepBean2 = new StepBean("3",-1);

                    stepsBeanList.add(stepBean0);
                    stepsBeanList.add(stepBean1);
                    stepsBeanList.add(stepBean2);

                    stepView.setStepViewTexts(stepsBeanList)
                            .setTextSize(16)//set textSize
                            .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                            .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                            .setStepViewComplectedTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsView text完成线的颜色
                            .setStepViewUnComplectedTextColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                    break;*/
                case 0:

                    //   List<StepBean> stepsBeanList = new ArrayList<>();
                    stepBean0 = new StepBean("1",0);
                    stepBean1 = new StepBean("2",-1);
                    stepBean2 = new StepBean("3",-1);

                    stepsBeanList.add(stepBean0);
                    stepsBeanList.add(stepBean1);
                    stepsBeanList.add(stepBean2);

                    stepView.setStepViewTexts(stepsBeanList)
                            .setTextSize(16)//set textSize
                            .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                            .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                            .setStepViewComplectedTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsView text完成线的颜色
                            .setStepViewUnComplectedTextColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                    break;
                case 1:
                    stepBean0 = new StepBean("1",1);
                    stepBean1 = new StepBean("2",0);
                    stepBean2 = new StepBean("3",-1);

                    stepsBeanList.add(stepBean0);
                    stepsBeanList.add(stepBean1);
                    stepsBeanList.add(stepBean2);


                    stepView.setStepViewTexts(stepsBeanList)
                            .setTextSize(16)//set textSize
                            .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                            .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                            .setStepViewComplectedTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsView text完成线的颜色
                            .setStepViewUnComplectedTextColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                    break;
                case 2:
                    stepBean0 = new StepBean("1",1);
                    stepBean1 = new StepBean("2",1);
                    stepBean2 = new StepBean("3",0);

                    stepsBeanList.add(stepBean0);
                    stepsBeanList.add(stepBean1);
                    stepsBeanList.add(stepBean2);

                    stepView.setStepViewTexts(stepsBeanList)
                            .setTextSize(16)//set textSize
                            .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                            .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                            .setStepViewComplectedTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsView text完成线的颜色
                            .setStepViewUnComplectedTextColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon
                    break;

            }



    }
}
