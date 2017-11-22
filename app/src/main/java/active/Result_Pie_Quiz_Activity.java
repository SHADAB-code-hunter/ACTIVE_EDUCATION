package active;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.gt.active_education.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.util.ArrayList;
import network.VolleySingleton;
import pojo.Quiz_Model;
import utilities.App_Static_Method;
import utilities.MyApplication;
import utilities.UrlEndpoints;

/**
 * Created by akashdeep on 31-Mar-17.
 */
public class Result_Pie_Quiz_Activity extends AppCompatActivity{// implements Pie_Chart_data_Set {

    private ImageView id_back_btn;
    private Button btn_review;
    private String str_time_taken;
    private TextView textClock;
    private PieChart pieChart;
    private ArrayList<Quiz_Model> mDailyQuizList;
    private SharedPreferences Shrd_prefs;
    private String str_ans_list;
    private MyApplication myApplication=new MyApplication();
    private TextView total_time_quiz;
    private ArrayList<String> xVals=new ArrayList<>();
    private ArrayList<Entry> yvalues=new ArrayList<>();
    private PieData data;
    private Button id_btn_close;
    private PieDataSet dataSet;
    private int int_attempt,int_totle_ques,int_skip_ques,int_wng_ques,int_rgt_ques,int_user_marks,int_tome_taken;

    private int str_pie1,str_pie2,str_pie3,str_pie4,str_pie5;
    private TextView id_back_btn_quiz;
    private SharedPreferences sharedPreferences_theme;
    private String PROFILE_PATH= UrlEndpoints.Profile_pic;
    private Toolbar toolbar2;
    private LinearLayout id_result_back;
    private CircularImageView id_image_profile;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreborad_pie_layout);
        id_btn_close=(Button)findViewById(R.id.id_btn_close);
      //total_time_quiz=(TextView)findViewById(R.id.total_time_quiz);
        btn_review=(Button)findViewById(R.id.btn_review);
        toolbar2=(Toolbar)findViewById(R.id.toolbar2);
        id_back_btn_quiz=(TextView)findViewById(R.id.id_back_btn_quiz);
        id_result_back=(LinearLayout)findViewById(R.id.id_result_back);
        id_image_profile=(CircularImageView)findViewById(R.id.id_image_profile);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
        mDailyQuizList = this.getIntent().getParcelableArrayListExtra("quiz_ques_list");
         str_pie1=this.getIntent().getIntExtra("skip_ques",-1);
         str_pie2=this.getIntent().getIntExtra("wrng_ques",-1);
         str_pie3=this.getIntent().getIntExtra("rht_ques",-1);

        Shrd_prefs = getApplicationContext().getSharedPreferences("Sub_Answer_Array_List", 0);
        str_ans_list=Shrd_prefs.getString("SrdPrf_ArrayList", "");
        //Log.d("count","oncreate");
        //set_percentage();

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d("hkj", ""+(mDailyQuizList.size()));
                //Log.d("dghgfhg",""+str_ans_list.length());
                //Log.d("jkui",str_ans_list);

                Intent i =new Intent(getApplicationContext(),Review_Answer_Activity.class);
                i.putParcelableArrayListExtra("quiz_ques_list",mDailyQuizList);
                i.putExtra("str_anslist",str_ans_list);
                startActivity(i);

            }
        });
        id_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences shrd_prf_login=null;

        // hold st
        /*if(myApplication.is_User_Login(getBaseContext()))
        {
            shrd_prf_login = myApplication.get_shrd_prf_login(getBaseContext());
        }else if(!myApplication.is_User_Login(getBaseContext()))
        {
            ConnectionCheck.login_Dialog("Quiz_Calc_Page",Result_Pie_Quiz_Activity.this,false);
        }*/
 // hold end

        pieChart= (PieChart) findViewById(R.id.piechart);
        pieChart.setCenterTextSize(50);
        pieChart.setDrawCenterText(true);
        pieChart.setAnimationCacheEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);
        pieChart.performClick();
        pieChart.setEnabled(false);
        pieChart.setUsePercentValues(true);

        yvalues = new ArrayList<Entry>();
        ////Log.d("chartdata"," "+int_skip_ques+" "+int_rgt_ques+" "+int_wng_ques);
        // //Log.d("quiz_calc",int_skip_ques+" "+int_wng_ques+" "+int_rgt_ques);
        // //Log.d("count","3");

        float p1=Float.parseFloat(String.valueOf(str_pie1)); float p2=Float.parseFloat (String.valueOf(str_pie2)); float p3=Float.parseFloat (String.valueOf(str_pie3));
        //Log.d("pie_chart_value",""+p1+" "+ p2+ " "+p3);
       /* yvalues.add(new Entry(p1,1));
        yvalues.add(new Entry(p2, 2));
        yvalues.add(new Entry(p3, 3));*/
        yvalues.add(new Entry(str_pie1,1));
        yvalues.add(new Entry(str_pie2, 2));
        yvalues.add(new Entry(str_pie3, 3));


        dataSet = new PieDataSet(yvalues, "Quiz Results");
        dataSet.setColors(new int[]{getResources().getColor(R.color.skip_color),getResources().getColor(R.color.wrong_color),getResources().getColor(R.color.right_color)});

        xVals = new ArrayList<String>();
        xVals.add("Skip");
        xVals.add("Wrong");
        xVals.add("Right");
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        //Log.d("vcsdvc","ndcmndm");
        id_back_btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // hold start

      //  sharedPreferences_theme=myApplication.getTheme_Preferences(getApplicationContext(),KEY_COLOR_THEME,"0");
       /* if(myApplication.getTheme_Preferences(getApplicationContext(),KEY_COLOR_THEME,"0").equals("0"))
        {

        }else if(!(myApplication.getTheme_Preferences(getApplicationContext(),KEY_COLOR_THEME,"0").equals("0")))
        {
            set_theme(myApplication.getTheme_Preferences(getApplicationContext(),KEY_COLOR_THEME,"0"));
        }*/
      //  SharedPreferences sharedPref=myApplication.get_shrd_prf_login(getBaseContext());


      /*
       */

      /// hold end
        TextView tv=(TextView) findViewById(R.id.id_name_tv);
        tv.setText(App_Static_Method.session_type().get("mobile"));
        loadImages(id_image_profile, App_Static_Method.session_type().get("mobile"), mImageLoader);

    }

    private void loadImages(final CircularImageView id_imgView_profile,String login_pic,ImageLoader idImageProfile) {
      //Log.d("ddddddd", UrlEndpoints.Profile_pic+login_pic);
        idImageProfile.get(UrlEndpoints.Profile_pic+login_pic, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                id_imgView_profile.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Log.d("count","onpostresume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Log.d("count","onsaveinstencestate");
    }

    private void set_theme(SharedPreferences sharedPreferences_theme) {

/*
        switch (sharedPreferences_theme.getString(KEY_THEME_NUM, "")) {
            case "THEME1":
                //Log.d("hgdhghghgd", "THEME1");
                toolbar2.setBackgroundColor(Color.RED);
                id_result_back.setBackground(getResources().getDrawable(R.drawable.ic_blur_main));
                break;
            case "THEME2":
                //Log.d("hgdhghghgd", "THEME2");
                toolbar2.setBackgroundColor(Color.TRANSPARENT);
                id_result_back.setBackground(getResources().getDrawable(R.drawable.blur_img));
                break;
            case "THEME3":
                //Log.d("hgdhghghgd", "THEME3");
                toolbar2.setBackgroundColor(Color.BLUE);
                id_result_back.setBackground(getResources().getDrawable(R.drawable.ic_back_main));
                break;
        }
*/
    }
}
