package adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gt.active_education.Item;
import com.gt.active_education.R;
import com.gt.active_education.SelectableItem;

import java.util.ArrayList;
import java.util.List;

import pojo.State_Pojo;
import utilities.Selectable_Item;

/**
 * Created by GT on 12/17/2017.
 */

public class Selectable_Adapter extends RecyclerView.Adapter implements Selectable_ViewHolder.OnItemSelectedListener {

    private final List<Selectable_Item> mValues;
    private boolean isMultiSelectionEnabled = false;
    Selectable_ViewHolder.OnItemSelectedListener listener;
    private String str_key;


    public Selectable_Adapter(Selectable_ViewHolder.OnItemSelectedListener listener, List<State_Pojo> items, boolean isMultiSelectionEnabled, String str_key) {
        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;
        this.str_key=str_key;

        mValues = new ArrayList<>();
        for (State_Pojo item : items) {
            Log.d("b__n_bn",""+item.getName());
            mValues.add(new Selectable_Item(item, false));
        }
    }

    @Override
    public Selectable_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_item, parent, false);

        return new Selectable_ViewHolder(itemView, this,str_key);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        Selectable_ViewHolder holder = (Selectable_ViewHolder) viewHolder;
        Selectable_Item selectableItem = mValues.get(position);
        String name = selectableItem.getName();
        Log.d("b__nbn",""+name);
        holder.textView.setText(name);
        if (isMultiSelectionEnabled) {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        } else {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        }

        holder.mItem = selectableItem;
        holder.setChecked(holder.mItem.isSelected());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public List<Selectable_Item> getSelectedItems() {

        List<Selectable_Item> selectedItems = new ArrayList<>();
        for (Selectable_Item item : mValues) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    @Override
    public int getItemViewType(int position) {
        if(isMultiSelectionEnabled){
            return Selectable_ViewHolder.MULTI_SELECTION;
        }
        else{
            return Selectable_ViewHolder.SINGLE_SELECTION;
        }
    }

    @Override
    public void onItemSelected(Selectable_Item item,String str_key) {
        if (!isMultiSelectionEnabled) {

            for (Selectable_Item selectableItem : mValues) {
                if (!selectableItem.equals(item)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(item)
                        && item.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onItemSelected(item,str_key);
    }
}