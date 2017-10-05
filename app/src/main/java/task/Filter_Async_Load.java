package task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.List;

import callbacks.Filter_List_Listener;
import network.VolleySingleton;

import utilities.Common_Pojo;
import utilities.List_Utils;

/**
 * Created by GT on 9/15/2017.
 */

public class Filter_Async_Load extends AsyncTask<Void, Void, List<Common_Pojo>> {
    private Filter_List_Listener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String filter_url,s;



    public Filter_Async_Load(Filter_List_Listener myComponent, String filter_url, String s) {

        this.filter_url=filter_url;
        this.myComponent = myComponent;
        this.s=s;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected List<Common_Pojo> doInBackground(Void... params) {
        List<Common_Pojo> list= List_Utils.load_pager_filter(requestQueue,filter_url,s);
        return list;
    }

    @Override
    protected void onPostExecute(List<Common_Pojo> listMovies) {
        if (myComponent != null) {
            myComponent.on_Filter_pager(listMovies,s);
        }
    }
}
