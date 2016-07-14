package com.brilyong.technology.activity;

import com.brilyong.technology.httputils.ScreenUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView
{

	private int mScreenWidth;
	/**
	 * dp
	 */
	private int mMenuRightPadding;
	/**
	 * 
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;

	private boolean isOpen;

	private boolean once;

	public SlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);

	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{	
		
		super(context, attrs, defStyle);
		
		mScreenWidth = ScreenUtils.getScreenWidth(context);
		int[] d = {0x7f010000};
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				d, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case 0:
				
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));
				break;
			}
		}
		a.recycle();
	}

	public SlidingMenu(Context context)
	{
		this(context, null, 0);
	}



	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		/**
		 * 鏄剧ず鐨勮缃竴涓锟�
		 */
		if (!once)
		{
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
			ViewGroup content = (ViewGroup) wrapper.getChildAt(1);

			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			menu.getLayoutParams().width = mMenuWidth;
			content.getLayoutParams().width = mScreenWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			// 灏嗚彍鍗曢殣锟�
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {  
        
//        if (SlidingMenu.flag == 0) {  
//            return false;  
//        }else{  
            return false;  
//        }  
    }  
	public static int flag = -1;  
    public float Down_x = 0;  
    public float diff = 100;  
    public long  Down_t = 0;  
    public long timeout = 200;  
	 @Override
	public boolean onTouchEvent(MotionEvent ev) {  
		  
             return false;    
     }  
	/**
	 * 鎵撳紑鑿滃崟
	 */
	public void openMenu()
	{
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	/**
	 * 鍏抽棴鑿滃崟
	 */
	public void closeMenu()
	{
		if (isOpen)
		{
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
	}

	/**
	 * 鍒囨崲鑿滃崟鐘讹�?
	 */
	public void toggle()
	{
		if (isOpen)
		{
			closeMenu();
		} else
		{
			openMenu();
		}
	}

}
