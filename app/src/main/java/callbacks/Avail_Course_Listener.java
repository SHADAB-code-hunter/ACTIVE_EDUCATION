package callbacks;

import java.util.ArrayList;

import pojo.Notif;

/**
 * Created by GT on 10/30/2017.
 */

public interface Avail_Course_Listener {
    public void onAvailCourse(String str_id, String c_id, String course_id, String branch_id, String c_branch);
}
