package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gt.active_education.R;

/**
 * Created by GT on 12/6/2017.
 */

public class College_Fragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

             View rootView = inflater.inflate(R.layout.college_frg, container, false);

             return rootView;
    }
}
