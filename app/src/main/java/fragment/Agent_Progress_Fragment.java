package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import Fab_Filter.MoviesAdapter;

/**
 * Created by GT on 8/12/2017.
 */

public class Agent_Progress_Fragment extends Fragment {
    MoviesAdapter mAdapter;
    Picasso picasso;
    RecyclerView recyclerView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frg_agent_progress, container, false);
        /*recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setScrollingTouchSlop();*/
        //recyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
