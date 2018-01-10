package utilities;

import com.gt.active_education.Item;

import pojo.State_Pojo;

/**
 * Created by GT on 12/17/2017.
 */

public class Selectable_Item extends State_Pojo{
    private boolean isSelected = false;


    public Selectable_Item(State_Pojo item, boolean isSelected) {
        super(item.getId(),item.getName());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}