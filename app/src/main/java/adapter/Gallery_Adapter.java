package adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import callbacks.VP_PageChange_Listener;
import extras.Constants;
import network.VolleySingleton;
import pojo.Gallery_Model;
import utilities.App_Static_Method;
import utilities.UrlEndpoints;

import static utilities.UrlEndpoints.IMAGE_PATH_ADAPTER;

/**
 * Created by GT on 5/30/2017.
 */

public class Gallery_Adapter extends RecyclerView.Adapter<Gallery_Adapter.MyViewHolder> {

    private final VP_PageChange_Listener vpPageChangeListener;
    private final Picasso picasso;
    public int i;
    private Context context;
    private ArrayList<Gallery_Model> quiz_Ans_List;

    private String str_type;
    private Gallery_Model answerQuizMdl;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;

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

        return quiz_Ans_List.size();
    }

    public Gallery_Adapter(Context context, VP_PageChange_Listener vpPageChangeListener, ArrayList<Gallery_Model> quiz_Ans_List, Picasso picasso) {
        this.quiz_Ans_List=quiz_Ans_List;
        this.context=context;
        this.picasso=picasso;
      //  this.vpPageChangeListener=vpPageChangeListener;
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
        this.vpPageChangeListener=vpPageChangeListener;
    }

    @Override
    public Gallery_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallery_new, parent, false);

        return new Gallery_Adapter.MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(Gallery_Adapter.MyViewHolder holder, final int position) {
        Gallery_Model gallery_model = quiz_Ans_List.get(position);

            holder.id_img_glr.setTag(position);
            holder.id_img_glr.setVisibility(View.VISIBLE);
            String str_img = gallery_model.getImage_name();
            holder.id_img_glr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (vpPageChangeListener).on_opt_next_Ques(quiz_Ans_List,position);
                }
            });
        String strimg = UrlEndpoints.GALLERY_IMG_PATH + str_img;

        Log.d("strimg", strimg);


        String halg_img_path = "http://www.activeeduindia.com/new_admin/media/";
       String[] str_cat_arr = context.getResources().getStringArray(R.array.category_image);

       //;
       // if (str_url != null) {
          /*  if ( !isSubstring("getSearchResult.php",str_url)) {
                str_type = str_url.split("=")[1];
            }else {
                str_type = mList.get(position).getType();
            }*/
            switch ( App_Static_Method.session_type().get("utype")) {
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
    //    }

        Log.d("djgjhgjgdj",halg_img_path);
        if (halg_img_path != null) {
            Log.d("imgpath", "" + halg_img_path+"/picture/" + quiz_Ans_List.get(position).getImage_name());
            picasso.load(halg_img_path+"/picture/" + (quiz_Ans_List.get(position).getImage_name()))
                    .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.id_img_glr);
        }

       /* if(!quiz_Ans_List.get(position).getImage_name().equals("picture.png")) {

        }else {

            Log.d("noimage",halg_img_path);
            if(!quiz_Ans_List.get(position).getC_image().equals(null)){
                Log.d("imgpddath", "" + halg_img_path+"/picture/" + mList.get(position).getC_image());
                Glide.with(halg_img_path+"/picture/"  + mList.get(position).getC_image())
                        .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.iv_cover);}
            else {

            }
        }*/


    }
    private void loadImages(final String urlThumbnail, final Gallery_Adapter.MyViewHolder holder) {
        if (!urlThumbnail.equals(Constants.NA)) {
            //Log.d("strimg", urlThumbnail);
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.id_img_glr.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }


}
