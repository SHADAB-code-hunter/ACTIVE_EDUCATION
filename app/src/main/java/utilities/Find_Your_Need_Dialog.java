package utilities;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gt.active_education.Agent_login_Activity;
import com.gt.active_education.Main_School_List_Activity;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Search_Spinner.SearchableSpinner;
import adapter.Adter_RV_Filter;
import callbacks.Callback_list_Listener;
import task.NEW_ASYNCH_HEADER;

import static com.facebook.FacebookSdk.getApplicationContext;
import static utilities.UrlEndpoints.MAIN_URL_CITY;
import static utilities.UrlEndpoints.MAIN_URL_STATE;
import static utilities.UrlEndpoints.MAIN_URL_collge_code;
import static utilities.UrlEndpoints.MAIN_URL_collge_course;
import static utilities.UrlEndpoints.MAIN_URL_school_board;
import static utilities.UrlEndpoints.MAIN_URL_school_class;
import static utilities.UrlEndpoints.MAIN_URL_school_stream;
import static utilities.UrlEndpoints.MAIN_submit;
import static utilities.UrlEndpoints.SCHOOL_TOP_AVAIL_CLASS_FULL;

/**
 * Created by GT on 12/6/2017.
 */

public class Find_Your_Need_Dialog extends Dialog implements View.OnClickListener ,Callback_list_Listener {

    private Activity context;
    private SearchableSpinner id_spn_cat;
    private int type_postion = 0;
    private LinearLayout id_linear;
    private ViewPager id_vp;
    private Map<String, String> main_map = new HashMap<>();
    private RecyclerView _id_third;
    private RecyclerView _id_fourth;
    private FrameLayout id_third_frm;
    private FrameLayout id_frm_fourth;

    /**
     *
     */
    Activity act;
    private FrameLayout id_state_frm, id_city_frm, id_class_frm, id_class_stream, id_forth_frm, id_fifth_frm;
    private ExpandableRelativeLayout expandableLayout1_student, expandableLayout1_city, expandableLayout1_third, expandableLayout1_fourth;
    private RecyclerView _id_state, _id_city, _id_class, _id_stream;
    private boolean bl = false;
    private ArrayList<JSONObject> al_state = new ArrayList<>();
    private JSONArray jarray_al_state = new JSONArray();
    private JSONArray jarray_al_city = new JSONArray();
    private JSONArray jarray_al_board = new JSONArray();
    private JSONArray jarray_al_class = new JSONArray();
    private JSONArray jarray_al_mode = new JSONArray();
    private JSONArray jarray_al_stream = new JSONArray();
    private ArrayList<JSONObject> al_city = new ArrayList<>();
    private TextView id__fb_btn;
    private Map<String, String> map_jsobn = new HashMap<>();
    JSONObject jobj_common = new JSONObject();
    private Button id_submit;
    private Filter_Dialogue_layout.FINAL_OBJ_LISTNER final_obj_listner;
    private TextView id_tv_third, id_tv_fourth, id_tv_fifth;
    private FrameLayout id_select_category;
    private boolean open = false;
    private Custom_List_Dialog custom_list_dialog;
    private String[] str_log_array;
    private TextView id_tv_cat;
    private String[] stockArr;
    private ExpandableRelativeLayout expandableLayout1_fifth;
    private RecyclerView _id_fifth;

    public Find_Your_Need_Dialog(Activity context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_find_your_need);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("School");
        arrayList.add("College");
        arrayList.add("University");
        arrayList.add("ITI / Diploma");
        arrayList.add("Coaching / Institute");
        arrayList.add("Training Institute");

        stockArr = new String[5];
        stockArr = arrayList.toArray(stockArr);
        id_spn_cat = (SearchableSpinner) findViewById(R.id.id_spn_cat);
        id_linear = (LinearLayout) findViewById(R.id.id_linear);

        id_tv_third = (TextView) findViewById(R.id.id_tv_third);
        id_tv_fourth = (TextView) findViewById(R.id.id_tv_fourth);
        id_tv_fifth = (TextView) findViewById(R.id.id_tv_fifth);
        id_tv_cat = (TextView) findViewById(R.id.id_tv_cat);
        id_submit = (Button) findViewById(R.id.id_submit);id_submit.setOnClickListener(this);

        str_log_array = Find_Your_Need_Dialog.this.getContext().getResources().getStringArray(R.array.login_array);

