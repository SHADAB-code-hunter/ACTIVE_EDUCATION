package fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gt.active_education.Agent_Profile_Activity;
import com.gt.active_education.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Delayed;

import callbacks.JOBJ_Listener;
import task.Asynch_Agent_Form_JObject;
import utilities.AnimUtils;
import utilities.App_Raw_Data;
import utilities.App_Static_Method;
import utilities.ConnectionCheck;
import utilities.DroidDialog;
import utilities.MyApplication;
import utilities.State_City_Search;

import static utilities.UrlEndpoints.GET_CITY;
import static utilities.UrlEndpoints.GET_STATE;
import static utilities.UrlEndpoints.UPDATE_PROFILE;


/**
 * Created by GT on 8/12/2017.
 */

public class Agent_Profile_Detail_Fragment extends Fragment implements View.OnClickListener, JOBJ_Listener {

    private LinearLayout id_info_relatiove;
    private boolean bl=false;
    private ExpandableRelativeLayout layout1;
    private CompoundButton.OnCheckedChangeListener listener;
    private Button id_btn_save,id_btn_save2;
    private EditText edt_fname,edt_lastname,edt_email;
    private FrameLayout id_frm_state,id_frm_city;
    private boolean open=false;
    private State_City_Search state_city_search;
    private Map<String,String> map2=new HashMap<>();
    private TextView id_text_tv_state,id_text_tv_city;
    private EditText edt_old_pass;
    private EditText edt_new_pass,edt_con_pass;
    private Context context;
    private EditText edt_add;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frg_agent_profile, container, false);
        id_info_relatiove=(LinearLayout)rootView.findViewById(R.id.id_info_relatiove);
        id_btn_save=(Button)rootView.findViewById(R.id.id_btn_save);
        id_frm_state=(FrameLayout)rootView.findViewById(R.id.id_frm_state);
        id_frm_city=(FrameLayout)rootView.findViewById(R.id.id_frm_city);
        id_text_tv_state=(TextView)rootView.findViewById(R.id.id_text_tv_state);
        id_text_tv_city=(TextView)rootView.findViewById(R.id.id_text_tv_city);
        edt_new_pass=(EditText)rootView.findViewById(R.id.edt_new_pass);
        edt_con_pass=(EditText)rootView.findViewById(R.id.edt_con_pass);
        edt_old_pass=(EditText)rootView.findViewById(R.id.edt_pass);
        edt_fname=(EditText)rootView.findViewById(R.id.edt_fname);
        edt_email=(EditText)rootView.findViewById(R.id.edt_email);
        edt_add=(EditText)rootView.findViewById(R.id.edt_add);
        //layout1 = (ExpandableRelativeLayout)rootView. findViewById(R.id.expandableLayout1);

        id_btn_save2=(Button)rootView.findViewById(R.id.id_btn_save2); id_btn_save2.setOnClickListener(this);
        id_info_relatiove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                    layout1.toggle();
                 //   bl=false;
                return false;
            }
        });
       /* listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                layout1.toggle();
            }
        };*/

        //StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_tv_state.getText().toString());
        id_frm_state.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!open) {
                    Log.d("kkjkjkjkj",""+open);
                    open = true;
                    state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s,String s_id) {
                            // id_tv_state.setText(s);id_tv_city.setText("Select City");
                            Log.d("kkjkjkjkj",""+s_id+" "+s);
                            if(!s.equals("na")){
                                id_text_tv_state.setText(s);
                                id_text_tv_city.setText("City");
                                map2.put("state_id", ""+ App_Raw_Data.local_parseJson(s));
                                Log.d("sgsdfe",""+App_Raw_Data.local_parseJson(s))  ;
                                  }
                            else {
                                id_text_tv_state.setText("State"); id_text_tv_city.setText("City");
                            }
                            state_city_search.cancel();
                            open=false;
                        }
                    }, Agent_Profile_Detail_Fragment.this.getContext(), GET_STATE,"data");
                    state_city_search.show();
                }
                return false;
            }
        });


        id_frm_city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!open) {
                    if (!id_text_tv_state.getText().toString().equals("State")) {
                        open = true;
                        StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_text_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s,String s_id) {
                                //  Log.d("jdjfhf",""+s);
                                if(!s.equals("na")){
                                    id_text_tv_city.setText(s);
                                    map2.put("city_id",s_id);
                                }else {
                                    id_text_tv_city.setText("City");
                                }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Agent_Profile_Detail_Fragment.this.getContext(), GET_CITY +map2.get("state_id"),"city");
                        state_city_search.show();
                    } else if(id_text_tv_state.getText().toString().equals("State")) {
                        Toast.makeText(Agent_Profile_Detail_Fragment.this.getContext(), "Please Select State First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        id_btn_save.setOnClickListener(this);
      //id_btn_save2.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_btn_save:
                update();
                break;
            case R.id.id_btn_save2:
                change_password();
                break;
        }
    }

    private void change_password() {
        Map<String,String> map=new HashMap<>();
        String str_pass=edt_old_pass.getText().toString();
        String str_new_pass=edt_new_pass.getText().toString();
        String str_con_pass=edt_con_pass.getText().toString();

        if(str_con_pass.equals(str_new_pass))
        {
            map.put("old_pwd",str_pass);
            map.put("new_pwd",str_new_pass);
            map.putAll(App_Static_Method.session_type());
        }

        new Asynch_Agent_Form_JObject(Agent_Profile_Detail_Fragment.this,UPDATE_PROFILE,map).execute();

    }

    private void update() {
        Map<String,String> map=new HashMap<>();
       // if(edt_fname.getText().toString().equals(null))
        map.put("aname",edt_fname.getText().toString());
      //  if(edt_email.getText().toString().equals(null))
        map.put("email",edt_email.getText().toString());
        map.put("address",edt_add.getText().toString());
        if(!map2.isEmpty())
        map.put("state",map2.get("state_id"));
        if(!map2.isEmpty())
        map.put("city",map2.get("city_id"));
        map.putAll(App_Static_Method.session_type());
        new Asynch_Agent_Form_JObject(Agent_Profile_Detail_Fragment.this,UPDATE_PROFILE,map).execute();
    }

    @Override
    public void onLJsonLoaded(JSONObject jsonObject) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLJsonLoaded_new(JSONObject jsonObject) {
        if(jsonObject!=null) {
            Log.d("strjobject", jsonObject.toString());
           // update();
            new DroidDialog.Builder(Agent_Profile_Detail_Fragment.this.getContext())
                    .icon(R.drawable.ic_checked)
                    .title("Successfull Updated !!!")
                    .cancelable(true, false)

                    .neutralButton("Cancle", new DroidDialog.onNeutralListener() {
                        @Override
                        public void onNeutral(Dialog droidDialog) {
                            //	activity.getParent().onBackPressed();
                            //    context.finish();

                          //  new Agent_Profile_Detail_Fragment();

                            droidDialog.dismiss();
                        }
                    })
                    .animation(AnimUtils.AnimZoomInOut)
                    .color(context.getColor(R.color.colorPrimary), context.getColor(R.color.colorPrimaryDark),
                            ContextCompat.getColor(context, R.color.colorAccent))
                    .divider(true, ContextCompat.getColor(context, R.color.orange))
                    .show();
            //{"msg":"Agent updated successfully"}
        }else {
            Toast.makeText(context, "Not Responding !!!", Toast.LENGTH_SHORT).show();
        }

    }
}
