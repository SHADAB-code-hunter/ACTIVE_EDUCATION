package adapter;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import utilities.Common_Pojo;

/**
 * Created by GT on 11/18/2017.
 */

public class Facility_Adapter_JSon extends RecyclerView.Adapter<Facility_Adapter_JSon.MyViewHolder> {

    public int i;
    private Activity context;
    private LayoutInflater mInflater;
    private TextView txt_title;
    private  int[] Img_Bnr;
    List<Common_Pojo> common_pojos;
    private CardView id_card_chips;
    private JSONArray jsonArray;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_title,id_tv;
        private ImageView img_pfl_pic;
        private CheckBox id_check;
        // public CardView id_card_chips;

        public MyViewHolder(View view) {
            super(view);
            img_pfl_pic = (ImageView) view.findViewById(R.id.img_pfl_pic);
            id_tv = (TextView) view.findViewById(R.id.id_tv);

        }

    }
    public Facility_Adapter_JSon(Activity context, JSONArray jsonArray) {
        this.context=context;
        this.jsonArray=jsonArray;
    }


    @Override
    public Facility_Adapter_JSon.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facility_school, parent, false);

        return new Facility_Adapter_JSon.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Facility_Adapter_JSon.MyViewHolder holder, int position) {


        try {
            JSONObject jsonObject=jsonArray.getJSONObject(position);
            holder.id_tv.setText(jsonArray.getJSONObject(position).getString("name"));
          //  holder.img_pfl_pic.setText(jsonArray.getJSONObject(position).getString("name"));

            if(jsonObject.has("image"))
                Glide.with(context)
                        .load(jsonObject.getString("image"))
                        .into(holder.img_pfl_pic);

        } catch (Exception e) {
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
