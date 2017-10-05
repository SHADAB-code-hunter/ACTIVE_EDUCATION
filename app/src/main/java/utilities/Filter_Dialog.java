package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telecom.Connection;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.flexbox.FlexboxLayout;
import com.gt.active_education.Filter_Activity;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Search_Spinner.SearchableSpinner;
import callbacks.Dialog_Pager_Listener;
import network.VolleySingleton;
import retrofit2.http.POST;


/**
 * Created by GT on 8/18/2017.
 */

public class Filter_Dialog extends Dialog implements View.OnClickListener, Dialog_Pager_Listener {
    RelativeLayout id_relative_dialog;

    ArrayMap<String, String> applied_filters;
    private View btnRed;
    private Activity act;
    private Spinner id_spn_state,id_spn_city;
    private SearchableSpinner id_spn_cat;
    private int type_postion=0;
    private Button id_btn_submit,id_refresh;
    private ViewPager vp_types;
    private TabLayout tabs_types;
    private FrameLayout id_linear_extra;
    private Button id_final_dialog_list;
    private List<TextView> textviews = new ArrayList<>();
    private String final_list_dialog_url=null;
    private String cat_type_url;
    private String final_URL;
    private ArrayList<String> city_arrayList,city_arrayList_id;
    private ArrayList<String> state_arrayList;
    private boolean is_list=true;
    private TextView id_filter_url;

    public Filter_Dialog(Activity act) {
        super(act);
        this.act=act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_dialog);
        Display display = act.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Log.d("widthsize",""+width);
        Log.d("heightsize",""+height);
       // act.getWindow().setLayout((width-20),new Layout);
        getWindow().setBackgroundDrawable(new ColorDrawable(MyApplication.getAppContext().getResources().getColor(R.color.white)));
        id_relative_dialog=(RelativeLayout)findViewById(R.id.id_relative_dialog);
        id_spn_cat=(SearchableSpinner) findViewById(R.id.id_spn_cat);
        id_spn_state=(Spinner) findViewById(R.id.id_spn_state);
        id_spn_city=(Spinner) findViewById(R.id.id_spn_city);
        id_btn_submit=(Button)findViewById(R.id.id_btn_submit);id_btn_submit.setOnClickListener(this);
        vp_types = (ViewPager) findViewById(R.id.vp_types);
        tabs_types = (TabLayout) findViewById(R.id.tabs_types);
        id_linear_extra=(FrameLayout)findViewById(R.id.id_linear_extra);
        id_final_dialog_list=(Button)findViewById(R.id.id_final_dialog_list);
        id_filter_url=(TextView)findViewById(R.id.id_filter_url);
        id_final_dialog_list.setOnClickListener(this);
        id_refresh=(Button)findViewById(R.id.id_refresh); id_refresh.setOnClickListener(this);
        applied_filters = new ArrayMap<>();


