package adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import fragment.Agent_Best_Deal_Fragment;
import fragment.Agent_Book_FRG;
import fragment.Edit_Profile_Frg;

import tab_bar.My_Booking_Fragment;
import utilities.UrlEndpoints;

/**
 * Created by GT on 11/14/2017.
 */

public class User_Profile_Adapter extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 2;
    Activity activity;

    public User_Profile_Adapter(FragmentManager FragmentManager) {
        super(FragmentManager);
        // this.activity=activity;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment= new My_Booking_Fragment();
               /* bundle.putString("url","my_test_url222");
                fragment.setArguments(bundle);*/
                return  fragment;
            case 1:
                fragment= new Edit_Profile_Frg();
                Bundle bundle=new Bundle();
                bundle.putString("URL", ""+UrlEndpoints.GET_BOOKING_LIST);
                fragment.setArguments(bundle);
               /* bundle.putString("url","my_test_url");
               */
                return  fragment;
           /* case 2:
                fragment= new My_Booking_Fragment();
               *//* bundle.putString("url","my_test_url222");
                fragment.setArguments(bundle);*//*
                return  fragment;*/
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}

