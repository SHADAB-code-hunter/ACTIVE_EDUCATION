package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gt.active_education.R;

import java.util.List;

import callbacks.Call_Dilaog_Listener;
import utilities.Common_Pojo;

import static utilities.UrlEndpoints.URL_GET_FACILITY_IMG;


/**
 * Created by akashdeep on 22-Oct-17.
 */

public class Adapter_Facility extends RecyclerView.Adapter<Adapter_Facility.MyViewHolder> {

    private List<String> list_item ;
    public Call_Dilaog_Listener listener;
    Context baseContext;
    int[] img_array;
    String[] tv_array;
    List<Common_Pojo> facility_list;


    public Adapter_Facility(Context baseContext, List<Common_Pojo> facility_list) {
        this.baseContext=baseContext;
        this.facility_list=facility_list;
        Log.d("djkhdjc","cjhdj");
    }

    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public Adapter_Facility.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adp_city, null);

        Adapter_Facility.MyViewHolder myViewHolder = new Adapter_Facility.MyViewHolder(view);
        return myViewHolder;
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final Adapter_Facility.MyViewHolder viewHolder, final int position ) {
      
       viewHolder.id_tv.setText(facility_list.get(position).getName());
        Log.d("ig_link",URL_GET_FACILITY_IMG+facility_list.get(position).getDesc());
        Glide.with(baseContext)
                .load(URL_GET_FACILITY_IMG+facility_list.get(position).getDesc())
                .into( viewHolder.img_view);
    }

    // initializes some private fields to be used by RecyclerView.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img_view;
        private TextView country_name,id_frst_letter,id_tv;
        private FrameLayout id_frame_Layout;
        private RelativeLayout id_rlative;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            img_view = (ImageView) itemLayoutView.findViewById(R.id.img_pfl_pic);
            country_name = (TextView) itemLayoutView.findViewById(R.id.country_name);
            id_tv = (TextView) itemLayoutView.findViewById(R.id.id_tv);
            id_frst_letter=(TextView)itemLayoutView.findViewById(R.id.id_frst_letter);
            id_frame_Layout=(FrameLayout)itemLayoutView.findViewById(R.id.id_frame_Layout);
            id_rlative=(RelativeLayout)itemLayoutView.findViewById(R.id.id_rlative);

        }
    }

    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {

        return facility_list.size();
    }


}
