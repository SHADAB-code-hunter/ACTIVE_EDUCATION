package adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragment.Agent_Account_Frg;
import fragment.Agent_Best_Deal_Fragment;
import fragment.Agent_Book_FRG;
import fragment.Agent_Profile_Detail_Fragment;
import fragment.Agent_Progress_Fragment;
import fragment.Agent_Target_Frg;
import fragment.Partner_Seat_Submission;
import tab_bar.My_Booking_Fragment;
import utilities.UrlEndpoints;

/**
 * Created by GT on 10/23/2017.
 */

public class Agent_Profile_VP_Adpter extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 6;
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
                fragment =new Agent_Best_Deal_Fragment();

                break;
            case 1:

                fragment =new My_Booking_Fragment();
                Bundle bundle=new Bundle();
                bundle.putString("URL", ""+ UrlEndpoints.GET_BOOK_OFFER);
                fragment.setArguments(bundle);
                break;
            case 2:

                fragment =new Agent_Profile_Detail_Fragment();
                break;
            case 3:

                fragment =new Agent_Target_Frg();
                break;
            case 4:
                fragment =new Agent_Account_Frg();
                break;
            case 5:
                fragment =new Agent_Progress_Fragment();
                break;


        }
        return fragment;
    }
    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}

