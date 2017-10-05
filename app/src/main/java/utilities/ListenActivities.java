package utilities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gt.active_education.DashBoard_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import callbacks.Log_Out_Listener;
import network.VolleySingleton;

/**
 * Created by GT on 8/31/2017.
 */

public class ListenActivities extends Thread{
    boolean exit = false;
    ActivityManager am = null;
    Context context = null;

    public ListenActivities(Context con){
        context = con;
        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public void run(){

        Looper.prepare();

        while(!exit){

            // get the info from the currently running task
            List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(MAX_PRIORITY);

            String activityName = taskInfo.get(0).topActivity.getClassName();


            Log.d("topActivity", "CURRENT Activity ::"
                    + activityName);

            if (activityName.equals("com.gt.active_education.UninstallerActivity")) {
                // User has clicked on the Uninstall button under the Manage Apps settings

                //do whatever pre-uninstallation task you want to perform here
                // show dialogue or start another activity or database operations etc..etc..

                // context.startActivity(new Intent(context, MyPreUninstallationMsgActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                exit = true;
                Toast.makeText(context, "Done with preuninstallation tasks... Exiting Now", Toast.LENGTH_SHORT).show();
                logout();

            } else if(activityName.equals("com.android.settings.ManageApplications")) {
                // back button was pressed and the user has been taken back to Manage Applications window
                // we should close the activity monitoring now
                exit=true;
            }
        }
        Looper.loop();
    }
    public void logout() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence,0);
        String str_email=sharedPreferences.getString("email", "na");
        String str_token=sharedPreferences.getString("Login_Token", "na");
        String str_type=sharedPreferences.getString("type", "na");


        if(!(str_email.equals("na")) && !(str_token.equals("na")) &&  !(str_type.equals("na"))) {
            Log.d("gfhgfhgfhg",str_email+"  "+str_token+" "+str_type);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlEndpoints.SET_LOG_OUT +"email="+ str_email
                    + "&token=" + str_token+"&type="+str_type,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                Log.d("lougour", response.toString());
                                //{"msg":1}
                                Log.d("ffff",""+jObj.getString("msg"));
                                if ((""+(jObj.getString("msg"))).equals("1")) { // have to must convert in to string
                                    Log.d("udpgad", response.toString());
                                    SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(UpdateValues.LG_U_Prefrence, 0);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear().commit();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //  Toast.makeText(dashBoard_activity, "Logout Unsuccessfull", Toast.LENGTH_LONG).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    return null;
                }
            };

            RequestQueue requestQueue= VolleySingleton.getInstance().getRequestQueue();
            requestQueue.add(stringRequest);

        }else {


        }

    }

}
