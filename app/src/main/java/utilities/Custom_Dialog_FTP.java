package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gt.active_education.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.Simple_adb2;
import callbacks.Attach_Listener;
import callbacks.Call_newDialog_Listener;

/**
 * Created by GT on 11/4/2017.
 */

public class Custom_Dialog_FTP extends Dialog implements View.OnClickListener , Attach_Listener {

    public EditText search;
    private Activity activity;

    public Custom_Dialog_FTP(Activity activity) {
        super(activity);
        this.activity=activity;

    }


  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment, container, false);

        // Do something

        return rootView;
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_cancle:

                break;

        }
    }

    @Override
    public void onAttach() {
        Log.d("hdjhjd","ddll");
    }
}
