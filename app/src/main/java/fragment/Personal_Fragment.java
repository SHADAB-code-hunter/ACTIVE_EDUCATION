package fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.R;
import com.gt.active_education.SplashScreen_Activity;
import com.gt.active_education.Target_Circle_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import callbacks.SignUp_Pager_Swape_Listener;
import callbacks.Pager_Change_listener;
import utilities.App_Raw_Data;
import utilities.Image_picker;
import utilities.MyApplication;
import utilities.State_City_Search;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

import static utilities.App_Static_Method.get_session_type;
import static utilities.App_Static_Method.toMap;
import static utilities.UpdateValues.ADDMISSION;
import static utilities.UrlEndpoints.GET_CITY;
import static utilities.UrlEndpoints.GET_STATE;

/**
 * Created by GT on 8/24/2017.
 */

public class Personal_Fragment extends Fragment implements Image_picker.ImageAttachmentListener{//implements View.OnClickListener {

    SignUp_Pager_Swape_Listener mListener;
    Button btn_submit;
    String str_email, str_pass, str_uname, str_conPass;
    RadioGroup radioGroup;
    private String st_name,st_password,st_confirmpassword,st_fname,st_lname,st_fth_name,st_dob,st_state,st_city,st_rBtn,st_email,st_mobile,st_cat,st_add,st_pincode;
    private EditText edt_f_name,edt_lname,edt_fth_name,edt_email,edt_mob,edt_fname,edt_dob,edt_state,edt_city,edt_cat,edt_add,edt_pincode;
    private ImageView id_img_picker;
    private Image_picker imagePicker;
    private static final int lower_CAMERA_REQUEST = 1888;
    private String str_profile_bitmap="";
    private Bitmap bitmap;
    private String file_name;
    private Map<String, String> map;
    private Bundle bundle;
    private  String cat_type;
    private Bitmap str_image_prfl;
    private boolean open=false;
    private State_City_Search state_city_search;
    private TextView id_text_tv_state;
    private TextView id_text_tv_city;
    private Map<String,String> map2=new HashMap<>();
    private Map<String,String> map3=new HashMap<>();
    private RelativeLayout id_frm_city,id_frm_state;
    private String admission;
    private JSONObject jsonObject_admission;
    private Context context;
    private EditText id_edt_reffreralcode;

