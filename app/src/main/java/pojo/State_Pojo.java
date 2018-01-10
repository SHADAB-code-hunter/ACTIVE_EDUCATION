package pojo;

import com.gt.active_education.Item;

/**
 * Created by GT on 12/18/2017.
 */

public class State_Pojo {

    private String id;
    private String name;
    public State_Pojo(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        State_Pojo itemCompare = (State_Pojo) obj;
        if(itemCompare.getName().equals(this.getName()))
            return true;

        return false;
    }

}
