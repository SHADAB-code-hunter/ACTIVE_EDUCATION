package com.gt.active_education;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;

import pojo.GenericsType;

/**
 * Created by GT on 7/27/2017.
 */

public class Test_Activity extends AppCompatActivity {
    public View btnRed;
    private TextView body;
    private RelativeLayout id_relative_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        body = ((TextView) findViewById(R.id.sample_body));
        id_relative_layout=(RelativeLayout)findViewById(R.id.id_relative_layout);
        btnRed=findViewById(R.id.square_red);
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   revealRed();
            }
        });
        ImageView imageView=(ImageView)findViewById(R.id.square_red);
        doBounceAnimation(imageView);
    }
    private void doBounceAnimation(View targetView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationY", 0, 1200,0);
        animator.setInterpolator(new EasingInterpolator(Ease.QUAD_IN));
        animator.setStartDelay(2);
        animator.setDuration(1000);
        animator.start();
    }
    private void revealRed() {
        final ViewGroup.LayoutParams originalParams = btnRed.getLayoutParams();
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealColor(id_relative_layout, R.color.sample_red);
                body.setText(R.string.reveal_body3);
                body.setTextColor(ContextCompat.getColor(Test_Activity.this, R.color.theme1));
                btnRed.setLayoutParams(originalParams);
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
        TransitionManager.beginDelayedTransition(id_relative_layout, transition);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        btnRed.setLayoutParams(layoutParams);
    }
    private void animateRevealColor(ViewGroup viewRoot, @ColorRes int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        animateRevealColorFromCoordinates(viewRoot, color, cx, cy);
    }
    private Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color, int x, int y) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }
}
