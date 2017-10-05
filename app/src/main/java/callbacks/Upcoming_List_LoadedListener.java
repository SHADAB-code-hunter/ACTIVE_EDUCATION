package callbacks;

import java.util.List;

import pojo.Cat_Model;
import utilities.Common_Pojo;


/**
 * Created by Windows on 13-04-2015.
 */
public interface Upcoming_List_LoadedListener {
    public void onUpcomingLoaded(List<Cat_Model> listMovies);
    public void onUpcomingLoaded(List<Cat_Model> listMovies,String poss);
    public void onUpcomingcourses(List<Common_Pojo> common_pojos);
    public void onUpcomingexams(List<Common_Pojo> common_pojos);
}
