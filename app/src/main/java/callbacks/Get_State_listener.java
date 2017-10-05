package callbacks;

import java.util.ArrayList;
import java.util.List;

import pojo.Gallery_Model;
import utilities.Common_Pojo;

/**
 * Created by GT on 8/18/2017.
 */

public interface Get_State_listener {
    public void onListLoaded(List<Common_Pojo> list);
    public void onCity_ListLoaded(List<Common_Pojo> list);
}
