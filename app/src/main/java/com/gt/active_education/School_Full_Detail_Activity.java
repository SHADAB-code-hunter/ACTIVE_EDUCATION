package com.gt.active_education;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import adapter.Banner_Adapter_JSon;
import adapter.Facility_Adapter_JSon;
import adapter.Gallery_Dapter_school_json;
import callbacks.CALL_ADAPTER;
import task.New_Asynch_MAp;
import utilities.App_Static_Method;

import static utilities.UrlEndpoints.SCHOOL_AVAIL_CLASS_FULL;

/**
 * Created by GT on 12/5/2017.
 */

public class School_Full_Detail_Activity extends AppCompatActivity implements View.OnClickListener {


    private ViewPager id_collge_banner;
    private TextView id_name_tv,id_course,duration,id_fees1,id_course_desc,id_college_desc;
    private RecyclerView id_rv_facility;
    private GridLayoutManager verticleLayoutManager,verticleLayoutManager2;
    private JSONObject jsonObject;
    private LinearLayout id_download_broucher;
    private RecyclerView id_gallery_recycler_view;
    private GridLayoutManager verticleLayoutManager3;
    private RecyclerView id_rv_certficate;
    private TextView id_below_location,id_website,id_mail,id_afflictiobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);
        id_collge_banner=(ViewPager)findViewById(R.id.id_collge_banner);
        id_name_tv=(TextView)findViewById(R.id.id_name_tv);
        id_course=(TextView)findViewById(R.id.id_course);
        id_fees1=(TextView)findViewById(R.id.id_fees1);
        duration=(TextView)findViewById(R.id.d1);
        id_course_desc=(TextView)findViewById(R.id.id_course_desc);
        id_college_desc=(TextView)findViewById(R.id.id_college_desc);
        id_rv_facility=(RecyclerView)findViewById(R.id.id_rv_facility);
        id_gallery_recycler_view=(RecyclerView)findViewById(R.id.id_gallery_recycler_view);
        id_rv_certficate=(RecyclerView)findViewById(R.id.id_rv_certficate);
        id_below_location=(TextView)findViewById(R.id.id_below_location);
        id_website=(TextView)findViewById(R.id.id_website);
        id_mail=(TextView)findViewById(R.id.id_mail);
        id_afflictiobby=(TextView)findViewById(R.id.id_afflictiobby);




        id_download_broucher=(LinearLayout)findViewById(R.id.id_download_broucher);
        id_download_broucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    call_Download(jsonObject.getString("brochure"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        verticleLayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
        id_rv_facility.setLayoutManager(verticleLayoutManager);
        verticleLayoutManager2 = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
        id_gallery_recycler_view.setLayoutManager(verticleLayoutManager2);
        verticleLayoutManager3 = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
        id_rv_certficate.setLayoutManager(verticleLayoutManager3);

        try {
            jsonObject=new JSONObject(getIntent().getStringExtra("JSON_OBJ"));

            new New_Asynch_MAp(new New_Asynch_MAp.JOBJ_LISTENER() {
                @Override
                public void on_listener(JSONObject jsonobject, String str_key) {
                    try {
                    Log.d("fulldta",""+jsonobject);
                        id_rv_certficate.setAdapter(new Gallery_Dapter_school_json(School_Full_Detail_Activity.this,jsonobject.getJSONArray("certificate")));
                        id_collge_banner.setAdapter(new Banner_Adapter_JSon(School_Full_Detail_Activity.this,jsonobject.getJSONArray("banner"),"banner"));
                        id_rv_facility.setAdapter(new Facility_Adapter_JSon(School_Full_Detail_Activity.this,jsonobject.getJSONArray("facility")));
                        id_gallery_recycler_view.setAdapter(new Gallery_Dapter_school_json(School_Full_Detail_Activity.this,jsonobject.getJSONArray("gallery")));

                        get_detail_school(jsonobject.getJSONArray(str_key).getJSONObject(0));


                    } catch (Exception e) {

                        Toast.makeText(School_Full_Detail_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("gdgdgdgd",""+e.getMessage());
                    }
                }
            }, App_Static_Method.toMap(jsonObject),SCHOOL_AVAIL_CLASS_FULL,"data").execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void get_detail_school(JSONObject jsonObject) {

        try {
            if(jsonObject.has("c_name"))
            id_name_tv.setText(jsonObject.getString("c_name"));

            if(jsonObject.has("class_name"))
            id_course.setText(jsonObject.getString("class_name"));
          //  duration.append(jsonObject.getString("class_name"));

            id_fees1.append(jsonObject.getString("fee")+"Rs / Year ");
            id_course_desc.setText(jsonObject.getString("c_info"));
            id_college_desc.setText(jsonObject.getString("school_info"));
            id_below_location.setText(jsonObject.getString("c_address"));
            id_website.setText(jsonObject.getString("c_website"));
            id_mail.setText(jsonObject.getString("c_email"));
            id_afflictiobby.setText(jsonObject.getString("affiliation"));


        } catch (Exception e) {
            Toast.makeText(School_Full_Detail_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
           Log.d("nnmnm",""+e.getMessage());
        }

    }

    private void call_Download(String brouchure_doewnload) {

        //   String brouchure_doewnload=BROUCHURE_DOWNLOAD+str_type+"&brochure="+str_brouchuer;
      //  String brouchure_doewnload="http://activeeduindia.com/admin/webservices/downloadBrochure.php?type=1&brochure="+str_brouchuer;

        if (brouchure_doewnload!=null) {
            if (isSubstring("http://", brouchure_doewnload)) {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(School_Full_Detail_Activity.this, Website_Activity.class);
                intent.putExtra("Url_Web", brouchure_doewnload);
                startActivity(intent);
            } else {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(School_Full_Detail_Activity.this, Website_Activity.class);
                intent.putExtra("Url_Web", "http://" + brouchure_doewnload);
                startActivity(intent);
            }
        }else {

            Toast.makeText(School_Full_Detail_Activity.this, "Link Not Found !!!!", Toast.LENGTH_SHORT).show();

        }

    }
    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

        }

    }


}
