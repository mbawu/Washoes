package com.cn.hongwei;

import java.util.Map;

/**
 * 
     * 此类描述的是： 请求参数的包装类
     * @author: wake 
     * @version: 2014年12月2日 上午10:51:20
 */
public class RequestWrapper {
	
	private String op; //操作类型
	private String mobile; //手机号
	private String password; //密码
	private String type; //验证码类型(注册0   密码找回1  手机号更新2)
	private boolean showDialog=false;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isShowDialog() {
		return showDialog;
	}
	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

}
