package adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragment.Agent_Best_Deal_Fragment;
import fragment.Agent_Profile_Detail_Fragment;
import fragment.Agent_Progress_Fragment;
import fragment.Agent_Target_Frg;
import fragment.Partner_Detail_Frag;
import fragment.Partner_Regisration_Frg;
import fragment.Partner_Seat_Submission;


/**
 * Created by GT on 8/12/2017.
 */

public class Agent_Profile_VP_Adpter  extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 3;
    Activity activity;

    public Agent_Profile_VP_Adpter(FragmentManager FragmentManager) {
        super(FragmentManager);
       // this.activity=activity;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment =new Partner_Regisration_Frg();

                break;
            case 1:

                fragment =new Partner_Seat_Submission();
                break;
            case 2:

                fragment =new Partner_Detail_Frag();
                break;


        }
        return fragment;
    }
    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}

