package com.cn.washoes.person;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.washoes.R;
import com.cn.washoes.util.NetworkAction;

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
	private int defaultCount = 60;// 默认多长时间(秒)重复获取验证码
	private int count = defaultCount;//计时器
	private String phone;//点击获取验证码时候获取到的手机号
	private String sms_id;//验证码ID
	
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
				phone=phoneTxt.getText().toString();
				if(!MyApplication.isPhoneNumberValid(phone))
				{
					Toast.makeText(ForgotActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
					return;
				}
				handler.sendEmptyMessage(count);
				getCode();
			}
		});
	}
	
	/**
	 * 获取验证码
	 */
	private void getCode() {
		RequestWrapper requestWrapper=new RequestWrapper();
		requestWrapper.setOp(NetworkAction.code.toString());
		requestWrapper.setMobile(phone);
		requestWrapper.setType("1");
		sendData(requestWrapper, NetworkAction.code);
		
	}
	
	
	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if(requestType==NetworkAction.code)
		{
			sms_id=responseWrapper.getSms_id();
			Toast.makeText(this, "获取到验证码为"+sms_id, Toast.LENGTH_SHORT).show();
		}
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
	
	/**
	 * 倒计时用的handler
	 * 
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (count > 0) {
				getCodeBtn.setText("" + count);
				getCodeBtn.setBackgroundResource(R.drawable.get_code_off);
				getCodeBtn.setEnabled(false);
				count--;
				handler.sendEmptyMessageDelayed(count, 1000);
			} else {
				changeBtnNormal();
				count = defaultCount;
				getCodeBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						getCode();
						getCodeBtn.setEnabled(true);
					}

					
				});
			}
		};
	};
	
	
	//在服务器出错的时候恢复可获取验证码的点击动作
	public void sendDataErrorResponse(NetworkAction requestType) {
		//在服务器出错的时候恢复可获取验证码的点击动作
		if(requestType==NetworkAction.code)
		{
			changeBtnNormal();
		}
	};
	
	/**
	 * 还原可点击的获取验证码的功能
	 */
	private void changeBtnNormal() {
		getCodeBtn.setText("获取验证码");
		getCodeBtn.setBackgroundResource(R.drawable.get_code_on);
		getCodeBtn.setEnabled(true);
	}
}
