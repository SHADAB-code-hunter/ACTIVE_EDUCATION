package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.List;

import callbacks.CLG_DESC_Listener;
import network.VolleySingleton;
import pojo.Cat_Model;
import utilities.List_Utils;

/**
 * Created by GT on 8/21/2017.
 */

public class Load_College_Desc_Async extends AsyncTask<Void, Void, List<Cat_Model>> {
    private CLG_DESC_Listener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url,content2;

    public Load_College_Desc_Async(CLG_DESC_Listener myComponent,String url) {
        this.url=url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        String content = url;
        content2 = content.replace("null1", "2");
        Log.d("hhfhfh",""+content2);
    }

    @Override
    protected List<Cat_Model> doInBackground(Void... params) {
        List<Cat_Model> list= List_Utils.load_Clg_Detail(requestQueue,content2,"data");
        return list;
    }

    @Override
    protected void onPostExecute(List<Cat_Model> listMovies) {
        if (myComponent != null) {
            if(listMovies.size()>0) {
                myComponent.onDesc_Data(listMovies);
            }
        }
    }
}
