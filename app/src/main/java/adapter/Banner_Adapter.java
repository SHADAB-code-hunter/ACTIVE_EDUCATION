package adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import static android.R.attr.width;
import static java.lang.System.load;

/**
 * Created by GT on 5/31/2017.
 */

public class Banner_Adapter extends PagerAdapter {

    public int i;
    private Context context;
    private  int[] Img_Bnr;

    public Banner_Adapter(Context context, int[] Img_Bnr) {
        this.context=context;
        this.Img_Bnr=Img_Bnr;
    }

    public int getCount() {
        return Img_Bnr.length;
    }

    public Object instantiateItem(View collection, final int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.banner_adapter, null);
        ImageView id_iv_banner=(ImageView) layout.findViewById(R.id.id_iv_banner);
        id_iv_banner.setVisibility(View.VISIBLE);
        id_iv_banner.setImageResource(Img_Bnr[position]);
            /**/
          /*  Picasso.with(context);
                  //  .load("YOUR IMAGE URL HERE")
                    .placeholder(R.drawable.ic_manav_rcahna_banner)   // optional
                    .error(DRAWABLE RESOURCE)      // optional
                   // .resize(width, height)                        // optional
                   // .rotate(degree)                             // optional
                    .into(id_iv_banner);*/
            /**/
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
