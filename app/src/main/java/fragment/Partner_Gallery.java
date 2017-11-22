package fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.gt.active_education.Big_ImgView_Activity;
import com.gt.active_education.Gallery_Activity;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adapter.Gallery_Adapter;
import callbacks.Gallery_list_listener;
import callbacks.VP_PageChange_Listener;
import pojo.Gallery_Model;
import utilities.App_Static_Method;
import utilities.Common_Pojo;
import utilities.Get_Gellary_List;
import utilities.UrlEndpoints;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by GT on 10/10/2017.
 */

public class Partner_Gallery extends Fragment implements View.OnClickListener, VP_PageChange_Listener {

    private GridLayoutManager verticleLayoutManager;
    private RecyclerView gallery_recycler_view;
    private TextView id_back_btn_quiz;
    private Picasso picasso;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_gallery, container, false);
        gallery_recycler_view=(RecyclerView)rootView.findViewById(R.id.gallery_recycler_view);
        rootView.findViewById(R.id.toolbar_daily_quiz).setVisibility(View.GONE);

        picasso = Picasso.with(Partner_Gallery.this.getContext());
       // Get_Gellary_List.get_galllery_list(Partner_Gallery.this.getContext(), UrlEndpoints.URL_GET_GALLERY);

        new Get_Gellary_List(new Get_Gellary_List.Gallery_list_listener() {
            @Override
            public void onListLoaded(ArrayList<Gallery_Model> listgallery) {
                verticleLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
                gallery_recycler_view.setLayoutManager(verticleLayoutManager);
                Gallery_Adapter galleryAdapter=new Gallery_Adapter(Partner_Gallery.this.getContext(),(VP_PageChange_Listener)(Partner_Gallery.this), listgallery,picasso);
                gallery_recycler_view.setAdapter(galleryAdapter);
            }

            @Override
            public void onList_Loaded(ArrayList<Common_Pojo> listgallery) {
                Log.d("sgetr",listgallery.toString());

            }
        },Partner_Gallery.this.getContext(),UrlEndpoints.URL_GET_GALLERY, App_Static_Method.session_type(),"Partner");

        id_back_btn_quiz=(TextView)rootView.findViewById(R.id.id_back_btn_quiz);id_back_btn_quiz.setOnClickListener(this);

        return rootView;
    }


  /*  @Override
    public void onListLoaded(ArrayList<Gallery_Model> listgallery) {
        Log.d("sgetr",listgallery.toString());
        verticleLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false);
        gallery_recycler_view.setLayoutManager(verticleLayoutManager);
        Gallery_Adapter galleryAdapter=new Gallery_Adapter(Partner_Gallery.this.getContext(),listgallery);
        gallery_recycler_view.setAdapter(galleryAdapter);
    }*/


    @Override
    public void on_opt_next_Ques() {

    }

    @Override
    public void on_opt_next_Ques(ArrayList<Gallery_Model> quiz_Ans_List, int poss) {

        //Log.d("popodp", String.valueOf(poss));
        Intent i = new Intent(getApplicationContext(), Big_ImgView_Activity.class);
        i.putParcelableArrayListExtra("list", quiz_Ans_List);
        i.putExtra("position_click",""+poss);
        i.putExtra("URL", UrlEndpoints.GALLERY_IMG_PATH );
        i.putExtra("click_page","Gallery");
        /*  i.putExtra("quiz_type","1");
        i.putExtra("quiz_time","0");*/
        startActivity(i);
        //  finish();
    }

    @Override
    public void onClick(View v) {

    }
}
