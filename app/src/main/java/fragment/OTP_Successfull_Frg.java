package fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gt.active_education.R;

import callbacks.SignUp_Pager_Swape_Listener;

/**
 * Created by GT on 7/8/2017.
 */

public class OTP_Successfull_Frg extends Fragment {

    protected static final int TIMER_RUNTIME = 2500;

    SignUp_Pager_Swape_Listener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (SignUp_Pager_Swape_Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ExampleFragmentCallbackInterface ");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.otp_successfull_frg_layout, container, false);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                mListener.onPager_swap_method("Login_page");
            }
        }, 2500);


        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
