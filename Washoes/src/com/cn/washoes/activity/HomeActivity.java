package com.cn.washoes.activity;

import android.os.Bundle;
import android.view.View;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;

public class HomeActivity extends BaseActivity {
	
	private TopTitleView topTitleView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("首页");
		topTitleView.setBackImageViewVisable(View.GONE);
	}
}
