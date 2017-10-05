package fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.gt.active_education.R;

import org.json.JSONObject;

import java.util.HashMap;

import task.Async_Respoce;
import utilities.App_Raw_Data;
import utilities.App_Static_Method;
import utilities.Image_picker;
import utilities.State_City_Search;
import utilities.UrlEndpoints;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_ADDRESS;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_CITY;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_COLLEGE_NAME;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_COLLGE_SHORT_NAME;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_EMAIL;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_ESTABLISHED;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_INFORMATION;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_PHONE;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_STATE;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_TYPE;
import static extras.Keys.KEY_PARTNER_DETAIL.KEY_WEBSITE;
import static utilities.App_Static_Method.permission_check;
import static utilities.UrlEndpoints.GET_CITY;
import static utilities.UrlEndpoints.PARTNER_DETAIL;
import static utilities.UrlEndpoints.SEAT_SUBMIT;

/**
 * Created by GT on 9/29/2017.
 */

public class Partner_Seat_Submission extends Fragment {


    private EditText id_clg_name,id_course_name,id_brach_name,id_branch_fees,id_discnt_fees,id_total_seats,id_remain_seats,id_phone,id_information,id_course_duration;
    private Button btn_submit;
    private CheckBox id_term_con;
    private String str_clg_name,str_clg_phone,str_info,str_course_duration;
    private String str_course_name;
    private String str_branch_name;
    private String str_branch_fees;
    private String str_discount;
    private String str_total_seat;
    private String str_remain_seat;
    private HashMap<String, String> map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.partner_seat_submit_frg, container, false);
        id_clg_name=(EditText)rootView.findViewById(R.id.id_clg_name);
        id_course_name=(EditText)rootView.findViewById(R.id.id_course_name);
        id_brach_name=(EditText)rootView.findViewById(R.id.id_brach_name);
        id_course_duration=(EditText)rootView.findViewById(R.id.id_course_duration);
        id_branch_fees=(EditText)rootView.findViewById(R.id.id_branch_fees);
        id_discnt_fees=(EditText)rootView.findViewById(R.id.id_discnt_fees);
        id_total_seats=(EditText)rootView.findViewById(R.id.id_total_seats);
        id_remain_seats=(EditText)rootView.findViewById(R.id.id_remain_seats);
        id_information=(EditText)rootView.findViewById(R.id.id_information);
        id_phone=(EditText)rootView.findViewById(R.id.id_phone);
        id_term_con=(CheckBox)rootView.findViewById(R.id.id_term_con);
        btn_submit=(Button)rootView.findViewById(R.id.id_submit);
        btn_submit.setOnClickListener(v -> {
            Log.d("badfbba",""+"dgdgdg");
            if (id_term_con.isChecked())
            {
                str_clg_name=id_clg_name.getText().toString();
                str_course_name=id_course_name.getText().toString();
                str_branch_name=id_brach_name.getText().toString();
                str_course_duration=id_course_duration.getText().toString();
                str_branch_fees=id_branch_fees.getText().toString();
                str_discount=id_discnt_fees.getText().toString();
                str_total_seat=id_total_seats.getText().toString();
                str_remain_seat=id_remain_seats.getText().toString();
                str_clg_phone=id_phone.getText().toString();
                str_info=id_information.getText().toString();
             //   str_clgestab_year=id_information.getText().toString();

                if (!App_Static_Method.isValidName(str_clg_name)) {     // fname
                    id_clg_name.setError("You must more characters");
                } else {
                    if (!App_Static_Method.isValidName(str_course_name)) {     // fname
                        id_course_name.setError("You must more characters");
                    } else {

                        if (!App_Static_Method.isValidName(str_branch_name)) {
                            id_brach_name.setError("Please Enter a Valid State !!! ");
                        } else {

                            if (!App_Static_Method.isValidName(str_course_duration)) {
                                id_course_duration.setError("Please Enter a Valid Address !!! ");
                            } else {
                                if (!App_Static_Method.isValidPhone(str_clg_phone)) {
                                    id_phone.setError("Please Enter a Valid Address !!! ");
                                } else {
                                    if (!App_Static_Method.isValidName(str_branch_fees)) {
                                        id_branch_fees.setError("Please Enter a Valid Address !!! ");
                                    } else {

                                        if (!App_Static_Method.isValidName(str_discount)) {
                                            id_discnt_fees.setError("Please Enter a Valid Website !!! ");
                                        } else {

                                            if (!App_Static_Method.isValidName(str_total_seat)) {
                                                id_total_seats.setError("Please Enter a Valid Website !!! ");
                                            } else {

                                                if (!App_Static_Method.isValidName(str_remain_seat)) {
                                                    id_remain_seats.setError("Please Enter a Valid Website !!! ");
                                                } else {

                                                    if (!App_Static_Method.isValidName(str_info)) {
                                                        id_information.setError("Please Enter a Valid Website !!! ");
                                                    } else {
                                                        map = new HashMap<String, String>();
                                                        map.put("college", str_clg_name);
                                                        map.put("course", str_course_name);
                                                        map.put("branch", str_branch_name);
                                                        map.put("branch_fee", str_branch_fees);
                                                        map.put("discounted_fee",str_discount );
                                                        map.put("total_seats", str_total_seat);
                                                        map.put("remaining_seats", str_remain_seat);
                                                     /* progressDialog = new ProgressDialog(Partner_Detail_Frag.this.getContext());
                                                        progressDialog.setCancelable(true);
                                                        progressDialog.show();
                                                        progressDialog.setMessage(getString(R.string.SignUp));
*/
                                                        new Async_Respoce(new Async_Respoce.Responce_Obj_Lisatener() {

                                                            @Override
                                                            public void on_responce(JSONObject listMovies) {

                                                            Log.d("objectggg",listMovies.toString());

                                                            }
                                                        }, SEAT_SUBMIT, map).execute();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                  }

            }else {

            }
        });

        return rootView;
    }

}
