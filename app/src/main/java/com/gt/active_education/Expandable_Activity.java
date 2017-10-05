package com.gt.active_education;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.gt.active_education.R;

public class Expandable_Activity extends AppCompatActivity {


    SwitchCompat switch1, switch2;
    ExpandableRelativeLayout layout1, layout2;
    private CompoundButton.OnCheckedChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toggle(compoundButton);
            }
        };
        switch1 = (SwitchCompat) findViewById(R.id.switch_button1);
        switch1.setOnCheckedChangeListener(listener);

        layout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);

        switch2 = (SwitchCompat) findViewById(R.id.switch_button2);
        switch2.setOnCheckedChangeListener(listener);

        layout2 = (ExpandableRelativeLayout) findViewById(R.id.id_expand_addr);

    }

    private void toggle(View v) {
        if(v.getId() == R.id.switch_button1)
        {
            layout1.toggle();
        }
        else if(v.getId() == R.id.switch_button2)
        {
            layout2.toggle();
        }
    }

}
