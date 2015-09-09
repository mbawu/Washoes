package com.cn.washoes.person;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
 * 注册页面
 * @author Wu Jiang
 *
 */
public class RegisterActivity extends BaseActivity {

	private EditText idTxt;//身份证号
	private EditText nameTxt;//姓名
	private EditText phoneTxt;//手机号
	private EditText codeTxt;//输入的验证码
	private TextView getCodeBtn;//获取验证码按钮
	private TextView registerBtn;//注册按钮
	private String sms_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_register);
		initView();
		MyApplication.getKey(this);
	}

	private void initView() {
		idTxt=(EditText) findViewById(R.id.register_id);
		nameTxt=(EditText) findViewById(R.id.register_name);
		phoneTxt=(EditText) findViewById(R.id.register_phone);
		codeTxt=(EditText) findViewById(R.id.register_code);
		idTxt.addTextChangedListener(watcher);
		nameTxt.addTextChangedListener(watcher);
		phoneTxt.addTextChangedListener(watcher);
		codeTxt.addTextChangedListener(watcher);
		getCodeBtn=(TextView) findViewById(R.id.get_code_btn);
		registerBtn=(TextView) findViewById(R.id.register_btn);
		getCodeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String phone=phoneTxt.getText().toString();
				if(!MyApplication.isPhoneNumberValid(phone))
				{
					Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
					return;
				}
				Code.getCode(getCodeBtn, phone, RegisterActivity.this);
			}
		});
		
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				register();
				
			}
		});
	}
	
	private void register()
	{
		RequestWrapper requestWrapper=new RequestWrapper();
		requestWrapper.setOp(NetworkAction.register.toString());
		requestWrapper.setIdcard(idTxt.getText().toString());
		requestWrapper.setRealname(nameTxt.getText().toString());
		requestWrapper.setMobile(phoneTxt.getText().toString());
		requestWrapper.setGps(MyApplication.lat+","+MyApplication.lng);
		requestWrapper.setCode(codeTxt.getText().toString());
		if(sms_id!=null)
			requestWrapper.setSms_id(sms_id);
		sendData(requestWrapper, NetworkAction.register);
	}
	
	/**
	 * 监听是否所有的输入框都已输入内容，但不校验正确性
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
	        if(idTxt.length()>1 && nameTxt.length()>1 && phoneTxt.length()>1 && codeTxt.length()>1)
	        {
	        	registerBtn.setBackgroundResource(R.drawable.login_bg);
	        	registerBtn.setEnabled(true);
	        }
	        	
	        else
	        {
	        	registerBtn.setBackgroundResource(R.drawable.enable_btn_off);
	        	registerBtn.setEnabled(false);
	        }
	        	
	       
	    }
	};
	
	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if(requestType==NetworkAction.code)
		{
			sms_id=responseWrapper.getSms_id();
			Toast.makeText(this, "验证码已发送成功", Toast.LENGTH_SHORT).show();
		}
		else if(requestType==NetworkAction.register)
		{
			Intent intent=new Intent();
			intent.setClass(RegisterActivity.this, SetPwdActivity.class);
//			intent.
		}
		else if(requestType==NetworkAction.login)
		{
			Log.i("test", "aid--->"+responseWrapper.getInfo().getAid());
			Log.i("test", "Seskey--->"+responseWrapper.getInfo().getSeskey());
		}
	}
	
	/**
	 * 在返回错误信息的时候也恢复可获取验证码的点击动作
	 */
	public void getErrorMsg(NetworkAction requestType) {
		if(requestType==NetworkAction.code)
		{
			Code.changeBtnNormal(getCodeBtn);
		}
	};
	
	/**
	 * 在服务器出错的时候恢复可获取验证码的点击动作
	 */
	public void sendDataErrorResponse(NetworkAction requestType) {
		//在服务器出错的时候恢复可获取验证码的点击动作
		if(requestType==NetworkAction.code)
		{
			Code.changeBtnNormal(getCodeBtn);
		}
	};
}
