package task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import active.Quiz_SubList_Activity;
import callbacks.Quiz_Sub_uestion_Listener;
import network.VolleySingleton;
import pojo.Quiz_Subject_Ques_Model;
import utilities.MovieUtils;

/**
 * Created by akashdeep on 16-Apr-17.
 */

public class TaskLoad_Sub_Quiz extends AsyncTask<Void, Void, ArrayList<Quiz_Subject_Ques_Model>> {
    private Quiz_Sub_uestion_Listener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<Quiz_Subject_Ques_Model> listDailyQuiz;
    private String str_url;
    private Quiz_SubList_Activity quiz_subList_activity;
    private String l_username,login_token,str_sub_id;

    public TaskLoad_Sub_Quiz(Quiz_Sub_uestion_Listener myComponent, String str_url) {
        this.str_url=str_url;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    public TaskLoad_Sub_Quiz(Quiz_SubList_Activity myComponent, String l_username, String login_token, String str_sub_id) {
        //this.quiz_subList_activity=quiz_subList_activity;
        this.l_username=l_username;
        this.login_token=login_token;
        this.str_sub_id=str_sub_id;
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Quiz_Subject_Ques_Model> doInBackground(Void... params) {

        ArrayList<Quiz_Subject_Ques_Model> listDailyQuiz = MovieUtils.getQuiz_Subject_Question(requestQueue,l_username,login_token,str_sub_id);
        Log.d("sizelist", ""+listDailyQuiz);

        return listDailyQuiz;
    }

    @Override
    protected void onPostExecute(ArrayList<Quiz_Subject_Ques_Model> listDailyQuiz) {
        if (myComponent != null) {
            myComponent.onQuiz_Sub_Ques_Loaded(listDailyQuiz,str_url);
            this.listDailyQuiz=listDailyQuiz;
        }
    }

}
