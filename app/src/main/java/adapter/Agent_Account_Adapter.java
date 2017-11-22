package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pojo.Agent_Deal_Pojo;

/**
 * Created by GT on 10/24/2017.
 */

public class Agent_Account_Adapter extends RecyclerView.Adapter<Agent_Account_Adapter.MovieViewHolder> {

    List<Agent_Deal_Pojo> mList = new ArrayList<>();
    Picasso picasso;
    Context _activity;
    JSONArray jsonArray;

    public Agent_Account_Adapter(Context ctx, JSONObject jsonObject) {
        this._activity = ctx;
        try {
            this.jsonArray=jsonObject.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Agent_Account_Adapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_list_adapter, parent, false);
        return new Agent_Account_Adapter.MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Agent_Account_Adapter.MovieViewHolder holder, final int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       /* if (position == 0){
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin_new),(int) _activity.getResources().getDimension(R.dimen.card_margin_new),(int) _activity.getResources().getDimension(R.dimen.card_margin_new),(int) _activity.getResources().getDimension(R.dimen.card_margin_new));
        }else {
            layoutParams.setMargins((int) _activity.getResources().getDimension(R.dimen.card_margin_new),0,(int) _activity.getResources().getDimension(R.dimen.card_margin_new),(int) _activity.getResources().getDimension(R.dimen.card_margin_new));
        }
        holder.card_view.setLayoutParams(layoutParams);
        Agent_Deal_Pojo agent_deal_pojo=mList.get(position);

        holder.tv_cat.setText(agent_deal_pojo.getCategory_name());
        holder.tv_course.setText(agent_deal_pojo.getCourse_name());
        holder.tv_l_date.setText(agent_deal_pojo.getEnd_date());
        holder.tv_seat.setText(agent_deal_pojo.getTotal_admission());
        holder.tv_offer.setText("20 % ");*/

        try {
            JSONObject jsonObject=jsonArray.getJSONObject(position);

            holder.id_tv_title.setText(jsonObject.getString("bank"));
            holder.id_tv_reciept_no.setText("Payment Receipt No: "+jsonObject.getString("receipt"));
            holder.id_tv_pay_date.setText(jsonObject.getString("added_date"));
            holder.id_payment.setText(jsonObject.getString("price")+" /-");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private CardView card_view;
        private Button id_btn_apply;
        private ImageView iv_cover;
        private TextView id_tv_title, id_tv_reciept_no, id_tv_pay_date ,id_payment;
        public MovieViewHolder(View x) {
            super(x);





            id_tv_title = (TextView) x.findViewById(R.id.id_tv_title);
            id_tv_reciept_no = (TextView) x.findViewById(R.id.id_tv_reciept_no);
            id_tv_pay_date = (TextView) x.findViewById(R.id.id_tv_pay_date);
            id_payment = (TextView) x.findViewById(R.id.id_payment);

            id_btn_apply=(Button)x.findViewById(R.id.id_btn_apply);
        }

    }



}
