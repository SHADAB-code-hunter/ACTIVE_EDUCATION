package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import adapter.Agent_Deal_Adapter;
import pojo.Agent_Deal_Pojo;
import task.Agent_Async;
import utilities.MyApplication;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

/**
 * Created by GT on 10/30/2017.
 */

public class Banner_Fragment extends Fragment{

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frg_best_deal, container, false);
       /* recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        rootView.findViewById(R.id.id_heading).setVisibility(View.GONE);
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence,0);
        String str_token=sharedPreferences.getString("Login_Token", "na");
        String str_email=sharedPreferences.getString("email", "na");
        *//*recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setScrollingTouchSlop();*//*
        //recyclerView.setHasFixedSize(true);
        Log.d("uel", UrlEndpoints.GET_BEST_DEAL+"email="+str_email+"&token="+str_token);

        new Agent_Async(Agent_Best_Deal_Fragment.this,
                UrlEndpoints.GET_BEST_DEAL+"email="+str_email+"&token="+str_token).execute();
        picasso = Picasso.with(getContext());
*/

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
