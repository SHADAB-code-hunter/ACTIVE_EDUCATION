package utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gt.active_education.Filter_Activity;
import com.gt.active_education.R;

import java.util.HashMap;
import java.util.Map;

import callbacks.Dialog_Pager_Listener;
import callbacks.Inner_Dialog_Listener;
import fragment.Partner_Detail_Frag;

import static utilities.UrlEndpoints.GET_CITY;

/**
 * Created by GT on 10/27/2017.
 */

public class Inner_Filter_Dialog extends Dialog implements View.OnClickListener {

    private Button id_submit;
    private FrameLayout id_state_frm;
    private boolean open;
    private State_City_Search state_city_search;
    private TextView id_tv_state;
    private TextView id_tv_city;
    private FrameLayout id_city_frm;
    private FrameLayout id_course_frm;
    private Map<String,String> map=new HashMap<>();
    private TextView id_tv_course;
    private FrameLayout id_branch_frm;
    private TextView id_tv_branch;
    private String str_Url;
    private Inner_Dialog_Listener inner_dialog_listener;

    public Inner_Filter_Dialog(@NonNull Context context, String str_Url, Inner_Dialog_Listener inner_dialog_listener) {
        super(context);
        this.str_Url=str_Url;
        this.inner_dialog_listener=inner_dialog_listener;
    }

   /* public Inner_Filter_Dialog(Filter_Activity filter_activity, String urlDealCatMain) {
        super(filter_activity.getBaseContext());
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.inner_filter_dialog);
        id_tv_state=(TextView)findViewById(R.id.id_tv_state);
        id_tv_city=(TextView)findViewById(R.id.id_tv_city);
        id_tv_course=(TextView)findViewById(R.id.id_tv_course);
        id_tv_branch=(TextView)findViewById(R.id.id_tv_branch);
        id_state_frm=(FrameLayout)findViewById(R.id.id_state_frm);
        id_city_frm=(FrameLayout)findViewById(R.id.id_city_frm);
        id_branch_frm = (FrameLayout) findViewById(R.id.id_branch_frm);
        if(isSubstring("type=1",str_Url)) {

            id_branch_frm.setVisibility(View.GONE);
        }
        id_course_frm=(FrameLayout)findViewById(R.id.id_course_frm);


        id_state_frm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!open) {
                    open = true;

                    state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s,String s_id) {
                           // id_tv_state.setText(s);id_tv_city.setText("Select City");
                            if(!s.equals("na")){
                                id_tv_state.setText(s);
                                id_tv_city.setText("Select City");
                                map.put("state_id", ""+App_Raw_Data.local_parseJson(s));
                          }
                            else {

                                id_tv_state.setText("Select State"); id_tv_city.setText("Select City");
                            }
                            state_city_search.cancel();
                            open=false;
                        }
                    }, Inner_Filter_Dialog.this.getContext(), str_Url,"state");
                    state_city_search.show();
                }
                return false;
            }
        });

        id_city_frm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!open) {
                    open = true;

                    if (id_tv_state.getText().toString() != null) {
                        StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s,String s_id) {

                              //  Log.d("jdjfhf",""+s);
                                 if(!s.equals("na")){
                                    id_tv_city.setText(s);

                                     map.put("city_id",s_id);
                                }else {
                                     id_tv_city.setText("Select City");
                                 }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Inner_Filter_Dialog.this.getContext(), str_Url +"state="+map.get("state_id"),"city");
                        state_city_search.show();
                    } else {
                        Toast.makeText(Inner_Filter_Dialog.this.getContext(), "Please Select State First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        id_course_frm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!open) {
                    open = true;

                    if (id_tv_city.getText().toString() != null) {
                       // StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s,String s_id) {

                                //  Log.d("jdjfhf",""+s);
                                if(!s.equals("na")){
                                    id_tv_course.setText(s);
                                    map.put("course_id",s_id);
                                }else {
                                    id_tv_course.setText("Select Course");
                                }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Inner_Filter_Dialog.this.getContext(), str_Url +"state="+map.get("state_id")+"&city="+ map.get("city_id"),"course");
                        state_city_search.show();
                    } else {
                        Toast.makeText(Inner_Filter_Dialog.this.getContext(), "Please Select State First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        id_branch_frm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!open) {
                    open = true;

                    if (id_tv_course.getText().toString() != null) {
                      //  StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s,String s_id) {

                                //  Log.d("jdjfhf",""+s);
                                if(!s.equals("na")){
                                    id_tv_branch.setText(s);
                                    map.put("branch_id",s_id);

                                }else {
                                    id_tv_branch.setText("Select Branch");
                                }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Inner_Filter_Dialog.this.getContext(), str_Url  +"state="+map.get("state_id")+"&city="+ map.get("city_id")+"&course="+map.get("course_id"),"branch");
                        state_city_search.show();
                    } else {
                        Toast.makeText(Inner_Filter_Dialog.this.getContext(), "Please Select Course First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
//http://activeeduindia.com/admin/webservices/getTopList.php?type=1&state=36&city=1038&course=1

        id_submit=(Button)findViewById(R.id.id_submit);id_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

     switch (v.getId())
     {
         case R.id.id_submit:

             inner_dialog_listener.onInner_Dialog(map);

             break;
     }

    }
    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }
}
