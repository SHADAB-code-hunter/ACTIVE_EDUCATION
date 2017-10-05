package utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.gt.active_education.R;


public class ConnectionCheck {
	ConnectivityManager cm;
	NetworkInfo activeNetwork;
	boolean isConnected;
	Context _context;
	private MediaPlayer mPlay;
	public ConnectionCheck(Context context)
	{
		this._context=context;
	}
	public  boolean checkConnection()
	{
		cm =(ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);
		activeNetwork = cm.getActiveNetworkInfo();
		if (null ==activeNetwork)
			return false;
		isConnected = activeNetwork.isConnectedOrConnecting();
		return isConnected;

	  }

	public static void check_network( final Activity activity) {
		new DroidDialog.Builder(activity)
				.icon(R.drawable.ic_checked)
				.title("Please Checked Internet !!!")
				.cancelable(true, false)

				.neutralButton("Cancle", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {
						//	activity.getParent().onBackPressed();
						activity.finish();
						droidDialog.dismiss();
					}
				})
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorPrimary), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorAccent))
				.divider(true, ContextCompat.getColor(activity, R.color.orange))
				.show();
	}


/*
	public static void exit_open_Dialog(Boolean bl_ok_true, final Activity activity) {
		new DroidDialog.Builder(activity)
				.icon(R.drawable.exit)
				.title("Do You Want to Quit Quiz !!")
				.content(activity.getResources().getString(R.string.short_text))
				.cancelable(true, false)
				.negativeButton("No", new DroidDialog.onNegativeListener() {
					@Override
					public void onNegative(Dialog droidDialog) {
						droidDialog.dismiss();
					}
				})
				.neutralButton("Yes", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {
					//	activity.getParent().onBackPressed();
						activity.finish();
						droidDialog.dismiss();
					}
				})
				.typeface("regular.ttf")
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorPrimary), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorAccent))
				.divider(true, ContextCompat.getColor(activity, R.color.orange))
				.show();
	}

	public static void openDialog(Boolean bl_ok_true, final Activity activity) {
		new DroidDialog.Builder(activity)
				.icon(R.drawable.icon_conn_lose)
				.title("Internet Not Found !!")
				.content(activity.getResources().getString(R.string.short_text))
				.cancelable(true, false)
				.negativeButton("Cancle", new DroidDialog.onNegativeListener() {
					@Override
					public void onNegative(Dialog droidDialog) {
						droidDialog.dismiss();
						// Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
					}
				})
				.neutralButton("Network Setting", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {
						Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS );
						activity.startActivity(intent);
						droidDialog.dismiss();
						// Toast.makeText(getApplicationContext(), "Skip", Toast.LENGTH_SHORT).show();
					}
				})
				.typeface("regular.ttf")
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorPrimaryDark))
				.divider(true, ContextCompat.getColor(activity, R.color.colorPrimaryDark))
				.show();
	}
	public static void login_Dialog(final String str_gate, final Activity activity, final boolean skip_status) {
		new DroidDialog.Builder(activity)
				.icon(R.drawable.ic_login_sts)
				.title("Would You Continue With Login !!")
				.content(activity.getResources().getString(R.string.login_text))
				.cancelable(true, false)
				.negativeButton("Skip Login", new DroidDialog.onNegativeListener() {
					@Override
					public void onNegative(Dialog droidDialog) {
						if(skip_status) {
							Intent intent = new Intent(activity, New_Dashboard_Activity.class);
							intent.putExtra("Dash_act", "2");
							activity.startActivity(intent);
							droidDialog.dismiss();
							activity.finish();
						}
						else {
							droidDialog.dismiss();
						}
					}
				})
				.neutralButton("Login", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {
						Intent i = new Intent(activity, User_Login_Activity.class);
						i.putExtra("lg_call_page",str_gate);
						activity.startActivity(i);
						droidDialog.dismiss();
						activity.finish();
					}
				})
				.typeface("regular.ttf")
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorPrimaryDark))
				.divider(true, ContextCompat.getColor(activity, R.color.orange))
				.show();
	}


	public static void unAuth_prob(final Activity activity,final String str_gate) {
		new DroidDialog.Builder(activity)
				.icon(R.drawable.ic_login_sts)
				.title("Unauthorised User !!")
				.content(activity.getResources().getString(R.string.login_text))
				.cancelable(true, false)

				.neutralButton("OK", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {

						if(activity.getLocalClassName().equals("Quiz_SubList_Activity"))
						{
							droidDialog.dismiss();
							activity.finish();
						}else{
							droidDialog.dismiss();

						}
					}
				})
				.typeface("regular.ttf")
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorPrimaryDark))
				.divider(true, ContextCompat.getColor(activity, R.color.orange))
				.show();
	}


	public static void list_not_get(final Activity activity,final String str_gate) {
		Log.d("nabsmenm",activity.getLocalClassName());
		new DroidDialog.Builder(activity)
				.icon(R.drawable.ic_null_list)
				.title(str_gate)
				.content(activity.getResources().getString(R.string.list_null))
				.cancelable(true, false)
				.neutralButton("OK", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {

						if(activity.getLocalClassName().equals("New_Dashboard_Activity"))
						{
							droidDialog.dismiss();
						}else{
							droidDialog.dismiss();
							activity.finish();
						}
						//
					}
				})
				.typeface("regular.ttf")
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorPrimaryDark))
				.divider(true, ContextCompat.getColor(activity, R.color.orange))
				.show();
	}
	public static void quiz_time_not_get(final Activity activity) {
		Log.d("nabsmenm",activity.getLocalClassName());
		new DroidDialog.Builder(activity)
				.icon(R.drawable.ic_null_list)
				.title(" Quiz Time is not declared yet !!!!")
				.content(activity.getResources().getString(R.string.list_null))
				.cancelable(true, false)
				.neutralButton("OK", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {

						if(activity.getLocalClassName().equals("New_Dashboard_Activity"))
						{
							droidDialog.dismiss();
						}else{
							droidDialog.dismiss();
							activity.finish();
						}
						//
					}
				})
				.typeface("regular.ttf")
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorPrimaryDark))
				.divider(true, ContextCompat.getColor(activity, R.color.orange))
				.show();
	}
	public static void quiz_end_not_get(final Activity activity) {
		Log.d("nabsmenm",activity.getLocalClassName());
		new DroidDialog.Builder(activity)
				.icon(R.drawable.ic_null_list)
				.title("Result Will Be Shown In ScoreCard Menu !!!")
				.content(activity.getResources().getString(R.string.list_null))
				.cancelable(true, false)
				.neutralButton("OK", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {

						droidDialog.dismiss();
						activity.finish();
						/*if(activity.getLocalClassName().equals("Adv_Activity"))
						{
							droidDialog.dismiss();
							activity.finish();
						}else{
							droidDialog.dismiss();
							activity.finish();
						}*/
