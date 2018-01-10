package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.Agent_Profile_Activity;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import active.Quiz_Save_Result_Activity;
import active.Result_Pie_Quiz_Activity;
import callbacks.Pie_Chart_data_Set;
import utilities.App_Static_Method;
import utilities.UrlEndpoints;

/**
 * Created by GT on 8/12/2017.
 */

public class Agent_Progress_Fragment extends Fragment implements Pie_Chart_data_Set {

    Activity context;
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context=context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frg_agent_progress, container, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.URL_AGENT_PROGRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try {
                            Log.d("quiz_open_responce",""+str_response);
                            JSONObject response = new JSONObject(str_response);

                                  //  JSONArray jsonArray=jsonObject.getJSONArray("data");

                                    Bundle bundle=new Bundle();
                                    bundle.putString("jobj",""+response);
                                    Log.d("kck_nk",""+response.toString());
                                    Progress_Frg progress_frg= new Progress_Frg();
                                    progress_frg.setArguments(bundle);
                                    FragmentManager fragmentManager=getFragmentManager();
                                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.id_frg_press,progress_frg).commit();

                        } catch (Exception e) {
                            Log.d("kcknk",""+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Log.d("ncnmxn",""+App_Static_Method.session_type());
            return App_Static_Method.session_type();

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }



    public void on_piechart_set_data(Activity quiz_calculation_activity, JSONObject  jsonObject) {


    }

    @Override
    public void on_piechart_set_data(Quiz_Save_Result_Activity quiz_calculation_activity, int int_skip_ques, int int_wng_ques, int int_rgt_ques) {

    }
}
