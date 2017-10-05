package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.List;

import callbacks.Upcoming_List_LoadedListener;
import network.VolleySingleton;
import pojo.Cat_Model;
import utilities.List_Utils;
import utilities.UrlEndpoints;


/**
 * Created by Windows on 02-03-2015.
 */
public class TaskLoad_List extends AsyncTask<Void, Void, List<Cat_Model>> {
    private Upcoming_List_LoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;

    public TaskLoad_List(Upcoming_List_LoadedListener myComponent,String url) {

        this.url=url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

    }


    @Override
    protected List<Cat_Model> doInBackground(Void... params) {
        Log.d("urlrft",""+url);
        List<Cat_Model> list=null;
        if(isSubstring("getSearchResult",url))
           list = List_Utils.load_search(requestQueue, url);
        else
            list = List_Utils.load_Cat_List(requestQueue, url);
        return list;
    }

    @Override
    protected void onPostExecute(List<Cat_Model> listMovies) {
        if (myComponent != null) {

            if(isSubstring("getSearchResult",url))
            {
                Log.d("listmovie",listMovies.toString());
                myComponent.onUpcomingLoaded(listMovies);
                return;
            }else {

                myComponent.onUpcomingLoaded(listMovies, url);
            }
        }
    }
    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }
}
