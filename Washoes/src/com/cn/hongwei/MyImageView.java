package com.cn.hongwei;

import com.cn.washoes.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ListView;

public class MyImageView extends ImageView {

	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyImageView(Context context) {
		super(context);
	}

	@SuppressWarnings("unused")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// For simple implementation, or internal size is always 0.
		// We depend on the container to specify the layout size of
		// our view. We can't really know what it is since we will be
		// adding and removing different arbitrary views and do not
		// want the layout to change as this happens.
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));

		// Children are just made to fill our space.
		int childWidthSize = getMeasuredWidth();
		int childHeightSize = getMeasuredHeight();
		// 高度和宽度一样
		heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(
				childWidthSize, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	
	
	/**
	 * 用于记录默认下载中状态的图片
	 */
	private int downLoadingImageId = 0;
	private int downLoadingImagefailureId = 0;
	// 图片是否加载成功
	private boolean loadSuccess = false;

	private boolean isRound = false;

	/**
	 * 不设置将使用默认图片 设置下载中，与加载失败的图片,
	 * 
	 * @param downlding
	 *            加载�? * @param failureId 加载失败
	 */
	public void setDefultDownLoadAndFailureImage(int downlding, int failureId) {
		downLoadingImageId = downlding;
		downLoadingImagefailureId = failureId;
	}


	/**
	 * 对外接口，用于调用ImageView的异步下载图片功�? *
	 * 
	 * @param url
	 *            图片的URL
	 */
	public void loadImage(String url) {

		if (isRound) {
			int d = this.getWidth();
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showStubImage(downLoadingImageId)
				.showImageForEmptyUri(R.drawable.head_default)
					.cacheInMemory().cacheOnDisc()
					.showImageOnFail(R.drawable.head_default)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.displayer(new RoundedBitmapDisplayer(d / 2)).build();
			ImageLoader.getInstance().displayImage(url, this, options);
		} else {
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showStubImage(downLoadingImageId)
				.showImageForEmptyUri(R.drawable.car_default)
					.cacheInMemory().cacheOnDisc()
					.showImageOnFail(R.drawable.car_default)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
			ImageLoader.getInstance().displayImage(url, this, options);
		}

		//
		// ImageLoader.getInstance().loadImage(url, options,
		// new ImageLoadingListener() {
		//
		// @Override
		// public void onLoadingStarted(String arg0, View arg1) {
		// loadSuccess = false;
		// setImageResource(downLoadingImageId);
		// }
		//
		// @Override
		// public void onLoadingFailed(String arg0, View arg1,
		// FailReason arg2) {
		// loadSuccess = false;
		// setImageResource(downLoadingImagefailureId);
		// }
		//
		// @Override
		// public void onLoadingComplete(String arg0, View arg1,
		// Bitmap arg2) {
		//
		// if (getTag() == null || arg0.equals(getTag())) {
		// loadSuccess = true;
		// setImageBitmap(arg2);
		// }
		// }
		//
		// @Override
		// public void onLoadingCancelled(String arg0, View arg1) {
		// loadSuccess = false;
		// setImageResource(downLoadingImagefailureId);
		// }
		// });
	}

	public boolean isLoadSuccess() {
		return loadSuccess;
	}

	public boolean isRound() {
		return isRound;
	}

	public void setRound(boolean isRound) {
		this.isRound = isRound;
	}
	
}