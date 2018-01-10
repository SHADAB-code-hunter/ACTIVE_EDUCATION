package com.gt.active_education;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Fab_Filter.MovieData;
import Fab_Filter.MyFabFragment;
import Zend_Chat.UserProfile;
import Zend_Chat.UserProfileStorage;
import adapter.MoviesAdapter;
import adapter.College_adapter;
import callbacks.AvailCourseListener_School;
import callbacks.Avail_Course_Listener;
import callbacks.CALL_ADAPTER;
import network.VolleySingleton;
import pojo.Cat_Model;
import task.NORMAL_ASYNCHTASK;
import utilities.App_Static_Method;
import utilities.Available_Courses_Dialog;
import utilities.Availschool_class;
import utilities.ConnectionCheck;
import utilities.Filter_Dialogue_layout;
import utilities.Inner_Filter_Dialog;
import utilities.MyApplication;
import utilities.State_City_Search;
import utilities.UrlEndpoints;

import static utilities.UrlEndpoints.TOP_INNER_SCHOOL_FILTER_DEFAULT;


public class Main_Uni_List_Activity extends AppCompatActivity implements View.OnClickListener, Avail_Course_Listener , AvailCourseListener_School , CALL_ADAPTER {

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
    private FrameLayout id_call,id_frm_chat;
    private Filter_Dialogue_layout filter_dialogue_layout;
    private College_adapter College_adapter;
    private String avail_str_Url;
    private Availschool_class availschool_class;
    private JSONObject jsonObject_jsondata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        id_call=(FrameLayout)findViewById(R.id.id_call);id_call.setOnClickListener(this);
        id_frm_chat=(FrameLayout)findViewById(R.id.id_frm_chat);id_frm_chat.setOnClickListener(this);


        if(!new ConnectionCheck(Main_Uni_List_Activity.this).checkConnection()) {
            new ConnectionCheck(Main_Uni_List_Activity.this).check_network(Main_Uni_List_Activity.this);
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
                Main_Uni_List_Activity.this.finish();
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
            try {
                jsonObject_jsondata=new JSONObject((getIntent().getStringExtra("JSON_DATA")));
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(Main_Uni_List_Activity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                College_adapter= new College_adapter(Main_Uni_List_Activity.this,jsonObject_jsondata.getJSONArray("data"));
                recyclerView.setAdapter(College_adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //set_sign_in();
        // getList data & progress bar & saved in shared Preference;


        category_title.setText("University Listing");

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
        //set_state_list(UrlEndpoints.get_filter_list+1);
        Map<String,String> map=new HashMap<>();

       /* new NORMAL_ASYNCHTASK(new NORMAL_ASYNCHTASK.JOBJ_LISTENER() {
            @Override
            public void on_listener(JSONObject jsonobject, String loginApi) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },map.toString(),TOP_INNER_SCHOOL_FILTER_DEFAULT,"data").execute();
*/
    }




    @Override
    protected void onPostResume() {
        super.onPostResume();

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

               /* if(!vis_bl) {
                   *//* Animation animation2;
                    animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.filter_down);
                    id_filter_layout.setAnimation(animation2);
                    id_filter_layout.setVisibility(View.VISIBLE);
                    id_filter_upper.setVisibility(View.GONE);
                    id_filter_search.setVisibility(View.VISIBLE);*//*
                    *//*inner_filter_dialog=new Inner_Filter_Dialog(Filter_Activity.this,str_Url,Filter_Activity.this);
                    inner_filter_dialog.show();*//*
                    filter_dialogue_layout=new Filter_Dialogue_layout(new Filter_Dialogue_layout.FINAL_OBJ_LISTNER() {
                        @Override
                        public void onfinal_list(JSONArray data) {
                            filter_dialogue_layout.cancel();
                            recyclerView.setAdapter(new College_adapter(Main_Uni_List_Activity.this,data));
                        }
                    },Main_Uni_List_Activity.this);
                    filter_dialogue_layout.show();
                    vis_bl = false;
                }*/
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

            case R.id.id_call:

                String phoneNumber = "01294014664";

                if (checkPermission(android.Manifest.permission.CALL_PHONE)) {
                    String dial = "tel:" + phoneNumber;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(Main_Uni_List_Activity.this, "Permission Phone Call denied", Toast.LENGTH_SHORT).show();
                }

                break;



            case R.id.id_frm_chat:

            Log.d("dhdhdhdhdhdhdhdhdhdh","chartt");
                AuthOnClickWrapper authOnClickWrapper= new AuthOnClickWrapper(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        PreChatForm build = new PreChatForm.Builder()
                                .name(PreChatForm.Field.REQUIRED)
                                .email(PreChatForm.Field.REQUIRED)
                                .phoneNumber(PreChatForm.Field.OPTIONAL)
                                .message(PreChatForm.Field.OPTIONAL)
                                .build();

                        ZopimChat.SessionConfig department = new ZopimChat.SessionConfig()
                                .preChatForm(build)
                                .department("The date");

                        ZopimChatActivity.startActivity(MyApplication.getAppContext(), department);
                    }
                },getApplicationContext());

                break;

        }
    }

