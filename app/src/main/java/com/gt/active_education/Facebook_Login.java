package com.gt.active_education;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.payu.magicretry.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by GT on 10/4/2017.
 */

public class Facebook_Login  extends AppCompatActivity{

    /* *************************************
    *              FACEBOOK               *
    ***************************************/
    /* The login button for Facebook */
    private LoginButton mFacebookLoginButton;
    /* The callback manager for Facebook */
    private CallbackManager mFacebookCallbackManager;
    /* Used to track user logging in/out off Facebook */
    private AccessTokenTracker mFacebookAccessTokenTracker;
    private Firebase mFirebaseRef;
    /* Data from the authenticated user */
    private AuthData mAuthData;
    private TextView mLoggedInStatusTextView;
    /* Listener for Firebase session changes */
    private Firebase.AuthStateListener mAuthStateListener;
    private LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Load the view and display it */
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_fb_login);
       /* mLoggedInStatusTextView = (TextView) findViewById(R.id.login_status);
        *//* *************************************
         *              FACEBOOK               *
         ***************************************//*
        *//* Load the Facebook login button and set up the tracker to monitor access token changes *//*
        mFacebookCallbackManager = CallbackManager.Factory.create();
        mFacebookLoginButton = (LoginButton) findViewById(R.id.connectWithFbButton);
        mFacebookAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.i("Facebook_Ac_Token", "Facebook.AccessTokenTracker.OnCurrentAccessTokenChanged");
                Facebook_Login.this.onFacebookAccessTokenChange(currentAccessToken);
            }
        };
*/
/*
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);*/
    // id_fb_img=(ImageView)findViewById(R.id.id_fb_img);

       /*google sign In*/
    //set_google_login_var();
        /*end signIn */

         loginButton=(LoginButton)findViewById(R.id.connectWithFbButton);
         loginButton.setReadPermissions("email");loginButton.setReadPermissions("user_friends");loginButton.setReadPermissions("public_profile");
         loginButton.setReadPermissions("picture");loginButton.setReadPermissions("user_birthday");
         callbackManager = CallbackManager.Factory.create();
        // LoginManager.getInstance().logInWithReadPermissions(Login_Activity.this, Arrays.asList("email", "user_friends", "public_profile","picture","user_birthday"));
       //  loginButton.setLoginBehavior(LoginBehavior.DEVICE_AUTH);
        // loginButton.setReadPermissions();
         //Log.d("hdh","hello");
         loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
         {
                     @Override
                     public void onSuccess(LoginResult loginResult) {
                         // App code
                         final AccessToken accessToken=loginResult.getAccessToken();
                         Log.d("data1token234",""+accessToken);
                         // final AccessToken accessToken = loginResult.getAccessToken();
                      //   final FBUser fbUser = new FBUser();
                         GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                             @Override
                             public void onCompleted(JSONObject user, GraphResponse graphResponse) {

                                 if (graphResponse != null)
                                 {
                                     try {
                                         Bundle bFacebookData = getFacebookData(user);
                                         JSONObject data = graphResponse.getJSONObject();
                                         Log.d("data1234",""+data);
                                         Log.d("bfacbook",""+bFacebookData.getString("profile_pic"));
                                       /*  if (data.has("picture")) {
                                            *//**//* String profilePicUrl = data.getJSONObject("picture").getString("url");
                                                    Bitmap profilePic= BitmapFactory.decodeStream(profilePicUrl.openConnection().getInputStream());
                                                    mImageView.setBitmap(profilePic);*//**//*
                                                }
                                                saveLogin_Prf_Status(getApplicationContext(), UpdateValues.Preferences,"F_OK",accessToken.getToken(),bFacebookData.getString("first_name"),
                                                        bFacebookData.getString("profile_pic"), bFacebookData.getString("email"));

                                                registered_database("F_OK",bFacebookData,accessToken.getToken());
                                                Intent intent=null;
                                                if(str_sp_lg_status.equals("sp_to_db")||str_sp_lg_status.equals("db_to_Lg")) {
                                                    //i.putExtra("lg_call_page", "sp_to_db");//splash to dashborad
                                                   *//**//* intent = new Intent(Login_Activity.this, New_Dashboard_Activity.class);
                                                    set_Intent_data(intent,accessToken,bFacebookData);*//**//*
                                                    finish();
                                                }
                                                else if(str_sp_lg_status.equals("null"))//audition to Audition detail page
                                                {
                                                    intent = new Intent(Login_Activity.this, Quiz_SubList_Activity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                               *//**//* else if(str_sp_lg_status.equals("Take_Quiz_Page"))//audition to Audition detail page
                                                {
                                                    intent = new Intent(Login_Activity.this, Take_Daily_Quiz_Activity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else if(str_sp_lg_status.equals("Quiz_Calc_Page"))//audition to Audition detail page
                                                {
                                                    intent = new Intent(Login_Activity.this, Result_Pie_Quiz_Activity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }*//**//*
        */

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }else {

                                         }
                                    }
                                });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                    @Override
                    public void onCancel() {
                        Log.d("LoginResult", "FB: login cancel");
                        //showDialog(getString(R.string.dialog_message_unknown_error));
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("LoginException", ""+exception.toString());
                        //  showDialog(getString(exception));

                    }
                });



           }




         /* ************************************
     *             FACEBOOK               *
     **************************************
     */
        private void onFacebookAccessTokenChange(AccessToken token) {
            if (token != null) {
                Log.d("Face_Ac_Token", ""+token);

                //  mAuthProgressDialog.show();
                mFirebaseRef.authWithOAuthToken("facebook", token.getToken(), new AuthResultHandler("facebook"));
            } else {
                Log.d("Face_Ac", ""+token);

                // Logged out of Facebook and currently authenticated with Firebase using Facebook, so do a logout
                if (this.mAuthData != null && this.mAuthData.getProvider().equals("facebook")) {
                    mFirebaseRef.unauth();
                    setAuthenticatedUser(null);
                }
            }
        }


    /**
     * Utility class for authentication results
     */
    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
           /* mAuthProgressDialog.hide();*/
            Log.i("auth_successful", provider + " auth successful");
            setAuthenticatedUser(authData);
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
         /*   mAuthProgressDialog.hide();*/
            showErrorDialog(firebaseError.toString());
        }
    }
        private Bundle getFacebookData(JSONObject object) {
            Bundle bundle = new Bundle();
            try {
                String id = object.getString("id");
                try {
                    URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                    Log.i("profile_pic", profile_pic + "");
                    bundle.putString("profile_pic", profile_pic.toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }

                bundle.putString("idFacebook", id);
                if (object.has("first_name"))
                    bundle.putString("first_name", object.getString("first_name"));
                if (object.has("last_name"))
                    bundle.putString("last_name", object.getString("last_name"));
                if (object.has("email"))
                    bundle.putString("email", object.getString("email"));
                if (object.has("gender"))
                    bundle.putString("gender", object.getString("gender"));
                if (object.has("birthday"))
                    bundle.putString("birthday", object.getString("birthday"));
                if (object.has("location"))
                    bundle.putString("location", object.getJSONObject("location").getString("name"));

                return bundle;
            }
            catch(JSONException e) {
                //Log.d(TAG,"Error parsing JSON");
            }
            bundle.putString("error", "error");
            return bundle;
        }

    private void showErrorDialog(String message) {
        Log.d("facebook_error",""+message);
    }


    /**
     * Once a user is logged in, take the mAuthData provided from Firebase and "use" it.
     */
    private void setAuthenticatedUser(AuthData authData) {
        if (authData != null) {
            /* Hide all the login buttons */
            mFacebookLoginButton.setVisibility(View.GONE);
           /* mGoogleLoginButton.setVisibility(View.GONE);
            mTwitterLoginButton.setVisibility(View.GONE);
            mPasswordLoginButton.setVisibility(View.GONE);
            mAnonymousLoginButton.setVisibility(View.GONE);*/
            mLoggedInStatusTextView.setVisibility(View.VISIBLE);
            /* show a provider specific status text */
            String name = null;
            if (authData.getProvider().equals("facebook")
                    || authData.getProvider().equals("google")
                    || authData.getProvider().equals("twitter")) {
                name = (String) authData.getProviderData().get("displayName");
            } else if (authData.getProvider().equals("anonymous")
                    || authData.getProvider().equals("password")) {
                name = authData.getUid();
            } else {
                Log.e("Invalid_provider", "Invalid provider: " + authData.getProvider());
            }
            if (name != null) {
                mLoggedInStatusTextView.setText("Logged in as " + name + " (" + authData.getProvider() + ")");
            }
        } else {
            /* No authenticated user show all the login buttons */
            mFacebookLoginButton.setVisibility(View.VISIBLE);

        }
        this.mAuthData = authData;
        /* invalidate options menu to hide/show the logout button */
        supportInvalidateOptionsMenu();
    }

}

