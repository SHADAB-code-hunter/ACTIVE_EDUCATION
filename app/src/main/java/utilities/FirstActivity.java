package utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gt.active_education.R;

public class FirstActivity extends Activity {

    public static final int DIVIDEND = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firsh_activity);
        TextView dividend = (TextView) findViewById(R.id.txtDividend);
        dividend.setText(String.valueOf(DIVIDEND));
        TextView name = (TextView) findViewById(R.id.txtName);
        name.setText(R.string.act1);
    }

    public void onCrashClick(View v) {
        EditText editNumber = (EditText) findViewById(R.id.edtNumber);
        String value = editNumber.getText().toString();
        double result = DIVIDEND / Double.parseDouble(value);
        TextView txtResult = (TextView) findViewById(R.id.txtResult);
        txtResult.setText(String.valueOf(result));
    }

    public void onBackgroundCrashClick(View v) {
        Log.d("catch","onBackgroundCrashClick");
        new Thread() {
            public void run() {
                Log.d("catch","onBackgroundCrashClick_run");
                throw new NullPointerException("NPE generated in the background");
            }
        }.start();
    }

    public void onNextClick(View v) {
        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i);
        Log.d("catch","onNextClick");

    }

}