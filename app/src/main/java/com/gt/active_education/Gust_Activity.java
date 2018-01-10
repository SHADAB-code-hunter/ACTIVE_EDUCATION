package com.gt.active_education;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import callbacks.FInsi_Listener;
import utilities.Gust_Regis_Fragment;

import static utilities.App_Static_Method.get_session_type;

/**
 * Created by GT on 11/25/2017.
 */

public class Gust_Activity extends AppCompatActivity implements FInsi_Listener {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.guset_activity);
        Log.d("checksession",""+get_session_type());
        if(get_session_type()!=null)
        {
            get_progress();
        }else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.id_frm, new Gust_Regis_Fragment()).commit();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String message=data.getStringExtra("MESSAGE");
          if(message.equalsIgnoreCase("OPEN"))
          {
              if(progressDialog!=null)
                  progressDialog.cancel();
          }
        }
    }

    @Override
    public void onCall() {
       get_progress();
    }

    private void get_progress() {
        progressDialog = new ProgressDialog(Gust_Activity.this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.Loading));
        startActivity(new Intent(getApplication(),DashBoard_Activity.class));
    }
}
