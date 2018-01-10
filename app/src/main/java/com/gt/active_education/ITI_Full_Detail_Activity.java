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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.Banner_Adapter_JSon;
import adapter.Facility_Adapter_JSon;
import adapter.Gallery_Dapter_school_json;
import task.New_Asynch_MAp;
import utilities.App_Static_Method;
import utilities.Availschool_class;
import utilities.RecyclerTouchListener;

import static utilities.UpdateValues.ADDMISSION;
import static utilities.UrlEndpoints.MAIN_URL_ITI_FUL_DETAIL;
import static utilities.UrlEndpoints.SCHOOL_AVAIL_CLASS_FULL;
import static utilities.UrlEndpoints.SCHOOL_TOP_AVAIL_CLASS_FULL;

/**
 * Created by GT on 12/5/2017.
 */

public class ITI_Full_Detail_Activity extends AppCompatActivity implements View.OnClickListener {


    private ViewPager id_collge_banner;
    private TextView id_name_tv,id_course,duration,id_fees1,id_course_desc,id_college_desc,_corse_text,id_trdedescv;
    private RecyclerView id_rv_facility;
    private GridLayoutManager verticleLayoutManager,verticleLayoutManager2;
    private JSONObject jsonObject;
    private LinearLayout id_download_broucher;
    private RecyclerView id_gallery_recycler_view;
    private GridLayoutManager verticleLayoutManager3;
    private RecyclerView id_rv_certficate;
    private TextView id_below_location,id_website,id_mail,id_afflictiobby;
    private Button id_btn_apply;
    private LinearLayout id_linear_change_course;
    private Availschool_class availschool_class;
    private JSONObject jsonObject2;
    private JSONObject jsonObject3;
    private Banner_Adapter_JSon banner_adapter_jSon;
    private Gallery_Dapter_school_json certifiacte_adapter;
    private JSONArray jsonArray;
    private Intent i;
    private TextView id_clg_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);

        try {
            jsonObject3=new JSONObject(getIntent().getStringExtra("JSON_OBJ"));
            Log.d("detailfulldata",""+jsonObject3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        id_collge_banner=(ViewPager)findViewById(R.id.id_collge_banner);
        id_name_tv=(TextView)findViewById(R.id.id_name_tv);
        id_course=(TextView)findViewById(R.id.id_course);
        id_fees1=(TextView)findViewById(R.id.id_fees1);
        duration=(TextView)findViewById(R.id.d1);
        id_course_desc=(TextView)findViewById(R.id.id_course_desc);
        _corse_text=(TextView)findViewById(R.id._corse_text);_corse_text.setText("ITI Trade Name");
        id_trdedescv=(TextView)findViewById(R.id.id_trdedescv);id_trdedescv.setText("Trade Description");
        id_clg_desc=(TextView)findViewById(R.id.id_clg_desc);id_clg_desc.setText("ITI College Description");
        id_college_desc=(TextView)findViewById(R.id.id_college_desc);
        id_rv_facility=(RecyclerView)findViewById(R.id.id_rv_facility);
        id_gallery_recycler_view=(RecyclerView)findViewById(R.id.id_gallery_recycler_view);
        id_rv_certficate=(RecyclerView)findViewById(R.id.id_rv_certficate);
        id_below_location=(TextView)findViewById(R.id.id_below_location);
        id_website=(TextView)findViewById(R.id.id_website);
        id_mail=(TextView)findViewById(R.id.id_mail);
        id_afflictiobby=(TextView)findViewById(R.id.id_afflictiobby);


        id_btn_apply=(Button)findViewById(R.id.id_btn_apply);id_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admission_Form_Activity.class);
                intent.putExtra(ADDMISSION,""+jsonObject3);
                startActivity(intent);
            }
        });

        id_download_broucher=(LinearLayout)findViewById(R.id.id_download_broucher);
        id_download_broucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    call_Download(jsonObject3.getString("brochure"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        id_linear_change_course=(LinearLayout)findViewById(R.id.id_linear_change_course);
        id_linear_change_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    availschool_class=new Availschool_class(new Availschool_class.FINAL_OBJ_LISTNER() {
                        @Override
                        public void onfinal_list(JSONObject data, String choose_class) {
                            try {
                                //availschool_class.cancel();
                                Log.d("nm_nm",""+data);
                                if(availschool_class!=null)
                                    availschool_class.cancel();
                                JSONObject jsonObject=  ITI_Full_Detail_Activity.this.jsonObject3.put("course_id",""+data.getString("id"));
                                get_deatil_asynch(jsonObject);
                           //   Toast.makeText(School_Listing_Activity.this, "Choose Courses :  "+data.getString("name"), Toast.LENGTH_SHORT).show();
                               /* Intent  i = new Intent(getApplicationContext(), School_Full_Detail_Activity.class);
                                i.putExtra("JSON_OBJ", "" + data);
                                i.putExtra("JSON_OBJ", "" + App_Static_Method.toMERGE_JSON(data,jsonObject));
                                startActivity(i);
                                */
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    },ITI_Full_Detail_Activity.this,SCHOOL_TOP_AVAIL_CLASS_FULL+ jsonObject3.getString("c_id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                availschool_class.show();

            }
        });
        verticleLayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
        id_rv_facility.setLayoutManager(verticleLayoutManager);
        verticleLayoutManager2 = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
        id_gallery_recycler_view.setLayoutManager(verticleLayoutManager2);
        verticleLayoutManager3 = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
        id_rv_certficate.setLayoutManager(verticleLayoutManager3);

        get_deatil_asynch(jsonObject3);

        id_rv_certficate.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),id_rv_certficate,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        try {
                        JSONObject jsonObject=new JSONObject();
                        i=new Intent(getApplicationContext(),Open_Image_Activity.class);
                        jsonObject.put("IMAGE_ARRAY",certifiacte_adapter.getJSONARRAY());
                        i.putExtra("IMAGE_ARRAY",""+jsonObject);
                        i.putExtra("click_page",""+position);
                        startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));
    }

    private void get_deatil_asynch(JSONObject jsonObject3) {
        try {
            // getting full detail api
            Log.d("ful_updateldta",""+jsonObject3);
            new New_Asynch_MAp(new New_Asynch_MAp.JOBJ_LISTENER() {
                @Override
                public void on_listener(JSONObject jsonobject, String str_key) {
                    try {
                        Log.d("fulldta",""+jsonobject); //getJSONARRAY
                        certifiacte_adapter=new Gallery_Dapter_school_json(ITI_Full_Detail_Activity.this,jsonobject.getJSONArray("certificate"));
                        id_rv_certficate.setAdapter(certifiacte_adapter);
                        banner_adapter_jSon=new Banner_Adapter_JSon(ITI_Full_Detail_Activity.this,jsonobject.getJSONArray("banner"),"banner");
                        id_collge_banner.setAdapter(banner_adapter_jSon);
                        id_rv_facility.setAdapter(new Facility_Adapter_JSon(ITI_Full_Detail_Activity.this,jsonobject.getJSONArray("facility")));
                        id_gallery_recycler_view.setAdapter(new Gallery_Dapter_school_json(ITI_Full_Detail_Activity.this,jsonobject.getJSONArray("gallery")));

                        get_detail_school(jsonobject.getJSONArray(str_key).getJSONObject(0));

                    } catch (Exception e) {
                        //   Toast.makeText(School_Full_Detail_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("gdgdgdgd",""+e.getMessage());
                    }
                }
            }, App_Static_Method.toMap(jsonObject3),MAIN_URL_ITI_FUL_DETAIL,"data").execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void get_detail_school(JSONObject jsonObject) {

        try {
            Log.d("coll_ege",""+jsonObject);
            if(jsonObject.has("c_name"))
            id_name_tv.setText(jsonObject.getString("c_name"));

            if(jsonObject.has("t_name"))
            id_course.setText(jsonObject.getString("t_name"));
          //  duration.append(jsonObject.getString("class_name"));

            id_fees1.setText("Fee : "+jsonObject.getString("fee")+"Rs / Year ");
            id_course_desc.setText(jsonObject.getString("t_info"));
            id_afflictiobby.setText(jsonObject.getString("affiliation"));
            id_college_desc.setText(jsonObject.getString("iti_info"));
            id_below_location.setText(jsonObject.getString("c_address"));
            id_website.setText(jsonObject.getString("c_website"));
            id_mail.setText(jsonObject.getString("c_email"));



        } catch (Exception e) {
         //   Toast.makeText(School_Full_Detail_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
           Log.d("nnmnm",""+e.getMessage());
        }

    }

    private void call_Download(String brouchure_doewnload) {

        //   String brouchure_doewnload=BROUCHURE_DOWNLOAD+str_type+"&brochure="+str_brouchuer;
      //  String brouchure_doewnload="http://activeeduindia.com/admin/webservices/downloadBrochure.php?type=1&brochure="+str_brouchuer;

        if (brouchure_doewnload!=null) {
            if (isSubstring("http://", brouchure_doewnload)) {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(ITI_Full_Detail_Activity.this, Website_Activity.class);
                intent.putExtra("Url_Web", brouchure_doewnload);
                startActivity(intent);
            } else {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(ITI_Full_Detail_Activity.this, Website_Activity.class);
                intent.putExtra("Url_Web", "http://" + brouchure_doewnload);
                startActivity(intent);
            }
        }else {

            Toast.makeText(ITI_Full_Detail_Activity.this, "Link Not Found !!!!", Toast.LENGTH_SHORT).show();

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
