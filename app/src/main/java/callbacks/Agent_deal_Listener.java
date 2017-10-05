package callbacks;

import java.util.List;

import pojo.Agent_Deal_Pojo;
import pojo.Get_Course_desc;

/**
 * Created by GT on 8/28/2017.
 */

public interface Agent_deal_Listener {
    public void on_deal_call(List<Agent_Deal_Pojo> listMovies, String status_call);
}
