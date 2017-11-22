package com.gt.active_education;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Zend_Chat.UserProfile;
import Zend_Chat.UserProfileStorage;
import adapter.Adapter_Facility;
import adapter.Banner_Adapter;
import adapter.Banner_Adapter_Clg;
import adapter.Common_pojo_adapter;
import adapter.Gallery_Dapter_College_Detail;
import adapter.Gallery_demo_adapter;
import callbacks.CLG_DESC_Listener;
import callbacks.Choose_newCourses_Listener;
import callbacks.Upcoming_List_LoadedListener;
import pojo.Cat_Model;
import pojo.Gallery_Model;
import pojo.Get_Course_desc;
import task.Async_Respoce;
import task.Load_Async_Course_desc;
import task.Load_College_Desc_Async;
import task.Load_Course_AVail_Data;
import task.Load_Courses_Data;
import utilities.App_Static_Method;
import utilities.Change_Courses_Dialog;
import utilities.Common_Pojo;
import utilities.RecyclerTouchListener;
import utilities.UrlEndpoints;

import static utilities.App_Static_Method.show_load_progress;
import static utilities.UrlEndpoints.GET_CLG_COURSE;
import static utilities.UrlEndpoints.GET_COURSE_LIST;
import static utilities.UrlEndpoints.IMAGE_PATH_ADAPTER;
import static utilities.UrlEndpoints.URL_GET_FULL_DETAIL;

/**
 * Created by GT on 7/25/2017.
 */

