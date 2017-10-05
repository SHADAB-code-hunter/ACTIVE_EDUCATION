package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gt.active_education.R;

/**
 * Created by GT on 6/29/2017.
 */

public class Top_Recycler_Adapter extends RecyclerView.Adapter<Top_Recycler_Adapter.MyViewHolder> {

    public int i;
    private Context context;
    private LayoutInflater mInflater;
    private TextView tv_url;
    private  int[] Img_Bnr;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,tv_url;
        public ImageView id_imgView;


        public MyViewHolder(View view) {
            super(view);
            id_imgView = (ImageView) view.findViewById(R.id.id_img_rv_adapter);
        }

    }
    public Top_Recycler_Adapter(Context context, int[] Img_Bnr) {

        this.Img_Bnr=Img_Bnr;
    }


    @Override
    public Top_Recycler_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_recycler_adapter, parent, false);

        return new Top_Recycler_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Top_Recycler_Adapter.MyViewHolder holder, int position) {

        holder.id_imgView.setImageResource(Img_Bnr[position]);
    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
