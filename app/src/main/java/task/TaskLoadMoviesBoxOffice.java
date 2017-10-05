package task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import callbacks.BoxOfficeMoviesLoadedListener;
import extras.MovieUtils;
import network.VolleySingleton;
import pojo.Movie;
import pojo.Notif;


/**
 * Created by Windows on 02-03-2015.
 */
public class TaskLoadMoviesBoxOffice extends AsyncTask<Void, Void, ArrayList<Notif>> {
    private BoxOfficeMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMoviesBoxOffice(BoxOfficeMoviesLoadedListener myComponent) {
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Notif> doInBackground(Void... params) {

        ArrayList<Notif> listMovies = MovieUtils.loadBoxOfficeMovies(requestQueue);
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Notif> listMovies) {
        if (myComponent != null) {
            myComponent.onBoxOfficeMoviesLoaded(listMovies);
        }
    }
}