/*					}
				})
				.typeface("regular.ttf")
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorPrimaryDark))
				.divider(true, ContextCompat.getColor(activity, R.color.orange))
				.show();
	}

	public static void quiz_term_cond(final Activity activity)
	{
		Log.d("nabsmenm",activity.getLocalClassName());
		new DroidDialog.Builder(activity)
				.icon(R.drawable.ic_null_list)
				.title(" Quiz Time is not declared yet !!!!")
				.content(activity.getResources().getString(R.string.list_null))
				.cancelable(true, false)
				.neutralButton("OK", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {

						if(activity.getLocalClassName().equals("New_Dashboard_Activity"))
						{
							droidDialog.dismiss();
						}else{
							droidDialog.dismiss();
							activity.finish();
						}
					}
				})
				.typeface("regular.ttf")
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorPrimaryDark))
				.divider(true, ContextCompat.getColor(activity, R.color.orange))
				.show();
	}

	public static void result_zero(final Activity activity)
	{
		Log.d("nabsmenm",activity.getLocalClassName());
		new DroidDialog.Builder(activity)
				.icon(R.drawable.ic_null_list)
				.title(" Sorry You Have Obtain No Marks  !!!!")
				.content(activity.getResources().getString(R.string.list_null))
				.cancelable(true, false)
				.neutralButton("OK", new DroidDialog.onNeutralListener() {
					@Override
					public void onNeutral(Dialog droidDialog) {

						if(activity.getLocalClassName().equals("New_Dashboard_Activity"))
						{
							droidDialog.dismiss();
						}else{
							droidDialog.dismiss();
							activity.finish();
						}
					}
				})
				.typeface("regular.ttf")
				.animation(AnimUtils.AnimZoomInOut)
				.color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
						ContextCompat.getColor(activity, R.color.colorPrimaryDark))
				.divider(true, ContextCompat.getColor(activity, R.color.orange))
				.show();
	}*/

   public static void user_Already_exist(final Activity activity,final String str_gate) {
	new DroidDialog.Builder(activity)
			.icon(R.drawable.ic_login)
			.title(str_gate)
			.content(" Already Exists !!!!")
			.cancelable(true, false)

			.neutralButton("OK", new DroidDialog.onNeutralListener() {
				@Override
				public void onNeutral(Dialog droidDialog) {
						droidDialog.dismiss();
				}
			})
			.typeface("regular.ttf")
			.animation(AnimUtils.AnimZoomInOut)
			.color(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorPrimaryDark),
					ContextCompat.getColor(activity, R.color.colorPrimaryDark))
			.divider(true, ContextCompat.getColor(activity, R.color.orange))
			.show();
     }
}
