package adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragment.Education_Form_Fragment;
import fragment.Payment_Page;
import fragment.Personal_Fragment;

/**
 * Created by GT on 8/28/2017.
 */

public class Admission_Form_Adapter extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 3;
    Activity activity;
    private Bundle bundle;

    public Admission_Form_Adapter(FragmentManager FragmentManager, Activity activity) {
        super(FragmentManager);
        this.activity=activity;
    }
    public Admission_Form_Adapter(FragmentManager FragmentManager, Activity activity,Bundle bundle) {
        super(FragmentManager);
        this.activity=activity;
        this.bundle=bundle;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment=new Personal_Fragment();
                fragment.setArguments(bundle);
                break;

            case 1:

                fragment=new Education_Form_Fragment();
                fragment.setArguments(bundle);
                break;


            case 2:

                fragment=new Payment_Page();
                break;

        }
        return fragment;
    }

    public int getCount() {
        return FRAGMENT_COUNT;
    }
}

