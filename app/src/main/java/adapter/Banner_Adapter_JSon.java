package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by GT on 5/31/2017.
 */

public class Banner_Adapter_JSon extends PagerAdapter {

    public int i;
    private Activity context;
    private JSONArray jsonArray;
    String page_status;


    public Banner_Adapter_JSon(Activity applicationContext, JSONArray jsonArray, String page_status) {
        this.context=applicationContext;
        this.jsonArray=jsonArray;
        this.page_status=page_status;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
   /* }
        if(Img_Bnrint!=null){
            return Img_Bnrint.length;}
        return 0;*/
    }
    @Override
    public Object instantiateItem(View collection, final int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.banner_adapter, null);
        try {

            JSONObject jsonObject=jsonArray.getJSONObject(position);
            ImageView id_iv_banner = (ImageView) layout.findViewById(R.id.id_iv_banner);

         //   Toast.makeText(context, "banner :  "+jsonObject.getString("name"), Toast.LENGTH_SHORT).show();

            if(jsonObject.has("name"))
                Glide.with(context)
                        .load(jsonObject.getString("name"))
                        .into(id_iv_banner);


            id_iv_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* try {
                        if(page_status.equalsIgnoreCase("HOME")) {
                            Intent i = new Intent(context, Restaurant_dashboard_activity.class);
                            i.putExtra("JSON_DATA", "" + jsonArray.getJSONObject(position).toString());
                            i.putExtra("Position_Pager", "1");
                          //  Log.d("JSON_DATA", "" + jsonArray.getJSONObject(position).toString());

                            context.startActivity(i);
                        }
                    } catch (Exception e){
                        Log.d("excep",e.getMessage());

                    }*/

                }
            });
                }catch (Exception e){
                        Log.d("exc_ep",e.getMessage());

                }
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
