package utilities;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.Adter_RV_Filter;
import callbacks.Callback_list_Listener;
import task.NORMAL_ASYNCHTASK;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by GT on 12/2/2017.
 */

public class Check_Eligibility_Dialog extends Dialog implements View.OnClickListener ,Callback_list_Listener{
    Activity act;
    private FrameLayout id_state_frm,id_city_frm,id_class_frm,id_class_stream;
    private ExpandableRelativeLayout expandableLayout1_student,expandableLayout1_city,expandableLayout1_class,expandableLayout1_stream;
    private RecyclerView _id_state,_id_city,_id_class,_id_stream;
    private boolean bl=false;
    private ArrayList<JSONObject> al_state=new ArrayList<>();
    private JSONArray jarray_al_state=new JSONArray();
    private JSONArray jarray_al_city=new JSONArray();
    private JSONArray jarray_al_class=new JSONArray();
    private JSONArray jarray_al_stream=new JSONArray();
    private ArrayList<JSONObject> al_city=new ArrayList<>();
    private TextView id__fb_btn,id_tv_state;
    private Map<String,String> map_jsobn=new HashMap<>();
    JSONObject jobj_city=new JSONObject();
    private Button id_submit;
    private FINAL_OBJ_LISTNER final_obj_listner;
    private String strid;
    private Button id_btn;


    public Check_Eligibility_Dialog(FINAL_OBJ_LISTNER final_obj_listner, Activity act, String strid) {
        super(act);
        this.act=act;
        this.final_obj_listner=final_obj_listner;
        this.strid=strid;

        Log.d("bnbn",strid+" :  "+act.getLocalClassName());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.availclass);
        getWindow().setBackgroundDrawable(new ColorDrawable(MyApplication.getAppContext().getResources().getColor(R.color.trans_theme)));
        id_state_frm=(FrameLayout)findViewById(R.id.id_state_frm);id_state_frm.setOnClickListener(this);
        id_btn=(Button)findViewById(R.id.id_btn);id_btn.setOnClickListener(this);
        expandableLayout1_student=(ExpandableRelativeLayout)findViewById(R.id.expandableLayout1_student);
        id_tv_state=(TextView)findViewById(R.id.id_tv_state);id_tv_state.setText("Check Elegbility");

        final MyInterpolator myInterpolator=new MyInterpolator();
        Map<String,String> map=new HashMap<>();

        JSONObject jobj_class=new JSONObject();

        _id_state=(RecyclerView)findViewById(R.id._id_state);_id_state.setLayoutManager(new GridLayoutManager(getApplicationContext(),1, LinearLayoutManager.VERTICAL, false));

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",""+strid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!expandableLayout1_student.isExpanded())
        {
          new NORMAL_ASYNCHTASK(new NORMAL_ASYNCHTASK.JOBJ_LISTENER() {
              @Override
              public void on_listener(JSONObject jsonobject, String str_key) {

                  try {
                      Log.d("jonbd__jnd", "" + jsonobject);
                      //    _id_state.removeAllViews();
                      Adter_RV_Filter adter_rv_filter = new Adter_RV_Filter(Check_Eligibility_Dialog.this.getContext(), jsonobject.getJSONArray(str_key), (Callback_list_Listener) Check_Eligibility_Dialog.this, str_key);
                      _id_state.setAdapter(adter_rv_filter);
                      //  adter_rv_filter.notifyDataSetChanged();
                      expandableLayout1_student.expand(100, myInterpolator);
                      // al_state
                  } catch (Exception e) {
                      Log.d("jonb_djnd", "" + e.getMessage());
                  }

              }
          },jsonObject.toString(),strid, "data").execute();


        }else if(expandableLayout1_student.isExpanded())
        {
            expandableLayout1_student.collapse(100,myInterpolator);

        }
        //  set_expand_layout(expandableLayout1_student);

   }

 // public void get_activityname()

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_btn:



                break;
        }

    }

    @Override
    public void onAdap_OBJ(JSONObject jsonObject, String str_key) {

        if(str_key.equalsIgnoreCase("AVAIL"))
        {
            try {
                Toast.makeText(act, jsonObject.getString("bname")+ "Choose Courses !!!", Toast.LENGTH_SHORT).show();
                final_obj_listner.onfinal_list(jsonObject,"Choose_Class");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {
            final_obj_listner.onfinal_list(jsonObject, "DUMMY");
        }
    }


    public interface FINAL_OBJ_LISTNER{
        public void onfinal_list(JSONObject data, String choose_class);
    }


    public class MyInterpolator implements TimeInterpolator {
        public MyInterpolator() {
            super();
        }

        public float getInterpolation(float t) {
            float x = 2.0f * t - 1.0f;
            return 0.5f * (x * x * x + 1.0f);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