        id_state_frm = (FrameLayout) findViewById(R.id.id_state_frm);
        id_state_frm.setOnClickListener(this);
        id_city_frm = (FrameLayout) findViewById(R.id.id_city_frm);
        id_city_frm.setOnClickListener(this);
        id_third_frm = (FrameLayout) findViewById(R.id.id_third_frm);
        id_third_frm.setOnClickListener(this);
        id_forth_frm = (FrameLayout) findViewById(R.id.id_forth_frm);
        id_forth_frm.setOnClickListener(this);
        id_fifth_frm = (FrameLayout) findViewById(R.id.id_fifth_frm);
        id_fifth_frm.setOnClickListener(this);
        id_select_category = (FrameLayout) findViewById(R.id.id_select_category);
        id_select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!open) {
                    open = true;

                    custom_list_dialog = new Custom_List_Dialog(new Custom_List_Dialog.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s) {

                            if (s.isEmpty()) {
                                custom_list_dialog.cancel();
                                id_tv_cat.setText("Select Category");
                                open = false;
                            } else {
                                setlayout_accordingto_category(s);
                                custom_list_dialog.cancel();
                                id_tv_cat.setText(s);
                                open = false;
                            }


                           /* Log.d("strr","fgrt"+position);
                            type_postion=(position+1);
                            */
                            //  ;
                        }
                    }, Find_Your_Need_Dialog.this.getContext(), stockArr);
                    custom_list_dialog.show();
                }

            }
        });


        expandableLayout1_student = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1_student);
        expandableLayout1_city = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1_city);
        expandableLayout1_third = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1_third);
        expandableLayout1_fourth = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1_fourth);
        expandableLayout1_fifth = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1_fifth);

        //   id_vp=(ViewPager)findViewById(R.id.id_vp);
        //  String str_cat[]= new String[]{"School","College","University","Coaching / Institute ","Training Institute","ITI College"};

        ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
       /* id_spn_cat.setAdapter(cat_adapter);

        id_spn_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //  str_choose_date=arrayList.get(position);

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });*/


        _id_state = (RecyclerView) findViewById(R.id._id_state);
        _id_state.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));
        _id_city = (RecyclerView) findViewById(R.id._id_city);
        _id_city.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));
        _id_third = (RecyclerView) findViewById(R.id._id_third);
        _id_third.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));
        _id_fourth = (RecyclerView) findViewById(R.id._id_fourth);
        _id_fourth.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));
        _id_fifth = (RecyclerView) findViewById(R.id._id_fifth);
        _id_fifth.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));

    }

    private void setlayout_accordingto_category(String str) {
        Log.d("posss", "" + str);
        switch (str) {
            case "School":
                main_map.put("cat_id", "1");
                get_state();
                break;
            case "College":
                main_map.put("cat_id", "2");
                get_state();
                break;
            case "University":
                main_map.put("cat_id", "3");
                get_state();
                break;
            case "ITI / Diploma":
                main_map.put("cat_id", "4");
                //   get_state();
                break;
            case "Coaching / Institute":
                main_map.put("cat_id", "5");
                break;
            case "Training Institute":
                main_map.put("cat_id", "6");
                break;

        }

    }

    private void get_state() {
        if (!expandableLayout1_student.isExpanded()) {
            new NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
                @Override
                public void on_listener(JSONObject jsonobject, String str_key) {
                    try {
                        Log.d("state", "" + jsonobject);
                        expandableLayout1_student.expand(100, new MyInterpolator());
                        //     Toast.makeText(act, "State List : "+jsonobject, Toast.LENGTH_SHORT).show();
                        _id_state.removeAllViews();
                        Adter_RV_Filter adter_rv_filter = new Adter_RV_Filter(Find_Your_Need_Dialog.this.getContext(), jsonobject.getJSONArray(str_key), (Callback_list_Listener) Find_Your_Need_Dialog.this, "state");
                        _id_state.setAdapter(adter_rv_filter);
                        //  adter_rv_filter.notifyDataSetChanged();

                        // al_state
                    } catch (Exception e) {
                        Log.d("jonb_djnd", "" + e.getMessage());
                    }

                }
            }, "", MAIN_URL_STATE, "data").execute();


        } else if (expandableLayout1_student.isExpanded()) {
            expandableLayout1_student.collapse(100, new MyInterpolator());

        }
    }

    @Override
    public void onAdap_OBJ(JSONObject jsonObject, String str_key) {
        Log.d("bcjj", "" + jsonObject + " " + str_key);
        try {
            final MyInterpolator myInterpolator = new MyInterpolator();

            switch (str_key) {
                case "state": // add data in city
                    jarray_al_state.put(jsonObject);
                    jobj_common.put(str_key, jarray_al_state);
                    Log.d("state__ :", "" + jobj_common);
                    al_state.add(jsonObject);
                    break;
                case "city": // add data in third hieararcy
                    jarray_al_city.put(jsonObject);
                    jobj_common.put(str_key, jarray_al_city);
                    Log.d("city__ :", "" + jobj_common);
                    check_caegory();
                    al_city.add(jsonObject);

                    break;
                case "board": //add data in forth hiearray
                    jarray_al_board.put(jsonObject);
                    jobj_common.put(str_key, jarray_al_board);
                    on_forth_frm();
                    break;
                case "class":  // add data in fifth hierarchy

                    jarray_al_class.put(jsonObject);
                    Log.d("cl_assls", "" + jsonObject.getString("name"));
                    jobj_common.put(str_key, jarray_al_class);
                    String str_type = jsonObject.getString("name").split(" ")[1];
                    Log.d("classls", "" + str_type);
                    if (Integer.parseInt(str_type) > 10)
                        on_fifth_frm();

                    break;
                case "stream":  // add data in fifth hierarchy

                    jarray_al_stream.put(jsonObject);
                    Log.d("cl_assls", "" + jsonObject.getString("name"));
                    jobj_common.put(str_key, jarray_al_stream);
                    break;
                case "mode":
                    Log.d("mo_de", "" + main_map.get("cat_id"));
                    jarray_al_mode.put(jsonObject);
                    jobj_common.put(str_key, jarray_al_mode);
                    on_forth_frm();

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void on_fifth_frm() {
        switch (main_map.get("cat_id")) {

            case "1":
                id_tv_fifth.setText("Select Stream");
                id_fifth_frm.setVisibility(View.VISIBLE);
                break;
            case "2":
                id_tv_fifth.setText("Select Mode");
                id_fifth_frm.setVisibility(View.VISIBLE);
                break;
         /*   case "3":
                id_tv_third.setText("Select Course");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "4":
                id_tv_third.setText("Select Trade");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "5":
                id_tv_third.setText("Select Class");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "6":
                id_tv_third.setText("Select Exams");
                id_third_frm.setVisibility(View.VISIBLE);
                break;*/

        }

    }

    private void on_forth_frm() {
        switch (main_map.get("cat_id")) {

            case "1":
                id_tv_fourth.setText("Select Class");
                id_forth_frm.setVisibility(View.VISIBLE);
                break;
            case "2":
                id_tv_fourth.setText("Select Mode");
                id_forth_frm.setVisibility(View.VISIBLE);
                break;
       /*     case "3":
                id_tv_third.setText("Select Course");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "4":
                id_tv_third.setText("Select Trade");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "5":
                id_tv_third.setText("Select Class");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "6":
                id_tv_third.setText("Select Exams");
                id_third_frm.setVisibility(View.VISIBLE);
                break;*/

        }

    }

    private void check_caegory() {

        switch (main_map.get("cat_id")) {

            case "1":
                id_tv_third.setText("Select Board");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "2":
                id_tv_third.setText("Select Course");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "3":
                id_tv_third.setText("Select Course");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "4":
                id_tv_third.setText("Select Trade");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "5":
                id_tv_third.setText("Select Class");
                id_third_frm.setVisibility(View.VISIBLE);
                break;
            case "6":
                id_tv_third.setText("Select Exams");
                id_third_frm.setVisibility(View.VISIBLE);
                break;

        }


    }

    private void open_layout_accordingto_category() {

    }

    public interface FINAL_OBJ_LISTNER {
        public void onfinal_list(JSONArray data);
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_city_frm:
                get_city_data();
                break;

            case R.id.id_third_frm: // for click third frame
                get_Third_heirarachy_data(main_map.get("cat_id"));
                break;
            case R.id.id_forth_frm: // for click fourth frame
                get_forth_heirarachy_data(main_map.get("cat_id"));
                break;
            case R.id.id_fifth_frm: // for click fourth frame
                get_fifth_heirarchy_data(main_map.get("cat_id"));
                break;
            case R.id.id_submit: // for click fourth frame
                send_data_to_next_page();
                break;
        }

    }

    private void send_data_to_next_page() {
        new NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
            @Override
            public void on_listener(JSONObject jsonobject, String str_key) {
                try {

                    Log.d("submit_full", "" + jsonobject);
                    Intent i=new Intent(getContext(),Main_School_List_Activity.class);
                    i.putExtra("JSON_DATA",""+jsonobject);
                    Find_Your_Need_Dialog.this.getContext().startActivity(i);
                } catch (Exception e) {
                    Log.d("jonb_djnd", "" + e.getMessage());
                }
            }
        }, jobj_common.toString(), MAIN_submit + "?jsonData=", "data").execute();

    }

    private void get_fifth_heirarchy_data(String cat_id) {

        Log.d("getfifth_data", "" + cat_id);
        switch (cat_id) {
            case "1":
                get_stream_data();
                break;
            case "2":
              //  get_college_mode_data();
                break;
            case "3":
                //    get_uni_course_data();
                break;
            case "4":
                //    get_iti_trade_data();
                break;
            case "5":
                //    get_coaching_class_data();
                break;
            case "6":
                //  get_training_exam_data();
                break;

        }
    }

    private void get_stream_data() {
        try {

            if (!expandableLayout1_fifth.isExpanded()) // current
            {
                expandableLayout1_fourth.collapse();

                try {
                    jobj_common.put("city", jarray_al_city);

                } catch (Exception e) {
                    Log.d("Excs_prf", "" + e.getMessage());
                }
                new NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
                    @Override
                    public void on_listener(JSONObject jsonobject, String str_key) {
                        try {
                            Log.d("jonb_streamdjnd", "" + jsonobject);

                            // _id_class.removeAllViews();
                            Adter_RV_Filter adter_rv_filter = new Adter_RV_Filter(Find_Your_Need_Dialog.this.getContext(), jsonobject.getJSONArray(str_key), (Callback_list_Listener) Find_Your_Need_Dialog.this, "stream");
                            _id_fifth.setAdapter(adter_rv_filter);
                            expandableLayout1_fifth.expand(100, new MyInterpolator());
                        } catch (Exception e) {
                            Log.d("jonb_djnd", "" + e.getMessage());
                        }
                    }
                }, jobj_common.toString(), MAIN_URL_school_stream + "?jsonData=", "data").execute();


            } else if (expandableLayout1_fifth.isExpanded()) {
                expandableLayout1_fifth.collapse(100, new MyInterpolator());

            }

        } catch (Exception e) {
            Log.d("Excsprf", "" + e.getMessage());
        }
    }



    private void get_Third_heirarachy_data(String cat_id) {
       Log.d("getthird_data",""+cat_id);
        switch (cat_id)
        {

            case "1":
                get_borad_data();
            break;
             case "2":
                 get_college_course_data();
            break;
             case "3":
             //    get_uni_course_data();
            break;
             case "4":
             //    get_iti_trade_data();
            break;
            case "5":
            //    get_coaching_class_data();
                break;
            case "6":
          //  get_training_exam_data();
            break;


        }
    }
    private void get_forth_heirarachy_data(String cat_id) {
        Log.d("getforth_data",""+cat_id);
        switch (cat_id)
        {
            case "1":
                get_class_data();
                break;
            case "2":
                 get_college_mode_data();
                break;
            case "3":
                //    get_uni_course_data();
                break;
            case "4":
                //    get_iti_trade_data();
                break;
            case "5":
                //    get_coaching_class_data();
                break;
            case "6":
                //  get_training_exam_data();
                break;


        }
    }

    private void get_college_mode_data() {
        if(!expandableLayout1_fourth.isExpanded()) // current
        {
            expandableLayout1_third.collapse();

            try {
                jobj_common.put("city",jarray_al_city);

            } catch (Exception e) {
                Log.d("Excs_prf",""+e.getMessage());
            }
            new  NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
                @Override
                public void on_listener(JSONObject jsonobject, String str_key) {
                    try {
                        Log.d("jonb_classdjnd",""+jsonobject);

                        // _id_class.removeAllViews();
                        Adter_RV_Filter adter_rv_filter=new Adter_RV_Filter(Find_Your_Need_Dialog.this.getContext(),jsonobject.getJSONArray("data"),(Callback_list_Listener)Find_Your_Need_Dialog.this,str_key);
                        _id_fourth.setAdapter(adter_rv_filter);
                        expandableLayout1_fourth.expand(100,new MyInterpolator());
                    } catch (Exception e) {
                        Log.d("jonb_djnd",""+e.getMessage());
                    }
                }
            }, jobj_common.toString(),MAIN_URL_collge_code+"?jsonData=","mode").execute();


        }
    }

    private void get_borad_data() {
        try {

            if(!expandableLayout1_third.isExpanded())
            {
                expandableLayout1_city.collapse();

                try {
                    jobj_common.put("city",jarray_al_city);

                } catch (Exception e) {
                    Log.d("Excs_prf",""+e.getMessage());
                }
                new  NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
                    @Override
                    public void on_listener(JSONObject jsonobject, String str_key) {
                        try {
                            Log.d("jonb_classdjnd",""+jsonobject);


                            // _id_class.removeAllViews();
                            Adter_RV_Filter adter_rv_filter=new Adter_RV_Filter(Find_Your_Need_Dialog.this.getContext(),jsonobject.getJSONArray(str_key),(Callback_list_Listener)Find_Your_Need_Dialog.this,"board");
                            _id_third.setAdapter(adter_rv_filter);
                            expandableLayout1_third.expand(100,new MyInterpolator());
                        } catch (Exception e) {
                            Log.d("jonb_djnd",""+e.getMessage());
                        }
                    }
                }, jobj_common.toString(),MAIN_URL_school_board+"?jsonData=","data").execute();


            }else if(expandableLayout1_third.isExpanded())
            {
                expandableLayout1_third.collapse(100,new MyInterpolator());

            }

        } catch (Exception e) {
            Log.d("Excsprf",""+e.getMessage());
        }
    }

    private void get_college_course_data() {
        try {

            if(!expandableLayout1_third.isExpanded())
            {
                expandableLayout1_city.collapse();

                try {
                    jobj_common.put("city",jarray_al_city);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new  NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
                    @Override
                    public void on_listener(JSONObject jsonobject, String str_key) {
                        try {
                            Log.d("jonb_classdjnd",""+jsonobject);
                            // _id_class.removeAllViews();
                            Adter_RV_Filter adter_rv_filter=new Adter_RV_Filter(Find_Your_Need_Dialog.this.getContext(),jsonobject.getJSONArray(str_key),(Callback_list_Listener)Find_Your_Need_Dialog.this,"mode");
                            _id_third.setAdapter(adter_rv_filter);
                            expandableLayout1_third.expand(100,new MyInterpolator());
                        } catch (Exception e) {
                            Log.d("jonb_djnd",""+e.getMessage());
                        }
                    }
                }, jobj_common.toString(),MAIN_URL_collge_course+"?jsonData=","data").execute();


            }else if(expandableLayout1_third.isExpanded())
            {
                expandableLayout1_third.collapse(100,new MyInterpolator());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_class_data() {
        try {

            if(!expandableLayout1_fourth.isExpanded()) // current
            {
                expandableLayout1_third.collapse();

                try {
                    jobj_common.put("city",jarray_al_city);

                } catch (Exception e) {
                    Log.d("Excs_prf",""+e.getMessage());
                }
                new  NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
                    @Override
                    public void on_listener(JSONObject jsonobject, String str_key) {
                        try {
                            Log.d("jonb_classdjnd",""+jsonobject);

                            // _id_class.removeAllViews();
                            Adter_RV_Filter adter_rv_filter=new Adter_RV_Filter(Find_Your_Need_Dialog.this.getContext(),jsonobject.getJSONArray(str_key),(Callback_list_Listener)Find_Your_Need_Dialog.this,"class");
                            _id_fourth.setAdapter(adter_rv_filter);
                            expandableLayout1_fourth.expand(100,new MyInterpolator());
                        } catch (Exception e) {
                            Log.d("jonb_djnd",""+e.getMessage());
                        }
                    }
                }, jobj_common.toString(),MAIN_URL_school_class+"?jsonData=","data").execute();


            }else if(expandableLayout1_fourth.isExpanded())
            {
                expandableLayout1_fourth.collapse(100,new MyInterpolator());

            }

        } catch (Exception e) {
            Log.d("Excsprf",""+e.getMessage());
        }

    }

    private void get_city_data() {
        final MyInterpolator myInterpolator=new MyInterpolator();
        if(!expandableLayout1_city.isExpanded())
        {
            expandableLayout1_student.collapse();

            // map_jsobn.put("jsonData",map.toString());
            new NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
                @Override
                public void on_listener(JSONObject jsonobject, String str_key) {
                    try {
                        Log.d("jo_nbdjnd",""+jsonobject);
                        expandableLayout1_city.expand(100,myInterpolator);
                        // Toast.makeText(act, "City List : "+jsonobject, Toast.LENGTH_SHORT).show();
                        //   _id_city.removeAllViews();
                        Adter_RV_Filter adter_rv_filter =new Adter_RV_Filter(Find_Your_Need_Dialog.this.getContext(),jsonobject.getJSONArray(str_key),(Callback_list_Listener)Find_Your_Need_Dialog.this,"city");
                        _id_city.setAdapter(adter_rv_filter);
                        // adter_rv_filter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Log.d("jonb_djnd",""+e.getMessage());
                    }

                }
            }, jobj_common.toString(),MAIN_URL_CITY+"?jsonData=","data").execute();


        }else if(expandableLayout1_city.isExpanded())
        {  expandableLayout1_city.collapse(100,myInterpolator);

        }
    }
}
