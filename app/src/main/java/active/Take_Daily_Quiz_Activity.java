package active;

import android.app.ProgressDialog;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.QuizDailyQuiz_Activity;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import callbacks.DailyQuizLoadedListener;
import callbacks.Spinner_Date_Listener;
import network.VolleySingleton;
import pojo.Quiz_Model;
import task.TaskLoadDailyQuiz;
import utilities.ConnectionCheck;
import utilities.Custom_Term_Cond_Dialog;
import utilities.MyApplication;
import utilities.UrlEndpoints;


/**
 * Created by GT on 3/29/2017.
 */

public class Take_Daily_Quiz_Activity extends AppCompatActivity implements DailyQuizLoadedListener, Spinner_Date_Listener {
    private JobScheduler mJobScheduler;
    private static final int JOB_ID = 200;
    private static final long POLL_FREQUENCY = 30000;
    private static final String DAILY_QUIZLIST = "daily_quiz_list";
    private TextView btn_hr,btn_min,btn_sec;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<Quiz_Model>  listDailyQuiz= new ArrayList<>();
    private MyApplication myApplication=new MyApplication();
    private SharedPreferences sharedPreferences_theme;
    private ConnectionCheck cCheck;
    private boolean Conn_Status=false;
    private boolean Login_Bool=false;
    private ProgressDialog pd;
    private Button take_quiz_Btn;
    private MediaPlayer mPlay =null;
    private String Per_Ques_Time,Slot_Num;
    private int Slot_TIME,ADV_TIME;//,;
    private String str_color;
    private Toolbar toolbar_daily_quiz;
    private LinearLayout idtake_quiz_linear;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_daily_quiz_start_activity);
        idtake_quiz_linear=(LinearLayout)findViewById(R.id.idtake_quiz_linear);

        btn_hr=(TextView)findViewById(R.id.btn_hr);
        take_quiz_Btn=(Button)findViewById(R.id.take_quiz_btn);btn_min=(TextView)findViewById(R.id.btn_min); btn_sec=(TextView)findViewById(R.id.btn_sec);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set_progres_dialog();

        ImageView  id_img_timer=(ImageView)findViewById(R.id.id_img_timer);

   /*     int myVectorColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        id_img_timer.getIcon().setColorFilter(myVectorColor, PorterDuff.Mode.SRC_IN);*/

        // id_img_timer.set
        pd= new ProgressDialog(Take_Daily_Quiz_Activity.this);
        //Log.d("calll","call");
        Time_For_Quiz();

        cCheck = new ConnectionCheck(getApplicationContext());
        if(cCheck.checkConnection())
        {
            Conn_Status=true;

        } else {
            common_click_sound();
            ConnectionCheck.openDialog(Conn_Status,Take_Daily_Quiz_Activity.this);
        }
        /*end connection */

        take_quiz_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(Login_Bool)
                {
                  //  set_progres_dialog();
                    take_quiz_Btn.setEnabled(false);
                    common_click_sound();
                    Custom_Term_Cond_Dialog cdd=new Custom_Term_Cond_Dialog(Take_Daily_Quiz_Activity.this,v);
                    cdd.show();

                }else if(!Login_Bool)
                {
                   /* common_click_sound();
                    ConnectionCheck.login_Dialog("Take_Quiz_Page",Take_Daily_Quiz_Activity.this,false);*/
                }
            }
        });
        findViewById(R.id.id_back_btn_quiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        finish();
            }
        });


        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            private long time = 0;

            @Override
            public void run()
            {
                // do stuff then
                // can call h again after work!
                time += 1000;
               // //Log.d("TimerExample", "Going for... " + time);
                h.postDelayed(this, 1000);
            }
        }, 1000);
