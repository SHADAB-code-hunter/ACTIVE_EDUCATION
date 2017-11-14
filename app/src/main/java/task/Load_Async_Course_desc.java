package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.List;

import callbacks.Choose_newCourses_Listener;
import network.VolleySingleton;
import pojo.Get_Course_desc;
import utilities.List_Utils;

/**
 * Created by GT on 8/21/2017.
 */

public class Load_Async_Course_desc extends AsyncTask<Void, Void, List<Get_Course_desc>> {

private VolleySingleton volleySingleton;
private RequestQueue requestQueue;
private String url;
private Choose_newCourses_Listener myComponent;
private String status_call;

public Load_Async_Course_desc(Choose_newCourses_Listener myComponent,String url,String status_call) {
        this.url=url;
        this.myComponent = myComponent;
        this.status_call=status_call;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        }

    @Override
    protected List<Get_Course_desc> doInBackground(Void... params) {
          Log.d("jdhjhdddcjhch",url);
            List<Get_Course_desc> list= List_Utils.get_course_descs(requestQueue,url);
            return list;}

    @Override
    protected void onPostExecute(List<Get_Course_desc> listMovies) {
            if (myComponent != null) {
             myComponent.on_choose_courses(listMovies,status_call);
            }
   }
}
