package com.gt.active_education;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import com.gt.active_education.databinding.ActivitySharedelementBinding;

public class SharedElementActivity extends BaseDetailActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sample sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);//1
        bindData(sample);
        setupWindowAnimations();
        setupLayout(sample);//2
        setupToolbar();
    }

    private void bindData(Sample sample) {
        ActivitySharedelementBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sharedelement);
        binding.setSharedSample(sample);
    }

    private void setupWindowAnimations() {
        // We are not interested in defining a new Enter Transition. Instead we change default transition duration
        getWindow().getEnterTransition().setDuration(500);
    }

    private void setupLayout(Sample sample) {
        // Transition for fragment1
        Slide slideTransition = new Slide(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        // Create fragment and define some of it transitions
        SharedElementFragment1 sharedElementFragment1 = SharedElementFragment1.newInstance(sample);//3
       /* sharedElementFragment1.setReenterTransition(slideTransition);
        sharedElementFragment1.setExitTransition(slideTransition);*/
       // sharedElementFragment1.setSharedElementEnterTransition(new ChangeBounds());

        getSupportFragmentManager().beginTransaction()//4
                .replace(R.id.sample2_content, sharedElementFragment1)
                .commit();
    }
}
