package com.cn.washoes.model;

import java.util.List;

public class OrderInfo {
	private String uid;//用户ID
	private ComInfo com_info;//评论信息
	private String is_comment;//是否已评价（1是 0否）
	private String is_pay;//是否已支付（1是 0否）
	private List<ServiceInfo> list;
	private String order_id;//订单ID
	private String order_code;//订单号（编码）
	private OrderAddress info;//用户信息
	private String createtime;//下单时间
	private String real_price;//实付金额（使用优惠券后）
	private String flag;//订单状态（2待服务 3已取消 	4服务中 5已完成）

	private String servicetime;//服务时间
	private List<ImgInfo> ultu_def;
	private String utag;// 用户标识(0新用户 1老用户)
	private String pay_price;// 订单金额
	private String art_nickname;// 技师昵称
	private String rank_id;// 技师头衔（1组员 2组长）

	
	/**
	 * 技师昵称
	 * @return
	 */
	public String getArt_nickname() {
		return art_nickname;
	}

	public void setArt_nickname(String art_nickname) {
		this.art_nickname = art_nickname;
	}

	/**
	 * 技师头衔（1组员 2组长）
	 * @return
	 */
	public String getRank_id() {
		return rank_id;
	}

	public void setRank_id(String rank_id) {
		this.rank_id = rank_id;
	}

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
