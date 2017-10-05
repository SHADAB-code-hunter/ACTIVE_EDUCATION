package adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gt.active_education.R;

import utilities.App_Static_Method;
import utilities.MyApplication;
import utilities.MyBounceInterpolator;

/**
 * Created by GT on 4/15/2017.
 */

public class DashBoard_Menu_Adapter extends RecyclerView.Adapter<DashBoard_Menu_Adapter.MyViewHolder> {

    private  int[] img_array;
    private  Context context;
    private  LayoutInflater mInflater;
    private  String[] tv_array;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,tv_url;
        public ImageView audition_img;
        public CardView id_card;


        public MyViewHolder(View view) {
            super(view);
            audition_img = (ImageView) view.findViewById(R.id.img_sub);
            title = (TextView) view.findViewById(R.id.tv_url);
            id_card=(CardView)view.findViewById(R.id.id_card);
        }

    }
    public DashBoard_Menu_Adapter(Context context, int[] img_array, String[] tv_array) {
        this.context=context;
        this.img_array=img_array;
        this.tv_array=tv_array;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public DashBoard_Menu_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adp_db_grid_layout, parent, false);

        return new DashBoard_Menu_Adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(DashBoard_Menu_Adapter.MyViewHolder holder, int position) {

        holder.title.setText(tv_array[position]);
        holder.audition_img.setImageResource(img_array[position]);
        holder.id_card.setCardBackgroundColor(getMatColor(position));
        App_Static_Method.btn_animation(holder.id_card,context);
    }

    @Override
    public int getItemCount() {
        return img_array.length;
    }

    private int getMatColor(int index)
    {
        int returnColor = Color.WHITE;
        int arrayId = MyApplication.getAppContext().getResources().getIdentifier("cat_color",
                "array", MyApplication.getAppContext().getPackageName());

        if (arrayId != 0)
        {
            TypedArray colors = MyApplication.getAppContext().getResources().obtainTypedArray(arrayId);
           // int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

/*
    private void btn_animation(CardView holder)
    {
      //  Button button = (Button)findViewById(R.id.button);
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        holder.startAnimation(myAnim);
    }
*/

}
