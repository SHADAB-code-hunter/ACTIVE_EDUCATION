package com.gt.active_education;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import callbacks.Forgot_Close_Listener;
import fragment.Partner_Detail_Frag;
import utilities.App_Forgot_Password;
import utilities.Custom_List_Dialog;
import utilities.MyApplication;
import utilities.State_City_Search;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

/**
 * Created by GT on 8/26/2017.
 */

public class Agent_login_Activity extends AppCompatActivity implements View.OnClickListener,Forgot_Close_Listener {
    Button btn_sign_up;
    private boolean open=false;
    Button btn_login;
    ProgressDialog progressDialog;
    private TextView id_forgot_password;
    private App_Forgot_Password appForgotPassword;
    private EditText id_login_type;
    private Custom_List_Dialog custom_list_dialog;
    private String[] str_log_array;
    private Button sign_in_button,fb_in_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sign_in_button=(Button)findViewById(R.id.sign_in_button);sign_in_button.setOnClickListener(this);
        fb_in_button=(Button)findViewById(R.id.fb_in_button);fb_in_button.setOnClickListener(this);
        Button btn_sign_up=(Button)findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(this);
        id_login_type=(EditText)findViewById(R.id.id_login_type);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        str_log_array=getResources().getStringArray(R.array.login_array);
        id_forgot_password=(TextView)findViewById(R.id.id_forgot_password);id_forgot_password.setOnClickListener(this);
        id_login_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(!open) {
                    open = true;

                    custom_list_dialog = new Custom_List_Dialog(new Custom_List_Dialog.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s) {
                            custom_list_dialog.cancel();
                            id_login_type.setText(s);
                            open = false;
                        }
                    }, Agent_login_Activity.this,str_log_array);
                    custom_list_dialog.show();
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_sign_up:

                Intent i=new Intent(getBaseContext(),SignUp_Activity.class);
                i.putExtra("Status","Agent");
                startActivity(i);

                break;

             case R.id.fb_in_button:

                startActivity(new Intent(getBaseContext(),FacebookLoginActivity.class));

                break;

            case R.id.btn_login:

                String str_uname=((EditText)findViewById(R.id.edt_uname)).getText().toString().trim();
                String str_pass=((EditText)findViewById(R.id.edt_pass)).getText().toString().trim();

                if(str_uname==null) {
                    ((EditText) findViewById(R.id.edt_uname)).setError(" Please Enter UserName !!!");
                }else{
                    if(str_pass==null) {
                        ((EditText) findViewById(R.id.edt_pass)).setError(" Please Enter Password !!!");
                    }else{
                        if(id_login_type.getText().toString()==null) {
                            ((EditText) findViewById(R.id.id_login_type)).setError(" Please Enter Password !!!");
                        }else{
                            switch (id_login_type.getText().toString().trim())
                            {
                                case "Partner":
                                    set_sign_in(str_uname,str_pass);
                                    break;
                                case "School":

                                    startActivity(new Intent(getApplicationContext(),Partner_Activity.class));
                                    finish();
                                    break;
                                case "College":
                                    startActivity(new Intent(getApplicationContext(),Partner_Activity.class));
                                    finish();
                                    break;
                                case "University":
                                    startActivity(new Intent(getApplicationContext(),Partner_Activity.class));
                                    finish();
                                    break;
                                case "ITI / Diploma":
                                    startActivity(new Intent(getApplicationContext(),Partner_Activity.class));
                                    finish();
                                    break;
                                case "Coaching":
                                    startActivity(new Intent(getApplicationContext(),Partner_Activity.class));
                                    finish();
                                    break;

                                case "Training Center":
                                    startActivity(new Intent(getApplicationContext(),Partner_Activity.class));
                                    finish();
                                    break;
                            }
                        }
                    }
                }
                break;

            case R.id.id_forgot_password:
                appForgotPassword =new App_Forgot_Password(Agent_login_Activity.this);
                appForgotPassword.show();
                break;
            case R.id.sign_in_button:
               startActivity(new Intent(getApplicationContext(),GoogleSignInActivity.class));
                break;

        }
    }

    private void set_sign_in(String str_email, String str_pass)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.LogIn));
        Log.d("agent_login_res", ""+str_email+"  "+str_pass);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.Agent_Login+"email="+str_email+"&pwd="+str_pass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Toast.makeText(Agent_login_Activity.this, str_email+"   "+str_pass+"   "+response.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("a_login",response.toString());
                            //{"data":[{"email":"sa@gmail.com","token":"wvCfUJwlBE"}]}
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
                                startActivity(new Intent(getApplicationContext(),Agent_Profile_Activity.class));
                                progressDialog.cancel();
                                finish();
                            }else if(jObj.has("exist"))
                            {
                                progressDialog.cancel();
                                // ConnectionCheck.user_Already_exist(SignUpActivity.this,"User Already Exist !!!");
                                Toast.makeText(Agent_login_Activity.this, "User Already Exist !!!", Toast.LENGTH_SHORT).show();

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

                return null;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        requestQueue.add(stringRequest);

    }
    @Override
    public void on_forgot_close() {
        appForgotPassword.cancel();
    }
}
