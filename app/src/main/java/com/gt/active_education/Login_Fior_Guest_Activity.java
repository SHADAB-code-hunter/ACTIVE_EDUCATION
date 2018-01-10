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

import java.util.Map;

import callbacks.Forgot_Close_Listener;
import callbacks.Forgot_password;
import task.Asynch_Obj;
import utilities.Guest_App_Forgot_Password;
import utilities.Guset_type_dialog;
import utilities.MyApplication;
import utilities.UpdateValues;
import utilities.UrlEndpoints;
import utilities.Migrate_Dialog;

import static utilities.App_Static_Method.toMap;
import static utilities.UpdateValues.GUSET_SESSION;
import static utilities.UpdateValues.USER_SESSION;

/**
 * Created by GT on 8/26/2017.
 */

public class Login_Fior_Guest_Activity extends AppCompatActivity implements View.OnClickListener,Forgot_Close_Listener, Forgot_password {
    Button btn_sign_up;
    private boolean open=false;
    Button btn_login;
    ProgressDialog progressDialog;
    private TextView id_forgot_password;
    private Guest_App_Forgot_Password appForgotPassword;
    private EditText id_login_type;
    private Guset_type_dialog Guset_type_dialog;
    private String[] str_log_array;
    private Button sign_in_button,fb_in_button;
    private TextView id_Reg_here;
    private Migrate_Dialog userBookingDialog;

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
        id_Reg_here=(TextView)findViewById(R.id.id_Reg_here);id_Reg_here.setOnClickListener(this);
        id_login_type=(EditText)findViewById(R.id.id_login_type);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        str_log_array=getResources().getStringArray(R.array.guest_login_array);
        id_forgot_password=(TextView)findViewById(R.id.id_forgot_password);id_forgot_password.setOnClickListener(this);
        id_login_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(!open) {
                    open = true;
                    Guset_type_dialog = new Guset_type_dialog(new Guset_type_dialog.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s) {
                            Guset_type_dialog.cancel();
                            id_login_type.setText(s);
                            open = false;
                        }
                    }, Login_Fior_Guest_Activity.this,str_log_array);
                    Guset_type_dialog.show();
                }

                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
             case R.id.fb_in_button:

                startActivity(new Intent(getBaseContext(),FacebookLoginActivity.class));

                break;

            case R.id.btn_login:

                String str_uname=((EditText)findViewById(R.id.edt_uname)).getText().toString().trim();
                String str_pass=((EditText)findViewById(R.id.edt_pass)).getText().toString().trim();

                if((id_login_type.getText().toString()).equals("Please Choose Login Type")) {
                    Toast.makeText(this, "Please Choose Login Type", Toast.LENGTH_SHORT).show();
                    return;
                }

                    if(str_uname==null)
                    {
                        ((EditText) findViewById(R.id.edt_uname)).setError(" Please Enter UserName !!!");
                    }else {
                        if (str_pass == null) {
                            ((EditText) findViewById(R.id.edt_pass)).setError(" Please Enter Password !!!");
                        } else {
                            if (id_login_type.getText().toString() == null) {
                                ((EditText) findViewById(R.id.id_login_type)).setError(" Please Enter Password !!!");
                            } else {
                                switch (id_login_type.getText().toString().trim()) {

                                    case "Student Login" :
                                        set_sign_in(str_uname, str_pass, UrlEndpoints.G_LOGIN_API, "0");

                                        break;
                                    case "School Login":
                                        set_sign_in(str_uname, str_pass, UrlEndpoints.SEAT_PROVIDER_SIGNIN, "1");

                                        break;
                                    case "College Login":
                                        set_sign_in(str_uname, str_pass, UrlEndpoints.SEAT_PROVIDER_SIGNIN, "2");

                                        break;
                                    case "University Login":
                                        set_sign_in(str_uname, str_pass, UrlEndpoints.SEAT_PROVIDER_SIGNIN, "3");

                                        break;
                                    case "ITI College Login":
                                        set_sign_in(str_uname, str_pass, UrlEndpoints.SEAT_PROVIDER_SIGNIN, "4");

                                        break;
                                    case "Coaching Login":
                                        set_sign_in(str_uname, str_pass, UrlEndpoints.SEAT_PROVIDER_SIGNIN, "5");

                                        break;

                                    case "Training Center Login":
                                        set_sign_in(str_uname, str_pass, UrlEndpoints.SEAT_PROVIDER_SIGNIN, "6");

                                        break;
                                    case "Partner Login":
                                        set_sign_in(str_uname, str_pass, UrlEndpoints.Agent_Login, "7");
                                        break;
                                }
                            }
                        }
                    }

                break;

            case R.id.id_forgot_password:
                appForgotPassword =new Guest_App_Forgot_Password(Login_Fior_Guest_Activity.this);
                appForgotPassword.show();
                break;
            case R.id.sign_in_button:
               startActivity(new Intent(getApplicationContext(),GoogleSignInActivity.class));
                break;
            case R.id.id_Reg_here:
                 userBookingDialog=new Migrate_Dialog(new Migrate_Dialog.User_Boking_Listener() {
                    @Override
                    public void on_dialog__listener(JSONObject map) {
                        try {
                            new Asynch_Obj(new Asynch_Obj.OBJ_Lister() {
                                @Override
                                public void on_lis_obj(JSONObject jsonObject, String str_key) {
                                    Log.d("jsonObject",""+jsonObject.toString());

                                    try {

                                        if(jsonObject.has("exist"))
                                        {
                                             if(jsonObject.getInt("exist")==1)
                                            {
                                                //  Login_Fior_Guest_Activity.this.finish();
                                                userBookingDialog.cancel();
                                                Toast.makeText(Login_Fior_Guest_Activity.this, "Already Registered Please Login with Your ID / Password ", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                            SharedPreferences sharedPreferences3 = MyApplication.getAppContext().getSharedPreferences(USER_SESSION, 0);
                                            SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                                            editor3.putString(GUSET_SESSION,""+jsonObject.getJSONArray("data").getJSONObject(0));
                                            editor3.commit();
                                            userBookingDialog.cancel();
                                            Login_Fior_Guest_Activity.this.finish();

                                    } catch (Exception e) {
                                       Log.d("excep",""+e.getMessage());
                                    }
                                }
                            }, map.getString("URL_TYPE"),toMap(map), "migrate").execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },Login_Fior_Guest_Activity.this);
                userBookingDialog.show();
                break;

        }
    }

    private void set_sign_in(final String str_mobile, final String str_pass,String str_url,String str_type)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.LogIn));
        Log.d("agent_login_res",str_url+"  "+ str_type+"  "+str_mobile+"  "+str_pass+" "+ str_type);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,str_url+"mobile="+str_mobile+"&pwd="+str_pass+"&type="+str_type,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                        //    Toast.makeText(Agent_login_Activity.this, str_mobile+"   "+str_pass+"   "+response.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("a_login",response.toString());

                            SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.G_PARTNER_Prefrence, 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("U_SESSUIN", ""+jObj.getJSONArray("data").getJSONObject(0));
                            editor.commit();
                            if(jObj.getJSONArray("data").getJSONObject(0).getString("type").equalsIgnoreCase("guest"))
                            startActivity(new Intent(getApplicationContext(),User_Profile_Activity.class));finish();
                            if(jObj.getJSONArray("data").getJSONObject(0).getString("type").equalsIgnoreCase("agent"))
                                startActivity(new Intent(getApplicationContext(),Agent_Profile_Activity.class));finish();

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
       // appForgotPassword.cancel();
    }

    @Override
    public void on_get_message() {

        appForgotPassword.cancel();

    }
}
