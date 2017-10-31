package adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gt.active_education.Agent_Deal_Offer_Detail_Activity;
import com.gt.active_education.College_Detail_Activity;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pojo.Agent_Deal_Pojo;
import pojo.Agent_Deal_Pojo;
import utilities.UrlEndpoints;

/**
 * Created by GT on 8/28/2017.
 */

public class Agent_Deal_Adapter extends RecyclerView.Adapter<Agent_Deal_Adapter.MovieViewHolder> {

    List<Agent_Deal_Pojo> mList = new ArrayList<>();
    Picasso picasso;
    Activity _activity;

    public Agent_Deal_Adapter(List<Agent_Deal_Pojo> list_urls, Picasso p, Activity a) {
        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
    }


    @Override
    public Agent_Deal_Adapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_movie, parent, false);
        return new Agent_Deal_Adapter.MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Agent_Deal_Adapter.MovieViewHolder holder, final int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position == 0){
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }else {
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),0,(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }
        holder.card_view.setLayoutParams(layoutParams);
     /*   Log.d("hjdh",""+mList.get(position).getC_image());
        Log.d("urlimgpath", UrlEndpoints.URL_IMAGE_APTH+mList.get(position).getC_image());
        picasso.load(UrlEndpoints.URL_IMAGE_APTH+mList.get(position).getC_image()).placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);
        holder.tv_title.setText(mList.get(position).getC_name());
        holder.tv_genre.setText("Location : " +mList.get(position).getC_city()+", "+mList.get(position).getC_state());
        holder.tv_rating.setText("Avg Fees: " + mList.get(position).getBranch_fees()+"/ year");*/
        //  holder.tv_year.setText("Year: " + mList.get(position).getYear());
        //   holder.tv_quality.setText("Quality: " +mList.get(position).getQuality());

        // holder.card_view.setTag(position);
        //   holder.tv_quality.setText("Course: "+ mList.get(position).getC_());
       /* holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d("k9","clicked");
              //  String pos= (String) v.getTag();
                Intent i=new Intent(_activity.getApplicationContext(),Agent_Deal_Offer_Detail_Activity.class);
                Log.d("porfdd",""+mList.get(position).getClg_id()+"   "+
                       mList.get(position).getCourse_id()+" "+
                       mList.get(position).getBranch_id());
               *//* form_map.put("clgid","lmcp");
                form_map.put("course","1");
                form_map.put("branch","1");*//*

                Bundle bundle = new Bundle();
                bundle.putString("clg_id",""+mList.get(position).getClg_id());
                bundle.putString("course",""+mList.get(position).getCourse_id());
                bundle.putString("branch",""+mList.get(position).getBranch_id());
                i.putExtras(bundle);
               *//* i.putExtra("clg_id",""+mList.get(position).getClg_id());
                i.putExtra("course",""+mList.get(position).getCourse_id());
                i.putExtra("branch",""+mList.get(position).getBranch_id());*//*
                _activity.startActivity(i);
            }
        });*/
/*
        holder.id_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("k9","clicked");
                String pos= (String) v.getTag();
                Intent i=new Intent(_activity.getApplicationContext(),College_Detail_Activity.class);
                Log.d("porfdd",""+mList.get(position).getC_id());
                i.putExtra("course_id",mList.get(position).getCourse_id());
                i.putExtra("clg_id",mList.get(position).getC_id());
                i.putExtra("brn_id",mList.get(position).getBranch_id());
                _activity.startActivity(i);
            }
        });
*/
        holder.id_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_applynow(v, position);
            }
        });
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_applynow(v, position);
            }
        });
    }
    private void set_applynow(View v, int position) {
        Log.d("kdddd9","clicked"+
                ""+mList.get(position).getClg_id()
                +" "+mList.get(position).getCourse_id()
                +" "+mList.get(position).getBranch_id());
        String pos= (String) v.getTag();
        Intent i=new Intent(_activity.getApplicationContext(),College_Detail_Activity.class);

        Bundle bundle = new Bundle();
        bundle.putString("clg_id",""+mList.get(position).getClg_id());
        bundle.putString("course",""+mList.get(position).getCourse_id());
        bundle.putString("branch",""+mList.get(position).getBranch_id());
        bundle.putString("branch_name","Dummy Branch");
        i.putExtras(bundle);

        if(!(mList.get(position).getCategory()).equals("NA"))// for
        {
            Log.d("djeejd","dhdh"+(mList.get(position).getCategory()));
            i.putExtra("type",""+(mList.get(position).getCategory()));
        }
        else
        {
            i.putExtra("type",""+(position+1));
        }
        _activity.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_cover;
        private TextView tv_title;
        private TextView tv_genre;
        private TextView tv_rating;
        private TextView tv_year;
        private TextView tv_quality;
        private CardView card_view;
        private Button id_btn_apply;

        public MovieViewHolder(View x) {
            super(x);
            iv_cover = (ImageView) x.findViewById(R.id.iv_cover);
            tv_title = (TextView) x.findViewById(R.id.tv_title);
            tv_genre = (TextView) x.findViewById(R.id.tv_genre);
            tv_rating = (TextView) x.findViewById(R.id.tv_rating);
            tv_year = (TextView) x.findViewById(R.id.tv_year);
            tv_quality = (TextView) x.findViewById(R.id.tv_quality);
            card_view = (CardView) x.findViewById(R.id.card_view);
            id_btn_apply=(Button)x.findViewById(R.id.id_btn_apply);
        }

    }

}
