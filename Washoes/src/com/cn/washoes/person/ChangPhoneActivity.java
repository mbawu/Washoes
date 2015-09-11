package com.cn.washoes.person;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;

/**
 * 修改手机号码成功页面
 * @author Wu Jiang
 *
 */
public class ChangPhoneActivity extends BaseActivity {

	
	private TopTitleView topTitleView;//标题栏
	private TextView btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_changephone);
		topTitleView= new TopTitleView(this);
		topTitleView.setTitle("修改手机号");
		btn=(TextView) findViewById(R.id.back_home);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
