package fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.R;
import com.gt.active_education.Sign_Up_Process_Activity;

import callbacks.SignUp_Pager_Swape_Listener;

/**
 * Created by GT on 7/8/2017.
 */

public class SignUp_Successfull_Frg extends Fragment {

    SignUp_Pager_Swape_Listener mListener;
    Sign_Up_Process_Activity signUpProcessActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (SignUp_Pager_Swape_Listener) context;
            signUpProcessActivity= (Sign_Up_Process_Activity)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ExampleFragmentCallbackInterface ");
        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

   /* @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                startActivity(new Intent(getContext(), DashBoard_Activity.class));
            }
        }, 2500);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.signup_success_frg_layout, container, false);

        ImageView button_check=(ImageView)rootView.findViewById(R.id.button_check_btn);
        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DashBoard_Activity.class));
                signUpProcessActivity.finish();
            }
        });

        return rootView;
    }


}
