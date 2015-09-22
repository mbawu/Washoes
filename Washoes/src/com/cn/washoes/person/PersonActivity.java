package com.cn.washoes.person;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.activity.LoadActivity;
import com.cn.washoes.model.Info;
import com.cn.washoes.util.NetworkAction;

/**
 * 个人中心页面
 * 
 * @author Wu Jiang
 * 
 */
public class PersonActivity extends BaseActivity implements OnClickListener {

	private TopTitleView topTitleView;// 标题栏
	private FrameLayout timeLayout; // 我的时间
	private FrameLayout msgLayout; // 我的消息
	private FrameLayout changeLayout; // 修改手机号
	private FrameLayout pwdLayout; // 修改密码
	private FrameLayout locationLayout; // 服务位置
	private FrameLayout callLayout; // 平台咨询
	private TextView logout;// 退出按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person);
		initView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(MyApplication.loginStat)
			logout.setVisibility(View.VISIBLE);
		else
			logout.setVisibility(View.GONE);
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
		logout = (TextView) findViewById(R.id.logout_btn);
		timeLayout.setOnClickListener(this);
		msgLayout.setOnClickListener(this);
		changeLayout.setOnClickListener(this);
		pwdLayout.setOnClickListener(this);
		callLayout.setOnClickListener(this);
		callLayout.setOnClickListener(this);
		logout.setOnClickListener(this);
		locationLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		// 我的时间
		case R.id.person_time:

			break;
		// 我的消息
		case R.id.person_msg:
			intent = new Intent().setClass(PersonActivity.this,
					MessageActivity.class);
			break;
		// 修改手机号
		case R.id.person_change:
			intent = new Intent().setClass(PersonActivity.this,
					ForgotActivity.class);
			intent.putExtra("changephone", "changephone");
			break;
		// 修改密码
		case R.id.person_pwd:
			intent = new Intent().setClass(PersonActivity.this,
					ForgotActivity.class);
			intent.putExtra("changepwd", "changepwd");
			break;
		// 服务位置
		case R.id.person_location:
			intent = new Intent().setClass(PersonActivity.this,
					LocationActivity.class);
			break;
		// 平台咨询
		case R.id.person_call:
			intent = new Intent("android.intent.action.CALL",
					Uri.parse("tel:4000918189"));

			break;
		// 退出按钮
		case R.id.logout_btn:
			RequestWrapper requestWrapper=new RequestWrapper();
			requestWrapper.setOp(NetworkAction.logout.toString());
			sendData(requestWrapper, NetworkAction.logout);
			break;
		}

		if (intent != null) {
			startActivity(intent);
		}
	}
	
	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if(requestType==NetworkAction.logout)
		{
			Toast.makeText(PersonActivity.this, responseWrapper.getMsg(), Toast.LENGTH_SHORT).show();
			MyApplication.loginStat=false;
//			Info info=MyApplication.getInfo();
//			info.setLoginState(false);
			MyApplication.setInfo(null);
			logout.setVisibility(View.GONE);
			Intent intent=new Intent().setClass(PersonActivity.this, LoadActivity.class);
			startActivity(intent);
		}
	}
	
}
