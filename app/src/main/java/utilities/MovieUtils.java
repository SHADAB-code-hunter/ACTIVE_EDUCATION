package utilities;

import android.app.Activity;
import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import json.Endpoints;
import json.Parser;
import json.Requestor;
import pojo.Quiz_Model;
import pojo.Quiz_Pending_Model;
import pojo.Quiz_Subject_Model;
import pojo.Quiz_Subject_Ques_Model;


/**
 * Created by Windows on 02-03-2015.
 */
public class MovieUtils {


    public static ArrayList<Movie> loadFilter_BoxOfficeMovies(RequestQueue requestQueue, String URL) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getFilter_RequestUrlBoxOfficeMovies(URL));
        //  Log.d("responce_pojo",""+response.length());
        ArrayList<Movie> listMovies = Parser.parseMoviesJSON(response);//for parsing json data
        Log.d("inserting_request",""+listMovies.size());
//        MyApplication.getWritableDatabase().insertMovies(DBMovies.BOX_OFFICE, listMovies, true);
        return listMovies;
    }
    public static ArrayList<Movie> loadBoxOfficeMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlBoxOfficeMovies(30));
        //  Log.d("responce_pojo",""+response.length());
        ArrayList<Movie> listMovies = Parser.parseMoviesJSON(response);//for parsing json data
        Log.d("inserting_request",""+listMovies.size());
//        MyApplication.getWritableDatabase().insertMovies(DBMovies.BOX_OFFICE, listMovies, true);
        return listMovies;
    }

    public static ArrayList<Movie> loadUpcomingMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlUpcomingMovies(30));
        ArrayList<Movie> listMovies = Parser.parseMoviesJSON(response);
       // MyApplication.getWritableDatabase().insertMovies(DBMovies.UPCOMING, listMovies, true);
        return listMovies;
    }
    public static ArrayList<Quiz_Model> loadQuizModel(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlDailyQuiz(30));
        //  Log.d("responce_pojo",""+response.length());
        ArrayList<Quiz_Model> listMovies = Parser.parseQuizJSON(response);//for parsing json data
        Log.d("insert_request",""+listMovies.size());
       // MyApplication.getWritableDatabase().insertMovies(DBMovies.BOX_OFFICE, listMovies, true);
        return listMovies;
    }
    public static ArrayList<Quiz_Model> loadValid_Token_QuizModel(RequestQueue requestQueue, String str_mob, String str_token) {
        Log.d("hgdfhgf","fhjfghjfhvbjhb");
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlDailyQuiz(str_mob,str_token));
          Log.d("responce_dpojo",""+response.length());
        ArrayList<Quiz_Model> listMovies = Parser.parseQuizJSON(response);//for parsing json data
        Log.d("insert_requestsdc",""+listMovies.size());
        // MyApplication.getWritableDatabase().insertMovies(DBMovies.BOX_OFFICE, listMovies, true);
        return listMovies;
    }
    public static ArrayList<Quiz_Pending_Model> loadQuizPendingModel(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlDailyQuizPending(30));
        //  Log.d("responce_pojo",""+response.length());

        ArrayList<Quiz_Pending_Model> quiz_pending_list = Parser.parseQuizPendingJSON(response);//for parsing json data

        // MyApplication.getWritableDatabase().insertMovies(DBMovies.BOX_OFFICE, listMovies, true);
        return quiz_pending_list;
    }

    public static ArrayList<Quiz_Subject_Model> getQuiz_SubjectPending(RequestQueue requestQueue, Activity activity, String l_username, String str_token) {//
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getQuiz_SubjectPending(activity,l_username,str_token));

        ArrayList<Quiz_Subject_Model> quiz_pending_list = Parser.parseQuizSubjectJson(response);//for parsing json data
        Log.d("responce_pojooo",""+quiz_pending_list.size());
        return quiz_pending_list;
    }
    public static ArrayList<Quiz_Subject_Ques_Model> getQuiz_Subject_Question(RequestQueue requestQueue, String l_username, String str_token, String str_sub_id) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getQuiz_SubjectQuestion(l_username,str_token,str_sub_id));

        ArrayList<Quiz_Subject_Ques_Model> quiz_pending_list = Parser.parseQuizSubject_Question_Json(response);//for parsing json data
        Log.d("jsonres",""+response);
        return quiz_pending_list;
    }

}
