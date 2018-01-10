package com.gt.active_education;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import pojo.Staff;
import task.NORMAL_ASYNCHTASK;
import utilities.App_Static_Method;
import utilities.MyApplication;
import utilities.UrlEndpoints;

import static utilities.UrlEndpoints.TOP_INNER_SCHOOL_FILTER_DEFAULT;

/**
 * Created by GT on 8/24/2017.
 */

public class Test2_Activity extends AppCompatActivity {

    Map<String,String> map=new HashMap<>();
    Button mbtn1,mbtn2,mbtn3;
    private JSONObject jobj_common=new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("food_items_id","155");
        jsonObject.put("order_quantity","2");
        jsonObject.put("subtotal","2");
        jsonObject.put("total","100");
        jsonObject.put("grand_total","300");

            JSONObject jsonObject2=new JSONObject();

            jsonObject2.put("food_items_id","155");
            jsonObject2.put("order_quantity","2");
            jsonObject2.put("subtotal","2");
            jsonObject2.put("total","100");
            jsonObject2.put("grand_total","300");
            JSONArray jsonArray2=new JSONArray();
            jsonArray2.put(jsonObject2);

            JSONArray jsonArray=new JSONArray();
            jsonArray.put(jsonObject);
            jsonArray.put(jsonObject);
            jobj_common.put("order_info", jsonArray);
            jobj_common.put("access_info", jsonArray2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        set_sign_in();
    }

    private void set_sign_in()
    {

        // Log.d("urfgdffgdldg", ""+str_email+"  "+str_pass);"?jsonData="
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.REGOISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("user_login_rers",""+response.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map=new HashMap<>();
                try {
                    map.put("json",""+jobj_common.toString());
                    Log.d("cnncnjnj",""+map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        requestQueue.add(stringRequest);

    }


}

