package fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gt.active_education.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import callbacks.JOBJ_Listener;
import task.Asynch_Agent_Form_JObject;
import utilities.App_Static_Method;
import utilities.UrlEndpoints;

import static utilities.App_Static_Method.progressDialog;

/**
 * Created by GT on 10/2/2017.
 */

public class Partner_Regisration_Frg extends Fragment implements View.OnClickListener , JOBJ_Listener{

    SwitchCompat switch1, switch2,switch_button1_socail,switch_btn_clg_img;
    ExpandableRelativeLayout layout1, layout2,socail_layout1,id_expand_attach_images;
    private CompoundButton.OnCheckedChangeListener listener;
    private ExpandableRelativeLayout id_expand_attach_brochre;
    private SwitchCompat switch_btn_clg_brochure;
    private Button id_btn_submit;
    private ExpandableRelativeLayout id_expand_attach_certificate;
    private SwitchCompat switch_btn_clg_crtficate;
    private ExpandableRelativeLayout id_expand_bank_detail;
    private SwitchCompat switch_btn_clg_bank_detail;
    private SwitchCompat switch_button2_address;
    private ExpandableRelativeLayout id_expand_addr;
    private EditText id_clg_name,id_short_clg_name,id_phone,id_edt_land_number,id_email,id_website,id_est_year,id_clg_about;
    private String[] str_partner_hint_key;
    private String[] str_partner_server_key;
    private Map<String, String> map=new HashMap<>();
    private Button id_detail_save_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frg_partner_registeration, container, false);
        listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggle(compoundButton);
            }
        };

        set_clg_detail(rootView);


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
        id_btn_submit=(Button)rootView.findViewById(R.id.id_btn_submit);id_btn_submit.setOnClickListener(this);

        return rootView;

    }

    private void set_clg_social(View rootView) {
        id_clg_name=(EditText)rootView.findViewById(R.id.id_clg_name);
        id_short_clg_name=(EditText)rootView.findViewById(R.id.id_short_clg_name);
        id_phone=(EditText)rootView.findViewById(R.id.id_phone);
        id_edt_land_number=(EditText)rootView.findViewById(R.id.id_edt_land_number);
        id_email=(EditText)rootView.findViewById(R.id.id_email);
        id_website=(EditText)rootView.findViewById(R.id.id_website);
        id_est_year=(EditText)rootView.findViewById(R.id.id_est_year);
        id_clg_about=(EditText)rootView.findViewById(R.id.id_clg_about);

        id_detail_save_btn=(Button)rootView.findViewById(R.id.id_detail_save_btn);id_detail_save_btn.setOnClickListener(this);

        Log.d("hintstring", id_clg_name.getHint().toString());
         str_partner_server_key=new String[]{"name" ,"shortName","phone","landline","email","website","established","information"};
         str_partner_hint_key=new String[]{"Enter College Name" ,"Enter College Short Name","Enter Mobile Number"," Enter LandLine Number","Enter E-Mail"," Enter Website","Enter Established Year",
                "Type your Portfolio Description"};

        setTextWatcher(id_clg_name);
        setTextWatcher(id_short_clg_name);
        setTextWatcher(id_phone);
        setTextWatcher(id_edt_land_number);
        setTextWatcher(id_email);
        setTextWatcher(id_website);
        setTextWatcher(id_est_year);
        setTextWatcher(id_clg_about);

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

        id_detail_save_btn=(Button)rootView.findViewById(R.id.id_detail_save_btn);id_detail_save_btn.setOnClickListener(this);

        Log.d("hintstring", id_clg_name.getHint().toString());
         str_partner_server_key=new String[]{"name" ,"shortName","phone","landline","email","website","established","information"};
         str_partner_hint_key=new String[]{"Enter College Name" ,"Enter College Short Name","Enter Mobile Number"," Enter LandLine Number","Enter E-Mail"," Enter Website","Enter Established Year",
                "Type your Portfolio Description"};

        setTextWatcher(id_clg_name);
        setTextWatcher(id_short_clg_name);
        setTextWatcher(id_phone);
        setTextWatcher(id_edt_land_number);
        setTextWatcher(id_email);
        setTextWatcher(id_website);
        setTextWatcher(id_est_year);
        setTextWatcher(id_clg_about);

    }
    private void setTextWatcher(final EditText id_edt_a) {

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
        switch(v.getId())
        {
            case R.id.id_btn_submit:
                // all sunmission code
                break;
            case R.id.id_detail_save_btn:
                // all sunmission code
                map.putAll(App_Static_Method.get_for_submit_partener_detail());
                progressDialog = new ProgressDialog(Partner_Regisration_Frg.this.getContext());
                progressDialog.setCancelable(true);
                progressDialog.show();
                progressDialog.setMessage(getString(R.string.loading));
                new Asynch_Agent_Form_JObject(Partner_Regisration_Frg.this, UrlEndpoints.SEAT_PROVIDER_DETAIL,map).execute();

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
}
