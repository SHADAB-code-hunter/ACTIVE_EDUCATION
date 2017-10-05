package pojo;

/**
 * Created by GT on 7/27/2017.
 */

public class StateData
{
    private String id;
    private String state_name;
    private String is_active;

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    public void setIs_active(String is_active){
        this.is_active = is_active;
    }
    public String getIs_active(){
        return this.is_active;
    }
}

