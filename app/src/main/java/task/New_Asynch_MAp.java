package task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.Map;

import network.VolleySingleton;
import utilities.App_Static_Method;
import utilities.List_Utils;

/**
 * Created by GT on 12/5/2017.
 */

public class New_Asynch_MAp extends AsyncTask<Void, Void,JSONObject> {
    private JOBJ_LISTENER myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    private String url;
    private String loginApi;
    private Context context;
    Map<String,String> map;



    public New_Asynch_MAp(JOBJ_LISTENER myComponent, Map<String,String> map, String str_url, String loginApi) {
        Log.d("urlrft",""+str_url);
        Log.d("url___map",""+map.toString());
       // Log.d("url___json",""+ new JSONObject(map));
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
