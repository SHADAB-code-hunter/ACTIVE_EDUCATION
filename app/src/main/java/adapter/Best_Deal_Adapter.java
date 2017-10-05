package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pojo.Cat_Model;
import utilities.UrlEndpoints;

/**
 * Created by GT on 8/10/2017.
 */

public class Best_Deal_Adapter extends BaseAdapter{
    List<Cat_Model> mList = new ArrayList<>();
    Picasso picasso;
    Activity _activity;

    public Best_Deal_Adapter(List<Cat_Model> listMovies, Picasso picasso, Activity context) {

        this.mList = listMovies;
        this.picasso = picasso;
        this._activity = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     //   LayoutInflater li=getLay
        LayoutInflater inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.single_movie, parent, false);

        ImageView iv_cover = (ImageView) rowView.findViewById(R.id.iv_cover);
        TextView tv_title = (TextView) rowView.findViewById(R.id.tv_title);
        TextView tv_genre = (TextView) rowView.findViewById(R.id.tv_genre);
        TextView tv_rating = (TextView) rowView.findViewById(R.id.tv_rating);
        TextView tv_year = (TextView) rowView.findViewById(R.id.tv_year);
        TextView tv_quality = (TextView) rowView.findViewById(R.id.tv_quality);
        CardView card_view = (CardView) rowView.findViewById(R.id.card_view);
      //  MovieViewHolder holder=new MovieViewHolder(rowView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position == 0){
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }else {
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin),0,(int) _activity.getResources().getDimension(R.dimen.card_margin),(int) _activity.getResources().getDimension(R.dimen.card_margin));
        }
        card_view.setLayoutParams(layoutParams);
        Log.d("hjdh",""+mList.get(position).getC_image());
        picasso.load(UrlEndpoints.URL_IMAGE_APTH+mList.get(position).getC_image()).placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(iv_cover);
        tv_title.setText(mList.get(position).getC_name());
        tv_genre.setText("Location : " +mList.get(position).getC_city()+", "+mList.get(position).getC_state());
        tv_rating.setText("Rating: " + mList.get(position).getRating());
        //  holder.tv_year.setText("Year: " + mList.get(position).getYear());
        //   holder.tv_quality.setText("Quality: " +mList.get(position).getQuality());

        card_view.setTag(position);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("k9","clicked");
               /* String pos= (String) v.getTag();
                Intent i=new Intent(_activity.getApplicationContext(),College_Detail_Activity.class);
                Log.d("podd",""+pos);
                i.putExtra("position",pos);
                _activity.startActivity(i);*/
            }
        });


        return rowView;
    }

    public class MovieViewHolder  {
        private ImageView iv_cover;
        private TextView tv_title;
        private TextView tv_genre;
        private TextView tv_rating;
        private TextView tv_year;
        private TextView tv_quality;
        private CardView card_view;

        public MovieViewHolder(View x) {
           // super(x);
            iv_cover = (ImageView) x.findViewById(R.id.iv_cover);
            tv_title = (TextView) x.findViewById(R.id.tv_title);
            tv_genre = (TextView) x.findViewById(R.id.tv_genre);
            tv_rating = (TextView) x.findViewById(R.id.tv_rating);
            tv_year = (TextView) x.findViewById(R.id.tv_year);
            tv_quality = (TextView) x.findViewById(R.id.tv_quality);
            card_view = (CardView) x.findViewById(R.id.card_view);
        }

    }


}
