package com.gt.active_education;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.Adapter_Facility;
import adapter.Banner_Adapter;
import adapter.Common_pojo_adapter;
import adapter.Gallery_demo_adapter;
import callbacks.CLG_DESC_Listener;
import callbacks.Choose_newCourses_Listener;
import callbacks.Upcoming_List_LoadedListener;
import pojo.Cat_Model;
import pojo.Get_Course_desc;
import task.Async_Respoce;
import task.Load_Async_Course_desc;
import task.Load_College_Desc_Async;
import task.Load_Course_AVail_Data;
import task.Load_Courses_Data;
import utilities.App_Static_Method;
import utilities.Common_Pojo;
import utilities.UrlEndpoints;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        id_name_tv=(TextView)findViewById(R.id.id_name_tv);
        id_add_tv=(TextView)findViewById(R.id.id_add_tv);
        id_college_desc=(TextView)findViewById(R.id.id_college_desc);
     //   id_recycler_view=(RecyclerView)findViewById(R.id.id_recycler_view);
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

        ((Button) findViewById(R.id.id_btn_apply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent()!=null) {
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
        viewPager=(ViewPager)findViewById(R.id.id_collge_banner);
        id_linear_change_course=(LinearLayout)findViewById(R.id.id_linear_change_course);
        id_linear_change_course.setOnClickListener(this);

        if (str_brnch_name!=null)
        id_branch_name.setText(str_brnch_name);

        LinearLayout id_change_courses_linear=(LinearLayout)findViewById(R.id.id_change_courses_linear);
        id_change_courses_linear.setOnClickListener(this);
        Toast.makeText(this,""+str_clg_id+" "+str_type, Toast.LENGTH_SHORT).show();
        Log.d("collegeid_",""+str_clg_id+" "+str_type);
        id_college_desc_url.setText(UrlEndpoints.GET_CLG_DESC+"id="+str_clg_id+"&type="+str_type);
        new Load_College_Desc_Async(College_Detail_Activity.this, UrlEndpoints.GET_CLG_DESC+"id="+str_clg_id+"&type="+str_type).execute();
        //  new Load_Courses_Data(College_Detail_Activity.this,UrlEndpoints.GET_COURSE_LIST+str_clg_id).execute();
        String url=UrlEndpoints.GET_CLG_COURSE+"clgid="+str_clg_id+"&branch="+str_brn_id+"&course="+str_course_id;
        String status_call="activity";
        id_corse_url.setText(url+" _________________________   desc ________________"+UrlEndpoints.URL_TOP_COURSES);
        new Load_Async_Course_desc(College_Detail_Activity.this,url,status_call).execute();
        new Load_Courses_Data(College_Detail_Activity.this, UrlEndpoints.URL_TOP_COURSES).execute();

        int[] Img_Bnr=new int[]{R.drawable.ic_manav_rcahna_banner,R.drawable.ic_manav_rcahna_banner,R.drawable.ic_manav_rcahna_banner,
                R.drawable.ic_manav_rcahna_banner,R.drawable.ic_manav_rcahna_banner,R.drawable.ic_manav_rcahna_banner};

        Gallery_demo_adapter galleryDemoAdapter=new Gallery_demo_adapter(College_Detail_Activity.this,Img_Bnr);
        GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
        id_gallery_recycler_view.setLayoutManager(horizontal_LayoutManager);
        id_gallery_recycler_view.setItemAnimator(new DefaultItemAnimator());
        id_gallery_recycler_view.setAdapter(galleryDemoAdapter);

        new Async_Respoce(College_Detail_Activity.this,URL_GET_FULL_DETAIL, map).execute();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_linear_change_course:
                clg_dialog=new Colleg_dialog(College_Detail_Activity.this,
                        UrlEndpoints.GET_COURSE_LIST+str_clg_id,str_clg_id);
                clg_dialog.show();
                break;

            case R.id.id_frm_back:
                finish();
                break;
             case R.id.id_download_broucher:
                 call_Download();
                    break;
        }
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
                clg_dialog.cancel();
            }
        id_branch_name.setText(listMovies.get(0).getCname());
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
    public void on_responce(JSONObject jsonObject) {
       // {"data":[{"added_date":"2017-10-23 05:44:07","c_address":"Sector 3, Phase-I Dwarka, New Delhi","c_city":"1038","c_country":"INDIA","c_email":"mail@dpsdwarka.com","c_established":"1997","c_id":"dps","c_image":"dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg","c_info":"The core philosophy of DPS Dwarka is its endeavour to instill a value system in each individual, so as to help him withstand all the tests of time. We believe that true education is training of both the head & the heart. Academic excellence is desirable but inculcating good values is the essence of education.","c_name":"Delhi Public School","c_phone1":"011 25074472-75","c_phone2":null,"c_state":"36","c_type":"PRIVATE","c_website":"http:\/\/www.dpsdwarka.com\/dpsdwarka\/","edit_date":"2017-10-27 13:21:25","rating":"4","brochure":"dpsdwarka.pdf","id":"2","is_active":"1","user_id":null,"c_banner":"dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg","class":"10","c_facility":"2,3,4,9,12,22,29,30","gallery":["dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg"],"banner":["dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg"],"facility":[{"name":"MATH LAB","image":"picture.png"},{"name":"Placement","image":"picture.png"},{"name":"A\/C","image":"desig12.jpg"},{"name":"CANTEEN","image":"picture.png"},{"name":"COUNSELLING","image":"picture.png"},{"name":"LIBRARY","image":"picture.png"},{"name":"SHOPPING","image":"picture.png"},{"name":"CLUB","image":"picture.png"}]},{"added_date":"2017-10-23 05:44:07","c_address":"Sector 3, Phase-I Dwarka, New Delhi","c_city":"1038","c_country":"INDIA","c_email":"mail@dpsdwarka.com","c_established":"1997","c_id":"dps","c_image":"dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg","c_info":"The core philosophy of DPS Dwarka is its endeavour to instill a value system in each individual, so as to help him withstand all the tests of time. We believe that true education is training of both the head & the heart. Academic excellence is desirable but inculcating good values is the essence of education.","c_name":"Delhi Public School","c_phone1":"011 25074472-75","c_phone2":null,"c_state":"36","c_type":"PRIVATE","c_website":"http:\/\/www.dpsdwarka.com\/dpsdwarka\/","edit_date":"2017-10-27 13:21:25","rating":"4","brochure":"dpsdwarka.pdf","id":"2","is_active":"1","user_id":null,"c_banner":"dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg,dpsdwarka.jpg","class":"1","c_facility":"2,3,4,9,12,22,29,30","gallery":["dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg"],"banner":["dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg","dpsdwarka.jpg"],"facility":[{"name":"MATH LAB","image":"picture.png"},{"name":"Placement","image":"picture.png"},{"name":"A\/C","image":"desig12.jpg"},{"name":"CANTEEN","image":"picture.png"},{"name":"COUNSELLING","image":"picture.png"},{"name":"LIBRARY","image":"picture.png"},{"name":"SHOPPING","image":"picture.png"},{"name":"CLUB","image":"picture.png"}]}]}
        Log.d("ccvv",""+jsonObject.toString());
        try {
            String str_add="na";
            String str_info="na";
            String str_nameclg="na";
            JSONArray jsonArray1= jsonObject.getJSONArray("data");
            JSONObject jsonObject1= jsonArray1.getJSONObject(0);
            str_nameclg=jsonObject1.getString("c_name");
            str_add=jsonObject1.getString("c_address");
            str_info=jsonObject1.getString("c_info");

            id_name_tv.setText(str_nameclg);
            id_add_tv.setText(str_add);
            id_college_desc.setText(str_info);
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

            int[] Img_Bnr=new int[]{R.drawable.ic_manav_rcahna_banner,R.drawable.ic_manav_rcahna_banner,R.drawable.ic_manav_rcahna_banner,
                    R.drawable.ic_manav_rcahna_banner,R.drawable.ic_manav_rcahna_banner,R.drawable.ic_manav_rcahna_banner};
            JSONArray jsongallery=jsonObject.getJSONArray("gallery");
            JSONObject jsonobject= jsongallery.getJSONObject(0);
            Log.d("hcjcvvhdcj",""+jsongallery.toString());

            /*String[] Img_Bnr=null;
            for(int i=0; i<jsongallery.length();i++)
            {
                Img_Bnr=jsongallery.get(i);
            }*/
            id_collge_banner.setAdapter(new Banner_Adapter(getApplicationContext(),Img_Bnr));

        } catch (JSONException e) {
                Log.d("nmchbbh","hhfhfh");
        }

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
