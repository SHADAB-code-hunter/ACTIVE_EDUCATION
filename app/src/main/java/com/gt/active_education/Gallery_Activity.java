package com.gt.active_education;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.Gallery_Adapter;
import callbacks.Gallery_list_listener;
import callbacks.VP_PageChange_Listener;
import fragment.Partner_Gallery;
import pojo.Gallery_Model;
import utilities.Get_Gellary_List;
import utilities.UrlEndpoints;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by GT on 8/17/2017.
 */

public class Gallery_Activity  extends AppCompatActivity implements  View.OnClickListener, VP_PageChange_Listener {

    private GridLayoutManager verticleLayoutManager;
    private RecyclerView gallery_recycler_view;
    private TextView id_back_btn_quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gallery_recycler_view=(RecyclerView)findViewById(R.id.gallery_recycler_view);
        //Get_Gellary_List.get_galllery_list(Gallery_Activity.this, UrlEndpoints.URL_GET_GALLERY);

        new Get_Gellary_List(new Get_Gellary_List.Gallery_list_listener() {
            @Override
            public void onListLoaded(ArrayList<Gallery_Model> listgallery) {
                Log.d("sgetr",listgallery.toString());
                verticleLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
                gallery_recycler_view.setLayoutManager(verticleLayoutManager);
                Gallery_Adapter galleryAdapter=new Gallery_Adapter(Gallery_Activity.this.getBaseContext(),(VP_PageChange_Listener)(Gallery_Activity.this),listgallery);
                gallery_recycler_view.setAdapter(galleryAdapter);
            }
        },Gallery_Activity.this.getBaseContext(),UrlEndpoints.URL_GET_GALLERY);
        id_back_btn_quiz=(TextView)findViewById(R.id.id_back_btn_quiz);id_back_btn_quiz.setOnClickListener(this);
    }

   /* @Override
    public void onListLoaded(ArrayList<Gallery_Model> listgallery) {
        Log.d("sgetr",listgallery.toString());
        verticleLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        gallery_recycler_view.setLayoutManager(verticleLayoutManager);
        Gallery_Adapter galleryAdapter=new Gallery_Adapter(Gallery_Activity.this,listgallery);
        gallery_recycler_view.setAdapter(galleryAdapter);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_back_btn_quiz:
                finish();
                break;
        }
    }

    @Override
    public void on_opt_next_Ques() {

    }

    @Override
    public void on_opt_next_Ques(ArrayList<Gallery_Model> quiz_Ans_List, int poss) {

        //Log.d("popodp", String.valueOf(poss));
        Intent i = new Intent(getApplicationContext(), Big_ImgView_Activity.class);
        i.putParcelableArrayListExtra("list", quiz_Ans_List);
        i.putExtra("URL", UrlEndpoints.GALLERY_IMG_PATH );
        i.putExtra("position_click",""+poss);
        i.putExtra("click_page","Gallery");
        /*  i.putExtra("quiz_type","1");
        i.putExtra("quiz_time","0");*/
        startActivity(i);
        //  finish();
    }
}
