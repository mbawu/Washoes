package com.cn.washoes.person;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.activity.MenuTable;
import com.cn.washoes.model.Info;
import com.cn.washoes.util.Cst;
import com.cn.washoes.util.NetworkAction;

/**
 * 登录页面
 * @author Wu Jiang
 *
 */
public class LoginActivity extends BaseActivity {

	private TopTitleView topTitleView;//标题栏
	private EditText phoneTxt;//电话号码输入框
	private EditText pwdTxt;//密码输入框
	private TextView loginBtn;//登录按钮
	private TextView forgot_btn;
	private String phone;
	private String pwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_login);
		initView();
	}
	
	/**
	 * 初始化界面
	 */
	private void initView() {
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("登录");
		phoneTxt=(EditText) findViewById(R.id.login_phone);
		pwdTxt=(EditText) findViewById(R.id.login_pwd);
		loginBtn=(TextView) findViewById(R.id.login_btn);
		forgot_btn=(TextView) findViewById(R.id.forgot_btn);
		forgot_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, ForgotActivity.class);
				startActivity(intent);
//				finish();
				
				
			}
		});
		
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				phone=phoneTxt.getText().toString();
				pwd=pwdTxt.getText().toString();
				
				if(phone.equals(""))
				{
					Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
					return;
				}
					
				if(pwd.equals(""))
				{
					Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
					pwdTxt.requestFocus();
					return;
				}
				if(!MyApplication.isPhoneNumberValid(phone))
				{
					Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
					return;
				}
				RequestWrapper requestWrapper=new RequestWrapper();
				requestWrapper.setOp(NetworkAction.login.toString());
				requestWrapper.setMobile(phone);
				requestWrapper.setPassword(pwd);
				sendData(requestWrapper, NetworkAction.login);
					
			}
		});
		
	}
	
	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if(requestType==NetworkAction.login)
		{
			Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
			//登录成功以后更改登录状态并写入用户信息
			Info info=responseWrapper.getInfo();
			info.setLoginState(true);
			MyApplication.loginStat=true;
			MyApplication.setInfo(info);
			Intent intent=new Intent().setClass(this, MenuTable.class);
			startActivity(intent);
			
			// 登录成功以后刷新一次订单列表
			Intent mIntent = new Intent(Cst.GET_RECEIVE);
			// 发送广播
			sendBroadcast(mIntent);
		
		}
	}
	
}
