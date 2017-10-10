package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gt.active_education.R;

/**
 * Created by GT on 10/2/2017.
 */

public class Partner_Regisration_Frg extends Fragment implements View.OnClickListener {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frg_partner_registeration, container, false);
        listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggle(compoundButton);
            }
        };

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

        }
    }
}
