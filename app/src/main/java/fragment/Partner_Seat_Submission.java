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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gt.active_education.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import task.Async_Respoce;
import utilities.App_Raw_Data;
import utilities.App_Static_Method;
import utilities.Inner_Filter_Dialog;
import utilities.State_City_Search;

import static utilities.UrlEndpoints.GET_ALL_COURSE;
import static utilities.UrlEndpoints.GET_BRANCH_BY_ID;
import static utilities.UrlEndpoints.SEAT_SUBMIT_partner;
import static utilities.UrlEndpoints.get_filter_list;

/**
 * Created by GT on 9/29/2017.
 */

public class Partner_Seat_Submission extends Fragment {


    private EditText id_clg_name,id_course_name,id_brach_name,id_branch_fees,id_discnt_fees,id_total_seats,id_remain_seats,id_phone,id_information;//,id_course_duration;
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
    private Map<String, String> map_store=new HashMap<String, String>();
    private FrameLayout id_course_frm,id_branch_frm;
    private TextView id_tv_course,id_tv_branch;
    private boolean open=false;
    private State_City_Search state_city_search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.partner_seat_submit_frg, container, false);
        id_clg_name=(EditText)rootView.findViewById(R.id.id_clg_name);
       // id_course_duration=(EditText)rootView.findViewById(R.id.id_course_duration);
        id_branch_fees=(EditText)rootView.findViewById(R.id.id_branch_fees);
        id_discnt_fees=(EditText)rootView.findViewById(R.id.id_discnt_fees);
        id_total_seats=(EditText)rootView.findViewById(R.id.id_total_seats);
        id_remain_seats=(EditText)rootView.findViewById(R.id.id_remain_seats);
        id_information=(EditText)rootView.findViewById(R.id.id_information);
        id_phone=(EditText)rootView.findViewById(R.id.id_phone);
        id_term_con=(CheckBox)rootView.findViewById(R.id.id_term_con);
        btn_submit=(Button)rootView.findViewById(R.id.id_submit);

        id_tv_course=(TextView)rootView.findViewById(R.id.id_tv_course);
        id_tv_branch=(TextView)rootView.findViewById(R.id.id_tv_branch);

        id_course_frm=(FrameLayout)rootView.findViewById(R.id.id_course_frm);
        id_branch_frm=(FrameLayout)rootView.findViewById(R.id.id_branch_frm);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("badfbba", "" + "dgdgdg");
                if (id_term_con.isChecked()) {
                    str_clg_name = id_clg_name.getText().toString();
                  /*  str_course_name = id_course_name.getText().toString();
                    str_branch_name = id_brach_name.getText().toString();*/
               //     str_course_duration = id_course_duration.getText().toString();
                    str_branch_fees = id_branch_fees.getText().toString();
                    str_discount = id_discnt_fees.getText().toString();
                    str_total_seat = id_total_seats.getText().toString();
                    str_remain_seat = id_remain_seats.getText().toString();
               //     str_clg_phone = id_phone.getText().toString();
//                    str_info = id_information.getText().toString();
                    //   str_clgestab_year=id_information.getText().toString();

                  /*  if (!App_Static_Method.isValidName(str_clg_name)) {     // fname
                        id_clg_name.setError("You must more characters");
                    } else {*/
                      /*  if (!App_Static_Method.isValidName(str_course_name)) {     // fname
                            id_course_name.setError("You must more characters");
                        } else {

                            if (!App_Static_Method.isValidName(str_branch_name)) {
                                id_brach_name.setError("Please Enter a Valid State !!! ");
                            } else {*/

                               /* if (!App_Static_Method.isValidName(str_course_duration)) {
                                    id_course_duration.setError("Please Enter a Valid Address !!! ");
                                } else {*/
                                   /* if (!App_Static_Method.isValidPhone(str_clg_phone)) {
                                        id_phone.setError("Please Enter a Valid Address !!! ");
                                    } else {*/
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

                                                       /* if (!App_Static_Method.isValidName(str_info)) {
                                                            id_information.setError("Please Enter a Valid Website !!! ");
                                                        } else {*/
                                                            map = new HashMap<String, String>();
                                                            map.putAll(App_Static_Method.session_type());
                                                            map.put("fee", str_branch_fees);
                                                            map.put("discountedFee", str_discount);
                                                            map.put("totalSeats", str_total_seat);
                                                            map.put("remainingSeats", str_remain_seat);
                                                            if(map_store!=null)
                                                            map.putAll(map_store);
                                                            Log.d("mamsmjchv",""+map);
                                                             /* progressDialog = new ProgressDialog(Partner_Detail_Frag.this.getContext());
                                                                progressDialog.setCancelable(true);
                                                                progressDialog.show();
                                                                progressDialog.setMessage(getString(R.string.SignUp));
        */
                                                            new Async_Respoce(new Async_Respoce.Responce_Obj_Lisatener() {

                                                                @Override
                                                                public void on_responce(JSONObject listMovies) {

                                                                    Log.d("objectggg", ""+listMovies);
                                                                    Toast.makeText(Partner_Seat_Submission.this.getContext(), "Seat Submitted Successfully", Toast.LENGTH_SHORT).show();

                                                                }
                                                            }, SEAT_SUBMIT_partner, map).execute();
                                                    //    }
                                                    }
                                                }
                                            }
                                   //     }
                                   // }
                               // }
                            }
                      //  }
                  //  }

                } else {

                }
            }
        });


        id_course_frm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!open) {
                    open = true;

                    if (id_tv_course.getText().toString() != null) {
                        // StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s,String s_id) {

                                  Log.d("jdjfhf",""+s);
                                if(!s.equals("na")){
                                    id_tv_course.setText(s);
                                    map_store.put("course",s_id);
                                }else {
                                    id_tv_course.setText("Select Course");
                                }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Partner_Seat_Submission.this.getContext(), GET_ALL_COURSE,"data");
                        state_city_search.show();
                    } else {
                        Toast.makeText(Partner_Seat_Submission.this.getContext(), "Please Select State First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        id_branch_frm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!open) {
                    open = true;

                    if (id_tv_course.getText().toString() != null) {
                        //  StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s,String s_id) {

                                //  Log.d("jdjfhf",""+s);
                                if(!s.equals("na")){
                                    id_tv_branch.setText(s);
                                    map_store.put("branch",s_id);

                                }else {
                                    id_tv_branch.setText("Select Branch");
                                }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Partner_Seat_Submission.this.getContext(), GET_BRANCH_BY_ID+map_store.get("course"),"data");
                        state_city_search.show();
                    } else {
                        Toast.makeText(Partner_Seat_Submission.this.getContext(), "Please Select Course First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });


        return rootView;
    }

   /* private void set_clg_address(View rootView) {

        Log.d("hintstring", id_clg_name.getHint().toString());
        str_partner_address_server_key=new String[]{"building" ,"area","location","district","tahsheel","pin"};
        str_partner_address_hint_key=new String[]{"Enter Building Number","Enter Area Name","Enter Nearest Location","Enter District","Enter Tahsheel","Enter Pincode"};

        setText_Add_Watcher(id_building_number, str_partner_address_hint_key);
        setText_Add_Watcher(id_area, str_partner_address_hint_key);
        setText_Add_Watcher(id_edt_location, str_partner_address_hint_key);
        setText_Add_Watcher(id_edt_dist, str_partner_address_hint_key);
        setText_Add_Watcher(id_tehshil, str_partner_address_hint_key);
        setText_Add_Watcher(id_pincode, str_partner_address_hint_key);

    }
*/
}
