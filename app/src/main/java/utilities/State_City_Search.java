package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.Simple_Adapter;
import callbacks.Call_Dilaog_Listener;
import callbacks.Forgot_Close_Listener;

import static utilities.App_Static_Method.show_load_progress;

/**
 * Created by GT on 9/27/2017.
 */

public class State_City_Search extends Dialog implements View.OnClickListener,Call_Dilaog_Listener {

    private RecyclerView mRecyclerView;
    public EditText search;
    private List<String> list = new ArrayList<String>();
    Simple_Adapter mAdapter;
    private ImageView id_cancle;
    //private Fragment context;
    private String str_url;
    private Dialog_Spinner_Listener dialogSpinnerListener;
    private List<String> server_list;
    private ProgressDialog progressDialog;
    private Context mcontext;


   /* public State_City_Search(Dialog_Spinner_Listener dialogSpinnerListener,Fragment context, String str_url) {
        super(context.getContext());
        this.dialogSpinnerListener=dialogSpinnerListener;
        this.context=context;
        this.str_url=str_url;
    }*/
    public State_City_Search(Dialog_Spinner_Listener dialogSpinnerListener, Context context, String str_url) {
        super(context);
        this.dialogSpinnerListener=dialogSpinnerListener;
        this.mcontext=context;
        this.str_url=str_url;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_search_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // progressDialog =show_load_progress(State_City_Search.this.getOwnerActivity(),MyApplication.getAppContext().getString(R.string.Loading));
        id_cancle=(ImageView)findViewById(R.id.id_cancle);id_cancle.setOnClickListener(this);
        search = (EditText) findViewById( R.id.search);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

      //  countryList();  // in this method, Create a list of items.

        // call the adapter with argument list of items and context.
      /*  mAdapter = new Simple_Adapter(list,this);
        mRecyclerView.setAdapter(mAdapter);*/
        set_state_list(str_url);
        addTextListener(server_list);

    }

   /* public void countryList(){

        list.add("Afghanistan");
        list.add("Albania");
        list.add("Algeria");
        list.add("Bangladesh");
        list.add("Belarus");
        list.add("Canada");
        list.add("Cape Verde");
        list.add("Central African Republic");
        list.add("Denmark");
        list.add("Dominican Republic");
        list.add("Egypt");
        list.add("France");
        list.add("Germany");
        list.add("Hong Kong");
        list.add("India");
        list.add("Iceland");

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_cancle:
                if(search.getText().toString()!=null)
               // ((Call_Dilaog_Listener)context).on_id_listener(search.getText().toString());
                    dialogSpinnerListener.on_listdata(search.getText().toString());
                else
                    Toast.makeText(mcontext, "Please Select Any Option !!!!", Toast.LENGTH_LONG).show();
                break;

        }
    }


    public void addTextListener(List<String> server_list){

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                Log.d("editabale","g   "+s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("dcccdc","djhj   "+s);
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                Log.d("editabadccle","djhj   "+query);
                if(query!=null) {
                    query = query.toString().toLowerCase();

                    final List<String> filteredList = new ArrayList<>();

                    for (int i = 0; i < State_City_Search.this.server_list.size(); i++) {

                        final String text = State_City_Search.this.server_list.get(i).toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(State_City_Search.this.server_list.get(i));
                        }
                    }

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(State_City_Search.this.getContext()));
                    mAdapter = new Simple_Adapter(filteredList, State_City_Search.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();  // data set changed
                }else {

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(State_City_Search.this.getContext()));
                    mAdapter = new Simple_Adapter(State_City_Search.this.server_list, State_City_Search.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialogSpinnerListener.on_listdata("");

    }

    @Override
    public void on_id_listener(String str_id) {
        search.setText(str_id);
        dialogSpinnerListener.on_listdata(str_id);
       // dialogSpinnerListener.on_listdata(str_id);
    }

    private void set_state_list(String url) {
        Log.d("striurl",url) ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("city",""+str_response);
                            ArrayList<String> arrayList=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has("state"))
                            {
                                JSONArray array = response.getJSONArray("state");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                 /*   sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));*/
                                    //  Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(json.getString("name"));
                                }
                                on_list_data_new(arrayList);
                                 // data set changed
                            }
                            else if(response.has("findCity"))
                            {
                                JSONArray array = response.getJSONArray("findCity");

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                 /*   sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));*/
                                    //  Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(json.getString("name"));
                                }
                                on_list_data_new(arrayList);
                                // data set changed
                            }
                            else if(response.has("msg"))
                            {
                                App_Static_Method.city_not_get(State_City_Search.this.getOwnerActivity()," Please Select Correct State !!!");
                            }
                        } catch (JSONException e) {
                            //pd.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  pd.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Username & Password is incorrect", Toast.LENGTH_LONG).show();
                     //   progressDialog.cancel();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(stringRequest);

    }


    public void on_list_data_new(List<String> s) {

        server_list=s;
    //    progressDialog.cancel();
        if(!server_list.isEmpty()) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(State_City_Search.this.getContext()));
            mAdapter = new Simple_Adapter(server_list, State_City_Search.this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    public interface Dialog_Spinner_Listener {

        public void on_listdata(String s);

    }

}
