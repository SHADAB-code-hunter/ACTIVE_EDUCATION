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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import callbacks.SignUp_Pager_Swape_Listener;
import extras.Keys;
import utilities.App_Static_Method;
import utilities.Common_Pojo;
import utilities.Image_picker;
import utilities.MyApplication;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

import static extras.Keys.KEY_USER_LOGIN.KEY_EMAIL;
import static extras.Keys.KEY_USER_LOGIN.KEY_TOKEN;
import static utilities.App_Static_Method.isValidPass;
import static utilities.App_Static_Method.permission_check;
import static utilities.App_Static_Method.request_permission_result;

/**
 * Created by GT on 9/14/2017.
 */

public class Edit_Profile_Frg extends Fragment implements View.OnClickListener {

    SignUp_Pager_Swape_Listener mListener;
    Button btn_submit;
    String str_email,str_pass,str_uname,str_conPass;
    ImageView id_edit_camera_iv,id_image_profile_signup;
    EditText edt_f_name,edt_oldpass,edt_con_pass,edt_state,edt_con_city,edt_mobile,edt_pincode,edt_new_pass;
    private Bitmap bitmap;
    /* image picker*/
    Image_picker imagePicker;
    private String str_profile_bitmap="";
    private String file_name,str_pincode;
    private String st_email,str_state,str_city,st_mobile,str_oldpass,str_newPass;
    private Map<String,String> map;
    private CheckBox id_term_con;
    private Spinner id_spn_state,id_spn_city;
    private FrameLayout id_state;
    private  String  city_id,state_id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.edit_prf_frg, container, false);

        //  id_term_con=(CheckBox)rootView.findViewById(R.id.id_term_con);
      //  id_image_profile_signup=(ImageView)rootView.findViewById(R.id.id_image_profile_signup);
       /* id_state=(FrameLayout)rootView.findViewById(R.id.id_state);
        id_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        btn_submit=(Button)rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    edt_f_name = ((EditText) rootView.findViewById(R.id.edt_uname));
                    str_uname = edt_f_name.getText().toString().trim();
                    edt_mobile = ((EditText) rootView.findViewById(R.id.edt_mobile));

                    edt_oldpass = ((EditText) rootView.findViewById(R.id.edt_oldpass));
                    str_oldpass = edt_oldpass.getText().toString().trim();
                    edt_new_pass = ((EditText) rootView.findViewById(R.id.edt_new_pass));
                    str_newPass = edt_new_pass.getText().toString().trim();

                 /*   edt_state = ((EditText) rootView.findViewById(R.id.edt_state));
                    str_state=edt_state.getText().toString().trim();
                    edt_con_city = ((EditText) rootView.findViewById(R.id.edt_con_city));
                    str_city=edt_con_city.getText().toString().trim();*/
                    edt_pincode=((EditText) rootView.findViewById(R.id.edt_pincode));
                    str_pincode=edt_pincode.getText().toString().trim();




               /*     if (!App_Static_Method.isValidName(str_uname)) {     // fname
                        edt_f_name.setError("You must more characters");
                    } else {
                        st_mobile = edt_mobile.getText().toString().trim();
                        if (!App_Static_Method.isValidPhone(st_email)) {                            // email
                            edt_mobile.setError("Invalid Mobile Number ");
                        } else {
                            if (str_oldpass!=null) {
                                edt_oldpass.setError("Password is not Correct ");
                            } else {
                                if (str_newPass!=null) {
                                    edt_new_pass.setError("New Password is not Correct ");
                                } else {
                                if (str_state.equals(null)) {
                                    edt_state.setError("Please Enter State Name ");
                                } else {
                                    edt_state.setError("Please Enter State ");

                                    if (str_city.equals(null)) {

                                    } else {
                                        edt_con_city.setError("Please Enter City ");

                                        //  if ((str_profile_bitmap.equals("")) || (str_profile_bitmap.length() >= 0)) {
*/
                                        map = new HashMap<String, String>();
                                        map.put("uname", str_uname);
                                        map.put("mobile",""+ edt_mobile.getText().toString());
                                        map.put(KEY_EMAIL, App_Static_Method.get_session_data(KEY_EMAIL));
                                        map.put("token", App_Static_Method.get_session_data(KEY_TOKEN));
                                        map.put("old_pwd", str_oldpass);
                                        map.put("new_pwd", str_newPass);
                                        map.put("state", state_id);
                                        map.put("city",city_id );
                                        map.put("pincode", str_pincode);
                                        set_sign_up();

                                       /* } else {

                                        }*/
                                     /* }
                                    }
                                }
                            }
                        }
                    }*/

            }

        });



        id_spn_state=(Spinner)rootView.findViewById(R.id.id_spnner_state);
        id_spn_city=(Spinner)rootView.findViewById(R.id.id_spnner_city);
        set_state_list(UrlEndpoints.get_filter_list+1);// get state list


        return rootView;
    }
    private void set_state_list(String url) {
        Log.d("striurl",url) ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("city",""+str_response);
                            ArrayList<Common_Pojo> arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("state"))
                            {
                                JSONArray array = response.getJSONArray("state");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                    sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));
                                    arrayList.add(sendDateModel);
                                }
                                set_state_inadapter(arrayList);
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                Log.d("unauth","un_Auth");
                                //  ConnectionCheck.unAuth_prob(activity,response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void set_state_inadapter(ArrayList<Common_Pojo> list) {

        ArrayList<String> arrayList=new ArrayList<>();
        final ArrayList<String> state_arrayList=new ArrayList<>();

        for (Common_Pojo commonPojo:list)
        {
            Log.d("statelsit_anme",commonPojo.getName());
            arrayList.add(commonPojo.getName());
            state_arrayList.add(commonPojo.getId());
        }

        ArrayAdapter<String> state_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,arrayList);
        /* Spinner_Adapter spinnerAdapter=new Spinner_Adapter(getContext(),arrayList);*/
        id_spn_state.setAdapter(state_adapter);

        // calling for city
        id_spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                state_id=state_arrayList.get(position);
                set_city_list(UrlEndpoints.GET_CITY+state_arrayList.get(position));//
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }
    private void set_city_list(String url) {

        Log.d("cityiurl",url) ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("check_city",""+str_response);
                            ArrayList<Common_Pojo> arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("data"))
                            {
                                JSONArray array = response.getJSONArray("data");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                    sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));

                                    //  Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(sendDateModel);
                                }

                                set_city_inadapter(arrayList);
                            }
                            else if(response.has("msg"))
                            {

                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    private void set_city_inadapter(ArrayList<Common_Pojo> list) {

        ArrayList<String> city_arrayList=new ArrayList<>();
        final ArrayList<String> city_arrayList_id=new ArrayList<>();
        for (Common_Pojo commonPojo:list)
        {
            Log.d("citylsit_anme",commonPojo.getName());
            Log.d("citylsit_id",commonPojo.getId());
            city_arrayList.add(commonPojo.getName());
            city_arrayList_id.add(commonPojo.getId());
        }

        ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,city_arrayList);
        id_spn_city.setAdapter(city_adapter);

        id_spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                city_id= city_arrayList_id.get(position);
                Log.d("common_city"," "+city_id);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void set_sign_up()
    { // shadab221  9599805432 ahmed.shadab221@gmail.com 7EVi7ONkcE  123456 shd786 1 1 208005
        Log.d("call_method","method call");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.SEND_PROFILE_UPDATE,
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
                                /*JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                App_Static_Method.user_session(Edit_Profile_Frg.this.getActivity(),jsonObject);

                                mListener.onPager_swap_method();*/
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


/*
                map = new HashMap<String, String>();
                map.put("uname", str_uname);
                map.put("mobile",""+ edt_mobile.getText().toString());
                map.put(KEY_EMAIL, App_Static_Method.get_session_data(KEY_EMAIL));
                map.put("token", App_Static_Method.get_session_data(KEY_TOKEN));
                map.put("old_pwd", str_oldpass);
                map.put("new_pwd", str_newPass);
                map.put("state", state_id);
                map.put("city",city_id );
                map.put("pincode", str_pincode);*/

                Log.d("imge",""//+map.get("image")
                        +"  "+
                        map.get("uname")+"  "+
                        map.get("mobile")+" "+
                        map.get("email")+" "+
                        map.get("token")+"  "+
                        map.get("old_pwd")+" "+
                        map.get("new_pwd")+" "+
                        map.get("state") +" "+
                        map.get("city" )+" "+
                        map.get("pincode"));
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
                permission_check(101,Edit_Profile_Frg.this);
                imagePicker =new Image_picker(Edit_Profile_Frg.this);

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
                    App_Static_Method.lower_camera_call(Edit_Profile_Frg.this);
                }
                break;

          /*  case R.id.id_edit_camera_iv:

                break;

            case R.id.id_edit_camera_iv:

                break;*/
        }
    }

}
