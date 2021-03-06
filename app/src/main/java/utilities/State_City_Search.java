package utilities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gt.active_education.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.Simple_Adapter;
import callbacks.Call_Dilaog_Listener;

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
    private List<String> server_list,arrayList_id;
    private ProgressDialog progressDialog;
    private Context mcontext;
    private String str_key_array;

   /* public State_City_Search(Dialog_Spinner_Listener dialogSpinnerListener,Fragment context, String str_url) {
        super(context.getContext());
        this.dialogSpinnerListener=dialogSpinnerListener;
        this.context=context;
        this.str_url=str_url;
    }*/
    public State_City_Search(Dialog_Spinner_Listener dialogSpinnerListener, Context context, String str_url,String str_key_array) {
        super(context);
        this.dialogSpinnerListener=dialogSpinnerListener;
        this.mcontext=context;
        this.str_url=str_url;
        this.str_key_array=str_key_array;

        Log.d("str_key",str_key_array);
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
        set_state_list(str_url);
        addTextListener(server_list);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_cancle:
                if(search.getText().toString()!=null)
               // ((Call_Dilaog_Listener)context).on_id_listener(search.getText().toString());
                    dialogSpinnerListener.on_listdata("na","na");
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
                    final List<String> filteredList_id = new ArrayList<>();

                    for (int i = 0; i < State_City_Search.this.server_list.size(); i++) {

                        final String text = State_City_Search.this.server_list.get(i).toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(State_City_Search.this.server_list.get(i));
                            filteredList_id.add(State_City_Search.this.arrayList_id.get(i));
                        }
                    }

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(State_City_Search.this.getContext()));
                    mAdapter = new Simple_Adapter(filteredList, arrayList_id, State_City_Search.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();  // data set changed
                }else {

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(State_City_Search.this.getContext()));
                    mAdapter = new Simple_Adapter(State_City_Search.this.server_list, arrayList_id, State_City_Search.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialogSpinnerListener.on_listdata("na","na");

    }

    @Override
    public void on_id_listener(String s, String str_id) {
        search.setText(s);
        dialogSpinnerListener.on_listdata(s,str_id);
       // dialogSpinnerListener.on_listdata(str_id);
    }

    private void set_state_list(String url) {
        Log.d("striurl",url) ;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str_response) {
                        try{
                            Log.d("responce_",""+str_response);
                            ArrayList<String> arrayList=new ArrayList<>();
                            ArrayList<String> arrayList_id=new ArrayList<>();
                            JSONObject response = new JSONObject(str_response);
                            if(response.has(str_key_array))
                            {
                                JSONArray array = response.getJSONArray(str_key_array);

                                for (int i = 0; i < array.length(); i++)
                                {
                                    Common_Pojo sendDateModel = new Common_Pojo();
                                    JSONObject json = array.getJSONObject(i);
                                 /*   sendDateModel.setId(json.getString("id"));
                                    sendDateModel.setName(json.getString("name"));*/
                                    //  Log.d("banner_imd",""+json.getString("image_name"));
                                    arrayList.add(json.getString("name"));
                                    arrayList_id.add(json.getString("id"));
                                    Log.d("nndknd",json.getString("id"));
                                }
                                on_list_data_new(arrayList,arrayList_id);
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
                                    arrayList_id.add(json.getString("id"));
                                }
                                on_list_data_new(arrayList, arrayList_id);
                                // data set changed
                            }
                            else if(response.has("msg"))
                            {
                               // App_Static_Method.city_not_get(State_City_Search.this.getOwnerActivity()," Please Select Correct State !!!");
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


    public void on_list_data_new(List<String> s, List<String> arrayList_id) {

        Log.d("stringckeck",""+s.toString()+"  "+arrayList_id.toString());
        server_list=s;
        State_City_Search.this.arrayList_id=arrayList_id;
    //    progressDialog.cancel();
        if(!server_list.isEmpty()) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(State_City_Search.this.getContext()));
            mAdapter = new Simple_Adapter(server_list,arrayList_id, State_City_Search.this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    public interface Dialog_Spinner_Listener {

        public void on_listdata(String s,String sid);

    }

}
