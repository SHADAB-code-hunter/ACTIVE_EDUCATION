package utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.Agent_login_Activity;
import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.R;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import callbacks.FInsi_Listener;
import callbacks.JsonRes_Listener;
import callbacks.SMS_RECEIVER_LISTENER;
import task.Login_Asynch;

import static utilities.UpdateValues.GUSET_SESSION;
import static utilities.UpdateValues.USER_SESSION;

/**
 * Created by GT on 11/15/2017.
 */

public class Gust_Regis_Fragment extends Fragment implements View.OnClickListener, JsonRes_Listener,SMS_RECEIVER_LISTENER {

    private EditText edt_uname, edt_email, edt_mobile,id_tv_otp;
    public IntentFilter intentFilter;
    private Button btn_login,id_btn;
    private Button id_Reg_here;
    private Activity activity;
    private RadioGroup id_radio_group;
    private int payment_option;
    private LinearLayout id_book_date_linear;
    private LinearLayout id_book_time_linaer;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView id_book_date,id_book_time;
    private Calendar c;
    private SmsReceiver receiver;
    private JSONObject responseObj;
    Map<String, String> map = new HashMap<>();
    private ProgressDialog progressDialog;
    private FrameLayout id_frm;
    private String Str_otp;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.guset_register, container, false);
        receiver = new SmsReceiver();//1
        intentFilter = new IntentFilter("android.permission.RECEIVE_SMS");//2
        SmsReceiver.bindListener((SMS_RECEIVER_LISTENER) Gust_Regis_Fragment.this);
        edt_uname = (EditText)rootView. findViewById(R.id.edt_uname);
        edt_mobile = (EditText) rootView.findViewById(R.id.edt_mobile);
        btn_login = (Button) rootView.findViewById(R.id.btn_login);
        id_btn = (Button) rootView.findViewById(R.id.id_btn);id_btn.setOnClickListener(this);
        id_Reg_here = (Button) rootView.findViewById(R.id.id_Reg_here);id_Reg_here.setOnClickListener(this);

        btn_login.setOnClickListener(this);
        id_frm=(FrameLayout)rootView.findViewById(R.id.id_frm);
        id_tv_otp=(EditText)rootView.findViewById(R.id.id_tv_otp);

        return rootView;
    }
    public void requestForSMS(final String mobile) {
        Log.d("mobils",""+mobile.length());
        Log.d("urlll",""+UrlEndpoints.OTP_GENERATE_API+mobile.toString().trim()+"/AUTOGEN");
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, UrlEndpoints.OTP_GENERATE_API+mobile.toString().trim()+"/AUTOGEN",new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseObj) {
                Log.d("OTP_GEN_Status", responseObj.toString());

                try {
                    // JSONObject responseObj = new JSONObject(responseObj);
                    if (responseObj.has("Status")) {

                        Gust_Regis_Fragment.this.responseObj=responseObj;
                        if(progressDialog!=null)
                            progressDialog.cancel();

                    } else if(responseObj.getString("Status").equals("Error"))
                    {
                        //Log.d("otp_send_status","error");
                        if(progressDialog!=null)
                            progressDialog.cancel();
                    }

                } catch (Exception e) {
                    Log.d("otp_send_status_exp",e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley error", "Error: " + error.getMessage());
              /*   Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);*/
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        requestQueue.add(req);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getAppContext().registerReceiver(receiver, intentFilter);//3
        //Log.d("count","3");
    }
    @Override
    public void onStop()
    {
        try {
            MyApplication.getAppContext().unregisterReceiver(receiver);
        }catch (Exception e)
        {
            //Log.d("exception"," Signup_UnRegister_receiver");
        }
        super.onStop();
    }
    private void otp_verification(JSONObject responseObj, String str_otp) {
        try {
        Log.d("verify_otp",""+responseObj+"/"+str_otp);
        JsonObjectRequest req= new JsonObjectRequest(Request.Method.GET, UrlEndpoints.OTP_VERIFY_API+ responseObj.getString("Details")+"/"+str_otp,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject responseObj) {
                            Log.d("OTP_verify_Status", ""+responseObj);

                            try {
                                // JSONObject responseObj = new JSONObject(responseObj);
                                if (responseObj.getString("Status").equals("Success")) {
                                    // boolean flag saying device is waiting for sms
                                    //animation popup for success
                                    if(!responseObj.getString("Details").equals("")) {
                                        String OTP_Session = responseObj.getString("Details");

                                        SharedPreferences shrd_otp_prf= MyApplication.getAppContext().getSharedPreferences(UpdateValues.OTP_Prefrence, 0);
                                        SharedPreferences.Editor editor = shrd_otp_prf.edit();
                                        editor.putString("otp_api_key", ""+responseObj);
                                        editor.commit();

                                        new Login_Asynch(Gust_Regis_Fragment.this,map,UrlEndpoints.GUSET_REGISTER,"Guset_Register").execute();

                                    }
                                } else if(responseObj.getString("Status").equals("Error"))
                                {
                                    //Log.d("otp_verify_status","error");
                                }
                            } catch (Exception e) {
                                //Log.d("otp_send_status_exp",e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley_error_otp_verify", "Error: " + error.getMessage());
                }
            });

        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        requestQueue.add(req);
        } catch (Exception e) {
           Log.d("nmnmnmn",""+e.getMessage());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login:
             //   startActivity(new Intent(getContext(),DashBoard_Activity.class));
                InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        progressDialog = new ProgressDialog(Gust_Regis_Fragment.this.getContext());
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                        progressDialog.setMessage(getString(R.string.Loading));
                if (edt_uname.getText().toString().isEmpty()) {

                    if(progressDialog!=null)
                        progressDialog.cancel();

                    Toast.makeText(Gust_Regis_Fragment.this.getContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                       else {
                            if (edt_mobile.getText().toString().isEmpty())
                                {
                                    if(progressDialog!=null)
                                        progressDialog.cancel();
                                    Toast.makeText(Gust_Regis_Fragment.this.getContext(), "Please Enter MOBILE", Toast.LENGTH_SHORT).show();

                                    return;
                                }else {

                                    map.put("name",""+edt_uname.getText());
                                    map.put("mobile",""+edt_mobile.getText());
                                    map.put("type","guest");

                                    id_frm.setVisibility(View.VISIBLE);

                                    requestForSMS(edt_mobile.getText().toString());

                            }
                      }
                break;


            case R.id.id_Reg_here:

                startActivity(new Intent(getContext(), Agent_login_Activity.class));

                break;
            case R.id.id_btn:

                otp_verification(responseObj,id_tv_otp.getText().toString());

                break;
        }
    }

    @Override
    public void on_res_litsner(JSONObject jsonObject, String restLocatDash) {
      //  Toast.makeText(Gust_Regis_Fragment.this.getContext(), ""+responseObj.getInt("exist"), Toast.LENGTH_SHORT).show();

        try { Log.d("hg__hghg", ""+jsonObject);
            Toast.makeText(Gust_Regis_Fragment.this.getContext(), ""+jsonObject, Toast.LENGTH_SHORT).show();
            if(jsonObject.has("exist"))
            {

                if(jsonObject.getInt("exist")==1)
                {
                    Toast.makeText(activity, ""+jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            else
            {
                SharedPreferences sharedPreferences3 = MyApplication.getAppContext().getSharedPreferences(USER_SESSION, 0);
                SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                editor3.putString(GUSET_SESSION,""+jsonObject);
                editor3.commit();

                ((FInsi_Listener)activity).onCall();

                Log.d("Guest_ssesion", jsonObject.toString());

            }
        } catch (Exception e) {
           Log.d("exception",e.getMessage());
        }
    }

    @Override
    public void on_otp_listener(String str_OTP) {
        Log.d("otppp",""+str_OTP);
        if(str_OTP!=null) {
            Log.d("ed_yotp",""+str_OTP);
          //  edt_otp.setText(str_OTP);
            Str_otp=str_OTP;
            id_tv_otp.setText(""+str_OTP);
            if(progressDialog!=null)
            progressDialog.cancel();
          //  otp_verification(responseObj,str_OTP);

        }
    }

    public interface fini__listenr{
        public void onCall();
    }

}
