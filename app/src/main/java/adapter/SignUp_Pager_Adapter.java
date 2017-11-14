package adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import fragment.Agent_Progress_Fragment;
import fragment.Agent_Registered_frg;
import fragment.Education_Form_Fragment;
import fragment.Num_Verify_Fragment;
import fragment.OTP_Successfull_Frg;
import fragment.OTP_Verification_Frg;
import fragment.Personal_Fragment;
import fragment.SignUp_Fragment;
import fragment.SignUp_Successfull_Frg;

/**
 * Created by GT on 7/8/2017.
 */

public class SignUp_Pager_Adapter extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 3;
    private Activity activity;
    private String str_status;

    public SignUp_Pager_Adapter(FragmentManager FragmentManager, Activity activity,String str_status) {
        super(FragmentManager);
        this.str_status=str_status;
        this.activity=activity;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment=new Num_Verify_Fragment();
                break;

            case 1:

                fragment=new OTP_Verification_Frg();
                break;

            case 2:

               /* if (str_status.equals("User")) {
                    Log.d("fgr",""+str_status);*/
                    fragment = new SignUp_Fragment();
               /* }else if(str_status.equals("Agent")) {
                    Log.d("fgr",""+str_status);
                    fragment = new Agent_Registered_frg();
                }
*/
                break;

        }
        return fragment;
    }

    public int getCount() {
        return FRAGMENT_COUNT;
    }
}

