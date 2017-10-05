package com.gt.active_education;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.Map;

import utilities.MyApplication;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

/**
 * Created by GT on 6/29/2017.
 */

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    Button btn_sign_up;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button btn_sign_up=(Button)findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(this);

        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_sign_up:

                Intent i=new Intent(getBaseContext(),Sign_Up_Process_Activity.class);
                i.putExtra("Status","User");
                startActivity(i);
                break;

            case R.id.btn_login:

                String str_uname=((EditText)findViewById(R.id.edt_uname)).getText().toString().trim();
                String str_pass=((EditText)findViewById(R.id.edt_pass)).getText().toString().trim();

              //  set_sign_in("san@gmail.com","123456");
                  set_sign_in(str_uname,str_pass);
             break;
        }
    }

    private void set_sign_in( String str_email, String str_pass)
    {
        Log.d("urfgdffgdldg", ""+str_email+"  "+str_pass);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.LOGIN_API+str_email+"&pwd="+str_pass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("user_login_rers",response.toString());
//{"data":[{"email":"ahmed.shadab@gmail.com","token":"2vvjEQ7Opi","type":"user"}]}
                            //   mListener.onPager_swap_method();

                            if(!jObj.has("exist")) {
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("login_Status", "L_OK");
                                editor.putString("Login_Token", jsonObject.getString("token"));
                                editor.putString("email", jsonObject.getString("email"));
                                editor.putString("type", jsonObject.getString("type"));
                                editor.commit();
                               // editor.putString("mobile", jsonObject.getString("mobile"));
                                Intent intent = new Intent(Login_Activity.this, User_Profile_Activity.class);
                                startActivity(intent);
                                finish();
                            }else if(jObj.has("exist"))
                            {
                               // ConnectionCheck.user_Already_exist(SignUpActivity.this,"User Already Exist !!!");
                                Toast.makeText(Login_Activity.this, "User Already Exist !!!", Toast.LENGTH_SHORT).show();
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        requestQueue.add(stringRequest);

    }

}
