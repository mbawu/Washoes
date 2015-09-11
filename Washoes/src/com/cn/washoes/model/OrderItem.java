package com.cn.washoes.model;

public class OrderItem {

	private String createtime;// 下单时间
	private String real_price;// 实付金额（使用优惠券后）
	private String uid;// 用户ID
	private String is_comment;// 是否已评价（1是 0否）
	private String flag;// 订单状态（2待服务 3已取消 4服务中 5已完成）

	private String servicetime;// 服务时间
	private String utag;// 用户标识(0新用户 1老用户)
	private String is_pay;// 是否已支付（1是 0否）
	private String realname;// 用户姓名
	private String order_id; // 订单ID
	private String pay_price;// 订单金额
	private String order_code;// 订单号（编码）
	private String mobile;// 用户手机号

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getReal_price() {
		return real_price;
	}

	public void setReal_price(String real_price) {
		this.real_price = real_price;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIs_comment() {
		return is_comment;
	}

	public void setIs_comment(String is_comment) {
		this.is_comment = is_comment;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getServicetime() {
		return servicetime;
	}

	public void setServicetime(String servicetime) {
		this.servicetime = servicetime;
	}

	public String getUtag() {
		return utag;
	}

	public void setUtag(String utag) {
		this.utag = utag;
	}

	public String getIs_pay() {
		return is_pay;
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPay_price() {
		return pay_price;
	}

	public void setPay_price(String pay_price) {
		this.pay_price = pay_price;
	}

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
