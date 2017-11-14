package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import callbacks.Spinner_Date_Listener;
import pojo.State_MODEL;

/**
 * Created by GT on 5/27/2017.
 */

public class Custom_DialogClass extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    private Spinner date_spinner;
    private String str_date=null;
    private String str_choose_date;
    private  ArrayList<String> arrayList;
    private Spinner_Date_Listener spinnerDateListener;
    private View find_v;


    public Custom_DialogClass(Activity a, View find_v) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.find_v=find_v;
        this.spinnerDateListener=(Spinner_Date_Listener) a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom__new_dialog);
        date_spinner=(Spinner)findViewById(R.id.date_spinner);
    //  Theme_Change.Theme_Change_Prefrense((FrameLayout)findViewById(R.id.id_frm_dialog));
        getState_list(date_spinner);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                str_choose_date=arrayList.get(position);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                //  getdate and send to main anctivity
                Log.d("hvchvccjcc","vcbvcbvc");
                spinnerDateListener.onSpinner_Date(str_choose_date,find_v);
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
    private void getState_list(final Spinner date_spinner) {

        // Log.d("mobils",""+mobile);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, UrlEndpoints.URL_DATE,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseObj) {
                Log.d("dfgrfr", responseObj.toString());
                 arrayList=new ArrayList<>();
                try {
                    JSONArray array=responseObj.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++)
                    {
                        JSONObject   json = array.getJSONObject(i);
                        State_MODEL state_model = new State_MODEL();
                        str_date=json.getString("date");
                        arrayList.add(str_date);
                    }

                    ArrayAdapter<String> karant_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,arrayList );
                    date_spinner.setAdapter(karant_adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley_error_otp_verify", "Error: " + error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(req);
    }


}