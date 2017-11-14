package com.gt.active_education;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by GT on 11/6/2017.
 */

public class Booking_Detail_Activity extends AppCompatActivity {

    private TextView id_f_name,id_l_name,id_clg_name,course_name,id_brnch_name,fees_status,id_booking_date,id_fees_yearly,
            id_clg_add,id_st_add,id_state,id_city,id_pincode;
    private ImageView id_prfl_img;
    private TextView mobile_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        id_f_name=(TextView)findViewById(R.id.id_f_name);
        id_l_name=(TextView)findViewById(R.id.id_l_name);
        id_clg_name=(TextView)findViewById(R.id.id_clg_name);
        course_name=(TextView)findViewById(R.id.course_name);
        id_brnch_name=(TextView)findViewById(R.id.id_brnch_name);
        mobile_id=(TextView)findViewById(R.id.mobile_id);
        fees_status=(TextView)findViewById(R.id.fees_status);
        id_booking_date=(TextView)findViewById(R.id.id_booking_date);
        id_fees_yearly=(TextView)findViewById(R.id.id_fees_yearly);
        id_clg_add=(TextView)findViewById(R.id.id_clg_add);
        id_prfl_img=(ImageView)findViewById(R.id.id_prfl_img);
        id_st_add=(TextView)findViewById(R.id.id_st_add);
        id_state=(TextView)findViewById(R.id.id_state);
        id_city=(TextView)findViewById(R.id.id_city);
        id_pincode=(TextView)findViewById(R.id.id_pincode);

        try {
        if(getIntent()!=null)
        {
            JSONObject jsonObject= new JSONObject(getIntent().getStringExtra("Booking_Object"));
            id_f_name.setText("First  : "+jsonObject.getString("f_name"));
            id_l_name.setText("Last : "+jsonObject.getString("l_name"));
            mobile_id.setText("Mobile : "+jsonObject.getString("mobile"));

            if(jsonObject.getString("fee_status").equals("0"))
            {
                fees_status.setText("Fees Status : "+" Unpaid");
            }else {
                fees_status.setText("Fees Status : "+"Paid");
            }

            id_clg_name.setText("College : "+jsonObject.getString("clg_name"));
            course_name.setText("Course : "+jsonObject.getString("course_name"));
            id_brnch_name.setText("Branch : "+jsonObject.getString("branch_name"));
            id_booking_date.setText("Booing Date : "+jsonObject.getString("added_date"));
            id_fees_yearly.setText("Price : "+jsonObject.getString("price"));
            id_clg_add.setText("Address : "+jsonObject.getString("address"));
            id_state.setText("State : "+jsonObject.getString("state"));
            id_city.setText("City : "+jsonObject.getString("city"));
            id_pincode.setText("Pincode : "+jsonObject.getString("pincode"));

               /* Glide.with()
                        .placeholder(android.R.color.darker_gray).config(Bitmap.Config.RGB_565).into(id_prfl_img);*/

        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
