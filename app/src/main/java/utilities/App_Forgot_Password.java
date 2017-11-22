package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.Agent_Profile_Activity;
import com.gt.active_education.Agent_login_Activity;
import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.FacebookLoginActivity;
import com.gt.active_education.GoogleSignInActivity;
import com.gt.active_education.R;
import com.gt.active_education.SignUp_Activity;
import com.gt.active_education.Sign_Up_Process_Activity;
import com.gt.active_education.Target_Circle_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import callbacks.Call_Dilaog_Listener;
import callbacks.Forgot_Close_Listener;
import callbacks.Forgot_password;

/**
 * Created by GT on 9/18/2017.
 */

public class App_Forgot_Password extends Dialog implements View.OnClickListener {
    RelativeLayout id_relative_dialog;
    private View btnRed;
    private Activity act;
    private ImageView id_website,id_video_call,id_sms,id_call;
    private Button btn_forgot;

    Button btn_sign_up;
    private boolean open=false;
    Button btn_login;
    ProgressDialog progressDialog;
    private App_Forgot_Password appForgotPassword;
    private EditText id_login_type;
    private Custom_List_Dialog custom_list_dialog;
    private String[] str_log_array;
    private Button sign_in_button,fb_in_button;
    private TextView id_Reg_here;


    public App_Forgot_Password(Activity act) {
        super(act);
        this.act=act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_forgot);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn_forgot=(Button)findViewById(R.id.btn_forgot);btn_forgot.setOnClickListener(this);
        id_login_type=(EditText)findViewById(R.id.id_login_type);
        str_log_array=act.getResources().getStringArray(R.array.login_array);
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
                    }, App_Forgot_Password.this.getContext(),str_log_array);
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
            case R.id.btn_forgot:

                String str_uname=((EditText)findViewById(R.id.edt_uname)).getText().toString().trim();

                if((id_login_type.getText().toString()).equals("Please Choose Login Type")) {
                    Toast.makeText(act, "Please Choose Login Type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(str_uname==null)
                {
                    ((EditText) findViewById(R.id.edt_uname)).setError(" Please Enter UserName !!!");
                }else {
                   
                        if (id_login_type.getText().toString() == null) {
                            ((EditText) findViewById(R.id.id_login_type)).setError(" Please Enter Password !!!");
                        } else {
                            switch (id_login_type.getText().toString().trim()) {

                                case "Student":
                                    set_sign_in(str_uname,  UrlEndpoints.FORGOT_API, "user");

                                    break;
                                case "School":
                                    set_sign_in(str_uname,  UrlEndpoints.FORGOT_API, "user");

                                    break;
                                case "College":
                                    set_sign_in(str_uname,  UrlEndpoints.FORGOT_API, "user");

                                    break;
                                case "University":
                                    set_sign_in(str_uname,  UrlEndpoints.FORGOT_API, "user");

                                    break;
                                case "ITI / Diploma":
                                    set_sign_in(str_uname,  UrlEndpoints.FORGOT_API, "user");

                                    break;
                                case "Coaching":
                                    set_sign_in(str_uname,  UrlEndpoints.FORGOT_API, "user");

                                    break;

                                case "Training Center":
                                    set_sign_in(str_uname,  UrlEndpoints.FORGOT_API, "user");

                                    break;
                                case "Partner":
                                    set_sign_in(str_uname,  UrlEndpoints.FORGOT_API, "agent");
                                    break;
                            }
                        }
                    }
               // }

                break;

        }
    }

    private void set_sign_in(final String str_mobile,String str_type,String str_user)
    {
        progressDialog = new ProgressDialog(act);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage("Sending Message .......");
        progressDialog.cancel();
        Log.d("agent_login_res", str_type+"  "+str_mobile+" "+ str_user);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,str_type+"mobile="+str_mobile+"&type="+str_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("a_login",""+jObj);
                            progressDialog.cancel();
                            ((Forgot_password)act).on_get_message();

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



}
