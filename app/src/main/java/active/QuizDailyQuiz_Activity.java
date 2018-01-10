package active;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import active_adapter.ViewPagerAdapter;
import callbacks.Quiz_Opt_Choose_Event_Listener;
import callbacks.VP_PageChange_Listener;
import pojo.Gallery_Model;
import pojo.Quiz_Answer_Model;
import pojo.Quiz_Model;
import utilities.AnimUtils;
import utilities.App_Static_Method;
import utilities.ConnectionCheck;
import utilities.DroidDialog;
import utilities.MyApplication;
import utilities.NonSwipeableViewPager;
import utilities.UrlEndpoints;


/**
 * Created by GT on 3/27/2017.
 */

public class QuizDailyQuiz_Activity extends AppCompatActivity implements VP_PageChange_Listener, Quiz_Opt_Choose_Event_Listener,View.OnClickListener {

    private int COUNT=1;
    private ViewPagerAdapter main_pager_Adapter;
    public NonSwipeableViewPager main_viewPager;
    private FrameLayout quiz_next_action_btn,quiz_preveious_action_btn;
    private Timer swipeTimer,Q_swipeTimer,PQ_swipeTimer;
    private FrameLayout skip_action_button;
    //The key used to store arraylist of movie objects to and from parcelable
    private static final String DAILY_QUIZLIST = "daily_quiz_list";
    //private ArrayList<Quiz_Model> mDailyQuizList = new ArrayList<>();
    private MyApplication myApplication=new MyApplication();
    private ArrayList<Quiz_Model>  listDailyQuiz= new ArrayList<>();
    private ViewPagerAdapter newAdapter;
    private TextView TV_toolbar_skip,id_icon_timer,back_press_tv,toolbar_skip;
    private ArrayList<Quiz_Model> mDailyQuizList;
    private LinearLayout id_linear_daily_quiz;
    private TextView id_tv_q_no;
    private Boolean bl_close_quiz=false;
   // private int QUIZ_SINGLE_SLOT_TIME;
    private TextView toolbar_timer,toolbar_timer_Q;
    private String str_type,str_quiz_time;
    private Handler PQ_handler,handler,Q_handler;
    private Runnable PQ_Update,Update,Q_Update;
    private Handler customHandler = new Handler();
    private Handler Q_customHandler = new Handler();
    private MediaPlayer mPlay;
    private String  str_SLOT_NUM;

    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    private long Q_startTime = 0L;
    long Q_timeInMilliseconds = 0L;
    long Q_timeSwapBuff = 0L;
    long Q_updatedTime = 0L;
    private String ADV_TIME,str_ADV_TIME;

