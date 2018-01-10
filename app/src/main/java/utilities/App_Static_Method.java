package utilities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.Filter_Activity;
import com.gt.active_education.Login_Fior_Guest_Activity;
import com.gt.active_education.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import callbacks.Form_Responce_Listener;
import callbacks.Log_Out_Listener;
import callbacks.Profile_Image_Listener;
import network.VolleySingleton;
import pojo.Cat_Model;

import static extras.Keys.KEY_USER_LOGIN.KEY_EMAIL;
import static extras.Keys.KEY_USER_LOGIN.KEY_IMAGE;
import static extras.Keys.KEY_USER_LOGIN.KEY_TOKEN;
import static utilities.UpdateValues.AGENT_SESSION;
import static utilities.UpdateValues.GUSET_SESSION;
import static utilities.UpdateValues.G_PARTNER_Prefrence;
import static utilities.UpdateValues.REAL_USER_SESSION;
import static utilities.UpdateValues.USER_SESSION;

/**
 * Created by GT on 7/25/2017.
 */

public class App_Static_Method {
    public static final int lower_CAMERA_REQUEST = 1888;
    public static ProgressDialog progressDialog;
    private static SharedPreferences sharedPreferences;


    public static List<Cat_Model> get_cat_model_list() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences("Temp_List",0);
        String str_list=sharedPreferences.getString("shrd_list", "na");
        Gson gson = new Gson();java.lang.reflect.Type type;type = new TypeToken<List<Cat_Model>>() {}.getType();
        if(!str_list.equals("na")) {
            List<Cat_Model> Cat_List = (List<Cat_Model>) gson.fromJson(str_list, type);
            Log.d("hfgvhfvbhj", "" + Cat_List.toString());
            return Cat_List;
        }
        else {
            Cat_Model catModel=new Cat_Model();
            catModel.setC_name("empty");
            List<Cat_Model> Cat_List_empty =new ArrayList<>();
            Cat_List_empty.add(catModel);
            return  Cat_List_empty;
        }
    }


