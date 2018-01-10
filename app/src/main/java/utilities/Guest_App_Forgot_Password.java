package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.gt.active_education.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import callbacks.Forgot_password;

/**
 * Created by GT on 9/18/2017.
 */

public class Guest_App_Forgot_Password extends Dialog implements View.OnClickListener {
    RelativeLayout id_relative_dialog;
    private View btnRed;
    private Activity act;
    private ImageView id_website,id_video_call,id_sms,id_call;
    private Button btn_forgot;

    Button btn_sign_up;
    private boolean open=false;
    Button btn_login;
    ProgressDialog progressDialog;
    private Guest_App_Forgot_Password appForgotPassword;
    private EditText id_login_type;
    private Custom_List_Dialog custom_list_dialog;
    private String[] str_log_array;
    private Button sign_in_button,fb_in_button;
    private TextView id_Reg_here;


    public Guest_App_Forgot_Password(Activity act) {
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
        str_log_array=act.getResources().getStringArray(R.array.guest_login_array);
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
                    }, Guest_App_Forgot_Password.this.getContext(),str_log_array);
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

                if((id_login_type.getText().toString()).equalsIgnoreCase("Please Choose Login Type")) {
                    Toast.makeText(act, "Please Choose Login Type", Toast.LENGTH_SHORT).show();
                    ((EditText) findViewById(R.id.id_login_type)).setError(" Please Choose Login Type !!!");

                    return;
                }

                if(str_uname.isEmpty())
                {
                    ((EditText) findViewById(R.id.edt_uname)).setError(" Please Enter UserName !!!");
                    return;
                }else {
                    Toast.makeText(act, "Please Choosedd Login Type", Toast.LENGTH_SHORT).show();

                            switch (id_login_type.getText().toString().trim()) {

                                case "Student Login":
                                    set_sign_in(str_uname, UrlEndpoints.FORGOT_API, "user");
                                    Toast.makeText(act, "Please Choosess Login Type", Toast.LENGTH_SHORT).show();
                                    break;
                                case "Partner Login":
                                    set_sign_in(str_uname, UrlEndpoints.FORGOT_API, "agent");
                                    Toast.makeText(act, "Please Chooseeee Login Type", Toast.LENGTH_SHORT).show();
                                    break;

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
                            if(jObj.getInt("msg")==0)
                            {
                                Toast.makeText(act, "You Have Not Registered ", Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                Toast.makeText(act, "You will Got  ID / Password Shortly !!! ", Toast.LENGTH_SHORT).show();
                            }

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
