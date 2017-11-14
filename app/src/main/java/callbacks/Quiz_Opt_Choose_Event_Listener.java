package callbacks;

import pojo.Quiz_Answer_Model;

/**
 * Created by GT on 4/11/2017.
 */

public interface Quiz_Opt_Choose_Event_Listener {
    public void onOption_Choosen(Quiz_Answer_Model quizAnswerModel);
    public void onOption_Choosen(int postion, String str_ans, String q_id);
}
