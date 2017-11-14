package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.gt.active_education.Sign_Up_Process_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import callbacks.SignUp_Pager_Swape_Listener;
import utilities.App_Static_Method;
import utilities.Image_picker;
import utilities.MyApplication;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

import static utilities.App_Static_Method.isValidPass;
import static utilities.App_Static_Method.permission_check;
import static utilities.App_Static_Method.request_permission_result;

/**
 * Created by GT on 7/8/2017.
 */

public class SignUp_Fragment extends Fragment implements View.OnClickListener , Image_picker.ImageAttachmentListener {

    SignUp_Pager_Swape_Listener mListener;
    Button btn_submit;
    String str_email,str_pass,str_uname,str_conPass;
    ImageView id_edit_camera_iv,id_image_profile_signup;
    EditText edt_f_name,edt_email,edt_pass,edt_con_pass;
    private Bitmap bitmap;
    /* image picker*/
    Image_picker imagePicker;
    private String str_profile_bitmap="";
    private String file_name;
    private String st_email;
    private Map<String,String> map;
    private CheckBox id_term_con;
    private TextView id_skip_tv;
    private Context context;

    //SIGN_UP_API
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

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

        final View rootView = inflater.inflate(R.layout.signup_frg_layout, container, false);

        id_term_con=(CheckBox)rootView.findViewById(R.id.id_term_con);
        id_image_profile_signup=(ImageView)rootView.findViewById(R.id.id_image_profile_signup);
        id_edit_camera_iv=(ImageView)rootView.findViewById(R.id.id_edit_camera_iv);id_edit_camera_iv.setOnClickListener(this);
        btn_submit=(Button)rootView.findViewById(R.id.btn_submit_bt);

