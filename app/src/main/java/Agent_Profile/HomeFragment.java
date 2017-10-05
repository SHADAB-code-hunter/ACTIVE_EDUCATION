package Agent_Profile;


import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gt.active_education.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapter.Best_Deal_Adapter;
import callbacks.Upcoming_List_LoadedListener;
import pojo.Cat_Model;
import utilities.Common_Pojo;


/**
 * @author xuechinahb@gmail.com
 *
 */
public class HomeFragment extends Fragment implements OnClickListener, Upcoming_List_LoadedListener {
	/**
	 * five menus 
	 */
	private MenuLayout imageA, imageB, imageC, imageD, imageE;
	/**
	 * round ImageView in this middle of five menus
	 */
	private CenterImageView mCenterImageView;
	/**
	 * parent view of five menus
	 */
	private View mMenuParentView;
	
	
	/**
	 *  header view of {@link #mListView}, a empty view
	 */
	private View mListHeaderView;
	/**
	 * height of {@link //mPlaceHolderView}
	 */
	private int mListViewHeaderHeight;
	/**
	 * default animation duration
	 */
	private int mDuration = 300;
	
	/**
	 * menu expands or not.
	 * true: menu expands，forming an arc，touch move {@link #mMenuParentView} left and right to switch menus.
	 * false: menu collapses, five menus is at the same horizontal lines. at this time, you can't touch move {@link #mMenuParentView} left and right to switch menus.
	 */
	private boolean mExpandFlag = true;
	/**
	 * scale animation value of the middle menu
	 */
	private float mScaleValue = 1.35f;
	private ListView mListView;
	
	/**
	 * used to get coordinate, width and height of some views, {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, only once
	 */
	private boolean mFlag;
	/**
	 * ad.
	 */
	private ViewPagerAD mViewPager;
	/**
	 * begin and end coordinate of {@link #mViewPager} animation. when scroll up an down {@link #mListView}, 
	 * {@link #mViewPager} load translate animation in the y direction.
	 */
	private float mViewPagerBeginY, mViewPagerEndY;
	
	/**
	 * search box
	 */
	private View mLayoutSearch;
	
