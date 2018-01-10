package adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.gt.active_education.R;
import com.gt.active_education.Website_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import RAting_BAr.MaterialRatingBar;
import callbacks.Avail_Course_Listener;
import pojo.Cat_Model;

import static utilities.UrlEndpoints.IMAGE_PATH_ADAPTER;

/**
 * Created by krupenghetiya on 27/06/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    List<Cat_Model> mList = new ArrayList<>();
    Picasso picasso;
    Activity _activity;
    String str_url,str_img_path, str_type="NA";

    private String[] str_cat_arr;

    public MoviesAdapter(List<Cat_Model> list_urls, Picasso p, Activity a) {
        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
        Log.d("sseehhsh",str_url+"  data ::"+list_urls.toString());
    }
    public MoviesAdapter(List<Cat_Model> list_urls, Picasso p, Activity a,String url) {
        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
        this.str_url=url;
        Log.d("sshhsh",str_url+"  data ::"+list_urls.toString());
    }

    public MoviesAdapter(List<Cat_Model> list_urls, Picasso p, Activity a,String url, String str_img_path) {

        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
        this.str_url=url;
        this.str_img_path=str_img_path;
        Log.d("sse33ehhsh",str_url+"  data ::"+list_urls.toString());
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

        holder.card_view.setLayoutParams(layoutParams);
        holder.tv_title.setText(mList.get(position).getC_name());
        holder.tv_genre.setText("Location : " + mList.get(position).getC_city() + ", " + mList.get(position).getC_state());
        holder.tv_rating.setText("Avg Fees: " + mList.get(position).getBranch_fees() + "/ year");
        holder.tv_course.setText("Course: " + mList.get(position).getC_course());
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
        holder.img_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // continue

                ((Avail_Course_Listener) _activity).onAvailCourse(str_type, mList.get(position).getC_id(),
                        mList.get(position).getCourse_id(),
                        mList.get(position).getBranch_id(),
                        mList.get(position).getC_branch());

            }
        });
        holder.img_broucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_Download(mList.get(position).getBroucher());
            }
        });

        if (str_url != null) {
            if (isSubstring("getTopList", str_url)) {
                holder.is_discout_linear.setVisibility(View.VISIBLE);
                if (!mList.get(position).getDiscount_fee().equals("NA")) {
                    holder.id_discout_text.setText(Math.round(Float.parseFloat(mList.get(position).getDiscount_fee())) + " %");//mList.get(position).getDiscount_fee()
                }

            }
        }


        //   ==============================================================
        Log.d("dgdggd", "" + str_url + "   " + str_type);

                String halg_img_path = IMAGE_PATH_ADAPTER;
                str_cat_arr = _activity.getResources().getStringArray(R.array.category_image);
                if (str_url != null) {
                    if ( !isSubstring("getSearchResult.php",str_url)) {
                        str_type = str_url.split("=")[1];
                    }else {
                        str_type = mList.get(position).getType();
                    }
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
                }

            Log.d("djgjhgjgdj",halg_img_path);

            if(!mList.get(position).getC_image().equals("picture.png")) {
                if (halg_img_path != null) {
                    Log.d("imgpath", "" + halg_img_path+"/picture/" + mList.get(position).getC_image());
                    picasso.load(halg_img_path+"/picture/" + mList.get(position).getC_image())
                            .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);
                }
            }else {

                Log.d("noimage",halg_img_path);
                if(!mList.get(position).getC_image().equals(null)){
                    Log.d("imgpddath", "" + halg_img_path+"/picture/" + mList.get(position).getC_image());
                    picasso.load(halg_img_path+"/picture/"  + mList.get(position).getC_image())
                            .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);}
                else {

                }
            }


        Log.d("listarray",position+"  "+mList.get(position).getRating());
        if(!(mList.get(position).getRating().equals("NA"))  && !(mList.get(position).getRating().equals(null)))
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
                    Log.d("startt", position + "   " + mList.get(position).getRating());
                    //  holder.id_rating_bar.setRating(Float.parseFloat(mList.get(position).getRating()));
                    Float fls = Float.parseFloat(mList.get(position).getRating());
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
    }

    private void call_Download(String str_brouchuer) {

     //   String brouchure_doewnload=BROUCHURE_DOWNLOAD+str_type+"&brochure="+str_brouchuer;
         String brouchure_doewnload="http://activeeduindia.com/admin/webservices/downloadBrochure.php?type=1&brochure=dpsdwarka.pdf";

        if (brouchure_doewnload!=null) {
            if (isSubstring("http://", brouchure_doewnload)) {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(_activity, Website_Activity.class);
                intent.putExtra("Url_Web", brouchure_doewnload);
                _activity.startActivity(intent);
            } else {
                Log.d("sswess", "" + brouchure_doewnload);
                Intent intent = new Intent(_activity, Website_Activity.class);
                intent.putExtra("Url_Web", "http://" + brouchure_doewnload);
                _activity.startActivity(intent);
            }
        }else {

            Toast.makeText(_activity, "Link Not Found !!!!", Toast.LENGTH_SHORT).show();

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
        Log.d("ndnccnnc",""+mList.get(position).toString());
        Log.d("kdddd9","clicked"+
                ""+mList.get(position).getC_id()
        +" "+mList.get(position).getCourse_id()
        +" "+mList.get(position).getBranch_id()
        +" course_name : "+mList.get(position).getC_course());
        String pos= (String) v.getTag();
        Intent i=new Intent(_activity.getApplicationContext(),College_Detail_Activity.class);

        Bundle bundle = new Bundle();
        bundle.putString("clg_id",""+mList.get(position).getC_id());
        bundle.putString("course",""+mList.get(position).getCourse_id());
        bundle.putString("course_name",""+mList.get(position).getC_course());
        bundle.putString("branch",""+mList.get(position).getBranch_id());
        bundle.putString("branch_name",""+mList.get(position).getC_branch());
        bundle.putString("branch_fee",""+mList.get(position).getBranch_fees());

        if(mList.get(position).getDealid()!=null) {
            bundle.putString("dealid",""+mList.get(position).getDealid());
        }
        bundle.putString("id",""+mList.get(position).getId());
        i.putExtras(bundle);

        if(!(mList.get(position).getCategory()).equals("NA"))// for
        {
            Log.d("djeejd","dhdh"+(mList.get(position).getCategory()));
            i.putExtra("type",""+str_type);
        }
        else
        {
            i.putExtra("type",""+str_type);
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
        private TextView tv_course;
        private CardView card_view;
        private Button id_btn_apply;
        private LinearLayout is_discout_linear;
        private MaterialRatingBar id_rating_bar;
        private LinearLayout id_rating_linear;

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
          //  id_rating_bar=(MaterialRatingBar)x.findViewById(R.id.id_rating_bar);

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
