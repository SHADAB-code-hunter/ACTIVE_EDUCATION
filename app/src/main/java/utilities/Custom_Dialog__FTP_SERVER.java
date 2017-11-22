package utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import com.gt.active_education.R;
import callbacks.Attach_Listener;

/**
 * Created by GT on 11/17/2017.
 */

public class Custom_Dialog__FTP_SERVER extends Dialog implements View.OnClickListener , Attach_Listener {

    public EditText search;
    private Context context;
    private Fragment id_ftp_attach;
    private String titleStepNum;

    public Custom_Dialog__FTP_SERVER(Context context, String titleStepNum) {
        super(context);
        this.context=context;
        this.titleStepNum=titleStepNum;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_ftp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_cancle:

                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onAttach() {
        Log.d("hdjhjd","ddll");
    }
}
