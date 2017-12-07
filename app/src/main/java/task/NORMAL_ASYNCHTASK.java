package task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import network.VolleySingleton;
import utilities.List_Utils;

/**
 * Created by GT on 12/4/2017.
 */

public class NORMAL_ASYNCHTASK extends AsyncTask<Void, Void,JSONObject> {
    private NORMAL_ASYNCHTASK.JOBJ_LISTENER myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String map;
    private String url;
    private String loginApi;
    private Context context;


    public NORMAL_ASYNCHTASK(NORMAL_ASYNCHTASK.JOBJ_LISTENER myComponent, String  map, String str_url, String loginApi) {
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
        JSONObject jsonobject= List_Utils.load_json_header(requestQueue,url);
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
