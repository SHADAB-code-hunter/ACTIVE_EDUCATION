package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gt.active_education.Login_Fior_Guest_Activity;
import com.gt.active_education.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fragment.Payment_Page;
import task.Asynch_Obj;

import static utilities.App_Static_Method.get_session_type;
import static utilities.App_Static_Method.toMap;

/**
 * Created by GT on 11/15/2017.
 */

public class T_and_C_Dialog extends Dialog implements View.OnClickListener {

    private EditText edt_uname, edt_email, edt_mobile;
    private Button btn_login;
    private TextView id_Reg_here,id_term_c;
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
    private EditText amt;
    private Button pay;


    public T_and_C_Dialog(User_Boking_Listener userBokingListener,JSONObject jsonObject, Activity activity) {
        super(activity);
        this.activity=activity;
        this.userBokingListener=userBokingListener;
        this.jsonObject=jsonObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ter_and_cond_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // create own drawable
        id_term_c=(TextView)findViewById(R.id.id_term_c);
        amt = (EditText) findViewById(R.id.amount);
        pay = (Button) findViewById(R.id.pay_btn);pay.setOnClickListener(this);


        try {
            new NEW_ASYNCH(new NEW_ASYNCH.JOBJ_LISTENER() {
                @Override
                public void on_listener(JSONObject jsonobject, String loginApi) {
                  //  userBokingListener.on_dialog__listener(jsonobject);
                    try {
                        id_term_c.setText(jsonobject.getJSONArray("data").getJSONObject(0).getString("name"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },toMap(jsonObject),UrlEndpoints.MAIN_URL_school_TERM,"TERM").execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.pay_btn:
                // if(get_session_type().equals())
                JSONObject jsonObject=null;
                try {
                    jsonObject= new JSONObject(get_session_type());
                    //          Toast.makeText(context, ""+jsonObject.getString("type"), Toast.LENGTH_SHORT).show();
                    switch (jsonObject.getString("type"))
                    {

                        case "guest":

                            activity.startActivity(new Intent(T_and_C_Dialog.this.getContext(), Login_Fior_Guest_Activity.class));
                            break;
                        case "user":
                            fake_apyment(jsonObject);
                            //  startActivity(new Intent(Payment_Page.this.getContext(), Login_Fior_Guest_Activity.class));
                            break;
                        case "agent":
                            fake_apyment(jsonObject);
                            //  startActivity(new Intent(Payment_Page.this.getContext(), Login_Fior_Guest_Activity.class));
                            break;

                    }


                    Log.d("sessionrtpe",""+get_session_type());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }


    public interface User_Boking_Listener {

        public void on_dialog__listener(JSONObject map);
    }
    private void fake_apyment(JSONObject jsonObject) {

        SharedPreferences sharedPreferences =activity.getSharedPreferences(UpdateValues.FORM_ID, 0);

        try {

            jsonObject.put("formid",""+sharedPreferences.getString("Form_ID","NA"));
            jsonObject.put("price",jsonObject.getString("branch_fee"));
            Log.d("ojf_jn",""+jsonObject);
            if(!jsonObject.getString("formid").equalsIgnoreCase("NA"))
            {
                new Asynch_Obj(new Asynch_Obj.OBJ_Lister() {
                    @Override
                    public void on_lis_obj(JSONObject jsonObject, String str_key) {
                        Log.d("jsonObject", "" + jsonObject.toString());
                       /* price:100*/
                        try {
                            if(jsonObject.getInt("msg")==1) {
                                SharedPreferences sharedPreferences = activity.getSharedPreferences(UpdateValues.FORM_ID, 0);
                                SharedPreferences.Editor editor= sharedPreferences.edit();
                                editor.putString("Form_ID","NA");
                                editor.commit();


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, UrlEndpoints.SEAT_USER_PAYEMNT, toMap(jsonObject), "payment").execute();
            }else {
                Toast.makeText(activity, "Please Fill The Admission Detail First ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d("Eceio",""+e.getMessage());
        }

    }

}
