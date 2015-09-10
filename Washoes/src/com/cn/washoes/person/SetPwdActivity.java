package com.cn.washoes.person;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.washoes.R;
import com.cn.washoes.activity.ConfirmDialog;
import com.cn.washoes.util.NetworkAction;


/**
 * 设置密码页面
 * @author Wu Jiang
 *
 */
public class SetPwdActivity extends BaseActivity {

	
	private EditText pwd1Txt;//第一次输入的密码
	private EditText pwd2Txt;//第二次输入的密码
	private TextView btn;
	private String aid;//技师ID
	private String mobile;//手机号
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_setpwd);
		initView();
		getData();
	}
	private void getData() {
		Intent intent=getIntent();
		String type=intent.getStringExtra("type");
		//注册页面设置密码
		if(type.equals("0"))
		{
			aid=intent.getStringExtra("aid");
			mobile=intent.getStringExtra("mobile");
		}
		
	}
	private void initView() {
		pwd1Txt=(EditText) findViewById(R.id.setpwd_pwd1);
		pwd2Txt=(EditText) findViewById(R.id.setpwd_pwd2);
		btn=(TextView) findViewById(R.id.setpwd_btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(pwd1Txt.length()>=6 && pwd2Txt.length()>=6 )
				{
					String pwd1=pwd1Txt.getText().toString();
					String pwd2=pwd2Txt.getText().toString();
					if(!pwd1.equals(pwd2))
					{
						Toast.makeText(SetPwdActivity.this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
						return;
					}
					//设置密码
					setpwd();
				}
				else
				{
					Toast.makeText(SetPwdActivity.this, "密码不允许为空并且要大于6位", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
	
	/**
	 * 注册设置密码
	 */
	private void setpwd()
	{
		RequestWrapper requestWrapper=new RequestWrapper();
		requestWrapper.setOp(NetworkAction.setpwd.toString());
		requestWrapper.setAid(aid);
		requestWrapper.setMobile(mobile);
		requestWrapper.setPassword(pwd1Txt.getText().toString());
		requestWrapper.setRepassword(pwd2Txt.getText().toString());
		sendData(requestWrapper, NetworkAction.setpwd);
	}
	
	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if(requestType==NetworkAction.setpwd)
		{
			ConfirmDialog dialog=new ConfirmDialog(this);
			dialog.setTitle("提示");
			dialog.setMessage(responseWrapper.getMsg());
			
			dialog.setOkButton("我知道了", new ConfirmDialog.OnClickListener() {
				
				@Override
				public void onClick(Dialog dialog, View view) {
					SetPwdActivity.this.finish();
					
				}
			});
			dialog.show();
		}
	}
}
