package adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.gt.active_education.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import active.Adv_Activity;
import network.VolleySingleton;
import pojo.ADV_RANK_MODEL;
import utilities.MyApplication;
import utilities.UrlEndpoints;

import static extras.Keys.EndpointColor_Theme.KEY_COLOR_THEME;


/**
 * Created by Administrator on 06-07-2017.
 */
public class ADV_VPager_Adapter extends PagerAdapter  {

    public FragmentManager sfm;
    public Adv_Activity adv_activity;
    public ArrayList<ADV_RANK_MODEL> adv_rank_list;
    private MyApplication myApplication=new MyApplication();
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;


    public ADV_VPager_Adapter(FragmentManager supportFragmentManager, Adv_Activity adv_activity, ArrayList<ADV_RANK_MODEL> adv_rank_list) {
        this.sfm=supportFragmentManager;
        this.adv_activity=adv_activity;
        this.adv_rank_list=adv_rank_list;

    }

    @Override
    public int getCount() {
        //Log.d("sizews",""+adv_rank_list.size());
        return adv_rank_list.size();
    }


    public Object instantiateItem(View collection, final int position) {

        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SharedPreferences sharedPreferences_theme;
        View layout = inflater.inflate(R.layout.adv_frg_layout, null);
        TextView id_rank_number=(TextView)layout.findViewById(R.id.id_rank_number);
        LinearLayout id_winner_linear=(LinearLayout)layout.findViewById(R.id.id_winner_linear);
        LinearLayout id_toolbar_winner=(LinearLayout)layout.findViewById(R.id.id_toolbar_winner);
        CircularImageView id_imgView_profile=(CircularImageView)layout.findViewById(R.id.id_image_profile);
        TextView id_state=(TextView)layout.findViewById(R.id.id_state);
        id_rank_number.setText("Rank "+(position+1));
        id_state.setText(adv_rank_list.get(position).getRK_STATE());
       // sharedPreferences_theme=myApplication.getTheme_Preferences(getApplicationContext(),KEY_COLOR_THEME,"0");
      /*  if(myApplication.getTheme_Preferences(adv_activity.getApplicationContext(),KEY_COLOR_THEME,"0").equals("0"))
        {

        }else if(!(myApplication.getTheme_Preferences(adv_activity.getApplicationContext(),KEY_COLOR_THEME,"0").equals("0")))
        {
            set_theme(myApplication.getTheme_Preferences(adv_activity.getApplicationContext(),KEY_COLOR_THEME,"0"),id_winner_linear,id_toolbar_winner);
        }*/
        mVolleySingleton = VolleySingleton.getInstance();mImageLoader = mVolleySingleton.getImageLoader();
        loadImages(id_imgView_profile, adv_rank_list.get(position).getRK_IMAGE(), mImageLoader);

        ((ViewPager) collection).addView(layout);

        return layout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    private void set_theme(SharedPreferences sharedPreferences_theme, LinearLayout id_winner_linear, LinearLayout id_toolbar_winner) {

/*
        switch (sharedPreferences_theme.getString(KEY_THEME_NUM, "")) {
            case "THEME1":
                //Log.d("hgdhghghgd", "THEME1");

                id_toolbar_winner.setBackgroundColor(Color.RED);
                id_winner_linear.setBackground(adv_activity.getResources().getDrawable(R.drawable.ic_blur_main));
                break;
            case "THEME2":
                //Log.d("hgdhghghgd", "THEME2");
                id_toolbar_winner.setBackgroundColor(Color.TRANSPARENT);
                id_winner_linear.setBackground(adv_activity.getResources().getDrawable(R.drawable.blur_img));
                break;
            case "THEME3":
                //Log.d("hgdhghghgd", "THEME3");
                id_toolbar_winner.setBackgroundColor(Color.BLUE);
                id_winner_linear.setBackground(adv_activity.getResources().getDrawable(R.drawable.ic_back_main));
                break;
        }
*/
    }
    private void loadImages(final CircularImageView id_imgView_profile, String login_pic, ImageLoader idImageProfile) {

     //Log.d("cfgascfgcx",UrlEndpoints.Profile_pic+login_pic);
     idImageProfile.get(UrlEndpoints.Profile_pic+login_pic, new ImageLoader.ImageListener() {
     @Override
      public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
      //Log.d("facebookpic","bc  m");
             id_imgView_profile.setImageBitmap(response.getBitmap());
      }
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

    }

}
