package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import pojo.Send_date_Model;
import utilities.UrlEndpoints;

/**
 * Created by GT on 5/27/2017.
 */

public class Gift_Voucher_Adapter extends RecyclerView.Adapter<Gift_Voucher_Adapter.MyViewHolder> {

public int i;
private Context context;
private LayoutInflater mInflater;
private VolleySingleton mVolleySingleton;
private ImageLoader mImageLoader;
private ArrayList<Send_date_Model> sendDateModels;
private TextView tv_url;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView id_name,id_img,added_date,rdate,rank;
    public ImageView audition_img;


    public MyViewHolder(View view) {
        super(view);
        audition_img = (ImageView) view.findViewById(R.id.img_gift);
       // id_name = (TextView) view.findViewById(R.id.id_name);
    }
}


    public Gift_Voucher_Adapter(Context context, ArrayList<Send_date_Model> sendDateModels) {
        this.sendDateModels = sendDateModels;
        this.context=context;
        mInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gift_voucher_adapetr, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Send_date_Model currentMovie = sendDateModels.get(position);
        String str=currentMovie.getAdded_date();
        String str_name=currentMovie.getName();
        Log.d("straddaed",str_name);
//        holder.id_img.setText(currentMovie.getImage());

       /* if(!str_name.isEmpty()) {
            holder.id_name.setText(str_name);
            holder.added_date.setText(str);
        }*/
//        holder.rdate.setText(currentMovie.getRdate());
//        holder.rank.setText(currentMovie.getRank());
        //Log.d("check",""+currentMovie.getId());
        //retrieved date may be null
      /*  String quiz_sub_name = currentMovie.getRank();
        if (quiz_sub_name != null) {
            // String formattedDate = mFormatter.format(movieReleaseDate);
//            holder.title.setText(quiz_sub_name);
        } else {
            holder.added_date.setText(Constants.NA);
        }

        String str_id=currentMovie.getId();
        if (str_id != null) {
            // String formattedDate = mFormatter.format(movieReleaseDate);
            //Log.d("idd",str_id);
            holder.tv_url.setText(str_id);
        } else {
            //  holder.title.setText(Constants.NA);//for default
        }
*/
        String str_img=currentMovie.getImage();
        if (str_img != null) {
            // String formattedDate = mFormatter.format(movieReleaseDate);
            String strimg= UrlEndpoints.URL_Gift_image+str_img;
            //Log.d("strimg",strimg);
            loadImages(strimg, holder);
        } else {
            //  holder.title.setText(Constants.NA);//for default
        }


    }

    private void loadImages(final String urlThumbnail, final Gift_Voucher_Adapter.MyViewHolder holder) {
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
        return sendDateModels.size();
    }
}
