package com.cn.washoes.person;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.washoes.R;

/**
 * 忘记密码页面
 * @author Wu Jiang
 *
 */
public class ForgotActivity extends BaseActivity {

	
	private EditText phoneTxt;//手机号输入框
	private EditText codeTxt;//验证码输入框
	private TextView getCodeBtn;//获取验证码按钮
	private TextView forgotBtn;//下一步按钮
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_forgot);
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		phoneTxt=(EditText) findViewById(R.id.forgot_phone);
		codeTxt=(EditText) findViewById(R.id.forgot_code);
		codeTxt.addTextChangedListener(watcher);
		getCodeBtn=(TextView) findViewById(R.id.get_code_btn);
		forgotBtn=(TextView) findViewById(R.id.forgot_btn);
		getCodeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(ForgotActivity.this, "点了我", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
	/**
	 * 监听是否输入验证码以开启下一步按钮
	 */
	private TextWatcher watcher = new TextWatcher() {
		   
	    @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	        // TODO Auto-generated method stub
	       
	    }
	   
	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
	            int after) {
	        // TODO Auto-generated method stub
	       
	    }
	   
	    @Override
	    public void afterTextChanged(Editable s) {
	        if(s.length()>1)
	        {
	        	forgotBtn.setBackgroundResource(R.drawable.login_bg);
	        	forgotBtn.setEnabled(true);
	        }
	        	
	        else
	        {
	        	forgotBtn.setBackgroundResource(R.drawable.enable_btn_off);
	        	forgotBtn.setEnabled(false);
	        }
	        	
	       
	    }
	};
}
