package tab_bar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.gt.active_education.R;

import java.util.ArrayList;

/**
 * Created by Md Farhan Raja on 2/16/2017.
 */

public class My_Prpfile_VP_Adapter extends AwesomeTabBarAdapter
{
   /* ArrayList<Fragment> fragments=new ArrayList<>();*/
    ArrayList<String> titles=new ArrayList<>();
    int[] colors={R.color.transparent,R.color.transparent};
    int[] textColors={R.color.theme1};
    int[] icons={R.drawable.ic_edt_prfl,R.drawable.ic_my_booking};

    public My_Prpfile_VP_Adapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
        titles.add("Edit Profile");
        titles.add("My Booking");
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        Bundle bundle=new Bundle();
        switch (position)
        {
            case 0:
                fragment= new TestFragment();
                bundle.putString("url","my_test_url");
                fragment.setArguments(bundle);
                return  fragment;

            case 1:
                fragment= new My_Booking_Fragment();
               /* bundle.putString("url","my_test_url222");
                fragment.setArguments(bundle);*/
                return  fragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getColorResource(int position) {
        return colors[0];
    }

    @Override
    public int getTextColorResource(int position) {
        return textColors[0];
    }

    @Override
    public int getIconResource(int position) {
        return icons[position];
    }
}
