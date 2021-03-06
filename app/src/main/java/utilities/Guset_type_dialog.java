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

import com.gt.active_education.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.Simple_adb2;
import callbacks.Call_newDialog_Listener;

/**
 * Created by GT on 9/27/2017.
 */

public class Guset_type_dialog extends Dialog implements View.OnClickListener, Call_newDialog_Listener {

    private RecyclerView mRecyclerView;
    public EditText search;
    private List<String> list = new ArrayList<String>();
    Simple_adb2 mAdapter;
    private ImageView id_cancle;
    private Context context;
    private String str_lg_type[];
    private Dialog_Spinner_Listener dialogSpinnerListener;
    private List<String> server_list;
    private ProgressDialog progressDialog;


    public Guset_type_dialog(Dialog_Spinner_Listener dialogSpinnerListener, Context context, String[] str_lg_type) {
        super(context);
        this.dialogSpinnerListener=dialogSpinnerListener;
        this.context=context;
        this.str_lg_type =str_lg_type;
        this.server_list= Arrays.asList(str_lg_type);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_search_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      //  progressDialog =show_load_progress(Custom_List_Dialog.this.getContext(),MyApplication.getAppContext().getString(R.string.Loading));
        id_cancle=(ImageView)findViewById(R.id.id_cancle);id_cancle.setOnClickListener(this);
        search = (EditText) findViewById( R.id.search);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

      //  countryList();  // in this method, Create a list of items.

        // call the adapter with argument list of items and context.
      /*  mAdapter = new Simple_adb2(list,this);
        mRecyclerView.setAdapter(mAdapter);*/
        //set_state_list(str_lg_type);
        on_list_data_new(server_list);
        addTextListener(server_list);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_cancle:
                if(search.getText().toString()!=null)
               // ((Call_Dilaog_Listener)context).on_id_listener(search.getText().toString());
                    dialogSpinnerListener.on_listdata(search.getText().toString());
                else
                    Toast.makeText(context, "Please Select Any Option !!!!", Toast.LENGTH_LONG).show();
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

                    for (int i = 0; i < Guset_type_dialog.this.server_list.size(); i++) {

                        final String text = Guset_type_dialog.this.server_list.get(i).toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(Guset_type_dialog.this.server_list.get(i));
                        }
                    }

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(Guset_type_dialog.this.getContext()));
                    mAdapter = new Simple_adb2(filteredList, Guset_type_dialog.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();  // data set changed
                }else {

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(Guset_type_dialog.this.getContext()));
                    mAdapter = new Simple_adb2(Guset_type_dialog.this.server_list, Guset_type_dialog.this);
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
    }



    public void on_list_data_new(List<String> s) {

        server_list=s;
   //    progressDialog.cancel();
        if(!server_list.isEmpty()) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(Guset_type_dialog.this.getContext()));
            mAdapter = new Simple_adb2(server_list, Guset_type_dialog.this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    public interface Dialog_Spinner_Listener {

        public void on_listdata(String s);

    }

}