/* public static  void hide_input(Context activity){

        InputMethodManager imm = (InputMethodManager)activity. getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }*/
    public static ArrayList<String> get_status_list() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences("Temp_List",0);
        String str_list=sharedPreferences.getString("TAB_LIST", "na");
        Gson gson = new Gson();java.lang.reflect.Type type;type = new TypeToken<ArrayList<String>>() {}.getType();
        if(!str_list.equals("na")) {
            ArrayList<String> common_List = (ArrayList<String>) gson.fromJson(str_list, type);
            Log.d("hfgvhfvbhj", "" + common_List.toString());
            return common_List;
        }
        return null;
    }
    public static ProgressDialog show_load_progress(Activity applicationContext, String string) {
        progressDialog = new ProgressDialog(applicationContext);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(string);
        return progressDialog;

    }

    public static List<Common_Pojo> get_status_one_by_one_list(String s) {
        Log.d("ddddd", "" +s);
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences("Temp_List",0);
        String str_list=sharedPreferences.getString(s, "na");
        Gson gson = new Gson();java.lang.reflect.Type type;type = new TypeToken<ArrayList<Common_Pojo>>() {}.getType();
        if(!str_list.equals("na")) {
            List<Common_Pojo> common_List = (ArrayList<Common_Pojo>) gson.fromJson(str_list, type);
            Log.d("hfgvhfvbhj", "" + common_List.toString());
            return common_List;
        }
        return null;
    }
    public static Map<String,String> get_full_session_data() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_PARTNER_Prefrence, 0);
        Map<String,String> session_hm=new HashMap<>();
        session_hm.put("clg_id",sharedPreferences.getString("clg_id", "na"));
        session_hm.put("token",sharedPreferences.getString("token", "na"));
        session_hm.put("email",sharedPreferences.getString("email", "na"));
        session_hm.put("utype",sharedPreferences.getString("utype", "na"));

        return session_hm;

    }

    public static Map<String,String> get_for_submit_partener_detail() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_PARTNER_Prefrence, 0);
        Map<String,String> session_hm=new HashMap<>();

        session_hm.put("crd_email",sharedPreferences.getString("email", "na"));
        session_hm.put("token",sharedPreferences.getString("token", "na"));
        session_hm.put("utype",sharedPreferences.getString("utype", "na"));
        session_hm.put("userid",sharedPreferences.getString("userid", "na"));

        return session_hm;

    }

    public static void list_not_get(final Activity activity, final String str_gate) {
        Log.d("nabsmenm",activity.getLocalClassName());
        new DroidDialog.Builder(activity)
                .icon(R.drawable.ic_null_list)
                .title(str_gate)
                .content(activity.getResources().getString(R.string.list_null))
                .cancelable(true, false)
                .neutralButton("OK", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {

                        droidDialog.dismiss();

                    }
                })

                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
                        ContextCompat.getColor(activity, R.color.colorPrimaryDark))
                .divider(true, ContextCompat.getColor(activity, R.color.orange))
                .show();
    }
    public static void city_not_get(final Activity activity, final String str_gate) {
//        Log.d("nabsmenm",activity.getLocalClassName());
        new DroidDialog.Builder(activity)
                .icon(R.drawable.ic_null_list)
                .title(str_gate)
                .content(MyApplication.getAppContext().getResources().getString(R.string.list_null))
                .cancelable(true, false)
                .neutralButton("OK", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {

                        droidDialog.dismiss();

                    }
                })

                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
                        ContextCompat.getColor(activity, R.color.colorPrimaryDark))
                .divider(true, ContextCompat.getColor(activity, R.color.orange))
                .show();
    }

    public static void dialog_list_not_get(final Filter_Dialog activity, final String str_gate) {
      //  Log.d("nabsmenm",activity.getLocalClassName());
        new DroidDialog.Builder(activity.getContext())
                .icon(R.drawable.ic_null_list)
                .title(str_gate)
                .content(activity.getContext().getResources().getString(R.string.list_null))
                .cancelable(true, false)
                .neutralButton("OK", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {

                        droidDialog.dismiss();

                    }
                })

                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(activity.getContext(), R.color.colorAccent), ContextCompat.getColor(activity.getContext(), R.color.colorPrimaryDark),
                        ContextCompat.getColor(activity.getContext(), R.color.colorPrimaryDark))
                .divider(true, ContextCompat.getColor(activity.getContext(), R.color.orange))
                .show();
    }


    public static void list_filter_dialog(final Activity activity, final String str_gate) {
        Log.d("nabsmenm",activity.getLocalClassName());
        new DroidDialog.Builder(activity)
                .icon(R.drawable.ic_null_list)
                .cancelable(true, false)
                .neutralButton("Filter", new DroidDialog.onNeutralListener() {
                    @Override
                    public void onNeutral(Dialog droidDialog) {
                        activity.startActivity(new Intent(activity, Filter_Activity.class));
                        droidDialog.dismiss();

                    }
                })

                .animation(AnimUtils.AnimZoomInOut)
                .color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
                        ContextCompat.getColor(activity, R.color.colorPrimaryDark))
                .divider(true, ContextCompat.getColor(activity, R.color.orange))
                .show();
    }


    public static void permission_check(final int code,final Fragment obj_fragment)//for sms reading permission 101
    {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(obj_fragment.getContext(),
                Manifest.permission.RECEIVE_SMS);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
        {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(obj_fragment.getActivity(),Manifest.permission.RECEIVE_SMS))
            {
                showMessageOKCancel(obj_fragment,"For adding images , You need to provide permission to access your SMS",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //   String[] PERMISSIONS = {ValueConstants.CAMERA_PERMISSION, ValueConstants.READ_EXTERNAL_STORAGE_PERMISSION, ValueConstants.WRITE_EXTERNAL_STORAGE_PERMISSION};

                                //reuesting for permission
                                Log.d("which_status",""+which);
                                ActivityCompat.requestPermissions(obj_fragment.getActivity(),
                                        new String[] {Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE,Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,Manifest.permission_group.SMS},
                                        code);
                            }
                        });
                return;
            }

            ActivityCompat.requestPermissions(obj_fragment.getActivity(),
                    new String[] {Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE,Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS,Manifest.permission_group.SMS},
                    code);
            return;
        }
       /* else {

            requestForSMS(st_mobile);
        }*/

    }
    public static void showMessageOKCancel(Fragment obj_fragment,String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(obj_fragment.getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public static void lower_camera_call(Fragment obj_fragment) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        obj_fragment.startActivityForResult(cameraIntent, 1888);
    }
    public static void lower_camera_call_act(Activity obj_fragment) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        obj_fragment.startActivityForResult(cameraIntent, 1888);
    }
    public static void request_permission_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //Log.d("cou8nter","101");
        switch (requestCode)
        {
            case 101:
                if (grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED||grantResults[1] == PackageManager.PERMISSION_GRANTED||grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    // requestForSMS(st_mobile);
                    Log.d("sms_perm","permission granted");
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

    public static boolean isValidName(String st_name) {
        if (st_name != null && st_name.length() > 1) {
            return true;
        }
        return false;
    }
    public static boolean isValidPincode(String st_name) {
        if (st_name != null && st_name.length() ==6) {
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(String st_email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(st_email);
        return matcher.matches();
    }

    public static boolean isValidSubject(String st_password) {
        if (st_password != null && st_password.length() > 5) {
            return true;
        }
        return false;
    }
    public static String  checktype() {
        SharedPreferences shprf_seater = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE,0);

        return  shprf_seater.getString("type","NA");
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
   /* public static boolean isPhoneValid(String phone) {
        boolean retval = false;
        String phoneNumberPattern = "(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}";
        retval = phone.matches(phoneNumberPattern);
        String msg = "NO MATCH: pattern:" + phone + "\r\n regex: " + phoneNumberPattern;

        if (retval) {
            msg = " MATCH: pattern:" + phone + "\r\n regex: " + phoneNumberPattern;
        }
        System.out.println(msg + "\r\n");
        return retval;
    }*/
    public static boolean isValidPhone(String st_mobile) {
        if (st_mobile != null && st_mobile.length() == 10) {
            return true;
        }
        return false;
    }
    public static String get_Type() {
        SharedPreferences shprf_ = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE, 0);

        return shprf_.getString("type","NA");
    }
    public static boolean isValidPass(String pass,String pass2) {
        if (pass != null && pass2 != null && pass2.length() >4 && pass.length() >4 &&  pass.equals(pass2)) {
            return true;
        }
        return false;
    }
    public static String get_register_mobile() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.OTP_Prefrence,0);
        String res_mob=sharedPreferences.getString("otp_mbl", "na");
        if(!res_mob.equals("na"))
        {
            Log.d("mmm",""+res_mob);
            return res_mob;
        }
        Log.d("mmm",""+res_mob);
        return "na";
    }

    public static Map<String, String>  session_type() {
        SharedPreferences shprf_ = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE, 0);
        SharedPreferences sharedPreferences=null;
        String str_token = null;
        String str_mobie = null;

        Map<String,String> map=new HashMap<>();

        switch (shprf_.getString("type", "na")) {
            case "agent":
                sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_PARTNER_Prefrence, 0);
                str_mobie = sharedPreferences.getString("mobile", "na");
                str_token = sharedPreferences.getString("token", "na");
                break;
            case "user":
                sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
                str_mobie = sharedPreferences.getString("mobile", "na");
                str_token = sharedPreferences.getString("token", "na");
                break;
            case "seater":
                sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_Seater_Pref, 0);
                str_mobie = sharedPreferences.getString("mobile", "na");
                str_token = sharedPreferences.getString("token", "na");
                Log.d("kkkhkh",sharedPreferences.getString("userid", "na"));
                break;
        }

        map.put("mobile",str_mobie);
        map.put("token",str_token);
        map.put("type",shprf_.getString("type", "na"));
        if(shprf_.getString("type", "na").equals("seater"))
        {
         map.put("userid",""+sharedPreferences.getString("userid", "na"));
         map.put("crd_mobile",""+sharedPreferences.getString("mobile", "na"));
         map.put("utype",""+sharedPreferences.getString("utype", "na"));
         map.put("clg_id",""+sharedPreferences.getString("clg_id", "na"));
         map.put("image",""+sharedPreferences.getString("image", "na"));
         map.put("name",""+sharedPreferences.getString("name", "na"));

        }


