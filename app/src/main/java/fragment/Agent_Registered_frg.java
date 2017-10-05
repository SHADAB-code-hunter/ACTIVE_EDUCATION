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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.R;

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
 * Created by GT on 8/28/2017.
 */

public class Agent_Registered_frg extends Fragment implements View.OnClickListener , Image_picker.ImageAttachmentListener {

    SignUp_Pager_Swape_Listener mListener;
    Button btn_submit;
    String str_email,str_pass,str_uname,str_conPass;
    ImageView id_edit_camera_iv,id_image_profile_signup;
    EditText edt_f_name,edt_email,edt_pass,edt_con_pass,edt_address,edt_state,edt_city;
    private Bitmap bitmap;
    /* image picker*/
    Image_picker imagePicker;
    private String str_profile_bitmap="";
    private String file_name;
    private String st_email,st_add;
    private Map<String,String> map;
    private CheckBox id_term_con;
    private String st_state,st_city;

    //SIGN_UP_API
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

        final View rootView = inflater.inflate(R.layout.signup_frg_agent, container, false);

        id_term_con=(CheckBox)rootView.findViewById(R.id.id_term_con);
        id_image_profile_signup=(ImageView)rootView.findViewById(R.id.id_image_profile_signup);
        id_edit_camera_iv=(ImageView)rootView.findViewById(R.id.id_edit_camera_iv);id_edit_camera_iv.setOnClickListener(this);
        btn_submit=(Button)rootView.findViewById(R.id.btn_submit_bt);
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
                    edt_address=(EditText)rootView.findViewById(R.id.edt_address);
                    edt_state=(EditText)rootView.findViewById(R.id.edt_state);
                    edt_city=(EditText)rootView.findViewById(R.id.edt_city);

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
                                st_add = edt_address.getText().toString().trim();
                                if (!App_Static_Method.isValidName(st_add)) {
                                    edt_address.setError("You must more characters");

                                } else {
                                if ((App_Static_Method.get_register_mobile().equals("na"))) {

                                } else {

                                    st_state = edt_state.getText().toString().trim();
                                    if (!App_Static_Method.isValidName(st_state)) {
                                        edt_state.setError("You must more characters");

                                    } else {

                                        st_city = edt_city.getText().toString().trim();
                                        if (!App_Static_Method.isValidName(st_state)) {
                                            edt_city.setError("You must more characters");

                                        } else {
                                            if ((str_profile_bitmap.equals("")) || (str_profile_bitmap.length() >= 0)) {

                                                map = new HashMap<String, String>();
                                                map.put("uname", str_uname);
                                                map.put("email", st_email);
                                                map.put("mobile", App_Static_Method.get_register_mobile());
                                            /*  map.put("add", st_add);
                                                map.put("state", st_state);
                                                map.put("city", st_city);*/
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
                            }
                        }
                    }
                }else {
                    Toast.makeText(Agent_Registered_frg.this.getContext(), "Please Select -Agree to Privacy Policy ", Toast.LENGTH_SHORT).show();
                }
            }

        });

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
                                editor.putString("email", jsonObject.getString("email"));
                                editor.putString("type", jsonObject.getString("type"));
                                editor.putString("mobile", jsonObject.getString("mobile"));
                                //  editor.putString("prf_pic", jsonObject.getString("image"));
                                editor.commit();

                                mListener.onPager_swap_method();
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
                 /* image pick*/
                permission_check(101,Agent_Registered_frg.this);
                imagePicker =new Image_picker(Agent_Registered_frg.this);

                //   id_img_picker=(ImageView)rootView.findViewById(R.id.id_img_picker);
             /*   id_img_picker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/

                if (Build.VERSION.SDK_INT >= 23)
                {
                    imagePicker.imagepicker(1,"img1");
                }
                else
                {
                    App_Static_Method.lower_camera_call(Agent_Registered_frg.this);
                }
                break;

          /*  case R.id.id_edit_camera_iv:

                break;

            case R.id.id_edit_camera_iv:

                break;*/
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
