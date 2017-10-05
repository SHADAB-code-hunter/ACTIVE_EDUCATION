package utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import services.ServiceMoviesBoxOffice;

/**
 * Created by GT on 6/21/2017.
 */

public class wake_Up_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(wake_Up_Receiver.class.getSimpleName(), "Wake Up Broadcast listner !!!!");

        /* Bundle extras = intent.getExtras();
                if (extras != null) {
                        String state = extras.getString(TelephonyManager.EXTRA_STATE);*/

        //  Toast.makeText(context, "boradcast start service", Toast.LENGTH_LONG).show();

        context.startService(new Intent(context, ServiceMoviesBoxOffice.class));
    }

}
