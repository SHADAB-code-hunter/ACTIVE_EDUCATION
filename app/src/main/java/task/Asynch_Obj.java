package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.Map;

import callbacks.JOBJ_Listener;
import fragment.Partner_Regisration_Frg;
import network.VolleySingleton;
import utilities.List_Utils;

/**
 * Created by GT on 11/17/2017.
 */

public class Asynch_Obj extends AsyncTask<Void, Void, JSONObject> {
    private OBJ_Lister myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String str_key,url;
    private Map<String,String> map;

    public Asynch_Obj(OBJ_Lister myComponent, String url, Map<String,String> map,String str_key) {
            Log.d("urlrft",""+url);
            this.str_key=str_key;
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
            myComponent.on_lis_obj(jsonObject,str_key);
        }
    }

    public interface OBJ_Lister {

        public void on_lis_obj(JSONObject jsonObject, String str_key);
    }
}
