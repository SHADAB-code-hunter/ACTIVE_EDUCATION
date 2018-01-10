package com.gt.active_education;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.Agent_Profile_VP_Adpter;
import callbacks.JOBJ_Listener;
import callbacks.Log_Out_Listener;
import nav_tabbar.NavigationTabBar;
import task.Asynch_Agent_Form_JObject;
import task.Asynch_Responce_OBJ;
import utilities.App_Static_Method;
import utilities.Imager_Picker_Activity;
import utilities.UrlEndpoints;

import static com.gt.active_education.SignUp_Activity.permission_check;
import static utilities.App_Static_Method.request_permission_result;
import static utilities.App_Static_Method.session_type;
import static utilities.UrlEndpoints.GET_PROFILE;
import static utilities.UrlEndpoints.GET_PROFILE_DATA;

/**
 * Created by GT on 8/12/2017.
 */

public class Agent_Profile_Activity extends AppCompatActivity implements Log_Out_Listener, View.OnClickListener, JOBJ_Listener,Imager_Picker_Activity.ImageAttachmentListener{
    TextView id_back_btn_quiz;
    ImageView tv_home;
    ProgressDialog progressDialog;
    private ImageView id_image_profile_signup;
    private TextView id_add,id_name_prfl;
    private Imager_Picker_Activity imagePicker;
    private Bitmap bitmap;
    private String file_name;
    private String str_profile_bitmap;
    private FrameLayout id_frm;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_agent_profile);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            id_back_btn_quiz=(TextView)findViewById(R.id.id_back_btn_quiz);id_back_btn_quiz.setOnClickListener(this);
            tv_home=(ImageView)findViewById(R.id.tv_home);tv_home.setOnClickListener(this);
            id_add=(TextView)findViewById(R.id.id_add);
            id_name_prfl=(TextView)findViewById(R.id.id_name_prfl);
            id_image_profile_signup=(ImageView)findViewById(R.id.id_image_profile_signup);
            id_name_prfl=(TextView)findViewById(R.id.id_name_prfl);// name
            id_frm=(FrameLayout)findViewById(R.id.id_frm);id_frm.setOnClickListener(this);
            imagePicker =new Imager_Picker_Activity(Agent_Profile_Activity.this);
            tv_home.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     progressDialog = new ProgressDialog(Agent_Profile_Activity.this);
                     progressDialog.setCancelable(true);
                     progressDialog.show();
                     progressDialog.setMessage(getString(R.string.Loading));
                     new Thread(new Runnable() {
                         @Override
                         public void run() {
                             try {
                                 Thread.sleep(1500);

                                 startActivity(new Intent( Agent_Profile_Activity.this,DashBoard_Activity.class));
                                 finish();

                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                         }
                     }).start();
                 }
             });

        Log.d("sgsggsgs", session_type().toString());
        //id_image_profile_signup.setImageResource();

        new Asynch_Agent_Form_JObject(Agent_Profile_Activity.this, GET_PROFILE_DATA,session_type()).execute();
        initUI();
        }

    private void initUI() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);

        Agent_Profile_VP_Adpter agentProfileVpAdpter=new Agent_Profile_VP_Adpter(getSupportFragmentManager());

        viewPager.setAdapter(agentProfileVpAdpter);
       /* final WrapContentViewPager wrapContentViewPager = (WrapContentViewPager) findViewById(R.id.vp_horizontal_ntb);

        wrapContentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                wrapContentViewPager.reMeasureCurrentPage(wrapContentViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Agent_Profile_VP_Adpter agentProfileVpAdpter=new Agent_Profile_VP_Adpter(getSupportFragmentManager());
        wrapContentViewPager.setAdapter(agentProfileVpAdpter);*/
