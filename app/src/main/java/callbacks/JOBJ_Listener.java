package callbacks;

import org.json.JSONObject;

import java.util.List;

import utilities.Common_Pojo;

/**
 * Created by GT on 10/25/2017.
 */

public interface JOBJ_Listener  {
    public void onLJsonLoaded(JSONObject jsonObject);
    public void onLJsonLoaded_new(JSONObject jsonObject);
}
