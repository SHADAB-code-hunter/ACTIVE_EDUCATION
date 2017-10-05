package com.gt.active_education;

import android.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import callbacks.Num_Verify_Listener;
import callbacks.SignUp_Pager_Swape_Listener;
import utilities.App_Static_Method;
import utilities.ConnectionCheck;
import utilities.Image_picker;
import utilities.Imager_Picker_Activity;
import utilities.MyApplication;
import utilities.Num_Verify_dialog;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

import static extras.Keys.KEY_USER_LOGIN.KEY_EMAIL;
import static utilities.App_Static_Method.isValidPass;
import static utilities.App_Static_Method.request_permission_result;

/**
 * Created by GT on 7/7/2017.
 */

public class SignUp_Activity extends AppCompatActivity implements View.OnClickListener , Imager_Picker_Activity.ImageAttachmentListener,
     Num_Verify_Listener {
    SignUp_Pager_Swape_Listener mListener;
    Button btn_submit,btn_submit_cancle;
    String str_email,str_pass,str_uname,str_conPass;
    ImageView id_edit_camera_iv,id_image_profile_signup;
    EditText edt_f_name,edt_email,edt_pass,edt_con_pass,edt_mobile;
    private Bitmap bitmap;
    /* image picker*/
    Imager_Picker_Activity imagePicker;
    private String str_profile_bitmap="";
    private String file_name;
    private String st_email;
    private Map<String,String> map;
    private CheckBox id_term_con;
    private Boolean mbl_verify_vbl=false;
    Num_Verify_dialog dialogClass;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_frg_layout);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        id_term_con=(CheckBox)findViewById(R.id.id_term_con);
        id_image_profile_signup=(ImageView)findViewById(R.id.id_image_profile_signup);
        id_edit_camera_iv=(ImageView)findViewById(R.id.id_edit_camera_iv);id_edit_camera_iv.setOnClickListener(this);
        edt_pass = (EditText) findViewById(R.id.edt_pass);

