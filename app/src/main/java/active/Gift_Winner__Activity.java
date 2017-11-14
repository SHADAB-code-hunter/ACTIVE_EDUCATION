package active;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.gt.active_education.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Bubble_explosion.LiquidRadioButton;
import adapter.Gift_Voucher_Adapter;
import network.VolleySingleton;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import pojo.Send_date_Model;
import pojo.Winner_Model;
import utilities.UrlEndpoints;

/**
 * Created by GT on 5/27/2017.
 */

public class Gift_Winner__Activity extends AppCompatActivity {
    private RecyclerView id_gift_lv;
    private ArrayList<Send_date_Model> send_dta_arrayList;
    private ArrayList<Winner_Model> winner_arrayList;
    private Gift_Voucher_Adapter giftVoucherAdapter;
   // private Winner_Adapter winner_adapter;
    private TextView id_back_quicz_Quick_btn;
    private String str_dialog_status;
    private TextView toolbar_title;
    private TextView id_name_rank1,id_rank1,id_name_rank2,id_rank2,id_name_rank3,id_rank3,id_name_rank4,id_rank4,id_name_rank5,id_rank5;
    private CircularImageView  id_img_rank1,id_img_rank2,id_img_rank3,id_img_rank4,id_img_rank5;
    private FrameLayout id_id_winner_framlayout;
    private VolleySingleton mVolleySingleton;
    private ImageLoader mImageLoader;

