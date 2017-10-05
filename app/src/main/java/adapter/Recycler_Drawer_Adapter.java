package adapter;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.R;


import java.util.List;

import pojo.Drawer_Pojo;

/**
 * Created by GT on 9/7/2017.
 */

public class Recycler_Drawer_Adapter extends RecyclerView.Adapter<Recycler_Drawer_Adapter.SamplesViewHolder> {

    private int[] img_drwr;
    private String[] str_menu_name;
    private Activity activity;

    public Recycler_Drawer_Adapter(DashBoard_Activity dashBoard_activity, int[] img_drwr, String[] str_menu_name) {
        this.activity=dashBoard_activity;
        this.img_drwr = img_drwr;
        this.str_menu_name = str_menu_name;
    }

    @Override
    public Recycler_Drawer_Adapter.SamplesViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.single_menu, parent, false);


        return new Recycler_Drawer_Adapter.SamplesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Recycler_Drawer_Adapter.SamplesViewHolder viewHolder, final int position) {

        viewHolder.flt_btn.setImageResource(img_drwr[position]);
        viewHolder.tv_menu_name.setText(str_menu_name[position]);
    }


    @Override
    public int getItemCount() {
        Log.d("coungt","dhdhd");
        return str_menu_name.length;
    }


    public static class SamplesViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_menu_name;
        private ImageView flt_btn;

        public SamplesViewHolder(View rootView) {
            super(rootView);
            tv_menu_name=(TextView)rootView.findViewById(R.id.tv_menu_name);
            flt_btn=(ImageView) rootView.findViewById(R.id.flt_btn);
        }
    }
}
