package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.gt.active_education.R;

import java.util.ArrayList;

import callbacks.VP_PageChange_Listener;
import extras.Constants;
import network.VolleySingleton;
import pojo.Gallery_Model;
import utilities.UrlEndpoints;

/**
 * Created by GT on 5/30/2017.
 */

public class Gallery_Adapter extends RecyclerView.Adapter<Gallery_Adapter.MyViewHolder> {

    private final VP_PageChange_Listener vpPageChangeListener;
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

    public Gallery_Adapter(Context context,VP_PageChange_Listener vpPageChangeListener, ArrayList<Gallery_Model> quiz_Ans_List) {
        this.quiz_Ans_List=quiz_Ans_List;
        this.context=context;
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
        loadImages(strimg, holder);
      final   int _poss= position;
       /* holder.id_img_glr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int poss= (int) v.getTag();
             //   vpPageChangeListener.on_opt_next_Ques();
                vpPageChangeListener.on_opt_next_Ques(quiz_Ans_List,_poss);
            }
        });*/

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