    private LiquidRadioButton id_buble_radio;

    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private Handler customHandler = new Handler();
    private Timer swipeTimer;
    private Handler handler;
    private Runnable Update;
    long SINGLE_SLOT_TIME=0L;
    public KonfettiView konfettiView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_voucher);
        id_gift_lv=(RecyclerView)findViewById(R.id.id_gift_lv);
        id_back_quicz_Quick_btn=(TextView)findViewById(R.id.id_back_quicz_Quick_btn);
        id_id_winner_framlayout=(FrameLayout)findViewById(R.id.id_id_winner_framlayout);
        id_id_winner_framlayout.setVisibility(View.GONE);id_gift_lv.setVisibility(View.GONE);
        toolbar_title=(TextView)findViewById(R.id.toolbar_title);
        id_name_rank1=(TextView)findViewById(R.id.id_name_rank1);  id_name_rank2=(TextView)findViewById(R.id.id_name_rank2); id_name_rank3=(TextView)findViewById(R.id.id_name_rank3);
        id_name_rank4=(TextView)findViewById(R.id.id_name_rank4);  id_name_rank5=(TextView)findViewById(R.id.id_name_rank5);
        id_img_rank1=(CircularImageView)findViewById(R.id.id_img_rank1);
        id_img_rank2=(CircularImageView)findViewById(R.id.id_img_rank2);
        id_img_rank3=(CircularImageView)findViewById(R.id.id_img_rank3);
        id_img_rank4=(CircularImageView)findViewById(R.id.id_img_rank4);
        id_img_rank5=(CircularImageView)findViewById(R.id.id_img_rank5);
        id_rank1=(TextView)findViewById(R.id.id_rank1);   id_rank2=(TextView)findViewById(R.id.id_rank2);  id_rank3=(TextView)findViewById(R.id.id_rank3);
        id_rank4=(TextView)findViewById(R.id.id_rank4);  id_rank5=(TextView)findViewById(R.id.id_rank5);
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
        str_dialog_status=this.getIntent().getStringExtra("str_dialog_status");
        GridLayoutManager verticleLayoutManager = new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.VERTICAL,false);
        id_gift_lv.setLayoutManager(verticleLayoutManager);

        if(str_dialog_status.equals("WINNER"))
        {
            id_id_winner_framlayout.setVisibility(View.VISIBLE);
            winner_arrayList = this.getIntent().getParcelableArrayListExtra("list");
            //Log.d("winnerdsfe", ""+winner_arrayList);
           /* winner_adapter=new Winner_Adapter(getApplicationContext(),winner_arrayList);
            id_gift_lv.setAdapter(winner_adapter);*/
            toolbar_title.setText("Winner Peoples");
            setRank_Data();

        }else if(str_dialog_status.equals("GIFT_VOUVHER"))
        {   id_gift_lv.setVisibility(View.VISIBLE);
            send_dta_arrayList = this.getIntent().getParcelableArrayListExtra("list");
            //Log.d("fhvhfvh", ""+send_dta_arrayList.size());
            giftVoucherAdapter=new Gift_Voucher_Adapter(getApplicationContext(),send_dta_arrayList);
            id_gift_lv.setAdapter(giftVoucherAdapter);
            toolbar_title.setText("Gift Voucher");
        }

        id_back_quicz_Quick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        start_PTimer();

        konfettiView = (KonfettiView)findViewById(R.id.konfettiView);
        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(false)
                .setTimeToLive(10000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5f))
                .setPosition(0f, konfettiView.getWidth() + 1000f, -150f, -150f)
                .stream(600, 60000L);
    }

    private void setRank_Data() {
        int winner_size=winner_arrayList.size();

        id_name_rank1.setText(  winner_arrayList.get(0).getUname());id_name_rank2.setText(  winner_arrayList.get(1).getUname());id_name_rank3.setText(  winner_arrayList.get(2).getUname());
        id_name_rank4.setText(  winner_arrayList.get(3).getUname());id_name_rank5.setText(  winner_arrayList.get(4).getUname());

      /*  id_rank1.setText(  winner_arrayList.get(0).get());id_rank2.setText(  winner_arrayList.get(1).getUname());id_rank3.setText(  winner_arrayList.get(2).getUname());
        id_rank4.setText(  winner_arrayList.get(3).getUname());id_rank5.setText(  winner_arrayList.get(4).getUname());*/
      /*  id_img_rank1,id_img_rank2,id_img_rank3,id_img_rank4,id_img_rank5;*/
        loadImages(id_img_rank1,mImageLoader,winner_arrayList.get(0).getImage()); loadImages(id_img_rank2,mImageLoader,winner_arrayList.get(1).getImage());
        loadImages(id_img_rank3,mImageLoader,winner_arrayList.get(2).getImage()); loadImages(id_img_rank4,mImageLoader,winner_arrayList.get(3).getImage());
        loadImages(id_img_rank5,mImageLoader,winner_arrayList.get(4).getImage());
    }

    private void loadImages(final CircularImageView id_img_rank1, ImageLoader mImageLoader, String image) {
        //Log.d("fgfgfgfghh", UrlEndpoints.Profile_pic+image);
        mImageLoader.get(UrlEndpoints.Profile_pic+image, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                id_img_rank1.setImageBitmap(response.getBitmap());
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
    private void loadImages(final CircularImageView id_imgView_profile, final String username,final String id_mobile, ImageLoader idImageProfile) {

    }
    private void start_PTimer() {
     /*   customHandler.removeCallbacks(updateTimerThread, 0);
        startTime = 0;
        timeInMilliseconds = 0;
        updatedTime = 0;
        timeSwapBuff = 0;
        updateTimerThread=null;
        startTime = SystemClock.uptimeMillis();*/

        //  customHandler.postDelayed(updateTimerThread, 0);

        handler = new Handler();
        Update = new Runnable() {
            public void run() {


                Log.d("timewr","call");
                LinearLayout container1 = (LinearLayout)findViewById(R.id.container1);
                LinearLayout container2 = (LinearLayout)findViewById(R.id.container2);
                LinearLayout container3 = (LinearLayout)findViewById(R.id.container3);

                LinearLayout container4 = (LinearLayout)findViewById(R.id.container4);
                LinearLayout container5 = (LinearLayout)findViewById(R.id.container5);
                LinearLayout container6 = (LinearLayout)findViewById(R.id.container6);

                LinearLayout container7 = (LinearLayout)findViewById(R.id.container7);
                LinearLayout container8 = (LinearLayout)findViewById(R.id.container8);
                LinearLayout container9 = (LinearLayout)findViewById(R.id.container9);

                setView1(container1);setView2(container1);setView3(container1);
                setView1(container2);setView2(container2);setView3(container2);
                setView1(container3);setView2(container3);setView3(container3);

                setView1(container4);setView2(container4);setView3(container4);
                setView1(container5);setView2(container5);setView3(container5);
                setView1(container6);setView2(container6);setView3(container6);

                setView1(container7);setView2(container7);setView3(container7);
                setView1(container8);setView2(container8);setView3(container8);
                setView1(container9);setView2(container9);setView3(container9);

                // swipeTimer.cancel();
            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 1000);
    }
    private void setView1(LinearLayout container)
    {
        FrameLayout.LayoutParams lpView = new  FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        int i1=getRandomPositionLeft(); int i2=getRandomPositionRight();
        Log.d("random1",i1+" "+i2);
        lpView.setMargins(i1, i2, i1, i2);
        container.removeAllViews();
        LiquidRadioButton liquidRadioButton=new LiquidRadioButton(Gift_Winner__Activity.this);
        liquidRadioButton.setLayoutParams(lpView);
        //  container.setGravity(Gravity.CENTER);
        container.addView(liquidRadioButton);
    }
    private void setView2(LinearLayout container)
    {
        FrameLayout.LayoutParams lpView = new  FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        int i1=getRandomPositionLeft(); int i2=getRandomPositionRight();
        Log.d("random2",i1+" "+i2);
        lpView.setMargins(i1, i2, i1, i2);
        container.removeAllViews();
        LiquidRadioButton liquidRadioButton=new LiquidRadioButton(Gift_Winner__Activity.this);
        liquidRadioButton.setLayoutParams(lpView);
        // container.setGravity(Gravity.CENTER);
        container.addView(liquidRadioButton);
    }
    private void setView3(LinearLayout container)
    {
        FrameLayout.LayoutParams lpView = new  FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        int i1=getRandomPositionLeft(); int i2=getRandomPositionRight();
        Log.d("random3",i1+" "+i2);
        lpView.setMargins(i1, i2, i1, i2);
        container.removeAllViews();
        LiquidRadioButton liquidRadioButton=new LiquidRadioButton(Gift_Winner__Activity.this);
        liquidRadioButton.setLayoutParams(lpView);
        // container.setGravity(Gravity.CENTER);
        container.addView(liquidRadioButton);
    }

    private int getRandomPositionLeft() {
        Random r = new Random();

        // This isn't supposed to be an actual range, it's just for testing
        return r.nextInt(150);
    }
    private int getRandomPositionRight() {
        Random r = new Random();

        // This isn't supposed to be an actual range, it's just for testing
        return r.nextInt(150);
    }
    @Override
    public void onPause() {
        super.onPause();
        swipeTimer.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        swipeTimer.cancel();
    }
}
