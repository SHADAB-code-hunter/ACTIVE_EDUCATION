package callbacks;

import java.util.List;

import pojo.Cat_Model;
import utilities.Common_Pojo;

/**
 * Created by GT on 7/27/2017.
 */

public interface Filter_List_Listener {
    public void on_Filter_Loaded(List<Cat_Model> listMovies);
    public void on_Filter_pager(List<Common_Pojo> listMovies, String s);
}