public class College_Detail_Activity extends AppCompatActivity implements View.OnClickListener, Choose_newCourses_Listener, CLG_DESC_Listener , Upcoming_List_LoadedListener,
        Async_Respoce.Responce_Obj_Lisatener, Load_Course_AVail_Data.List_LoadedListener {

    private  ViewPager viewPager;
    private  String cid;
    private  Colleg_dialog clg_dialog;
    private  TextView id_tv_course,id_course_desc,id_tv_clg_desc,id_course;
    private  String str_clg_id,str_course_id,str_brn_id,str_type,str_brnch_name;
    private  LinearLayout id_linear_change_course;
    private  RecyclerView id_recycler_view,id_gallery_recycler_view;
    private  TextView id_branch_name;
    private FrameLayout id_frm_back;
    private TextView id_corse_url;
    private TextView id_college_desc_url;
    private Map<String,String> map=new HashMap<>();
    private List<Common_Pojo> facility_list=new ArrayList<>();
    private TextView id_name_tv,id_add_tv,id_college_desc;
    private RecyclerView id_avail_recycler_view;
    private RecyclerView id_rv_facility;
    private GridLayoutManager verticleLayoutManager;
    private ViewPager id_collge_banner;
    private LinearLayout id_download_broucher;
    private String[] str_cat_arr;
    private TextView id_mail;
    private TextView id_website;
    private ProgressDialog progressDialog;
    private ArrayList<Common_Pojo> gallery_lis;
    private TextView id_below_location;
    private String str_course_name;
    private Change_Courses_Dialog change_courses_dialog;
    private LinearLayout id_view_more_linear;
    private TextView id_duration;
    private TextView id_fees2;
    private FrameLayout  id_call,id_frm_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        id_name_tv=(TextView)findViewById(R.id.id_name_tv);
        id_add_tv=(TextView)findViewById(R.id.id_add_tv);
        id_college_desc=(TextView)findViewById(R.id.id_college_desc);
        id_below_location=(TextView)findViewById(R.id.id_below_location);
     // id_recycler_view=(RecyclerView)findViewById(R.id.id_recycler_view);
        id_gallery_recycler_view=(RecyclerView)findViewById(R.id.id_gallery_recycler_view);
        id_frm_back=(FrameLayout)findViewById(R.id.id_frm_back);id_frm_back.setOnClickListener(this);
        id_branch_name=(TextView)findViewById(R.id.id_branch_name);
        id_corse_url=(TextView)findViewById(R.id.id_corse_url);
        id_college_desc_url=(TextView)findViewById(R.id.id_college_desc_url);
        id_rv_facility=(RecyclerView)findViewById(R.id.id_rv_facility);
        verticleLayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
        id_rv_facility.setLayoutManager(verticleLayoutManager);
        id_avail_recycler_view=(RecyclerView) findViewById(R.id.id_avail_recycler_view);
        id_collge_banner=(ViewPager)findViewById(R.id.id_collge_banner);
        id_download_broucher=(LinearLayout)findViewById(R.id.id_download_broucher);id_download_broucher.setOnClickListener(this);
        id_mail=(TextView)findViewById(R.id.id_mail);
        id_website=(TextView)findViewById(R.id.id_website);
        id_view_more_linear=(LinearLayout)findViewById(R.id.id_view_more_linear);id_view_more_linear.setOnClickListener(this);
        id_duration=(TextView)findViewById(R.id.id_duration);
        id_fees2=(TextView)findViewById(R.id.id_fees2);

        id_call=(FrameLayout)findViewById(R.id.id_call);id_call.setOnClickListener(this);
        id_frm_chat=(FrameLayout)findViewById(R.id.id_frm_chat);id_frm_chat.setOnClickListener(this);

        progressDialog =show_load_progress(College_Detail_Activity.this,getString(R.string.Loading));

        ((Button) findViewById(R.id.id_btn_apply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent()!=null) {
                 //   Log.d("dlkldkl",""+)
                    Intent intent=new Intent(getApplicationContext(),Admission_Form_Activity.class);
                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);
                }
            }
        });
        if(!getIntent().equals(null))
        {
            str_clg_id=getIntent().getStringExtra("clg_id");
            str_course_id=getIntent().getStringExtra("course");
            str_brn_id=getIntent().getStringExtra("branch");
            str_course_name=getIntent().getStringExtra("course_name");
       //     Toast.makeText(this, ""+str_brn_id, Toast.LENGTH_SHORT).show();
            if (getIntent().getStringExtra("type")!=null)
            {
                str_type=getIntent().getStringExtra("type");
            }
            if(getIntent().getStringExtra("branch_name")!=null)
            {
                str_brnch_name=getIntent().getStringExtra("branch_name");
            }
            map.put("id",str_clg_id);
            map.put("type",str_type);
        }
        Toast.makeText(this, "URl ::  "+str_type, Toast.LENGTH_SHORT).show();
        new Load_Course_AVail_Data(College_Detail_Activity.this, UrlEndpoints.URL_GET_AVAIL_COURSES+"id="+str_clg_id+"&type="+str_type).execute();
        id_course=(TextView)findViewById(R.id.id_course);
        id_course_desc=(TextView)findViewById(R.id.id_course_desc);
        id_tv_clg_desc=(TextView)findViewById(R.id.id_tv_clg_desc);
        id_course.setText(str_course_name);
        id_linear_change_course=(LinearLayout)findViewById(R.id.id_linear_change_course);
        id_linear_change_course.setOnClickListener(this);

        if (str_brnch_name!=null)
        id_branch_name.setText(str_brnch_name);

        LinearLayout id_change_courses_linear=(LinearLayout)findViewById(R.id.id_change_courses_linear);
        id_change_courses_linear.setOnClickListener(this);
     //   Toast.makeText(this,""+str_clg_id+" "+str_type, Toast.LENGTH_SHORT).show();
        Log.d("collegeid_",""+str_clg_id+" "+str_type);
        id_college_desc_url.setText(UrlEndpoints.GET_CLG_DESC+"id="+str_clg_id+"&type="+str_type);
        new Load_College_Desc_Async(College_Detail_Activity.this, UrlEndpoints.GET_CLG_DESC+"id="+str_clg_id+"&type="+str_type).execute();

        String status_call="activity";
      /*  id_corse_url.setText(url+" _________________________   desc ________________"+UrlEndpoints.URL_TOP_COURSES);
        *//* hit api */
        new Load_Async_Course_desc(College_Detail_Activity.this,GET_CLG_COURSE+
                "clgid="+str_clg_id
                +"&branch="+str_brn_id
                +"&type="+str_type
                +"&course="+str_course_id,status_call).execute();
        new Load_Courses_Data(College_Detail_Activity.this, UrlEndpoints.URL_TOP_COURSES).execute();
        new Async_Respoce(College_Detail_Activity.this,URL_GET_FULL_DETAIL, map).execute();


        id_avail_recycler_view.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), id_avail_recycler_view,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {

                        show_Chenge_Courses_Dialog();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));

    }


    public void show_Chenge_Courses_Dialog()
    {
        change_courses_dialog=new Change_Courses_Dialog(College_Detail_Activity.this, GET_COURSE_LIST + str_clg_id,
                new Change_Courses_Dialog.Change_Course_Listener() {
                    @Override
                    public void onCourse_Dialog(Map<String, String> map) {
                        Log.d("mdmdmmd",""+map);
                        change_courses_dialog.cancel();
                        new Load_Async_Course_desc(((Choose_newCourses_Listener)College_Detail_Activity.this),GET_CLG_COURSE+
                                "clgid="+str_clg_id
                                +"&branch="+map.get("branch_id")
                                +"&type="+str_type
                                +"&course="+map.get("course_id"),"dialog").execute();

                    }
                });
        change_courses_dialog.show();

    }
    static class AuthOnClickWrapper implements View.OnClickListener {

        private View.OnClickListener mOnClickListener;
        private UserProfileStorage mUserProfileStorage;
        private Context mContext;

        public AuthOnClickWrapper(View.OnClickListener onClickListener, Context context){
            this.mOnClickListener = onClickListener;
            this.mUserProfileStorage = new UserProfileStorage(context);
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {
            final UserProfile profile = mUserProfileStorage.getProfile();

            //  if(StringUtils.hasLength("ahmed.shadab221@gmail.com")){
            mOnClickListener.onClick(v);
            //   }else{
            // showDialog();
            //  }
        }

       /* private void showDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage(R.string.dialog_auth_title)
                    .setPositiveButton(R.string.dialog_auth_positive_btn, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                          //  startActivity(new Intent(mContext, CreateProfileActivity.class));
                        }
                    })
                    .setNegativeButton(R.string.dialog_auth_negative_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Intentionally empty
                        }
                    });
            builder.create().show();
        }*/
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_linear_change_course:
                show_Chenge_Courses_Dialog();
                break;

            case R.id.id_frm_back:
                finish();
                break;

             case R.id.id_download_broucher:

                 call_Download();
                    break;
            case R.id.id_view_more_linear:

                    break;

            case R.id.id_call:

                String phoneNumber = "01294014664";

                if (checkPermission(android.Manifest.permission.CALL_PHONE)) {
                    String dial = "tel:" + phoneNumber;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(College_Detail_Activity.this, "Permission Phone Call denied", Toast.LENGTH_SHORT).show();
                }

                break;



            case R.id.id_frm_chat:


                AuthOnClickWrapper authOnClickWrapper=  new AuthOnClickWrapper(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        PreChatForm build = new PreChatForm.Builder()
                                .name(PreChatForm.Field.REQUIRED)
                                .email(PreChatForm.Field.REQUIRED)
                                .phoneNumber(PreChatForm.Field.OPTIONAL)
                                .message(PreChatForm.Field.OPTIONAL)
                                .build();

                        ZopimChat.SessionConfig department = new ZopimChat.SessionConfig()
                                .preChatForm(build)
                                .department("The date");

                        ZopimChatActivity.startActivity(College_Detail_Activity.this, department);
                    }
                },getApplicationContext());
                break;
        }
    }
    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void call_Download() {

        //   String brouchure_doewnload=BROUCHURE_DOWNLOAD+str_type+"&brochure="+str_brouchuer;
        String brouchure_doewnload="http://activeeduindia.com/admin/webservices/downloadBrochure.php?type=1&brochure=dpsdwarka.pdf";

        if (brouchure_doewnload!=null) {
            if (isSubstring("http://", brouchure_doewnload)) {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(College_Detail_Activity.this, Website_Activity.class);
                intent.putExtra("Url_Web", brouchure_doewnload);
                startActivity(intent);
            } else {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(College_Detail_Activity.this, Website_Activity.class);
                intent.putExtra("Url_Web", "http://" + brouchure_doewnload);
                startActivity(intent);
            }
        }else {

            Toast.makeText(College_Detail_Activity.this, "Link Not Found !!!!", Toast.LENGTH_SHORT).show();

        }

    }
    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }

    @Override
    public void on_choose_courses(List<Get_Course_desc> listMovies, String status_call) {

//        Log.d("courseget",""+listMovies.get(0).getC_name());
             if (listMovies.isEmpty())
                 return;

            id_course.setText(listMovies.get(0).getC_name());
            if (listMovies.get(0).getC_info()!=null)
            id_course_desc.setText(listMovies.get(0).getC_info());
            if (status_call.equals("dialog")) {
                change_courses_dialog.cancel();
            }
        id_branch_name.setText(listMovies.get(0).getCname());
        if(!listMovies.get(0).getC_duration().equals("NA"))
        id_duration.setText("  "+listMovies.get(0).getC_duration()+" Year /"+(2*Integer.parseInt(listMovies.get(0).getC_duration()))+" Semester ");

        id_fees2.setText("  "+listMovies.get(0).getBranch_fee()+"/Annually");
      }

    @Override
    public void onDesc_Data(List<Cat_Model> listMovies) {
        if (listMovies.get(0).getC_info()!=null) {
            id_tv_clg_desc.setText(listMovies.get(0).getC_info());
        }
    }

    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies) {

    }

    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies, String poss) {

    }

    @Override
    public void onUpcomingcourses(List<Common_Pojo> common_pojos) {
      /*  Log.d("sgfrt",common_pojos.toString());
        Common_pojo_adapter mAdapter = new Common_pojo_adapter(College_Detail_Activity.this,common_pojos);
        GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),4,
                LinearLayoutManager.VERTICAL, false);
        id_recycler_view.setLayoutManager(horizontal_LayoutManager);
        id_recycler_view.setItemAnimator(new DefaultItemAnimator());
        id_recycler_view.setAdapter(mAdapter);*/
    }

    @Override
    public void onUpcomingexams(List<Common_Pojo> common_pojos) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        progressDialog =show_load_progress(College_Detail_Activity.this,getString(R.string.Loading));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(progressDialog!=null);
        progressDialog.cancel();
    }

    @Override
    public void on_responce(JSONObject jsonObject) {
       // {"data":[{"added_date":"2017-10-23 05:44:07","c_address":"Sector 3, Phase-I Dwarka, New Delhi","c_city":"1038","c_country":"INDIA","c_email":"mail@dpsdwarka.com","c_established":"1997","c_id":"dps","c_image":"dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg","c_info":"The core philosophy of DPS Dwarka is its endeavour to instill a value system in each individual, so as to help him withstand all the tests of time. We believe that true education is training of both the head & the heart. Academic excellence is desirable but inculcating good values is the essence of education.","c_name":"Delhi Public School","c_phone1":"011 25074472-75","c_phone2":null,"c_state":"36","c_type":"PRIVATE","c_website":"http:\/\/www.dpsdwarka.com\/dpsdwarka\/","edit_date":"2017-10-27 13:21:25","rating":"4","brochure":"dpsdwarka.pdf","id":"2","is_active":"1","user_id":null,"c_banner":"dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg","class":"10","c_facility":"2,3,4,9,12,22,29,30","gallery":["dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg"],"banner":["dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg"],"facility":[{"name":"MATH LAB","image":"picture.png"},{"name":"Placement","image":"picture.png"},{"name":"A\/C","image":"desig12.jpg"},{"name":"CANTEEN","image":"picture.png"},{"name":"COUNSELLING","image":"picture.png"},{"name":"LIBRARY","image":"picture.png"},{"name":"SHOPPING","image":"picture.png"},{"name":"CLUB","image":"picture.png"}]},{"added_date":"2017-10-23 05:44:07","c_address":"Sector 3, Phase-I Dwarka, New Delhi","c_city":"1038","c_country":"INDIA","c_email":"mail@dpsdwarka.com","c_established":"1997","c_id":"dps","c_image":"dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg","c_info":"The core philosophy of DPS Dwarka is its endeavour to instill a value system in each individual, so as to help him withstand all the tests of time. We believe that true education is training of both the head & the heart. Academic excellence is desirable but inculcating good values is the essence of education.","c_name":"Delhi Public School","c_phone1":"011 25074472-75","c_phone2":null,"c_state":"36","c_type":"PRIVATE","c_website":"http:\/\/www.dpsdwarka.com\/dpsdwarka\/","edit_date":"2017-10-27 13:21:25","rating":"4","brochure":"dpsdwarka.pdf","id":"2","is_active":"1","user_id":null,"c_banner":"dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg","class":"1","c_facility":"2,3,4,9,12,22,29,30","gallery":["dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg"],"banner":["dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg"],"facility":[{"name":"MATH LAB","image":"picture.png"},{"name":"Placement","image":"picture.png"},{"name":"A\/C","image":"desig12.jpg"},{"name":"CANTEEN","image":"picture.png"},{"name":"COUNSELLING","image":"picture.png"},{"name":"LIBRARY","image":"picture.png"},{"name":"SHOPPING","image":"picture.png"},{"name":"CLUB","image":"picture.png"}]}]}
        Log.d("ccvv",""+jsonObject.toString());
        try {
            String str_add="na";
            String str_info="na";
            String str_nameclg="na";
            String str_email="na";
            String str_website="na";
            JSONArray jsonArray1= jsonObject.getJSONArray("data");
            JSONObject jsonObject1= jsonArray1.getJSONObject(0);
            str_nameclg=jsonObject1.getString("c_name");
            str_add=jsonObject1.getString("c_address");
            str_info=jsonObject1.getString("c_info");
            str_email=jsonObject1.getString("c_email");
            str_website=jsonObject1.getString("c_website");

            id_name_tv.setText(str_nameclg);
            id_add_tv.setText(str_add);
            id_below_location.setText(str_add);
            id_college_desc.setText(str_info);
            id_mail.setText(str_email);
            id_website.setText(str_website);
            JSONArray jsonArray2=jsonObject.getJSONArray("facility");
          //  JSONArray jsonArray=jsonObject1.getJSONArray("facility");
            Log.d("hcjhdcj",""+jsonArray2.toString());

                   for(int i=0; i<jsonArray2.length();i++)
                    {
                        Common_Pojo common_pojo=new Common_Pojo();
                        JSONObject jsonObject2=jsonArray2.getJSONObject(i);
                        common_pojo.setName(jsonObject2.getString("name"));
                        common_pojo.setDesc(jsonObject2.getString("image"));
                        facility_list.add(common_pojo);
                        Log.d("ccvv",""+jsonArray2.length()+"  "+jsonObject2.getString("image")+"  "+jsonObject2.getString("name"));
                   }
            Adapter_Facility adapter_city=new Adapter_Facility(College_Detail_Activity.this,facility_list);
            id_rv_facility.setAdapter(adapter_city);
            /*  ==================== facility ==========================       */

            JSONArray json_banner=jsonObject.getJSONArray("banner");

            Log.d("hcjcvvhdcj",""+json_banner.toString());
            ArrayList<String> al_str=new ArrayList<>();
            for(int i=0; i<json_banner.length();i++)
            {
                JSONObject jsonobject= json_banner.getJSONObject(i);
                String Img_Bnr_ = jsonobject.getString("name");

                al_str.add(Img_Bnr_);
            }


            String halg_img_path=IMAGE_PATH_ADAPTER;
           // Log.d("kfjk",map.get("id"));
            str_cat_arr= getResources().getStringArray(R.array.category_image);
            if(str_type!=null) {
               // str_type=str_url.split("=")[1];

                switch (str_type) {
                    case "1":
                        halg_img_path = halg_img_path + str_cat_arr[0];
                        break;

                    case "2":
                        halg_img_path = halg_img_path + str_cat_arr[1];

                        break;
                    case "3":
                        halg_img_path = halg_img_path + str_cat_arr[2];

                        break;
                    case "4":
                        halg_img_path = halg_img_path + str_cat_arr[3];
                        break;
                    case "5":
                        halg_img_path = halg_img_path + str_cat_arr[4];
                        break;
                    case "6":
                        halg_img_path = halg_img_path + str_cat_arr[5];
                        break;
                }
            }
            id_collge_banner.setAdapter(new Banner_Adapter_Clg(getApplicationContext(),al_str,halg_img_path));

            /* ==================      banner  =====================*/
            JSONArray json_gallery=jsonObject.getJSONArray("gallery");
            //  JSONArray jsonArray=jsonObject1.getJSONArray("facility");
            Log.d("hcjhdcj",""+json_gallery.toString());
            gallery_lis=new ArrayList<>();
            for(int i=0; i<json_gallery.length();i++)
            {
                Common_Pojo common_pojo=new Common_Pojo();
                JSONObject jsonObject2=json_gallery.getJSONObject(i);
                common_pojo.setName(jsonObject2.getString("name"));
                gallery_lis.add(common_pojo);
                Log.d("galleryimg",""+json_gallery.length()+"  "+jsonObject2.getString("name"));
            }
          //  Gallery_Dapter_College_Detail adapter_gallery=new Gallery_Dapter_College_Detail(College_Detail_Activity.this,jsonObject.getJSONArray("gallery"),halg_img_path);
            Gallery_Dapter_College_Detail adapter_gallery=new Gallery_Dapter_College_Detail(College_Detail_Activity.this,gallery_lis,halg_img_path);
            GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
            id_gallery_recycler_view.setLayoutManager(horizontal_LayoutManager);
            id_gallery_recycler_view.setAdapter(adapter_gallery);
            final String finalHalg_img_path = halg_img_path;
            id_gallery_recycler_view.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), id_gallery_recycler_view,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, final int position) {

                            Gallery_Model sendDateModel = new Gallery_Model();
                            sendDateModel.setImage_name(gallery_lis.get(position).getName());

                            ArrayList<Gallery_Model> gallerylist=new ArrayList<Gallery_Model>();gallerylist.add(sendDateModel);

                            Intent i = new Intent(getApplicationContext(), Big_ImgView_Activity.class);
                            Log.d("vkkcvkcnk", finalHalg_img_path+"/picture/" +"     :    "+gallery_lis.get(0).getName());
                            i.putExtra("URL", finalHalg_img_path+"/picture/");
                            i.putParcelableArrayListExtra("list", gallerylist);
                            i.putExtra("position_click",""+position);
                            i.putExtra("click_page","Gallery");
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }
            ));

            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(progressDialog!=null)
                        progressDialog.cancel();

                    Log.d("gggggdd","jfghgrt");
                }
            });
            t.start();

        } catch (JSONException e) {
                Log.d("exception",""+e.getMessage());
        }

    }
    @Override
    protected void onPostResume() {
        super.onPostResume();

        /*if(progressDialog!=null)
            progressDialog.cancel();

        Log.d("gggggdd","jfghgrt");*/
    }
        // avail courses
    @Override
    public void onavail_courses(List<Common_Pojo> common_pojos) {
        Log.d("bdcb",common_pojos.toString());
        if(common_pojos==null)
            return;
        Common_pojo_adapter mAdapter = new Common_pojo_adapter(College_Detail_Activity.this,common_pojos);
        GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),4, LinearLayoutManager.VERTICAL, false);
        id_avail_recycler_view.setLayoutManager(horizontal_LayoutManager);
        id_avail_recycler_view.setItemAnimator(new DefaultItemAnimator());
        id_avail_recycler_view.setAdapter(mAdapter);

    }
}
