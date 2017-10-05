package com.gt.active_education;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import utilities.MyApplication;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

/**
 * Created by GT on 8/29/2017.
 */

public class Agent_Deal_Offer_Detail_Activity extends AppCompatActivity {
    ProgressDialog progressDialog;
    TextView added_date,branch_fee,branch_id,cat_id,city_name,clg_id,course_id,c_address,c_brochure,c_city,c_country,c_email,c_established,
            c_id,c_image,c_info,c_name,c_phone1,c_phone2,c_state,c_type,c_website,edited_date,edit_date,end_date,id,
            is_active,offer,rating,remaining_seats,start_date,state_name,top_deal,total_seats,user_id;
    Button id_btn_apply;
    String str_course,str_clg,str_brnch;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_deal_offer_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(!getIntent().equals(null))
        {
            bundle = getIntent().getExtras();
            str_course=bundle.getString("course");
            str_clg=bundle.getString("clg_id");
            str_brnch=bundle.getString("branch");
        }

        start_date=(TextView)findViewById(R.id.start_date);
        state_name=(TextView)findViewById(R.id.state_name);
        top_deal=(TextView)findViewById(R.id.top_deal);
        total_seats=(TextView)findViewById(R.id.total_seats);
        user_id=(TextView)findViewById(R.id.user_id);

        c_type=(TextView)findViewById(R.id.c_type);
        c_website=(TextView)findViewById(R.id.c_website);
        edited_date=(TextView)findViewById(R.id.edited_date);
        edit_date=(TextView)findViewById(R.id.edit_date);
        end_date=(TextView)findViewById(R.id.end_date);
        id=(TextView)findViewById(R.id.id);
        is_active=(TextView)findViewById(R.id.is_active);
        offer=(TextView)findViewById(R.id.offer);
        rating=(TextView)findViewById(R.id.rating);
        remaining_seats=(TextView)findViewById(R.id.remaining_seats);

        added_date=(TextView)findViewById(R.id.added_date);
        branch_fee=(TextView)findViewById(R.id.branch_fee);
        branch_id=(TextView)findViewById(R.id.branch_id);
        cat_id=(TextView)findViewById(R.id.cat_id);
        city_name=(TextView)findViewById(R.id.city_name);
        clg_id=(TextView)findViewById(R.id.course_id);
        course_id=(TextView)findViewById(R.id.course_id);
        c_address=(TextView)findViewById(R.id.c_address);
        c_brochure=(TextView)findViewById(R.id.c_brochure);
        c_city=(TextView)findViewById(R.id.c_city);

        c_country=(TextView)findViewById(R.id.c_country);
        c_email=(TextView)findViewById(R.id.c_email);
        c_established=(TextView)findViewById(R.id.c_established);
        c_id=(TextView)findViewById(R.id.c_id);
        c_image=(TextView)findViewById(R.id.c_image);
        c_info=(TextView)findViewById(R.id.c_info);
        c_name=(TextView)findViewById(R.id.c_name);
        c_phone1=(TextView)findViewById(R.id.c_phone1);
        c_phone2=(TextView)findViewById(R.id.c_phone2);
        c_state=(TextView)findViewById(R.id.c_state);

        id_btn_apply=(Button)findViewById(R.id.id_btn_apply);
        id_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!bundle.isEmpty()) {
                    Intent intent = new Intent(getBaseContext(), Admission_Form_Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        get_Offer_Detail();
    }
        private void get_Offer_Detail()
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setMessage(getString(R.string.LogIn));
            //  Log.d("agent_login_res", ""+str_email+"  "+str_pass);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.GET_AGENT_OFFER_DETAIL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                Log.d("srugfgts",response.toString());
                                if(!jObj.has("msg")) {
                                    JSONArray jsonArray = jObj.getJSONArray("data");
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                                    added_date.setText(jsonObject.getString("added_date"));
                                    branch_fee.setText(jsonObject.getString("branch_fee"));
                                    branch_id.setText(jsonObject.getString("branch_id"));
                                    cat_id.setText(jsonObject.getString("cat_id"));
                                    city_name.setText(jsonObject.getString("city_name"));
                                    clg_id.setText(jsonObject.getString("clg_id"));
                                    course_id.setText(jsonObject.getString("course_id"));
                                    c_address.setText(jsonObject.getString("c_address"));
                                    c_brochure.setText(jsonObject.getString("c_brochure"));
                                    c_city.setText(jsonObject.getString("c_city"));
                                    c_country.setText(jsonObject.getString("c_country"));
                                    c_email.setText(jsonObject.getString("c_email"));
                                    c_established.setText(jsonObject.getString("c_established"));
                                    c_id.setText(jsonObject.getString("c_id"));
                                    c_image.setText(jsonObject.getString("c_image"));
                                    c_info.setText(jsonObject.getString("c_info"));
                                    c_name.setText(jsonObject.getString("c_name"));
                                    c_phone1.setText(jsonObject.getString("c_phone1"));
                                    c_phone2.setText(jsonObject.getString("c_phone2"));
                                    c_state.setText(jsonObject.getString("c_state"));
                                    c_type.setText(jsonObject.getString("c_type"));
                                    c_website.setText(jsonObject.getString("c_website"));

                                    edited_date.setText(jsonObject.getString("edited_date"));
                                    edit_date.setText(jsonObject.getString("edit_date"));
                                    end_date.setText(jsonObject.getString("end_date"));
                                    id.setText(jsonObject.getString("id"));
                                    is_active.setText(jsonObject.getString("is_active"));
                                    offer.setText(jsonObject.getString("offer"));
                                    rating.setText(jsonObject.getString("rating"));
                                    remaining_seats.setText(jsonObject.getString("remaining_seats"));
                                    start_date.setText(jsonObject.getString("start_date"));
                                    state_name.setText(jsonObject.getString("state_name"));
                                    top_deal.setText(jsonObject.getString("top_deal"));
                                    total_seats.setText(jsonObject.getString("total_seats"));
                                    user_id.setText(jsonObject.getString("user_id"));
                                    progressDialog.cancel();

                                }else if((""+(jObj.getString("msg"))).equals("0"))
                                {
                                    progressDialog.cancel();
                                 //   Toast.makeText(getBaseContext(), "User Already Exist !!!", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                progressDialog.cancel();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.cancel();
                            //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence,0);
                    String str_email=sharedPreferences.getString("email", "na");
                    String str_token=sharedPreferences.getString("Login_Token", "na");
                    Map<String,String> map=new HashMap<>();
                    //college=lmcp&course=1&branch=1
                    map.put("email",str_email);
                    map.put("token",str_token);
                    Log.d("token",str_token);
                    Log.d("college",str_clg);
                    Log.d("course",str_course);
                    Log.d("branch",str_brnch);
                    map.put("college",str_clg);
                    map.put("course",str_course);
                    map.put("branch",str_brnch);

                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
            requestQueue.add(stringRequest);

        }

    }
