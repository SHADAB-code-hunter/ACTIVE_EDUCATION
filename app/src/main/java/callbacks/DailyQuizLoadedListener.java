package callbacks;

import java.util.ArrayList;

import pojo.Quiz_Model;

/**
 * Created by GT on 4/5/2017.
 */

public interface DailyQuizLoadedListener {
    public void onDailyQuizLoaded(ArrayList<Quiz_Model> listDailyQuiz);
    public void onDailyQuizLoaded(ArrayList<Quiz_Model> listDailyQuiz, String str_quiz_time);
}
