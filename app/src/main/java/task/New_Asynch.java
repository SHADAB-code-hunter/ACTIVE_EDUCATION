package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.List;

import callbacks.Upcoming_List_LoadedListener;
import network.VolleySingleton;
import utilities.Common_Pojo;
import utilities.List_Utils;

/**
 * Created by GT on 11/26/2017.
 */

public class New_Asynch extends AsyncTask<Void, Void, JSONObject> {
    private OBJ_Lister myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;

    public New_Asynch(OBJ_Lister myComponent,String url) {
        this.url=url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        Log.d("hdgfhdgfhdgf","dhfjdhfj");
       JSONObject jsonObject= List_Utils.load_JSONOBJECT(requestQueue,url);
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject listMovies) {
        if (myComponent != null) {
            myComponent.on_lis_obj(listMovies);
        }
    }

    public interface OBJ_Lister {

        public void on_lis_obj(JSONObject jsonObject);
    }
}
