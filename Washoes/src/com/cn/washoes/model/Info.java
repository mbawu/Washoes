package com.cn.washoes.model;

public class Info {

	private String aid; //技师ID
	private String realname; //姓名
	private String idcard; //身份证号
	private String seskey; //身份密钥
	private String mobile;  //手机号
	private String photo;   //头像
	private String qrcode_url;  //二维码跳转链接
	private String lastlogin;  //最后登录时间
	private String logintimes;  //登录次数
	private boolean loginState;//登录状态
	
	
	
	public boolean isLoginState() {
		return loginState;
	}
	public void setLoginState(boolean loginState) {
		this.loginState = loginState;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getSeskey() {
		return seskey;
	}
	public void setSeskey(String seskey) {
		this.seskey = seskey;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getQrcode_url() {
		return qrcode_url;
	}
	public void setQrcode_url(String qrcode_url) {
		this.qrcode_url = qrcode_url;
	}
	public String getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}
	public String getLogintimes() {
		return logintimes;
	}
	public void setLogintimes(String logintimes) {
		this.logintimes = logintimes;
	}
	
	
}
