package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import utilities.Common_Pojo;

/**
 * Created by GT on 8/18/2017.
 */

public class Spinner_Adapter extends BaseAdapter implements Filterable {
    List<Common_Pojo> mList = new ArrayList<>();
    Picasso picasso;
    Activity _activity;
    ArrayList<String> arrayList;
    Context context;


    public Spinner_Adapter(Context context, ArrayList<String> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.spinner_adapter, parent, false);

        TextView tv_title = (TextView) rowView.findViewById(R.id.tv_menu_name);
        tv_title.setText(arrayList.get(position));

        return rowView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
