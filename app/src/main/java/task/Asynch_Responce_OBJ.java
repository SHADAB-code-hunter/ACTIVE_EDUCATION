package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.Map;


import network.VolleySingleton;
import utilities.List_Utils;

/**
 * Created by GT on 11/11/2017.
 */

public class Asynch_Responce_OBJ extends AsyncTask<Void, Void, JSONObject> {
    private OBJ_LISTENER myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;
    private Map<String,String> map;

    public Asynch_Responce_OBJ(OBJ_LISTENER myComponent, String url ,Map<String, String> map)
    {
        Log.d("book_url",""+url);
        Log.d("semap",""+map);
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
            myComponent.obn_obj_find(jsonObject);
        }
    }

    public interface OBJ_LISTENER
    {
        public void obn_obj_find(JSONObject jsonObject);

    }
}
