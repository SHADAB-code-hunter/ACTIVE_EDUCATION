package utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.Map;

import network.VolleySingleton;

/**
 * Created by GT on 11/27/2017.
 */

public class NEW_ASYNCH extends AsyncTask<Void, Void,JSONObject> {
    private JOBJ_LISTENER myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private Map<String, String> map;
    private String url;
    private String loginApi;
    private Context context;


    public NEW_ASYNCH(JOBJ_LISTENER myComponent, Map<String, String> map, String str_url, String loginApi) {
        Log.d("urlrft",""+str_url);
        Log.d("url___map",""+map.toString());
        this.url=str_url;
        this.loginApi=loginApi;
        this.myComponent =myComponent;
        this.map=map;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject jsonobject= List_Utils.load_json(requestQueue,url,map);
        return jsonobject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonobject) {
        if (myComponent != null) {
            myComponent.on_listener(jsonobject, loginApi);
        }
    }

   public  interface JOBJ_LISTENER
    {
        public void on_listener(JSONObject jsonobject, String loginApi);
    }
}
