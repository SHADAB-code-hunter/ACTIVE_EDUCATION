package fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.R;
import com.gt.active_education.Sign_Up_Process_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import callbacks.SignUp_Pager_Swape_Listener;
import utilities.SmsReceiver;
import utilities.UrlEndpoints;
import utilities.MyApplication;
import utilities.UpdateValues;

/**
 * Created by GT on 6/7/2017.
 */

public class Num_Verify_Fragment extends Fragment implements View.OnClickListener {
    SignUp_Pager_Swape_Listener mListener;
    Sign_Up_Process_Activity signUpProcessActivity;
    private SharedPreferences shrd_otp_prf;
    public  SmsReceiver receiver;
    public IntentFilter intentFilter;
    private boolean bln_status=false;
    private FrameLayout id_lg_typ1,id_lg_typ2,id_lg_typ3;
    private FrameLayout id_check1,id_check2,id_check3;
    private Map<String,String> map=new HashMap<>();
    private EditText edt_num;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (SignUp_Pager_Swape_Listener) context;
          //  signUpProcessActivity= (Sign_Up_Process_Activity)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ExampleFragmentCallbackInterface ");
        }
    }

    @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.num_verify_frg_layout, container, false);
        edt_num=((EditText)rootView.findViewById(R.id.id_edt_number));
        id_lg_typ1=(FrameLayout)rootView.findViewById(R.id.id_lg_typ1);id_lg_typ1.setOnClickListener(this);
        id_lg_typ2=(FrameLayout)rootView.findViewById(R.id.id_lg_typ2);id_lg_typ2.setOnClickListener(this);
        id_lg_typ3=(FrameLayout)rootView.findViewById(R.id.id_lg_typ3);id_lg_typ3.setOnClickListener(this);
        id_check1=(FrameLayout)rootView.findViewById(R.id.id_check1);
        id_check2=(FrameLayout)rootView.findViewById(R.id.id_check2);
        id_check3=(FrameLayout)rootView.findViewById(R.id.id_check3);
        Log.d("kjkj",""+id_check1.getVisibility());
      //  receiver = new SmsReceiver();//1

        rootView.findViewById(R.id.id_verify_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_mobile_num=edt_num.getText().toString().trim();
                if(str_mobile_num==null ) {

                }else {
                    if((id_check1.getVisibility()==View.VISIBLE) || (id_check2.getVisibility()==View.VISIBLE) || (id_check3.getVisibility()==View.VISIBLE)) {
                        requestForSMS(str_mobile_num);
                    }else {
                        Toast.makeText(Num_Verify_Fragment.this.getContext(), "Please Choose Any Login Type !!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        //SmsReceiver.bindListener((SMS_RECEIVER_LISTENER)this);
        return rootView;
    }


    public void requestForSMS(final String mobile) {
        Log.d("mobils",""+mobile.length());
        Log.d("urlll",""+UrlEndpoints.OTP_GENERATE_API+mobile.toString().trim()+"/AUTOGEN");
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, UrlEndpoints.OTP_GENERATE_API+mobile.toString().trim()+"/AUTOGEN",new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseObj) {
                Log.d("OTP_GEN_Status", responseObj.toString());

                try {
                    // JSONObject responseObj = new JSONObject(responseObj);
                    if (responseObj.getString("Status").equals("Success")) {
                        // boolean flag saying device is waiting for sms
                        //animation popup for success
                        if(!responseObj.getString("Details").equals("")) {
                            String OTP_Session = responseObj.getString("Details");
                            shrd_otp_prf= MyApplication.getAppContext().getSharedPreferences(UpdateValues.OTP_Prefrence, 0);

                            SharedPreferences.Editor editor = shrd_otp_prf.edit();
                            editor.putString("otp_api_key",UrlEndpoints.OTP_API_KEY);
                            editor.putString("otp_session", OTP_Session);
                            editor.putString("otp_mbl", mobile);
                            Log.d("mbl_num",""+mobile);
                            editor.commit();
                            //Log.d("valueesscscddf",UrlEndpoints.OTP_API_KEY+"  "+OTP_Session+"  "+mobile);

                              /*  Intent intent = new Intent("android.permission.RECEIVE_SMS");
                                sendBroadcast(intent);*/
                          //  SmsReceiver.bindListener(Num_Verify_Fragment.this);
                           mListener.onPager_swap_method("Login_page");
                            Log.d("dered",""+mobile);
                        }
                    } else if(responseObj.getString("Status").equals("Error"))
                    {
                        //Log.d("otp_send_status","error");
                    }

                } catch (JSONException e) {
                  /*  Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();*/
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_lg_typ1:
                id_check1.setVisibility(View.VISIBLE);
                id_check2.setVisibility(View.INVISIBLE);
                id_check3.setVisibility(View.INVISIBLE);
                map.put("type","seater");
            //    id_lg_typ1.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.color1)));
                SharedPreferences sharedPreferences1 = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE, 0);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.putString("type","seater");
                editor1.commit();
              //  editor1.putString("NUMBER",edt_num.toString());
                break;
            case R.id.id_lg_typ2:
                id_check1.setVisibility(View.INVISIBLE);
                id_check2.setVisibility(View.VISIBLE);
                id_check3.setVisibility(View.INVISIBLE);
              //  id_lg_typ2.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.color1)));
                map.put("type","agent");
                SharedPreferences sharedPreferences2 = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE, 0);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putString("type","agent");
                editor2.commit();
                Log.d("mama",sharedPreferences2.getString("type","NA"));
              //  editor2.putString("NUMBER",edt_num.toString());

                break;
            case R.id.id_lg_typ3:
                id_check1.setVisibility(View.INVISIBLE);
                id_check2.setVisibility(View.INVISIBLE);
                id_check3.setVisibility(View.VISIBLE);
                map.put("type","user");
               // id_lg_typ3.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.color1)));
                SharedPreferences sharedPreferences3 = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE, 0);
                SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                editor3.putString("type","user");
                editor3.commit();
               // editor3.putString("NUMBER",edt_num.toString());
                break;
        }
    }

  /*  @Override
    public void on_otp_listener(String str_OTP) {

    }*/
}
