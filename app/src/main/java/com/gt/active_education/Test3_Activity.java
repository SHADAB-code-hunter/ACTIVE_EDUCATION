package com.gt.active_education;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import utilities.BatteryProgressView;

/**
 * Created by GT on 10/3/2017.
 */

public class Test3_Activity  extends AppCompatActivity {

    Button mbtn1,mbtn2,mbtn3;
    private EditText id_edt_a;
    private EditText id_edt_b;
    private EditText id_edt_c;
    private Map<String,String> map=new HashMap<>();
    private Button id_btn;
    private BatteryProgressView progress;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       /* id_btn=(Button)findViewById(R.id.id_btn) ;
        id_edt_a=(EditText)findViewById(R.id.id_edt_a);
        id_edt_b=(EditText)findViewById(R.id.id_edt_b);
        id_edt_c=(EditText)findViewById(R.id.id_edt_c);
        id_edt_c.getHint();

        setTextWatcher(id_edt_a); setTextWatcher(id_edt_b); setTextWatcher(id_edt_c);
        id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Map.Entry<String,String> entry:map.entrySet()){
                    String key=entry.getKey();
                    String value=entry.getValue();

                    StringBuilder stringBuilder=new StringBuilder();

                    stringBuilder.append("key  "+key);
                    stringBuilder.append("value  "+value);
                    Toast.makeText(Test3_Activity.this, stringBuilder, Toast.LENGTH_SHORT).show();

                }
            }
        });*/
       /* progress= (BatteryProgressView) findViewById(R.id.progress);
        progress.setProgress(66, circle_progress_color1);
        handler=new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setProgress(63, circle_progress_color1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.setProgress(77, circle_progress_color1);
                    }
                },1000);
            }
        },2000);*/
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

                map.put(id_edt_a.getHint().toString(),query.toString());
              //  Toast.makeText(Test3_Activity.this,   id_edt_a.getHint()+"    "+query, Toast.LENGTH_SHORT).show();

            }
        });


    }
}
