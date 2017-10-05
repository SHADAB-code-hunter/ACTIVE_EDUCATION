package callbacks;

import java.util.List;

import pojo.Get_Course_desc;

/**
 * Created by GT on 8/21/2017.
 */

public interface Choose_newCourses_Listener {

    public void on_choose_courses(List<Get_Course_desc> listMovies, String status_call);
}
