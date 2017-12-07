package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import callbacks.DailyQuizLoadedListener;
import network.VolleySingleton;
import pojo.Quiz_Model;
import utilities.MovieUtils;

/**
 * Created by GT on 4/5/2017.
 */

public class TaskLoadDailyQuiz extends AsyncTask<Void, Void, ArrayList<Quiz_Model>> {
    private DailyQuizLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<Quiz_Model> listDailyQuiz;
    private String str_mobile,str_token;
    private String str_quiz_time=null;


    public TaskLoadDailyQuiz(DailyQuizLoadedListener myComponent) {

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }
    public TaskLoadDailyQuiz(DailyQuizLoadedListener myComponent, String str_mobile, String str_token, String str_quiz_time ) {
        this.str_mobile=str_mobile;
        this.str_token=str_token;
        this.str_quiz_time=str_quiz_time;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Quiz_Model> doInBackground(Void... params) {
        ArrayList<Quiz_Model> listDailyQuiz=null;

        if(str_mobile==null||str_token==null) {
            Log.d("hgdfhgf","fhjf___ghjfhvbjhb");

            listDailyQuiz = MovieUtils.loadQuizModel(requestQueue);
        }
        else if(!(str_mobile==null||str_token==null)) {
            Log.d("hgdfhgf","fhjf___grgrg_ghjfhvbjhb");
            listDailyQuiz = MovieUtils.loadValid_Token_QuizModel(requestQueue,str_mobile,str_token);
        }
        return listDailyQuiz;
    }

    @Override
    protected void onPostExecute(ArrayList<Quiz_Model> listDailyQuiz) {
        if (myComponent != null) {
            if(!(str_quiz_time==null)) {
                Log.d("hgdfllhgf","fhjf___hghghghg_   ghjfhvbjhb");
                myComponent.onDailyQuizLoaded(listDailyQuiz, str_quiz_time);
                this.listDailyQuiz=listDailyQuiz;
            }else if(str_quiz_time==null) {
                Log.d("hgdfh__gf","fhjf___hghghghg_   ghjfhvbjhb");
                this.listDailyQuiz = listDailyQuiz;
                myComponent.onDailyQuizLoaded(listDailyQuiz);
            }
        }
    }

}
