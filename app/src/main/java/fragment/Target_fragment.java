package fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gt.active_education.R;
import com.gt.active_education.Target_Circle_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import callbacks.JOBJ_Listener;
import task.Asynch_JObject;
import utilities.App_Static_Method;
import utilities.BatteryProgressView;
import utilities.BatteryProgressView2;
import utilities.BatteryProgressView3;
import utilities.UrlEndpoints;


/**
 * Created by GT on 10/10/2017.
 */

public class Target_fragment extends Fragment  implements JOBJ_Listener{
    private BatteryProgressView progress1;
    private BatteryProgressView2 progress2;
    private BatteryProgressView3 progress3;
    private TextView id_total_no,id_fill_no,id_remain_no;
    private Target_Circle_Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity=(Target_Circle_Activity)context;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frg_partner_target, container, false);
        //{"data":[{"mobile":"9166833551","token":"S6dqKXf8Zt","image":null,"uname":"demo","email":"sb@ss.com","clg_id":"dps","utype":"1","type":"user"}]}
        id_total_no=(TextView)rootView.findViewById(R.id.id_total_no);
        id_fill_no=(TextView)rootView.findViewById(R.id.id_fill_no);
        id_remain_no=(TextView)rootView.findViewById(R.id.id_remain_no);
        progress1= (BatteryProgressView)rootView. findViewById(R.id.progress1);
        progress2= (BatteryProgressView2)rootView. findViewById(R.id.progress2);
        progress3= (BatteryProgressView3)rootView. findViewById(R.id.progress3);

        new Asynch_JObject(Target_fragment.this, UrlEndpoints.SEAT_PROVIDER_TARGET, App_Static_Method.get_full_session_data()).execute();

       /* set_progress(progress1,R.color.circle_progress_color1);
        set_progress(progress2,R.color.circle_progress_color2);
        set_progress(progress3,R.color.circle_progress_color3);*/
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onLJsonLoaded(JSONObject jsonObject) {

        String str_pr_1="100";
        String str_pr_2="0";
        String str_pr_3="0";
        Log.d("targfret",""+jsonObject.toString());
// {"data":[{"total":"100","remaining":"50","filled":50,"total_per":100,"remaining_per":50,"filled_per":50}]}
        try {
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            JSONObject jsonObject1=jsonArray.getJSONObject(0);
            str_pr_1=jsonObject1.getString("total_per");
            str_pr_2=jsonObject1.getString("filled_per");
            str_pr_3=jsonObject1.getString("remaining_per");
            id_total_no.setText(jsonObject1.getString("total"));
            id_fill_no.setText(jsonObject1.getString("filled"));
            id_remain_no.setText(jsonObject1.getString("remaining"));
         //   activity.on_frg_act_linking();

        } catch (JSONException e) {
           Log.d("error_parsing",e.toString());
        }

        final Handler handler1;
        final Handler handler2;
        final Handler handler3;

        final float per1=Float.parseFloat(str_pr_1);
        final float per2= Float.parseFloat(str_pr_2);
        final float per3= Float.parseFloat(str_pr_3);

        Log.d("percentage",per1+"  "+per2+" "+per3);
        progress1.setProgress(Math.round(per1),R.color.circle_progress_color1);//progress.setProgressColor(circle_progress_color1);
        handler1=new android.os.Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress1.setProgress(Math.round(per1),R.color.circle_progress_color1);
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress1.setProgress(100, R.color.circle_progress_color1);

                    }
                },1000);
            }
        },2000);

        progress2.setProgress(Math.round(per2),R.color.circle_progress_color2);//progress.setProgressColor(circle_progress_color1);
        handler2=new android.os.Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress2.setProgress(Math.round(per2),R.color.circle_progress_color2);
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress2.setProgress(Math.round(per2), R.color.circle_progress_color2);

                    }
                },1000);
            }
        },2000);

        progress3.setProgress(Math.round(per3),R.color.circle_progress_color3);//progress.setProgressColor(circle_progress_color1);
        handler3=new android.os.Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress3.setProgress(Math.round(per3),R.color.circle_progress_color3);
                handler3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress3.setProgress(Math.round(per3), R.color.circle_progress_color3);

                    }
                },1000);
            }
        },2000);
    }

    @Override
    public void onLJsonLoaded_new(JSONObject jsonObject) {

    }
}
