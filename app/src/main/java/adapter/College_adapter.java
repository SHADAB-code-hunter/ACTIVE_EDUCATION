package adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.gt.active_education.R;
import com.gt.active_education.Website_Activity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import RAting_BAr.MaterialRatingBar;
import callbacks.AvailCourseListener_School;
import callbacks.CALL_ADAPTER;
import callbacks.Listing_Listener;

/**
 * Created by krupenghetiya on 27/06/17.
 */

public class College_adapter extends RecyclerView.Adapter<College_adapter.MovieViewHolder> {


    Picasso picasso;
    Activity _activity;
    String str_url, str_img_path, str_type = "NA";
    JSONArray data;

    private String[] str_cat_arr;
    private String _website;
    private String str_call;
    private String str_brochure;


    public College_adapter(Activity _activity, JSONArray data) {
       /* this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
        this.str_url=url;*/
        this.str_img_path = str_img_path;
        this.data = data;
        this._activity = _activity;
        Log.d("sse33ehhsh", str_url + "  data ::" + data.toString());
    }
    public void add(int position, JSONArray data) {
        try {
            data.put(position,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyItemInserted(position);
    }
    public JSONArray getJSONARRAy()
    {
        return data;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (position == 0) {
                    layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin), (int) _activity.getResources().getDimension(R.dimen.card_margin), (int) _activity.getResources().getDimension(R.dimen.card_margin), (int) _activity.getResources().getDimension(R.dimen.card_margin));
                } else {
                    layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin), 0, (int) _activity.getResources().getDimension(R.dimen.card_margin), (int) _activity.getResources().getDimension(R.dimen.card_margin));
                }
        try {


            ((Listing_Listener)_activity).on_List_method(position);
            JSONObject jsonObject=data.getJSONObject(position);
            Log.d("hjhjhjhj",""+jsonObject);

           // Toast.makeText(_activity, ""+jsonObject.getString("c_image"), Toast.LENGTH_SHORT).show();
            if(jsonObject.has("c_name"))
            holder.tv_title.setText(jsonObject.getString("c_name"));

            if(jsonObject.has("c_image"))
            Glide.with(_activity)
                    .load(jsonObject.getString("c_image"))
                    .into(holder.iv_cover);

            if(jsonObject.has("discount")) {
                holder.is_discout_linear.setVisibility(View.VISIBLE);
                holder.id_discout_text.setText(jsonObject.getString("discount")+"%");
            }

            if(jsonObject.has("c_course")) {
                holder.tv_course.setText("Course : " +jsonObject.getString("c_course"));
            }
            if(jsonObject.has("branch_fee")) {
                holder.tv_rating.setText("Course Fee : " +jsonObject.getString("branch_fee"));
            }
            if(jsonObject.has("c_website")) {
                 _website=jsonObject.getString("c_website");
            }
            if(jsonObject.has("discount")) {
                str_call=jsonObject.getString("discount");
            }
            if(jsonObject.has("affiliation")) {
               holder.tv_genre.setText("Affiliation : " +jsonObject.getString("affiliation"));
            }
             if(jsonObject.has("c_branch")) {
               holder.tv_year.setText("Branch : " +jsonObject.getString("c_branch"));
            }

            holder.img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPermission(Manifest.permission.CALL_PHONE)) {
                        String dial = "tel:" + str_call;
                        _activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    } else {
                        Toast.makeText(_activity, "Permission Phone Call denied", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            holder.img_web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    call_website(_website);
                }
            });

            if(jsonObject.has("brochure")) {
                str_brochure=jsonObject.getString("brochure");
            }

            holder.img_broucher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        call_Download(data.getJSONObject(position),str_brochure);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            holder.id_btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        ( (CALL_ADAPTER)_activity).oncall_adapter(data.getJSONObject(position),"action");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.id_applylayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        ( (CALL_ADAPTER)_activity).oncall_adapter(data.getJSONObject(position), "action");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            holder.img_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // continue
                    try {
                        ((AvailCourseListener_School) _activity).onAvailCourse(data.getJSONObject(position),data.getJSONObject(position).getString("c_id"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            // rating

            Log.d("listarray",position+"  ");
            if(jsonObject.has("rating"))
            {
                holder.id_rating_linear.setTag(position);
                int poss= -1;
                poss=(int) holder.id_rating_linear.getTag();
                if(poss!=-1)
                {
                    int childCount=0;
                    childCount=  holder.id_rating_linear.getChildCount();

                    if(childCount==0)
                    {
                        Log.d("jdjhdjh", poss + "    " + childCount);

                        //  Log.d("startt",mList.get(position).getRating());
                        Log.d("startt", position + "   " + jsonObject.getString("rating"));
                        //  holder.id_rating_bar.setRating(Float.parseFloat(mList.get(position).getRating()));
                        Float fls = Float.parseFloat(jsonObject.getString("rating"));
                        int rount_int;
                        float modulus_int = (fls % 1);
                        if (modulus_int != 0) {
                            rount_int = (Math.round(fls) - 1);
                        } else {
                            rount_int = Math.round(fls);
                        }
                        Log.d("djhjd", "" + modulus_int + "  " + rount_int);
                        for (int i = 1; i <= 5; i++) {
                            ImageView imageView = new ImageView(_activity);
                            if (i <= (rount_int)) {
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                imageView.setMaxHeight(10);
                                imageView.setMaxWidth(10);
                                imageView.setImageResource(R.drawable.mrb_star_icon_black_36dp);
                                Drawable layerDrawable = imageView.getDrawable();
                                layerDrawable.setTint(_activity.getResources().getColor(R.color.tumblr_red));

                            } else {
                                if (modulus_int != 0 && i == (rount_int + 1)) {
                                    //imageView.
                                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                    imageView.setImageResource(R.drawable.ic_half_star_full);
                                } else {
                                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                    imageView.setImageResource(R.drawable.mrb_star_border_icon_black_36dp);
                                }
                            }

                            ((LinearLayout) holder.id_rating_linear).addView(imageView);
                        }
                    }else {
                        Log.d("jhhj","elslelslls");
                    }
                }
            }else {
                holder.id_rating_linear.removeAllViews();
            }


        } catch (Exception e) {
            Log.d("excep",""+e.getMessage());
        }

    }
    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(_activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void call_Download(JSONObject jsonObject, String brouchure_doewnload) {
        Toast.makeText(_activity, ""+brouchure_doewnload, Toast.LENGTH_SHORT).show();

        //   String brouchure_doewnload=BROUCHURE_DOWNLOAD+str_type+"&brochure="+str_brouchuer;
      //  String brouchure_doewnload="http://activeeduindia.com/admin/webservices/downloadBrochure.php?type=1&brochure="+str_brouchuer;

        if (!brouchure_doewnload.isEmpty()) {

            if (isSubstring("http://", brouchure_doewnload)) {
                Log.d("sswess", "" + brouchure_doewnload);

                ( (CALL_ADAPTER)_activity).oncall_adapter(jsonObject, "Brochure");
            } else {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(_activity, Website_Activity.class);
                intent.putExtra("Url_Web", "http://" + brouchure_doewnload);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _activity.startActivity(intent);
                ( (CALL_ADAPTER)_activity).oncall_adapter(jsonObject, "Brochure");
            }
        }else {

            Toast.makeText(_activity, "Link Not Found !!!!", Toast.LENGTH_SHORT).show();

        }

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


    @Override
    public int getItemCount() {
        return data.length();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_cover,img_web,img_course,img_call,img_broucher;
        private TextView tv_title,id_discout_text;
        private TextView tv_genre;
        private TextView tv_rating;
        private TextView tv_year;
        private TextView tv_course;
        private CardView card_view;
        private Button id_btn_apply;
        private LinearLayout is_discout_linear;
        private MaterialRatingBar id_rating_bar;
        private LinearLayout id_rating_linear,id_applylayout;

        public MovieViewHolder(View x) {
            super(x);
            iv_cover = (ImageView) x.findViewById(R.id.iv_cover);
            tv_title = (TextView) x.findViewById(R.id.tv_title);
            tv_genre = (TextView) x.findViewById(R.id.tv_genre);
            tv_rating = (TextView) x.findViewById(R.id.tv_rating);
            tv_year = (TextView) x.findViewById(R.id.tv_year);
            tv_course = (TextView) x.findViewById(R.id.tv_quality);
            id_discout_text = (TextView) x.findViewById(R.id.id_discout_text);
            card_view = (CardView) x.findViewById(R.id.card_view);
            id_btn_apply=(Button)x.findViewById(R.id.id_btn_apply);
            id_rating_linear=(LinearLayout)x.findViewById(R.id.id_rating_linear);
            id_applylayout=(LinearLayout)x.findViewById(R.id.id_applylayout);
          //  id_rating_bar=(MaterialRatingBar)x.findViewById(R.id.id_rating_bar);

            img_web=(ImageView)x.findViewById(R.id.img_web);
            img_course=(ImageView)x.findViewById(R.id.img_course);
            img_call=(ImageView)x.findViewById(R.id.img_call);
            img_broucher=(ImageView)x.findViewById(R.id.img_broucher);
            is_discout_linear=(LinearLayout) x.findViewById(R.id.is_discout_linear);

        }

    }

    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }

}