package com.cn.washoes.activity;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cn.hongwei.CarImageView;
import com.cn.hongwei.CarouselAdapter;
import com.cn.washoes.R;
import com.cn.washoes.model.ImgInfo;

public class ImageShowActivity extends Activity {

	//private RadioGroup radioGroup;// 轮播图标
	private ViewPager viewPager;// 轮播容器
	private ArrayList<View> carouseImageViews = new ArrayList<View>();// 轮播数据源

	// private List<Banner> banners;// 轮播数据源
	private List<ImgInfo> images;// 服务前照片列表

	private int index = 0;
	
	private TextView textCurrentImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.img_show_layout);
		images = (List<ImgInfo>) getIntent().getSerializableExtra("images");
		index = getIntent().getIntExtra("index", 0);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		//radioGroup = (RadioGroup) findViewById(R.id.viewpager_radiogroup);
		textCurrentImg =  (TextView) findViewById(R.id.viewpager_text);
		initViewPager();
	}

	/**
	 * 初始化轮播组件
	 */
	private void initViewPager() {
		boolean repat = true;
		for (int i = 0; i < images.size(); i++) {
			CarImageView imageView = new CarImageView(this);
			final String url = images.get(i).getFile_path();
			imageView.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ScaleType.FIT_CENTER);
			imageView.setBackgroundColor(color.black);
			imageView.loadImage(url);

			carouseImageViews.add(imageView);
			/*if (images.size() == 2 && i == 1 && repat) {
				i = -1;
				repat = false;
			}*/
		}
		CarouselAdapter homeAdapter = new CarouselAdapter(this);
		homeAdapter.setArrayList(carouseImageViews);
		viewPager.setAdapter(homeAdapter);

/*		for (int i = 0; i < images.size(); i++) {
			RadioButton rb = (RadioButton) getLayoutInflater().inflate(
					R.layout.homepage_radio_item, radioGroup, false);
			rb.setId(i);
			radioGroup.addView(rb);
			rb.setChecked(i == 0);
		}*/
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
//				((RadioButton) radioGroup.getChildAt(arg0
//						% radioGroup.getChildCount())).setChecked(true);
				textCurrentImg.setText((arg0%images.size()+1)+"/"+images.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		viewPager.setCurrentItem(index);

	}
}
