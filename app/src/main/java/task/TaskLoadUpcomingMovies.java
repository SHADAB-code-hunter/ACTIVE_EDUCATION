package task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import callbacks.Upcoming_List_LoadedListener;
import extras.MovieUtils;
import network.VolleySingleton;
import pojo.Movie;

/**
 * Created by Windows on 02-03-2015.
 */
public class TaskLoadUpcomingMovies extends AsyncTask<Void, Void, ArrayList<Movie>> {
    private Upcoming_List_LoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadUpcomingMovies(Upcoming_List_LoadedListener myComponent) {

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {

        ArrayList<Movie> listMovies = MovieUtils.loadUpcomingMovies(requestQueue);
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> listMovies) {
        if (myComponent != null) {
          //  myComponent.onUpcomingMoviesLoaded(listMovies);
        }
    }
}
