package com.gt.active_education;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import adapter.Notif_Adapter;
import utilities.SmsReceiver;
import utilities.UpdateValues;

/**
 * Created by GT on 7/7/2017.
 */

public class OTP_Verification_Activity extends AppCompatActivity implements View.OnClickListener {
    private Button button_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        button_verify=(Button)findViewById(R.id.button_verify);
        button_verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_verify:
                startActivity(new Intent(getApplicationContext(),SignUp_Succesfull_Activity.class));

                break;
        }
    }
/*

    @Override
    public void messageReceived(String str_otp) {
*/

       /* bln_status=true;
        if(bln_status) {
            shrd_otp_prf = getSharedPreferences(UpdateValues.Otp_Prf, 0);
            final String str_otp_api_key = shrd_otp_prf.getString("otp_api_key", "");
            final String str_otp_session = shrd_otp_prf.getString("otp_session", "");
            final String str_otp_mbl = shrd_otp_prf.getString("otp_mbl", "");
            //Log.d("gfdser", str_otp + "  " + str_otp_api_key + "  " + str_otp_session + "   " + str_otp_mbl);
            char[] otp_array = str_otp.toCharArray();
            final String str_top = str_otp;
            //Log.d("edyotp", otp_array[0] + "  " + otp_array[1] + " " + otp_array[2] + " " + otp_array[3] + " " + otp_array[4] + " " + otp_array[5]);
            otp_one_edit_text.setText(str_otp);

            otp_one_edit_text.setText(String.valueOf(otp_array[0]));
            otp_two_edit_text.setText(String.valueOf(otp_array[1]));
            otp_three_edit_text.setText(String.valueOf(otp_array[2]));
            otp_four_edit_text.setText(String.valueOf(otp_array[3]));
            otp_fifth_edit_text.setText(String.valueOf(otp_array[4]));
            otp_six_edit_text.setText(String.valueOf(otp_array[5]));

            btn_otp_verify.setBackgroundColor(getResources().getColor(R.color.color_golden));
            btn_otp_verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (str_otp_api_key == null) {
                        otp_verification(str_otp_api_key, str_otp_session, str_otp_mbl, str_top);
                    } else {
                    otp_one_edit_text.getText().toString();  otp_two_edit_text.getText().toString();  otp_three_edit_text.getText().toString();
                    otp_four_edit_text.getText().toString();  otp_fifth_edit_text.getText().toString();  otp_six_edit_text.getText().toString();
                        String Str_otp_typing_manually = otp_one_edit_text.getText().toString();
                        + otp_two_edit_text.getText().toString() + otp_three_edit_text.getText().toString() +
                                otp_four_edit_text.getText().toString() + otp_fifth_edit_text.getText().toString() + otp_six_edit_text.getText().toString();
                        //Log.d("maunyally_otp", "" + Str_otp_typing_manually);
                        otp_verification(str_otp_api_key, str_otp_session, str_otp_mbl, Str_otp_typing_manually);
                    }
                }
            });
        }*/
        //otp_verification(str_otp_api_key,str_otp_session,str_otp_mbl,str_otp);
  //  }
}
