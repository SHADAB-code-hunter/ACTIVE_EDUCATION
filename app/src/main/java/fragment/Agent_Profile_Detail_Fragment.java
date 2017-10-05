package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gt.active_education.R;


/**
 * Created by GT on 8/12/2017.
 */

public class Agent_Profile_Detail_Fragment extends Fragment {

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

        View rootView = inflater.inflate(R.layout.frg_agent_profile, container, false);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