//        Log.d("gdgdsgbvvbnbbsklvgn",""+sharedPreferences.getAll());
        return map;
    }

    public static void logout(final Activity dashBoard_activity) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.SET_LOG_OUT /*+"mobile="+ str_mobie
                    + "&token=" + str_token+"&type="+shprf_.getString("type", "na")*/,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                Log.d("lougour", ""+response.toString());
                                //{"msg":1}
                                    Log.d("ffff",""+jObj.getString("msg"));
                                    if (jObj.getInt("msg")==1)
                                    { // have to must convert in to string
                                        Log.d("udpgad", response.toString());


                                        ((Log_Out_Listener)dashBoard_activity).on_logout(remove_session());
                                        //  finish();

                                    }else {
                                        ((Log_Out_Listener)dashBoard_activity).on_logout(false);
                                    }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                             Toast.makeText(dashBoard_activity, "Logout Unsuccessfull", Toast.LENGTH_LONG).show();
                            ((Log_Out_Listener)dashBoard_activity).on_logout(false);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Log.d("sesstiontype:",""+get_session_type());
                    Map<String,String> map=null;
                    try {
                        map=toMap(new JSONObject(get_session_type()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return map;
                }
            };

            RequestQueue requestQueue= VolleySingleton.getInstance().getRequestQueue();
            requestQueue.add(stringRequest);

        /*}else {

            Log.d("hfhfhfh","fhfhf");
            ((Log_Out_Listener)dashBoard_activity).on_logout(false);
        }*/

    }

    public static boolean is_login(final Activity dashBoard_activity) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence,0);
        String str_email=sharedPreferences.getString("email", "na");
        String str_token=sharedPreferences.getString("Login_Token", "na");
        String str_type=sharedPreferences.getString("type", "na");

        if(!(str_email.equals("na")) && !(str_token.equals("na")) &&  !(str_type.equals("na"))) {
            Log.d("gfhgfhgfhg",str_email+"  "+str_token+" "+str_type);
            return true;
        }else {
           return false;
        }
    }

     public static String is_any_login(final Activity _activity) {

        SharedPreferences shprf_seater = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE,0);
         if(!shprf_seater.getString("type", "na").equals("NA"))
         {
             return shprf_seater.getString("type", "NA");
         }
         return "NA";
    }
    public static  Map<String,String> is_token(final String shrd_prf_name) {

        SharedPreferences shprf = MyApplication.getAppContext().getSharedPreferences(shrd_prf_name,0);
          Map<String,String> map=new HashMap<>();
          map.put("type",shprf.getString("type", "NA"));
          map.put("mobile",shprf.getString("mobile", "NA"));
          map.put("token",shprf.getString("token", "NA"));
         return map;
    }
    public static  String checked_num(final String sharedprefrence, String str_key) {

        SharedPreferences shprf = MyApplication.getAppContext().getSharedPreferences(sharedprefrence,0);

         return shprf.getString(str_key,"NA");
    }
    /* SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_PARTNER_Prefrence,0);
            SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence,0);
            String str_email=sharedPreferences.getString("email", "na");
            String str_token=sharedPreferences.getString("token", "na");
            String str_type=sharedPreferences.getString("type", "na");

            if(!(str_email.equals("na")) && !(str_token.equals("na")) &&  !(str_type.equals("na"))) {
                Log.d("gfhgfhgfhg",str_email+"  "+str_token+" "+str_type);
                return true;
            }else {
                return false;
            }*/
    public static boolean create_logout(final Activity dashBoard_activity) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_Seater_Pref,0);
        String str_email=sharedPreferences.getString("email", "na");
        String str_token=sharedPreferences.getString("Login_Token", "na");
        String str_type=sharedPreferences.getString("type", "na");

        if(!(str_email.equals("na")) && !(str_token.equals("na")) &&  !(str_type.equals("na"))) {
            Log.d("gfhgfhgfhg",str_email+"  "+str_token+" "+str_type);
            return true;
        }else {
            return false;
        }
    }
    public static void user_session(final Activity _activity, JSONObject jsonObject) {
        try {
            SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_Status", "L_OK");
            editor.putString("Login_Token", jsonObject.getString("token"));
            editor.putString("L_username", jsonObject.getString("uname"));
            editor.putString(KEY_EMAIL, jsonObject.getString("email"));
            editor.putString("type", jsonObject.getString("type"));
            editor.putString("mobile", jsonObject.getString("mobile"));
            editor.putString("image", jsonObject.getString("image"));
            editor.commit();
        }catch (JSONException jexp)
        {
        }
    }

    public static void profile_image(final Activity _activity, JSONObject jsonObject) {
        try {
            SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_Status", "L_OK");
            editor.putString("Login_Token", jsonObject.getString("token"));
            editor.putString("L_username", jsonObject.getString("uname"));
            editor.putString(KEY_EMAIL, jsonObject.getString("email"));
            editor.putString("type", jsonObject.getString("type"));
            editor.putString("mobile", jsonObject.getString("mobile"));
            editor.putString("image", jsonObject.getString("image"));
            editor.commit();
        }catch (JSONException jexp)
        {
        }
    }
    public static String get_session_data(final String str_key) {
            SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
            String str_value=sharedPreferences.getString(str_key, "na");
            return str_value;

    }
    public static String get_agent_session_data(final String str_key) {
            SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_PARTNER_Prefrence, 0);
            String str_value=sharedPreferences.getString(str_key, "na");
            return str_value;

    }

    public static void requestForSMS(final String mobile,final Activity act) {
        Log.d("mobils",""+mobile.length());
        Log.d("urlll",""+UrlEndpoints.OTP_GENERATE_API+mobile.toString().trim()+"/AUTOGEN");
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, UrlEndpoints.OTP_GENERATE_API+mobile.toString().trim()+"/AUTOGEN",
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

                                    SharedPreferences.Editor editor = shrd_otp_prf.edit();
                                    editor.putString("otp_api_key",UrlEndpoints.OTP_API_KEY);
                                    editor.putString("otp_session", OTP_Session);
                                    editor.putString("otp_mbl", mobile);
                                    Log.d("mbl_num",""+mobile);
                                    editor.commit();
                                    Log.d("valueesscscddf",UrlEndpoints.OTP_API_KEY+"  "+OTP_Session+"  "+mobile);

                              /*  Intent intent = new Intent("android.permission.RECEIVE_SMS");
                                sendBroadcast(intent);*/
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
        Log.d("dgdg","dhdhd");
    }


