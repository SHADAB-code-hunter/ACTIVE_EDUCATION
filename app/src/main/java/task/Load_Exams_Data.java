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
 * Created by GT on 8/17/2017.
 */

public class Load_Exams_Data extends AsyncTask<Void, Void, List<Common_Pojo>> {
    private Upcoming_List_LoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;

    public Load_Exams_Data(Upcoming_List_LoadedListener myComponent,String url) {
        this.url=url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected List<Common_Pojo> doInBackground(Void... params) {
        Log.d("hdgfhdgfhdgf","dhfjdhfj");
        List<Common_Pojo> list= List_Utils.load_common_pojo(requestQueue,url,"data");
        return list;
    }

    @Override
    protected void onPostExecute(List<Common_Pojo> listMovies) {
        if (myComponent != null) {
            myComponent.onUpcomingexams(listMovies);
        }
    }

    public interface OBJ_Lister {

        public void on_lis_obj(JSONObject jsonObject, String str_key);
    }
}
