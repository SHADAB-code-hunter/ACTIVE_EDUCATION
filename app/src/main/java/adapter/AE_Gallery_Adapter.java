package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import callbacks.VP_PageChange_Listener;
import extras.Constants;
import network.VolleySingleton;
import pojo.Gallery_Model;
import utilities.App_Static_Method;
import utilities.UrlEndpoints;

/**
 * Created by GT on 11/20/2017.
 */

public class AE_Gallery_Adapter extends RecyclerView.Adapter<AE_Gallery_Adapter.MyViewHolder> {

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

    public AE_Gallery_Adapter(Context context, VP_PageChange_Listener vpPageChangeListener, ArrayList<Gallery_Model> quiz_Ans_List, Picasso picasso) {
        this.quiz_Ans_List=quiz_Ans_List;
        this.context=context;
        this.picasso=picasso;
        //  this.vpPageChangeListener=vpPageChangeListener;
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
        this.vpPageChangeListener=vpPageChangeListener;
    }

    @Override
    public AE_Gallery_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallery_new, parent, false);

        return new AE_Gallery_Adapter.MyViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(AE_Gallery_Adapter.MyViewHolder holder, final int position) {
        Gallery_Model gallery_model = quiz_Ans_List.get(position);

        holder.id_img_glr.setTag(position);
        holder.id_img_glr.setVisibility(View.VISIBLE);
        String str_img = gallery_model.getImage_name();
       /* holder.id_img_glr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (vpPageChangeListener).on_opt_next_Ques(quiz_Ans_List,position);
            }
        });
*/
        gallery_model.getImage_name();

        Log.d("djgjhgjgdj", "http://www.activeeduindia.com/new_admin/media/event/"+ gallery_model.getImage_name());
       if (gallery_model.getImage_name() != null) {
            Log.d("imgpath", "http://www.activeeduindia.com/new_admin/media/event/" + gallery_model.getImage_name());
            picasso.load("http://www.activeeduindia.com/new_admin/media/event/" + gallery_model.getImage_name())
                    .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(holder.id_img_glr);
        }



    }
    private void loadImages(final String urlThumbnail, final AE_Gallery_Adapter.MyViewHolder holder) {
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
