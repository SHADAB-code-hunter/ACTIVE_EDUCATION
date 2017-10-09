package Fab_Filter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.gt.active_education.College_Detail_Activity;
import com.gt.active_education.Filter_Activity;
import com.gt.active_education.R;
import com.gt.active_education.Website_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pojo.Cat_Model;
import utilities.UrlEndpoints;

/**
 * Created by krupenghetiya on 27/06/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    List<Cat_Model> mList = new ArrayList<>();
    Picasso picasso;
    Activity _activity;
    String str_url,str_img_path;

    public MoviesAdapter(List<Cat_Model> list_urls, Picasso p, Activity a) {
        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
    }
    public MoviesAdapter(List<Cat_Model> list_urls, Picasso p, Activity a,String url) {
        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
        this.str_url=url;
        Log.d("sshhsh",str_url);
    }

    public MoviesAdapter(List<Cat_Model> list_urls, Picasso p, Activity a,String url, String str_img_path) {

        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
        this.str_url=url;
        this.str_img_path=str_img_path;
        Log.d("sshhsh",str_url);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position == 0){
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }else {
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),0,(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }
        holder.card_view.setLayoutParams(layoutParams);
        Log.d("hjdh",""+mList.get(position).getC_image());
        Log.d("urlimgpath",UrlEndpoints.URL_IMAGE_APTH+mList.get(position).getC_image());

        if (str_img_path!=null)
        {
            Log.d("imgpath",""+str_img_path+mList.get(position).getC_image());
            picasso.load(str_img_path+mList.get(position).getC_image())
                    .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);
        }

        holder.tv_title.setText(mList.get(position).getC_name());
        holder.tv_genre.setText("Location : " +mList.get(position).getC_city()+", "+mList.get(position).getC_state());
        holder.tv_rating.setText("Avg Fees: " + mList.get(position).getBranch_fees()+"/ year");
      //  holder.tv_year.setText("Year: " + mList.get(position).getYear());
     //   holder.tv_quality.setText("Quality: " +mList.get(position).getQuality());

       // holder.card_view.setTag(position);
        holder.tv_quality.setText("Course: "+ mList.get(position).getC_course());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoviesAdapter.this.set_applynow(v, position);
            }
        });
        holder.id_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_applynow(v, position);
            }
        });
        holder.img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    String dial = "tel:" + mList.get(position).getC_phone1();
                    _activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(_activity, "Permission Phone Call denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
      //  img_course,img_call,img_broucher
        holder.img_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_website(mList.get(position).getC_website());
            }
        });

        if (isSubstring("getTopList", str_url))
        {
            holder.is_discout_linear.setVisibility(View.VISIBLE);
            holder.id_discout_text.setText("10" );//mList.get(position).getDiscount_fee()

        }


    }
    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(_activity, permission) == PackageManager.PERMISSION_GRANTED;
    }
    private void call_website(String c_website) {

        if (c_website!=null) {
            if (isSubstring("http://", c_website)) {
                Log.d("sswess", "" + c_website);
                Intent intent = new Intent(_activity, Website_Activity.class);
                intent.putExtra("Url_Web", c_website);
                _activity.startActivity(intent);
            } else {
                Log.d("sswess", "" + c_website);
                Intent intent = new Intent(_activity, Website_Activity.class);
                intent.putExtra("Url_Web", "http://" + c_website);
                _activity.startActivity(intent);
            }
        }else {

            Toast.makeText(_activity, "Link Not Found !!!!", Toast.LENGTH_SHORT).show();

        }

    }

    private void set_applynow(View v, int position) {
        Log.d("kdddd9","clicked"+
                ""+mList.get(position).getC_id()
        +" "+mList.get(position).getCourse_id()
        +" "+mList.get(position).getBranch_id());
        String pos= (String) v.getTag();
        Intent i=new Intent(_activity.getApplicationContext(),College_Detail_Activity.class);

        Bundle bundle = new Bundle();
        bundle.putString("clg_id",""+mList.get(position).getC_id());
        bundle.putString("course",""+mList.get(position).getCourse_id());
        bundle.putString("branch",""+mList.get(position).getBranch_id());
        bundle.putString("branch_name",""+mList.get(position).getC_branch());
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
        private ImageView iv_cover,img_web,img_course,img_call,img_broucher;
        private TextView tv_title,id_discout_text;
        private TextView tv_genre;
        private TextView tv_rating;
        private TextView tv_year;
        private TextView tv_quality;
        private CardView card_view;
        private Button id_btn_apply;
        private LinearLayout is_discout_linear;

        public MovieViewHolder(View x) {
            super(x);
            iv_cover = (ImageView) x.findViewById(R.id.iv_cover);
            tv_title = (TextView) x.findViewById(R.id.tv_title);
            tv_genre = (TextView) x.findViewById(R.id.tv_genre);
            tv_rating = (TextView) x.findViewById(R.id.tv_rating);
            tv_year = (TextView) x.findViewById(R.id.tv_year);
            tv_quality = (TextView) x.findViewById(R.id.tv_quality);
            id_discout_text = (TextView) x.findViewById(R.id.id_discout_text);
            card_view = (CardView) x.findViewById(R.id.card_view);
            id_btn_apply=(Button)x.findViewById(R.id.id_btn_apply);

            img_web=(ImageView)x.findViewById(R.id.img_web);
            img_course=(ImageView)x.findViewById(R.id.img_course);
            img_call=(ImageView)x.findViewById(R.id.img_call);
            img_broucher=(ImageView)x.findViewById(R.id.img_broucher);
            is_discout_linear=(LinearLayout) x.findViewById(R.id.is_discout_linear);

        }

    }

    public void clearApplications() {
        int size = this.mList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }

}
