package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.List;
import network.VolleySingleton;
import utilities.Common_Pojo;
import utilities.List_Utils;

/**
 * Created by GT on 10/28/2017.
 */

public class Load_Course_AVail_Data extends AsyncTask<Void, Void, List<Common_Pojo>> {
    private List_LoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String url;

    public Load_Course_AVail_Data(List_LoadedListener myComponent,String url) {
        this.url=url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        Log.d("jdhjh",url);
    }

    @Override
    protected List<Common_Pojo> doInBackground(Void... params) {
        List<Common_Pojo> list= List_Utils.load_common_course_pojo(requestQueue,url,"data");
        return list;
    }

    @Override
    protected void onPostExecute(List<Common_Pojo> listMovies) {

        if (myComponent != null) {
            myComponent.onavail_courses(listMovies);
        }
    }

    public interface List_LoadedListener {
      
        public void onavail_courses(List<Common_Pojo> common_pojos);
       
    }

}
