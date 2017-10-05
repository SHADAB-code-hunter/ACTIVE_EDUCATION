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

import callbacks.SignUp_Pager_Swape_Listener;
import utilities.SmsReceiver;
import utilities.UrlEndpoints;
import utilities.MyApplication;
import utilities.UpdateValues;

/**
 * Created by GT on 6/7/2017.
 */

public class Num_Verify_Fragment extends Fragment {
    SignUp_Pager_Swape_Listener mListener;
    Sign_Up_Process_Activity signUpProcessActivity;
    private SharedPreferences shrd_otp_prf;
    public  SmsReceiver receiver;
    public IntentFilter intentFilter;
    private boolean bln_status=false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (SignUp_Pager_Swape_Listener) context;
            signUpProcessActivity= (Sign_Up_Process_Activity)context;
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
        final EditText editText=((EditText)rootView.findViewById(R.id.id_edt_number));

        receiver = new SmsReceiver();//1

        rootView.findViewById(R.id.id_verify_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_mobile_num=editText.getText().toString().trim();
                requestForSMS(str_mobile_num);

            }
        });

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
                           mListener.onPager_swap_method();
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

}
