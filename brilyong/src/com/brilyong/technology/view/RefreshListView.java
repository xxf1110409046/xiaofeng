package com.brilyong.technology.view;

import java.util.Date;

import com.brilyong.technology.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RefreshListView extends ListView implements OnScrollListener,
		OnClickListener {

	private View mHeadView;
	private TextView mRefreshTextview;
	private TextView mLastUpdateTextView;
	private ImageView mArrowImageView;
	private ProgressBar mHeadProgressBar;

	private int mHeadContentHeight;

	private IOnRefreshListener mOnRefreshListener;

	private boolean mIsRecord = false;
	private int mStartY = 0;
	private int mFirstItemIndex = -1;
	private int mMoveY = 0;
	private int mViewState = IListViewState.LVS_NORMAL;

	private final static int RATIO = 2;

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private boolean mBack = false;

	public RefreshListView(Context context) {

		super(context);
		init(context);
	}

	public RefreshListView(Context context, AttributeSet attrs) {

		super(context, attrs);
		init(context);
	}

	public void setOnRefreshListener(IOnRefreshListener listener) {

		mOnRefreshListener = listener;
	}

	private void onRefresh() {

		if (mOnRefreshListener != null) {
			mOnRefreshListener.OnRefresh();
		}
	}

	public void onRefreshComplete() {

		mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
		mLastUpdateTextView.setText(": " + new Date().toLocaleString());
		switchViewState(IListViewState.LVS_NORMAL);
	}

	private void init(Context context) {

		initHeadView(context);

		// initLoadMoreView(context);

		setOnScrollListener(this);
	}

	private void initHeadView(Context context) {

		mHeadView = LayoutInflater.from(context).inflate(
				R.layout.listview_head, null);

		mArrowImageView = (ImageView) mHeadView
				.findViewById(R.id.head_arrowImageView);
		mArrowImageView.setMinimumWidth(60);

		mHeadProgressBar = (ProgressBar) mHeadView
				.findViewById(R.id.head_progressBar);

		mRefreshTextview = (TextView) mHeadView
				.findViewById(R.id.head_tipsTextView);

		mLastUpdateTextView = (TextView) mHeadView
				.findViewById(R.id.head_lastUpdatedTextView);

		measureView(mHeadView);
		mHeadContentHeight = mHeadView.getMeasuredHeight();
		mHeadView.getMeasuredWidth();

		mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
		mHeadView.invalidate();

		addHeaderView(mHeadView, null, false);

		animation = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
	}

	private void measureView(View child) {

		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisiableItem,
			int visibleItemCount, int totalItemCount) {

		mFirstItemIndex = firstVisiableItem;

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (mOnRefreshListener != null) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				doActionDown(ev);
				break;
			case MotionEvent.ACTION_MOVE:
				doActionMove(ev);
				break;
			case MotionEvent.ACTION_UP:
				doActionUp(ev);
				break;
			default:
				break;
			}
		}

		return super.onTouchEvent(ev);
	}

	private void doActionDown(MotionEvent ev) {

		if (mIsRecord == false && mFirstItemIndex == 0) {
			mStartY = (int) ev.getY();
			mIsRecord = true;
		}
	}

	private void doActionMove(MotionEvent ev) {

		mMoveY = (int) ev.getY();

		if (mIsRecord == false && mFirstItemIndex == 0) {
			mStartY = (int) ev.getY();
			mIsRecord = true;
		}

		if (mIsRecord == false || mViewState == IListViewState.LVS_LOADING) {
			return;
		}

		int offset = (mMoveY - mStartY) / RATIO;

		switch (mViewState) {
		case IListViewState.LVS_NORMAL: {
			if (offset > 0) {
				mHeadView.setPadding(0, offset - mHeadContentHeight, 0, 0);
				switchViewState(IListViewState.LVS_PULL_REFRESH);
			}
		}
			break;
		case IListViewState.LVS_PULL_REFRESH: {
			setSelection(0);
			mHeadView.setPadding(0, offset - mHeadContentHeight, 0, 0);
			if (offset < 0) {
				switchViewState(IListViewState.LVS_NORMAL);
			} else if (offset > mHeadContentHeight) {
				switchViewState(IListViewState.LVS_RELEASE_REFRESH);
			}
		}
			break;
		case IListViewState.LVS_RELEASE_REFRESH: {
			setSelection(0);
			mHeadView.setPadding(0, offset - mHeadContentHeight, 0, 0);
			if (offset >= 0 && offset <= mHeadContentHeight) {
				mBack = true;
				switchViewState(IListViewState.LVS_PULL_REFRESH);
			} else if (offset < 0) {
				switchViewState(IListViewState.LVS_NORMAL);
			} else {

			}

		}
			break;
		default:
			return;
		}
		;

	}

	private void doActionUp(MotionEvent ev) {

		mIsRecord = false;
		mBack = false;

		if (mViewState == IListViewState.LVS_LOADING) {
			return;
		}

		switch (mViewState) {
		case IListViewState.LVS_NORMAL:
			break;
		case IListViewState.LVS_PULL_REFRESH:
			mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
			switchViewState(IListViewState.LVS_NORMAL);
			break;
		case IListViewState.LVS_RELEASE_REFRESH:
			mHeadView.setPadding(0, 0, 0, 0);
			switchViewState(IListViewState.LVS_LOADING);
			onRefresh();
			break;
		}

	}

	private void switchViewState(int state) {

		switch (state) {
		case IListViewState.LVS_NORMAL: {
			mArrowImageView.clearAnimation();
			mArrowImageView.setImageResource(R.drawable.arrow);
		}
			break;
		case IListViewState.LVS_PULL_REFRESH: {
			mHeadProgressBar.setVisibility(View.GONE);
			mArrowImageView.setVisibility(View.VISIBLE);
			mRefreshTextview.setText("ÏÂÀ­Ë¢ÐÂ");
			mArrowImageView.clearAnimation();

			if (mBack) {
				mBack = false;
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(reverseAnimation);
			}
		}
			break;
		case IListViewState.LVS_RELEASE_REFRESH: {
			mHeadProgressBar.setVisibility(View.GONE);
			mArrowImageView.setVisibility(View.VISIBLE);
			mRefreshTextview.setText("ËÉ¿ªË¢ÐÂ");
			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(animation);
		}
			break;
		case IListViewState.LVS_LOADING: {
			mHeadProgressBar.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.GONE);
			mRefreshTextview.setText("ÕýÔÚ¼ÓÔØ");
		}
			break;
		default:
			return;
		}

		mViewState = state;

	}

	private View mFootView;
	private View mLoadMoreView;
	private TextView mLoadMoreTextView;
	private View mLoadingView;
	private IOnLoadMoreListener mLoadMoreListener; 
	private int mLoadMoreState = IListViewState.LVS_NORMAL;

	public void setOnLoadMoreListener(IOnLoadMoreListener listener) {

		mLoadMoreListener = listener;
	}

	public void onLoadMoreComplete(boolean flag) {

		if (flag) {
			updateLoadMoreViewState(ILoadMoreViewState.LMVS_OVER);
		} else {
			updateLoadMoreViewState(ILoadMoreViewState.LMVS_NORMAL);
		}

	}

	private void updateLoadMoreViewState(int state) {

		switch (state) {
		case ILoadMoreViewState.LMVS_NORMAL:
			mLoadingView.setVisibility(View.GONE);
			mLoadMoreTextView.setVisibility(View.VISIBLE);
			break;
		case ILoadMoreViewState.LMVS_LOADING:
			mLoadingView.setVisibility(View.VISIBLE);
			mLoadMoreTextView.setVisibility(View.GONE);
			break;
		case ILoadMoreViewState.LMVS_OVER:
			mLoadingView.setVisibility(View.GONE);
			mLoadMoreTextView.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}

		mLoadMoreState = state;
	}

	public void removeFootView() {

		removeFooterView(mFootView);
	}

	 

	public interface IListViewState {

		int LVS_NORMAL = 0; // æ™®é?çŠ¶æ?
		int LVS_PULL_REFRESH = 1; // ä¸‹æ‹‰åˆ·æ–°çŠ¶æ?
		int LVS_RELEASE_REFRESH = 2; // æ¾å¼€åˆ·æ–°çŠ¶æ?
		int LVS_LOADING = 3; // åŠ è½½çŠ¶æ?
	}

	public interface IOnRefreshListener {

		void OnRefresh();
	}

	public interface ILoadMoreViewState {

		int LMVS_NORMAL = 0; // æ™®é?çŠ¶æ?
		int LMVS_LOADING = 1; // åŠ è½½çŠ¶æ?
		int LMVS_OVER = 2; // ç»“æŸçŠ¶æ?
	}

	public interface IOnLoadMoreListener {

		void OnLoadMore();
	}

	@Override
	public void onClick(View v) {


	}

}
