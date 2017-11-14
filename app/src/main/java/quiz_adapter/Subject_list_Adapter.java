package quiz_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.gt.active_education.R;

import java.util.ArrayList;

import extras.Constants;
import network.VolleySingleton;
import pojo.Quiz_Subject_Model;
import utilities.UrlEndpoints;

/**
 * Created by GT on 4/15/2017.
 */

public class Subject_list_Adapter extends RecyclerView.Adapter<Subject_list_Adapter.MyViewHolder> {

    public int i;
    private Context context;
    private LayoutInflater mInflater;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;
    private ArrayList<Quiz_Subject_Model> quizSubjectModels;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,tv_url;
        public ImageView audition_img;


        public MyViewHolder(View view) {
            super(view);
            audition_img = (ImageView) view.findViewById(R.id.img_sub);
            title = (TextView) view.findViewById(R.id.tv_sub);
            tv_url=(TextView)view.findViewById(R.id.tv_url);
        }

    }

    public Subject_list_Adapter(Context context, ArrayList<Quiz_Subject_Model> quizSubjectModels) {
        this.quizSubjectModels = quizSubjectModels;
        this.context=context;
        mInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();

    }

    @Override
    public Subject_list_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_choose_layout, parent, false);

        return new Subject_list_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Subject_list_Adapter.MyViewHolder holder, int position) {

        Quiz_Subject_Model currentMovie = quizSubjectModels.get(position);

        holder.title.setText(currentMovie.getSname());
        //Log.d("check",""+currentMovie.getId());
        //retrieved date may be null
        String quiz_sub_name = currentMovie.getSname();
        if (quiz_sub_name != null) {
            // String formattedDate = mFormatter.format(movieReleaseDate);
            holder.title.setText(quiz_sub_name);
        } else {
            holder.title.setText(Constants.NA);
        }

        String str_id=currentMovie.getId();
        if (str_id != null) {
            // String formattedDate = mFormatter.format(movieReleaseDate);
        //Log.d("idd",str_id);
            holder.tv_url.setText(str_id);
        } else {
            //  holder.title.setText(Constants.NA);//for default
        }

        String str_img=currentMovie.getImages();
        if (str_img != null) {
            // String formattedDate = mFormatter.format(movieReleaseDate);
            String strimg= UrlEndpoints.URL_Img_Sub_URL+str_img;
            //Log.d("strimg",strimg);
            loadImages(strimg, holder);
        } else {
          //  holder.title.setText(Constants.NA);//for default
        }
    }

    private void loadImages(final String urlThumbnail, final Subject_list_Adapter.MyViewHolder holder) {
        if (!urlThumbnail.equals(Constants.NA)) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.audition_img.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return quizSubjectModels.size();
    }
}
