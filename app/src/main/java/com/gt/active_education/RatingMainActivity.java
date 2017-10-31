/*
 * Copyright (c) 2016 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.gt.active_education;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.RatingBar;

import com.gt.active_education.R;

import RAting_BAr.MaterialRatingBar;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class RatingMainActivity extends AppCompatActivity {

   /* @BindViews({
            R.id.framework_decimal_ratingbar,
            R.id.library_decimal_ratingbar,
            R.id.library_tinted_decimal_ratingbar
    })*/
    RatingBar[] mDecimalRatingBars;
    private MaterialRatingBar library_normal_ratingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rating_main_activity);
        ButterKnife.bind(this);
        library_normal_ratingbar=(MaterialRatingBar)findViewById(R.id.library_normal_ratingbar);
        library_normal_ratingbar.setRating((float) 3.5);
//        mDecimalRatingBars[0].startAnimation(new RatingAnimation());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          /*  case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class RatingAnimation extends Animation {

        public RatingAnimation() {
            setDuration(mDecimalRatingBars[0].getNumStars()
                    * 4 * getResources().getInteger(android.R.integer.config_longAnimTime));
            setInterpolator(new LinearInterpolator());
            setRepeatCount(Animation.INFINITE);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int progress = Math.round(interpolatedTime * mDecimalRatingBars[0].getMax());
            for (RatingBar ratingBar : mDecimalRatingBars) {
                ratingBar.setProgress(progress);
            }
        }

        @Override
        public boolean willChangeTransformationMatrix() {
            return false;
        }

        @Override
        public boolean willChangeBounds() {
            return false;
        }
    }
}
