package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.gt.active_education.Admission_Form_Activity;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import adapter.Agent_Account_Adapter;
import adapter.Agent_Target_Adapter;
import callbacks.Agent_deal_Listener;
import pojo.Agent_Deal_Pojo;
import task.Agent_Async;
import utilities.MyApplication;
import utilities.RecyclerTouchListener;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

/**
 * Created by GT on 10/23/2017.
 */

public class Agent_Account_Frg extends Fragment {
    Agent_Account_Adapter mAdapter;
    Picasso picasso;
    RecyclerView recyclerView;
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

        View rootView = inflater.inflate(R.layout.frg_agent_account, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        mAdapter = new Agent_Account_Adapter(Agent_Account_Frg.this.getContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager((Activity) getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
