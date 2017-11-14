package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;


import com.gt.active_education.R;

import java.util.ArrayList;

import callbacks.Spinner_Date_Listener;

/**
 * Created by GT on 6/9/2017.
 */

public class Custom_Term_Cond_Dialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    private Spinner date_spinner;
    private String str_date = null;

    private ArrayList<String> arrayList;
    private Spinner_Date_Listener spinnerDateListener;
    private View find_v;


    public Custom_Term_Cond_Dialog(Activity a, View find_v) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.find_v = find_v;
        this.spinnerDateListener = (Spinner_Date_Listener) a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_term_cond_dialog);
       // Theme_Change.Toolbar_Change_Prefrense(findViewById(R.id.id_linear));
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                //  getdate and send to main anctivity
                Log.d("hvchvccjcc", "vcbvcbvc");
                spinnerDateListener.onSpinner_Date("", find_v);
                dismiss();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}


