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
	private String act;//程序入口参数
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
	private String seskey;//身份秘钥
	
	private String page;//当前页
	private String per;//每页显示条数（默认6）
	private String flag;//状态
	private String is_onum;//是否查询订单总数（1是 0否  flag=5时使用）
	
	private String order_id	;
	private String province_id;
	private String province_name;
	private String city_id;
	private String city_name;
	private String area_id;
	private String area_name;
	private String address;
	private String pos_json;//json格式字串(技师服务位置设置)
	
	

	public String getPos_json() {
		return pos_json;
	}
	public void setPos_json(String pos_json) {
		this.pos_json = pos_json;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
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
	public String getSeskey() {
		return seskey;
	}
	public void setSeskey(String seskey) {
		this.seskey = seskey;
	}
	public String getAct() {
		return act;
	}
	public void setAct(String act) {
		this.act = act;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getPer() {
		return per;
	}
	public void setPer(String per) {
		this.per = per;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIs_onum() {
		return is_onum;
	}
	public void setIs_onum(String is_onum) {
		this.is_onum = is_onum;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}
