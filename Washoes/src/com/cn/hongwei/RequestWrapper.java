package com.cn.hongwei;

import java.util.Map;

/**
 * 
     * 此类描述的是： 请求参数的包装类
     * @author: wake 
     * @version: 2014年12月2日 上午10:51:20
 */
public class RequestWrapper {
	
	private String op;
	private String mobile;
	private String password;
	private boolean showDialog=false;
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
