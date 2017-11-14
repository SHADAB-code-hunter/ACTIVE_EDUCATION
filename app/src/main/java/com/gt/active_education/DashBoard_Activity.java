package com.gt.active_education;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.zopim.android.sdk.api.ZopimChat;

import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import active.Gift_Winner__Activity;
import active.Quiz_SubList_Activity;
import active.Take_Daily_Quiz_Activity;
import adapter.MoviesAdapter;
import Zend_Chat.UserProfile;
import Zend_Chat.UserProfileStorage;
import adapter.Banner_Adapter;
//import adapter.DashBoard_Menu_Adapter;

import adapter.Common_pojo_adapter;
import adapter.DashBoard_Menu_Adapter;
import adapter.Recycler_Drawer_Adapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import callbacks.Avail_Course_Listener;
import callbacks.Call_newDialog_Listener;
import callbacks.JOBJ_Listener;
import callbacks.Log_Out_Listener;
import callbacks.Profile_Image_Listener;
import callbacks.Spinner_Date_Listener;
import callbacks.Upcoming_List_LoadedListener;
import fab_below.FabSpeedDial;
import fragment.Agent_Profile_Detail_Fragment;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import pojo.Drawer_Pojo;
import pojo.Send_date_Model;
import pojo.Winner_Model;
import task.Asynch_Agent_Form_JObject;
import task.Asynch_Book_Responce;
import task.Asynch_Responce_OBJ;
import task.Load_Courses_Data;
import task.Load_Exams_Data;
import task.TaskLoad_List;
import utilities.AnimUtils;
import utilities.App_Static_Method;
import pojo.Cat_Model;
import utilities.CirclePageIndicator;
import utilities.Common_Pojo;
import utilities.ConnectionCheck;
import utilities.Custom_DialogClass;
import utilities.DroidDialog;
import utilities.Filter_Dialog;
import utilities.MyApplication;
import utilities.NotifyingScrollView;
import utilities.RecyclerTouchListener;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

import static utilities.App_Static_Method.session_type;
import static utilities.App_Static_Method.show_load_progress;
import static utilities.UrlEndpoints.GET_OFFER_BANNER;
import static utilities.UrlEndpoints.GET_PROFILE;
import static utilities.UrlEndpoints.UPDATE_PROFILE;

