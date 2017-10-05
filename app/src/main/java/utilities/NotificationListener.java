package utilities;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/*import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;*/
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 26-09-2016.
 */
public class NotificationListener extends Service {


    public int counter=0;

    /*@Override
       public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        Toast.makeText(NotificationListener.this, "call from dashboard start running", Toast.LENGTH_SHORT).show();

    }*/

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public NotificationListener() {
        Log.i("RESTART", "here I am!");
    //    Toast.makeText(NotificationListener.this, "start running", Toast.LENGTH_SHORT).show();

        Handler handler= new Handler();

    }
    public NotificationListener(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
     //   Toast.makeText(applicationContext, "call from dashboard start running", Toast.LENGTH_SHORT).show();
    }


    //When the service is started
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
       // Toast.makeText(getApplicationContext(), "service ondestroy call", Toast.LENGTH_SHORT).show();

       // stoptimertask();
    }
    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getdata();
        return START_STICKY;
    }
    private void getdata() {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        // databaseReference.push().setValue()
        databaseReference.child(databaseReference.getKey()).getRef().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                Log.d("kfkvjk",dataSnapshot.getKey());

                Log.d("childffv",""+dataSnapshot.hasChild("message"));
                if (dataSnapshot.hasChild("message")) {
                    Log.d("gdggdg", "getMessageFromFirebaseUser: " + dataSnapshot.getValue() + " exists");
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("title")
                            .child("message").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                          /*  Chat chat = dataSnapshot.getValue(Chat.class);
                            Log.d("child_added",chat.message);
                            mOnGetMessagesListener.onGetMessagesSuccess(chat);*/
                         /*   Log.d("child_added3",s);
                            Log.d("child_added2",dataSnapshot.getKey()+"  "+dataSnapshot.getValue());*/
                            Toast.makeText(NotificationListener.this, "onChildAdded", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                          /*  Log.d("child_added3",s);
                            Log.d("child_added2",dataSnapshot.getKey()+"  "+dataSnapshot.getValue());*/
                            Toast.makeText(NotificationListener.this, "onChildChanged", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

                            Toast.makeText(NotificationListener.this, "onChildRemoved", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                            Toast.makeText(NotificationListener.this, "onChildMoved", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //  mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
                            Toast.makeText(NotificationListener.this, "onCancelled", Toast.LENGTH_SHORT).show();

                        }
                    });
                } /*else if (dataSnapshot.hasChild(room_type_2)) {
                    Log.e(TAG, "getMessageFromFirebaseUser: " + room_type_2 + " exists");
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_2).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            mOnGetMessagesListener.onGetMessagesSuccess(chat);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                          //  mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
                        }
                    });
                } else {
                    Log.e(TAG, "getMessageFromFirebaseUser: no such room available");
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
            }
        });

        //databaseReference.child("-KqNp6BJU14MzYAfmxUb").getRef().addListenerForSingleValueEvent(valueEventListener);
    }

}
