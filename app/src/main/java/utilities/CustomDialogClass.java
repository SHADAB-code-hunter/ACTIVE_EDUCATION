package utilities;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gt.active_education.R;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import Zend_Chat.UserProfile;
import Zend_Chat.UserProfileStorage;
import callbacks.Call_Dilaog_Listener;
import callbacks.Call_newDialog_Listener;
import callbacks.Spinner_Date_Listener;


/**
 * Created by GT on 5/27/2017.
 */

public class CustomDialogClass extends Dialog implements View.OnClickListener {
    RelativeLayout id_relative_dialog;
    private View btnRed;
    private Activity act;
    private ImageView id_website,id_video_call,id_sms,id_call;
    private View find_v;
    public Activity c;
    private Spinner_Date_Listener spinnerDateListener;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.find_v=find_v;
        this.spinnerDateListener=(Spinner_Date_Listener) a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        id_relative_dialog=(RelativeLayout)findViewById(R.id.id_relative_dialog);
        id_website=(ImageView)findViewById(R.id.id_website);id_website.setOnClickListener(this);
        id_video_call=(ImageView)findViewById(R.id.id_v_call);id_video_call.setOnClickListener(this);
        id_sms=(ImageView)findViewById(R.id.id_sms);id_sms.setOnClickListener(this);
        id_call=(ImageView)findViewById(R.id.id_call);id_sms.setOnClickListener(this);
        id_sms.setOnClickListener(new AuthOnClickWrapper(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                PreChatForm build = new PreChatForm.Builder()
                        .name(PreChatForm.Field.REQUIRED)
                        .email(PreChatForm.Field.REQUIRED)
                        .phoneNumber(PreChatForm.Field.OPTIONAL)
                        .message(PreChatForm.Field.OPTIONAL)
                        .build();

                ZopimChat.SessionConfig department = new ZopimChat.SessionConfig()
                        .preChatForm(build)
                        .department("The date");

                ZopimChatActivity.startActivity(CustomDialogClass.this.getContext(), department);
            }
        },this.getContext()));

        //revealRed();
    }


    private void revealRed() {
      //  final ViewGroup.LayoutParams originalParams = btnRed.getLayoutParams();
        Transition transition = TransitionInflater.from(act).inflateTransition(R.transition.changebounds_with_arcmotion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealColor(id_relative_dialog, R.color.sample_red);

            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        TransitionManager.beginDelayedTransition(id_relative_dialog, transition);
       /* RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        btnRed.setLayoutParams(layoutParams);*/
    }
    private void animateRevealColor(ViewGroup viewRoot, @ColorRes int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        animateRevealColorFromCoordinates(viewRoot, color, cx, cy);
    }
    private Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color, int x, int y) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(act, color));
        anim.setDuration(2000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_website:
                ((Call_newDialog_Listener)act).on_id_listener("WEBSITE");
                break;
          /*  case R.id.id_sms:
            //    ((Call_Dilaog_Listener)act).on_id_listener("CHAT");
                break;*/
            case R.id.id_v_call:
                ((Call_newDialog_Listener)act).on_id_listener("V_CALL");
                break;
            case R.id.id_call:
               ((Call_newDialog_Listener)act).on_id_listener("CALL");

                break;
        }
    }
    class AuthOnClickWrapper implements View.OnClickListener {

        private View.OnClickListener mOnClickListener;
        private UserProfileStorage mUserProfileStorage;
        private Context mContext;

        public AuthOnClickWrapper(View.OnClickListener onClickListener, Context context){
            this.mOnClickListener = onClickListener;
            this.mUserProfileStorage = new UserProfileStorage(context);
            this.mContext = context;
        }

        @Override
        public void onClick(View v) {
            final UserProfile profile = mUserProfileStorage.getProfile();

            //  if(StringUtils.hasLength("ahmed.shadab221@gmail.com")){
            mOnClickListener.onClick(v);
            //   }else{
            // showDialog();
            //  }
        }


    }

}