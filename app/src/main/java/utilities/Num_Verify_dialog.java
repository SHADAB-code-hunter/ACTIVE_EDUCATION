package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import callbacks.Num_Verify_Listener;
import callbacks.SignUp_Pager_Swape_Listener;
import fragment.Num_Verify_Fragment;
import fragment.OTP_Verification_Frg;

/**
 * Created by GT on 9/9/2017.
 */

public class Num_Verify_dialog extends Dialog implements View.OnClickListener ,
        SmsReceiver.SmsListener{
    RelativeLayout id_relative_dialog;
    private View btnRed;
    private Activity act;
    private ImageView id_website,id_video_call,id_sms,id_call;
    private EditText id_edt_m_num;

    SignUp_Pager_Swape_Listener mListener;
    Sign_Up_Process_Activity signUpProcessActivity;
    private SharedPreferences shrd_otp_prf;
    public  SmsReceiver receiver;
    public IntentFilter intentFilter;
    private boolean bln_status=false;
    private LinearLayout id_otp_verify;
    private Button id_otp_btn_verif;
    private EditText edt_otp;
    private TextView id_otp;
    private LinearLayout id_linear_upper;
    private String str_num;

    public Num_Verify_dialog(Activity act,String str_num) {
        super(act);
        this.act=act;
        this.str_num=str_num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_num_verify);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        id_otp=(TextView)findViewById(R.id.id_otp);
        id_edt_m_num=(EditText)findViewById(R.id.id_edt_m_num);
        id_edt_m_num.setText(str_num);
        id_otp_btn_verif=(Button)findViewById(R.id.id_otp_btn_verif);
       // receiver = new SmsReceiver(OTP_Verification_Frg.this);//1
        edt_otp=(EditText)findViewById(R.id.edt_otp);
        id_otp_verify=(LinearLayout)findViewById(R.id.id_otp_verify);
        id_linear_upper=(LinearLayout)findViewById(R.id.id_linear_upper);

        findViewById(R.id.id_btn_verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_mobile_num=id_edt_m_num.getText().toString().trim();
                Num_Verify_Fragment verify_fragment=new Num_Verify_Fragment();
              //  verify_fragment.requestForSMS(str_mobile_num);
              //  requestForSMS(str_mobile_num);
                App_Static_Method.requestForSMS(str_mobile_num,act);
                Log.d("uuurl",""+UrlEndpoints.OTP_GENERATE_API+str_mobile_num+"/AUTOGEN");
              //  new Num_Req_OTP_TASK(UrlEndpoints.OTP_GENERATE_API+str_mobile_num+"/AUTOGEN").execute();
                id_otp_verify.setVisibility(View.VISIBLE);
                id_linear_upper.setVisibility(View.GONE);
            }
        });

        id_otp_btn_verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Str_otp_typing_manually= edt_otp.getText().toString();
                if(Str_otp_typing_manually!=null) {
                    SharedPreferences shrd_otp_prf = MyApplication.getAppContext().getSharedPreferences(UpdateValues.OTP_Prefrence, 0);
                    final String str_otp_api_key = shrd_otp_prf.getString("otp_api_key", "");
                    final String str_otp_session = shrd_otp_prf.getString("otp_session", "");
                    final String str_otp_mbl = shrd_otp_prf.getString("otp_mbl", "");
                    Log.d("gfdser", Str_otp_typing_manually + "  " + str_otp_api_key + "  " + str_otp_session + "   " + str_otp_mbl);

                    otp_verification(str_otp_api_key, str_otp_session, str_otp_mbl, Str_otp_typing_manually);

                }
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_btn_verify:

                break;
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
                                    ((Num_Verify_Listener) act).on_num_verify(false);
                                    //Log.d("varify",UrlEndpoints.OTP_VERIFY_API+"  "+OTP_Session+"  "+str_otp_mbl);
                                    //contibuew
                                    //  set_sign_up();

                                 //   mListener.onPager_swap_method();


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
    public void messageReceived(String messageText) {

    }

}