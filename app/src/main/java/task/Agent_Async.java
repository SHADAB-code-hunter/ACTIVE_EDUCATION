package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import callbacks.Agent_deal_Listener;
import callbacks.Agent_deal_Listener;
import fragment.Agent_Target_Frg;
import network.VolleySingleton;
import pojo.Agent_Deal_Pojo;
import pojo.Agent_Deal_Pojo;
import utilities.List_Utils;

/**
 * Created by GT on 8/28/2017.
 */

public class Agent_Async extends AsyncTask<Void, Void, List<Agent_Deal_Pojo>> {
    private Agent_deal_Listener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;
    private Map<String, String> map;
    private ArrayList<Agent_Deal_Pojo> list;

    public Agent_Async(Agent_deal_Listener myComponent, String url, Map<String, String> map) {
        Log.d("urlrft",""+url);
        this.url=url;
        this.myComponent = myComponent;
        this.map = map;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }
    public Agent_Async(Agent_deal_Listener myComponent, String url) {
        Log.d("urlrft",""+url);
        this.url=url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected List<Agent_Deal_Pojo> doInBackground(Void... params) {
        if(map!=null)
            list= List_Utils.load_agent_deal_list(requestQueue,url,map);
        else {
            list= List_Utils.load_agent_deal_list(requestQueue,url);
        }


        return list;
    }

    @Override
    protected void onPostExecute(List<Agent_Deal_Pojo> listMovies) {
        if (myComponent != null) {
            myComponent.on_deal_call(listMovies,url);
        }
    }
}
