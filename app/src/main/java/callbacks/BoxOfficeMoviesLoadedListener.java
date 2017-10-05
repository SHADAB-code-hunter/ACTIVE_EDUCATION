package callbacks;

import java.util.ArrayList;

import pojo.Movie;
import pojo.Notif;

/**
 * Created by Windows on 02-03-2015.
 */
public interface BoxOfficeMoviesLoadedListener {
    public void onBoxOfficeMoviesLoaded(ArrayList<Notif> listMovies);
    public void on_id_listener(String str_id);

}
