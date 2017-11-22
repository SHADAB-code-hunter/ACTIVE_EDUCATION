package active;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gt.active_education.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import callbacks.Pie_Chart_data_Set;
import pojo.Quiz_Answer_Model;
import pojo.Quiz_Model;
import utilities.App_Static_Method;
import utilities.ConnectionCheck;
import utilities.MyApplication;
import utilities.UrlEndpoints;

/**
 * Created by GT on 6/10/2017.
 */

public class Quiz_Save_Result_Activity extends AppCompatActivity  implements Pie_Chart_data_Set {

    private String str_pie1,str_pie2,str_pie3,str_pie4,str_pie5;
    private ArrayList<Quiz_Model> mDailyQuizList;
    private SharedPreferences prefs,Shrd_prefs;
    private ArrayList<Quiz_Answer_Model> Quiz_Ans_List;
    MyApplication myApplication=new MyApplication();
    private MediaPlayer mPlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_save_result);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mDailyQuizList = this.getIntent().getParcelableArrayListExtra("quiz_ques_list");

        prefs = getApplicationContext().getSharedPreferences("Sub_Answer_Array_List", 0);
        final String storedHashMapString = prefs.getString("SrdPrf_ArrayList", "");
        final String total_list_no_ques = prefs.getString("LIST_NO", "");
        Gson gson = new Gson();
        java.lang.reflect.Type types;
        types = new TypeToken<ArrayList<Quiz_Answer_Model>>() {}.getType();
        Quiz_Ans_List = (ArrayList<Quiz_Answer_Model>) gson.fromJson(storedHashMapString, types);
       //Log.d("gjhdgjhg",""+mDailyQuizList);
       //Log.d("list_quisdecznbg",""+Quiz_Ans_List);

      //  final SharedPreferences shrd_prf_login = myApplication.get_shrd_prf_login(getBaseContext()); // hold

       //Log.d("gdvcbhv",shrd_prf_login.getString("mobile", "")+"  "+shrd_prf_login.getString("Login_Token", ""));
       /* map.put("mobile",shrd_prf_login.getString("mobile", ""));
        map.put("token",shrd_prf_login.getString("Login_Token", ""));*/


        Map<String, String> map = App_Static_Method.session_type();
        map.put("total",""+total_list_no_ques);
        map.put("jsonData",storedHashMapString);
        Log.d("gdvcbhv",""+storedHashMapString);
        String Url= UrlEndpoints.URL_save_result_practice;//+shrd_prf_login.getString("mobile", "")+"&token="+shrd_prf_login.getString("Login_Token", "")+"&total="+total_list_no_ques+"&jsonData="+storedHashMapString;
       //Log.d("urlsezxc",Url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.URL_save_result_practice,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try {
                           //Log.d("quiz_open_responce",""+str_response);
                            JSONObject response = new JSONObject(str_response);

                            if(response.has("data")) {
                                JSONObject jsonObject = response.getJSONObject("data");
                                if (jsonObject.getString("status").equals("1")) {

                                   //Log.d("hdghgdhcghchg", "dbcjdbcjdbcj");

                                    int int_attempt = Integer.parseInt(jsonObject.getString("total_attempted"));
                                    int int_totle_ques = Integer.parseInt(jsonObject.getString("total_questions"));
                                    int int_skip_ques = Integer.parseInt(jsonObject.getString("skip_questions"));
                                    int int_wng_ques = Integer.parseInt(jsonObject.getString("wrong_question"));
                                    int int_rgt_ques = Integer.parseInt(jsonObject.getString("right_questions"));
                                    int int_user_marks = Integer.parseInt(jsonObject.getString("user_marks"));
                                    Quiz_Save_Result_Activity.this.on_piechart_set_data(Quiz_Save_Result_Activity.this, int_skip_ques, int_wng_ques, int_rgt_ques);
                                  }
                                } else if (response.getString("status").equals("0")) {
                                Log.d("ghghgh","zerooo");
                                    //ConnectionCheck.result_zero(Quiz_Save_Result_Activity.this);
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
                Map<String, String> map = App_Static_Method.session_type();
               // if(myApplication.is_User_Login(getBaseContext())) {
                   // SharedPreferences shrd_prf_login = myApplication.get_shrd_prf_login(getBaseContext());

                   //Log.d("gdvcbhv",shrd_prf_login.getString("mobile", "")+"  "+shrd_prf_login.getString("Login_Token", "")+"  "+" total_quesnop"+total_list_no_ques+" "+storedHashMapString);
                  /*  map.put("mobile",shrd_prf_login.getString("mobile", ""));
                    map.put("token",shrd_prf_login.getString("Login_Token", ""));*/
                    map.put("total",""+total_list_no_ques);
                    map.put("jsonData",storedHashMapString);
                    Log.d("gdvcbhv",""+map);
                    return map;
               /* }else if(!myApplication.is_User_Login(getBaseContext())){
                    mPlay= MediaPlayer.create(Quiz_Save_Result_Activity.this, R.raw.dialog_aud);
                    mPlay.start();
                    Log.d("gdvcbhv","");
                   // ConnectionCheck.login_Dialog("Take_Quiz_Page",Quiz_Save_Result_Activity.this,false);
                }*/
              //  return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);

    }

    @Override
    public void on_piechart_set_data(Quiz_Save_Result_Activity quiz_calculation_activity, int int_skip_ques, int int_wng_ques, int int_rgt_ques) {

        Log.d("bbnbnbn",int_skip_ques+" "+int_wng_ques+" "+int_wng_ques+"  "+int_rgt_ques);
        Intent i=new Intent(getApplicationContext(),Result_Pie_Quiz_Activity.class);
        i.putParcelableArrayListExtra("quiz_ques_list",mDailyQuizList);
        i.putExtra("skip_ques",int_skip_ques);
        i.putExtra("wrng_ques",int_wng_ques);
        i.putExtra("rht_ques",int_rgt_ques);
        startActivityForResult(i,101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 101){
          clear();
          finish();
        }
    }
    public void clear()
    {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Sub_Answer_Array_List", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
