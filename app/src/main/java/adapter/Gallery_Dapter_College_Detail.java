package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.gt.active_education.College_Detail_Activity;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import callbacks.VP_PageChange_Listener;
import extras.Constants;
import network.VolleySingleton;
import pojo.Gallery_Model;
import utilities.Common_Pojo;
import utilities.UrlEndpoints;

/**
 * Created by GT on 10/31/2017.
 */

public class Gallery_Dapter_College_Detail extends RecyclerView.Adapter<Gallery_Dapter_College_Detail.MyViewHolder> {


    public int i;
    Activity college_detail_activity;
    JSONArray json_gallery;
    private String halg_img_path;
    private List<Common_Pojo> gallery_lis;

    public Gallery_Dapter_College_Detail(College_Detail_Activity college_detail_activity, JSONArray json_gallery, String halg_img_path) {
        this.college_detail_activity=college_detail_activity;
        this.json_gallery=json_gallery;
        this.halg_img_path=halg_img_path;
    }

    public Gallery_Dapter_College_Detail(Activity college_detail_activity, List<Common_Pojo> gallery_lis, String halg_img_path) {
        this.college_detail_activity=college_detail_activity;
        this.gallery_lis=gallery_lis;
        this.halg_img_path=halg_img_path;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView id_img_glr;
        //  private VideoView id_vd_glr;


        public MyViewHolder(View view) {
            super(view);
            id_img_glr=(ImageView) view.findViewById(R.id.id_img_glr);
            // id_vd_glr=(VideoView)view.findViewById(R.id.id_vd_glr);
        }
    }
    @Override
    public int getItemCount() {

        return gallery_lis.size();
    }


    @Override
    public Gallery_Dapter_College_Detail.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallery_college, parent, false);

        return new Gallery_Dapter_College_Detail.MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(Gallery_Dapter_College_Detail.MyViewHolder holder, final int position) {

        Glide.with(college_detail_activity).load(halg_img_path+"/picture/"+gallery_lis.get(position).getName()).into(holder.id_img_glr);
        holder.id_img_glr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // (vpPageChangeListener).on_opt_next_Ques(quiz_Ans_List,position);
            }
        });

    }


}
