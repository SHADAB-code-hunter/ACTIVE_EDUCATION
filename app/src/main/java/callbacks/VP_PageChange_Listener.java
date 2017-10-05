package callbacks;

import java.util.ArrayList;

import pojo.Gallery_Model;

/**
 * Created by GT on 5/27/2017.
 */

public interface VP_PageChange_Listener {
    public void on_opt_next_Ques();
    public void on_opt_next_Ques(ArrayList<Gallery_Model> quiz_Ans_List, int poss);
}
