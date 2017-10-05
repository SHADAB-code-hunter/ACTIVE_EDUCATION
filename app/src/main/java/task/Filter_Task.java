package task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.List;

import callbacks.Filter_List_Listener;
import network.VolleySingleton;
import pojo.Cat_Model;
import utilities.List_Utils;

/**
 * Created by GT on 7/27/2017.
 */

public class Filter_Task extends AsyncTask<Void, Void, List<Cat_Model>> {
    private Filter_List_Listener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String filter_url;


    public Filter_Task(Filter_List_Listener myComponent,String filter_url) {

        this.filter_url=filter_url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected List<Cat_Model> doInBackground(Void... params) {
        List<Cat_Model> list= List_Utils.filter_load_Cat_List(requestQueue,filter_url);
        return list;
    }

    @Override
    protected void onPostExecute(List<Cat_Model> listMovies) {
        if (myComponent != null) {
            myComponent.on_Filter_Loaded(listMovies);
        }
    }
}
