package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.gt.active_education.R;

import java.util.ArrayList;

import callbacks.VP_PageChange_Listener;
import extras.Constants;
import network.VolleySingleton;
import pojo.Gallery_Model;
import utilities.UrlEndpoints;

/**
 * Created by GT on 9/25/2017.
 */

public class Gallery_demo_adapter extends RecyclerView.Adapter<Gallery_demo_adapter.MyViewHolder> {

    public int i;
    private Context context;
    private ArrayList<Gallery_Model> quiz_Ans_List;

    private String str_type;
    private Gallery_Model answerQuizMdl;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private int img[];

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

        return img.length;
    }

    public Gallery_demo_adapter(Context context, int img[]) {
        this.img=img;
        this.context=context;
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_gallery, parent, false);

        return new MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.id_img_glr.setTag(position);
        holder.id_img_glr.setVisibility(View.VISIBLE);
        holder.id_img_glr.setImageResource(img[position]);

    }
 

}
