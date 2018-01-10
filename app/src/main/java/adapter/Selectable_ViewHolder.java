package adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;

import com.gt.active_education.R;
import com.gt.active_education.SelectableItem;

import utilities.Selectable_Item;

/**
 * Created by GT on 12/17/2017.
 */

public class Selectable_ViewHolder extends RecyclerView.ViewHolder {

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;
    CheckedTextView textView;
    Selectable_Item mItem;
    OnItemSelectedListener itemSelectedListener;
    String str_key;


    public Selectable_ViewHolder(View view, OnItemSelectedListener listener, final String str_key) {
        super(view);
        itemSelectedListener = listener;
        this.str_key=str_key;
        textView = (CheckedTextView) view.findViewById(R.id.checked_text_item);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mItem.isSelected() && getItemViewType() == MULTI_SELECTION) {
                    setChecked(false);
                } else {
                    setChecked(true);
                }
                itemSelectedListener.onItemSelected(mItem,str_key);

            }
        });
    }

    public void setChecked(boolean value) {
        if (value) {
            textView.setBackgroundColor(Color.LTGRAY);
        } else {
            textView.setBackground(null);
        }
        mItem.setSelected(value);
        textView.setChecked(value);
    }

    public interface OnItemSelectedListener {

        void onItemSelected(Selectable_Item item,String str_key);
    }

}