package adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.gt.active_education.R;

/**
 * Created by GT on 7/6/2017.
 */

public class Splash_Banner_Adapter extends PagerAdapter {

    public int i;
    private Context context;
    private  int[] Img_Bnr,Img_Bnr2;

    public Splash_Banner_Adapter(Context context, int[] Img_Bnr,int[] Img_Bnr2) {
        this.context=context;
        this.Img_Bnr=Img_Bnr;
        this.Img_Bnr2=Img_Bnr2;
    }

    public int getCount() {
        return Img_Bnr.length;
    }

    public Object instantiateItem(View collection, final int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.splash_adapter_layout, null);

        ImageView id_iv_banner=(ImageView) layout.findViewById(R.id.id_img_back);
        ImageView id_img_front=(ImageView)layout.findViewById(R.id.id_img_front);

        id_iv_banner.setImageResource(Img_Bnr[position]);
        id_img_front.setImageResource(Img_Bnr2[position]);

        ((ViewPager) collection).addView(layout);

        return layout;
    }


    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }



}
