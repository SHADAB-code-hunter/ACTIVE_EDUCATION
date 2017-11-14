package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.gt.active_education.College_Detail_Activity;
import com.gt.active_education.R;

import java.util.List;

import adapter.Simple_Adapter;
import callbacks.Call_Dilaog_Listener;
import task.Load_Course_AVail_Data;

/**
 * Created by GT on 10/30/2017.
 */

public class Available_Courses_Dialog extends Dialog implements View.OnClickListener, Load_Course_AVail_Data.List_LoadedListener, Call_Dilaog_Listener {

    private final String url;
    private final Dialog_Spinner_Listener dialogSpinnerListener;
    private RecyclerView mRecyclerView;
    private Simple_Adapter mAdapter;

    public Available_Courses_Dialog(Dialog_Spinner_Listener dialogSpinnerListener , Context activity, String s) {
        super(activity);
        this.url=s;
        this.dialogSpinnerListener=dialogSpinnerListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_search_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        new Load_Course_AVail_Data(Available_Courses_Dialog.this, url).execute();

/*
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
*/

        /*ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,arrayList);
        id_rv_course.setAdapter();*/
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onavail_courses(List<Common_Pojo> common_pojos) {
//

       /* server_list=s;
        State_City_Search.this.arrayList_id=arrayList_id;*/
        //    progressDialog.cancel();
        if(common_pojos!=null) {
          //  Log.d("jnvdcdczmfnvm",common_pojos.get(0).getId()+"   "+common_pojos.get(0).getName());
            Log.d("jnvdcdczmfnvm",common_pojos.toString());
            mAdapter = new Simple_Adapter(common_pojos, Available_Courses_Dialog.this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }else {

         //   ConnectionCheck.list_not_get(activity,"List Not Get");
        }

    }

    @Override
    public void on_id_listener(String str_id, String s) {

       Log.d("ncmvnmnv",str_id+"  "+s);
       dialogSpinnerListener.on_listdata(str_id,s);

    }

    public interface Dialog_Spinner_Listener {

        public void on_listdata(String s,String sid);

    }
}