        //  String str_cat[]= new String[]{"School","College","University","Coaching / Institute ","Training Institute","ITI College"};
        ArrayList<String> arrayList=new ArrayList<>();arrayList.add("School");arrayList.add("College");arrayList.add("University");
        arrayList.add("Coaching / Institute");arrayList.add("Training Institute");arrayList.add("ITI College");
        ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,arrayList);
        id_spn_cat.setAdapter(cat_adapter);

        id_spn_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //  str_choose_date=arrayList.get(position);
                Log.d("strr","fgrt");
                type_postion=(position+1);
                set_state_list(UrlEndpoints.get_filter_list+(position+1),position);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private void set_state_list(String url, final int cat_position) {
        Log.d("striurl",url) ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("city",""+str_response);
                            ArrayList<Common_Pojo> arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("state"))
                            {
                                JSONArray array = response.getJSONArray("state");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                    sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));
                                    //  Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(sendDateModel);
                                }
                                set_state_inadapter(arrayList,cat_position);
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                Log.d("unauth","un_Auth");
                                //  ConnectionCheck.unAuth_prob(activity,response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    private void set_state_inadapter(ArrayList<Common_Pojo> list, final int cat_position) {

        ArrayList<String> arrayList=new ArrayList<>();
        state_arrayList=new ArrayList<>();

        for (Common_Pojo commonPojo:list)
        {
            Log.d("statelsit_anme",commonPojo.getName());
            arrayList.add(commonPojo.getName());
            state_arrayList.add(commonPojo.getId());
        }

        ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,arrayList);
       /* Spinner_Adapter spinnerAdapter=new Spinner_Adapter(getContext(),arrayList);*/
        id_spn_state.setAdapter(cat_adapter);

        // calling for city
        id_spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                 /* if(type_postion>0)
                  {*/
                //Get_Gellary_List.get_city_list(Filter_Dialog.this,UrlEndpoints.get_filter_list+type_postion+"state="+(position+1),"city");
                //  }
                int poss= id_spn_cat.getSelectedItemPosition();
                set_city_list(UrlEndpoints.get_filter_list+(poss+1)+"&state="+(state_arrayList.get(position)),state_arrayList.get(position),cat_position);

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }
    private void set_city_list(String url, final String state_id, final int cat_position) {


        Log.d("cityiurl",url) ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("check_city",""+str_response);
                            ArrayList<Common_Pojo> arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("city"))
                            {
                                JSONArray array = response.getJSONArray("city");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                    sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));

                                    //  Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(sendDateModel);
                                }

                                set_city_inadapter(arrayList,state_id,cat_position);
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                Log.d("unauth","un_Auth");
                                Toast.makeText(act, " Not Exist in This Field", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void set_city_inadapter(ArrayList<Common_Pojo> list, final String state_id, int cat_position) {

        city_arrayList=new ArrayList<>();
        city_arrayList_id=new ArrayList<>();
        for (Common_Pojo commonPojo:list)
        {
            Log.d("citylsit_anme",commonPojo.getName());
            Log.d("citylsit_id",commonPojo.getId());
            city_arrayList.add(commonPojo.getName());
            city_arrayList_id.add(commonPojo.getId());
        }

        ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,city_arrayList);
        id_spn_city.setAdapter(cat_adapter);

        id_spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //  str_choose_date=arrayList.get(position);
                Log.d("strr","fgrt"+position);
               /* type_postion=(position+1);
                set_state_list(UrlEndpoints.get_filter_list+(position+1),position);*/

                ////////////////

              /*  String  city_id= city_arrayList_id.get(position);
                Log.d("common_city"," "+city_id);*/



           //     Log.d("dhghdg",final_URL);
                //get_pager_list_data(final_URL);
                // http://activeeduindia.com/admin/webservices/getCategoryFilter.php?type=2&state=1&city=1
                // id_linear_extra.setVisibility(View.VISIBLE);
                // id_linear_extra.setTranslationY(0.5f);

                get_pager_list_data(position,state_id);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_btn_submit:
               /* final_URL=UrlEndpoints.get_filter_list+(id_spn_cat.getSelectedItemPosition()+1)
                        +"&state="+(id_spn_state.getSelectedItemPosition()+1)+"&city="+(id_spn_city.getSelectedItemPosition()+1);

                Log.d("dhghdg",final_URL);
                //get_pager_list_data(final_URL);
               // http://activeeduindia.com/admin/webservices/getCategoryFilter.php?type=2&state=1&city=1
               // id_linear_extra.setVisibility(View.VISIBLE);
               // id_linear_extra.setTranslationY(0.5f);

                get_pager_list_data(final_URL);*/

                break;

            case R.id.id_final_dialog_list:

                 onResult(applied_filters);

                break;

            case R.id.id_refresh:

                for (TextView tv : textviews) {
                    tv.setTag("unselected");
                    tv.setBackgroundResource(R.drawable.chip_unselected);
                    tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
                }
                applied_filters.clear();

                break;
        }
    }
    public void onResult(Object result) {
        Log.d("k9ressderf", "onResult: " + result.toString());// onResult:{ex- key=[value]}: {branch=[Electrical Engg], city=[Faridabad], state=[Delhi], courses=[BACHELOR OF PHARMACY ]}
        if (result.toString().equalsIgnoreCase("swiped_down")) {
            //do something or nothing
        } else {
            if (result != null) {
                ArrayMap<String,String> applied_filters = (ArrayMap<String,String>) result;
                if (applied_filters.size() != 0) {
                    //List<Cat_Model> filteredList = mData.getAllMovies();
                    //iterate over arraymap
                    //http://activeeduindia.com/admin/webservices/getMainList.php?type=1&state=2&branch=2&course=1&city=2

                  /*  String filter_url= cat_type_url;
                    Log.d("urler",""+filter_url);
                    Log.d("posdd",""+cat_type_url);*/

                    ArrayList<String> arrayList= App_Static_Method.get_status_list();

                 /*   for (String str:arrayList)
                    {
                        Log.d("sghdfghdgf",str);
                    }
*/
                    for (Map.Entry<String, String> entry : applied_filters.entrySet()) {
                        Log.d("k9resfg", "entry.key: "+entry.getKey());
                        Log.d("k9revalue", "entry.value: "+entry.getValue());
                        final_URL=final_URL+"&"+(entry.getKey())+"="+entry.getValue();
                    }
                    Log.d("k9resurl", "new size: "+final_URL);

                    String message = final_URL;
                    String name = "getMainList";
                    String result_url = message.replaceAll("getCategoryFilter", name);

                       Log.d("resulturl",""+result_url);
                       Intent intent=new Intent(getContext(),Filter_Activity.class);
                       intent.putExtra("url",""+result_url);
                       intent.putExtra("title","Choice Available List");

                       isList_Avail(result_url);
                       if(!is_list)// true means not avail list
                       {
                           App_Static_Method.dialog_list_not_get(Filter_Dialog.this, "  List Not Get !!!!");
                           Toast.makeText(Filter_Dialog.this.getContext(), ""+result_url, Toast.LENGTH_SHORT).show();
                       }
                       else if(is_list)
                       (Filter_Dialog.this).getContext().startActivity(intent);
                }else {

                }
            }
            //handle result
        }
    }


    private void isList_Avail(String result_url) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST,result_url,
                response -> {

                    try {
                        JSONObject jObj = new JSONObject(response);

                        if (jObj.has("msg"))

                            is_list=false;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },error -> {

        });
        RequestQueue requestQueue= VolleySingleton.getInstance().getRequestQueue();
        requestQueue.add(stringRequest);

    }


    // PAGER DATA
    @Override
    public void onListDialog(ArrayList<Common_Pojo> list_common, String final_URL) {

        findViewById(R.id.id_linear_extra).setVisibility(View.VISIBLE);

        SectionsPagerAdapter   mAdapter = new SectionsPagerAdapter(list_common,final_URL);
        vp_types.setOffscreenPageLimit(4);
        vp_types.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tabs_types.setupWithViewPager(vp_types);
    }

    public class SectionsPagerAdapter extends PagerAdapter {

        ArrayList<Common_Pojo> list_common=new ArrayList<>();
        String final_URL;

        public SectionsPagerAdapter(ArrayList<Common_Pojo> list_common, String final_URL) {
            this.list_common=list_common;
            this.final_URL=final_URL;

        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_filters_sorters, collection, false);
            FlexboxLayout fbl = (FlexboxLayout) layout.findViewById(R.id.fbl);
            set_list_data_in_view(list_common.get(position).getName(),fbl,final_URL);
            collection.addView(layout);
            return layout;
        }

        private void set_list_data_in_view(String tab_str_name, FlexboxLayout fbl, String final_URL) {
          //  List<Common_Pojo> commonPojoList=App_Static_Method.get_status_one_by_one_list(s);
            get_vp_pager_data(fbl,tab_str_name,final_URL);

        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return list_common.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Log.d("cfdrdg",""+list_common.get(position));
            return (CharSequence) list_common.get(position).getName();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    private void get_vp_pager_data(final FlexboxLayout fbl, final String tab_str_name, final String final_url) {

        Log.d("finalurl",final_url) ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,final_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                             Log.d("drdcity",""+str_response);
                            ArrayList<Common_Pojo> single_status_arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has(tab_str_name))
                            {
                                JSONArray array = response.getJSONArray(tab_str_name);
                                Log.d("drddjgjdgjgd",""+array.toString());
                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                   // String str_key= String.valueOf((i+1));
                                    Log.d("getdata",""+json.toString());
                                    sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));
                                    single_status_arrayList.add(sendDateModel);
                                }
                                set_vp_list_datein_view(single_status_arrayList,fbl,tab_str_name);
                            }
                            else if(response.has("msg"))
                            {   //pd.dismiss();
                                Log.d("unauth","un_Auth");
                                Toast.makeText(act, " Not Exist in This Field", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void get_pager_list_data(final int spnr_city, String state_id) {

        String str_city=city_arrayList_id.get(spnr_city);
        final_URL=UrlEndpoints.get_filter_list+(id_spn_cat.getSelectedItemPosition()+1)
                +"&state="+(state_id)+"&city="+(str_city);
        id_filter_url.setText(final_URL);
        Log.d("finalurl_city",final_URL) ;
         // http://activeeduindia.com/admin/webservices/getCategoryFilter.php?type=1&state=1&city=1
       // String url_test="http://activeeduindia.com/admin/webservices/getMainList.php?type=2&state=1&city=1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,final_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                          //  Log.d("city",""+str_response);
                            ArrayList<Common_Pojo> status_arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("statusArray"))
                            {
                                JSONArray array = response.getJSONArray("statusArray");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                    sendDateModel.setName(json.getString(""+(i+1)));
                                    status_arrayList.add(sendDateModel);
                                }

                                (Filter_Dialog.this).onListDialog(status_arrayList,final_URL);
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                Log.d("unauth","un_Auth");
                                Toast.makeText(act, " Not Exist in This Field", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    private void set_vp_list_datein_view(List<Common_Pojo> keys, FlexboxLayout fbl, final String tab_str_name) {
        for (int i = 0; i < keys.size(); i++) {
            View subchild = Filter_Dialog.this.getLayoutInflater().inflate(R.layout.single_dialog_chip, null);
            final TextView tv = ((TextView) subchild.findViewById(R.id.txt_title));
            tv.setText(keys.get(i).getName());
            final int finalI = i;
            final List<Common_Pojo> finalValue = keys;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv.getTag() != null && tv.getTag().equals("selected")) {
                        tv.setTag("unselected");
                        tv.setBackgroundResource(R.drawable.chip_unselected);
                        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_chips));
                        removeFromSelectedMap(tab_str_name, finalValue.get(finalI).getId());
                    } else {
                        tv.setTag("selected");
                        tv.setBackgroundResource(R.drawable.chip_selected);
                        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.filters_header));
                        addToSelectedMap(tab_str_name, finalValue.get(finalI).getId());
                    }
                }
            });
            try {
                Log.d("k9res", "key: " + tab_str_name + " |val:" + keys.get(finalI));
                Log.d("k9res", "applied_filters != null: " + (applied_filters != null));
                Log.d("k9res", "applied_filters.get(key) != null: " + (applied_filters.get(tab_str_name) != null));
                Log.d("k9res", "applied_filters.get(key).contains(keys.get(finalI)): " + (applied_filters.get(tab_str_name).contains(keys.get(finalI).getName())));
            }catch (Exception e){

            }
            if (applied_filters != null && applied_filters.get(tab_str_name) != null && applied_filters.get(tab_str_name).contains(keys.get(finalI).getName())) {
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

        vp_types.setCurrentItem((vp_types.getCurrentItem()+1));

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