/*
        Theme_Change.Toolbar_Change_Prefrense((Toolbar) findViewById(R.id.toolbar_daily_quiz));
        Theme_Change.Theme_Change_Prefrense((Button)findViewById(R.id.take_quiz_btn));
        Theme_Change.Toolbar_Change_Prefrense((LinearLayout)findViewById(R.id.id_bottom_linear));
        Theme_Change.Theme_Change_Prefrense((FrameLayout)findViewById(R.id.id_frm_thank));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           // id_img_timer.setImageTintList(getResources().getColorStateList(R.color.btn_golden));
            Theme_Change.Image_Change_Prefrense((ImageView)findViewById(R.id.id_img_timer));
        }*/
    }

    private void set_progres_dialog() {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Connecting");
        progress.setMessage("Please wait while we fetching data...");
        progress.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable,700);
    }

    private void Time_For_Quiz() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,  UrlEndpoints.URL_QUIZ_TIME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try {
                            //Log.d("quiz_open_time",""+str_response);
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("data"))
                            {
                              //  JSONArray jsonArray = response.getJSONArray("data");
                                JSONObject jsonObject = response.getJSONObject("data");

                                if(jsonObject.has("time")) {
                                    JSONObject time_jobj = jsonObject.getJSONObject("time");
                                    JSONObject st_jobj = time_jobj.getJSONObject("startTime");

                                    btn_hr.setText(st_jobj.getString("hr"));
                                    btn_min.setText(st_jobj.getString("min"));
                                    btn_sec.setText(st_jobj.getString("sec"));
                                    //   Toast.makeText(Take_Daily_Quiz_Activity.this, "Quiz Has not Yet Started", Toast.LENGTH_SHORT).show();
                                    //Log.d("stuus", st_jobj.getString("hr"));
                                }else if(jsonObject.has("terms"))
                                {

                                }
                            }
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


    @Override
    protected void onStart() {
        super.onStart();
        //Log.d("status","onStart");
        if(myApplication.is_User_Login(Take_Daily_Quiz_Activity.this))
        {
            Login_Bool=myApplication.is_User_Login(Take_Daily_Quiz_Activity.this);

        }else if(!myApplication.is_User_Login(Take_Daily_Quiz_Activity.this)){
            Login_Bool=myApplication.is_User_Login(Take_Daily_Quiz_Activity.this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d("status","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d("status","onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("status","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("status","onPause");
        swap_page_audio_call();
    }

    @Override
    public void onDailyQuizLoaded(ArrayList<Quiz_Model> listDailyQuiz) {

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
    public void check_open_quiz_and_time()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.URL_START_DAILY_QUIZ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try {
                                //Log.d("quiz_open_daily_res",""+str_response);
                                JSONObject response = new JSONObject(str_response);
                            if(response.has("status"))
                            {
                                if (response.getString("status").equals("0")) {

                                    JSONArray jsonArray = response.getJSONArray("data");
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    JSONObject st_jobj = jsonObject.getJSONObject("startTime");

                                  /*  btn_hr.setText(st_jobj.getString("hr"));
                                    btn_min.setText(st_jobj.getString("min"));
                                    btn_sec.setText(st_jobj.getString("sec"));*/

                                   // ConnectionCheck.quiz_time_not_get(Take_Daily_Quiz_Activity.this);

                                } else if (response.getString("status").equals("1")) {
                                    //Log.d("stusnot", "Quiz Has Started");
                                    // pd.dismiss();
                                 //   set_progres_dialog();
                                 //   SharedPreferences shrd_prf_login = myApplication.get_shrd_prf_login(getBaseContext());
                                    //Log.d("logindgf", shrd_prf_login.getString("mobile", "")+"   "+shrd_prf_login.getString("Login_Token", "")+" "+response.getString("question_timing")+" "+response.getString("slot_time"));
                                    //Log.d("stusnot", "Quiz Has cxcStarted");
                                    ADV_TIME= Integer.parseInt(response.getString("ad_timing"));// adv total time
                                    Slot_TIME= Integer.parseInt(response.getString("slot_time"));// slot time
                                    Per_Ques_Time=response.getString("question_timing");// per ques time
                                    Slot_Num=response.getString("slot");// per ques time
                                    //Log.d("stusnot", "Quiz Has xcxcStarted");
                                   // new TaskLoadDailyQuiz(Take_Daily_Quiz_Activity.this, shrd_prf_login.getString("mobile", ""), shrd_prf_login.getString("Login_Token", ""), response.getString("question_timing")).execute();
                                }
                            }
                            else if(response.has("msg"))
                                {
                                   //pd.dismiss();
                                    //Log.d("unauth","un_Auth");
                                  //  ConnectionCheck.unAuth_prob(Take_Daily_Quiz_Activity.this,response.getString("msg"));
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
                if(myApplication.is_User_Login(getBaseContext())) {
                  //  SharedPreferences shrd_prf_login = myApplication.get_shrd_prf_login(getBaseContext());

                    //Log.d("gdvcbhv",shrd_prf_login.getString("mobile", "")+"  "+shrd_prf_login.getString("Login_Token", ""));
                 //   map.put("mobile",shrd_prf_login.getString("mobile", ""));
                 //   map.put("token",shrd_prf_login.getString("Login_Token", ""));
                    return map;
                }else if(!myApplication.is_User_Login(getBaseContext())){
                    mPlay= MediaPlayer.create(Take_Daily_Quiz_Activity.this, R.raw.dialog_aud);
                    mPlay.start();
                  //  ConnectionCheck.login_Dialog("Take_Quiz_Page",Take_Daily_Quiz_Activity.this,false);
                }
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);

    }

    private void swap_page_audio_call() {
        mPlay = MediaPlayer.create(Take_Daily_Quiz_Activity.this, R.raw.swap_page);
        mPlay.start();
    }
    private void common_click_sound() {

        mPlay = MediaPlayer.create(Take_Daily_Quiz_Activity.this, R.raw.dialog_aud);
        mPlay.start();
    }


    @Override
    public void onSpinner_Date(String str_choose_daet,View v) {

        switch(v.getId())
        {
            case R.id.take_quiz_btn:
                check_open_quiz_and_time();
                break;
        }

    }

}
