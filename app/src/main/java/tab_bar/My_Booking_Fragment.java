package tab_bar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.gt.active_education.Book_desc_Activity;
import com.gt.active_education.DashBoard_Activity;
import com.gt.active_education.Filter_Activity;
import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import adapter.Adapter_Book;
import adapter.MoviesAdapter;
import callbacks.Agent_deal_Listener;
import callbacks.Avail_Course_Listener;
import callbacks.JOBJ_Listener;
import pojo.Agent_Deal_Pojo;
import task.Asynch_Book_Responce;
import task.TaskLoad_List;
import pojo.Cat_Model;
import utilities.App_Static_Method;
import utilities.Common_Pojo;
import utilities.MyApplication;
import utilities.RecyclerTouchListener;
import utilities.UpdateValues;
import utilities.UrlEndpoints;

import static com.facebook.FacebookSdk.getApplicationContext;
import static utilities.App_Static_Method.after_guestregisyter;
import static utilities.App_Static_Method.get_session_type;
import static utilities.App_Static_Method.show_load_progress;
import static utilities.App_Static_Method.toMap;


/**
 * Created by GT on 8/5/2017.
 */

public class My_Booking_Fragment extends  Fragment implements Agent_deal_Listener, JOBJ_Listener {
    Adapter_Book adapter_book;
    Picasso picasso;
    RecyclerView recyclerView;
    private JSONArray jsonArray;
    private String strulr;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frg_agent_book, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        //rootView.findViewById(R.id.id_heading).setVisibility(View.GONE);

        String strurl="http://activeeduindia.com/new_admin/webservices/getGuestBookingList.php";
        try {
            new Asynch_Book_Responce(My_Booking_Fragment.this, strurl,toMap(new JSONObject(after_guestregisyter()))).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        picasso = Picasso.with(getContext());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Intent i=new Intent(getApplicationContext(),Book_desc_Activity.class);
                        try {
                            i.putExtra("book_obj",jsonArray.getJSONObject(position).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(i);

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void on_deal_call(List<Agent_Deal_Pojo> listMovies, String status_call) {
        // Log.d("kist",""+listMovies.toString());
        //  if (listMovies.isEmpty()) {
      /*  adapter_book = new Adapter_Book(listMovies, picasso, (Activity) getContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager((Activity) getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/
       /* }else {
            Toast.makeText(getContext(), "You Have No Deal", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onLJsonLoaded(JSONObject jsonObject) {

    }

    @Override
    public void onLJsonLoaded_new(JSONObject jsonObject) {
        Log.d("objwct_responce",""+jsonObject.toString());
//
        // mAdapter = new Agent_Deal_Adapter(jsonObject, picasso, (Activity) getContext());
        try {
            if(jsonObject.has("data")) {
                jsonArray=jsonObject.getJSONArray("data");
                adapter_book = new Adapter_Book(jsonArray, picasso, (Activity) getContext());
                LinearLayoutManager mLayoutManager = new LinearLayoutManager((Activity) getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter_book);
            }
        } catch (Exception e) {
           Log.d("jkjkjk",""+e.getMessage());
        }
    }
}
