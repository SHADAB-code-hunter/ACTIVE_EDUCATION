package Agent_Profile;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gt.active_education.R;


public class MenuLayout extends LinearLayout {


	public MenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	
	private int mIndexLable;
	
	private TextView textView;
	private MenuImageView menuImg;
	private void init(Context context, AttributeSet attrs){

		TypedArray attr = context.obtainStyledAttributes(attrs,  
                R.styleable.menuLayout);  
		mIndexLable = attr.getInteger(R.styleable.menuLayout_indexLable, 0);
		int imageBackgroundId = attr.getResourceId(R.styleable.menuLayout_imageBackground, R.drawable.bg_transparent
        		);
		
		int imageResourceId = attr.getResourceId(R.styleable.menuLayout_imageContent, R.drawable.bg_transparent);
		float textSize = attr.getDimension(R.styleable.menuLayout_textSize, getResources().getDimension(R.dimen.menu_textsize));
//		String label = attr.getString(R.styleable.menuLayout_text);
        attr.recycle();  
        
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        
        menuImg = new MenuImageView(context);
        menuImg.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT ));
        menuImg.setBackgroundResource(imageBackgroundId);
        menuImg.setImageResourceId(imageResourceId);
        menuImg.setScaleType(ScaleType.CENTER);
        addView(menuImg);
        textView = new TextView(context);
        textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setTextSize(10);
        textView.setSingleLine();
        addView(textView);
        
	}
	public int getIndexLable() {
		return mIndexLable;
	}
	public void setIndexLable(int mIndexLable) {
		if (mIndexLable > 4) {
			mIndexLable  -= 5;
		}else if(mIndexLable < 0){                     
			mIndexLable += 5;
		}
		this.mIndexLable = mIndexLable;
	}
	
	public void updateContent(String text){
		menuImg.updateContent();
		textView.setText(text);textView.setTextColor(getResources().getColor(R.color.white));
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return onTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false; 
	}
	public TextView getTextView() {
		return textView;
	}
	
	
	
	
}
