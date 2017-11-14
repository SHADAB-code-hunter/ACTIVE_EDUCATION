package task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import callbacks.Quiz_Subject_listener;

import network.VolleySingleton;
import pojo.Quiz_Subject_Model;
import utilities.MovieUtils;

/**
 * Created by GT on 4/15/2017.
 */

public class TaskQuizSubject_Async extends AsyncTask<Void, Void, ArrayList<Quiz_Subject_Model>> {
    private Quiz_Subject_listener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private Activity activity;
    private String login_token,l_username;


    public TaskQuizSubject_Async(Quiz_Subject_listener myComponent) {

        this.myComponent = myComponent;
        this.activity=(Activity)myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    public TaskQuizSubject_Async(Quiz_Subject_listener myComponent, String l_username, String login_token) {

        this.myComponent = myComponent;
        this.l_username=l_username;
        this.login_token=login_token;
        this.activity=(Activity)myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Quiz_Subject_Model> doInBackground(Void... params) {

        ArrayList<Quiz_Subject_Model> listMovies = MovieUtils.getQuiz_SubjectPending(requestQueue,activity,l_username,login_token);
        Log.d("Quiz_sub_model",""+listMovies);
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Quiz_Subject_Model> listMovies) {
        if (myComponent != null) {
            myComponent.onQuiz_Subject_Loaded(listMovies);
        }
    }


}
