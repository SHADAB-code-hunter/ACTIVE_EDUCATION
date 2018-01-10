package utilities;

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
 * Created by GT on 11/8/2017.
 */

public class Attach_Dialog extends Dialog implements View.OnClickListener {

    private LinearLayout id_linear_HS, id_linear_intermediate, id_linear_graduate, id_linear_inter_graduate;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences,shrdf;

    public Attach_Dialog(Context activity) {
        super(activity);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.attach_doc_layout);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        id_linear_HS=(LinearLayout)findViewById(R.id.id_linear_HS);
        id_linear_intermediate=(LinearLayout)findViewById(R.id.id_linear_intermediate);
        id_linear_graduate=(LinearLayout)findViewById(R.id.id_linear_graduate);
        id_linear_inter_graduate=(LinearLayout)findViewById(R.id.id_linear_graduate);

        id_linear_HS.setOnClickListener(this);
        id_linear_intermediate.setOnClickListener(this);
        id_linear_graduate.setOnClickListener(this);
        id_linear_inter_graduate.setOnClickListener(this);

        SharedPreferences sharedPreferences=null;
        SharedPreferences shprf_ = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_TYPE,0);
        switch (shprf_.getString("type", "na"))
        {
            case "agent":
                sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_PARTNER_Prefrence, 0);
               // SharedPreferences.Editor editor1 = sharedPreferences.edit();
               /* editor1.clear().commit();
                shprf_.edit().clear().commit();*/
                break;
            case "user":
                sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
               // SharedPreferences.Editor editor2 = sharedPreferences.edit();
               /* editor2.clear().commit();
                shprf_.edit().clear().commit();*/
                break;
            case "seater":
                sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_Seater_Pref, 0);
               // SharedPreferences.Editor editor3 = sharedPreferences.edit();
               /* editor3.clear().commit();
                shprf_.edit().clear().commit();*/
                break;
        }

        String str_prf_mobile=sharedPreferences.getString("mobile","NA");
        sharedPreferences=MyApplication.getAppContext().getSharedPreferences("Dialog_Callback",0);
        editor=sharedPreferences.edit();
        editor.putString("mobile",str_prf_mobile);
        editor.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_linear_HS:

                editor.putString("Position_School","HS");
                editor.commit();
                send_attach_dialog("HS");// open dialog
                break;
            case R.id.id_linear_intermediate:
                editor.putString("Position_School","INT");
                editor.commit();
                send_attach_dialog("INT");
                break;
            case R.id.id_linear_graduate:
                editor.putString("Position_School","GRD");
                editor.commit();
                send_attach_dialog("GRD");
                break;
            case R.id.id_linear_inter_graduate:
                editor.putString("Position_School","PGRD");
                editor.commit();
                send_attach_dialog("PGRD");
                break;
        }
    }

    private void send_attach_dialog(String str) {

        Custom_Dialog__FTP_SERVER Custom_Dialog__FTP_SERVER=new Custom_Dialog__FTP_SERVER(Attach_Dialog.this.getContext(),str);
        Custom_Dialog__FTP_SERVER.show();

    }
}
