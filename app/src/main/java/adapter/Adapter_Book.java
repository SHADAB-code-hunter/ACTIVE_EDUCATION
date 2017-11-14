package adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.gt.active_education.Booking_Detail_Activity;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pojo.Agent_Deal_Pojo;

import static utilities.UrlEndpoints.IMAGE_PATH_ADAPTER;

/**
 * Created by GT on 11/6/2017.
 */

public class Adapter_Book extends RecyclerView.Adapter<Adapter_Book.MovieViewHolder> {

    Picasso picasso;
    Activity _activity;
    private String[] str_cat_arr;
    String str_url,str_img_path, str_type="NA";
    private JSONArray jsonArray;
    private JSONObject jsonObject;


    public Adapter_Book(JSONArray jsonArray,Picasso picasso, Activity _activity) {
        this.jsonArray=jsonArray;
        this._activity = _activity;
        this.picasso=picasso;
    }


    @Override
    public Adapter_Book.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_book, parent, false);
        return new Adapter_Book.MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Adapter_Book.MovieViewHolder holder, final int position) {
        try {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position == 0){
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }else {
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),0,(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }

         jsonObject=jsonArray.getJSONObject(position);

        holder.card_view.setLayoutParams(layoutParams);
        holder.tv_title.setText(jsonObject.getString("clg_name"));
       // holder.tv_genre.setText(jsonObject.getString("clg_name")); //

            if(jsonObject.getString("seat_status").equals("0")) {
                holder.tv_genre.setText("Booking :" +" Pending ");
            }else {
                holder.tv_genre.setText("Booking :" +" Confirm ");
            }

        holder.tv_quality.setText("Course: " +jsonObject.getString("course_name"));
      //  holder.tv_rating.setText("Avg Fees: " +mList.get(position).getBranch_fee()+"/ year");
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

        String str_type= null;

            str_type = jsonArray.getJSONObject(position).getString("category");

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

        if(!jsonObject.getString("image").equals("picture.png")) {
            if (halg_img_path != null) {
                Log.d("imgpath", "" + halg_img_path+"/picture/" + jsonObject.getString("image"));
                picasso.load(halg_img_path+"/picture/" + jsonObject.getString("image"))
                        .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);
            }
        }else {

            Log.d("noimage",halg_img_path);
            if(!jsonObject.getString("image").equals(null)){
                Log.d("imgpddath", "" + halg_img_path+"/picture/" + jsonObject.getString("image"));
                picasso.load(halg_img_path+"/picture/"  + jsonObject.getString("image"))
                        .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);}
            else {

            }
        }
        holder.id_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(_activity,Booking_Detail_Activity.class);
                i.putExtra("Booking_Object",jsonObject.toString());
                _activity.startActivity(i);
            }
        });
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(_activity,Booking_Detail_Activity.class);
                i.putExtra("Booking_Object",jsonObject.toString());
                _activity.startActivity(i);
            }
        });
        } catch (Exception e) {
            Log.d("Exception_json",e.getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return jsonArray.length();
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
