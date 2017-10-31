package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gt.active_education.R;

import java.util.ArrayList;
import java.util.List;
import callbacks.Call_Dilaog_Listener;
import utilities.Available_Courses_Dialog;
import utilities.Common_Pojo;
import utilities.State_City_Search;

public class Simple_Adapter extends RecyclerView.Adapter<Simple_Adapter.MyViewHolder> {

    private List<String> list_item ;
    public Call_Dilaog_Listener listener;
    public List<String> arrayList_id;
    private List<Common_Pojo> common_pojos;
    private Available_Courses_Dialog available_courses_dialog;

    public Simple_Adapter(List<String> list, List<String> arrayList_id, Call_Dilaog_Listener listener) {

        this.list_item = list;
        this.listener = listener;
        this.arrayList_id=arrayList_id;
    }

    public Simple_Adapter(List<Common_Pojo> common_pojos, Call_Dilaog_Listener listener) {
        this.common_pojos = common_pojos;
        this.listener = listener;
    }

    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public Simple_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_search, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position ) {

        Log.d("ncmn"," "+common_pojos.get(position).getName());
        viewHolder.country_name.setText(common_pojos.get(position).getName());
       /* viewHolder.country_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.on_id_listener(list_item.get(position));
//                Toast.makeText(mcontext.getContext(), list_item.get(position),
//                        Toast.LENGTH_LONG).show();
            }
        });*/

//        viewHolder.id_frst_letter.setText(String.valueOf((list_item.get(position).toString()).charAt(0)));
        viewHolder.id_rlative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.on_id_listener(common_pojos.get(position).getId(),common_pojos.get(position).getName());
            }
        });
      //  viewHolder.id_frame_Layout.setBackgroundColor(App_Raw_Data.getMatColor(position,"mdcolor_A100"));

     /* StateListDrawable drawable = (StateListDrawable) viewHolder.id_frame_Layout.getBackground();
        GradientDrawable bgShape = (GradientDrawable) drawable.getCurrent();
        bgShape.setColor(App_Raw_Data.getMatColor(position,"mdcolor_A700"));*/
    }

    // initializes some private fields to be used by RecyclerView.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView country_name,id_frst_letter;
        private FrameLayout id_frame_Layout;
        private RelativeLayout id_rlative;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            country_name = (TextView) itemLayoutView.findViewById(R.id.country_name);
            id_frst_letter=(TextView)itemLayoutView.findViewById(R.id.id_frst_letter);
            id_frame_Layout=(FrameLayout)itemLayoutView.findViewById(R.id.id_frame_Layout);
            id_rlative=(RelativeLayout)itemLayoutView.findViewById(R.id.id_rlative);

        }
    }

    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        if(common_pojos!=null){
        return common_pojos.size();}
        else if(list_item!=null){
        return list_item.size();}
        return 0;
    }

}