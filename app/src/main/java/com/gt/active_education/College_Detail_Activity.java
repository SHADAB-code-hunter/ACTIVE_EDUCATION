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

import java.util.List;

import adapter.Common_pojo_adapter;
import adapter.Gallery_demo_adapter;
import callbacks.CLG_DESC_Listener;
import callbacks.Choose_newCourses_Listener;
import callbacks.Upcoming_List_LoadedListener;
import pojo.Cat_Model;
import pojo.Get_Course_desc;
import task.Load_Async_Course_desc;
import task.Load_College_Desc_Async;
import task.Load_Courses_Data;
import utilities.Common_Pojo;
import utilities.UrlEndpoints;

/**
 * Created by GT on 7/25/2017.
 */

public class College_Detail_Activity extends AppCompatActivity implements View.OnClickListener
, Choose_newCourses_Listener, CLG_DESC_Listener , Upcoming_List_LoadedListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        id_recycler_view=(RecyclerView)findViewById(R.id.id_recycler_view);
        id_gallery_recycler_view=(RecyclerView)findViewById(R.id.id_gallery_recycler_view);
        id_frm_back=(FrameLayout)findViewById(R.id.id_frm_back);id_frm_back.setOnClickListener(this);
        id_branch_name=(TextView)findViewById(R.id.id_branch_name);
        id_corse_url=(TextView)findViewById(R.id.id_corse_url);
        id_college_desc_url=(TextView)findViewById(R.id.id_college_desc_url);
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
        }
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
        Log.d("collegeid_",""+str_clg_id);
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
        GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),1,
                LinearLayoutManager.HORIZONTAL, false);
        id_gallery_recycler_view.setLayoutManager(horizontal_LayoutManager);
        id_gallery_recycler_view.setItemAnimator(new DefaultItemAnimator());
        id_gallery_recycler_view.setAdapter(galleryDemoAdapter);

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
        }
    }


    @Override
    public void on_choose_courses(List<Get_Course_desc> listMovies, String status_call) {
             if (listMovies.isEmpty())
                 return;

            id_course.setText(listMovies.get(0).getBname());
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
        Log.d("sgfrt",common_pojos.toString());
        Common_pojo_adapter mAdapter = new Common_pojo_adapter(College_Detail_Activity.this,common_pojos);
        GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),4,
                LinearLayoutManager.VERTICAL, false);
        id_recycler_view.setLayoutManager(horizontal_LayoutManager);
        id_recycler_view.setItemAnimator(new DefaultItemAnimator());
        id_recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onUpcomingexams(List<Common_Pojo> common_pojos) {

    }
}
