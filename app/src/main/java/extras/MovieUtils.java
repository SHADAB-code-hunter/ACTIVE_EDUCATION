package extras;

import android.util.Log;

import com.android.volley.RequestQueue;
import org.json.JSONObject;
import java.util.ArrayList;
import json.Endpoints;
import json.Parser;
import json.Requestor;
import pojo.Movie;
import pojo.Notif;
import utilities.DBMovies;
import utilities.MyApplication;

/**
 * Created by Windows on 02-03-2015.
 */
public class MovieUtils {
    public static ArrayList<Notif> loadBoxOfficeMovies(RequestQueue requestQueue) {
      //  JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlBoxOfficeMovies(30));

        Log.d("moview_utils","call");
      //  ArrayList<Notif> listMovies =Parser.parse_Notif_JSON(response);// Parser.parse_List_JSON(response);
       // Log.d("list_notif",""+listMovies);
       // MyApplication.getWritableDatabase().insertMovies(DBMovies.BOX_OFFICE, listMovies, true);
        return null;
    }

    public static ArrayList<Movie> loadUpcomingMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlUpcomingMovies(30));
       // ArrayList<Movie> listMovies = Parser.parse_List_JSON(response);
      //  MyApplication.getWritableDatabase().insertMovies(DBMovies.UPCOMING, listMovies, true);
        return null;
    }
}
