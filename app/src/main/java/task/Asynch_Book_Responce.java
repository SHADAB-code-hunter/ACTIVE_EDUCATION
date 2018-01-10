package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.gt.active_education.DashBoard_Activity;

import org.json.JSONObject;

import java.util.Map;

import callbacks.JOBJ_Listener;
import network.VolleySingleton;
import utilities.List_Utils;

/**
 * Created by GT on 11/6/2017.
 */

public class Asynch_Book_Responce extends AsyncTask<Void, Void, JSONObject> {
    private JOBJ_Listener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;
    private Map<String,String> map;

    public Asynch_Book_Responce(JOBJ_Listener myComponent, String url) {
        Log.d("book_url",""+url);
        this.url=url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }
    public Asynch_Book_Responce(JOBJ_Listener myComponent, String url, Map<String, String> map) {
        Log.d("book_url",""+url);
        Log.d("book_uggrl",""+map);
        this.url=url;
        this.myComponent = myComponent;
        this.map = map;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject jsonObject=null;
        if(map!=null) {
             jsonObject = List_Utils.load_JSONOBJECT(requestQueue, url, map);
        }else{
            jsonObject = List_Utils.load_JSONOBJECT(requestQueue, url);
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (myComponent != null) {
            myComponent.onLJsonLoaded_new(jsonObject);
        }
    }
}
