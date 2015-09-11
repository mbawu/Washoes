package com.cn.washoes.util;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.RequestWrapper;
import com.cn.washoes.R;

/**
 * 获取验证码相关
 * @author Wu Jiang
 *
 */
public class Code {

	private static int defaultCount = 60;// 默认多长时间(秒)重复获取验证码
	private static int count = defaultCount;//计时器
	private static TextView btn;
	private static String phoneNum;
	private static BaseActivity activity;
	
	public static void getCode(TextView getCodeBtn,String phone,BaseActivity baseActivity)
	{
		btn=getCodeBtn;
		phoneNum=phone;
		activity=baseActivity;
		handler.sendEmptyMessage(count);
		getCodeI();
	}
	
	
	/**
	 * 倒计时用的handler
	 * 
	 */
	static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (count > 0) {
				btn.setText("" + count);
				btn.setBackgroundResource(R.drawable.get_code_off);
				btn.setEnabled(false);
				count--;
				handler.sendEmptyMessageDelayed(count, 1000);
			} else {
				changeBtnNormal(btn);
				count = defaultCount;
				btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						getCodeI();
						btn.setEnabled(true);
					}

					
				});
			}
		};
	};
	
	/**
	 * 还原可点击的获取验证码的功能
	 */
	public static void changeBtnNormal(TextView btn) {
		count=0;
		btn.setText("获取验证码");
		btn.setBackgroundResource(R.drawable.get_code_on);
		btn.setEnabled(true);
	}
	
	/**
	 * 获取验证码
	 */
	private static void getCodeI() {
		RequestWrapper requestWrapper=new RequestWrapper();
		requestWrapper.setOp(NetworkAction.code.toString());
		requestWrapper.setMobile(phoneNum);
		Log.i("test", activity.getClass().getSimpleName());
		if(activity.getClass().getSimpleName().equals("ForgotActivity"))
			requestWrapper.setType("1");
		else if(activity.getClass().getSimpleName().equals("RegisterActivity"))
			requestWrapper.setType("0");
		activity.sendData(requestWrapper, NetworkAction.code);
		
	}
}
