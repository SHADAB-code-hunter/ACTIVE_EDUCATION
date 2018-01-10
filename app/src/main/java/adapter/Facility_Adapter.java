package adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import utilities.Common_Pojo;

/**
 * Created by GT on 11/18/2017.
 */

public class Facility_Adapter  extends RecyclerView.Adapter<Facility_Adapter.MyViewHolder> {

    public int i;
    private Context context;
    private LayoutInflater mInflater;
    private TextView txt_title;
    private  int[] Img_Bnr;
    List<Common_Pojo> common_pojos;
    private CardView id_card_chips;
    private JSONArray jsonArray;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_title,txt_id;
        private CheckBox id_check;
        // public CardView id_card_chips;

        public MyViewHolder(View view) {
            super(view);
            txt_title = (TextView) view.findViewById(R.id.txt_title);
            txt_id = (TextView) view.findViewById(R.id.txt_id);
            id_check=(CheckBox)view.findViewById(R.id.id_check);

        }

    }
    public Facility_Adapter(Context context, JSONArray jsonArray) {
        this.context=context;
        this.jsonArray=jsonArray;
    }


    @Override
    public Facility_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facility_item, parent, false);

        return new Facility_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Facility_Adapter.MyViewHolder holder, int position) {


        try {
            holder.id_check.setText(jsonArray.getJSONObject(position).getString("name"));
          //  holder.id_check.setText(jsonArray.getJSONObject(position).getString("name"));

        Log.d("nmncmdnc",jsonArray.toString());
      /*  holder.id_check.setText(common_pojos.get(position).getName());
        //Theme_Change_Prefrense(holder.txt_title);
       *//* holder.txt_title.setBackgroundColor(getMatColor(String.valueOf(600)));*//*
        //   holder.id_card_chips.setCardBackgroundColor(getMatColor(position,"mdcolor_A100"));
        if(common_pojos.get(position).getId()!=null)
        {
            holder.txt_id.setText(common_pojos.get(position).getId());
        }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        //return 20;
        return jsonArray.length();
    }



    public void Theme_Change_Prefrense(View viewById)
    {
        StateListDrawable drawable = (StateListDrawable) viewById.getBackground();
        Drawable bgShape = (Drawable) drawable.getCurrent();
        //  bgShape.setColor(context.getResources().getColor(R.color.theme1));
    }
}