	/**
	 * frame layout, it contains {@link #mMenuParentView}, {@link #blankView}, {@link #mViewPager}
	 */
	private View mLayoutFrame;
	/**
	 * blank view, background is yellow
	 */
	private View blankView;
	/**
	 * begin and end coordinate of {@link #mLayoutFrame} animation. 
	 */
	private float layoutFrameBeginY,  layoutFrameEndY;
	/**
	 * scroll offset of {@link #mListView}, 
	 */
	private float listScrollOffset;
	/**
	 * touch flag. if false, block  {@link #mListView} to call onScroll(AbsListView view, int firstVisibleItem,
	 *				int visibleItemCount, int totalItemCount)
	 */
	private boolean touchScrollFlag;
	private LinearLayout id_top_linear;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view =  inflater.inflate(R.layout.home_fragment, container, false);
		initView(view);
		ViewTreeObserver vto = view.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				if (!mFlag) {
					calCoordinate();
					mListViewHeaderHeight = mListHeaderView.getHeight();
					layoutFrameBeginY = mLayoutFrame.getY();
					layoutFrameEndY = layoutFrameBeginY - blankView.getMeasuredHeight();
					imageCircleAnim = ObjectAnimator.ofFloat(mCenterImageView, "y", -mCenterImageView.getMeasuredHeight()).setDuration(mDuration);
					imageCircleAnim.setInterpolator(new LinearInterpolator());
					initTranslationYAnim();
					mFlag = true;
					int offsetY = getResources().getDimensionPixelSize(R.dimen.ad_offsety);
					mViewPagerBeginY = mViewPager.getY();
					
					mViewPagerEndY = blankView.getY() + blankView.getMeasuredHeight() +  imageAEndY + imageA.getMeasuredHeight() + offsetY;
					listScrollOffset = imageAEndY + imageA.getMeasuredHeight()+ mViewPager.getMeasuredHeight()
							+ offsetY;
					
					RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (listScrollOffset - getResources().getDimensionPixelSize(R.dimen.blank_view_height)));
					layoutParam.addRule(RelativeLayout.BELOW, R.id.blank_view);
					getView().findViewById(R.id.blank_color_view).
					setLayoutParams(layoutParam);
				}
				
			}
		});



		return view;
	}
	
	private ObjectAnimator imageCircleAnim;
	private boolean mSrollStopFlag = true;
	
	private void initView(View view) {
		
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);

		imageA = (MenuLayout) view.findViewById(R.id.img_a);
		imageA.setOnClickListener(this);
		imageB = (MenuLayout) view.findViewById(R.id.img_b);
		imageB.setOnClickListener(this);
		imageC = (MenuLayout) view.findViewById(R.id.img_c);
		imageC.setOnClickListener(this);
		imageD = (MenuLayout) view.findViewById(R.id.img_d);
		imageD.setOnClickListener(this);
		imageE = (MenuLayout) view.findViewById(R.id.img_e);
		imageE.setOnClickListener(this);
		mCenterImageView = (CenterImageView) view.findViewById(R.id.circle);
		id_top_linear=(LinearLayout)view.findViewById(R.id.id_top_linear);
		mLayoutSearch = view.findViewById(R.id.layout_search);
		mMenuParentView = view.findViewById(R.id.layout_menu);
		//mMenuParentView.setBackgroundColor(getResources().getColor(R.color.grey_home));
		mLayoutFrame = view.findViewById(R.id.layout_frame);
		blankView = view.findViewById(R.id.blank_view);
		mLayoutSearch.bringToFront();
		
		mViewPager = (ViewPagerAD) view.findViewById(R.id.viewPager);
		View view1 = new View(getActivity());
		view1.setBackgroundColor(Color.BLUE);
		View view2 = new View(getActivity());
		view2.setBackgroundColor(Color.GREEN);
		View view3 = new View(getActivity());
		view3.setBackgroundColor(Color.RED);
		final View[] views = {view1, view2, view3};
		PagerAdapter pagerAdapter = new PagerAdapter() {
			  
            @Override  
            public boolean isViewFromObject(View arg0, Object arg1) {  
  
                return arg0 == arg1;  
            }  
  
            @Override  
            public int getCount() {  
  
                return views.length;  
            }  
  
            @Override  
            public void destroyItem(ViewGroup container, int position,  
                    Object object) {  
                container.removeView(views[position]);  
  
            }  

            @Override  
            public Object instantiateItem(ViewGroup container, int position) {  
                container.addView(views[position]);  
                return views[position];  
            }  
  
        };  
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				mViewPager.setCurrentItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		mListView = (ListView) view.findViewById(R.id.listview);
		mListHeaderView = getActivity().getLayoutInflater().inflate(R.layout.view_header_blank, mListView, false);
		mListView.addHeaderView(mListHeaderView);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			if(i == 0) list.add("scroll listview or touch move menu view " ); 
			else list.add("Item " + i);
		}
			/*  best offer list*/
		//new TaskLoad_List((Upcoming_List_LoadedListener) HomeFragment.this).execute();


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.single_movie, android.R.id.text1, list);
		mListView.setAdapter(adapter);
		mListView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (mListView.getFirstVisiblePosition() == 0) {
					initTranslationYAnim();
				}
				return false;
			}
		});

		mListView.setOnScrollListener(new OnScrollListener() {// fill data at first time, although not use finger to scroll listview, onsroll(***) can be called.
			private OnScrollListener onScrollListener;
			//private OnDetectScrollListener onDetectScrollListener;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				 int mLastFirstVisibleItem=0;
				 boolean mIsScrollingUp;
				Log.d("scrollstate",""+scrollState);
				mSrollStopFlag = false;
				if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
					touchScrollFlag = true;
				}
				if (!touchScrollFlag) {
					return;
				}
				
				if (scrollState == SCROLL_STATE_IDLE) {
					System.out.println("scrolling stopped...");

					Log.d("scroll_up",""+scrollState);
					mSrollStopFlag = true;
					float scrollHeight = getScrollY();
//					if (scrollHeight >= mListView.getChildAt(0).getHeight() - imageA.getHeight()) {
//						return;
//					}
					if (scrollHeight >= mListView.getChildAt(0).getHeight() - listScrollOffset) {
					//	id_top_linear.setBackgroundColor(getResources().getColor(R.color.grey_home));
						mExpandFlag = false;
						return;
					}
					
					int tempHeight =  (int) ((mViewPagerBeginY - mViewPagerEndY )/ 2);
					if (scrollHeight < tempHeight) {//distance of scroll up is small, revert previous location, menus is expanded.
					//	id_top_linear.setBackgroundColor(getResources().getColor(R.color.transparent));
						mExpandFlag = true;
						mListView.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								mListView.smoothScrollToPositionFromTop(0, 0, 200);
							}
						}, 50);
					}else {//menu collapses
					//	id_top_linear.setBackgroundColor(getResources().getColor(R.color.grey_home));
						mExpandFlag = false;
						mListView.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								mListView.smoothScrollToPositionFromTop(1, (int) listScrollOffset, 200);
							}
						}, 50);
					}
					
				}


				/**/

				//final ListView lw = mListViewgetListView();

				if (view.getId() == mListView.getId()) {
					final int currentFirstVisibleItem = mListView.getFirstVisiblePosition();

					if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                        //  Log.d("scder");
						mIsScrollingUp = false;
						Log.d("is_scroll","juujujuju"+currentFirstVisibleItem+" "+mLastFirstVisibleItem);
					} else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
						mIsScrollingUp = true;
					//	Log.d("is_scroll","hgtrde");
						Log.d("is_scroll","hgtrde"+currentFirstVisibleItem+" "+mLastFirstVisibleItem);
					}

					mLastFirstVisibleItem = currentFirstVisibleItem;
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				Log.d("itemes",""+firstVisibleItem+" "+visibleItemCount+" "+totalItemCount);

				if (onScrollListener != null) {
					onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
				}

				//if (onDetectScrollListener != null) {
				//	onDetectedListScroll(view, firstVisibleItem, totalItemCount, visibleItemCount);
				//}
				if (!touchScrollFlag) {
					Log.d("scrollin","derfs");
					return;
				}
				float scrollY = getScrollY();
				if (view.getFirstVisiblePosition() > 0) {
					Log.d("scrollin","trew");
					return;
				}
				updateTranslationYAnim((int)(scrollY / (mListViewHeaderHeight - listScrollOffset ) *  (mDuration)));

			}

		});
		
		mMenuParentView.setOnTouchListener(new OnTouchListener() {
			
			boolean moveToLeftFlag;
			
			/**
			 * move left or right, up or down. if true, block menu being clicked.
			 */
			boolean moveFlag;
			boolean moveHorizontalFlag, moveVerticalFlag;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				if (!mExpandFlag) {
//					return false;
//				}
				touchScrollFlag = true;
				initMoveLeftAnim();
				initMoveRightAnim();
				initTranslationYAnim();
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					lastX = event.getX();
					lastY = event.getY();
					moveToLeftFlag = false;
					moveFlag = false;
					moveHorizontalFlag = false;
					moveVerticalFlag = false;
					break;
				case MotionEvent.ACTION_MOVE:
						if (moveHorizontalFlag) {
							if((event.getX() - lastX) > 15){// move right
								moveFlag  = true;
								moveToLeftFlag = false;
								moveHorizontalFlag = true;
								if(mExpandFlag){
									updateMoveRightAnim((long)((event.getX() - lastX) / ( imageEBeginX - imageABeginX)  * mDuration ));
								}
							}else if((lastX - event.getX()) >15){// move left
								moveFlag = true;
								moveToLeftFlag = true;
								moveHorizontalFlag = true;
								if(mExpandFlag){
									updateMoveLeftAnim((long)((Math.abs(event.getX() - lastX)) / ( imageEBeginX - imageABeginX)  * mDuration ));
								}
							}
						}else{
							if(lastY - event.getY() > 15){ // move up
								if (!moveVerticalFlag) {
									moveVerticalFlag = true;
									moveFlag  = true;
									if (!moveHorizontalFlag) {
										if (mExpandFlag) {
											mExpandFlag = false;
											mListView.smoothScrollBy( (int) (listScrollOffset), 500);
										}
									}
								}
								
							}else if(event.getY() - lastY  > 15){ // move down
								if(mExpandFlag && mSrollStopFlag) moveHorizontalFlag = true;//will call updateMoveRightAnim(...) or updateMoveLeftAnim(...)
								else moveHorizontalFlag = false;
								
								if (!moveVerticalFlag) {
									moveVerticalFlag = true;
									moveFlag  = true;
									if (!moveHorizontalFlag) {
										mExpandFlag = true;
										mListView.smoothScrollToPositionFromTop(0, 0,  200);
									}
								}
							}else {
								if(Math.abs((event.getX() - lastX)) > 15) moveHorizontalFlag = true;
							}
						}
					
					break;
				case MotionEvent.ACTION_UP:
					if (!moveFlag) {
						if (isClickMenuLayout(event, imageA)) {
							onClick(imageA);
						}else if(isClickMenuLayout(event, imageB)) {
							onClick(imageB);
						}else if(isClickMenuLayout(event, imageC)) {
							onClick(imageC);
						}else if(isClickMenuLayout(event, imageD)) {
							onClick(imageD);
						}else if(isClickMenuLayout(event, imageE)) {
							onClick(imageE);
						}
				}else if(moveHorizontalFlag){
					if (moveToLeftFlag) {
						finishMoveLeftAnim();
					}else{
						finishMoveRightAnim();
					}
					
				}
					break;
				default:
					break;
				}
				
				return true;
			}
		});
		
	}
	private boolean isClickMenuLayout(MotionEvent event, MenuLayout menuLayout){
		return event.getX() >= menuLayout.getX() && event.getX() <=  menuLayout.getX() + menuLayout.getMeasuredWidth()
				&& event.getY() >= menuLayout.getY() && event.getY() <= menuLayout.getY() + menuLayout.getMeasuredHeight();
	}
	
	private SparseIntArray listViewItemHeights = new SparseIntArray();

	private int getScrollY() {
		if (mListView.getChildAt(1) == null) {
			return 0;
		}
	    View c = mListView.getChildAt(0); 
	    int scrollY = -c.getTop();
	    listViewItemHeights.put(mListView.getFirstVisiblePosition(), c.getHeight());
	    for (int i = 0; i < mListView.getFirstVisiblePosition(); ++i) {
	        if (listViewItemHeights.get(i) != 0) 
	            scrollY += listViewItemHeights.get(i); 
	    }
	    return scrollY;
	}
	
	private float lastX, lastY;
	
	private float imageABeginX, imageABeginY; 
	private float imageBBeginX, imageBBeginY;
	private float imageCBeginX, imageCBeginY;
	private float imageDBeginX, imageDBeginY;
	private float imageEBeginX, imageEBeginY;
	
	private float imageAEndX, imageAEndY; 
	private float imageBEndX, imageBEndY;
	private float imageCEndX, imageCEndY;
	private float imageDEndX, imageDEndY;
	private float imageEEndX, imageEEndY;
	
	/**
	 * index is consistent with  mIndexLable member variable of {@link MenuLayout}
	 */
	private float[]  beginCoordinateXs,beginCoordinateYs, endCoordinateXs, endCoordinateYs;
	private float yOffset = 2f;
	
	void calCoordinate(){
		float iconSize = imageA.getMeasuredWidth();
		float width = mMenuParentView.getWidth();
		float gap = (width - 5 * iconSize) / 4;
		imageBBeginX =  iconSize + gap;
		imageCBeginX = imageBBeginX * 2;
		imageDBeginX = imageBBeginX * 3;
		float radius = imageCBeginX;
		imageABeginX = (float) (imageCBeginX - radius * Math.cos(20*Math.PI/180));
		imageEBeginX =  (float) (imageCBeginX + radius * Math.cos(20*Math.PI/180));
		
		imageABeginY = (float) (radius * Math.sin(20*Math.PI/180));
		imageEBeginY = imageDBeginY = imageCBeginY = imageBBeginY = imageABeginY;
		
		
		imageAEndX = imageABeginX;
		imageAEndY = imageABeginY;
		imageEEndY = imageEBeginY;
		imageBEndX = (float) (imageCBeginX  - radius * Math.cos(55*Math.PI/180));
		imageBEndY = (float)(radius * Math.sin(55*Math.PI/180)) - yOffset;
		
		
		imageCEndX = imageCBeginX;
		imageCEndY = radius -  yOffset;
		imageDEndX = (float) (imageCBeginX + radius *   Math.cos(55*Math.PI/180));
		imageDEndY = imageBEndY;
		imageEEndX = imageEBeginX;
		
		float offsetY = (float) (radius * Math.sin(20*Math.PI/180));
		imageABeginX = 0;
		imageABeginY = 0;
		imageAEndX = 0;
		imageAEndY = 0;
		imageEBeginY = 0;
		imageEEndY = 0;
		
		imageBBeginY = 0;
		imageBEndY -= offsetY;
		imageCBeginY = 0;
		imageCEndY -= offsetY;
		imageDBeginY = 0;
		imageDEndY -= offsetY;
		
		
		beginCoordinateXs = new float[]{imageABeginX,imageEBeginX,imageDBeginX,imageCBeginX, imageBBeginX};
		beginCoordinateYs = new float[]{imageABeginY, imageEBeginY ,imageDBeginY,imageCBeginY ,imageBBeginY};
		endCoordinateXs = new float[]{imageAEndX,imageEEndX,imageDEndX,imageCEndX, imageBEndX};
		endCoordinateYs = new float[]{imageAEndY, imageEEndY ,imageDEndY,imageCEndY ,imageBEndY};
		
		ObjectAnimator imageA_translate_x = ObjectAnimator.ofFloat(imageA, "x", imageCBeginX, 0);
		ObjectAnimator imageB_translate_x = ObjectAnimator.ofFloat(imageB, "x", imageCBeginX, imageBEndX);
		ObjectAnimator imageB_translate_y = ObjectAnimator.ofFloat(imageB, "y", 0, imageBEndY);
		ObjectAnimator imageC_translate_y = ObjectAnimator.ofFloat(imageC, "y", 0, imageCEndY);
		ObjectAnimator imageC_scale_x= ObjectAnimator.ofFloat(imageC, "scaleX", mScaleValue);
		ObjectAnimator imageC_scale_y= ObjectAnimator.ofFloat(imageC, "scaleY", mScaleValue);
		ObjectAnimator imageD_translate_x = ObjectAnimator.ofFloat(imageD, "x", imageCBeginX, imageDEndX);
		ObjectAnimator imageD_translate_y = ObjectAnimator.ofFloat(imageD, "y", 0, imageDEndY);
		ObjectAnimator imageE_translate_x = ObjectAnimator.ofFloat(imageE, "x", imageCBeginX, imageEBeginX);
		
		ObjectAnimator headerAlpha = ObjectAnimator.ofFloat(mMenuParentView, "alpha", 0, 0.65f);
		headerAlpha.setDuration(1200);
		
		
		AnimatorSet translateSet = new AnimatorSet();
		translateSet.playTogether(imageA_translate_x, imageB_translate_x, imageB_translate_y,
				imageC_translate_y, imageC_scale_x, imageC_scale_y,
				imageD_translate_x, imageD_translate_y,
				imageE_translate_x);
		translateSet.setDuration(1000);
		AnimatorSet set = new AnimatorSet();
		set.play(translateSet).with(headerAlpha);
		headerAlpha.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				imageA.updateContent("Offer");
				imageB.updateContent("Target");
				imageC.updateContent("Progress");
				imageD.updateContent("Profile");
				imageE.updateContent("Account");
				ViewPropertyAnimator amimator = mMenuParentView.animate().alpha(1).setDuration(400);
				amimator.start();
				amimator.setListener(new AnimatorListener() {
					
					@Override
					public void onAnimationStart(Animator animation) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animator animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animator animation) {
					}
					
					@Override
					public void onAnimationCancel(Animator animation) {
						
						
					}
				});
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});
		
		set.start();
	}
	
	@Override
	public void onClick(View v) {
		MenuLayout menuLayout = (MenuLayout) v;
		if (!mExpandFlag) {
			Toast.makeText(getActivity(), menuLayout.getTextView().getText(), Toast.LENGTH_SHORT).show();
			return;
		}
		switch (menuLayout.getIndexLable()) {
		case 0:
			moveLeftAnim2();
			break;
		case 1:
			moveRightAnim2();
			break;
		case 2:
			moveRightAnim();
			break;
		case 3:
			Toast.makeText(getActivity(), menuLayout.getTextView().getText(), Toast.LENGTH_SHORT).show();
			break;
		case 4:
			moveLeftAnim();
			break;
		default:
			break;
		}
		
	}
	
	
	/**
	 * click menu, move to right,  clockwise direction.
	 */
	public void moveRightAnim(){
		PropertyValuesHolder imageA_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageE.getIndexLable()]);
	    PropertyValuesHolder imageA_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageE.getIndexLable()]);
	    ObjectAnimator imageAAnimator = null; 
		if (imageA.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else if(imageA.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else{
			imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y);
		}
		
		PropertyValuesHolder imageB_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageA.getIndexLable()]);
	    PropertyValuesHolder imageB_translate_y = PropertyValuesHolder.ofFloat("y",endCoordinateYs[imageA.getIndexLable()]);
	    ObjectAnimator imageBAnimator = null; 
		if (imageB.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else if(imageB.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else{
			imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y);
		}
		
		PropertyValuesHolder imageC_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageB.getIndexLable()]);
	    PropertyValuesHolder imageC_translate_y = PropertyValuesHolder.ofFloat("y",endCoordinateYs[imageB.getIndexLable()]);
	    ObjectAnimator imageCAnimator = null; 
		if (imageC.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else if(imageC.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else{
			imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y);
		}
		
		PropertyValuesHolder imageD_translate_x = PropertyValuesHolder.ofFloat("x" ,endCoordinateXs[imageC.getIndexLable()]);
	    PropertyValuesHolder imageD_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageC.getIndexLable()]);
	    ObjectAnimator imageDAnimator = null; 
		if (imageD.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else if(imageD.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else{
			imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y);
		}
		
		PropertyValuesHolder imageE_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageD.getIndexLable()]);
	    PropertyValuesHolder imageE_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageD.getIndexLable()]);
	    ObjectAnimator imageEAnimator = null; 
		if (imageE.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else if(imageE.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else{
			imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y);
		}
		
		setRightIndex();
		AnimatorSet set = new AnimatorSet();
		set.setDuration(mDuration);
		set.playTogether(imageAAnimator, imageBAnimator, imageCAnimator, imageDAnimator, imageEAnimator);
		set.start();
		
	}
	
	
	/**
	 * move right twice
	 */
	public void moveRightAnim2(){
		PropertyValuesHolder imageA_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageE.getIndexLable()]);
	    PropertyValuesHolder imageA_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageE.getIndexLable()]);
	    ObjectAnimator imageAAnimator = null; 
		if (imageA.getIndexLable()  == 3) {
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else if(imageA.getIndexLable() == 2){
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else{
			imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y);
		}
		
		
		PropertyValuesHolder imageB_translate_x = PropertyValuesHolder.ofFloat("x" , endCoordinateXs[imageA.getIndexLable()]);
	    PropertyValuesHolder imageB_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageA.getIndexLable()]);
	    ObjectAnimator imageBAnimator = null; 
		if (imageB.getIndexLable() == 3) {
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else if(imageB.getIndexLable() == 2){
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else{
			imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y);
		}
		
		PropertyValuesHolder imageC_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageB.getIndexLable()]);
	    PropertyValuesHolder imageC_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageB.getIndexLable()]);
	    ObjectAnimator imageCAnimator = null; 
		if (imageC.getIndexLable()  == 3) {
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else if(imageC.getIndexLable()  == 2){
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else{
			imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y);
		}
		
		PropertyValuesHolder imageD_translate_x = PropertyValuesHolder.ofFloat("x" ,endCoordinateXs[imageC.getIndexLable()]);
	    PropertyValuesHolder imageD_translate_y = PropertyValuesHolder.ofFloat("y",endCoordinateYs[imageC.getIndexLable()]);
	    ObjectAnimator imageDAnimator = null; 
		if (imageD.getIndexLable() == 3) {
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else if(imageD.getIndexLable()  == 2){
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else{
			imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y);
		}
		
		PropertyValuesHolder imageE_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageD.getIndexLable()]);
	    PropertyValuesHolder imageE_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageD.getIndexLable()]);
	    ObjectAnimator imageEAnimator = null; 
		if (imageE.getIndexLable() == 3) {
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else if(imageE.getIndexLable()  == 2){
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else{
			imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y);
		}
		
		setRightIndex();
		AnimatorSet set = new AnimatorSet();
		set.setDuration(mDuration);
		set.playTogether(imageAAnimator, imageBAnimator, imageCAnimator, imageDAnimator, imageEAnimator);
		set.start();
		
		set.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				
				moveRightAnim();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});
	}
	private  ObjectAnimator imageAAnimToRight ; 
	private  ObjectAnimator imageBAnimToRight ; 
	private ObjectAnimator imageCAnimToRight ; 
	private ObjectAnimator imageDAnimToRight ; 
	private  ObjectAnimator imageEAnimToRight ; 
	
	/**
	 * when move right, init animation
	 */
	public void initMoveRightAnim(){
		
		PropertyValuesHolder imageA_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageA.getIndexLable()], endCoordinateXs[imageE.getIndexLable()]);
	    PropertyValuesHolder imageA_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageA.getIndexLable()],endCoordinateYs[imageE.getIndexLable()]);
	   
		if (imageA.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue, 1);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageAAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else if(imageA.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX",1, mScaleValue);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY",1, mScaleValue);
		    imageAAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else{
			imageAAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y);
		}
		imageAAnimToRight.setDuration(mDuration);
		
		
		PropertyValuesHolder imageB_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageB.getIndexLable()],  endCoordinateXs[imageA.getIndexLable()]);
	    PropertyValuesHolder imageB_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageB.getIndexLable()],endCoordinateYs[imageA.getIndexLable()]);
	   
		if (imageB.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue, 1);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageBAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else if(imageB.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1,mScaleValue);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY",1, mScaleValue);
		    imageBAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else{
			imageBAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y);
		}
		imageBAnimToRight.setDuration(mDuration);
		
		PropertyValuesHolder imageC_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageC.getIndexLable()],endCoordinateXs[imageB.getIndexLable()]);
	    PropertyValuesHolder imageC_translate_y = PropertyValuesHolder.ofFloat("y",endCoordinateYs[imageC.getIndexLable()], endCoordinateYs[imageB.getIndexLable()]);
		if (imageC.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue, 1);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageCAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else if(imageC.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX",1, mScaleValue);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY",1, mScaleValue);
		    imageCAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else{
			imageCAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y);
		}
		imageCAnimToRight.setDuration(mDuration);
		
		PropertyValuesHolder imageD_translate_x = PropertyValuesHolder.ofFloat("x" , endCoordinateXs[imageD.getIndexLable()],endCoordinateXs[imageC.getIndexLable()]);
	    PropertyValuesHolder imageD_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageD.getIndexLable()],endCoordinateYs[imageC.getIndexLable()]);
		if (imageD.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue,1);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageDAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else if(imageD.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1, mScaleValue);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY",1, mScaleValue);
		    imageDAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else{
			imageDAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y);
		}
		imageDAnimToRight.setDuration(mDuration);
		
		PropertyValuesHolder imageE_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageE.getIndexLable()], endCoordinateXs[imageD.getIndexLable()]);
	    PropertyValuesHolder imageE_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageE.getIndexLable()],endCoordinateYs[imageD.getIndexLable()]);
	   
		if (imageE.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue, 1);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageEAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else if(imageE.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX",1, mScaleValue);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1, mScaleValue);
		    imageEAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else{
			imageEAnimToRight =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y);
		}
		imageEAnimToRight.setDuration(mDuration);
	}
	
	/**
	 * when move right, finger move up, finish animation
	 */
	public void finishMoveRightAnim(){
		if (!mExpandFlag) {
			return ;
		}
		ObjectAnimator imageAAnimator = null;
		PropertyValuesHolder imageA_translate_x = PropertyValuesHolder.ofFloat("x",  endCoordinateXs[imageE.getIndexLable()]);
	    PropertyValuesHolder imageA_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageE.getIndexLable()]);
		if (imageA.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else if(imageA.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else{
			imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y);
		}
		
		ObjectAnimator imageBAnimator = null;
		PropertyValuesHolder imageB_translate_x = PropertyValuesHolder.ofFloat("x",  endCoordinateXs[imageA.getIndexLable()]);
	    PropertyValuesHolder imageB_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageA.getIndexLable()]);
	   
		if (imageB.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else if(imageB.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else{
			imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y);
		}
		
		ObjectAnimator imageCAnimator = null;
		PropertyValuesHolder imageC_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageB.getIndexLable()]);
	    PropertyValuesHolder imageC_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageB.getIndexLable()]);
		if (imageC.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else if(imageC.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else{
			imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y);
		}
		
		ObjectAnimator imageDAnimator = null;
		PropertyValuesHolder imageD_translate_x = PropertyValuesHolder.ofFloat("x" , endCoordinateXs[imageC.getIndexLable()]);
	    PropertyValuesHolder imageD_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageC.getIndexLable()]);
		if (imageD.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else if(imageD.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else{
			imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y);
		}
		
		ObjectAnimator imageEAnimator = null;
		PropertyValuesHolder imageE_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageD.getIndexLable()]);
	    PropertyValuesHolder imageE_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageD.getIndexLable()]);
		if (imageE.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else if(imageE.getIndexLable() % 5 == 2){
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else{
			imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y);
		}
		
		setRightIndex();
		AnimatorSet set = new AnimatorSet();
		set.playTogether(imageAAnimator, imageBAnimator, imageCAnimator, imageDAnimator, imageEAnimator);
		set.setDuration(mDuration);
		set.start();
		

		
	}

	/**
	 * when move right, update mIndexLable value of {@link MenuLayout}
	 */
	private void setRightIndex() {
		imageA.setIndexLable(imageA.getIndexLable() + 1);
		imageB.setIndexLable(imageB.getIndexLable() + 1);
		imageC.setIndexLable(imageC.getIndexLable() + 1);
		imageD.setIndexLable(imageD.getIndexLable() + 1);
		imageE.setIndexLable(imageE.getIndexLable() + 1);
		
//		mCenterImageView.update(0, true);
	}
	
	/**
	 * when move right, update animation progress
	 * @param playTime
	 */
	public void updateMoveRightAnim(long playTime){
		imageAAnimToRight.setCurrentPlayTime(playTime);
		imageBAnimToRight.setCurrentPlayTime(playTime);
		imageCAnimToRight.setCurrentPlayTime(playTime);
		imageDAnimToRight.setCurrentPlayTime(playTime);
		imageEAnimToRight.setCurrentPlayTime(playTime);
		mCenterImageView.update(-mCenterImageView.getMeasuredWidth()*(1 - playTime* 1.0f/mDuration ), true);
	}
	
	/**
	 * click menu, move to left, counterclockwise direction.
	 */
	public void moveLeftAnim(){
		PropertyValuesHolder imageA_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageB.getIndexLable()]);
	    PropertyValuesHolder imageA_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageB.getIndexLable()]);
	    ObjectAnimator imageAAnimator = null; 
		if (imageA.getIndexLable()  == 3) {
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else if(imageA.getIndexLable() == 4){
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else{
			imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y);
		}
		
		
		PropertyValuesHolder imageB_translate_x = PropertyValuesHolder.ofFloat("x" , endCoordinateXs[imageC.getIndexLable()]);
	    PropertyValuesHolder imageB_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageC.getIndexLable()]);
	    ObjectAnimator imageBAnimator = null; 
		if (imageB.getIndexLable() == 3) {
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else if(imageB.getIndexLable() == 4){
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else{
			imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y);
		}
		
		PropertyValuesHolder imageC_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageD.getIndexLable()]);
	    PropertyValuesHolder imageC_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageD.getIndexLable()]);
	    ObjectAnimator imageCAnimator = null; 
		if (imageC.getIndexLable()  == 3) {
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else if(imageC.getIndexLable()  == 4){
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else{
			imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y);
		}
		
		PropertyValuesHolder imageD_translate_x = PropertyValuesHolder.ofFloat("x" ,endCoordinateXs[imageE.getIndexLable()]);
	    PropertyValuesHolder imageD_translate_y = PropertyValuesHolder.ofFloat("y",endCoordinateYs[imageE.getIndexLable()]);
	    ObjectAnimator imageDAnimator = null; 
		if (imageD.getIndexLable() == 3) {
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else if(imageD.getIndexLable()  == 4){
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else{
			imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y);
		}
		
		PropertyValuesHolder imageE_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageA.getIndexLable()]);
	    PropertyValuesHolder imageE_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageA.getIndexLable()]);
	    ObjectAnimator imageEAnimator = null; 
		if (imageE.getIndexLable() == 3) {
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else if(imageE.getIndexLable()  == 4){
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else{
			imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y);
		}
		
		setLeftIndex();
		AnimatorSet set = new AnimatorSet();
		set.setDuration(mDuration);
		set.playTogether(imageAAnimator, imageBAnimator, imageCAnimator, imageDAnimator, imageEAnimator);
		set.start();
		
	}

	/**
	 * when move left, update mIndexLable value of {@link MenuLayout}
	 */
	private void setLeftIndex() {
		imageA.setIndexLable(imageA.getIndexLable() - 1);
		imageB.setIndexLable(imageB.getIndexLable() - 1);
		imageC.setIndexLable(imageC.getIndexLable() - 1);
		imageD.setIndexLable(imageD.getIndexLable() - 1);
		imageE.setIndexLable(imageE.getIndexLable() - 1);
//		mCenterImageView.update(0, false);
	}
	
	/**
	 * move left twice
	 */
	public void moveLeftAnim2(){
		PropertyValuesHolder imageA_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageB.getIndexLable()]);
	    PropertyValuesHolder imageA_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageB.getIndexLable()]);
	    ObjectAnimator imageAAnimator = null; 
		if (imageA.getIndexLable()  == 3) {
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else if(imageA.getIndexLable() == 4){
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else{
			imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y);
		}
		
		
		PropertyValuesHolder imageB_translate_x = PropertyValuesHolder.ofFloat("x" , endCoordinateXs[imageC.getIndexLable()]);
	    PropertyValuesHolder imageB_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageC.getIndexLable()]);
	    ObjectAnimator imageBAnimator = null; 
		if (imageB.getIndexLable() == 3) {
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else if(imageB.getIndexLable() == 4){
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else{
			imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y);
		}
		
		PropertyValuesHolder imageC_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageD.getIndexLable()]);
	    PropertyValuesHolder imageC_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageD.getIndexLable()]);
	    ObjectAnimator imageCAnimator = null; 
		if (imageC.getIndexLable()  == 3) {
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else if(imageC.getIndexLable()  == 4){
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else{
			imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y);
		}
		
		PropertyValuesHolder imageD_translate_x = PropertyValuesHolder.ofFloat("x" ,endCoordinateXs[imageE.getIndexLable()]);
	    PropertyValuesHolder imageD_translate_y = PropertyValuesHolder.ofFloat("y",endCoordinateYs[imageE.getIndexLable()]);
	    ObjectAnimator imageDAnimator = null; 
		if (imageD.getIndexLable() == 3) {
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else if(imageD.getIndexLable()  == 4){
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else{
			imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y);
		}
		
		PropertyValuesHolder imageE_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageA.getIndexLable()]);
	    PropertyValuesHolder imageE_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageA.getIndexLable()]);
	    ObjectAnimator imageEAnimator = null; 
		if (imageE.getIndexLable() == 3) {
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else if(imageE.getIndexLable()  == 4){
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else{
			imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y);
		}
		
		setLeftIndex();
		AnimatorSet set = new AnimatorSet();
		set.setDuration(mDuration);
		set.playTogether(imageAAnimator, imageBAnimator, imageCAnimator, imageDAnimator, imageEAnimator);
		set.start();
		
		set.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				
				moveLeftAnim();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});
	}
	
	private ObjectAnimator imageAAnimToLeft ; 
	private ObjectAnimator imageBAnimToLeft ; 
	private ObjectAnimator imageCAnimToLeft ; 
	private ObjectAnimator imageDAnimToLeft ; 
	private ObjectAnimator imageEAnimToLeft ; 

	/**
	 * when move left, init animation
	 */
	public void initMoveLeftAnim(){
		PropertyValuesHolder imageA_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageA.getIndexLable()], endCoordinateXs[imageB.getIndexLable()]);
	    PropertyValuesHolder imageA_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageA.getIndexLable()],endCoordinateYs[imageB.getIndexLable()]);
		if (imageA.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue, 1);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageAAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else if(imageA.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX",1, mScaleValue);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY",1, mScaleValue);
		    imageAAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else{
			imageAAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y);
		}
		imageAAnimToLeft.setDuration(mDuration);
		
		
		PropertyValuesHolder imageB_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageB.getIndexLable()],  endCoordinateXs[imageC.getIndexLable()]);
	    PropertyValuesHolder imageB_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageB.getIndexLable()],endCoordinateYs[imageC.getIndexLable()]);
	   
		if (imageB.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue, 1);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageBAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else if(imageB.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1,mScaleValue);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY",1, mScaleValue);
		    imageBAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else{
			imageBAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y);
		}
		imageBAnimToLeft.setDuration(mDuration);
		
		PropertyValuesHolder imageC_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageC.getIndexLable()],endCoordinateXs[imageD.getIndexLable()]);
	    PropertyValuesHolder imageC_translate_y = PropertyValuesHolder.ofFloat("y",endCoordinateYs[imageC.getIndexLable()], endCoordinateYs[imageD.getIndexLable()]);
		if (imageC.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue, 1);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageCAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else if(imageC.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX",1, mScaleValue);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY",1, mScaleValue);
		    imageCAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else{
			imageCAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y);
		}
		imageCAnimToLeft.setDuration(mDuration);
		
		PropertyValuesHolder imageD_translate_x = PropertyValuesHolder.ofFloat("x" , endCoordinateXs[imageD.getIndexLable()],endCoordinateXs[imageE.getIndexLable()]);
	    PropertyValuesHolder imageD_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageD.getIndexLable()],endCoordinateYs[imageE.getIndexLable()]);
		if (imageD.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue,1);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageDAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else if(imageD.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1, mScaleValue);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY",1, mScaleValue);
		    imageDAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else{
			imageDAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y);
		}
		imageDAnimToLeft.setDuration(mDuration);
		
		PropertyValuesHolder imageE_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageE.getIndexLable()], endCoordinateXs[imageA.getIndexLable()]);
	    PropertyValuesHolder imageE_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageE.getIndexLable()],endCoordinateYs[imageA.getIndexLable()]);
	   
		if (imageE.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue, 1);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageEAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else if(imageE.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX",1, mScaleValue);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1, mScaleValue);
		    imageEAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else{
			imageEAnimToLeft =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y);
		}
		imageEAnimToLeft.setDuration(mDuration);
		
	}
	
	/**
	 * when move left, update animation progress
	 * @param playTime
	 */
	private void updateMoveLeftAnim(long playTime){
		imageAAnimToLeft.setCurrentPlayTime(playTime); 
		imageBAnimToLeft.setCurrentPlayTime(playTime);
		imageCAnimToLeft.setCurrentPlayTime(playTime);
		imageDAnimToLeft.setCurrentPlayTime(playTime);
		imageEAnimToLeft.setCurrentPlayTime(playTime);
		
		System.out.println("drawcircle----= " + -mCenterImageView.getMeasuredWidth()*(1 - playTime* 1.0f/mDuration ));
		mCenterImageView.update(mCenterImageView.getMeasuredWidth()*(1 - playTime* 1.0f/mDuration ), false);
	}
	
	
	/**
	 * when move left, finger move up, finish animation
	 */
	public void finishMoveLeftAnim(){
		if (!mExpandFlag) {
			return;
		}
		ObjectAnimator imageAAnimator = null;
		PropertyValuesHolder imageA_translate_x = PropertyValuesHolder.ofFloat("x",  endCoordinateXs[imageB.getIndexLable()]);
	    PropertyValuesHolder imageA_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageB.getIndexLable()]);
	   
		if (imageA.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else if(imageA.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else{
			imageAAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y);
		}
		
		ObjectAnimator imageBAnimator = null;
		PropertyValuesHolder imageB_translate_x = PropertyValuesHolder.ofFloat("x",  endCoordinateXs[imageC.getIndexLable()]);
	    PropertyValuesHolder imageB_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageC.getIndexLable()]);
		if (imageB.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else if(imageB.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else{
			imageBAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y);
		}
		
		ObjectAnimator imageCAnimator = null;
		PropertyValuesHolder imageC_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageD.getIndexLable()]);
	    PropertyValuesHolder imageC_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageD.getIndexLable()]);
		if (imageC.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else if(imageC.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else{
			imageCAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y);
		}
		imageCAnimator.setDuration(mDuration);
		
		ObjectAnimator imageDAnimator = null;
		PropertyValuesHolder imageD_translate_x = PropertyValuesHolder.ofFloat("x" , endCoordinateXs[imageE.getIndexLable()]);
	    PropertyValuesHolder imageD_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageE.getIndexLable()]);
		if (imageD.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else if(imageD.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else{
			imageDAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y);
		}
		
		ObjectAnimator imageEAnimator = null;
		PropertyValuesHolder imageE_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageA.getIndexLable()]);
	    PropertyValuesHolder imageE_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageA.getIndexLable()]);
		if (imageE.getIndexLable() % 5 == 3) {
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", 1);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", 1);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else if(imageE.getIndexLable() % 5 == 4){
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY", mScaleValue);
		    imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else{
			imageEAnimator =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y);
		}
		
		setLeftIndex();
		AnimatorSet set = new AnimatorSet();
		set.playTogether(imageAAnimator, imageBAnimator, imageCAnimator, imageDAnimator, imageEAnimator);
		set.setDuration(mDuration);
		set.start();
	}
	
	
	
	private ObjectAnimator imageATranslationYAnim ; 
	private ObjectAnimator imageBTranslationYAnim ; 
	private ObjectAnimator imageCTranslationYAnim ; 
	private ObjectAnimator imageDTranslationYAnim ; 
	private ObjectAnimator imageETranslationYAnim ; 
	
	
	private ObjectAnimator viewPagerTranslationYAnim ; 
	private ObjectAnimator layoutFrameTranslationYAnim ; 
	/**
	 *  when scroll {@link #mListView} up and down, init animation
	 */
	public void initTranslationYAnim(){
		
		PropertyValuesHolder imageA_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageA.getIndexLable()], beginCoordinateXs[imageA.getIndexLable()]);
	    PropertyValuesHolder imageA_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageA.getIndexLable()],0);
	    
		if (imageA.getIndexLable()  == 3) {
			PropertyValuesHolder imageA_scale_x = PropertyValuesHolder.ofFloat("scaleX", mScaleValue, 1);
		    PropertyValuesHolder imageA_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageATranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y, imageA_scale_x, imageA_scale_y);
		}else{
			imageATranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageA, imageA_translate_x, imageA_translate_y);
		}
		imageATranslationYAnim.setDuration(mDuration);
		imageATranslationYAnim.setInterpolator(new LinearInterpolator());
		
		
		PropertyValuesHolder imageB_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageB.getIndexLable()],  beginCoordinateXs[imageB.getIndexLable()]);
	    PropertyValuesHolder imageB_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageB.getIndexLable()],0);
	   
		if (imageB.getIndexLable()  == 3) {
			PropertyValuesHolder imageB_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue, 1);
		    PropertyValuesHolder imageB_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageBTranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y, imageB_scale_x, imageB_scale_y);
		}else{
			imageBTranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageB, imageB_translate_x, imageB_translate_y);
		}
		imageBTranslationYAnim.setDuration(mDuration);
		imageBTranslationYAnim.setInterpolator(new LinearInterpolator());
		
		PropertyValuesHolder imageC_translate_x = PropertyValuesHolder.ofFloat("x", endCoordinateXs[imageC.getIndexLable()],beginCoordinateXs[imageC.getIndexLable()]);
	    PropertyValuesHolder imageC_translate_y = PropertyValuesHolder.ofFloat("y",endCoordinateYs[imageC.getIndexLable()], 0);
		if (imageC.getIndexLable() == 3) {
			PropertyValuesHolder imageC_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue, 1);
		    PropertyValuesHolder imageC_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageCTranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y, imageC_scale_x, imageC_scale_y);
		}else{
			imageCTranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageC, imageC_translate_x, imageC_translate_y);
		}
		imageCTranslationYAnim.setDuration(mDuration);
		imageCTranslationYAnim.setInterpolator(new LinearInterpolator());
		
		PropertyValuesHolder imageD_translate_x = PropertyValuesHolder.ofFloat("x" , endCoordinateXs[imageD.getIndexLable()],beginCoordinateXs[imageD.getIndexLable()]);
	    PropertyValuesHolder imageD_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageD.getIndexLable()],0);
		if (imageD.getIndexLable()  == 3) {
			PropertyValuesHolder imageD_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue,1);
		    PropertyValuesHolder imageD_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageDTranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y, imageD_scale_x, imageD_scale_y);
		}else{
			imageDTranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageD, imageD_translate_x, imageD_translate_y);
		}
		imageDTranslationYAnim.setDuration(mDuration);
		imageDTranslationYAnim.setInterpolator(new LinearInterpolator());
		
		PropertyValuesHolder imageE_translate_x = PropertyValuesHolder.ofFloat("x",endCoordinateXs[imageE.getIndexLable()], beginCoordinateXs[imageE.getIndexLable()]);
	    PropertyValuesHolder imageE_translate_y = PropertyValuesHolder.ofFloat("y", endCoordinateYs[imageE.getIndexLable()],0);
	   
		if (imageE.getIndexLable()  == 3) {
			PropertyValuesHolder imageE_scale_x = PropertyValuesHolder.ofFloat("scaleX",mScaleValue, 1);
		    PropertyValuesHolder imageE_scale_y = PropertyValuesHolder.ofFloat("scaleY",mScaleValue, 1);
		    imageETranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y, imageE_scale_x, imageE_scale_y);
		}else{
			imageETranslationYAnim =  ObjectAnimator.ofPropertyValuesHolder(imageE, imageE_translate_x, imageE_translate_y);
		}
		imageETranslationYAnim.setDuration(mDuration);
		imageETranslationYAnim.setInterpolator(new LinearInterpolator());
		
		
		layoutFrameTranslationYAnim = ObjectAnimator.ofFloat(mLayoutFrame, "y",/*headerLayout.getY()*/layoutFrameBeginY, layoutFrameEndY).setDuration(mDuration);
		layoutFrameTranslationYAnim.setInterpolator(new LinearInterpolator());
		
		viewPagerTranslationYAnim = ObjectAnimator.ofFloat(mViewPager, "y", mViewPagerBeginY , mViewPagerEndY).setDuration(mDuration);
		viewPagerTranslationYAnim.setInterpolator(new LinearInterpolator());
	}
	
	/**
	 * when scroll {@link #mListView} up and down, update animation progress
	 * @param playTime
	 */
	public void updateTranslationYAnim(long playTime){
		imageCircleAnim.setCurrentPlayTime(playTime);
		imageATranslationYAnim.setCurrentPlayTime(playTime);
		imageBTranslationYAnim.setCurrentPlayTime(playTime); 
		imageCTranslationYAnim.setCurrentPlayTime(playTime);
		imageDTranslationYAnim.setCurrentPlayTime(playTime);
		imageETranslationYAnim.setCurrentPlayTime(playTime);
		layoutFrameTranslationYAnim.setCurrentPlayTime(playTime);
		viewPagerTranslationYAnim.setCurrentPlayTime(playTime );
	}

	@Override
	public void onUpcomingLoaded(List<Cat_Model> listMovies) {
		Picasso picasso = Picasso.with(getContext());
		//MoviesAdapter mAdapter = new MoviesAdapter(listMovies, picasso, (Activity) getContext());
		Best_Deal_Adapter mAdapter=new Best_Deal_Adapter(listMovies,picasso,(Activity) getContext());
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onUpcomingLoaded(List<Cat_Model> listMovies, String poss) {

	}

	@Override
	public void onUpcomingcourses(List<Common_Pojo> common_pojos) {

	}

	@Override
	public void onUpcomingexams(List<Common_Pojo> common_pojos) {

	}
}