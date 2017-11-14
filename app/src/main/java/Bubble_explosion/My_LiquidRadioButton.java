package Bubble_explosion;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;


public class My_LiquidRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private int mCircleRadius = Utils.dp2Px(6);
    private int mStrokeWidth = Utils.dp2Px(2);
    private int mExplodeCounts = 5;
    private int mStrokeRadius = Utils.dp2Px(10);
    private int mOuterPadding =  Utils.dp2Px(4);
    private int mInAnimDuration = 400;
    private int mOutAnimDuration = 300;
    private int mCheckedColor = Color.GRAY ;
    private int mUnCheckedColor = Color.GRAY ;

    public My_LiquidRadioButton(Context context) {
        super(context);
        Log.d("coiun","1");
        init();
    }

    public My_LiquidRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);


     //   for(int i=0; i<10;i++) {
            Log.d("coiun","2");
            init(attrs);
       // }
    }

    public My_LiquidRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("coiun","3");
        init(attrs);
    }

    public void init(AttributeSet attrs){
        Log.d("coiufrn","first");
/*
        if (attrs != null) {
            Log.d("coiufrn","21");
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.My_LiquidRadioButton);
            mCircleRadius = a.getDimensionPixelOffset(R.styleable.My_LiquidRadioButton_lrb_innerCircleRadius, mCircleRadius);
            mStrokeRadius = a.getDimensionPixelOffset(R.styleable.My_LiquidRadioButton_lrb_strokeRadius, mStrokeRadius);
            mStrokeWidth = a.getDimensionPixelOffset(R.styleable.My_LiquidRadioButton_lrb_strokeWidth, mStrokeWidth);
            mOuterPadding = a.getDimensionPixelOffset(R.styleable.My_LiquidRadioButton_lrb_outterPadding, mOuterPadding);
            mExplodeCounts = a.getInt(R.styleable.My_LiquidRadioButton_lrb_explodeCount, mExplodeCounts);
            mInAnimDuration = a.getInt(R.styleable.My_LiquidRadioButton_lrb_inAnimDuration, mInAnimDuration);
            mOutAnimDuration = a.getInt(R.styleable.My_LiquidRadioButton_lrb_outAnimDuration, mOutAnimDuration);
            mCheckedColor = a.getColor(R.styleable.My_LiquidRadioButton_lrb_checkedColor,mCheckedColor);
            mUnCheckedColor = a.getColor(R.styleable.My_LiquidRadioButton_lrb_unCheckedColor,mUnCheckedColor);
//            Boolean isChecked = a.getBoolean(R.styleable.androidc,false);
//            setChecked(isChecked);
        }
*/
        if(mCircleRadius>mStrokeRadius)
            throw new IllegalArgumentException("Outer radius can't be less than Inner Radius");
        Log.d("coiufrn","47");
        Log.d("gffrfg",mStrokeRadius+" "+mCircleRadius+" "+mStrokeWidth+" "+mExplodeCounts+" "+mCheckedColor+" "+mUnCheckedColor+" ");
        RadioButtonDrawable drawable = new RadioButtonDrawable.Builder()
                .inAnimDuration(1000)
                .outAnimDuration(1000)
                .radius(69)
                .innerRadius(15)
                .strokeSize(0)
                .explodeCount(10)
                .checkedColor(-1)
                .unCheckedColor(-552824)
                .strokeSize(0)
                .outerPadding(0)
                .build();
        drawable.setInEditMode(isInEditMode());
        drawable.setAnimEnable(true);
        setButtonDrawable(drawable);
        drawable.setAnimEnable(true);

        Log.d("coiufrn","lop");
    }


    public void init(){
        Log.d("coiufrn","first");
       /* if (attrs != null) {
            Log.d("coiufrn","21");
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LiquidRadioButton);
            mCircleRadius = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_innerCircleRadius, mCircleRadius);
            mStrokeRadius = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_strokeRadius, mStrokeRadius);
            mStrokeWidth = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_strokeWidth, mStrokeWidth);
            mOuterPadding = a.getDimensionPixelOffset(R.styleable.LiquidRadioButton_lrb_outterPadding, mOuterPadding);
            mExplodeCounts = a.getInt(R.styleable.LiquidRadioButton_lrb_explodeCount, mExplodeCounts);
            mInAnimDuration = a.getInt(R.styleable.LiquidRadioButton_lrb_inAnimDuration, mInAnimDuration);
            mOutAnimDuration = a.getInt(R.styleable.LiquidRadioButton_lrb_outAnimDuration, mOutAnimDuration);
            mCheckedColor = a.getColor(R.styleable.LiquidRadioButton_lrb_checkedColor,mCheckedColor);
            mUnCheckedColor = a.getColor(R.styleable.LiquidRadioButton_lrb_unCheckedColor,mUnCheckedColor);
//            Boolean isChecked = a.getBoolean(R.styleable.androidc,false);
//            setChecked(isChecked);
        }*/
        if(mCircleRadius>mStrokeRadius)
            throw new IllegalArgumentException("Outer radius can't be less than Inner Radius");
        Log.d("coiufrn","47");
        Log.d("gffrfg",mStrokeRadius+" "+mCircleRadius+" "+mStrokeWidth+" "+mExplodeCounts+" "+mCheckedColor+" "+mUnCheckedColor+" ");
        RadioButtonDrawable drawable = new RadioButtonDrawable.Builder()
                .inAnimDuration(1000)
                .outAnimDuration(1000)
                .radius(69)
                .innerRadius(15)
                .strokeSize(3)
                .explodeCount(10)
                .checkedColor(-1)
                .unCheckedColor(-552824)
                .outerPadding(8)
                .build();
        drawable.setInEditMode(isInEditMode());
        drawable.setAnimEnable(true);
        setButtonDrawable(drawable);
        drawable.setAnimEnable(true);

        Log.d("coiufrn","lop");
    }

    @Override
    public void toggle() {
        // we override to prevent toggle when the radio is already
        // checked (as opposed to check boxes widgets)
      //  if (!isChecked()) {
            super.toggle();
       // }
    }

   /* @Override public Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putBoolean("isChecked",isChecked());
        return bundle;
    }

    @Override public void onRestoreInstanceState(Parcelable state) {
        if ( state!=null && state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            setChecked(bundle.getBoolean("isChecked",false));
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }*/
}