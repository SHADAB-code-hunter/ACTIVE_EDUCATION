package callbacks;

import java.util.ArrayList;

import pojo.Quiz_Subject_Ques_Model;


/**
 * Created by akashdeep on 16-Apr-17.
 */

public interface Quiz_Sub_uestion_Listener {
    public void onQuiz_Sub_Ques_Loaded(ArrayList<Quiz_Subject_Ques_Model> listMovies, String str_url);
}
