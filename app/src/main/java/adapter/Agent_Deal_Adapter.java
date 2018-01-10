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

import com.gt.active_education.College_Detail_Activity;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pojo.Agent_Deal_Pojo;
import pojo.Agent_Deal_Pojo;
import utilities.UrlEndpoints;

import static utilities.UrlEndpoints.IMAGE_PATH_ADAPTER;

/**
 * Created by GT on 8/28/2017.
 */

public class Agent_Deal_Adapter extends RecyclerView.Adapter<Agent_Deal_Adapter.MovieViewHolder> {

    List<Agent_Deal_Pojo> mList = new ArrayList<>();
    Picasso picasso;
    Activity _activity;
    private String[] str_cat_arr;
    String str_url,str_img_path, str_type="NA";

    public Agent_Deal_Adapter(List<Agent_Deal_Pojo> list_urls, Picasso p, Activity a) {
        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
        this.str_type=list_urls.get(0).getCat_id();
    }


    @Override
    public Agent_Deal_Adapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agent_deal_adapter, parent, false);
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
        holder.tv_title.setText(mList.get(position).getClg_name());

        holder.id_discout_text.setText(mList.get(position).getDis_offer()+"%");
        holder.tv_quality.setText("Course: " +mList.get(position).getCourse_name());
        holder.tv_rating.setText("Avg Fees: " +mList.get(position).getBranch_fee()+"/ year");
        //   ==============================================================
        Log.d("dgdggd", "" + str_url + "   " + str_type);

        String halg_img_path = IMAGE_PATH_ADAPTER;
        str_cat_arr = _activity.getResources().getStringArray(R.array.category_image);
       // if (str_url != null) {
           /* if ( !isSubstring("getSearchResult.php",str_url)) {
                str_type = str_url.split("=")[1];
            }else {
                str_type = mList.get(position).getCat_id();
            }*/
            switch (str_type) {
                case "1":
                    halg_img_path = halg_img_path + str_cat_arr[0];
                    break;

                case "2":
                    halg_img_path = halg_img_path + str_cat_arr[1];

                    break;
                case "3":
                    halg_img_path = halg_img_path + str_cat_arr[2];

                    break;
                case "4":
                    halg_img_path = halg_img_path + str_cat_arr[3];
                    break;
                case "5":
                    halg_img_path = halg_img_path + str_cat_arr[4];
                    break;
                case "6":
                    halg_img_path = halg_img_path + str_cat_arr[5];
                    break;
            }
       // }

        Log.d("djgjhgjgdj",halg_img_path);

        if(!mList.get(position).getImage().equals("picture.png")) {
            if (halg_img_path != null) {
                Log.d("imgpath", "" + halg_img_path+"/picture/" + mList.get(position).getImage());
                picasso.load(halg_img_path+"/picture/" + mList.get(position).getImage())
                        .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);
            }
        }else {

            Log.d("noimage",halg_img_path);
            if(!mList.get(position).getImage().equals(null)){
                Log.d("imgpddath", "" + halg_img_path+"/picture/" + mList.get(position).getImage());
                picasso.load(halg_img_path+"/picture/"  + mList.get(position).getImage())
                        .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);}
            else {

            }
        }
        holder.id_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  set_applynow(v, position);
            }
        });
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  set_applynow(v, position);
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
        private TextView id_discout_text;
        private CardView card_view;
        private Button id_btn_apply;

        public MovieViewHolder(View x) {
            super(x);
            iv_cover = (ImageView) x.findViewById(R.id.iv_cover);
            tv_title = (TextView) x.findViewById(R.id.tv_title);
            tv_genre = (TextView) x.findViewById(R.id.tv_genre);
            tv_rating = (TextView) x.findViewById(R.id.tv_rating);
            tv_year = (TextView) x.findViewById(R.id.tv_year);
            id_discout_text = (TextView) x.findViewById(R.id.id_discout_text);
            tv_quality = (TextView) x.findViewById(R.id.tv_quality);
            card_view = (CardView) x.findViewById(R.id.card_view);
            id_btn_apply=(Button)x.findViewById(R.id.id_btn_apply);
        }

    }
    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }

}
