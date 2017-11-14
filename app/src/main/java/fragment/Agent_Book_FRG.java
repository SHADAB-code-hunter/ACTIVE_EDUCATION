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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import adapter.Adapter_Book;
import adapter.Agent_Deal_Adapter;
import callbacks.Agent_deal_Listener;
import callbacks.JOBJ_Listener;
import pojo.Agent_Deal_Pojo;
import task.Agent_Async;
import task.Asynch_Book_Responce;
import utilities.MyApplication;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

/**
 * Created by GT on 11/6/2017.
 */

public class Agent_Book_FRG extends Fragment implements Agent_deal_Listener , JOBJ_Listener {
    Adapter_Book adapter_book;
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

        View rootView = inflater.inflate(R.layout.frg_agent_book, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        //rootView.findViewById(R.id.id_heading).setVisibility(View.GONE);
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_PARTNER_Prefrence,0);
        String str_token=sharedPreferences.getString("token", "na");
        String str_email=sharedPreferences.getString("mobile", "na");

        Log.d("uel", UrlEndpoints.GET_BOOK_OFFER+"mobile="+str_email+"&token="+str_token);

        new Asynch_Book_Responce(Agent_Book_FRG.this, UrlEndpoints.GET_BOOK_OFFER+"mobile="+str_email+"&token="+str_token).execute();
        picasso = Picasso.with(getContext());

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void on_deal_call(List<Agent_Deal_Pojo> listMovies, String status_call) {
        // Log.d("kist",""+listMovies.toString());
        //  if (listMovies.isEmpty()) {
      /*  adapter_book = new Adapter_Book(listMovies, picasso, (Activity) getContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager((Activity) getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/
       /* }else {
            Toast.makeText(getContext(), "You Have No Deal", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onLJsonLoaded(JSONObject jsonObject) {

    }

    @Override
    public void onLJsonLoaded_new(JSONObject jsonObject) {
        Log.d("objwct_responce",jsonObject.toString());
//
       // mAdapter = new Agent_Deal_Adapter(jsonObject, picasso, (Activity) getContext());
        try {
        if(jsonObject.has("data")) {
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            adapter_book = new Adapter_Book(jsonArray, picasso, (Activity) getContext());
            LinearLayoutManager mLayoutManager = new LinearLayoutManager((Activity) getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter_book);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