        SharedPreferences sharedPreferences2 = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE, 0);
        Log.d("map_tostring",sharedPreferences2.getString("type","NA"));

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_term_con.isChecked())
                {
                    edt_f_name = ((EditText) rootView.findViewById(R.id.edt_uname));
                    str_uname = edt_f_name.getText().toString().trim();
                    edt_email = ((EditText) rootView.findViewById(R.id.edt_email));
                    edt_pass = ((EditText) rootView.findViewById(R.id.edt_pass));
                    str_pass = edt_pass.getText().toString().trim();
                    edt_con_pass = ((EditText) rootView.findViewById(R.id.edt_con_pass));
                    str_conPass = edt_con_pass.getText().toString().trim();

                    if (!App_Static_Method.isValidName(str_uname)) {     // fname
                        edt_f_name.setError("You must more characters");
                    } else {
                        st_email = edt_email.getText().toString().trim();
                        if (!App_Static_Method.isValidEmail(st_email)) {                            // email
                            edt_email.setError("Invalid Email");
                        } else {
                            if (!isValidPass(str_pass, str_conPass)) {
                                edt_con_pass.setError("Password is not Correct ");

                            } else {
                                if ((App_Static_Method.get_register_mobile().equals("na"))) {

                                } else {
                                    if ((str_profile_bitmap.equals("")) || (str_profile_bitmap.length() >= 0)) {

                                        map = new HashMap<String, String>();
                                        map.put("uname", str_uname);
                                        map.put("email", st_email);
                                        Log.d("checkmobile","  cjadedcek");
                                        map.put("mobile", App_Static_Method.get_register_mobile());
                                        Log.d("checkmobile","  cjacek"+App_Static_Method.get_register_mobile());
                                        map.put("pwd", str_conPass);
                                        map.put("filename", str_uname + ".png");
                                        //  map.put("image", str_profile_bitmap);
                                        set_sign_up();

                                      } else {

                                    }
                                }
                            }
                        }
                    }
                }else {
                    Toast.makeText(SignUp_Fragment.this.getContext(), "Please Select -Agree to Privacy Policy ", Toast.LENGTH_SHORT).show();
                }
            }

        });

        id_skip_tv=(TextView)rootView.findViewById(R.id.id_skip_tv);id_skip_tv.setOnClickListener(this);

        return rootView;
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
                         // image left
                           // mListener.onPager_swap_method();
                         //   {"msg":1,"data":[{"mobile":"9599805321","token":"N4ijuWn7Cy","image":"","uname":"dvdbdb","email":"asdf@gndd.vbn"}]}
                          //{"msg":1,"data":[{"mobile":"9599805321","token":"N4ijuWn7Cy","image":"","uname":"dvdbdb","email":"asdf@gndd.vbn"}]}
                            if(!jObj.has("exist")) {
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("login_Status", "L_OK");
                                editor.putString("Login_Token", jsonObject.getString("token"));
                                editor.putString("L_username", jsonObject.getString("uname"));
                                editor.putString("mobile", jsonObject.getString("mobile"));
                              //  editor.putString("prf_pic", jsonObject.getString("image"));
                                editor.commit();

                                mListener.onPager_swap_method("Login_page");
                            }else if(jObj.has("exist"))
                            {
                                Log.d("existm",""+jObj.getString("exist"));
                               // ConnectionCheck.user_Already_exist(SignUpActivity.this,"User Already Exist !!!");
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
                          Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("imge",""//+map.get("image")
                +"  "+
                  map.get("uname")+"  "+
                map.get("email")+"  "+
                map.get("mobile")+"  "+
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
                permission_check(101,SignUp_Fragment.this);
                imagePicker =new Image_picker(SignUp_Fragment.this);

                if (Build.VERSION.SDK_INT >= 23)
                {
                    imagePicker.imagepicker(1,"img1");
                }
                else
                {
                    App_Static_Method.lower_camera_call(SignUp_Fragment.this);
                }
                break;
            case R.id.id_skip_tv:

                register_only_num();

                startActivity(new Intent(SignUp_Fragment.this.getContext(), DashBoard_Activity.class));
                ((Sign_Up_Process_Activity)context).finish();
                break;
        }
    }

    private void register_only_num() {
        Log.d("call_method","method call");
        SharedPreferences  shrd_otp_prf =MyApplication.getAppContext().getSharedPreferences(UpdateValues.OTP_Prefrence, 0);
        final String str_otp_api_key = shrd_otp_prf.getString("otp_api_key", "");
        final String str_otp_session = shrd_otp_prf.getString("otp_session", "");
        final String str_otp_mbl = shrd_otp_prf.getString("otp_mbl", "");
        Log.d("gfdser",   str_otp_api_key + "  " + str_otp_session + "   " + str_otp_mbl);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.REG_NUM+str_otp_mbl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("rdfesruts",response.toString());
                            // image left
                            // mListener.onPager_swap_method();
                            //   {"msg":1,"data":[{"mobile":"9599805321","token":"N4ijuWn7Cy","image":"","uname":"dvdbdb","email":"asdf@gndd.vbn"}]}
                            //{"msg":1,"data":[{"mobile":"9599805321","token":"N4ijuWn7Cy","image":"","uname":"dvdbdb","email":"asdf@gndd.vbn"}]}
                            if(!jObj.has("exist")) {
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("login_Status", "L_OK");
                                editor.putString("Login_Token", jsonObject.getString("token"));
                                editor.putString("L_username", jsonObject.getString("uname"));
                                editor.putString("mobile", jsonObject.getString("mobile"));
                                //  editor.putString("prf_pic", jsonObject.getString("image"));
                                editor.commit();

                                mListener.onPager_swap_method("Login_page");
                            }else if(jObj.has("exist"))
                            {
                                Log.d("existm",""+jObj.getString("exist"));
                                // ConnectionCheck.user_Already_exist(SignUpActivity.this,"User Already Exist !!!");
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
                        Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("imge",""//+map.get("image")
                        +"  "+
                        map.get("uname")+"  "+
                        map.get("email")+"  "+
                        map.get("mobile")+"  "+
                        map.get("pwd") );
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        requestQueue.add(stringRequest);
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
            imagePicker.onActivityResult(requestCode, resultCode, data,"10001");
        }

    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String s,String str_img) {
        this.bitmap=file;
        this.file_name=filename; //Log.d("callll","call");
        id_image_profile_signup.setImageBitmap(file);
        str_profile_bitmap=getStringImage(file);
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
}
