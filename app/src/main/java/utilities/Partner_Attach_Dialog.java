package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.gt.active_education.R;

/**
 * Created by GT on 11/17/2017.
 */

public class Partner_Attach_Dialog extends Dialog implements View.OnClickListener {

    private LinearLayout id_linear_HS, id_linear_intermediate, id_linear_graduate, id_linear_inter_graduate;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences,shrdf;
    private Activity activity;

    public Partner_Attach_Dialog(Activity activity) {
        super(activity);
        this.activity=activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.attach_doc_layout);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_linear_HS:

                editor.putString("Position_School","HS");
                editor.commit();
                send_Partner_Attach_Dialog("HS");// open dialog
                break;
            case R.id.id_linear_intermediate:
                editor.putString("Position_School","INT");
                editor.commit();
                send_Partner_Attach_Dialog("INT");
                break;
            case R.id.id_linear_graduate:
                editor.putString("Position_School","GRD");
                editor.commit();
                send_Partner_Attach_Dialog("GRD");
                break;
            case R.id.id_linear_inter_graduate:
                editor.putString("Position_School","PGRD");
                editor.commit();
                send_Partner_Attach_Dialog("PGRD");
                break;
        }
    }

    private void send_Partner_Attach_Dialog(String str) {



    }
}
