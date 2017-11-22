package fragment;

import android.app.Activity;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import adapter.Agent_Account_Adapter;
import task.Asynch_Obj;
import utilities.UrlEndpoints;

import static utilities.App_Static_Method.session_type;

/**
 * Created by GT on 11/20/2017.
 */

public class Partner_Account_Frg extends Fragment {
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

        new Asynch_Obj(new Asynch_Obj.OBJ_Lister() {
            @Override
            public void on_lis_obj(JSONObject jsonObject, String str_key) {
                try {

                Log.d("jsonObject",""+jsonObject.getInt("msg"));

                if(jsonObject.getInt("msg")!=0) {
                    mAdapter = new Agent_Account_Adapter(Partner_Account_Frg.this.getContext(), jsonObject);

                    LinearLayoutManager mLayoutManager = new LinearLayoutManager((Activity) getContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }
                } catch (JSONException e) {
                    Log.d("jsonObject",e.getMessage());
                }

            }
        }, UrlEndpoints.SEAT_PROVIDER_ACCOUNT, session_type(), "social").execute();


        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
