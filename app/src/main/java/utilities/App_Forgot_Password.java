package utilities;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.R;
import com.gt.active_education.User_Login_Activity;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import Zend_Chat.UserProfile;
import Zend_Chat.UserProfileStorage;
import callbacks.Call_Dilaog_Listener;
import callbacks.Forgot_Close_Listener;
import callbacks.Form_Responce_Listener;

/**
 * Created by GT on 9/18/2017.
 */

public class App_Forgot_Password extends Dialog implements View.OnClickListener {
    RelativeLayout id_relative_dialog;
    private View btnRed;
    private Activity act;
    private ImageView id_website,id_video_call,id_sms,id_call;
    private Button btn_forgot;

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

    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_website:
                ((Call_Dilaog_Listener)act).on_id_listener("WEBSITE");
                break;

            case R.id.btn_forgot:
                // call api forgot
                ((Forgot_Close_Listener)act).on_forgot_close();

                break;
        }
    }
/*
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
                                Toast.makeText(User_Login_Activity.this, "Email or Password is not Correct !!!! ", Toast.LENGTH_SHORT).show();

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
*/

}