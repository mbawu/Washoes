package com.cn.washoes.person;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.washoes.R;

/**
 * 登录页面
 * @author Wu Jiang
 *
 */
public class LoginActivity extends BaseActivity {

	private TextView loginBtn;//登录按钮
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_login);
		initView();
	}
	private void initView() {
		loginBtn=(TextView) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(LoginActivity.this, "点了", Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}
	
}