/*
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(
                        getBaseContext()).inflate(R.layout.item_vp, null, false);

                final TextView txtPage = (TextView) view.findViewById(R.id.txt_vp_item_page);
                txtPage.setText(String.format("Page #%d", position));

                container.addView(view);
                return container;
            }
        });
*/

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_deal),
                        Color.parseColor(colors[2]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Deal")
                        //  .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_booking),
                        Color.parseColor(colors[5]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Seat Book")
                        // .badgeTitle("777")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_edt_profile),
                        Color.parseColor(colors[4]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Profile")
                        // .badgeTitle("777")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_target),
                        Color.parseColor(colors[0]))
                        //   .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Target")
                        // .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_calculator),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Account")
                        //  .badgeTitle("with")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_new_progress),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Progress")
                        //  .badgeTitle("icon")
                        .build()
        );


        navigationTabBar.setModels(models);

        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }

    @Override
    public void on_logout(boolean bl) {
        if(bl)
        {
            progressDialog.cancel();

            this.finish();
        }else if(!bl){
            progressDialog.cancel();
            Toast.makeText(this, "Logout UnSuccessFull", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_back_btn_quiz:
                finish();
                break;

            case R.id.id_frm:
                 /* image pick*/
                permission_check(101, Agent_Profile_Activity.this);


                if (Build.VERSION.SDK_INT >= 23)
                    imagePicker.imagepicker(1);
                else
                    App_Static_Method.lower_camera_call_act(Agent_Profile_Activity.this);
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1) {
            imagePicker.request_permission_result(requestCode, permissions, grantResults);
        }else if(requestCode==101){

            request_permission_result(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //  Log.d("dataedpack",data.toString());

        if (requestCode == App_Static_Method.lower_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            id_image_profile_signup.setImageBitmap(photo);
        }else if(requestCode!= App_Static_Method.lower_CAMERA_REQUEST) {
            imagePicker.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String s) {
        this.bitmap=file;
        this.file_name=filename;


        send_serve_image(getStringImage(file));
       // id_image_profile_signup.setImageBitmap(file);
        str_profile_bitmap=getStringImage(file);
        Log.d("callggll","call"+str_profile_bitmap);
        String path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imagePicker.createImage(file,filename,path,false);
    }

    private void send_serve_image(String stringImage) {
        Map<String,String>  map=session_type();
        map.put("filename",(map.get("mobile")+".png"));
        map.put("image",stringImage);
        Log.d("mmtgtg",""+map);

        new Asynch_Responce_OBJ(new Asynch_Responce_OBJ.OBJ_LISTENER() {
            @Override
            public void obn_obj_find(JSONObject jsonObject) {

                try {
                    Log.d("knknggknknk", "" + jsonObject);
                    if (jsonObject.getInt("msg")==1) {

                        id_image_profile_signup.setImageBitmap(bitmap);
                    }else {
                        Toast.makeText(Agent_Profile_Activity.this, "Profile Picture Not Updated", Toast.LENGTH_SHORT).show();
                        id_image_profile_signup.setImageResource(R.drawable.ic_profiledemo);
                    }
                }catch (Exception e)
                {
                    Log.d("ecaprui",e.getMessage());
                }

            }
        }, UrlEndpoints.UPDATE_AGENT_IMAGE,map).execute();

    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


      //  List<ActivityManager.RunningAppProcessInfo> list=new ArrayList<>();

     //   startActivity(new Intent(getApplicationContext(),DashBoard_Activity.class));
        finish();
    }

    @Override
    public void onLJsonLoaded(JSONObject jsonObject) {
        Log.d("objecttt",jsonObject.toString());
    }

    @Override
    public void onLJsonLoaded_new(JSONObject jsonObject) {
        if(jsonObject==null)
        {
            return;
        }
        try {
            Log.d("objecttt",jsonObject.toString());

            JSONObject jsonObject1=(jsonObject.getJSONArray("data")).getJSONObject(0);

            Picasso.with(Agent_Profile_Activity.this)
                    .load(GET_PROFILE+"partner/"+(jsonObject1).getString("image"))
                    //  .placeholder(R.drawable.ic_manav_rcahna_banner)   // optional
                    // .error(DRAWABLE RESOURCE)      // optional
                    // .resize(width, height)                        // optional
                    // .rotate(degree)                             // optional
                    .into(id_image_profile_signup);

            id_add.setText(jsonObject1.getString("address"));
            id_name_prfl.setText(jsonObject1.getString("mobile"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
