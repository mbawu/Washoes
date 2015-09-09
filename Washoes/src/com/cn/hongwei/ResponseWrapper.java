package com.cn.hongwei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * 
 * 此类描述的是： 返回数据的包装类
 * 
 * @author: wake
 * @version: 2014年12月2日 上午10:50:38
 * @param <T>
 */
public class ResponseWrapper {

	private String code;//代码值
	private String msg;//信息提示
	private String sms_id;//验证码ID
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSms_id() {
		return sms_id;
	}
	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}
	
	
	
}
