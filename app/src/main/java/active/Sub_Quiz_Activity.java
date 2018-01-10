package active;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gt.active_education.R;
import java.util.ArrayList;
import java.util.Timer;

import callbacks.Quiz_Opt_Choose_Event_Listener;
import callbacks.VP_PageChange_Listener;
import pojo.Gallery_Model;
import pojo.Quiz_Answer_Model;
import pojo.Quiz_Model;
import utilities.MyApplication;

/**
 * Created by GT on 6/9/2017.
 */

public class Sub_Quiz_Activity extends AppCompatActivity implements VP_PageChange_Listener, Quiz_Opt_Choose_Event_Listener,View.OnClickListener {

    private int COUNT=1;
   // private Sub_quiz_VP_Adapter main_pager_Adapter;
    public  ViewPager main_viewPager;
    private FrameLayout quiz_next_action_btn,quiz_preveious_action_btn;
    private Timer swipeTimer,Q_swipeTimer,PQ_swipeTimer;
    private FrameLayout skip_action_button;
    //The key used to store arraylist of movie objects to and from parcelable
    private static final String DAILY_QUIZLIST = "daily_quiz_list";
    //private ArrayList<Quiz_Model> mDailyQuizList = new ArrayList<>();
    private ArrayList<Quiz_Model> listDailyQuiz= new ArrayList<>();
  //  private Sub_quiz_VP_Adapter newAdapter;
    private TextView TV_toolbar_skip,id_icon_timer,back_press_tv,toolbar_skip;
    private ArrayList<Quiz_Model> mDailyQuizList;
    //private BlurLayout blurLayout,blurLayout3;
    private TextView id_tv_q_no;
    private Boolean bl_close_quiz=false;
    private int QUIZ_SINGLE_SLOT_TIME;
    private TextView toolbar_timer,toolbar_timer_Q;
    private String str_type,str_quiz_time;
    private Handler PQ_handler,handler,Q_handler;
    private Runnable PQ_Update,Update,Q_Update;
    private Handler customHandler = new Handler();
    private Handler Q_customHandler = new Handler();
    private MediaPlayer mPlay;
    private MyApplication myApplication=new MyApplication();
    private boolean BL_EXIT=true;
    private SharedPreferences sharedPreferences_theme;
    private LinearLayout id_prc_quiz_back;
    private Toolbar toolbar2;
    private Sub_quiz_VP_Adapter newAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_quiz);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        back_press_tv=(TextView)findViewById(R.id.id_back_quicz_Quick_btn);
        toolbar_skip=(TextView)findViewById(R.id.toolbar_skip);
        main_viewPager=(ViewPager)findViewById(R.id.id_viewpager_quiz_question);
        skip_action_button=(FrameLayout)findViewById(R.id.skip_action_button);
        TV_toolbar_skip=(TextView)findViewById(R.id.toolbar_skip);
        toolbar_timer=(TextView)findViewById(R.id.toolbar_timer);
        toolbar_timer_Q=(TextView)findViewById(R.id.toolbar_timer_Q);
        id_icon_timer=(TextView)findViewById(R.id.id_icon_timer);
        id_tv_q_no=(TextView)findViewById(R.id.id_tv_q_no);
        toolbar2=(Toolbar)findViewById(R.id.toolbar2);
        id_prc_quiz_back=(LinearLayout)findViewById(R.id.id_prc_quiz_back);
        /*  str_type=this.getIntent().getStringExtra("quiz_type");*/

        mDailyQuizList = this.getIntent().getParcelableArrayListExtra("list");
        ////Log.d("size_q",""+mDailyQuizList.size());
        back_press_tv.setOnClickListener(this);
        toolbar_skip.setText("Done");
        toolbar_skip.setOnClickListener(this);
        Gson gson = new Gson();
        java.lang.reflect.Type type= new TypeToken<ArrayList<Quiz_Answer_Model>>() {}.getType();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Sub_Answer_Array_List",0);
        SharedPreferences.Editor editor = prefs.edit();
        Log.d("checklength",""+  mDailyQuizList.toString());
        editor.putString("SrdPrf_ArrayList", gson.toJson(new  ArrayList<Quiz_Answer_Model>()));
        editor.putString("LIST_NO",String.valueOf(mDailyQuizList.size()));
        editor.commit();

        newAdapter=new Sub_quiz_VP_Adapter(this,getApplicationContext(),mDailyQuizList,this);
        main_viewPager.setAdapter(newAdapter);

        main_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                id_tv_q_no.setText(String.valueOf(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                ////Log.d("staetpage",""+state);

            }
        });
      //  Theme_Change.Toolbar_Change_Prefrense((Toolbar) findViewById(R.id.toolbar2));
     //   Theme_Change.Toolbar_Change_Prefrense((FrameLayout)findViewById(R.id.id_frm_bottom));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(DAILY_QUIZLIST, listDailyQuiz);
    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    public void onBackPressed() {
        mPlay=MediaPlayer.create(Sub_Quiz_Activity.this, R.raw.dialog_aud);
        mPlay.start();
      // ConnectionCheck.exit_open_Dialog(true,Sub_Quiz_Activity.this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_back_quicz_Quick_btn:
                mPlay=MediaPlayer.create(Sub_Quiz_Activity.this, R.raw.dialog_aud);
                mPlay.start();
              //  ConnectionCheck.exit_open_Dialog(true,Sub_Quiz_Activity.this);
                break;

            case R.id.toolbar_skip:
               /* mPlay=MediaPlayer.create(QuizDailyQuiz_Activity.this, R.raw.dialog_aud);
                mPlay.start();
                TV_toolbar_skip.setEnabled(false);
                quiz_close();
               */
                bl_close_quiz=true;
                mPlay=MediaPlayer.create(Sub_Quiz_Activity.this, R.raw.swap_page);
                mPlay.start();
                call_page();
                break;

        }
    }

    @Override
    public void onOption_Choosen(Quiz_Answer_Model quizAnswerModel) {

    }

    @Override
    public void onOption_Choosen(int postion, String str_ans, String q_id) {

    }

    @Override
    public void on_opt_next_Ques(ArrayList<Gallery_Model> quiz_Ans_List, int poss) {

    }
    @Override
    public void on_opt_next_Ques() {
      /*mPlay=MediaPlayer.create(Sub_Quiz_Activity.this, R.raw.dialog_aud);
        mPlay.start();*/
        Log.d("countt", "next"+" "+main_viewPager.getCurrentItem()+" "+main_viewPager.getAdapter().getCount());
        if ((main_viewPager.getCurrentItem())<main_viewPager.getAdapter().getCount()-1) {
             main_viewPager.setCurrentItem((main_viewPager.getCurrentItem()) + 1);
        }else if((main_viewPager.getCurrentItem())==main_viewPager.getAdapter().getCount()-1)
        {
            ////Log.d("adv_rank_pagecall"," hellll");
            if(BL_EXIT) {
                call_page();
                BL_EXIT = false;
            }
        }
    }
    private void call_page() {
        Log.d("sggdr",""+mDailyQuizList);
        Intent i = new Intent(getApplicationContext(), Quiz_Save_Result_Activity.class);
        i.putParcelableArrayListExtra("quiz_ques_list",mDailyQuizList);
        startActivityForResult(i,101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 101){
            finish();
        }
    }

    private void set_theme(SharedPreferences sharedPreferences_theme) {

/*
        switch (sharedPreferences_theme.getString(KEY_THEME_NUM, "")) {
            case "THEME1":
                ////Log.d("hgdhghghgd", "THEME1");

                toolbar2.setBackgroundColor(Color.RED);
                id_prc_quiz_back.setBackground(getResources().getDrawable(R.drawable.ic_blur_main));
                break;
            case "THEME2":
                ////Log.d("hgdhghghgd", "THEME2");
                toolbar2.setBackgroundColor(Color.TRANSPARENT);
                id_prc_quiz_back.setBackground(getResources().getDrawable(R.drawable.blur_img));
                break;
            case "THEME3":
                ////Log.d("hgdhghghgd", "THEME3");
                toolbar2.setBackgroundColor(Color.BLUE);
                id_prc_quiz_back.setBackground(getResources().getDrawable(R.drawable.ic_back_main));
                break;
        }
*/
    }
}
