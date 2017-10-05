package tab_bar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.gt.active_education.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_test, container, false);

        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);

        String sentString=null;
        if(getArguments()!=null){
            sentString = getArguments().getString("url");
            view.findViewById(R.id.id_tv_fragment);
        }
        TextView textView=(TextView)view.findViewById(R.id.id_tv_fragment);
        textView.setText(sentString);
        return view;
    }

}
