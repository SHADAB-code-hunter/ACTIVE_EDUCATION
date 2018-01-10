package adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callbacks.Callback_list_Listener;
import utilities.Common_Pojo;

/**
 * Created by GT on 12/2/2017.
 */

public class Adter_RV_Filter extends RecyclerView.Adapter<Adter_RV_Filter.MyViewHolder> {

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

        public TextView txt_title,txt_id;
        private FrameLayout id_frm;
        // public CardView id_card_chips;

        public MyViewHolder(View view) {
            super(view);
            txt_title = (TextView) view.findViewById(R.id.txt_title);
            txt_id = (TextView) view.findViewById(R.id.txt_id);
            id_frm = (FrameLayout) view.findViewById(R.id.id_frm);
            //  id_card_chips=(CardView)view.findViewById(R.id.id_card_chips);

        }

    }

     public Adter_RV_Filter(Context context, JSONArray jsonArray, Callback_list_Listener callback_list_listener, String str_key) {
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
    public Adter_RV_Filter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_adapter, parent, false);

        return new Adter_RV_Filter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Adter_RV_Filter.MyViewHolder holder, final int position) {

        try {
            holder.txt_title.setBackground(context.getResources().getDrawable(R.drawable.btn_outline_ract));
            final  JSONObject jsonObject=jsonArray.getJSONObject(position);
          //  Toast.makeText(context, "::  "+jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
              Log.d("knnkk",""+jsonArray);
          //  Log.d("ex_apd",""+jsonArray.getJSONObject(position).getString("name"));

            if(jsonObject.has("name"))
            holder.txt_title.setText(""+jsonObject.getString("name"));

            if(jsonObject.has("bname")) {
                str_key="AVAIL";
                holder.txt_title.setText("" + jsonObject.getString("bname"));
            }

            holder.txt_title.setTag(position);
            holder.txt_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag= String.valueOf(v.getTag());
                    Log.d("mmm",""+v.getTag());

                    Log.d("fsder",""+map_selection.getOrDefault(""+v.getTag(),"NA"));
                    if(map_selection.getOrDefault(tag,"NA").equalsIgnoreCase("NA")||(map_selection.getOrDefault(tag,"NA").equalsIgnoreCase("UNSELECTED"))) {
                        map_selection.put(""+v.getTag(),"SELECTED");
                       /* StateListDrawable drawable =(StateListDrawable) holder.txt_title.getBackground();
                        GradientDrawable bgShape = (GradientDrawable) drawable.getCurrent();
                        bgShape.setColor(context.getResources().getColor(R.color.tumblr_red));*/
                        holder.txt_title.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tumblr_red)));
                       // callback_list_listener.onAdap_OBJ(jsonObject,str_key);

                    }else if(map_selection.getOrDefault(tag,"NA").equalsIgnoreCase("SELECTED")) {
                        map_selection.put(""+v.getTag(),"UNSELECTED");
                        holder.txt_title.setBackground(context.getResources().getDrawable(R.drawable.btn_outline_ract));
                    }

                }
            });

            if(map_selection.getOrDefault(""+position,"NA").equalsIgnoreCase("NA")||(map_selection.getOrDefault(""+position,"NA").equalsIgnoreCase("UNSELECTED"))) {

                Log.d("coloemap",""+position+" :: "+map_selection.getOrDefault(""+position,"NA"));
                map_selection.put(""+position,"UNSELECTED");
                holder.txt_title.setBackground(context.getResources().getDrawable(R.drawable.btn_outline_ract));


            }else if(map_selection.getOrDefault(""+position,"NA").equalsIgnoreCase("SELECTED")) {
                Log.d("colo_emap",""+position+" :: "+map_selection.getOrDefault(""+position,"NA"));
                map_selection.put(""+position,"SELECTED");
                holder.txt_title.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.tumblr_red)));
            }


           /* StateListDrawable drawable =(StateListDrawable) holder.id_frm.getBackground();

            GradientDrawable bgShape = (GradientDrawable) drawable.getCurrent();
            bgShape.setColor(context.getResources().getColor(R.color.tumblr_red));
            callback_list_listener.onAdap_OBJ(jsonObject,str_key);*/

        } catch (Exception e) {
           Log.d("exapd",""+e.getMessage());
        }


    }


    @Override
    public int getItemCount() {
        return jsonArray.length();
    }



    public void Theme_Change_Prefrense(View viewById)
    {
        StateListDrawable drawable = (StateListDrawable) viewById.getBackground();
        Drawable bgShape = (Drawable) drawable.getCurrent();
        //  bgShape.setColor(context.getResources().getColor(R.color.theme1));
    }
}
