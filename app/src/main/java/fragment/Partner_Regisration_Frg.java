package fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.Filter_Activity;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.Common_pojo_adapter;
import adapter.Facility_Adapter;
import callbacks.JOBJ_Listener;
import task.Asynch_Agent_Form_JObject;
import task.Asynch_Book_Responce;
import task.Asynch_Obj;
import task.Asynch_Responce_OBJ;
import task.Asynch_facility_req;
import utilities.App_Raw_Data;
import utilities.App_Static_Method;
import utilities.Common_Pojo;
import utilities.Custom_Dialog_FTP;
import utilities.Custom_Dialog__FTP_SERVER;
import utilities.MyApplication;
import utilities.Partner_Attach_Dialog;
import utilities.RecyclerTouchListener;
import utilities.State_City_Search;
import utilities.UrlEndpoints;

import static com.facebook.FacebookSdk.getApplicationContext;
import static utilities.App_Static_Method.progressDialog;
import static utilities.App_Static_Method.session_type;
import static utilities.UrlEndpoints.SEAT_PROVIDER_BROCHURE;
import static utilities.UrlEndpoints.SEAT_PROVIDER_CERTIFICATES;
import static utilities.UrlEndpoints.SEAT_PROVIDER_FACILITY;
import static utilities.UrlEndpoints.SEAT_PROVIDER_FACILITY_UPDATED;
import static utilities.UrlEndpoints.SEAT_PROVIDER_IAMGE;
import static utilities.UrlEndpoints.get_filter_list;

/**
 * Created by GT on 10/2/2017.
 */

public class Partner_Regisration_Frg extends Fragment implements View.OnClickListener , JOBJ_Listener, Asynch_Responce_OBJ.OBJ_LISTENER {