    /* question time data*/
    int PQ_total_Page;
    long SINGLE_QUES_TIME=0L;
    long SINGLE_SLOT_TIME=0L;
    private SharedPreferences sharedPreferences_theme;
    private Toolbar toolbar2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizdailyquiz);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        back_press_tv=(TextView)findViewById(R.id.id_back_quicz_Quick_btn);
        toolbar_skip=(TextView)findViewById(R.id.toolbar_skip);
        main_viewPager=(NonSwipeableViewPager)findViewById(R.id.id_viewpager_quiz_question);
        skip_action_button=(FrameLayout)findViewById(R.id.skip_action_button);
        TV_toolbar_skip=(TextView)findViewById(R.id.toolbar_skip);
        toolbar_timer=(TextView)findViewById(R.id.toolbar_timer);
        toolbar_timer_Q=(TextView)findViewById(R.id.toolbar_timer_Q);
        id_icon_timer=(TextView)findViewById(R.id.id_icon_timer);
        id_linear_daily_quiz=(LinearLayout)findViewById(R.id.id_linear_daily_quiz);



         //continue
        str_quiz_time=this.getIntent().getStringExtra("ques_time");
        str_type=this.getIntent().getStringExtra("quiz_type");//quiz_type
        //Log.d("sggdr",str_type);
        mDailyQuizList = this.getIntent().getParcelableArrayListExtra("list");
        //Log.d("size_q",""+mDailyQuizList.size());

        int intype= Integer.parseInt(str_type);
        //Log.d("callservice","satrtservice");
        //  startService(new Intent(QuizDailyQuiz_Activity.this, TimeService.class));
        if(!(str_quiz_time.equals("0")))
        {
            //Log.d("size_q",""+mDailyQuizList.size()+" "+ SINGLE_SLOT_TIME +" "+intype);

            if (intype==2)
            {
                SINGLE_QUES_TIME=Long.parseLong(String.valueOf(this.getIntent().getStringExtra("ques_time")));//quiz_time
                SINGLE_SLOT_TIME =Long.parseLong((this.getIntent().getStringExtra("Slot_TIME")));// slot time in second
                str_ADV_TIME=this.getIntent().getStringExtra("ADV_TIME");
                str_SLOT_NUM=this.getIntent().getStringExtra("SLOT_NUM");
                ADV_TIME =String.valueOf(Integer.parseInt(str_ADV_TIME)*60000);// in 10 second  1*10000  change in to milisec
                //Log.d("inputtype",""+intype+" "+ADV_TIME+" "+SINGLE_QUES_TIME+ "   "+SINGLE_SLOT_TIME);
              /*  toolbar_timer.setVisibility(View.VISIBLE);
                id_icon_timer.setVisibility(View.VISIBLE);*/
                startTime = SystemClock.uptimeMillis();

                 customHandler.postDelayed(updateTimerThread, 0);

                   handler = new Handler();
                   Update = new Runnable() {
                        public void run() {

                            if(!bl_close_quiz) {
                                quiz_close();// viewpager end for adv
                                swipeTimer.cancel();
                                finish();
                            }
                        }
                    };
                    swipeTimer = new Timer();
                    swipeTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, SINGLE_SLOT_TIME, SINGLE_SLOT_TIME);
            }
        }

        newAdapter=new ViewPagerAdapter(this,getApplicationContext(),mDailyQuizList,this);
        main_viewPager.setAdapter(newAdapter);
        back_press_tv.setOnClickListener(this);
        TV_toolbar_skip.setOnClickListener(this);
        toolbar_skip.setOnClickListener(this);
        start_PQ_Timer();

        main_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==mDailyQuizList.size()-1)
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
                }
              //  id_tv_q_no.setText(String.valueOf(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                //Log.d("staetpage",""+state);

            }
        });

        // onQP_RUN(main_viewPager);
        // Auto start of viewpager
        PQ_total_Page= mDailyQuizList.size();
        //Log.d("sizsese",""+PQ_total_Page);
         PQ_handler = new Handler();
         PQ_Update = new Runnable() {
            public void run() {
                int PQ_currentPage= main_viewPager.getCurrentItem();
                //    //Log.d("dkjkdn",""+PQ_currentPage);

                if ((main_viewPager.getCurrentItem())<=main_viewPager.getAdapter().getCount()) {
                     main_viewPager.setCurrentItem((main_viewPager.getCurrentItem()) + 1);
                }

            }
        };
        PQ_swipeTimer = new Timer();
        PQ_swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                PQ_handler.post(PQ_Update);
            }
        },SINGLE_QUES_TIME,SINGLE_QUES_TIME);
     //   Theme_Change.Toolbar_Change_Prefrense(findViewById(R.id.toolbar2));
    }

    private void start_PQ_Timer() {
        Q_startTime = SystemClock.uptimeMillis();

        Q_customHandler.postDelayed(Q_updateTimerThread, 0);

        Q_handler = new Handler();
        Q_Update = new Runnable() {
            public void run() {
                    Q_swipeTimer.cancel();
            }
        };
        Q_swipeTimer = new Timer();

        Q_swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Q_handler.post(Q_Update);
            }
        }, SINGLE_SLOT_TIME, SINGLE_SLOT_TIME);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        mPlay=MediaPlayer.create(QuizDailyQuiz_Activity.this, R.raw.dialog_aud);
        mPlay.start();
        new DroidDialog.Builder(QuizDailyQuiz_Activity.this)
                .icon(R.drawable.exit)
                .title("Do You Want to Quit Quiz !!")
                .content(QuizDailyQuiz_Activity.this.getResources().getString(R.string.short_text))
                .cancelable(true, true)
                .negativeButton("No", new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog droidDialog) {
                        droidDialog.dismiss();
                    }
                })
                .neutralButton("Yes", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {
                        QuizDailyQuiz_Activity.super.onBackPressed();
                        droidDialog.dismiss();
                    }
                })
                .typeface("regular.ttf")
                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(QuizDailyQuiz_Activity.this, R.color.colorPrimary), ContextCompat.getColor(QuizDailyQuiz_Activity.this, R.color.colorPrimaryDark),
                        ContextCompat.getColor(QuizDailyQuiz_Activity.this, R.color.colorAccent))
                .divider(true, ContextCompat.getColor(QuizDailyQuiz_Activity.this, R.color.orange))
                .show();

       // finish();
    }

    private void quiz_close() {

        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
        //Log.d("tttt",""+timeSwapBuff);
        Intent i=new Intent(getApplicationContext(),Adv_Activity.class);
        i.putParcelableArrayListExtra("list", mDailyQuizList);
        //Log.d("count_poss",""+(COUNT));
        i.putExtra("SLOT_COUNT",str_SLOT_NUM);
        i.putExtra("ADV_TIME",""+ADV_TIME);
      //  i.putExtra("quiz_time",str_quiz_time);// get from api
        startActivity(i);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(DAILY_QUIZLIST, listDailyQuiz);
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            toolbar_timer.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };
    private Runnable Q_updateTimerThread = new Runnable() {

        public void run() {

            Q_timeInMilliseconds = SystemClock.uptimeMillis() - Q_startTime;

            Q_updatedTime = Q_timeSwapBuff + Q_timeInMilliseconds;

            int secs = (int) (Q_updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (Q_updatedTime % 1000);
            //Log.d("timerthraed",""+String.format("%02d", secs) + ":"+ String.format("%03d", milliseconds));
            toolbar_timer_Q.setText( String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            Q_customHandler.postDelayed(this, 0);
        }

    };

    // for banner position listener
    @Override
    public void on_opt_next_Ques() {
        mPlay=MediaPlayer.create(QuizDailyQuiz_Activity.this, R.raw.dialog_aud);
        mPlay.start();
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {*/
                //Log.d("countt", "next");
                if ((main_viewPager.getCurrentItem())<=main_viewPager.getAdapter().getCount()) {
                     main_viewPager.setCurrentItem((main_viewPager.getCurrentItem()) + 1);
                }

           /* }
        }, 50);*/
    }

    @Override
    public void on_opt_next_Ques(ArrayList<Gallery_Model> quiz_Ans_List, int poss) {

    }

    @Override
    public void onPause() {
        super.onPause();

        PQ_handler.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);
        Q_customHandler.removeCallbacksAndMessages(null);
        Q_swipeTimer.cancel();
        PQ_swipeTimer.cancel();
        swipeTimer.cancel();
        swap_page_audio_call();
       // startActivity(new Intent(getApplicationContext(),Quit_Quiz_Activity.class));
        finish();
        //Log.d("status","onPause_daily_quiz");

    }

    @Override
    protected void onStop() {
        super.onStop();
        PQ_handler.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);
        Q_customHandler.removeCallbacksAndMessages(null);
        Q_swipeTimer.cancel();
        PQ_swipeTimer.cancel();
        swipeTimer.cancel();
        mPlay.stop();
        finish();
    }

    private void swap_page_audio_call() {
        mPlay = MediaPlayer.create(QuizDailyQuiz_Activity.this, R.raw.swap_page);
        mPlay.start();

    }
    private void common_click_sound() {

        mPlay = MediaPlayer.create(QuizDailyQuiz_Activity.this, R.raw.dialog_aud);
        mPlay.start();
    }
    /////
    public void onQP_RUN(ViewPager main_viewPager) {



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_back_quicz_Quick_btn:
                mPlay=MediaPlayer.create(QuizDailyQuiz_Activity.this, R.raw.dialog_aud);
                mPlay.start();
                ConnectionCheck.exit_open_Dialog(true,QuizDailyQuiz_Activity.this);
                break;
            case R.id.quiz_next_action_button:
               /* //Log.d("countt", String.valueOf(main_viewPager.getAdapter().getCount()));
                if ((main_viewPager.getCurrentItem())<=main_viewPager.getAdapter().getCount()) {
                    main_viewPager.setCurrentItem((main_viewPager.getCurrentItem()) + 1);
                }*/
                break;
                /*  case R.id.quiz_preveious_action_button:
               *//* //Log.d("countt_back", String.valueOf(main_viewPager.getAdapter().getCount()));
                if ((main_viewPager.getCurrentItem())<=main_viewPager.getAdapter().getCount()) {
                    main_viewPager.setCurrentItem((main_viewPager.getCurrentItem()) - 1);
                }
                break;*/
            case R.id.toolbar_skip:
               /* mPlay=MediaPlayer.create(QuizDailyQuiz_Activity.this, R.raw.dialog_aud);
                mPlay.start();
                TV_toolbar_skip.setEnabled(false);
                quiz_close();
               */
                bl_close_quiz=true;
                mPlay=MediaPlayer.create(QuizDailyQuiz_Activity.this, R.raw.dialog_aud);
                mPlay.start();
                ConnectionCheck.exit_open_Dialog(true,QuizDailyQuiz_Activity.this);
                break;

        }
    }

    @Override
    public void onOption_Choosen(Quiz_Answer_Model quizAnswerModel) {

    }

    @Override
    public void onOption_Choosen(int postion,String str_ans,String str_Qid) {

        //Log.d("click_Time",""+Q_timeInMilliseconds);
      //  Toast.makeText(this, "option choose time  "+Q_timeInMilliseconds, Toast.LENGTH_SHORT).show();

        save_ans_to_server(Q_timeInMilliseconds,str_ans,str_Qid,postion);

    }

    private void save_ans_to_server(long q_timeInMilliseconds, String str_ans,String str_Qid,int poss) {


        String url= UrlEndpoints.URL_SAVE_EACH_ANS+ App_Static_Method.session_type().get("mobile")+"&token="+App_Static_Method.session_type().get("type")+"&question_id="
                +Integer.parseInt(str_Qid)+"&user_answer="+str_ans+"&time_taken="+q_timeInMilliseconds+"&slot="+COUNT;
        //Log.d("URL_save",""+url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try {
                            //Log.d("save_sngle_quiz_time",""+str_response);
                            JSONObject response = new JSONObject(str_response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);
    }



}
