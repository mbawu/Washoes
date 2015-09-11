package com.cn.washoes.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;

/**
 * 个人中心页面
 * 
 * @author Wu Jiang
 * 
 */
public class PersonActivity extends BaseActivity implements OnClickListener {

	private TopTitleView topTitleView;//标题栏
	private FrameLayout timeLayout; // 我的时间
	private FrameLayout msgLayout; // 我的消息
	private FrameLayout changeLayout; // 修改手机号
	private FrameLayout pwdLayout; // 修改密码
	private FrameLayout locationLayout; // 服务位置
	private FrameLayout callLayout; // 平台咨询

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person);
		initView();
	}

	private void initView() {
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("个人中心");
		topTitleView.setBackImageViewVisable(View.GONE);
		timeLayout = (FrameLayout) findViewById(R.id.person_time);
		msgLayout = (FrameLayout) findViewById(R.id.person_msg);
		changeLayout = (FrameLayout) findViewById(R.id.person_change);
		pwdLayout = (FrameLayout) findViewById(R.id.person_pwd);
		locationLayout = (FrameLayout) findViewById(R.id.person_location);
		callLayout = (FrameLayout) findViewById(R.id.person_call);
		timeLayout.setOnClickListener(this);
		msgLayout.setOnClickListener(this);
		changeLayout.setOnClickListener(this);
		pwdLayout.setOnClickListener(this);
		callLayout.setOnClickListener(this);
		callLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {
		// 我的时间
		case R.id.person_time:

			break;
		// 我的消息
		case R.id.person_msg:

			break;
		// 修改手机号
		case R.id.person_change:

			break;
		// 修改密码
		case R.id.person_pwd:
			intent=new Intent().setClass(PersonActivity.this, ForgotActivity.class);
			intent.putExtra("changepwd", "changepwd");
			break;
		// 服务位置
		case R.id.person_location:

			break;
		// 平台咨询
		case R.id.person_call:

			break;
		}

		if(intent!=null)
		{
			startActivity(intent);
		}
	}
}