package com.cn.washoes.model;

import java.util.List;

public class OrderInfo {
	private String uid;
	private ComInfo com_info;
	private String is_comment;
	private String is_pay;
	private List<ServiceInfo> list;
	private String order_id;
	private String order_code;
	private OrderAddress info;
	private String createtime;
	private String real_price;
	private String flag;
	private String servicetime;
	private List<ImgInfo> ultu_def;
	private String utag;
	private String pay_price;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public ComInfo getCom_info() {
		return com_info;
	}

	public void setCom_info(ComInfo com_info) {
		this.com_info = com_info;
	}

	public String getIs_comment() {
		return is_comment;
	}

	public void setIs_comment(String is_comment) {
		this.is_comment = is_comment;
	}

	public String getIs_pay() {
		return is_pay;
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	public List<ServiceInfo> getList() {
		return list;
	}

	public void setList(List<ServiceInfo> list) {
		this.list = list;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public OrderAddress getInfo() {
		return info;
	}

	public void setInfo(OrderAddress info) {
		this.info = info;
	}

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

	public List<ImgInfo> getUltu_def() {
		return ultu_def;
	}

	public void setUltu_def(List<ImgInfo> ultu_def) {
		this.ultu_def = ultu_def;
	}

	public String getUtag() {
		return utag;
	}

	public void setUtag(String utag) {
		this.utag = utag;
	}

	public String getPay_price() {
		return pay_price;
	}

	public void setPay_price(String pay_price) {
		this.pay_price = pay_price;
	}

}
