package fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gt.active_education.R;

import utilities.BatteryProgressView;


/**
 * Created by GT on 10/10/2017.
 */

public class Target_fragment extends Fragment {
    private BatteryProgressView progress1,progress2,progress3;
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

        View rootView = inflater.inflate(R.layout.frg_partner_target, container, false);
        progress1= (BatteryProgressView)rootView. findViewById(R.id.progress1);
        progress2= (BatteryProgressView)rootView. findViewById(R.id.progress2);
        progress3= (BatteryProgressView)rootView. findViewById(R.id.progress3);

        set_progress(progress1,R.color.circle_progress_color1);
        set_progress(progress2,R.color.circle_progress_color2);
        set_progress(progress3,R.color.circle_progress_color3);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }
    private void set_progress(final BatteryProgressView progress, final int circle_progress_color1) {
        final Handler handler;
        progress.setProgress(66,circle_progress_color1);//progress.setProgressColor(circle_progress_color1);
        handler=new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setProgress(63,circle_progress_color1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.setProgress(77, circle_progress_color1);

                    }
                },1000);
            }
        },2000);
    }

}
