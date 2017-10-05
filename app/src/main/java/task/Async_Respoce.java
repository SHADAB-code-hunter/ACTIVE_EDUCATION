package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import callbacks.Responce_Obj_Lisatener;
import callbacks.Responce_Obj_Lisatener;
import network.VolleySingleton;
import pojo.Agent_Deal_Pojo;
import utilities.List_Utils;

/**
 * Created by GT on 9/27/2017.
 */

public class Async_Respoce extends AsyncTask<Void, Void, JSONObject> {
    private final Map<String, String> map;
    private Responce_Obj_Lisatener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;

    public Async_Respoce(Responce_Obj_Lisatener myComponent, String url, Map<String, String> map) {
        Log.d("urlrft",""+url);
        this.url=url;
        this.map=map;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject jsonObject= List_Utils.load_json(requestQueue,url,map);
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject listMovies) {
        if (myComponent != null) {
            myComponent.on_responce(listMovies);
        }
    }

    public  interface Responce_Obj_Lisatener {
        public void on_responce(JSONObject listMovies);

    }
}
