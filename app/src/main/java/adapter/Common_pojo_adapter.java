package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gt.active_education.R;

import java.util.List;

import utilities.Common_Pojo;
import utilities.MyApplication;

import static utilities.App_Raw_Data.getMatColor;

/**
 * Created by GT on 8/16/2017.
 */

public class Common_pojo_adapter  extends RecyclerView.Adapter<Common_pojo_adapter.MyViewHolder> {

    public int i;
    private Context context;
    private LayoutInflater mInflater;
    private TextView txt_title;
    private  int[] Img_Bnr;
    List<Common_Pojo> common_pojos;
    private CardView id_card_chips;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_title,txt_id;
        public CardView id_card_chips;

        public MyViewHolder(View view) {
            super(view);
            txt_title = (TextView) view.findViewById(R.id.txt_title);
            txt_id = (TextView) view.findViewById(R.id.txt_id);
            id_card_chips=(CardView)view.findViewById(R.id.id_card_chips);

        }

    }
    public Common_pojo_adapter(Context context, List<Common_Pojo> common_pojos) {
        this.common_pojos=common_pojos;
        this.context=context;
    }


    @Override
    public Common_pojo_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_courses, parent, false);

        return new Common_pojo_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Common_pojo_adapter.MyViewHolder holder, int position) {

        holder.txt_title.setText(common_pojos.get(position).getName());
        //Theme_Change_Prefrense(holder.txt_title);
       /* holder.txt_title.setBackgroundColor(getMatColor(String.valueOf(600)));*/
        holder.id_card_chips.setCardBackgroundColor(getMatColor(position,"mdcolor_A100"));
        if(common_pojos.get(position).getId()!=null)
        {
            holder.txt_id.setText(common_pojos.get(position).getId());
        }

    }


    @Override
    public int getItemCount() {
        return common_pojos.size();
    }



    public void Theme_Change_Prefrense(View viewById)
    {
        StateListDrawable drawable = (StateListDrawable) viewById.getBackground();
        Drawable bgShape = (Drawable) drawable.getCurrent();
      //  bgShape.setColor(context.getResources().getColor(R.color.theme1));
    }
}
