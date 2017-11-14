package com.gt.active_education;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import callbacks.Choose_newCourses_Listener;
import callbacks.Upcoming_List_LoadedListener;
import task.Load_Async_Course_desc;
import task.Load_Courses_Data;

import pojo.Cat_Model;
import utilities.Common_Pojo;
import utilities.UrlEndpoints;

/**
 * Created by GT on 8/21/2017.
 */

public  class Colleg_dialog extends Dialog implements Upcoming_List_LoadedListener, View.OnClickListener {

    private Spinner id_course_spinner,id_branch_spinner;
    private String str_course;
    private Context applicationContext;
    private Button id_btn_submit;
    private  ArrayList<String> arrayList,arrayList_id;
    private String str_course_id,clg_id;


    public Colleg_dialog(Context applicationContext, String str_course, String clg_id) {
        super(applicationContext);
        this.applicationContext=applicationContext;
        this.str_course=str_course;
        this.clg_id=clg_id;
        Log.d("hdjhj",clg_id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.course_dialog);

        id_course_spinner=(Spinner)findViewById(R.id.id_course_spinner);
        id_branch_spinner=(Spinner)findViewById(R.id.id_branch_spinner);

        id_btn_submit=(Button)findViewById(R.id.id_btn_submit);
        id_btn_submit.setOnClickListener(this);
        Log.d("dhdhdhdh",str_course);
        new Load_Courses_Data(Colleg_dialog.this,str_course).execute();
    }

    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies) {

    }

    @Override
    public void onUpcomingLoaded(List<Cat_Model> listMovies, String poss) {
        Log.d("scgjhdcgjdcbh","dnjcfdcj");
    }

    @Override
    public void onUpcomingcourses(final List<Common_Pojo> common_pojos) {
        //  Log.d("scgjhdcgjdcbh",""+common_pojos.get(0).getName());
        if (common_pojos.isEmpty())
            return;
         ArrayList<String> arrayList = new ArrayList<>();
         for (int i = 0; i < common_pojos.size(); i++) {
             arrayList.add(common_pojos.get(i).getName());
             Log.d("stddddrr", ""+common_pojos.get(i).getName());
         }
         ArrayAdapter<String> course_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);

         id_course_spinner.setAdapter(course_adapter);
         id_course_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {   //  str_choose_date=arrayList.get(position);
                 Log.d("strr", UrlEndpoints.GET_BRANCH_LIST + str_course_id);
                 //  type_postion=(position+1);
                 str_course_id = common_pojos.get(position).getId();
                 set_state_list(UrlEndpoints.GET_BRANCH_LIST + str_course_id, position);
             }

             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

    }

    private void set_state_list(String s, final int position) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("citydd",""+str_response);
                            ArrayList<Common_Pojo> arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("data"))
                            {
                                JSONArray array = response.getJSONArray("data");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                    sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));
                                    //  Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(sendDateModel);
                                }
                                set_state_inadapter(arrayList,position);
                            }
                            else if(response.has("msg"))
                            {
                                //pd.dismiss();
                                Log.d("unauth","un_Auth");
                                //  ConnectionCheck.unAuth_prob(activity,response.getString("msg"));
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    private void set_state_inadapter(ArrayList<Common_Pojo> list, final int cat_position) {

        arrayList = new ArrayList<>(); arrayList_id = new ArrayList<>();

        for (Common_Pojo commonPojo : list) {
            Log.d("branch_name", commonPojo.getName());
            arrayList.add(commonPojo.getName());
            arrayList_id.add(commonPojo.getId());
        }

        ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        /* Spinner_Adapter spinnerAdapter=new Spinner_Adapter(getContext(),arrayList);*/
        id_branch_spinner.setAdapter(cat_adapter);
    }

    @Override
    public void onUpcomingexams(List<Common_Pojo> common_pojos) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_btn_submit:
                 String url=UrlEndpoints.GET_CLG_COURSE+"clgid="+clg_id+"&course="+str_course_id+"&branch="+(arrayList_id.get(id_branch_spinner.getSelectedItemPosition()));
                 Log.d("urrfl",url);
                 String status_call="dialog";
                 new Load_Async_Course_desc(((Choose_newCourses_Listener)applicationContext),url,status_call).execute();

              //  ((Choose_newCourses_Listener)applicationContext).on_choose_courses(arrayList.get(id_branch_spinner.getSelectedItemPosition()));

                break;
        }
    }
}
