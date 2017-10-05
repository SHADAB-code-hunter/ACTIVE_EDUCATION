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
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.R;

import org.json.JSONException;
import org.json.JSONObject;

import callbacks.SignUp_Pager_Swape_Listener;
import utilities.MyApplication;
import utilities.SmsReceiver;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

/**
 * Created by GT on 7/8/2017.
 */

public class OTP_Verification_Frg extends Fragment implements View.OnClickListener, SmsReceiver.SmsListener {

    SignUp_Pager_Swape_Listener mListener;
    Button btn_otp_Verify;
    private boolean bln_status=false;
    private EditText edt_otp;
    public  SmsReceiver receiver;
    public IntentFilter intentFilter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (SignUp_Pager_Swape_Listener) context;
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

        View rootView = inflater.inflate(R.layout.otp_verify_frg_layout, container, false);

        btn_otp_Verify=(Button)rootView.findViewById(R.id.btn_otp_verify);
        btn_otp_Verify.setOnClickListener(this);
        receiver = new SmsReceiver();//1
        intentFilter = new IntentFilter("android.permission.RECEIVE_SMS");//2
        edt_otp=(EditText)rootView.findViewById(R.id.edt_otp);

        SmsReceiver.bindListener(OTP_Verification_Frg.this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_otp_verify:

                String Str_otp_typing_manually= edt_otp.getText().toString();

                SharedPreferences  shrd_otp_prf =MyApplication.getAppContext().getSharedPreferences(UpdateValues.OTP_Prefrence, 0);
                final String str_otp_api_key = shrd_otp_prf.getString("otp_api_key", "");
                final String str_otp_session = shrd_otp_prf.getString("otp_session", "");
                final String str_otp_mbl = shrd_otp_prf.getString("otp_mbl", "");
                Log.d("gfdser", Str_otp_typing_manually + "  " + str_otp_api_key + "  " + str_otp_session + "   " + str_otp_mbl);

                otp_verification(str_otp_api_key, str_otp_session, str_otp_mbl, Str_otp_typing_manually);

                break;

        }
    }

    @Override
    public void messageReceived(String str_otp) {

        bln_status=true;
        if(bln_status) {
            Log.d("edyotp",""+str_otp); //otp_array[0] + "  " + otp_array[1] + " " + otp_array[2] + " " + otp_array[3] + " " + otp_array[4] + " " + otp_array[5]);
            edt_otp.setText(str_otp);

        }

    }

    private void otp_verification(final String str_otp_api_key, final String str_otp_session, final String str_otp_mbl, String str_otp) {
        Log.d("verify_otp",""+UrlEndpoints.OTP_VERIFY_API+str_otp_api_key+str_otp_session+"/"+str_otp);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, UrlEndpoints.OTP_VERIFY_API+str_otp_session+"/"+str_otp,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseObj) {
                Log.d("OTP_verify_Status", responseObj.toString());

                try {
                    // JSONObject responseObj = new JSONObject(responseObj);
                    if (responseObj.getString("Status").equals("Success")) {
                        // boolean flag saying device is waiting for sms
                        //animation popup for success
                        if(!responseObj.getString("Details").equals("")) {
                            String OTP_Session = responseObj.getString("Details");

                            SharedPreferences shrd_otp_prf=MyApplication.getAppContext().getSharedPreferences(UpdateValues.OTP_Prefrence, 0);

                            SharedPreferences.Editor editor = shrd_otp_prf.edit();
                            editor.putString("otp_api_key", str_otp_api_key);
                            editor.putString("otp_session", str_otp_session);
                            editor.putString("otp_mbl", str_otp_mbl);
                            Log.d("mobilevalue",""+str_otp_mbl);
                            editor.commit();
                            //Log.d("varify",UrlEndpoints.OTP_VERIFY_API+"  "+OTP_Session+"  "+str_otp_mbl);
                            //contibuew
                          //  set_sign_up();

                            mListener.onPager_swap_method();
                        }
                    } else if(responseObj.getString("Status").equals("Error"))
                    {
                        //Log.d("otp_verify_status","error");
                    }
                } catch (JSONException e) {
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
}
