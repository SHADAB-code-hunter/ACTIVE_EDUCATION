package utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gt.active_education.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import callbacks.Dialog_listener;

import static utilities.App_Static_Method.get_session_type;

/**
 * Created by GT on 11/15/2017.
 */

public class Migrate_Dialog extends Dialog implements View.OnClickListener {

    private EditText edt_uname, edt_email, edt_mobile;
    private Button btn_login;
    private TextView id_Reg_here;
    private Activity activity;
    private RadioGroup id_radio_group;
    private int payment_option;
    private LinearLayout id_book_date_linear;
    private LinearLayout id_book_time_linaer;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView id_book_date,id_book_time;
    private Calendar c;
    Map<String, String> map = new HashMap<>();
    User_Boking_Listener userBokingListener;
    private RadioButton id_student;
    private RadioButton id_partner;
    private String  url_submit="Cash on delivery";
    private JSONObject jsonObject;


    public Migrate_Dialog(User_Boking_Listener userBokingListener, Activity activity) {
        super(activity);
        this.activity=activity;
        this.userBokingListener=userBokingListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_booking);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // create own drawable

           /* "booking_date": "12/11/2017",
            "booking_time": "12:22 am", */
        edt_uname = (EditText) findViewById(R.id.edt_uname);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        btn_login = (Button) findViewById(R.id.btn_login);
        id_Reg_here = (TextView) findViewById(R.id.id_Reg_here);id_Reg_here.setOnClickListener(this);
       /* id_radio_group = (RadioGroup) findViewById(R.id.id_radio_group);
        id_book_date_linear= (LinearLayout) findViewById(R.id.id_book_date_linear);id_book_date_linear.setOnClickListener(this);
        id_book_time_linaer= (LinearLayout) findViewById(R.id.id_book_time_linaer);id_book_time_linaer.setOnClickListener(this);
        id_book_date = (TextView) findViewById(R.id.id_book_date);*/
       /* id_book_time = (TextView) findViewById(R.id.id_book_time);*/
        id_partner=(RadioButton) findViewById(R.id.id_partner);
        id_student=(RadioButton) findViewById(R.id.id_student);



        id_partner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                url_submit="http://activeeduindia.com/new_admin/webservices/migrateToAgent.php";
                Toast.makeText(activity, ""+url_submit, Toast.LENGTH_SHORT).show();
            }
        });
        id_student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                url_submit="http://activeeduindia.com/new_admin/webservices/migrateToUser.php";
                Toast.makeText(activity, ""+url_submit, Toast.LENGTH_SHORT).show();
            }
        });
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_login:
                try {

                                      jsonObject=new JSONObject(get_session_type());
                                      jsonObject.put("URL_TYPE",""+url_submit);
                                    (userBokingListener).on_dialog__listener(jsonObject);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


        }
    }


   /* public void on_res_litsner(Map<String, String> map) {

        try {
        if(map==null) {
            return;
        }



            *//*JSONObject jsonObject1=(jsonObject.getJSONArray("status")).getJSONObject(0);
            SharedPreferences guest_shard = MyApplication.getAppContext().getSharedPreferences(GUSET_SESSION, 0);
            SharedPreferences.Editor editor = guest_shard.edit();
            editor.putString("Guest_ssesion",jsonObject1.toString());*//*
            //Log.d("Guest_ssesion", jsonObject.toString());


        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public interface User_Boking_Listener {

        public void on_dialog__listener(JSONObject map);
    }

}
