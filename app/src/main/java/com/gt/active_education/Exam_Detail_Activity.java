package com.gt.active_education;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utilities.MyApplication;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

/**
 * Created by GT on 8/30/2017.
 */

public class Exam_Detail_Activity extends AppCompatActivity {

    TextView id,name,e_seats,exam_date,e_info;
    ProgressDialog progressDialog;
    String str_exam_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        id=(TextView)findViewById(R.id.id);
        name=(TextView)findViewById(R.id.name);
        e_seats=(TextView)findViewById(R.id.e_seats);
        exam_date=(TextView)findViewById(R.id.exam_date);
        e_info=(TextView)findViewById(R.id.e_info);
        if(!getIntent().equals(null))
        {
            str_exam_id=getIntent().getStringExtra("id");
        }

        get_Offer_Detail();
    }

    private void get_Offer_Detail() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.Loading));
        //  Log.d("agent_login_res", ""+str_email+"  "+str_pass);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.GET_EXAM_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("srugfgts",response.toString());
                            if(!jObj.has("msg")) {
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                id.setText(jsonObject.getString("id"));
                                name.setText("name   "+jsonObject.getString("name"));
                                e_seats.setText("Posts   "+jsonObject.getString("e_seats"));
                                exam_date.setText("Exam Date    "+jsonObject.getString("exam_date"));
                                e_info.setText("Exam info    "+jsonObject.getString("e_info"));

                                progressDialog.cancel();

                            }else if((""+(jObj.getString("msg"))).equals("0"))
                            {
                                progressDialog.cancel();
                                //   Toast.makeText(getBaseContext(), "User Already Exist !!!", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            progressDialog.cancel();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<String, String>();
                Log.d("sgsgsgsg",str_exam_id);
                map.put("exam",""+str_exam_id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        requestQueue.add(stringRequest);
    }

}
