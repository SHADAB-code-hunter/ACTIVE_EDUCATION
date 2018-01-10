package adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragment.College_Fragment;
import fragment.Num_Verify_Fragment;
import fragment.OTP_Verification_Frg;
import fragment.School_Fragment;
import fragment.SignUp_Fragment;

/**
 * Created by GT on 7/8/2017.
 */

public class VP_FILTER_MAIN extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 3;
    private Activity activity;
    private String str_status;

    public VP_FILTER_MAIN(FragmentManager FragmentManager, Activity activity, String str_status) {
        super(FragmentManager);
        this.str_status=str_status;
        this.activity=activity;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment=new School_Fragment();
                break;

            case 1:

                fragment=new College_Fragment();
                break;

            case 2:


        }
        return fragment;
    }

    public int getCount() {
        return FRAGMENT_COUNT;
    }
}

