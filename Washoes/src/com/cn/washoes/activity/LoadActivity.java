package com.cn.washoes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.model.Info;
import com.cn.washoes.person.LoginActivity;
import com.cn.washoes.person.RegisterActivity;


/**
 * app初次加载的页面
 * @author Administrator
 *
 */
public class LoadActivity extends BaseActivity {

	
	private TopTitleView topTitleView;//标题栏
	private TextView loginBtn;//登录按钮
	private TextView registerBtn;//注册按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle(getString(R.string.load_title));
		topTitleView.setBackImageViewVisable(View.GONE);
		loginBtn=(TextView) findViewById(R.id.login_btn);
		registerBtn=(TextView) findViewById(R.id.register_btn);
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent intent=new Intent().setClass(LoadActivity.this, LoginActivity.class);
			startActivity(intent);
				
			}
		});
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent intent=new Intent().setClass(LoadActivity.this, RegisterActivity.class);
			startActivity(intent);
				
			}
		});
		
	}
	
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				MyApplication.exit=true;
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
