package adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callbacks.Callback_list_Listener;
import callbacks.Dialog_listener;
import utilities.Common_Pojo;

/**
 * Created by GT on 12/2/2017.
 */

public class Adter_Avail_courses extends RecyclerView.Adapter<Adter_Avail_courses.MyViewHolder> {

    public int i;
    private Context context;
    private LayoutInflater mInflater;
    private TextView txt_title;
    private  int[] Img_Bnr;
    List<Common_Pojo> common_pojos;
    private CardView id_card_chips;
    JSONArray jsonArray;
    Callback_list_Listener callback_list_listener;
    String str_key;

    Map<String,String> map_selection=new HashMap<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_course,txt_fee,txt_discount;
        private LinearLayout id_linear_upper;
        // public CardView id_card_chips;

        public MyViewHolder(View view) {
            super(view);
            txt_course = (TextView) view.findViewById(R.id.txt_course);
            txt_fee = (TextView) view.findViewById(R.id.txt_fee);
            txt_title = (TextView) view.findViewById(R.id.txt_title);
            txt_discount = (TextView) view.findViewById(R.id.txt_discount);
            id_linear_upper = (LinearLayout) view.findViewById(R.id.id_linear_upper);
            //  id_card_chips=(CardView)view.findViewById(R.id.id_card_chips);

        }

    }

     public Adter_Avail_courses(Context context, JSONArray jsonArray, Callback_list_Listener callback_list_listener, String str_key) {
            this.jsonArray=jsonArray;
            Log.d("kn_nkk",""+str_key);
            this.context=context;
            this.callback_list_listener=callback_list_listener;
            this.str_key=str_key;
             for(int i=0; i<jsonArray.length();i++)
             {
                 map_selection.put(""+i,"UNSELECTED");
             }

        }


    @Override
    public Adter_Avail_courses.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.avail_class_adapter, parent, false);

        return new Adter_Avail_courses.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Adter_Avail_courses.MyViewHolder holder, final int position) {

        try {
            final  JSONObject jsonObject=jsonArray.getJSONObject(position);
          //  Toast.makeText(context, "::  "+jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
              Log.d("knnkk",""+jsonArray);
          //  Log.d("ex_apd",""+jsonArray.getJSONObject(position).getString("name"));

            if(jsonObject.has("name"))
            holder.txt_course.setText(""+jsonObject.getString("name"));

            holder.txt_fee.setText("" + jsonObject.getString("fee"));
            holder.txt_discount.setText("" + jsonObject.getString("display_offer"));
            holder.id_linear_upper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback_list_listener.onAdap_OBJ(jsonObject,"AVAIL");
                }
            });
        } catch (Exception e) {
           Log.d("exapd",""+e.getMessage());
        }


    }


    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

}
