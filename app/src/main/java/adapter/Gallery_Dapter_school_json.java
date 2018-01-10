package adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gt.active_education.College_Detail_Activity;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import utilities.Common_Pojo;

/**
 * Created by GT on 10/31/2017.
 */

public class Gallery_Dapter_school_json extends RecyclerView.Adapter<Gallery_Dapter_school_json.MyViewHolder> {


    public int i;
    Activity college_detail_activity;
    JSONArray json_gallery;
    private String halg_img_path;
    private List<Common_Pojo> gallery_lis;
    JSONArray jsonArray;
    private JSONObject jsonObject;


    public Gallery_Dapter_school_json(Activity college_detail_activity,JSONArray jsonArray) {
        this.college_detail_activity=college_detail_activity;
        this.jsonArray=jsonArray;
        Log.d("bbc__cbnbn",""+jsonArray);
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

        return jsonArray.length();
    }
    public  JSONArray getJSONARRAY() {

        return jsonArray;
    }


    @Override
    public Gallery_Dapter_school_json.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallery_college, parent, false);

        return new Gallery_Dapter_school_json.MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(Gallery_Dapter_school_json.MyViewHolder holder, final int position) {


        try {
             jsonObject=jsonArray.getJSONObject(position);
          //  Toast.makeText(college_detail_activity, ""+jsonObject.getString("name"), Toast.LENGTH_SHORT).show();

            Glide.with(college_detail_activity).load(jsonObject.getString("name")).into(holder.id_img_glr);
            holder.id_img_glr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // (vpPageChangeListener).on_opt_next_Ques(quiz_Ans_List,position);
                }
            });
        } catch (Exception e) {
            Log.d("gdgdgdgd",""+e.getMessage());
        }
    }


}
