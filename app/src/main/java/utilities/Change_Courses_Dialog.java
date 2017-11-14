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

import com.gt.active_education.R;

import java.util.HashMap;
import java.util.Map;

import callbacks.Inner_Dialog_Listener;

import static utilities.UrlEndpoints.GET_BRANCH_LIST;

/**
 * Created by GT on 11/1/2017.
 */

public class Change_Courses_Dialog extends Dialog implements View.OnClickListener {

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
    private TextView id_tv_branch;
    private String str_Url;
    private Change_Course_Listener change_course_listener;

    public Change_Courses_Dialog(@NonNull Context context, String str_Url, Change_Course_Listener change_course_listener) {
        super(context);
        this.str_Url=str_Url;
        this.change_course_listener=change_course_listener;
    }

   /* public Change_Courses_Dialog(Filter_Activity filter_activity, String urlDealCatMain) {
        super(filter_activity.getBaseContext());
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_corses_dialog);
        id_tv_state=(TextView)findViewById(R.id.id_tv_state);
        id_tv_city=(TextView)findViewById(R.id.id_tv_city);
        id_tv_course=(TextView)findViewById(R.id.id_tv_course);
        id_tv_branch=(TextView)findViewById(R.id.id_tv_branch);
        id_state_frm=(FrameLayout)findViewById(R.id.id_state_frm);
        id_city_frm=(FrameLayout)findViewById(R.id.id_city_frm);
        id_course_frm=(FrameLayout)findViewById(R.id.id_course_frm);

        Log.d("course_url",str_Url);
        id_state_frm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!open) {
                    open = true;

                    state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s,String s_id) {
                            // id_tv_state.setText(s);id_tv_city.setText("Select City");
                            Log.d("kkjkjkjkj",""+s_id);
                            if(!s.equals("na")){
                                id_tv_state.setText(s);
                                id_tv_city.setText("Select Branch");
                                map.put("course_id", s_id);
                              //  Log.d("sgsdfe",""+App_Raw_Data.local_parseJson(s))  ;
                            }
                            else {
                                id_tv_state.setText("Select Course"); id_tv_city.setText("Select Branch");
                            }
                            state_city_search.cancel();
                            open=false;
                        }
                    }, Change_Courses_Dialog.this.getContext(), str_Url,"data");
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

                                    map.put("branch_id",s_id);
                                }else {
                                    id_tv_city.setText("Select Branch");
                                }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Change_Courses_Dialog.this.getContext(), GET_BRANCH_LIST+map.get("course_id"),"data");
                        state_city_search.show();
                    } else {
                        Toast.makeText(Change_Courses_Dialog.this.getContext(), "Please Select State First !!!", Toast.LENGTH_SHORT).show();
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

                change_course_listener.onCourse_Dialog(map);

                break;
        }

    }

    public interface Change_Course_Listener
    {
        public void onCourse_Dialog(Map<String,String> map);
    }

    public static boolean isSubstring(String subStr, String mainStr){
        return mainStr.matches(".*\\Q" + subStr + "\\E.*");
    }
}
