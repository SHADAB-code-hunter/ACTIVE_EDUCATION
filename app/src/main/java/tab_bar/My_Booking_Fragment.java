package tab_bar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import adapter.MoviesAdapter;
import callbacks.Avail_Course_Listener;
import callbacks.Upcoming_List_LoadedListener;
import task.TaskLoad_List;
import pojo.Cat_Model;
import utilities.App_Static_Method;
import utilities.Common_Pojo;
import utilities.UrlEndpoints;

import static extras.Keys.KEY_USER_LOGIN.KEY_EMAIL;
import static extras.Keys.KEY_USER_LOGIN.KEY_TOKEN;

/**
 * Created by GT on 8/5/2017.
 */

public class My_Booking_Fragment extends Fragment implements Upcoming_List_LoadedListener , Avail_Course_Listener {

  private RecyclerView recyclerView;
    Picasso picasso;
    private  String str_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mybooking, container, false);
        picasso = Picasso.with(getContext());
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);

        String sentString=null;
        if(getArguments()!=null){
            sentString = getArguments().getString("url");
            view.findViewById(R.id.id_tv_fragment);}
        TextView textView=(TextView)view.findViewById(R.id.id_tv_fragment);
        // textView.setText(sentString);

        str_url=UrlEndpoints.GET_BOOKING_LIST+"email="+ App_Static_Method.get_session_data(KEY_EMAIL)
                +"&token="+ App_Static_Method.get_session_data(KEY_TOKEN);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        new TaskLoad_List((Upcoming_List_LoadedListener)My_Booking_Fragment.this, str_url).execute();
        picasso = Picasso.with(getContext());
        return view;
    }

    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies) {}

    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies, String poss) {
        MoviesAdapter mAdapter = new MoviesAdapter(listMovies, picasso,getActivity(),str_url);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onUpcomingcourses(List<Common_Pojo> common_pojos) {

    }

    @Override
    public void onUpcomingexams(List<Common_Pojo> common_pojos) {

    }

    @Override
    public void onAvailCourse(String str_id, String c_id, String course_id, String branch_id, String c_branch) {

    }
}