public static void set_profile_img(final Activity _activity) {

    SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence,0);
    String str_email=sharedPreferences.getString(KEY_EMAIL, "na");
    String str_token=sharedPreferences.getString(KEY_TOKEN, "na");
    Log.d("dedede", ""+str_email+"  "+str_token);
    if(!(str_email.equals("na")) && !(str_token.equals("na"))) {
        Log.d("dedede", "daddada");
        //
         String strurl=UrlEndpoints.GET_PROFILE_DATA +"email="+str_email+"&token="+str_token;
       //  String strurl="http://activeeduindia.com/admin/webservices/getProfilePicture.php?email=san@gmail.com&token=s0e2iZViIC";
         StringRequest stringRequest = new StringRequest(Request.Method.POST,strurl ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("srugfgts", response.toString());

                            if (jObj.has("data")) { // have to must convert in to string
                                Log.d("udpgad", response.toString());
                            // {"data":[{"image":"Sanjit.png"}]}
                                String strImg=jObj.getJSONArray("data").getJSONObject(0).getString(KEY_IMAGE);

                                ((Profile_Image_Listener)_activity).on_profile_image_listener(UrlEndpoints.URL_PROFILE_PATH+strImg);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(cnt, "Logout Unsuccessfull", Toast.LENGTH_LONG).show();
                        Log.d("errorlistenr",""+error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }
        };

        RequestQueue requestQueue= VolleySingleton.getInstance().getRequestQueue();
        requestQueue.add(stringRequest);

    }else {

      //  ((Profile_Image_Listener)_activity).on_profile_image_listener("");
    }

}

    public static void send_Toserver(final Fragment cnt, JSONObject bundle) {

        Log.d("formstring",""+getForm_string());
        String str_form=getForm_string();

      //  String str="\";
     //   String str_form_rep=str_form.replaceAll(" \ ","");
        Log.d("c_s_gfdr",""+getForm_string());

      try {

             Map<String,String>  form_map= new HashMap<String,String>();
            Log.d("c_s_gfdr",""+getForm_string());
            form_map.put("jsonData",getForm_string());
            Log.d("c_s_g_fdr",""+form_map.toString());
            form_map.putAll(toMap(bundle));

           final Map<String,String>  form_map_full=form_map;
           Log.d("c__gfdr",""+form_map_full.toString());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.SEND_FORM_DATA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                Log.d("srugfgts", response.toString());

                                if ((""+(jObj.getString("msg"))).equals("1")) { // have to must convert in to string
                                    Log.d("udpgad", response.toString());

                                    ((Form_Responce_Listener)cnt).on_form();
                                }
                                ((Form_Responce_Listener)cnt).on_form();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                          //  Toast.makeText(cnt, "Logout Unsuccessfull", Toast.LENGTH_LONG).show();
                          Log.d("errorlistenr",""+error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    return form_map_full;
                }
            };

            RequestQueue requestQueue= VolleySingleton.getInstance().getRequestQueue();
            requestQueue.add(stringRequest);
          } catch (Exception e) {
              e.printStackTrace();
          }

    }

    public static String getForm_string(){
        SharedPreferences form_shrd = MyApplication.getAppContext().getSharedPreferences(UpdateValues.EDU_FORM,0);
        String str_H_list=form_shrd.getString("High_School", "na");
        String str_I_list=form_shrd.getString("Inter", "na");
        String str_Dip_list=form_shrd.getString("ITI/Diploma", "na");
        String str_Gra=form_shrd.getString("Graduate", "na");

        Log.d("h_lidt",str_H_list);
        Gson gson = new Gson();
        java.lang.reflect.Type types;
        types = new TypeToken<HashMap<String,String>>(){}.getType();

        HashMap<String,String> hmap=gson.fromJson(str_H_list,types); HashMap<String,String> hmap2=gson.fromJson(str_I_list,types);
      //  HashMap<String,String> hmap3=gson.fromJson(str_Dip_list,types); HashMap<String,String> hmap4=gson.fromJson(str_Gra,types);


        HashMap<String,HashMap<String,String>> f_map=new HashMap<String,HashMap<String,String>>();
        f_map.put("High_School",hmap);
      //  f_map.put("Inter",hmap2);
        /*f_map.put("ITI/Diploma",hmap3);
        f_map.put("Graduate",hmap4);*/

        return gson.toJson(f_map, types);
    }
    public static void btn_animation(View holder,Context context )
    {
        //  Button button = (Button)findViewById(R.id.button);
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 15);
        myAnim.setInterpolator(interpolator);

        holder.startAnimation(myAnim);
    }

    public static Map<String, String> toMap(JSONObject object) throws Exception {
        Map<String, String> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, ""+(object.get(key)));
        }
        return map;
    }
    public static JSONObject toMERGE_JSON(JSONObject object1,JSONObject object2) throws Exception {

        Iterator keys = object2.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            object1.put(key, ""+(object2.get(key)));
        }
        return object1;
    }
    public static JSONObject toJSON(JSONObject object2) throws Exception {
        JSONObject object1=new JSONObject();
        Iterator keys = object2.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            object1.put(key, ""+(object2.get(key)));
        }
        return object1;
    }


   public static String get_type_session() {

       JSONObject jsonObject=null;
       String str_type=null;
       try {
           jsonObject  =new JSONObject(get_session_type());
           str_type=jsonObject.getString("type");

       } catch (Exception e) {
           e.printStackTrace();
       }

       return str_type;

   }

    public static JSONObject get_full_session_data_new() {
        try {
            SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(USER_SESSION, 0);

            if(!if_session_social())
            {
                return  new JSONObject(sharedPreferences.getString("User_ssesion", "na")).put("accesskey","9670A!@");
            }else {
                return  new JSONObject(sharedPreferences.getString("Social_ssesion", "na")).put("accesskey","9670A!@");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    public static boolean is_session_exist() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(USER_SESSION, 0);
        String str_value=sharedPreferences.getString("User_ssesion", "NA");
        String str_social=sharedPreferences.getString("Social_ssesion", "NA");

        if(str_value.equalsIgnoreCase("NA") && str_social.equalsIgnoreCase("NA"))
        {
            return false;
        }else {
            return true;
        }

    }
    public static boolean if_session_social() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(USER_SESSION, 0);
        String str_value=sharedPreferences.getString("Social_ssesion", "NA");

        if(str_value.equalsIgnoreCase("NA"))
        {
            return false;
        }else {
            return true;
        }

    }
    public static String get_session_type() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(USER_SESSION, 0);
        String str_value=sharedPreferences.getString(GUSET_SESSION, "NA");
        String str_value2=sharedPreferences.getString(REAL_USER_SESSION, "NA");
        String str_value3=sharedPreferences.getString(AGENT_SESSION, "NA");


        if(!str_value.equalsIgnoreCase("NA"))
        {
            return str_value;
        }
        if (!str_value2.equalsIgnoreCase("NA")){
            return str_value2;
        }
        if (!str_value3.equalsIgnoreCase("NA")){
            return str_value3;
        }
        return null;
    }
    public static String after_guestregisyter() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(G_PARTNER_Prefrence, 0);
        String str_value=sharedPreferences.getString("U_SESSUIN", "NA");


        if(!str_value.equalsIgnoreCase("NA"))
        {
            return str_value;
        }

        return null;
    }
    public static boolean remove_session() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(USER_SESSION, 0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear().commit();

        String str_value=sharedPreferences.getString(GUSET_SESSION, "NA");
        Log.d("renmove",""+str_value);
        if(str_value.equalsIgnoreCase("NA"))
        {
            return true;
        }
        return false;
    }
}
