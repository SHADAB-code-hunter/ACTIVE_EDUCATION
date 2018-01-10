package task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.Map;

import callbacks.JsonRes_Listener;
import network.VolleySingleton;
import utilities.List_Utils;

/**
 * Created by GT on 10/9/2017.
 */

public class Login_Asynch extends AsyncTask<Void, Void,JSONObject> {
    private JsonRes_Listener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private Map<String, String> map;
    private String url;
    private String loginApi;
    private Context context;


    public Login_Asynch(JsonRes_Listener myComponent, Map<String, String> map, String str_url, String loginApi) {
        Log.d("urlrft",""+str_url);
        Log.d("url_map",""+map.toString());
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
            myComponent.on_res_litsner(jsonobject, loginApi);
        }
    }
}
