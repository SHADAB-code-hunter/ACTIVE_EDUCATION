package fragment;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.Adter_RV_Filter;
import callbacks.Callback_list_Listener;
import task.NEW_ASYNCH_HEADER;
import utilities.NEW_ASYNCH;

import static com.facebook.FacebookSdk.getApplicationContext;
import static utilities.UrlEndpoints.TOP_INNER_SCHOOL_FILTER_;
import static utilities.UrlEndpoints.TOP_INNER_SCHOOL_FILTER_DEFAULT;

/**
 * Created by GT on 12/6/2017.
 */

public class School_Fragment extends Fragment implements View.OnClickListener {
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
    private TextView id__fb_btn;
    private Map<String,String> map_jsobn=new HashMap<>();
    JSONObject jobj_common =new JSONObject();
    private Button id_submit;
    private School_Fragment.FINAL_OBJ_LISTNER final_obj_listner;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.filter_dialog_layout, container, false);
           id__fb_btn=(TextView) rootView.findViewById(R.id.id__fb_btn);id__fb_btn.setVisibility(View.GONE);
             id_state_frm = (FrameLayout) rootView.findViewById(R.id.id_state_frm);
           id_state_frm.setOnClickListener(this);
            expandableLayout1_student = (ExpandableRelativeLayout) rootView.findViewById(R.id.expandableLayout1_student);expandableLayout1_student.collapse();
            id_city_frm = (FrameLayout) rootView.findViewById(R.id.id_city_frm);
            id_city_frm.setOnClickListener(this);
            expandableLayout1_city = (ExpandableRelativeLayout) rootView.findViewById(R.id.expandableLayout1_city);expandableLayout1_city.collapse();
            id_class_frm = (FrameLayout) rootView.findViewById(R.id.id_class_frm);
            id_class_frm.setOnClickListener(this);
            expandableLayout1_class = (ExpandableRelativeLayout) rootView.findViewById(R.id.expandableLayout1_class);expandableLayout1_class.collapse();
            id_class_stream = (FrameLayout) rootView.findViewById(R.id.id_class_stream);
            id_class_stream.setOnClickListener(this);
            expandableLayout1_stream = (ExpandableRelativeLayout) rootView.findViewById(R.id.expandableLayout1_stream);expandableLayout1_stream.collapse();
            id_submit = (Button) rootView.findViewById(R.id.id_submit);
            id_submit.setOnClickListener(this);
          /*   id__fb_btn = (TextView) rootView.findViewById(R.id.id__fb_btn);
            id__fb_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("gsfgdfg", "" + al_state.toString());
                    Log.d("gsfg_dfg", "" + al_city.toString());
                }
            });
*/
           /* if (act.getLocalClassName().equals("School_Listing_Activity")) {
                id__fb_btn.setText(" Find Your Schools ");
            }
*/

            //Log.d("gdgdgdgd",""+act.getClass().isInstance(Filter_Activity.class)+""+act.getCallingActivity().getShortClassName());

            _id_state = (RecyclerView) rootView.findViewById(R.id._id_state);
            _id_state.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));
            _id_city = (RecyclerView) rootView.findViewById(R.id._id_city);
            _id_city.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));
            _id_class = (RecyclerView) rootView.findViewById(R.id._id_class);
            _id_class.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));
            _id_stream = (RecyclerView) rootView.findViewById(R.id._id_stream);
            _id_stream.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false));

            return rootView;
        }

    @Override
    public void onClick(View v) {
        final MyInterpolator myInterpolator=new MyInterpolator();
        Map<String,String> map=new HashMap<>();

        JSONObject jobj_class=new JSONObject();
        switch (v.getId())
        {
            case R.id.id_state_frm:

                if(!expandableLayout1_student.isExpanded())
                {
                    new NEW_ASYNCH(new NEW_ASYNCH.JOBJ_LISTENER() {
                        @Override
                        public void on_listener(JSONObject jsonobject, String str_key) {
                            try {
                                Log.d("jonbdjnd",""+jsonobject);
                                //     Toast.makeText(act, "State List : "+jsonobject, Toast.LENGTH_SHORT).show();
                                //    _id_state.removeAllViews();
                                Adter_RV_Filter adter_rv_filter  =new Adter_RV_Filter(School_Fragment.this.getContext(),jsonobject.getJSONArray(str_key),(Callback_list_Listener)School_Fragment.this,str_key);
                                _id_state.setAdapter(adter_rv_filter);
                                //  adter_rv_filter.notifyDataSetChanged();
                                expandableLayout1_student.expand(100,myInterpolator);
                                // al_state
                            } catch (Exception e) {
                                Log.d("jonb_djnd",""+e.getMessage());
                            }

                        }
                    },map,TOP_INNER_SCHOOL_FILTER_DEFAULT,"state").execute();


                }else if(expandableLayout1_student.isExpanded())
                {
                    expandableLayout1_student.collapse(100,myInterpolator);

                }
                //  set_expand_layout(expandableLayout1_student);

                break;
            case R.id.id_city_frm:
                if(!expandableLayout1_city.isExpanded())
                {
                    expandableLayout1_student.collapse();

                    // map_jsobn.put("jsonData",map.toString());
                    new NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
                        @Override
                        public void on_listener(JSONObject jsonobject, String str_key) {
                            try {
                                Log.d("jo_nbdjnd",""+jsonobject);
                                // Toast.makeText(act, "City List : "+jsonobject, Toast.LENGTH_SHORT).show();
                                //   _id_city.removeAllViews();
                                Adter_RV_Filter adter_rv_filter =new Adter_RV_Filter(School_Fragment.this.getContext(),jsonobject.getJSONArray(str_key),(Callback_list_Listener)School_Fragment.this,str_key);
                                _id_city.setAdapter(adter_rv_filter);
                                // adter_rv_filter.notifyDataSetChanged();
                                expandableLayout1_city.expand(100,myInterpolator);
                            } catch (Exception e) {
                                Log.d("jonb_djnd",""+e.getMessage());
                            }

                        }
                    }, jobj_common.toString(),TOP_INNER_SCHOOL_FILTER_,"city").execute();


                }else if(expandableLayout1_city.isExpanded())
                {  expandableLayout1_city.collapse(100,myInterpolator);

                }
                //      set_expand_layout(expandableLayout1_city);

                break;
            case R.id.id_class_frm:

                //    set_expand_layout(expandableLayout1_class);
                 /**/

                if(!expandableLayout1_class.isExpanded())
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
                                Adter_RV_Filter adter_rv_filter=new Adter_RV_Filter(School_Fragment.this.getContext(),jsonobject.getJSONArray(str_key),(Callback_list_Listener)School_Fragment.this,str_key);
                                _id_class.setAdapter(adter_rv_filter);
                                expandableLayout1_class.expand(100,myInterpolator);
                            } catch (Exception e) {
                                Log.d("jonb_djnd",""+e.getMessage());
                            }
                        }
                    }, jobj_common.toString(),TOP_INNER_SCHOOL_FILTER_,"class").execute();


                }else if(expandableLayout1_class.isExpanded())
                {
                    expandableLayout1_class.collapse(100,myInterpolator);

                }


                break;
            case R.id.id_submit:

                new  NEW_ASYNCH_HEADER(new NEW_ASYNCH_HEADER.JOBJ_LISTENER() {
                    @Override
                    public void on_listener(JSONObject jsonobject, String str_key) {
                        try {
                            Log.d("hf_finalList",""+jsonobject);
                            Toast.makeText(act, "Submit : "+jsonobject, Toast.LENGTH_SHORT).show();
                            final_obj_listner.onfinal_list(jsonobject.getJSONArray("data"));

                        } catch (Exception e) {
                            Log.d("jonb_djnd",""+e.getMessage());
                        }

                    }
                }, jobj_common.toString(),TOP_INNER_SCHOOL_FILTER_,"data").execute();



                break;

        }
    }


    public interface FINAL_OBJ_LISTNER{
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

}
