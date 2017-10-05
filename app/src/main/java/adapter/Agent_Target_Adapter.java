package adapter;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

import pojo.Agent_Deal_Pojo;

/**
 * Created by GT on 8/29/2017.
 */

public class Agent_Target_Adapter extends RecyclerView.Adapter<Agent_Target_Adapter.MovieViewHolder> {

    List<Agent_Deal_Pojo> mList = new ArrayList<>();
    Picasso picasso;
    Activity _activity;

    public Agent_Target_Adapter(List<Agent_Deal_Pojo> list_urls, Picasso p, Activity a) {
        this.mList = list_urls;
        this.picasso = p;
        this._activity = a;
    }


    @Override
    public Agent_Target_Adapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agent_target_adptr, parent, false);
        return new Agent_Target_Adapter.MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Agent_Target_Adapter.MovieViewHolder holder, final int position) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (position == 0){
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
        holder.tv_offer.setText("20 % ");

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
       
        private CardView card_view;
        private Button id_btn_apply;
        private ImageView iv_cover;
        private TextView tv_cat, tv_course, tv_l_date ,tv_seat ,tv_offer;
        public MovieViewHolder(View x) {
            super(x);
            iv_cover = (ImageView) x.findViewById(R.id.iv_cover);
            tv_cat = (TextView) x.findViewById(R.id.tv_cat);
            tv_course = (TextView) x.findViewById(R.id.tv_course);
            tv_l_date = (TextView) x.findViewById(R.id.tv_l_date);
            tv_seat = (TextView) x.findViewById(R.id.tv_seat);
            tv_offer = (TextView) x.findViewById(R.id.tv_offer);
            card_view = (CardView) x.findViewById(R.id.card_view);
            id_btn_apply=(Button)x.findViewById(R.id.id_btn_apply);
        }

    }



}
