package active;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import adapter.ADV_VPager_Adapter;
import callbacks.ADV_LSIT_LISTENER;
import callbacks.DailyQuizLoadedListener;
import pojo.ADV_RANK_MODEL;
import pojo.Quiz_Model;
import task.TaskLoadDailyQuiz;
import utilities.App_Static_Method;
import utilities.ConnectionCheck;
import utilities.MyApplication;
import utilities.NonSwipeableViewPager;
import utilities.UrlEndpoints;

import static utilities.App_Static_Method.session_type;

/**
 * Created by GT on 6/2/2017.
 */

public class Adv_Activity extends AppCompatActivity implements DailyQuizLoadedListener, ADV_LSIT_LISTENER {

    private int SLOT_COUNT = 1;
  //  private ArrayList<Quiz_Model> mDailyQuizList;
    private long startTime__l = 0L;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private Handler PQ_handler,handler;
    private Runnable PQ_Update,Update;
    private Timer swipeTimer;
    private NonSwipeableViewPager id_adv_VP;
    private ADV_VPager_Adapter adv_vPager_adapter;
    private String str_adv_time,str_quiz_time;
    private Timer PQ_swipeTimer;
    private boolean BL_EXIT=true;
    ArrayList<Quiz_Model> listDailyQuiz;
    long TOTAL_ADV_TIME=0L;
    long RANK_TIME_DISPLAY=0L;
    private int Slot_TIME,ADV_TIME;//,;
    private MyApplication myApplication=new MyApplication();
    private MediaPlayer mPlay =null;
    private String Per_Ques_Time,Slot_Num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        id_adv_VP=(NonSwipeableViewPager)findViewById(R.id.id_adv_VP);
        //  mDailyQuizList = this.getIntent().getParcelableArrayListExtra("list");
        str_adv_time=this.getIntent().getStringExtra("ADV_TIME");
        // str_quiz_time=this.getIntent().getStringExtra("quiz_time");
        SLOT_COUNT=Integer.parseInt(this.getIntent().getStringExtra("SLOT_COUNT"));
        //Log.d("strdrt",""+str_adv_time);// adv time
        startTime__l= Long.parseLong(str_adv_time);
        Long l_str=(startTime__l/5);
        //Log.d("dghhdgc",""+l_str);
        RANK_TIME_DISPLAY=Long.parseLong(String.valueOf(l_str));
        startTime = SystemClock.uptimeMillis();
        call_adv_to_srver();
    }

    private void set_vp_pq_tmer() {
        PQ_handler = new Handler();
        PQ_Update = new Runnable() {
            public void run() {
                //  int PQ_currentPage= id_adv_VP.getCurrentItem();
                //    //Log.d("dkjkdn",""+PQ_currentPage);

                if ((id_adv_VP.getCurrentItem())<id_adv_VP.getAdapter().getCount()-1) {
                    id_adv_VP.setCurrentItem((id_adv_VP.getCurrentItem()) + 1);
                }else// if((id_adv_VP.getCurrentItem())==id_adv_VP.getAdapter().getCount())
                {
                    // id_adv_VP.setCurrentItem((id_adv_VP.getCurrentItem()) + 1);
                    // //Log.d("number",""+id_adv_VP.getCurrentItem());
                    if(BL_EXIT) {
                        //call_page();
                        check_open_quiz_and_time();
                        BL_EXIT = false;
                    }
                    else if(!BL_EXIT){
                        PQ_handler.removeCallbacksAndMessages(null);
                        PQ_swipeTimer.cancel();
                    }
                    //Log.d("adv_rank_pagecall"," hellll");
                }

            }
        };
        PQ_swipeTimer = new Timer();
        PQ_swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                PQ_handler.post(PQ_Update);
            }
        }, RANK_TIME_DISPLAY, RANK_TIME_DISPLAY);


    }

    private void call_adv_to_srver() {

        String Url= UrlEndpoints.ADV_SP_RANK_API+session_type().get("mobile")+"&token="+session_type().get("token")+"&slot="+SLOT_COUNT;
        Log.d("strrr",Url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("sruts", response.toString());
                            JSONArray data_jsonArray=response.getJSONArray("data");
                            JSONArray sp_jsonArray=response.getJSONArray("sponser");
                            ArrayList<ADV_RANK_MODEL> adv_rank_list=new ArrayList<>();
                            if(data_jsonArray.length()==sp_jsonArray.length())
                            {
                                for (int i=0;i<data_jsonArray.length();i++)
                              // for (int i=0;i<5;i++)
                                {
                                    JSONObject jsonObject=data_jsonArray.getJSONObject(i);
                                    JSONObject sp_jsonObject=sp_jsonArray.getJSONObject(i);
                                    //Log.d("ghdhg","cgbdhxgstyfgv   i  "+jsonObject);
                                    ADV_RANK_MODEL advRankModel=new ADV_RANK_MODEL();
                                    advRankModel.setRK_IMAGE(jsonObject.getString("image"));
                                    advRankModel.setRK_NAME(jsonObject.getString("uname"));
                                    advRankModel.setRK_STATE(jsonObject.getString("state"));
                                    advRankModel.setSP_IMAGE(sp_jsonObject.getString("image"));
                                    advRankModel.setSP_IMG_FLAG(sp_jsonObject.getString("image_flag"));
                                    advRankModel.setSP_IMG_SIZE(sp_jsonObject.getString("image_size"));
                                    advRankModel.setSP_NAME(sp_jsonObject.getString("name"));
                                    advRankModel.setSP_VEDIO(sp_jsonObject.getString("video_flag"));
                                    advRankModel.setSP_VEDIO_FLAG(sp_jsonObject.getString("video_link"));
                                    adv_rank_list.add(advRankModel);
                                }

                            }else if(data_jsonArray.length()<sp_jsonArray.length())
                            {

                               for (int i=0;i<data_jsonArray.length();i++)
                               // for (int i=0;i<5;i++)
                                {
                                    JSONObject jsonObject=data_jsonArray.getJSONObject(i);
                                    JSONObject sp_jsonObject=sp_jsonArray.getJSONObject(i);
                                    //Log.d("ghdhg","cgbdhxgstyfgv   i  "+jsonObject);
                                    ADV_RANK_MODEL advRankModel=new ADV_RANK_MODEL();
                                    advRankModel.setRK_IMAGE(jsonObject.getString("image"));
                                    advRankModel.setRK_NAME(jsonObject.getString("uname"));
                                    advRankModel.setRK_STATE(jsonObject.getString("state"));
                                    advRankModel.setSP_IMAGE(sp_jsonObject.getString("image"));
                                    advRankModel.setSP_IMG_FLAG(sp_jsonObject.getString("image_flag"));
                                    advRankModel.setSP_IMG_SIZE(sp_jsonObject.getString("image_size"));
                                    advRankModel.setSP_NAME(sp_jsonObject.getString("name"));
                                    advRankModel.setSP_VEDIO(sp_jsonObject.getString("video_flag"));
                                    advRankModel.setSP_VEDIO_FLAG(sp_jsonObject.getString("video_link"));
                                    adv_rank_list.add(advRankModel);
                                }

                            }else if(data_jsonArray.length()>sp_jsonArray.length())
                            {
                                for (int i=0;i<sp_jsonArray.length();i++)
                               // for (int i=0;i<5;i++)
                                {
                                    JSONObject jsonObject=data_jsonArray.getJSONObject(i);
                                    JSONObject sp_jsonObject=sp_jsonArray.getJSONObject(i);

                                    ADV_RANK_MODEL advRankModel=new ADV_RANK_MODEL();
                                    advRankModel.setRK_IMAGE(jsonObject.getString("image"));
                                    advRankModel.setRK_NAME(jsonObject.getString("uname"));
                                    advRankModel.setRK_STATE(jsonObject.getString("state"));
                                    advRankModel.setSP_IMAGE(sp_jsonObject.getString("image"));
                                    advRankModel.setSP_IMG_FLAG(sp_jsonObject.getString("image_flag"));
                                    advRankModel.setSP_IMG_SIZE(sp_jsonObject.getString("image_size"));
                                    advRankModel.setSP_NAME(sp_jsonObject.getString("name"));
                                    advRankModel.setSP_VEDIO(sp_jsonObject.getString("video_flag"));
                                    advRankModel.setSP_VEDIO_FLAG(sp_jsonObject.getString("video_link"));
                                    adv_rank_list.add(advRankModel);
                                }
                            }
                                Log.d("lsit_model",adv_rank_list.toString());
                            ((ADV_LSIT_LISTENER)Adv_Activity.this).on_adv_list(adv_rank_list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onPause() {
        super.onPause();
      //  swipeTimer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Log.d("status","onstop_adv");
        // this.stopService(new Intent(this, TimeService.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PQ_handler.removeCallbacksAndMessages(null);
        PQ_swipeTimer.cancel();
    }


    @Override
    public void on_adv_list(ArrayList<ADV_RANK_MODEL> adv_rank_list) {

        //Log.d("pager_call","oeverdire");
        adv_vPager_adapter=new ADV_VPager_Adapter(getSupportFragmentManager(),Adv_Activity.this,adv_rank_list);
        id_adv_VP.setAdapter(adv_vPager_adapter);
        set_vp_pq_tmer();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub

        if(requestCode == 101){
              PQ_handler.removeCallbacksAndMessages(null);
              //  handler.removeCallbacksAndMessages(null);
              PQ_swipeTimer.cancel();
              finish();
        }

    }

    @Override
    public void onDailyQuizLoaded(ArrayList<Quiz_Model> listDailyQuiz) {

    }

    public void check_open_quiz_and_time()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.URL_START_DAILY_QUIZ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try {
                            //Log.d("quiz_open_responce",""+str_response);
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("status"))
                            {
                                if (response.getString("status").equals("0")) {

                                  //  ConnectionCheck.quiz_end_not_get(Adv_Activity.this);

                                    startActivity(new Intent(getApplicationContext(),Quiz_Completed_Activity.class));
                                    finish();

                                } else if (response.getString("status").equals("1")) {
                                    //Log.d("stusnot", "Quiz Has Started");
                                    // pd.dismiss();
                                    //   set_progres_dialog();
                                    //SharedPreferences shrd_prf_login = myApplication.get_shrd_prf_login(getBaseContext());
                                    //Log.d("logindgf", shrd_prf_login.getString("mobile", "")+"   "+shrd_prf_login.getString("Login_Token", "")+" "+response.getString("question_timing")+" "+response.getString("slot_time"));
                                    //Log.d("stusnot", "Quiz Has cxcStarted");
                                    ADV_TIME= Integer.parseInt(response.getString("ad_timing"));// adv total time
                                    Slot_TIME= Integer.parseInt(response.getString("slot_time"));// slot time
                                    Per_Ques_Time=response.getString("question_timing");// per ques time
                                    Slot_Num=response.getString("slot");// per ques time
                                    //Log.d("stusnot", "Quiz Has xcxcStarted");
                                    new TaskLoadDailyQuiz(Adv_Activity.this, session_type().get("mobile"),session_type().get("token"), response.getString("question_timing")).execute();
                                }
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                //Log.d("unauth","un_Auth");
                                ConnectionCheck.unAuth_prob(Adv_Activity.this,response.getString("msg"));
                            }
                            else if(response.has("ad"))
                            {

                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("mobile",session_type().get("mobile"));
                map.put("token",session_type().get("token"));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);

    }
    @Override
    public void onDailyQuizLoaded(ArrayList<Quiz_Model> listDailyQuiz,String str_quiz_time) {

        this.listDailyQuiz=listDailyQuiz;

        if (listDailyQuiz.size()>=0)
        {
            //Log.d("ListSize_take",""+listDailyQuiz.size()+" quiz_time"+str_quiz_time);
            //Log.d("ListSize_takedsec",""+listDailyQuiz);
            Double int_q_time= Double.valueOf(Per_Ques_Time);
            String str_Q_time=String.valueOf(Math.round(int_q_time*1000));
            Intent i = new Intent(getApplicationContext(), QuizDailyQuiz_Activity.class);
            i.putParcelableArrayListExtra("list", listDailyQuiz);
            i.putExtra("quiz_type","2");
            i.putExtra("ques_time",str_Q_time);// second

            String str_slottime= String.valueOf(Slot_TIME*1000);
            i.putExtra("Slot_TIME",str_slottime);// per slot time in milisecond
            i.putExtra("ADV_TIME",String.valueOf(ADV_TIME));// advertisement_time in minute
            i.putExtra("SLOT_NUM",Slot_Num);
            //Log.d("adv_hgjh",ADV_TIME+" "+Slot_TIME+" "+str_Q_time+" "+Slot_Num);

            startActivity(i);
            finish();
        }
    }
}