public class DashBoard_Activity extends AppCompatActivity implements View.OnClickListener , Upcoming_List_LoadedListener,
        Log_Out_Listener, Call_newDialog_Listener, Profile_Image_Listener, Avail_Course_Listener, Spinner_Date_Listener , JOBJ_Listener {

    private RecyclerView top_deal_recyclerView,top_univerty_recyclerView,top_college_recyclerView,top_school_recyclerView;
    private CustomGridLayoutManager verticleLayoutManager,verticleLayoutManager1;
    private ViewPager id_viewpager;
    private int currentPage;
    android.os.Handler handler,handler_vis;
    private List<Sample> samples;
    private CirclePageIndicator circleIndicator;
    boolean hidden = true;
    LinearLayout mRevealView;
    private Toolbar toolbar;
    ImageButton ib_gallery, ib_contacts, ib_location;
    ImageButton ib_video, ib_audio, ib_camera;
    SupportAnimator mAnimator;
    boolean mPressed = false;
    public TextView id_rght_popup_menu;
    public TextView id_tv_learn,id_tv_earn,id_tv_fun,id_logout_tv;
    private   Filter_Dialog filterDialog;
    private FabSpeedDial fab;
    private NotifyingScrollView scrollview;
    private static final int SCROLL_TO_TOP = - 1;
    private static final int SCROLL_TO_BOTTOM = 1;
    private static final int SCROLL_DIRECTION_CHANGE_THRESHOLD = 5;
    private int mScrollDirection = 0;
    private LinearLayout id_lnr_myprofl,id_lnr_bottom,id_linear_chat,id_login_lnr;
    private PulsatorLayout mPulsator;
    private RecyclerView id_top_courses_rv,id_top_exams_rv;
    private LinearLayout id_gallery_linear,id_linear_about_us,id_share_app_linear,id_rateapp_linear,id_contact_us_linear,id_lnr_need,id_need_drawer
            ,id_linear_call;
    private DrawerLayout drawer;
    private ProgressDialog progressDialog;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 101;
    /// butterknife
    @BindView(R.id.id_btn_test)Button id_btn_test;
   // @BindView(R.id.id_login_tv)TextView id_login_tv;
   /* @BindView(R.id.top_univerty_recyclerView) RecyclerView top_univerty_recyclerView;
      @BindView(R.id.top_college_recyclerView) RecyclerView top_college_recyclerView;
      @BindView(R.id.top_school_recyclerView) RecyclerView top_school_recyclerView;*/

    private RecyclerView rclv_menu_dwr;
    private boolean scroll_bln=false;
    private TextView id_rght_serach_menu;
    private boolean search_bl=false;
    private FrameLayout id_searchlayout;
    private ImageView id_tv_close,id_image_profile;
    private Picasso picasso;
    private MoviesAdapter mAdapter;
    private EditText  id_edt_search;
    private FrameLayout id_Learn_frm,id_earn_frm,id_fun_frm;
    private TextView id_name_mobile,id_location_add;
    private LinearLayout id_login_linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(!new ConnectionCheck(DashBoard_Activity.this).checkConnection()) {
            new ConnectionCheck(DashBoard_Activity.this).check_network(DashBoard_Activity.this);
            return;
        }
        //  id_tv_close=(ImageView) findViewById(R.id.id_tv_close);id_tv_close.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        id_rght_serach_menu=(TextView)findViewById(R.id.id_rght_serach_menu); id_rght_serach_menu.setOnClickListener(this);
        id_name_mobile=(TextView)findViewById(R.id.id_name_mobile);
        id_location_add=(TextView)findViewById(R.id.id_location_add);
        circleIndicator = (CirclePageIndicator)findViewById(R.id.titles);
        rclv_menu_dwr=(RecyclerView)findViewById(R.id.rclv_menu_dwr);
        id_image_profile=(ImageView)findViewById(R.id.id_image_profile);
        id_searchlayout=(FrameLayout)findViewById(R.id.id_searchlayout);id_searchlayout.setOnClickListener(this);
        id_edt_search=(EditText)findViewById(R.id.id_edt_search);
        mRevealView = (LinearLayout) findViewById(R.id.reveal_items);
        id_login_linear = (LinearLayout) findViewById(R.id.id_login_linear);id_login_linear.setOnClickListener(this);

        id_Learn_frm=(FrameLayout)findViewById(R.id.id_Learn_frm);id_Learn_frm.setOnClickListener(this);
        id_earn_frm=(FrameLayout)findViewById(R.id.id_earn_frm);id_earn_frm.setOnClickListener(this);
        id_fun_frm=(FrameLayout)findViewById(R.id.id_fun_frm);id_fun_frm.setOnClickListener(this);

        final Handler handler = new Handler();
        picasso = Picasso.with(this);
        Drawer_Pojo drawer_pojo=new Drawer_Pojo();
        List<Drawer_Pojo> list_View=new ArrayList<>();list_View.add(drawer_pojo);list_View.add(drawer_pojo);list_View.add(drawer_pojo);

        setSupportActionBar(toolbar);setupSamples();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode=new Explode();
            explode.excludeTarget(R.id.toolbar,true);
            getWindow().setExitTransition(explode);
        }
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        setupSamples();setupWindowAnimations();
        //getWindow.
       // id_need_drawer=(LinearLayout)findViewById(R.id.id_need_drawer);id_need_drawer.setOnClickListener(this);
        id_lnr_need=(LinearLayout)findViewById(R.id.id_lnr_need);id_lnr_need.setOnClickListener(this);
        App_Static_Method.btn_animation(id_lnr_need,DashBoard_Activity.this);
        id_top_exams_rv=(RecyclerView)findViewById(R.id.id_top_exams_rv);
        id_top_courses_rv=(RecyclerView)findViewById(R.id.id_top_courses_rv);
        id_rght_serach_menu=(TextView)findViewById(R.id.id_rght_serach_menu);id_rght_serach_menu.setOnClickListener(this);
        //id_logout_tv=(TextView)findViewById(R.id.id_logout_tv);id_logout_tv.setOnClickListener(this);
       // id_rght_popup_menu=(TextView)findViewById(R.id.id_rght_popup_menu);
        id_tv_learn=(TextView)findViewById(R.id.id_tv_learn);
        id_tv_earn=(TextView)findViewById(R.id.id_tv_earn);
        id_tv_fun=(TextView)findViewById(R.id.id_tv_fun);
     //   id_lnr_myprofl=(LinearLayout)findViewById(R.id.id_lnr_myprofl);id_lnr_myprofl.setOnClickListener(this);
       // id_login_lnr=(LinearLayout)findViewById(R.id.id_login_lnr);id_login_lnr.setOnClickListener(this);
        id_linear_chat=(LinearLayout)findViewById(R.id.id_linear_chat);
        id_linear_call=(LinearLayout)findViewById(R.id.id_linear_call);id_linear_call.setOnClickListener(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Log.d("widthsize",""+width);
        Log.d("heightsize",""+height);
        id_edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    new TaskLoad_List(DashBoard_Activity.this,UrlEndpoints.SEARCH_API+id_edt_search.getText().toString()).execute();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });


        findViewById(R.id.id_rght_agent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
                String str_tkn=sharedPreferences.getString("Login_Token", "na");
                SharedPreferences sharedPreferences_agent = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_PARTNER_Prefrence, 0);
                String agent_str_tkn=sharedPreferences_agent.getString("token", "na");
                String agent_str_type=sharedPreferences_agent.getString("type", "na");
                *//*editor.putString("login_Status", "L_OK");
                editor.putString("Login_Token", jsonObject.getString("token"));
                editor.putString("email", jsonObject.getString("email"));*//*
                String str_email=sharedPreferences.getString("email", "na");
                String str_token=sharedPreferences.getString("Login_Token", "na");
                String str_type=sharedPreferences.getString("type", "na");
                Log.d("gfhgfhgfhg",str_type+"  "+agent_str_type);

                if((agent_str_type.equals("na") )&& (str_type.equals("na") ) ){
                    startActivity(new Intent(getApplicationContext(), Agent_login_Activity.class));
                }else {
                    Log.d("sessiondata",get_full_session_data().toString());

                    if(!agent_str_type.equals("na"))
                    {
                        startActivity(new Intent(getApplicationContext(), Target_Circle_Activity.class));
                    }else if(!str_type.equals("na"))
                    {
                        startActivity(new Intent(getApplicationContext(), Agent_login_Activity.class));
                    }

                   *//* if(!str_tkn.equals("na")&& !(str_email.equals("na")) &&  !(str_type.equals("agent"))) {
                        // Toast.makeText(DashBoard_Activity.this, "You have already Login ff!!! ", Toast.LENGTH_SHORT).show();
                        show_dialog(" You have Already Login !!!!");
                    } else if (str_tkn.equals("na")) {
                        startActivity(new Intent(getApplicationContext(), Agent_login_Activity.class));
                    }
                    else if(!str_tkn.equals("na")&& !(str_email.equals("na")) &&  (str_type.equals("agent")))  {
                        startActivity(new Intent(getApplicationContext(), Agent_Profile_Activity.class));
                    }*//*
                }*/
                SharedPreferences shprf_seater = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE,0);
                switch (shprf_seater.getString("type", "na"))
                {
                    case "agent":
                        startActivity(new Intent(DashBoard_Activity.this, Agent_Profile_Activity.class));
                        //  finish();
                        break;
                    case "user":
                        startActivity(new Intent(DashBoard_Activity.this, User_Profile_Activity.class));
                        //  finish();
                        break;
                    case "seater":
                        startActivity(new Intent(DashBoard_Activity.this,Target_Circle_Activity.class));
                        //  finish();
                        break;
                    default:
                        startActivity(new Intent(DashBoard_Activity.this,Agent_login_Activity.class));
                        break;
                }

            }
        });

        id_linear_chat.setOnClickListener(new AuthOnClickWrapper(new View.OnClickListener() {
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

                ZopimChatActivity.startActivity(DashBoard_Activity.this, department);
            }
        },getApplicationContext()));

        scrollview=(NotifyingScrollView)findViewById(R.id.id_notifi_sv);
        startZoomInAnimation(id_tv_learn); startZoomInAnimation(id_tv_earn);
        startZoomInAnimation(id_tv_fun);

        id_lnr_bottom=(LinearLayout)findViewById(R.id.id_lnr_bottom);
        //ButterKnife.inject(this);// for fragment== ButterKnife.inject(this, view);
        ButterKnife.bind(this);
        scrollview.scrollTo(0, scrollview.getTop());
      //  top_univerty_recyclerView=(RecyclerView)findViewById(R.id.top_univerty_recyclerView);
     /*   top_college_recyclerView=(RecyclerView)findViewById(R.id.top_college_recyclerView);
        top_school_recyclerView=(RecyclerView)findViewById(R.id.top_school_recyclerView);*/
        top_deal_recyclerView =(RecyclerView)findViewById(R.id.dashboard_recyclerView);
        id_viewpager=(ViewPager)findViewById(R.id.id_viewpager);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        int[] Img_Array=new int[]{R.drawable.school,R.drawable.college,R.drawable.iuniversity,
                R.drawable.tr,R.drawable.coach,R.drawable.tra};

        String[] Tv_Array=new String[]{"School","College","University","ITI College","Coaching Center","Training Center"};

        //GridLayoutManager verticleLayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.VERTICAL, false);
        verticleLayoutManager=new CustomGridLayoutManager(getApplicationContext(),3);
        verticleLayoutManager.setScrollEnabled(false);

        top_deal_recyclerView.setLayoutManager(verticleLayoutManager);
        /*SamplesRecyclerAdapter samplesRecyclerAdapter = new SamplesRecyclerAdapter(this, samples);
        top_deal_recyclerView.setAdapter(samplesRecyclerAdapter);*/
        DashBoard_Menu_Adapter dashBoard_menu_adapter=new DashBoard_Menu_Adapter(getApplicationContext(),Img_Array,Tv_Array);
        top_deal_recyclerView.setAdapter(dashBoard_menu_adapter);
        top_deal_recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), top_deal_recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                       /*progressDialog = new ProgressDialog(DashBoard_Activity.this);
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                        progressDialog.setMessage(getString(R.string.Loading));*/
                        progressDialog =show_load_progress(DashBoard_Activity.this,getString(R.string.Loading));
                        Intent i=new Intent(getApplicationContext(),Filter_Activity.class);
                        String url=UrlEndpoints.URL_DEAL_CAT_MAIN+"type="+(position+1);
                        String img_path[]=getResources().getStringArray(R.array.category_string);
                        i.putExtra("url",""+url);
                        String[] cat_list = getResources().getStringArray(R.array.Category_List);
                        i.putExtra("title","Top "+cat_list[position] +" Deal List");
                        i.putExtra("img_path",UrlEndpoints.URL_CUSTOM_IMAGE_APTH+img_path[position]+"/");
                        startActivity(i);

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));
        id_top_courses_rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),id_top_courses_rv,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        TextView txt_id=(TextView)view.findViewById(R.id.txt_id);
                        Intent i=new Intent(getApplicationContext(),Filter_Activity.class);
                        String url=UrlEndpoints.GET_ALL_COURSES+"course="+txt_id.getText().toString();
                        Log.d("urffl",""+url);
                        i.putExtra("url",""+url);
                        i.putExtra("title","Top Courses List");
                        startActivity(i);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));
        id_top_exams_rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), id_top_exams_rv,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        TextView txt_id=(TextView)view.findViewById(R.id.txt_id);
                        Intent i=new Intent(getApplicationContext(),Exam_Detail_Activity.class);
                        i.putExtra("id",""+txt_id.getText().toString());
                        i.putExtra("title","Top Exams List");
                        startActivity(i);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));



        //setProgressView();
        setNotify_scrollView();

      //  findViewById(R.id.id_login_btn).setOnClickListener(this);

        final LinearLayout id_linear_find=(LinearLayout)findViewById(R.id.id_linear_find);
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(400);
                animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
                ArrayList<Animator> animatorList=new ArrayList<Animator>();
                ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(id_linear_find, "ScaleX", 0f, 1.2f, 1f);
                animatorList.add(scaleXAnimator);
                ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(id_linear_find, "ScaleY", 0f, 1.2f, 1f);
                animatorList.add(scaleYAnimator);
                animatorSet.playTogether(animatorList);
                //foundDevice.setVisibility(View.VISIBLE);
                animatorSet.start();
            }
        },3000);



       /* final RippleBackground rippleBackground2=(RippleBackground)findViewById(R.id.content_profile);
        rippleBackground2.startRippleAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(400);
                animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
                ArrayList<Animator> animatorList=new ArrayList<Animator>();
                ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(id_linear_find, "ScaleX", 0f, 1.2f, 1f);
                animatorList.add(scaleXAnimator);
                ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(id_linear_find, "ScaleY", 0f, 1.2f, 1f);
                animatorList.add(scaleYAnimator);
                animatorSet.playTogether(animatorList);
                //foundDevice.setVisibility(View.VISIBLE);
                animatorSet.start();
            }
        },3000);*/
        new Load_Courses_Data(DashBoard_Activity.this, UrlEndpoints.URL_TOP_COURSES).execute();
        new Load_Exams_Data(DashBoard_Activity.this, UrlEndpoints.URL_TOP_EXAMS).execute();

        int[] img_drwr=new int[]{R.drawable.ic_edit_prfl,R.drawable.ic_gallery,R.drawable.ic_gps,R.drawable.ic_share,
                R.drawable.ic_stars,R.drawable.ic_contactus, R.drawable.ic_info,
                R.drawable.ic_setting};
       String[] str_menu_name=new String[]{"My Profile","Gallery","Find Your Need","Share App","Rate This App","Contact Us",
       "About Us","Setting"};

        verticleLayoutManager1=new CustomGridLayoutManager(getApplicationContext(),1);
        verticleLayoutManager1.setScrollEnabled(false);
        rclv_menu_dwr.setLayoutManager(verticleLayoutManager1);
        rclv_menu_dwr.setAdapter(new Recycler_Drawer_Adapter(DashBoard_Activity.this,img_drwr,str_menu_name));
        rclv_menu_dwr.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rclv_menu_dwr,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {

                      /*  if(position!=2 || position!=8 || position!=5 ) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(100);
                                        set_drawer_click_listener(position);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }else {

                        }*/
                       // drawer.closeDrawer(Gravity.LEFT);
                        set_drawer_click_listener(position);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));


        scrollview.setOnTouchListener( new View.OnTouchListener( ) {

            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                switch ( event.getAction( ) ) {
                    case MotionEvent.ACTION_SCROLL:
                        Log.e( "SCROLL", "ACTION_st" );

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e( "SCROLL", "ACTION_SCROLL" );
                        break;
                    case MotionEvent.ACTION_DOWN:
                        Log.e( "SCROLL", "ACTION_DOWN" );
                        scroll_bln=false;
                        break;
                    case MotionEvent.ACTION_CANCEL:

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e( "SCROLL", "ACTION_UP" );

                        scroll_bln=true;
                        /**/

                        break;
                }
                return false;
            }
        } );

       App_Static_Method.set_profile_img(DashBoard_Activity.this);
       getHashKey();

        new Asynch_Responce_OBJ(new Asynch_Responce_OBJ.OBJ_LISTENER() {
            @Override
            public void obn_obj_find(JSONObject jsonObject) {

                try {
                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                    Log.d("knknggknknk", "" + jsonObject);
                    if (jsonArray.getJSONObject(0).has("image")) {
                        Picasso.with(DashBoard_Activity.this)
                                .load(GET_PROFILE + "partner/" + (jsonArray.getJSONObject(0).getString("image")))
                                //  .placeholder(R.drawable.ic_manav_rcahna_banner)   // optional
                                // .error(DRAWABLE RESOURCE)      // optional
                                // .resize(width, height)                        // optional
                                // .rotate(degree)                             // optional
                                .into(id_image_profile);
                        // id_image_profile.setImageResource();

                        id_name_mobile.setText(jsonArray.getJSONObject(0).getString("mobile"));
                        id_location_add.setText(jsonArray.getJSONObject(0).getString("address"));
                    }
                }catch (Exception e)
                {
                    Log.d("ecaprui",e.getMessage());
                }

            }
        }, UrlEndpoints.GET_PROFILE_DATA,session_type()).execute();

        new Asynch_Book_Responce(DashBoard_Activity.this,GET_OFFER_BANNER,session_type()).execute();
        set_logindrawer();
        // oncreate end
    }

    private void set_logindrawer() {

        if(session_type().get("token")==null)
        {
            TextView tv_menu_name=(TextView)findViewById(R.id.tv_menu_name);
            tv_menu_name.setText("Login");
            ImageView imageView=(ImageView)findViewById(R.id.flt_btn);
            imageView.setImageDrawable(MyApplication.getAppContext().getResources().getDrawable(R.drawable.ic_login));
        }else {
            TextView tv_menu_name=(TextView)findViewById(R.id.tv_menu_name);
            tv_menu_name.setText("Logout");
            ImageView imageView=(ImageView)findViewById(R.id.flt_btn);
            imageView.setImageDrawable(MyApplication.getAppContext().getResources().getDrawable(R.drawable.logout));
        }
    }


    private void getHashKey() {
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY_KEY_HASH:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
/*

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.LogOut));


        if(!App_Static_Method.is_login(DashBoard_Activity.this)) {
            Log.d("logoufft", "hdhdhdd");
            App_Static_Method.logout(DashBoard_Activity.this);
        }
*/


    }
    @Override
    public void onSpinner_Date(String str_choose_daet,View v) {
        //Log.d("getdata",str_choose_daet);


        String str_url=UrlEndpoints.URL_Send_DAte+str_choose_daet;
        get_intent_data(str_url,"GIFT_VOUVHER");

       /* switch(v.getId())
        {
            case R.id.id_tv_gift_voucher:
                String str_url=UrlEndpoints.URL_Send_DAte+str_choose_daet;
                get_intent_data(str_url,"GIFT_VOUVHER");
                break;
            case R.id.id_tv_winner:
                String str_winner_url=UrlEndpoints.URL_Winner+str_choose_daet;
                get_intent_data(str_winner_url,"WINNER");
                break;
        }*/

    }
    @Override
    protected void onStop() {
        super.onStop();
        if(progressDialog!=null)
        progressDialog.cancel();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(progressDialog!=null)
        progressDialog.cancel();

        Log.d("gggggdd","jfghgrt");
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

           /*
           try{for (int i = 0; i < pager_pages; i++) {
                dots[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
            dots[position].setTextColor(getResources().getColor(R.color.amber_500));
            }catch(Exception e){}*/
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void set_drawer_click_listener(int postion) {
        switch (postion)
        {
            case 0: // profile

                SharedPreferences shprf_seater = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE,0);
                switch (shprf_seater.getString("type", "na"))
                {
                    case "agent":
                        startActivity(new Intent(DashBoard_Activity.this, Agent_Profile_Activity.class));
                      //  finish();
                        break;
                    case "user":
                        startActivity(new Intent(DashBoard_Activity.this, User_Profile_Activity.class));
                      //  finish();
                        break;
                    case "seater":
                        startActivity(new Intent(DashBoard_Activity.this,Target_Circle_Activity.class));
                      //  finish();
                        break;
                    case "na":
                        startActivity(new Intent(DashBoard_Activity.this,Agent_login_Activity.class));
                        break;
                }
              /*  if(App_Static_Method.is_any_login(DashBoard_Activity.this).equals("agent")) {

                }else {
                    Toast.makeText(this, "Please Login !!!!!", Toast.LENGTH_SHORT).show();
                }
*/
                break;
            case 1:// gallery
               // drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(getApplicationContext(),Gallery_Activity.class));

                break;

            case 2: // need
              //  drawer.closeDrawer(Gravity.LEFT);
                filterDialog=new Filter_Dialog(DashBoard_Activity.this);
                filterDialog.show();

                break;
            case 3: // share app
              //  drawer.closeDrawer(Gravity.LEFT);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=com.active.gt.active_quiz&hl=en");
                sendIntent.setType("text/plain");

                startActivityForResult(sendIntent,101);

                break;

            case 4: // rate this app
              //  drawer.closeDrawer(Gravity.LEFT);
                Uri uri = Uri.parse("market://details?id=" + "com.active.gt.active_quiz");
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    //startActivity(myAppLinkToMarket);
                    startActivityForResult(myAppLinkToMarket,105);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
                }

                break;

            case 5:// contact us
            //    drawer.closeDrawer(Gravity.LEFT);
               /* CustomDialogClass dialogClass=new CustomDialogClass(DashBoard_Activity.this,);
                dialogClass.show();*/

                break;
            case 6:// About us
             //   drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(getApplicationContext(),About_Us_Activity.class));

                break;
            case 7:// setting
              //  drawer.closeDrawer(Gravity.LEFT);
                /*CustomDialogClass dialogClass=new CustomDialogClass(DashBoard_Activity.this);
                dialogClass.show();*/
                break;
           /* case 8:// Login/ logout
             // drawer.closeDrawer(Gravity.LEFT);
               logout_method();
                break;*/

          /*  case R.id.id_linear_call:

                String phoneNumber = "01294014664";

                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    String dial = "tel:" + phoneNumber;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(DashBoard_Activity.this, "Permission Phone Call denied", Toast.LENGTH_SHORT).show();
                }
                break;
*/

        }

    }

    private void logout_method() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.LogOut));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    App_Static_Method.logout(DashBoard_Activity.this);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    private void setNotify_scrollView() {
        scrollview.setOnScrollChangedListener(new NotifyingScrollView.OnScrollChangedListener() {

            int mScrollPosition;

            public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                Log.d("tst",""+Math.abs(t - mScrollPosition));
                if(Math.abs(t - mScrollPosition) >= SCROLL_DIRECTION_CHANGE_THRESHOLD) {

                    onScrollPositionChanged(mScrollPosition, t);

                    //     Toast.makeText(context,"oldpos..."+mScrollPosition+"newposs..."+t,Toast.LENGTH_SHORT).show();
                }

                mScrollPosition = t;
            }
        });

    }

    @SuppressLint("ResourceType")
    private void setupSamples() {
        samples = Arrays.asList(
                new Sample(ContextCompat.getColor(this, R.color.sample_red), "Transitions"),
                new Sample(ContextCompat.getColor(this, R.color.sample_blue), "Shared Elements"),
                new Sample(ContextCompat.getColor(this, R.color.sample_green), "View animations"),
                new Sample(ContextCompat.getColor(this, R.color.sample_yellow), "Circular Reveal Animation")
        );
    }
    private void onScrollPositionChanged(int oldScrollPosition, int newScrollPosition) {

        int newScrollDirection;

        if(newScrollPosition < oldScrollPosition) {
            newScrollDirection = SCROLL_TO_TOP;

            if(newScrollDirection != mScrollDirection) {
                mScrollDirection = newScrollDirection;

                Animation animation;
                animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.up_from_bottom);

                id_lnr_bottom.setAnimation(animation);
            }
            id_lnr_bottom.setVisibility(View.VISIBLE);

        } else {
            newScrollDirection = SCROLL_TO_BOTTOM;
            if (newScrollDirection != mScrollDirection) {
                mScrollDirection = newScrollDirection;

                Animation animation2;
                animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.down_from_top);
                id_lnr_bottom.setAnimation(animation2);
            }
            id_lnr_bottom.setVisibility(View.INVISIBLE);
            if (scroll_bln) {
                handler_vis = new android.os.Handler();
                handler_vis.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Animation animation;
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up_from_bottom);

                        id_lnr_bottom.setAnimation(animation);
                        id_lnr_bottom.setVisibility(View.VISIBLE);

                    }
                }, 700);
            }
        }

    }

    @Override
    public void onBackPressed() {
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (search_bl) {
            if(mAdapter!=null)
            mAdapter.clearApplications();
            id_edt_search.setText("");
            findViewById(R.id.id_lnr_search).setVisibility(View.INVISIBLE);
            popup_menu();
            search_bl=false;
        }else {
            super.onBackPressed();
        }
    }


        private void set_recycle_view(RecyclerView recycle_view)
        {
            GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.HORIZONTAL, false);
            recycle_view.setLayoutManager(horizontal_LayoutManager);

        }


    @OnClick(R.id.id_btn_test)
    public void sayHi(View o_r_v) {
        Toast.makeText(DashBoard_Activity.this, "RecyclerView", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            /*case R.id.id_login_btn:
                SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence,0);
                String str_email=sharedPreferences.getString("email", "na");
                String str_token=sharedPreferences.getString("Login_Token", "na");
                if(!(str_email.equals("na")) && !(str_token.equals("na"))) {
                    Toast.makeText(this, "You have Alreday Login !!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                 //   Toast.makeText(this, "Login !!!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),User_Login_Activity.class));
                }
                break;*/

            case R.id.id_lnr_need:
                filterDialog=new Filter_Dialog(DashBoard_Activity.this);
                filterDialog.show();
                break;

            case R.id.id_rght_serach_menu:
                //search finction
                if (!search_bl) {

                    App_Static_Method.btn_animation(findViewById(R.id.id_lnr_search),getApplicationContext());
                    findViewById(R.id.id_lnr_search).setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    popup_menu();
                    search_bl=true;
                }else if(search_bl) {
                  //  App_Static_Method.btn_animation(findViewById(R.id.id_lnr_search),getApplicationContext());
                    findViewById(R.id.id_lnr_search).setVisibility(View.INVISIBLE);
                    popup_menu();
                    search_bl=false;
                }
                break;
            case R.id.id_linear_call:

                String phoneNumber = "01294014664";

                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    String dial = "tel:" + phoneNumber;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(DashBoard_Activity.this, "Permission Phone Call denied", Toast.LENGTH_SHORT).show();
                }
                break;

           /* case R.id.id_tv_close:
                if (search_bl) {
                    findViewById(R.id.id_lnr_search).setVisibility(View.INVISIBLE);
                    popup_menu();
                    search_bl=false;
                }*//*else if(frame_bl)
                {
                    id_frame_layout.setVisibility(View.VISIBLE);
                }*/

            case R.id.id_searchlayout:

                new TaskLoad_List(DashBoard_Activity.this,UrlEndpoints.SEARCH_API+id_edt_search.getText().toString()).execute();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;

            case R.id.id_Learn_frm:

                if(session_type().get("type").equals("user")) {
                startActivity(new Intent(getApplicationContext(), Quiz_SubList_Activity.class));
                }else
                    {
                        Toast.makeText(this, "Please Login as User !!", Toast.LENGTH_SHORT).show();
                      /*  new DroidDialog.Builder(this)
                                .icon(R.drawable.ic_null_list)
                                .title("Please Login as User !!")
                                .cancelable(true, false)
                                .negativeButton("Cancle", new DroidDialog.onNegativeListener() {
                                    @Override
                                    public void onNegative(Dialog droidDialog) {
                                        droidDialog.dismiss();
                                        // Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                *//*.neutralButton("Network Setting", new DroidDialog.onNeutralListener() {
                                    @Override
                                    public void onNeutral(Dialog droidDialog) {
                                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS );
                                        startActivity(intent);
                                        droidDialog.dismiss();
                                        // Toast.makeText(getApplicationContext(), "Skip", Toast.LENGTH_SHORT).show();
                                    }
                                })*//*
                                .typeface("regular.ttf")
                                .animation(AnimUtils.AnimZoomInOut)
                                *//*.color(R.color.colorAccent, R.color.colorPrimaryDark),
                                        ContextCompat.getColor(this, R.color.colorPrimaryDark))
                                .divider(true, ContextCompat.getColor(this, R.color.colorPrimaryDark))*//*
                                .show();*/
                    }
                break;
            case R.id.id_earn_frm:

                if(session_type().get("type").equals("user")) {
                    Custom_DialogClass cdd = new Custom_DialogClass(DashBoard_Activity.this, v);
                    cdd.show();
                }else
                {
                    Toast.makeText(this, "Please Login as User !!", Toast.LENGTH_SHORT).show();
                  /*  new DroidDialog.Builder(this)
                            .icon(R.drawable.ic_null_list)
                            .title("Please Login as User !!")
                            .cancelable(true, false)
                            .negativeButton("Cancle", new DroidDialog.onNegativeListener() {
                                @Override
                                public void onNegative(Dialog droidDialog) {
                                    droidDialog.dismiss();
                                    // Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                                }
                            })
                                *//*.neutralButton("Network Setting", new DroidDialog.onNeutralListener() {
                                    @Override
                                    public void onNeutral(Dialog droidDialog) {
                                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS );
                                        startActivity(intent);
                                        droidDialog.dismiss();
                                        // Toast.makeText(getApplicationContext(), "Skip", Toast.LENGTH_SHORT).show();
                                    }
                                })*//*
                            .typeface("regular.ttf")
                            .animation(AnimUtils.AnimZoomInOut)
                            .color(ContextCompat.getColor(this, R.color.colorAccent), ContextCompat.getColor(this, R.color.colorPrimaryDark),
                                    ContextCompat.getColor(this, R.color.colorPrimaryDark))
                            .divider(true, ContextCompat.getColor(this, R.color.colorPrimaryDark))
                            .show();*/
                }
                break;
            case R.id.id_fun_frm:

                if(session_type().get("type").equals("user")) {
                    startActivity(new Intent(getApplicationContext(), Take_Daily_Quiz_Activity.class));
                }else
                {
                    Toast.makeText(this, "Please Login as User !!", Toast.LENGTH_SHORT).show();
                  /*  new DroidDialog.Builder(this)
                            .icon(R.drawable.ic_null_list)
                            .title("Please Login as User !!")
                            .cancelable(true, false)
                            .negativeButton("Cancle", new DroidDialog.onNegativeListener() {
                                @Override
                                public void onNegative(Dialog droidDialog) {
                                    droidDialog.dismiss();
                                    // Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                                }
                            })
                                *//*.neutralButton("Network Setting", new DroidDialog.onNeutralListener() {
                                    @Override
                                    public void onNeutral(Dialog droidDialog) {
                                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS );
                                        startActivity(intent);
                                        droidDialog.dismiss();
                                        // Toast.makeText(getApplicationContext(), "Skip", Toast.LENGTH_SHORT).show();
                                    }
                                })*//*
                            .typeface("regular.ttf")
                            .animation(AnimUtils.AnimZoomInOut)
                            .color(ContextCompat.getColor(this, R.color.colorAccent), ContextCompat.getColor(this, R.color.colorPrimaryDark),
                                    ContextCompat.getColor(this, R.color.colorPrimaryDark))
                            .divider(true, ContextCompat.getColor(this, R.color.colorPrimaryDark))
                            .show();*/
                }
                break;
            case R.id.id_login_linear:
                set_login_switch();
                break;
        }
    }

    private void set_login_switch() {

        if(session_type().get("token")!=null)
        {
            logout_method();
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }else {

           startActivity(new Intent(getBaseContext(),Agent_login_Activity.class));
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }
    }

    // conticnue  http://activeeduindia.com/admin/webservices/getMainList.php?type=2&state=1&city=1&branch=1&course=1&mode=1
    @Override
    protected void onPause() {
        super.onPause();

        if(filterDialog!=null)
        filterDialog.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
       // Toast.makeText(this, "Got Result " + String.valueOf(resultCode)+" bfr "+requestCode,     Toast.LENGTH_SHORT).show();
        Log.d("sfrt","Got Result " + String.valueOf(resultCode)+" bfr "+requestCode);
        if(requestCode==101)
        {
            Random r = new Random();
            int Low = 10;
            int High = 100000000;
            int Result = r.nextInt(High-Low) + Low;
            Log.d("uniuwnum",""+Result);
        }

        if(requestCode==105)
        {

          // getUserId and send to backend
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies) {

        Log.d("listmovie",listMovies.toString());

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.id_search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false));
        mAdapter = new MoviesAdapter(listMovies, picasso, DashBoard_Activity.this,UrlEndpoints.SEARCH_API);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies, String poss) {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE :
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // dial.setEnabled(true);
                    //  Toast.makeText(this, "You can call the number by clicking click the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    @Override
    public void onUpcomingcourses(List<Common_Pojo> common_pojos) {
        Log.d("sgfrt",common_pojos.toString());
        Common_pojo_adapter mAdapter = new Common_pojo_adapter(DashBoard_Activity.this,common_pojos);
        GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),4,
                LinearLayoutManager.VERTICAL, false);
        id_top_courses_rv.setLayoutManager(horizontal_LayoutManager);
        id_top_courses_rv.setItemAnimator(new DefaultItemAnimator());
        id_top_courses_rv.setAdapter(mAdapter);

    }

    @Override
    public void onUpcomingexams(List<Common_Pojo> common_pojos) {
        Common_pojo_adapter mAdapter = new Common_pojo_adapter(DashBoard_Activity.this,common_pojos);
        GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),4,
                LinearLayoutManager.VERTICAL, false);
        id_top_exams_rv.setLayoutManager(horizontal_LayoutManager);
        id_top_exams_rv.setItemAnimator(new DefaultItemAnimator());
        id_top_exams_rv.setAdapter(mAdapter);
    }

    @Override
    public void on_id_listener(String str_id) {
        switch (str_id)
        {
            case "WEBSITE":

                Intent intent=new Intent(DashBoard_Activity.this,Website_Activity.class);
                intent.putExtra("Url_Web","http://activeeduindia.com/active/");
                startActivity(intent);
                break;

            case "CALL":

                String phoneNumber = "01294014664";

                if (checkPermission(Manifest.permission.CALL_PHONE)) {
                    String dial = "tel:" + phoneNumber;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(DashBoard_Activity.this, "Permission Phone Call denied", Toast.LENGTH_SHORT).show();
                }

                break;

            case "V_CALL":

                Toast.makeText(DashBoard_Activity.this, "Video Call Not Available Yet", Toast.LENGTH_SHORT).show();

                break;

            case "CHAT":

                AuthOnClickWrapper authOnClickWrapper=  new AuthOnClickWrapper(new View.OnClickListener() {
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

                        ZopimChatActivity.startActivity(DashBoard_Activity.this, department);
                    }
                },getApplicationContext());
                break;


        }
    }

    @Override
    public void on_profile_image_listener(String s) {

        Log.d("url_image",""+s);
       // if(s!=null) {
            Glide.with(DashBoard_Activity.this).load(s)
                    .thumbnail(0.5f)
                    .into(id_image_profile);
     //   }else {
      //      id_image_profile.setImageResource(R.drawable.ic_profile);
     //   }
    }

    @Override
    public void onAvailCourse(String str_id, String c_id, String course_id, String branch_id, String c_branch) {
        // availcourses
    }

    @Override
    public void onLJsonLoaded(JSONObject jsonObject) {

    }

    @Override
    public void onLJsonLoaded_new(JSONObject jsonObject) {
            // continue
        Log.d("jjkjk",""+jsonObject);

        try {
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            id_viewpager.setAdapter(new Banner_Adapter(getApplicationContext(),jsonArray));

     /*   final int NUM_PAGES=jsonArray.length();
        final float density = getResources().getDisplayMetrics().density;
        Log.d("dhghdghgfffh",""+jsonArray.length());
        // Auto start of viewpager

        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                id_viewpager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 000, NUM_PAGES*2500);*/

     /*   circleIndicator.setViewPager(id_viewpager);
        circleIndicator.setOnPageChangeListener(viewPagerPageChangeListener);*/

     //Log.d("knknknknk",GET_PROFILE+"/user/"+(jsonArray.getJSONObject(0).getString("image")));
     Log.d("knknknknk",""+jsonArray.getJSONObject(0));
        if(jsonArray.getJSONObject(0).has("image"))
        {
            Picasso.with(DashBoard_Activity.this)
                    .load(GET_PROFILE+"partner/"+(jsonArray.getJSONObject(0).getString("image")))
                    //  .placeholder(R.drawable.ic_manav_rcahna_banner)   // optional
                    // .error(DRAWABLE RESOURCE)      // optional
                    // .resize(width, height)                        // optional
                    // .rotate(degree)                             // optional
                    .into(id_image_profile);
           // id_image_profile.setImageResource();
        }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class CustomGridLayoutManager extends GridLayoutManager {
        private boolean isScrollEnabled = false;

        public CustomGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }


        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
    }
    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }


    private void popup_menu() {

        if (!mPressed) {
            mPressed = true;
            int cx = (mRevealView.getLeft() + mRevealView.getRight());
            int cy = mRevealView.getTop();
            int endradius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());
            mAnimator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, endradius);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.setDuration(200);

            if (hidden) {
                mRevealView.setVisibility(View.VISIBLE);
                mAnimator.start();
                hidden = false;
            }
        }
        else {
            if (mAnimator != null && !mAnimator.isRunning()) {
                mAnimator = mAnimator.reverse();
                mAnimator.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                        mPressed = false;

                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }

                });
                mAnimator.start();
            }


        }

    }

    public void startZoomInAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_animation);
        view.startAnimation(animation);
    }

    class AuthOnClickWrapper implements View.OnClickListener {

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

        private void showDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage(R.string.dialog_auth_title)
                    .setPositiveButton(R.string.dialog_auth_positive_btn, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                          //  startActivity(new Intent(mContext, CreateProfileActivity.class));
                        }
                    })
                    .setNegativeButton(R.string.dialog_auth_negative_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Intentionally empty
                        }
                    });
            builder.create().show();
        }
    }
    @Override
    public void on_logout(boolean bl) {

        Log.d("blelogin",""+bl);
        if(bl)
        {
            SharedPreferences sharedPreferences;
            SharedPreferences shprf_ = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE,0);
            switch (shprf_.getString("type", "na"))
            {
                case "agent":
                    sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_PARTNER_Prefrence, 0);
                    SharedPreferences.Editor editor1 = sharedPreferences.edit();
                    editor1.clear().commit();
                    shprf_.edit().clear().commit();
                    break;
                case "user":
                    sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
                    SharedPreferences.Editor editor2 = sharedPreferences.edit();
                    editor2.clear().commit();
                    shprf_.edit().clear().commit();
                    break;
                case "seater":
                    sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_Seater_Pref, 0);
                    SharedPreferences.Editor editor3 = sharedPreferences.edit();
                    editor3.clear().commit();
                    shprf_.edit().clear().commit();
                    break;

            }

            Log.d("ssssssww","dffffff"+bl);
            id_image_profile.setImageResource(R.drawable.ic_profile);
            if(progressDialog!=null)
                progressDialog.cancel();

            set_logindrawer();
        }else if(!bl){

            if(progressDialog!=null)
            progressDialog.cancel();
            Log.d("notlogout","dffffff"+bl);

            set_logindrawer();

        }
    }
    private void show_dialog(String str_dialog){
        AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard_Activity.this);
        builder.setMessage(str_dialog)
                .setPositiveButton(" Cancle ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  startActivity(new Intent(mContext, CreateProfileActivity.class));
                    }
                });

        builder.create().show();
    }
    private void get_intent_data(String str_url, final String str_dialog_status) {
        //Log.d("vvbhv",str_url);
        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,str_url,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseObj) {
                //Log.d("hjrtibbvcc", responseObj.toString()+" "+str_dialog_status);
                try {
                    if(responseObj.has("msg"))
                    {
                      /*  mPlay= MediaPlayer.create(New_Dashboard_Activity.this, R.raw.dialog_aud);
                        mPlay.start();
                        ConnectionCheck.list_not_get(New_Dashboard_Activity.this," Gift Voucher List Not Get");*/
                    }
                    else if(str_dialog_status.equals("GIFT_VOUVHER"))
                    {

                        ArrayList<Send_date_Model> arrayList = new ArrayList<>();
                        JSONArray array = responseObj.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            //Log.d("bcjkdcb","cm,,");
                            Send_date_Model sendDateModel = new Send_date_Model();
                            JSONObject json = array.getJSONObject(i);
                            sendDateModel.setId(json.getString("id"));
                            sendDateModel.setName(json.getString("name"));
                            sendDateModel.setImage(json.getString("image"));
                            sendDateModel.setRank(json.getString("rank"));
                            sendDateModel.setRdate(json.getString("rdate"));
                            sendDateModel.setAdded_date(json.getString("added_date"));
                            sendDateModel.setIs_active(json.getString("is_active"));
                            arrayList.add(sendDateModel);
                        }
                        //Log.d("lidtsta", String.valueOf(arrayList));
                        if (arrayList.size() != 0) {
                            //Log.d("hjfhlis", "" + arrayList.size());
                            Intent i = new Intent(getApplicationContext(), Gift_Winner__Activity.class);
                            i.putParcelableArrayListExtra("list", arrayList);
                            i.putExtra("str_dialog_status", str_dialog_status);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        }
                    }
                    //  finish();

                    else if(str_dialog_status.equals("WINNER"))
                    {
                        //Log.d("hdgch", "dbcdc");
                        //Log.d("ojbject", String.valueOf(responseObj));
                        ArrayList<Winner_Model> arrayList = new ArrayList<>();
                        // arrayList=null;
                        if(responseObj.has("data")) {
                            JSONArray array = responseObj.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                Winner_Model sendDateModel = new Winner_Model();
                                JSONObject json = array.getJSONObject(i);
                                //sendDateModel.setUid(json.getString("id"));
                                sendDateModel.setUname(json.getString("uname"));
                                sendDateModel.setTotal_marks(json.getString("total_marks"));
                                sendDateModel.setUser_marks(json.getString("user_marks"));
                                sendDateModel.setPer(json.getString("per"));
                                // sendDateModel.setTotal_attempted(json.getString("total_attempted"));
                                sendDateModel.setImage(json.getString("image"));
                                arrayList.add(sendDateModel);
                            }
                            //  //Log.d("lidtsta", String.valueOf(arrayList));
                            if (arrayList.size() != 0) {
                                //Log.d("winbnblidst", "" + arrayList);
                                Intent i = new Intent(getApplicationContext(), Gift_Winner__Activity.class);
                                i.putParcelableArrayListExtra("list", arrayList);
                                i.putExtra("str_dialog_status", str_dialog_status);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            }
                        }else if(responseObj.has("msg"))
                        {
                          /*  mPlay= MediaPlayer.create(New_Dashboard_Activity.this, R.raw.dialog_aud);
                            mPlay.start();
                            ConnectionCheck.list_not_get(New_Dashboard_Activity.this,"Winner List Not Get");*/
                        }

                        //  finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley_error_otp_verify", "Error: " + error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(req);

    }
}
