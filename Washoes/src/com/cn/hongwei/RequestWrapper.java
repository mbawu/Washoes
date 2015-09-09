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
	private String repassword;//再次输入的密码
	private String type; //验证码类型(注册0   密码找回1  手机号更新2)
	private String idcard;//身份证号码
	private String realname;//姓名(注册模块)
	private String gps;//GPS字串(经度,纬度  如：116.237543,39.911836)
	private String code;//验证码
	private String sms_id;//验证码ID
	private String aid;//技师ID
	
	
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	private boolean showDialog=false;
	
	
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSms_id() {
		return sms_id;
	}
	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}
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
