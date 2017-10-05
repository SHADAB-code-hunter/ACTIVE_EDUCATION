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

import adapter.Agent_Deal_Adapter;
import adapter.Agent_Target_Adapter;
import callbacks.Agent_deal_Listener;
import pojo.Agent_Deal_Pojo;
import task.Agent_Async;
import utilities.MyApplication;
import utilities.RecyclerTouchListener;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

/**
 * Created by GT on 8/28/2017.
 */

public class Agent_Target_Frg extends Fragment implements Agent_deal_Listener {
    Agent_Target_Adapter mAdapter;
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

        View rootView = inflater.inflate(R.layout.frg_best_deal, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);

        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence,0);
        String str_token=sharedPreferences.getString("Login_Token", "na");
        String str_email=sharedPreferences.getString("email", "na");
        /*recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setScrollingTouchSlop();*/

        Log.d("uel", UrlEndpoints.GET_BEST_DEAL+"email="+str_email+"&token="+str_token);

        new Agent_Async(Agent_Target_Frg.this,
                UrlEndpoints.GET_AGENT_TARGET+"email="+str_email+"&token="+str_token).execute();
        picasso = Picasso.with(getContext());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Intent i=new Intent(getContext(),Admission_Form_Activity.class);
                       /* String url=UrlEndpoints.URL_DEAL_CAT_MAIN+(position+1);
                        i.putExtra("url",""+url);*/
                        startActivity(i);

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void on_deal_call(List<Agent_Deal_Pojo> listMovies, String status_call) {
       //   Log.d("kist",""+listMovies.toString());
        if(!listMovies.isEmpty()) {
            mAdapter = new Agent_Target_Adapter(listMovies, picasso, (Activity) getContext());
            LinearLayoutManager mLayoutManager = new LinearLayoutManager((Activity) getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);


        }else {
            Toast.makeText(getContext(), "You have no Target", Toast.LENGTH_SHORT).show();
        }

    }
}