    @Override
    public void onAvailCourse(final JSONObject jsonObject, String strid) {

        /* availschool_class=new Availschool_class(new Availschool_class.FINAL_OBJ_LISTNER() {
            @Override
            public void onfinal_list(JSONObject data, String choose_class) {
                try {
                    availschool_class.cancel();
                    Log.d("nmnm",""+data);
                    Toast.makeText(Main_Uni_List_Activity.this, "Choose Courses :  "+data.getString("name"), Toast.LENGTH_SHORT).show();
                    Intent  i = new Intent(getApplicationContext(), School_Full_Detail_Activity.class);
                    i.putExtra("JSON_OBJ", "" + data);
                    i.putExtra("JSON_OBJ", "" + App_Static_Method.toMERGE_JSON(data,jsonObject));
                    startActivity(i);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },Main_Uni_List_Activity.this,avail_str_Url+strid);
        availschool_class.show();*/

    }

    @Override
    public void oncall_adapter(JSONObject jsonObject, String action) {
        try {

            if(action.equalsIgnoreCase("Brochure"))
            {
                Intent intent = new Intent(this, Website_Activity.class);
                intent.putExtra("Url_Web", action);
                startActivity(intent);
            }else {
               /* Intent i = null;
                String url = null;
                i = new Intent(getApplicationContext(), School_Full_Detail_Activity.class);
                Log.d("nmnmnm",""+jsonObject);
                i.putExtra("JSON_OBJ", "" + jsonObject);
                // i.putExtra("url",SCHOOL_AVAIL_CLASS_FULL+College_adapter.getJSONARRAy().getJSONObject(position).getString("id")+"&course_id="+College_adapter.getJSONARRAy().getJSONObject(position).getString("course_id"));
                //      Toast.makeText(School_Listing_Activity.this, ""+College_adapter.getJSONARRAy().getJSONObject(position).getString("id"), Toast.LENGTH_SHORT).show();
                startActivity(i);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class AuthOnClickWrapper implements View.OnClickListener {

        private View.OnClickListener mOnClickListener;
        private UserProfileStorage mUserProfileStorage;
        private Context mContext;

        public AuthOnClickWrapper(View.OnClickListener onClickListener, Context context){
            this.mOnClickListener = onClickListener;
            this.mUserProfileStorage = new UserProfileStorage(context);
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {
            final UserProfile profile = mUserProfileStorage.getProfile();

            //  if(StringUtils.hasLength("ahmed.shadab221@gmail.com")){
            mOnClickListener.onClick(v);
            //   }else{
            // showDialog();
            //  }
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

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onAvailCourse(final String str_type,final String clg_id,final String course_id,final String branch_id,final String branch_name) {
        // avail courses

    //    Log.d("ncvkcnvm",str_type+"  "+ clg_id);

         availableCoursesDialog=new Available_Courses_Dialog(new Available_Courses_Dialog.Dialog_Spinner_Listener() {
            @Override
            public void on_listdata(String s, String sid) {
                Log.d("ncvkcnvm",s+"  "+ sid);
                availableCoursesDialog.cancel();

                Intent i=new Intent(Main_Uni_List_Activity.this,College_Detail_Activity.class);
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
        },Main_Uni_List_Activity.this,UrlEndpoints.URL_GET_AVAIL_COURSES+"id="+clg_id+"&type="+str_type);
        availableCoursesDialog.show();
    }

  /*  private void set_applynow() {

    }*/
}
