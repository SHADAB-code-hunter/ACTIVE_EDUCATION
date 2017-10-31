package com.gt.active_education;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Fab_Filter.AAH_FabulousFragment;
import Fab_Filter.MovieData;
import adapter.MoviesAdapter;
import Fab_Filter.MyFabFragment;
import callbacks.Avail_Course_Listener;
import callbacks.Filter_List_Listener;
import callbacks.Inner_Dialog_Listener;
import callbacks.JOBJ_Listener;
import callbacks.Upcoming_List_LoadedListener;
import task.Filter_Task;
import task.TaskLoad_List;
import network.VolleySingleton;
import utilities.App_Static_Method;
import pojo.Cat_Model;
import utilities.Available_Courses_Dialog;
import utilities.Common_Pojo;
import utilities.ConnectionCheck;
import utilities.Inner_Filter_Dialog;
import utilities.State_City_Search;
import utilities.UrlEndpoints;


public class Filter_Activity extends AppCompatActivity implements AAH_FabulousFragment.Callbacks,
        Upcoming_List_LoadedListener,Filter_List_Listener, View.OnClickListener, Inner_Dialog_Listener, JOBJ_Listener, Avail_Course_Listener {

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private String cat_type_url;
    FloatingActionButton fab,fab2;
    RecyclerView recyclerView;
    MovieData mData;
    MoviesAdapter mAdapter;
    Picasso picasso;
    List<Cat_Model> mList = new ArrayList<>();
    private TextView id_back_btn_quiz,category_title;
    private  String str_Url,str_Title,str_img_path;
    //private ArrayMap<String, List<String>> applied_filters =new ArrayMap<>();
    private ArrayMap<String,String> applied_filters =new ArrayMap<>();
    private HorizontalScrollView  id_filter_layout;
    private FrameLayout id_filter_upper,id_filter_search;
    private boolean vis_bl=false;
    private LinearLayout id_lnr_bottom;
    private boolean scroll_bln=false;
    android.os.Handler handler,handler_vis;
    private LinearLayout id_toolbar;
    private Spinner state_spinner,city_spinner;
    private FrameLayout id_frm_back;
    private State_City_Search state_city_search;
    private LinearLayout id_state_filter,id_city_filter,id_course_filter,id_branch_filter;
    private boolean open=false;
    private TextView id_tv_state,id_tv_city,id_tv_course,id_tv_branch;
    private TextView id_list_url;
    private RelativeLayout id_rv_filter;
    private Inner_Filter_Dialog inner_filter_dialog;
    private Available_Courses_Dialog availableCoursesDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(!new ConnectionCheck(Filter_Activity.this).checkConnection()) {
            new ConnectionCheck(Filter_Activity.this).check_network(Filter_Activity.this);
            return;
        }
        id_list_url=(TextView)findViewById(R.id.id_list_url);
        id_state_filter=(LinearLayout)findViewById(R.id.id_state_filter);
        id_city_filter=(LinearLayout)findViewById(R.id.id_city_filter);
        id_course_filter=(LinearLayout)findViewById(R.id.id_course_filter);
        id_branch_filter=(LinearLayout)findViewById(R.id.id_branch_filter);
        id_frm_back=(FrameLayout)findViewById(R.id.id_frm_back);id_frm_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter_Activity.this.finish();
            }
        });
        id_rv_filter=(RelativeLayout)findViewById(R.id.id_rv_filter);id_rv_filter.setOnClickListener(this);
        category_title=(TextView)findViewById(R.id.category_title);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        id_lnr_bottom=(LinearLayout)findViewById(R.id.id_lnr_bottom);
        id_toolbar=(LinearLayout)findViewById(R.id.id_toolbar);

        id_tv_state=(TextView)findViewById(R.id.id_tv_state);
        id_tv_city=(TextView)findViewById(R.id.id_tv_city);
        id_tv_course=(TextView)findViewById(R.id.id_tv_course);
        id_tv_branch=(TextView)findViewById(R.id.id_tv_branch);

        city_spinner=(Spinner)findViewById(R.id.id_city);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScrollPositionChanged();
            }
        });

        id_filter_layout=(HorizontalScrollView)findViewById(R.id.id_filter_layout);