/*
        edt_pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Specify your database function here.
                    Log.d("gdgf","hfhf");
                    if(mbl_verify_vbl) {
                        Toast.makeText(SignUp_Activity.this, "dvhfghg", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
                return false;
            }
        });
*/
      /*  id_otp_btn_verif.setOnClickListener(new View.OnClickListener() {
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
        });*/
        edt_mobile=(EditText)findViewById(R.id.edt_mobile);

        edt_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                Log.d("gdgf","hfhf");
                mbl_verify_vbl=true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
        edt_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

               /* Log.d("gdffgf","hfhf");
                if(mbl_verify_vbl) {
                    Toast.makeText(SignUp_Activity.this, "dvhfghg", Toast.LENGTH_SHORT).show();
                    dialogClass=new Num_Verify_dialog(SignUp_Activity.this,edt_mobile.getText().toString());
                    dialogClass.show();
                }*/
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
        btn_submit=(Button)findViewById(R.id.btn_submit_btn);
      /*btn_submit_cancle=(Button)findViewById(R.id.btn_submit_cancle);
        btn_submit_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             // Log.d("mobils",""+mobile.length());
                Log.d("urlll",""+UrlEndpoints.OTP_GENERATE_API+"9599805321"+"/AUTOGEN");
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, UrlEndpoints.OTP_GENERATE_API+"9599805321"+"/AUTOGEN",
                        new Response.Listener<JSONObject>() {
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
                                            SharedPreferences  shrd_otp_prf= MyApplication.getAppContext().getSharedPreferences(UpdateValues.OTP_Prefrence, 0);

                                           *//* SharedPreferences.Editor editor = shrd_otp_prf.edit();
                                            editor.putString("otp_api_key",UrlEndpoints.OTP_API_KEY);
                                            editor.putString("otp_session", OTP_Session);
                                            editor.putString("otp_mbl", mobile);
                                            Log.d("mbl_num",""+mobile);
                                            editor.commit();*//*
                                         //   Log.d("valueesscscddf",UrlEndpoints.OTP_API_KEY+"  "+OTP_Session+"  "+mobile);

                              *//*  Intent intent = new Intent("android.permission.RECEIVE_SMS");
                                sendBroadcast(intent);*//*
                                            //  SmsReceiver.bindListener(Num_Verify_Fragment.this);
                                            //  mListener.onPager_swap_method();

                                            // set otp in edit text or manually
                                            //  edt_otp.setText(mobile);// listener
                                        }
                                    } else if(responseObj.getString("Status").equals("Error"))
                                    {
                                        //Log.d("otp_send_status","error");
                                    }

                                } catch (JSONException e) {
                  *//*  Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();*//*
                                    Log.d("otp_send_status_exp",e.getMessage());
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley error", "Error: " + error.getMessage());
              *//*   Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);*//*
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

                requestQueue.add(req);
                Log.d("dgdg","dhdhd");
            }
        });*/
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("badfbba",""+"dgdgdg");
                if (id_term_con.isChecked())
                {
                    edt_f_name = ((EditText) findViewById(R.id.edt_uname));
                    str_uname = edt_f_name.getText().toString().trim();
                    edt_email = ((EditText) findViewById(R.id.edt_email));
                    edt_pass = ((EditText) findViewById(R.id.edt_pass));
                    str_pass = edt_pass.getText().toString().trim();
                    edt_con_pass = ((EditText) findViewById(R.id.edt_con_pass));
                    str_conPass = edt_con_pass.getText().toString().trim();

                    if (!App_Static_Method.isValidName(str_uname)) {     // fname
                        edt_f_name.setError("You must more characters");
                    } else {
                        st_email = edt_email.getText().toString().trim();
                        if (!App_Static_Method.isValidEmail(st_email)) {                            // email
                            edt_email.setError("Invalid Email");
                        } else {
                            String str_mobile=edt_mobile.getText().toString();
                            Log.d("babba",""+str_mobile);
                            if (!App_Static_Method.isValidPhone(str_mobile)) {
                                edt_mobile.setError("Please Enter a Valid Mobile Number !!! ");
                            } else {
                            if (!isValidPass(str_pass, str_conPass)) {
                                edt_con_pass.setError("Password is not Correct ");

                            } else {
                               /* if ((App_Static_Method.get_register_mobile().equals("na"))) {

                                } else {*/
                                    if ((str_profile_bitmap.equals("")) || (str_profile_bitmap.length() >= 0)) {

                                        map = new HashMap<String, String>();
                                        map.put("uname", str_uname);
                                        map.put("email", st_email);
                                        Log.d("checkmobile", "  cjadedcek");
                                        //  map.put("mobile", App_Static_Method.get_register_mobile());
                                        map.put("mobile", str_mobile);
                                        Log.d("checkmobile", "  cjacek" + App_Static_Method.get_register_mobile());
                                        map.put("pwd", str_conPass);
                                        map.put("filename", str_uname + ".png");
                                        map.put("image", str_profile_bitmap);
                                        Log.d("pjpii",""+str_profile_bitmap);

                                        progressDialog = new ProgressDialog(SignUp_Activity.this);
                                        progressDialog.setCancelable(true);
                                        progressDialog.show();
                                        progressDialog.setMessage(getString(R.string.SignUp));

                                        set_sign_up();

                                    } else {

                              //   }
                                }
                              }
                            }
                        }
                    }
                }else {
                    Toast.makeText(SignUp_Activity.this, "Please Select -Agree to Privacy Policy ", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
    

    private void set_sign_up()
    {
        Log.d("call_method","method call");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.SIGN_UP_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("rdfesruts",response.toString());
                            //    {"msg":1,"data":[{"mobile":"9599805321","token":"GhcFU5DPvR","image":"shadab 221.png","uname":"shadab 221","email":"ahm@fb.bnm","type":"user"}]}
                            //{"msg":1,"data":[{"mobile":"9599805321","token":"N4ijuWn7Cy","image":"","uname":"dvdbdb","email":"asdf@gndd.vbn"}]}
                            if(!jObj.has("exist")) {
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                App_Static_Method.user_session(SignUp_Activity.this,jsonObject);

                               // mListener.onPager_swap_method();
                                startActivity(new Intent(getApplicationContext(),DashBoard_Activity.class));
                                progressDialog.cancel();
                                finish();

                            }else if(jObj.has("exist"))
                            {
                                Log.d("existm",""+jObj.getString("exist"));
                              //   ConnectionCheck.user_Already_exist(SignUp_Activity.this,"User Already Exist !!!");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.d("expdcc",""+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp_Activity.this, ""+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("imgefgr",""//+map.get("image")
                        +"  "+
                        map.get("uname")+"  "+
                        map.get("email")+"  "+
                        map.get("mobile")+"  "+
                        map.get("mobile")+" "+
                        map.get("pwd") );
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_edit_camera_iv:
                 /* image pick*/
                permission_check(101, SignUp_Activity.this);
                imagePicker =new Imager_Picker_Activity(SignUp_Activity.this);

                //   id_img_picker=(ImageView)findViewById(R.id.id_img_picker);
             /*   id_img_picker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/

                if (Build.VERSION.SDK_INT >= 23)
                {
                    imagePicker.imagepicker(1);
                }
                else
                {
                    App_Static_Method.lower_camera_call_act(SignUp_Activity.this);
                }
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1) {
            imagePicker.request_permission_result(requestCode, permissions, grantResults);
        }else if(requestCode==101){

            request_permission_result(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("dataedpack",data.toString());

        if (requestCode == App_Static_Method.lower_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            id_image_profile_signup.setImageBitmap(photo);
        }else if(requestCode!= App_Static_Method.lower_CAMERA_REQUEST) {
            imagePicker.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String s) {
        this.bitmap=file;
        this.file_name=filename;

        id_image_profile_signup.setImageBitmap(file);
        str_profile_bitmap=getStringImage(file);
        Log.d("callggll","call"+str_profile_bitmap);
        String path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imagePicker.createImage(file,filename,path,false);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void on_num_verify(boolean bln) {
        mbl_verify_vbl=bln;
        dialogClass.cancel();
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
                                 //   ((Num_Verify_Listener) act).on_num_verify(false);
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
    public static void permission_check(final int code,final Activity obj_fragment)//for sms reading permission 101
    {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(obj_fragment.getApplicationContext(),
                android.Manifest.permission.RECEIVE_SMS);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
        {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(obj_fragment, android.Manifest.permission.RECEIVE_SMS))
            {
                showMessageOKCancel(obj_fragment,"For adding images , You need to provide permission to access your SMS",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //   String[] PERMISSIONS = {ValueConstants.CAMERA_PERMISSION, ValueConstants.READ_EXTERNAL_STORAGE_PERMISSION, ValueConstants.WRITE_EXTERNAL_STORAGE_PERMISSION};

                                //reuesting for permission
                                ActivityCompat.requestPermissions(obj_fragment,
                                        new String[] {android.Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE, android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.READ_SMS, android.Manifest.permission_group.SMS},
                                        code);
                            }
                        });
                return;
            }

            ActivityCompat.requestPermissions(obj_fragment,
                    new String[] {android.Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE, android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.READ_SMS, android.Manifest.permission_group.SMS},
                    code);
            return;
        }
       /* else {

            requestForSMS(st_mobile);
        }*/

    }
    public static void showMessageOKCancel(Activity obj_fragment,String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(obj_fragment)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
