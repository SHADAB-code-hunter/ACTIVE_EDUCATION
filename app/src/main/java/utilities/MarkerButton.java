package utilities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.gt.active_education.R;


public abstract class MarkerButton extends CompoundToggleButton {
    private static final String LOG_TAG = MarkerButton.class.getSimpleName();
    protected static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };
    protected TextView mTvText;
    protected ImageView mIvBg;
    protected ColorStateList mTextColorStateList;
    protected int mMarkerColor;
    protected boolean mRadioStyle;

    public MarkerButton(Context context) {
        this(context, null);
    }

    public MarkerButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_marker_button, this, true);
        mIvBg = (ImageView) findViewById(R.id.iv_bg);
        mTvText = (TextView) findViewById(R.id.tv_text);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.MarkerButton, 0, 0);
        try {
            CharSequence text = a.getText(R.styleable.MarkerButton_android_text);
            mTvText.setText(text);

            mTextColorStateList = a.getColorStateList(R.styleable.MarkerButton_android_textColor);
            if (mTextColorStateList == null) {
                mTextColorStateList = ContextCompat.getColorStateList(context, R.color.pure_red);//selector_marker_text
            }
            mTvText.setTextColor(mTextColorStateList);

            mMarkerColor = a.getColor(R.styleable.MarkerButton_tbgMarkerColor, ContextCompat.getColor(getContext(), R.color.color_golden));

            mRadioStyle = a.getBoolean(R.styleable.MarkerButton_tbgRadioStyle, false);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void toggle() {
        // Do not allow toggle to unchecked state when mRadioStyle is true
        if (mRadioStyle && isChecked()) {
            return;
        }
        super.toggle();
    }

    public boolean isRadioStyle() {
        return mRadioStyle;
    }

    public void setRadioStyle(boolean radioStyle) {
        mRadioStyle = radioStyle;
    }

    public void setText(CharSequence text) {
        mTvText.setText(text);
    }

    public CharSequence getText() {
        return mTvText.getText();
    }

    public void setTextColor(int color) {
        mTvText.setTextColor(color);
    }

    public void setTextColor(ColorStateList colors) {
        mTvText.setTextColor(colors);
    }

    public ColorStateList getTextColors() {
        return mTvText.getTextColors();
    }

    public void setTextBackground(Drawable drawable) {
        mTvText.setBackgroundDrawable(drawable);
    }

    public Drawable getTextBackground() {
        return mTvText.getBackground();
    }

    public void setCheckedImageDrawable(Drawable drawable) {
        mIvBg.setImageDrawable(drawable);
    }

    public Drawable getCheckedImageDrawable() {
        return mIvBg.getDrawable();
    }

    protected int getDefaultTextColor() {
        return mTextColorStateList.getDefaultColor();
    }

    protected int getCheckedTextColor() {
        return mTextColorStateList.getColorForState(CHECKED_STATE_SET, getDefaultTextColor());
    }

    protected float dpToPx(float dp){
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