//        id_filter_upper=(FrameLayout)findViewById(R.id.id_filter_upper);id_filter_upper.setOnClickListener(this);
      //  id_filter_search=(FrameLayout)findViewById(R.id.id_filter_search);id_filter_search.setOnClickListener(this);
        //  ll = (LinearLayout) findViewById(R.id.ll);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(false);

            Slide slide=new Slide(Gravity.RIGHT);
            getWindow().setReenterTransition(slide);
        }
        if(!getIntent().equals(null))
        {
            str_Url=getIntent().getStringExtra("url");
            str_Title=getIntent().getStringExtra("title");
            str_img_path=getIntent().getStringExtra("img_path");
            id_list_url.setText(str_Url);
            new TaskLoad_List(Filter_Activity.this,str_Url).execute();
        }
        //set_sign_in();
        // getList data & progress bar & saved in shared Preference;

        if (str_Title!=null)
        category_title.setText(str_Title);

        picasso = Picasso.with(this);
        // add shared Preference list

        if (getIntent().getIntExtra("fab",1)==2){
         /*   fab2.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);*/
        }else {
           /* fab2.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            ll.setVisibility(View.GONE);*/
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFabFragment dialogFrag = MyFabFragment.newInstance();
                Bundle bundle=new Bundle(); bundle.putString("Utlextra",str_Url);
                dialogFrag.setArguments(bundle);
                dialogFrag.setParentFab(fab);
                dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
            }
        });
        /*fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFabFragment dialogFrag = MyFabFragment.newInstance();
                dialogFrag.setParentFab(fab2);
                dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
            }
        });*/
        set_state_list(UrlEndpoints.get_filter_list+1);
      /*  id_state_filter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!open) {
                    open = true;

                    state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s,String s_id) {
                            if(s.equals("")){
                                id_tv_state.setText("State"); id_tv_city.setText("City");}
                            else id_tv_state.setText(s);

                            state_city_search.cancel();
                            open=false;
                        }
                    }, Filter_Activity.this, UrlEndpoints.get_filter_list + 1,"");
                    state_city_search.show();
                }
                return false;
            }
        });

        id_city_filter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(!open) {
                    open = true;

                    if(id_tv_state.getText().toString()!=null) {
                        StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s) {
                                if (s.equals(""))
                                    id_tv_city.setText("City");
                                else
                                    id_tv_city.setText(s);
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Filter_Activity.this, GET_CITY+stringBuilder.toString());
                        state_city_search.show();
                    }else {
                        Toast.makeText(Filter_Activity.this, "Please Select State First !!!", Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }
        });
*/
    }


    private void set_state_list(String url) {

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
                                set_state_inadapter(arrayList);
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private void set_state_inadapter(ArrayList<Common_Pojo> list) {

        ArrayList<String> arrayList=new ArrayList<>();
        ArrayList<String> state_arrayList=new ArrayList<>();

        for (Common_Pojo commonPojo:list)
        {
            Log.d("statelsit_anme",commonPojo.getName());
            arrayList.add(commonPojo.getName());
            state_arrayList.add(commonPojo.getId());
        }

        ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,arrayList);
       /* Spinner_Adapter spinnerAdapter=new Spinner_Adapter(getContext(),arrayList);*/
      //  state_spinner.setAdapter(cat_adapter);

        // calling for city
      /*  state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                 *//* if(type_postion>0)
                  {*//*
                //Get_Gellary_List.get_city_list(Filter_Dialog.this,UrlEndpoints.get_filter_list+type_postion+"state="+(position+1),"city");
                //  }
                int poss= state_spinner.getSelectedItemPosition();
                set_city_list(UrlEndpoints.GET_CITY+(state_arrayList.get(position)),state_arrayList.get(position));

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
*/
    }
    private void set_city_list(String url, final String state_id) {


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

                                set_city_inadapter(arrayList,state_id);
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                Log.d("unauth","un_Auth");
                                Toast.makeText(getApplicationContext(), " Not Exist in This Field", Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    private void set_city_inadapter(ArrayList<Common_Pojo> list, final String state_id) {

        ArrayList<String> city_arrayList=new ArrayList<>();
        ArrayList<String> city_arrayList_id=new ArrayList<>();
        for (Common_Pojo commonPojo:list)
        {
            Log.d("citylsit_anme",commonPojo.getName());
            Log.d("citylsit_id",commonPojo.getId());
            city_arrayList.add(commonPojo.getName());
            city_arrayList_id.add(commonPojo.getId());
        }

        ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,city_arrayList);
        city_spinner.setAdapter(cat_adapter);

       /* city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //  str_choose_date=arrayList.get(position);
                Log.d("strr","fgrt"+position);
               *//* type_postion=(position+1);
                set_state_list(UrlEndpoints.get_filter_list+(position+1),position);*//*

                ////////////////

              *//*  String  city_id= city_arrayList_id.get(position);
                Log.d("common_city"," "+city_id);*//*



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
        });*/
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    // it will run after selection
    @Override
    public void onResult(Object result) {
        Log.d("k9res", "onResult: " + result.toString());// onResult:{ex- key=[value]}: {branch=[Electrical Engg], city=[Faridabad], state=[Delhi], courses=[BACHELOR OF PHARMACY ]}
        if (result.toString().equalsIgnoreCase("swiped_down")) {
            //do something or nothing
        } else {
            if (result != null) {
                ArrayMap<String,String> applied_filters = (ArrayMap<String,String>) result;
                if (applied_filters.size() != 0) {
                 //List<Cat_Model> filteredList = mData.getAllMovies();
                 //iterate over arraymap
                 //http://activeeduindia.com/admin/webservices/getMainList.php?type=1&state=2&branch=2&course=1&city=2

                    String filter_url= cat_type_url;
                    Log.d("urler",filter_url);
                    Log.d("posdd",cat_type_url);
                    id_list_url.setText(cat_type_url);
                    ArrayList<String> arrayList= App_Static_Method.get_status_list();

                    for (String str:arrayList)
                    {
                        Log.d("sghdfghdgf",str);
                    }

                    for (Map.Entry<String, String> entry : applied_filters.entrySet()) {
                        Log.d("k9resfg", "entry.key: "+entry.getKey());
                        Log.d("k9revalue", "entry.value: "+entry.getValue());
                        filter_url=filter_url+"&"+(entry.getKey())+"="+entry.getValue();
                    }
                    Log.d("k9resurl", "new size: "+filter_url);
                    new Filter_Task(Filter_Activity.this,filter_url).execute();

                }else {
                  /*  mList.addAll(App_Static_Method.get_list_data());
                    mAdapter.notifyDataSetChanged();*/

                }
            }
            //handle result
        }
    }

   // public ArrayMap<String, List<String>> getApplied_filters() {
    public ArrayMap<String, String> getApplied_filters() {
        return applied_filters;
    }

    public MovieData getmData() {

        Log.d("mdata",""+mData);
        return mData;
    }

    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies) {
       /* Log.d("kist",""+listMovies.toString());
        mAdapter = new MoviesAdapter(listMovies, picasso, Filter_Activity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mList.addAll(listMovies);
        mData = new MovieData(listMovies);*/
    }

    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies, String cat_type_url) {
        this.cat_type_url=cat_type_url;
        Log.d("kist_new",""+cat_type_url);
        Log.d("list_movie",""+listMovies.size());

        if (str_img_path!=null)
        {
            mAdapter = new MoviesAdapter(listMovies, picasso, Filter_Activity.this,cat_type_url,str_img_path);
        }else {
            mAdapter = new MoviesAdapter(listMovies, picasso, Filter_Activity.this,cat_type_url);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mList.addAll(listMovies);
        mData = new MovieData(listMovies);
    }

    @Override
    public void onUpcomingcourses(List<Common_Pojo> common_pojos) {

    }

    @Override
    public void onUpcomingexams(List<Common_Pojo> common_pojos) {

    }


    @Override
    public void on_Filter_Loaded(List<Cat_Model> filter_list) {
        if(!filter_list.isEmpty()) {
            Log.d("filterd", "" + filter_list);
            if(inner_filter_dialog!=null)
            {
                inner_filter_dialog.cancel();
            }
            mList.clear();
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new MoviesAdapter(filter_list, picasso, Filter_Activity.this);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mList.addAll(filter_list);
            mData = new MovieData(filter_list);
        }else {
            App_Static_Method.list_not_get(Filter_Activity.this,"Wrong Option Combination");
        }
    }

    @Override
    public void on_Filter_pager(List<Common_Pojo> listMovies, String s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_filter_upper:

                if(!vis_bl) {
                    Animation animation2;
                    animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.filter_down);
                    id_filter_layout.setAnimation(animation2);
                    id_filter_layout.setVisibility(View.VISIBLE);
                    id_filter_upper.setVisibility(View.GONE);
                    id_filter_search.setVisibility(View.VISIBLE);
                    vis_bl = true;
                }
                else if(vis_bl) {
                   /* Animation animation2;
                    animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.filter_up);
                    id_filter_layout.setAnimation(animation2);*/
                    id_filter_layout.setVisibility(View.INVISIBLE);
                    id_filter_upper.setVisibility(View.VISIBLE);
                    id_filter_search.setVisibility(View.INVISIBLE);
                    vis_bl = false;
                }

                break;

            case R.id.id_rv_filter:

                if(!vis_bl) {
                   /* Animation animation2;
                    animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.filter_down);
                    id_filter_layout.setAnimation(animation2);
                    id_filter_layout.setVisibility(View.VISIBLE);
                    id_filter_upper.setVisibility(View.GONE);
                    id_filter_search.setVisibility(View.VISIBLE);*/
                    inner_filter_dialog=new Inner_Filter_Dialog(Filter_Activity.this,str_Url,Filter_Activity.this);
                    inner_filter_dialog.show();
                    vis_bl = false;
                }
                /*else if(vis_bl) {
                   *//* Animation animation2;
                    animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.filter_up);
                    id_filter_layout.setAnimation(animation2);*//*
                    id_filter_layout.setVisibility(View.INVISIBLE);
                    id_filter_upper.setVisibility(View.VISIBLE);
                    id_filter_search.setVisibility(View.INVISIBLE);
                    vis_bl = false;
                }*/


                // call final URL
                //  new Filter_Task(Filter_Activity.this,filter_url).execute();
                break;

        }
    }

    private void onScrollPositionChanged() {

     //   Animation animation2;
       /* animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down_from_top);
        id_lnr_bottom.setAnimation(animation2);*/
       // id_toolbar.setVisibility(View.INVISIBLE);
        id_lnr_bottom.setVisibility(View.INVISIBLE);
        scroll_bln=true;
            if (scroll_bln) {
                handler_vis = new android.os.Handler();
                handler_vis.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Animation animation;
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up_from_bottom);
                      /*  Animation animation2;
                        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.filter_down);*/

                        id_lnr_bottom.setAnimation(animation);
                        id_lnr_bottom.setVisibility(View.VISIBLE);
                    /*    id_toolbar.setAnimation(animation2);
                        id_toolbar.setVisibility(View.VISIBLE);*/

                        scroll_bln=false;
                    }
                }, 1000);
        }
    }


    @Override
    public void onInner_Dialog(Map<String, String> map) {
        Log.d("kdkdk",map.toString());
        new Filter_Task(Filter_Activity.this,str_Url,map).execute();
    }

    @Override
    public void onLJsonLoaded(JSONObject jsonObject) {


    }

    @Override
    public void onLJsonLoaded_new(JSONObject jsonObject) {

    }

    @Override
    public void onAvailCourse(final String str_type,final String clg_id,final String course_id,final String branch_id,final String branch_name) {
        // avail courses

        Log.d("ncvkcnvm",str_type+"  "+ clg_id);

         availableCoursesDialog=new Available_Courses_Dialog(new Available_Courses_Dialog.Dialog_Spinner_Listener() {
            @Override
            public void on_listdata(String s, String sid) {
                Log.d("ncvkcnvm",s+"  "+ sid);
                availableCoursesDialog.cancel();

                Intent i=new Intent(Filter_Activity.this,College_Detail_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("clg_id",""+clg_id);
                bundle.putString("course",""+course_id);
                bundle.putString("branch",""+branch_id);
                bundle.putString("branch_name",""+branch_name);
                i.putExtras(bundle);
                Log.d("courseid","cvcvcv"+(course_id));
                if(!(str_type).equals("NA"))// for
                {
                    Log.d("djeejd","dhdh"+(str_type));
                    i.putExtra("type",""+str_type);
                }
                else
                {
                    i.putExtra("type",""+str_type);
                }
                startActivity(i);
            }
        },Filter_Activity.this,UrlEndpoints.URL_GET_AVAIL_COURSES+"id="+clg_id+"&type="+str_type);
        availableCoursesDialog.show();
    }

  /*  private void set_applynow() {

    }*/
}
