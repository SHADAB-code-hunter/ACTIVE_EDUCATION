package utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import services.ServiceMoviesBoxOffice;

/**
 * Created by fabio on 24/01/2016.
 */
public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Broadcast listner !!!!");


        /* Bundle extras = intent.getExtras();
                if (extras != null) {
                        String state = extras.getString(TelephonyManager.EXTRA_STATE);*/

       //  Toast.makeText(context, "boradcast start service", Toast.LENGTH_LONG).show();

        context.startService(new Intent(context, ServiceMoviesBoxOffice.class));
    }

}
