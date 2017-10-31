package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.Map;

import callbacks.JOBJ_Listener;
import network.VolleySingleton;
import utilities.List_Utils;

/**
 * Created by GT on 10/25/2017.
 */

public class Asynch_Agent_Form_JObject extends AsyncTask<Void, Void, JSONObject> {
    private JOBJ_Listener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;
    private Map<String,String> map;

    public Asynch_Agent_Form_JObject(JOBJ_Listener myComponent, String url, Map<String,String> map) {
        Log.d("urlrft",""+url);
        this.url=url;
        this.myComponent = myComponent;
        this.map=map;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        Log.d("map_string",map.toString());
    }


    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject jsonObject= List_Utils.load_JSONOBJECT(requestQueue,url,map);
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (myComponent != null) {
            myComponent.onLJsonLoaded_new(jsonObject);
        }
    }
}
