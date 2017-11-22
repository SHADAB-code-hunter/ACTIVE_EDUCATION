package com.gt.active_education;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.Adapter_Facility;
import adapter.Gallery_Dapter_College_Detail;
import callbacks.Choose_newCourses_Listener;
import pojo.Gallery_Model;
import pojo.Get_Course_desc;
import task.Async_Respoce;
import task.Load_Async_Course_desc;
import utilities.Common_Pojo;
import utilities.RecyclerTouchListener;

import static utilities.UrlEndpoints.GET_CLG_COURSE;
import static utilities.UrlEndpoints.IMAGE_PATH_ADAPTER;
import static utilities.UrlEndpoints.URL_GET_FULL_DETAIL;

/**
 * Created by GT on 11/20/2017.
 */

public class Book_desc_Activity extends AppCompatActivity implements Choose_newCourses_Listener, Async_Respoce.Responce_Obj_Lisatener,View.OnClickListener {

    TextView id_course,id_fees1,d1,id_name_tv,id_fees_status,id_course_desc,id_branch_name,id_college_desc;
    TextView id_below_location,id_website,id_mail,id_call_option;
    ImageView id_collge_banner;
    private JSONObject jsonObject;
    private RecyclerView id_rv_facility;
    private GridLayoutManager verticleLayoutManager;
    private List<Common_Pojo> facility_list=new ArrayList<>();
    private ArrayList<Common_Pojo> gallery_lis;
    private RecyclerView id_gallery_recycler_view;
    private String[] str_cat_arr;
    private LinearLayout id_download_broucher;//=(LinearLayout)findViewById(R.id.id_download_broucher);id_download_broucher.setOnClickListener(this);
    private FrameLayout id_frm_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boobk_desc_activity);
        id_course=(TextView)findViewById(R.id.id_course);
        d1=(TextView)findViewById(R.id.d1);
        id_fees1=(TextView)findViewById(R.id.id_fees1);
        id_name_tv=(TextView)findViewById(R.id.id_name_tv);
        id_fees_status=(TextView)findViewById(R.id.id_fees_status);
        id_course_desc=(TextView)findViewById(R.id.id_course_desc);
        id_branch_name=(TextView)findViewById(R.id.id_branch_name);
        id_college_desc=(TextView)findViewById(R.id.id_college_desc);
        id_download_broucher=(LinearLayout)findViewById(R.id.id_download_broucher);id_download_broucher.setOnClickListener(this);
        id_below_location=(TextView)findViewById(R.id.id_below_location);
        id_call_option=(TextView)findViewById(R.id.id_call_option);
        id_website=(TextView)findViewById(R.id.id_website);
        id_mail=(TextView)findViewById(R.id.id_mail);
        id_frm_back=(FrameLayout)findViewById(R.id.id_frm_back);id_frm_back.setOnClickListener(this);
        id_collge_banner=(ImageView)findViewById(R.id.id_collge_banner);
        id_gallery_recycler_view=(RecyclerView)findViewById(R.id.id_gallery_recycler_view);
        id_rv_facility=(RecyclerView)findViewById(R.id.id_rv_facility);
        verticleLayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
        id_rv_facility.setLayoutManager(verticleLayoutManager);

        try {
        if(getIntent().getStringExtra("book_obj")!=null)
        {
            Log.d("gdgdgdgd",""+getIntent().getStringExtra("book_obj"));
            jsonObject=new JSONObject(getIntent().getStringExtra("book_obj").toString());
            id_course.setText(jsonObject.getString("course_name"));
            id_branch_name.setText("Branch Name : "+jsonObject.getString("branch_name"));
            id_fees1.setText("Fee :"+jsonObject.getString("fee"));

            id_name_tv.setText(jsonObject.getString("clg_name"));
            if(jsonObject.getString("fee_status").equals("0")) {

                id_fees_status.setText("Fees Status :" + "Pending");
            }else {
                id_fees_status.setText("Fees Status :" + "Paid");
            }

            Glide.with(Book_desc_Activity.this)
                    .load(jsonObject.getString("image_path"))
                    .into(id_collge_banner);
        }

        new Load_Async_Course_desc(Book_desc_Activity.this,GET_CLG_COURSE+
                "clgid="+jsonObject.getString("clg_id")
                +"&branch="+jsonObject.getString("branch_id") // 0
                +"&type="+jsonObject.getString("category")
                +"&course="+jsonObject.getString("course_id"),"book_status").execute(); // 0


            Map<String,String> map=new HashMap<>();
            map.put("type",""+jsonObject.getString("category"));
            map.put("id",""+jsonObject.getString("clg_id"));

        new Async_Respoce(Book_desc_Activity.this,URL_GET_FULL_DETAIL, map).execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void on_choose_courses(List<Get_Course_desc> listMovies, String status_call) {

        id_course_desc.setText(listMovies.get(0).getC_info());
        d1.setText("Duration :"+listMovies.get(0).getC_duration());
             /* Glide.with(Book_desc_Activity.this)
                .load()*/

    }
    private void call_Download() {

        //   String brouchure_doewnload=BROUCHURE_DOWNLOAD+str_type+"&brochure="+str_brouchuer;
        String brouchure_doewnload="http://activeeduindia.com/admin/webservices/downloadBrochure.php?type=1&brochure=dpsdwarka.pdf";

        if (brouchure_doewnload!=null) {
            if (isSubstring("http://", brouchure_doewnload)) {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(Book_desc_Activity.this, Website_Activity.class);
                intent.putExtra("Url_Web", brouchure_doewnload);
                startActivity(intent);
            } else {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(Book_desc_Activity.this, Website_Activity.class);
                intent.putExtra("Url_Web", "http://" + brouchure_doewnload);
                startActivity(intent);
            }
        }else {

            Toast.makeText(Book_desc_Activity.this, "Link Not Found !!!!", Toast.LENGTH_SHORT).show();

        }

    }

    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }

    @Override
    public void on_responce(JSONObject listMovies) {
        Log.d("hc_jhdcj",""+listMovies.toString());

        try {
            id_college_desc.setText(listMovies.getJSONArray("data").getJSONObject(0).getString("c_info"));

            id_name_tv.setText(listMovies.getJSONArray("data").getJSONObject(0).getString("c_name"));
            id_below_location.setText(listMovies.getJSONArray("data").getJSONObject(0).getString("c_address"));
            id_college_desc.setText(listMovies.getJSONArray("data").getJSONObject(0).getString("c_info"));
            id_mail.setText(listMovies.getJSONArray("data").getJSONObject(0).getString("c_email"));
            id_website.setText(listMovies.getJSONArray("data").getJSONObject(0).getString("c_website"));

            /* faciity*/

            JSONArray jsonArray2=listMovies.getJSONArray("facility");
            //JSONArray jsonArray=jsonObject1.getJSONArray("facility");
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
            Adapter_Facility adapter_city=new Adapter_Facility(Book_desc_Activity.this,facility_list);
            id_rv_facility.setAdapter(adapter_city);


            /* gallery*/

              /* ==================      banner  =====================*/


            String halg_img_path=IMAGE_PATH_ADAPTER;
            // Log.d("kfjk",map.get("id"));
            str_cat_arr= getResources().getStringArray(R.array.category_image);
            if(jsonObject.getString("category")!=null) {
                // str_type=str_url.split("=")[1];

                switch (jsonObject.getString("category")) {
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

            JSONArray json_gallery=listMovies.getJSONArray("gallery");
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
            //  Gallery_Dapter_College_Detail adapter_gallery=new Gallery_Dapter_College_Detail(Book_desc_Activity.this,jsonObject.getJSONArray("gallery"),halg_img_path);
            Gallery_Dapter_College_Detail adapter_gallery=new Gallery_Dapter_College_Detail(Book_desc_Activity.this,gallery_lis,halg_img_path);
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


        } catch (JSONException e) {
           Log.d("excep_eroor",""+e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_download_broucher:

                call_Download();
                break;
            case R.id.id_frm_back:
                finish();
                break;

        }
    }
}
