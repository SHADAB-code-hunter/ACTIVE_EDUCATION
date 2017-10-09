package com.gt.active_education;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Search_Spinner.SearchableSpinner;
import callbacks.Forgot_Close_Listener;
import utilities.App_Forgot_Password;
import utilities.App_Static_Method;
import utilities.MyApplication;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

import static extras.Keys.KEY_USER_LOGIN.KEY_EMAIL;

/**
 * Created by GT on 6/29/2017.
 */

public class User_Login_Activity extends AppCompatActivity implements View.OnClickListener, Forgot_Close_Listener {

    Button btn_sign_up;
    Button btn_login;
    private ProgressDialog progressDialog;
    private TextView id_forgot_password;
    private  App_Forgot_Password appForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        id_forgot_password=(TextView)findViewById(R.id.id_forgot_password);
        Button btn_sign_up=(Button)findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(this);
       /* SearchableSpinner spnr_login=(SearchableSpinner)findViewById(R.id.id_spn_login);

        ArrayList arrayList=new ArrayList();
        arrayList.add("User"); arrayList.add("School Partner"); arrayList.add("College Partner"); arrayList.add("Institute Partner"); arrayList.add("ITI/ DIPLOMA Partner"); arrayList.add("Coaching Partner");
        arrayList.add("Training Partner");
        ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_list_item,arrayList);

        spnr_login.setAdapter(arrayAdapter);*/
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        id_forgot_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_sign_up:

                Intent i=new Intent(getBaseContext(),SignUp_Activity.class);
                i.putExtra("Status","User");
                startActivity(i);
                break;

            case R.id.btn_login:

                String str_uname=((EditText)findViewById(R.id.edt_uname)).getText().toString().trim();
                String str_pass=((EditText)findViewById(R.id.edt_pass)).getText().toString().trim();

                //  set_sign_in("san@gmail.com","123456");
                progressDialog = new ProgressDialog(this);
                progressDialog.setCancelable(true);
                progressDialog.show();
                progressDialog.setMessage(getString(R.string.LogIn));

                if(!App_Static_Method.is_login(User_Login_Activity.this))
                set_sign_in(str_uname,str_pass);
                else {
                    Toast.makeText(this, "You have Already Login !!!!", Toast.LENGTH_SHORT).show();
                }
             break;

            case R.id.id_forgot_password:
                appForgotPassword =new App_Forgot_Password(User_Login_Activity.this);
                appForgotPassword.show();
                break;
        }
    }

    private void set_sign_in(final String str_email, final String str_pass)
    {
        Log.d("urfgdffgdldg", ""+str_email+"  "+str_pass);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.LOGIN_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("user_login_rers",response.toString());
                            //{"data":[{"email":"ahmed.shadab@gmail.com","token":"2vvjEQ7Opi","type":"user"}]}
                            //   mListener.onPager_swap_method();

                            if(jObj.has("data")) {
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                App_Static_Method.user_session(User_Login_Activity.this,jsonObject);
                                // editor.putString("mobile", jsonObject.getString("mobile"));
                                progressDialog.cancel();
                                Intent intent = new Intent(User_Login_Activity.this, DashBoard_Activity.class);
                                startActivity(intent);
                                finish();
                            }else if(jObj.has("msg"))
                            {
                                // ConnectionCheck.user_Already_exist(SignUpActivity.this,"User Already Exist !!!");
                                progressDialog.cancel();
                                Toast.makeText(User_Login_Activity.this, jObj.getString("msg")+" !!!! ", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
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
                map.put("email",str_email);
                map.put("pwd",str_pass);
                return map;
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
