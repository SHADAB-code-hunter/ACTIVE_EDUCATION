package com.gt.active_education;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.List;

import callbacks.Upcoming_List_LoadedListener;
import network.VolleySingleton;
import utilities.Common_Pojo;
import utilities.List_Utils;

/**
 * Created by GT on 8/23/2017.
 */

public class Load_College_Desc_detail extends AsyncTask<Void, Void, List<Common_Pojo>> {
    private Upcoming_List_LoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;

    public Load_College_Desc_detail(Upcoming_List_LoadedListener myComponent,String url) {
        this.url=url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected List<Common_Pojo> doInBackground(Void... params) {
        List<Common_Pojo> list= List_Utils.load_common_pojo(requestQueue,url,"data");
        return list;
    }

    @Override
    protected void onPostExecute(List<Common_Pojo> listMovies) {

        if (myComponent != null) {
            myComponent.onUpcomingcourses(listMovies);
        }
    }
}