    SwitchCompat switch1, switch2,switch_button1_socail,switch_btn_clg_img;
    ExpandableRelativeLayout layout1, layout2,socail_layout1,id_expand_attach_images;
    private CompoundButton.OnCheckedChangeListener listener;
    private ExpandableRelativeLayout id_expand_attach_brochre;
    private SwitchCompat switch_btn_clg_brochure;
    private Button id_btn_submit,id_add_save_btn;
    private ExpandableRelativeLayout id_expand_attach_certificate;
    private SwitchCompat switch_btn_clg_crtficate;
    private ExpandableRelativeLayout id_expand_bank_detail;
    private SwitchCompat switch_btn_clg_bank_detail;
    private SwitchCompat switch_button2_address;
    private ExpandableRelativeLayout id_expand_addr;
    private EditText id_clg_name,id_short_clg_name,id_phone,id_edt_land_number,id_email,id_website,id_est_year,id_clg_about;
    private EditText id_building_number,id_area,id_edt_location,id_edt_dist,id_tehshil,id_pincode;
    private EditText id_gplus,id_fb,id_whatsapp,id_twitter,id_linked_account,id_skype,id_instarag,id_tumblr;
    private String[] str_partner_hint_key;
    private String[] str_partner_server_key,str_partner_address_server_key;
    private Map<String, String> map=new HashMap<>();
    private Map<String, String> map_add=new HashMap<>();
    private Map<String, String> map_social=new HashMap<>();
    private Map<String, String> map_facility=new HashMap<>();
    private Button id_detail_save_btn;
    private FrameLayout id_state_frm;
    private FrameLayout id_city_frm;
    private boolean open=false;
    private State_City_Search state_city_search;
    private TextView id_tv_state,id_tv_city,id_img_attach,id_cert_attach;
    private String[] str_partner_address_hint_key;
    private Button id_social_save_btn,id_social_facilitysave_btn;
    private String[] str_SOCIAL_server_key;
    private String[] str_SOCIAL_hint_key;
    private TextView id_brochure_attach;
    private RecyclerView id_recycler_view;
    private ArrayList<Map<String,String>> arryList=new ArrayList<>();
    private   JSONArray jsonArray1=new JSONArray();
    private JSONObject res_jsonObject=new JSONObject();
    private JSONArray req_jsonarray=new JSONArray();
    private JSONArray res_jsonArray=new JSONArray();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.frg_partner_registeration, container, false);

        id_state_frm=(FrameLayout)rootView.findViewById(R.id.id_state_frm);
        id_city_frm=(FrameLayout)rootView.findViewById(R.id.id_city_frm);

        id_tv_state=(TextView)rootView.findViewById(R.id.id_tv_state);
        id_tv_city=(TextView)rootView.findViewById(R.id.id_tv_city);
        id_img_attach=(TextView)rootView.findViewById(R.id.id_img_attach);id_img_attach.setOnClickListener(this);
        id_cert_attach=(TextView)rootView.findViewById(R.id.id_cert_attach);id_cert_attach.setOnClickListener(this);
        id_brochure_attach=(TextView)rootView.findViewById(R.id.id_brochure_attach);id_brochure_attach.setOnClickListener(this);

        id_recycler_view=(RecyclerView)rootView.findViewById(R.id.id_recycler_view);
        /**/

        id_building_number=(EditText)rootView.findViewById(R.id.id_building_number);
        id_area=(EditText)rootView.findViewById(R.id.id_area);
        id_edt_location=(EditText)rootView.findViewById(R.id.id_edt_location);
        id_edt_dist=(EditText)rootView.findViewById(R.id.id_edt_dist);
        id_tehshil=(EditText)rootView.findViewById(R.id.id_tehshil);
        id_pincode=(EditText)rootView.findViewById(R.id.id_pincode);

        id_add_save_btn=(Button)rootView.findViewById(R.id.id_add_save_btn);id_add_save_btn.setOnClickListener(this);
        id_social_save_btn=(Button)rootView.findViewById(R.id.id_social_save_btn);id_social_save_btn.setOnClickListener(this);
        id_social_facilitysave_btn=(Button)rootView.findViewById(R.id.id_social_facilitysave_btn);id_social_facilitysave_btn.setOnClickListener(this);


        listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggle(compoundButton);
            }
        };

        set_clg_detail(rootView);
        set_clg_address(rootView);
        set_clg_social(rootView);

        layout1 = (ExpandableRelativeLayout)rootView. findViewById(R.id.expandableLayout1);layout1.collapse();
        switch1 = (SwitchCompat)rootView. findViewById(R.id.switch_button1);
        switch1.setOnCheckedChangeListener(listener);


        switch_button2_address = (SwitchCompat) rootView.findViewById(R.id.switch_button2_address);
        switch_button2_address.setOnCheckedChangeListener(listener);
        id_expand_addr = (ExpandableRelativeLayout) rootView.findViewById(R.id.id_expand_addr);


        /*  */

        socail_layout1 = (ExpandableRelativeLayout)rootView. findViewById(R.id.id_exp_social);socail_layout1.collapse();
        switch_button1_socail = (SwitchCompat)rootView. findViewById(R.id.switch_button1_socail);
        switch_button1_socail.setOnCheckedChangeListener(listener);

        switch_btn_clg_img = (SwitchCompat) rootView.findViewById(R.id.switch_btn_clg_img);
        switch_btn_clg_img.setOnCheckedChangeListener(listener);
        id_expand_attach_images = (ExpandableRelativeLayout) rootView.findViewById(R.id.id_expand_attach_images);
         /*  */

          /*  */

        id_expand_attach_brochre = (ExpandableRelativeLayout)rootView. findViewById(R.id.id_expand_attach_brochre);id_expand_attach_brochre.collapse();
        switch_btn_clg_brochure = (SwitchCompat)rootView. findViewById(R.id.switch_btn_clg_brochure);
        switch_btn_clg_brochure.setOnCheckedChangeListener(listener);

        switch_btn_clg_crtficate = (SwitchCompat) rootView.findViewById(R.id.switch_btn_clg_crtficate);
        switch_btn_clg_crtficate.setOnCheckedChangeListener(listener);
        id_expand_attach_certificate = (ExpandableRelativeLayout) rootView.findViewById(R.id.id_expand_attach_certificate);id_expand_attach_certificate.collapse();

        switch_btn_clg_bank_detail = (SwitchCompat) rootView.findViewById(R.id.switch_btn_clg_bank_detail);
        switch_btn_clg_bank_detail.setOnCheckedChangeListener(listener);
        id_expand_bank_detail = (ExpandableRelativeLayout) rootView.findViewById(R.id.id_expand_bank_detail);id_expand_bank_detail.collapse();
         /*  */
      //  id_btn_submit=(Button)rootView.findViewById(R.id.id_btn_submit);id_btn_submit.setOnClickListener(this);




        id_state_frm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!open) {
                    open = true;

                    state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                        @Override
                        public void on_listdata(String s,String s_id) {
                            // id_tv_state.setText(s);id_tv_city.setText("Select City");
                            Log.d("kkjkjkjkj",""+s_id);
                            if(!s.equals("na")){
                                id_tv_state.setText(s);
                                id_tv_city.setText("Select City");
                                map.put("state_id", ""+ App_Raw_Data.local_parseJson(s));
                                Log.d("sgsdfe",""+App_Raw_Data.local_parseJson(s))  ;                                                      }
                            else {
                                id_tv_state.setText("Select State"); id_tv_city.setText("Select City");
                            }
                            state_city_search.cancel();
                            open=false;
                        }
                    }, Partner_Regisration_Frg.this.getContext(), get_filter_list+"1","state");
                    state_city_search.show();
                }
                return false;
            }
        });

        id_city_frm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!open) {
                    open = true;

                    if (id_tv_state.getText().toString() != null) {
                        StringBuilder stringBuilder = App_Raw_Data.local_parseJson(id_tv_state.getText().toString());
                        state_city_search = new State_City_Search(new State_City_Search.Dialog_Spinner_Listener() {
                            @Override
                            public void on_listdata(String s,String s_id) {

                                //  Log.d("jdjfhf",""+s);
                                if(!s.equals("na")){
                                    id_tv_city.setText(s);

                                    map.put("city_id",s_id);
                                }else {
                                    id_tv_city.setText("Select City");
                                }
                                state_city_search.cancel();
                                open = false;
                            }
                        }, Partner_Regisration_Frg.this.getContext(), get_filter_list+"1" +"&state="+map.get("state_id"),"city");
                        state_city_search.show();
                    } else {
                        Toast.makeText(Partner_Regisration_Frg.this.getContext(), "Please Select State First !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        id_recycler_view.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),id_recycler_view,
            new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    try {

                    if(req_jsonarray!=null) {

                        req_jsonarray.put(res_jsonArray.getJSONObject(position));

                      }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }
         ));

        new Asynch_Responce_OBJ(Partner_Regisration_Frg.this,SEAT_PROVIDER_FACILITY,session_type()).execute();

        return rootView;

    }

    private void set_clg_address(View rootView) {

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

    private void set_clg_social(View rootView) {
        id_gplus=(EditText)rootView.findViewById(R.id.id_gplus);
        id_fb=(EditText)rootView.findViewById(R.id.id_fb);
        id_whatsapp=(EditText)rootView.findViewById(R.id.id_whatsapp);
        id_twitter=(EditText)rootView.findViewById(R.id.id_twitter);
        id_linked_account=(EditText)rootView.findViewById(R.id.id_linked_account);
        id_skype=(EditText)rootView.findViewById(R.id.id_skype);
        id_instarag=(EditText)rootView.findViewById(R.id.id_instarag);
        id_tumblr=(EditText)rootView.findViewById(R.id.id_tumblr);

  //    id_detail_save_btn=(Button)rootView.findViewById(R.id.id_detail_save_btn);id_detail_save_btn.setOnClickListener(this);

         Log.d("hintstring", id_clg_name.getHint().toString());

         str_SOCIAL_server_key=new String[]{"name" ,"shortName","phone","landline","email","website","established","information"};
         str_SOCIAL_hint_key=new String[]{"Google Account" ,"Facebook Account"," WhatsApp","Twitter Account","LinkedIn Account","Skype Account","Instagram Account", "Tumblr Account"};

        set_SOCIAL_TextWatcher(id_gplus, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_fb, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_whatsapp, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_twitter, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_linked_account, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_skype, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_instarag, str_SOCIAL_hint_key);
        set_SOCIAL_TextWatcher(id_tumblr, str_SOCIAL_hint_key);

    }
    private void set_clg_detail(View rootView) {
        id_clg_name=(EditText)rootView.findViewById(R.id.id_clg_name);
        id_short_clg_name=(EditText)rootView.findViewById(R.id.id_short_clg_name);
        id_phone=(EditText)rootView.findViewById(R.id.id_phone);
        id_edt_land_number=(EditText)rootView.findViewById(R.id.id_edt_land_number);
        id_email=(EditText)rootView.findViewById(R.id.id_email);
        id_website=(EditText)rootView.findViewById(R.id.id_website);
        id_est_year=(EditText)rootView.findViewById(R.id.id_est_year);
        id_clg_about=(EditText)rootView.findViewById(R.id.id_clg_about);

        // social addaress

        id_detail_save_btn=(Button)rootView.findViewById(R.id.id_detail_save_btn);id_detail_save_btn.setOnClickListener(this);

         Log.d("hintstring", id_clg_name.getHint().toString());
         str_partner_server_key=new String[]{"name" ,"shortName","phone","landline","email","website","established","information"};
         str_partner_hint_key=new String[]{"Enter College Name" ,"Enter College Short Name","Enter Mobile Number"," Enter LandLine Number","Enter E-Mail"," Enter Website","Enter Established Year",
                "Type your Portfolio Description"};

        setTextWatcher(id_clg_name,str_partner_hint_key);
        setTextWatcher(id_short_clg_name, str_partner_hint_key);
        setTextWatcher(id_phone, str_partner_hint_key);
        setTextWatcher(id_edt_land_number, str_partner_hint_key);
        setTextWatcher(id_email, str_partner_hint_key);
        setTextWatcher(id_website, str_partner_hint_key);
        setTextWatcher(id_est_year, str_partner_hint_key);
        setTextWatcher(id_clg_about, str_partner_hint_key);

    }
    private void setTextWatcher(final EditText id_edt_a,final String[] str_partner_hint_key) {

        id_edt_a.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                Log.d("editabale","g   "+s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("dcccdc","djhj   "+s);
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                if(id_edt_a.getHint().toString()!=null) {
                    //  Log.d("str_key", "1");
                    for (int i = 0; i < str_partner_hint_key.length; i++) {
                        String str_server = str_partner_hint_key[i];
                        //  Log.d("str_server", "2" + str_server);
                        //  Log.d("id_edt", "3" + id_edt_a.getHint().toString());
                        if (id_edt_a.getHint().toString().equals(str_server)) {
                            String str_key = str_partner_server_key[i];
                            map.put(str_key, query.toString());
                            Log.d("str_key", str_key);
                        }
                    }
                }
            }
        });
    }
    private void setText_Add_Watcher(final EditText id_edt_a,final String[] str_partner_hint_key) {

            id_edt_a.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                Log.d("editabale","g   "+s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("dcccdc","djhj   "+s);
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                if(id_edt_a.getHint().toString()!=null) {
                    //  Log.d("str_key", "1");
                    for (int i = 0; i < str_partner_hint_key.length; i++) {
                        String str_server = str_partner_hint_key[i];
                        //  Log.d("str_server", "2" + str_server);
                        //  Log.d("id_edt", "3" + id_edt_a.getHint().toString());
                        if (id_edt_a.getHint().toString().equals(str_server)) {
                            String str_key = str_partner_address_server_key[i];
                            map_add.put(str_key, query.toString());
                            Log.d("str_key", str_key);
                        }
                    }
                }
            }
        });
    }
    private void set_SOCIAL_TextWatcher(final EditText id_edt_a,final String[] str_partner_hint_key) {

            id_edt_a.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                Log.d("editabale","g   "+s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("dcccdc","djhj   "+s);
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {
                if(id_edt_a.getHint().toString()!=null) {
                    //  Log.d("str_key", "1");
                    for (int i = 0; i < str_partner_hint_key.length; i++) {
                        String str_server = str_partner_hint_key[i];
                        //  Log.d("str_server", "2" + str_server);
                        //  Log.d("id_edt", "3" + id_edt_a.getHint().toString());
                        if (id_edt_a.getHint().toString().equals(str_server)) {
                            String str_key = str_SOCIAL_server_key[i];
                            map_add.put(str_key, query.toString());
                            Log.d("str_key", str_key);
                        }
                    }
                }
            }
        });
    }


    private void toggle(View v) {
        if(v.getId() == R.id.switch_button1)
        {
            layout1.toggle();
        }
        else if(v.getId() == R.id.switch_button2_address)
        {
            id_expand_addr.toggle();
        }
        else if(v.getId() == R.id.switch_button1_socail)
        {
            socail_layout1.toggle();
        }
        else if(v.getId() == R.id.switch_btn_clg_img)
        {
            id_expand_attach_images.toggle();
        }
        else if(v.getId() == R.id.switch_btn_clg_brochure)
        {
            id_expand_attach_brochre.toggle();
        }
        else if(v.getId() == R.id.switch_btn_clg_crtficate)
        {
            id_expand_attach_certificate.toggle();
        }
        else if(v.getId() == R.id.switch_btn_clg_bank_detail)
        {
            id_expand_bank_detail.toggle();
        }
    }

    @Override
    public void onClick(View v) {

        String str_url=null;

        SharedPreferences sharedPreferences= MyApplication.getAppContext().getSharedPreferences("Attach_Dialog",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Custom_Dialog__FTP_SERVER custom_dialog_ftp;

        switch(v.getId())
        {
            /*case R.id.id_btn_submit:
                // all sunmission code
                break;*/
            case R.id.id_social_facilitysave_btn:
                // all sunmission code

                Map<String,String> map_facility=new HashMap<>();
                map_facility.putAll(session_type());
                map_facility.put("jsonData",req_jsonarray.toString());
                Log.d("arraresp",""+map_facility);
                new Asynch_facility_req(new Asynch_facility_req.OBJ_Lister() {
                    @Override
                    public void on_lis_obj(JSONObject jsonObject, String str_key) {
                        Log.d("arraresp",jsonObject.toString());

                    }
                },SEAT_PROVIDER_FACILITY_UPDATED,map_facility,"Facility_map").execute();

                break;
            case R.id.id_cert_attach:
                // all sunmission code

                str_url=SEAT_PROVIDER_CERTIFICATES+"&crd_mobile="+session_type().get("crd_mobile")+"&token="+session_type().get("token")
                        +"&utype="+session_type().get("utype")+"&userid="+session_type().get("userid");//+//5Yy5cn3dc5&utype=1&userid=7";
                editor.putString("URL",str_url);
                editor.commit();
                custom_dialog_ftp=new Custom_Dialog__FTP_SERVER(Partner_Regisration_Frg.this.getContext(),str_url);
                custom_dialog_ftp.show();
                break;
            case R.id.id_brochure_attach:
                // all sunmission code

                str_url=SEAT_PROVIDER_BROCHURE+"&crd_mobile="+session_type().get("crd_mobile")+"&token="+session_type().get("token")
                        +"&utype="+session_type().get("utype")+"&userid="+session_type().get("userid");//+//5Yy5cn3dc5&utype=1&userid=7";
                editor.putString("URL",str_url);
                editor.commit();
                custom_dialog_ftp=new Custom_Dialog__FTP_SERVER(Partner_Regisration_Frg.this.getContext(),str_url);
                custom_dialog_ftp.show();
                break;
            case R.id.id_img_attach:
                // all sunmission code
             //   session_type();

                 str_url=SEAT_PROVIDER_IAMGE+"&crd_mobile="+session_type().get("crd_mobile")+"&token="+session_type().get("token")
                     +"&utype="+session_type().get("utype")+"&userid="+session_type().get("userid");//+//5Yy5cn3dc5&utype=1&userid=7";

                editor.putString("URL",str_url);
                editor.commit();

                custom_dialog_ftp=new Custom_Dialog__FTP_SERVER(Partner_Regisration_Frg.this.getContext(),str_url);
                custom_dialog_ftp.show();

                break;
            case R.id.id_add_save_btn:

                map_add.putAll(session_type());
                map_add.put("state",this.map.get("state_id"));
                map_add.put("city",this.map.get("city_id"));
                new Asynch_Obj(new Asynch_Obj.OBJ_Lister() {
                    @Override
                    public void on_lis_obj(JSONObject jsonObject, String str_key) {
                        Log.d("jsonObject",jsonObject.toString());

                        id_expand_addr.toggle();
                      //  progressDialog.cancel();
                        switch1.setChecked(false);
                    }
                }, UrlEndpoints.SEAT_PROVIDER_ADDRESS, map_add, "address").execute();

                break;
            case R.id.id_detail_save_btn:
                // all sunmission code
                map.putAll(session_type());
                progressDialog = new ProgressDialog(Partner_Regisration_Frg.this.getContext());
                progressDialog.setCancelable(true);
                progressDialog.show();
                progressDialog.setMessage(getString(R.string.loading));
                new Asynch_Agent_Form_JObject(Partner_Regisration_Frg.this, UrlEndpoints.SEAT_PROVIDER_DETAIL,map).execute();

                break;
            case R.id.id_social_save_btn:
                // all sunmission code
                map_social.putAll(session_type());
                progressDialog = new ProgressDialog(Partner_Regisration_Frg.this.getContext());
                progressDialog.setCancelable(true);
                progressDialog.show();
                progressDialog.setMessage(getString(R.string.loading));
                new Asynch_Obj(new Asynch_Obj.OBJ_Lister() {
                    @Override
                    public void on_lis_obj(JSONObject jsonObject, String str_key) {
                        Log.d("jsonObject",jsonObject.toString());

                        socail_layout1.toggle();
                        progressDialog.cancel();
                        switch_button1_socail.setChecked(false);
                    }
                }, UrlEndpoints.SEAT_PROVIDER__SOCIAL, map_social, "social").execute();

                break;

        }
    }

    @Override
    public void onLJsonLoaded(JSONObject jsonObject) {

    }
    @Override
    public void onLJsonLoaded_new(JSONObject jsonObject) {
        try {
        Log.d("jsononbject",jsonObject.toString());
          //  progressDialog.cancel();
        if(jsonObject==null)
            return;
        if(jsonObject.has("msg"))
        {
           /* Log.d("ndn",jsonObject.getString("msg"));
            jsonObject.getString("msg");
           */
                if(jsonObject.getInt("msg")==(0))
                {

                    progressDialog.cancel();
                    Toast.makeText(Partner_Regisration_Frg.this.getContext(), "Not Saved", Toast.LENGTH_SHORT).show();
                   // layout1.collapse();
                  //  switch1.setOnCheckedChangeListener(listener);

                }else if(jsonObject.getInt("msg")==(1)){
                    layout1.toggle();
                    progressDialog.cancel();
                    switch1.setChecked(false);
                    InputMethodManager imm = (InputMethodManager) Partner_Regisration_Frg.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Partner_Regisration_Frg.this.getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
        }
        } catch (JSONException e) {
            progressDialog.cancel();
            e.printStackTrace();
        }
        progressDialog.cancel();
    }

    @Override
    public void obn_obj_find(JSONObject jsonObject) {
        try {
        this.res_jsonObject=jsonObject;
        this.res_jsonArray=jsonObject.getJSONArray("data");

        Facility_Adapter mAdapter = new Facility_Adapter(Partner_Regisration_Frg.this.getContext(),jsonObject.getJSONArray("data"));
        GridLayoutManager horizontal_LayoutManager = new GridLayoutManager(getApplicationContext(),4, LinearLayoutManager.HORIZONTAL, false);
        id_recycler_view.setLayoutManager(horizontal_LayoutManager);
        id_recycler_view.setItemAnimator(new DefaultItemAnimator());
        id_recycler_view.setAdapter(mAdapter);
        } catch (JSONException e) {
           Log.d("hjkhkk",""+e.getMessage());
        }
    }
}