    //SIGN_UP_API
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
//        throw new ClassCastException(context.toString() + " must implement ExampleFragmentCallbackInterface ");

    } /*try {
            mListener = (SignUp_Pager_Swape_Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ExampleFragmentCallbackInterface ");
        }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.admission_frg, container, false);
        radioGroup = (RadioGroup)rootView.findViewById(R.id.id_radio_group);
        edt_f_name =(EditText)rootView.findViewById(R.id.id_edt_fname);
        edt_lname=(EditText)rootView.findViewById(R.id.id_l_name);
        edt_fth_name=(EditText)rootView.findViewById(R.id.id_fth_name);
        edt_mob=(EditText)rootView.findViewById(R.id.id_m_num);
        edt_email=(EditText)rootView.findViewById(R.id.id_edt_email);
        edt_cat=(EditText)rootView.findViewById(R.id.id_edt_cat);
        edt_dob=(EditText)rootView.findViewById(R.id.id_edt_dob);
        edt_add=(EditText)rootView.findViewById(R.id.id_edt_add);
        id_edt_reffreralcode=(EditText)rootView.findViewById(R.id.id_edt_reffreralcode);
       // edt_state=(EditText)rootView.findViewById(R.id.id_edt_state);
        //   edt_city=(EditText)rootView.findViewById(R.id.id_edt_city);

        edt_pincode=(EditText)rootView.findViewById(R.id.id_edt_pincode);
        id_frm_state=(RelativeLayout)rootView.findViewById(R.id.id_frm_state);
        id_frm_city=(RelativeLayout)rootView.findViewById(R.id.id_frm_city);

        id_text_tv_state=(TextView)rootView.findViewById(R.id.id_text_tv_state);
        id_text_tv_city=(TextView)rootView.findViewById(R.id.id_text_tv_city);

        if(getArguments()!=null)
        {
            bundle=getArguments();
            cat_type=bundle.getString(ADDMISSION);
            try {
                 jsonObject_admission=new JSONObject(bundle.getString(ADDMISSION));
                Log.d("ojfjn",""+jsonObject_admission);
                Log.d("sessiontype",""+get_session_type());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Log.d("bundrrle",""+bundle.getString("type"));
            //str_type=getIntent().getStringExtra("type");
        }
        /* image pick*/
        permission_check(101);
        imagePicker =new Image_picker(Personal_Fragment.this);

        id_img_picker=(ImageView)rootView.findViewById(R.id.id_img_picker);
        id_img_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23)
                {
                    imagePicker.imagepicker(1,"10001");
                }
                else
                {
                    lower_camera_call();
                }

            }
        });

        rootView.findViewById(R.id.id_btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              /*  int selectedId = radioGroup.getCheckedRadioButtonId();

                RadioButton   radioButton = (RadioButton)rootView.findViewById(selectedId);*/
                 st_fname=edt_f_name.getText().toString().trim();
                if(!isValidName(st_fname)) {// fname
                    edt_f_name.setError("You must more characters");
                } else {
                    st_lname=edt_lname.getText().toString().trim();
                    if (!isValidName(st_lname)) {//lname
                        edt_lname.setError("You must more characters");
                    } else {
                        st_fth_name=edt_fth_name.getText().toString().trim();
                        if(!isValidName(st_fth_name)) {// fthname
                            edt_fth_name.setError("You must more characters");
                        } else {
                        st_email = edt_email.getText().toString().trim();
                        if (!isValidEmail(st_email)) {                            // email
                            edt_email.setError("Invalid Email");
                        } else {
                            st_mobile = edt_mob.getText().toString().trim();
                            if (!isValidPhone(st_mobile)) {                       // mobile
                                edt_mob.setError("Mobile must at least  10 number");
                            } else {
                                st_cat = edt_cat.getText().toString().trim();
                                if (!isValidName(st_cat)) {
                                    edt_cat.setError("You must more characters "); // cat
                                } else {
                                    /*new feild*/
                                    st_dob = edt_dob.getText().toString().trim();
                                    if (!isValidDate(st_dob)) {
                                        edt_dob.setError("Please Enter Valid Date ");
                                    } else {
                                        st_add = edt_add.getText().toString().trim();
                                        if (!isValidName(st_add)) {
                                            edt_add.setError("Address is too short");
                                        } else {
                                            /*st_state = edt_state.getText().toString().trim();
                                            if (!isValidName(st_state)) {
                                                edt_state.setError("State Is not Correct ");
                                                // Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
                                            } else {*/
                                               /* st_city = edt_city.getText().toString().trim();
                                                if (!isValidName(st_city)) {
                                                    edt_state.setError("City Is not Correct ");
                                                    // Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
                                                } else {*/
                                                    st_pincode = edt_pincode.getText().toString().trim();
                                                    if (!isValidPincode(st_pincode)) {
                                                        edt_pincode.setError("Pincode Is not Correct ");
                                                        // Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
                                                    } else {

                                                        /*if ((radioButton.getText().toString()).equals("")) {
                                                            // st_rBtn.setError("City Name is too short  ");
                                                            // Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
                                                        } else {*/
                                                            if (!(str_profile_bitmap.equals("")) || (str_profile_bitmap.length() >= 0)) {
                                                                //    http://activequizindia.com/admin/webservices/signup.php?
                                                                //     uname=shadab ahmed&father_name=ahmed&dob=10/09/1991&mobile=9599805321&address=faridabad&state=1&city=1&sex=Male&pwd=123456&filename=shadab.png&email=ahmed.shadab221@gmail.com

                                                                map = new HashMap<String, String>();
                                                                map.put("f_name", st_fname);
                                                                map.put("l_name", st_lname);
                                                                map.put("father_name", st_fth_name);
                                                                map.put("email", st_email);
                                                                map.put("f_mobile", st_mobile);
                                                                map.put("caste_category", st_cat);
                                                                map.put("dob", st_dob);
                                                                map.put("address", st_add);
                                                                map.put("pincode", st_pincode);
                                                                map.put("gender", "male");
                                                                map.put("filename", st_fname + ".png");
                                                                map.put("image", str_profile_bitmap);
                                                                map.put("reffreral_code",id_edt_reffreralcode.getText().toString());
                                                                Log.d("sruts","gdgdg"+str_profile_bitmap);
                                                                call_submit_methos(map);
                                                            } else {

                                                            }
                                                        }
                                                   /* }
                                                }*/
                                          //  }
                                        }
                                    }
                                  }
                               }
                           }
                        }
                    }
                }

            }
        });


        id_frm_state.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!open) {
                    Log.d("kkjkjkjkj",""+open);
                    open = true;
                    state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s,String s_id) {
                            // id_tv_state.setText(s);id_tv_city.setText("Select City");
                            Log.d("kkjkjkjkj",""+s_id+" "+s);
                            if(!s.equals("na")){
                                id_text_tv_state.setText(s);
                                id_text_tv_city.setText("City");
                                map3.put("state",""+s_id);
                                map2.put("state", ""+ App_Raw_Data.local_parseJson(s));
                                Log.d("sgsdfe",""+App_Raw_Data.local_parseJson(s))  ;
                            }
                            else {
                                id_text_tv_state.setText("State"); id_text_tv_city.setText("City");
                            }
                            state_city_search.cancel();
                            open=false;
                        }
                    }, Personal_Fragment.this.getContext(), GET_STATE,"data");
                    state_city_search.show();
                }
                return false;
            }
        });


        id_frm_city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!open) {
                    if (!id_text_tv_state.getText().toString().equals("State")) {
                        open = true;
                        StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_text_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s,String s_id) {
                                  Log.d("jdjfhf",""+s);
                                if(!s.equals("na")){
                                    id_text_tv_city.setText(s);
                                    map3.put("city",""+s_id);
                                    map2.put("city",s);
                                   // map.put("city", st_city);
                                }else {
                                    id_text_tv_city.setText("City");
                                }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Personal_Fragment.this.getContext(), GET_CITY +map2.get("state"),"data");
                        state_city_search.show();
                    } else if(id_text_tv_state.getText().toString().equals("State")) {
                        Toast.makeText(Personal_Fragment.this.getContext(), "Please Select State First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        return rootView;
    }

    public void call_submit_methos(final Map<String, String> map)
    {
        try {
            map.putAll(toMap(jsonObject_admission));
            map.putAll(toMap(new JSONObject(get_session_type())));
            map.putAll(map3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("frgg",""+map);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.GET_admission_personal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("srutsrerre",response);

                            SharedPreferences sharedPreferences =context. getSharedPreferences(UpdateValues.FORM_ID, 0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Form_ID",jObj.getString("formid"));
                            editor.commit();
                            Pager_Change_listener pager_change_listener=(Pager_Change_listener)Personal_Fragment.this.getActivity();
                            pager_change_listener.on_pager_change(1,response);
                           /* if(!jObj.has("exist")) {
                                JSONArray jsonArray = jObj.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                SharedPreferences sharedPreferences = getSharedPreferences(UpdateValues.Preferences, 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("login_Status", "L_OK");
                                editor.putString("Login_Token", jsonObject.getString("token"));
                                editor.putString("L_username", jsonObject.getString("uname"));
                                editor.putString("mobile", jsonObject.getString("mobile"));
                                editor.putString("prf_pic", jsonObject.getString("image"));
                                editor.commit();
                                Intent intent = new Intent(SignUpActivity.this, New_Dashboard_Activity.class);
                                startActivity(intent);
                                finish();
                            }else if(jObj.has("exist"))
                            {
                                ConnectionCheck.user_Already_exist(SignUpActivity.this,"User Already Exist !!!");
                            }*/

                        } catch (Exception e) {
                            e.printStackTrace();
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
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1) {
            imagePicker.request_permission_result(requestCode, permissions, grantResults);
        }else if(requestCode==101){
            /*  //Log.d("permisss","permission find"+requestCode+" "+permissions[0]+" "+grantResults[0]);
                //Log.d("permisss2","permission find2"+requestCode+" "+permissions[1]+" "+grantResults[1]);
                //Log.d("permisss3","permission find3"+requestCode+" "+permissions[2]+" "+grantResults[2]);*/
            request_permission_result(requestCode, permissions, grantResults);
        }
    }

    public void request_permission_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //Log.d("cou8nter","101");
        switch (requestCode)
        {
            case 101:
                if (grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED||grantResults[1] == PackageManager.PERMISSION_GRANTED||grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    // requestForSMS(st_mobile);
                    //Log.d("sms_perm","permission granted");
                } else {
                   /* Intent i = new Intent(getActivity(), User_Login_Activity.class);
                    i.putExtra("lg_call_page","sp_to_db");
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "sms Permission denied",Toast.LENGTH_LONG).show();
                    finish();*/
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode!=0) {
            super.onActivityResult(requestCode, resultCode, data);
            Log.d("datapack", data.toString());
            // programmitically set screen orientation of activity (landscape)
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            // programmitically set screen orientation of activity (portrait)
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            if (requestCode == lower_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                id_img_picker.setImageBitmap(photo);
            } else if (requestCode != lower_CAMERA_REQUEST) {
                imagePicker.onActivityResult(requestCode, resultCode, data, "10001");
            }
        }else {
            Toast.makeText(Personal_Fragment.this.getContext(), "You Did'nt have Choosen any Image !!!", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String s,String str_status) {
        this.bitmap=file;
        this.file_name=filename; //Log.d("callll","call");
        str_image_prfl=file;
        Log.d("bitcdcmapp",file.toString());
        //Log.d("urlir",uri.toString()+"  "+from);
        id_img_picker.setImageBitmap(file);
        switch (str_status)
        {
            case "img1":
                id_img_picker.setImageBitmap(file);
                break;

            case "img2":

                break;

            case "img3":

                break;
            case "img4":

                break;
        }

        str_profile_bitmap=getStringImage(file);

        //str_profile_bitmap=s;
        String path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        /*BitmapProcessor bitmapProcessor = new BitmapProcessor(file, 1000, 1000, 90);
        id_image_profile_signup.setImageBitmap(bitmapProcessor.getBitmap());
        str_profile_bitmap=BitMapToString(bitmapProcessor.getBitmap());*/
        imagePicker.createImage(file,filename,path,false);

    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void lower_camera_call() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, lower_CAMERA_REQUEST);
    }
    private boolean isValidName(String st_name) {
        if (st_name != null && st_name.length() > 1) {
            return true;
        }
        return false;
    }
    private boolean isValidPincode(String st_name) {
        if (st_name != null && st_name.length() ==6) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String st_email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(st_email);
        return matcher.matches();
    }

    private boolean isValidSubject(String st_password) {
        if (st_password != null && st_password.length() > 5) {
            return true;
        }
        return false;
    }
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    public static boolean isPhoneValid(String phone) {
        boolean retval = false;
        String phoneNumberPattern = "(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}";
        retval = phone.matches(phoneNumberPattern);
        String msg = "NO MATCH: pattern:" + phone + "\r\n regex: " + phoneNumberPattern;

        if (retval) {
            msg = " MATCH: pattern:" + phone + "\r\n regex: " + phoneNumberPattern;
        }
        System.out.println(msg + "\r\n");
        return retval;
    }
    private boolean isValidPhone(String st_mobile) {
        if (st_mobile != null && st_mobile.length() == 10) {
            return true;
        }
        return false;
    }

    public void permission_check(final int code)//for sms reading permission 101
    {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.RECEIVE_SMS);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
        {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.RECEIVE_SMS))
            {
                showMessageOKCancel("For adding images , You need to provide permission to access your SMS",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //   String[] PERMISSIONS = {ValueConstants.CAMERA_PERMISSION, ValueConstants.READ_EXTERNAL_STORAGE_PERMISSION, ValueConstants.WRITE_EXTERNAL_STORAGE_PERMISSION};

                                //reuesting for permission
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[] {Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE,Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,Manifest.permission_group.SMS},
                                        code);
                            }
                        });
                return;
            }

            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE,Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,Manifest.permission_group.SMS},
                    code);
            return;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
