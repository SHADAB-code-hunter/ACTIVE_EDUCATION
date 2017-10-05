package Fab_Filter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.gt.active_education.Filter_Activity;
import com.gt.active_education.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import callbacks.Filter_List_Listener;
import pojo.Cat_Model;
import task.Filter_Async_Load;
import utilities.App_Static_Method;
import utilities.Common_Pojo;


/**
 * Created by krupenghetiya on 23/06/17.
 */

public class MyFabFragment extends AAH_FabulousFragment {

    //ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    ArrayMap<String, String> applied_filters = new ArrayMap<>();
    List<TextView> textviews = new ArrayList<>();
    TabLayout tabs_types;
    ImageButton imgbtn_refresh,imgbtn_apply;
    SectionsPagerAdapter mAdapter;
    private DisplayMetrics metrics;
    private ViewPager vp_types;
    private String str_bundle,str_title;
    private LinearLayout id_tab_linear;
    private ProgressDialog progressDialog;

    public static MyFabFragment newInstance() {
        MyFabFragment mff = new MyFabFragment();
        return mff;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applied_filters = ((Filter_Activity)getActivity()).getApplied_filters();
        metrics = this.getResources().getDisplayMetrics();
        progressDialog=new ProgressDialog(MyFabFragment.this.getContext());
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.LogOut));
         if (getArguments()!=null)
         {
             str_bundle=getArguments().getString("Utlextra");
             Log.d("sts",str_bundle);
         }
        //  for (Map.Entry<String, List<String>> entry : applied_filters.entrySet()) { //applied_filters.entrySet()  return Set<Entry<K, V>>
        for (Map.Entry<String, String> entry : applied_filters.entrySet()) { //applied_filters.entrySet()  return Set<Entry<K, V>>       
            Log.d("k9resed", "from activity: "+entry.getKey());
           /* for(String s: entry.getValue()){
                Log.d("k9res", "from activity val: "+s);
            }*/
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (progressDialog!=null)
        {
            progressDialog.cancel();
        }

    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.filter_view, null);

        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.rl_content);
        LinearLayout ll_buttons = (LinearLayout) contentView.findViewById(R.id.ll_buttons);
        imgbtn_refresh = (ImageButton) contentView.findViewById(R.id.imgbtn_refresh);
        imgbtn_apply = (ImageButton) contentView.findViewById(R.id.imgbtn_apply);
        vp_types = (ViewPager) contentView.findViewById(R.id.vp_types);
        tabs_types = (TabLayout) contentView.findViewById(R.id.tabs_types);
        id_tab_linear=(LinearLayout) contentView.findViewById(R.id.id_linear);
        id_tab_linear.setClickable(true);
        imgbtn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter(applied_filters);
            }
        });
        imgbtn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (TextView tv : textviews) {
                    tv.setTag("unselected");
                    tv.setBackgroundResource(R.drawable.chip_unselected);
                    tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
                }
                applied_filters.clear();
            }
        });
        mAdapter = new SectionsPagerAdapter();
        vp_types.setOffscreenPageLimit(4);
        vp_types.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tabs_types.setupWithViewPager(vp_types);


        //params to set
        setAnimationDuration(200); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setViewgroupStatic(ll_buttons); // optional; layout to stick at bottom on slide
        setViewPager(vp_types); //optional; if you use viewpager that has scrollview
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }



    // inner pager pager dapter class
    public class SectionsPagerAdapter extends PagerAdapter implements Filter_List_Listener{

        ArrayList<String> arrayList=App_Static_Method.get_status_list();
        private  FlexboxLayout fbl;
        private  int var_bl=0;

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_filters_sorters, collection, false);
            fbl = (FlexboxLayout) layout.findViewById(R.id.fbl);
            set_list_data_in_view(arrayList.get(position));
         /*   if (var_bl==0)
            {
                set_list_data_in_view(arrayList.get(var_bl));
            }else if(var_bl>0)
            {
                set_list_data_in_view(arrayList.get(var_bl));
            }*/


            collection.addView(layout);
            return layout;
        }

        private void set_list_data_in_view(String s) {
            Log.d("dddddddddss",""+s);
            List<Common_Pojo> commonPojoList=App_Static_Method.get_status_one_by_one_list(s);
            // new Filter_Async_Load(SectionsPagerAdapter.this,str_bundle,s).execute();
            set_list_datein_view(commonPojoList,fbl,s);
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {

            if(arrayList!=null)
            return arrayList.size();
            else return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Log.d("cfdrdg",""+arrayList.get(position));
            return (CharSequence) arrayList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public void on_Filter_Loaded(List<Cat_Model> listMovies) {

        }

        @Override
        public void on_Filter_pager(List<Common_Pojo> commonPojoList, String s) {
            Log.d("hdddhd","djdjdjj");
            set_list_datein_view(commonPojoList,fbl,s);
        }


    private void set_list_datein_view(List<Common_Pojo> keys, FlexboxLayout fbl, final String filter_category) {

        for (int i = 0; i < keys.size(); i++) {
            Log.d("keysl",""+keys.size()+" "+filter_category);
            View subchild = getActivity().getLayoutInflater().inflate(R.layout.single_chip, null);
            final TextView tv = ((TextView) subchild.findViewById(R.id.txt_title));
            tv.setText(keys.get(i).getName());
            Log.d("keyssssl",""+keys.get(i).getName());
            final int finalI = i;
            final List<Common_Pojo> finalValue = keys;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv.getTag() != null && tv.getTag().equals("selected")) {
                        tv.setTag("unselected");
                        tv.setBackgroundResource(R.drawable.chip_unselected);
                    //    tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
                        removeFromSelectedMap(filter_category, finalValue.get(finalI).getId());
                    } else {
                        tv.setTag("selected");
                        tv.setBackgroundResource(R.drawable.chip_selected);
                        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.theme4));
                        var_bl=(vp_types.getCurrentItem()+1);
                        addToSelectedMap(filter_category, finalValue.get(finalI).getId());
                    }
                }
            });
            try {
                Log.d("k9res", "key: " + filter_category + " |val:" + keys.get(finalI));
                Log.d("k9res", "applied_filters != null: " + (applied_filters != null));
                Log.d("k9res", "applied_filters.get(key) != null: " + (applied_filters.get(filter_category) != null));
                Log.d("k9res", "applied_filters.get(key).contains(keys.get(finalI)): " + (applied_filters.get(filter_category).contains(keys.get(finalI).getName())));
            }catch (Exception e){

            }
            if (applied_filters != null && applied_filters.get(filter_category) != null && applied_filters.get(filter_category).contains(keys.get(finalI).getName())) {
                tv.setTag("selected");
                tv.setBackgroundResource(R.drawable.chip_selected);
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_header));
            } else {
                tv.setBackgroundResource(R.drawable.chip_unselected);
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
            }
            textviews.add(tv);

            fbl.addView(subchild);
        }

    }

    private void addToSelectedMap(String key, String value) {
        Log.d("sfgrt","  ... "+key+"   "+value);


        vp_types.setCurrentItem(var_bl);

        if (applied_filters.get(key) != null && !applied_filters.get(key).contains(value)) {
            applied_filters.put(key,value);
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(value);
            applied_filters.put(key, value);
        }
    }

    private void removeFromSelectedMap(String key, String value) {
        if (applied_filters.get(key) .equals(1)) {
            applied_filters.remove(key);
        } else {
            applied_filters.put(key, value);
        }
    }
   }
}
