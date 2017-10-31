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

import java.util.ArrayList;

import static android.R.attr.width;
import static java.lang.System.load;

/**
 * Created by GT on 5/31/2017.
 */

public class Banner_Adapter extends PagerAdapter {

    public int i;
    private Context context;
    private ArrayList Img_Bnr_str;
    private  int[] Img_Bnrint;
    private String str_url;

    public Banner_Adapter(Context context, ArrayList<String> Img_Bnr_str, String str_url) {
        this.context=context;
        this.Img_Bnr_str=Img_Bnr_str;
        this.str_url=str_url;
    }

    public Banner_Adapter(Context applicationContext, int[] Img_Bnrint) {
        this.context=context;
        this.Img_Bnrint=Img_Bnrint;
    }

    public int getCount() {
        if(Img_Bnr_str!=null){
        return Img_Bnr_str.size();}
        if(Img_Bnrint!=null){
            return Img_Bnrint.length;}
        return 0;
    }

    public Object instantiateItem(View collection, final int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.banner_adapter, null);
        ImageView id_iv_banner=(ImageView) layout.findViewById(R.id.id_iv_banner);
        id_iv_banner.setVisibility(View.VISIBLE);

        if(Img_Bnr_str!=null)
        {
            Log.d("jfkj",""+str_url+"/banner/"+Img_Bnr_str.get(position));
            Picasso.with(context)
                    .load(str_url+"/banner/"+Img_Bnr_str.get(position))
                    .placeholder(R.drawable.ic_manav_rcahna_banner)   // optional
                    // .error(DRAWABLE RESOURCE)      // optional
                    // .resize(width, height)                        // optional
                    // .rotate(degree)                             // optional
                    .into(id_iv_banner);
      //  id_iv_banner.setImageResource(str_url+"/picture/"+Img_Bnr_str.get(position));
        }else if(Img_Bnrint!=null) {
            Log.d("jfkj",""+str_url+"/picture/"+Img_Bnrint[position]);
            Picasso.with(context)
                    .load(str_url+"/picture/"+Img_Bnrint[position])
                    .placeholder(R.drawable.ic_manav_rcahna_banner)   // optional
                    // .error(DRAWABLE RESOURCE)      // optional
                    // .resize(width, height)                        // optional
                    // .rotate(degree)                             // optional
                    .into(id_iv_banner);
        }
            /**/

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
