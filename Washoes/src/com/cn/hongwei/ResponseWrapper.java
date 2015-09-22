package com.cn.hongwei;

import java.util.ArrayList;
import java.util.List;

import com.cn.washoes.model.Area;
import com.cn.washoes.model.City;
import com.cn.washoes.model.DateInfo;
import com.cn.washoes.model.Distance;
import com.cn.washoes.model.ImgInfo;
import com.cn.washoes.model.Info;
import com.cn.washoes.model.LocArea;
import com.cn.washoes.model.LocInfo;
import com.cn.washoes.model.Msg;
import com.cn.washoes.model.OrderInfo;
import com.cn.washoes.model.OrderItem;
import com.cn.washoes.model.Province;
import com.cn.washoes.model.TimeInfo;

/**
 * 
 * 此类描述的是： 返回数据的包装类
 * 
 * @author: wake
 * @version: 2014年12月2日 上午10:50:38
 * @param <T>
 */
public class ResponseWrapper {

	private String code;// 代码值
	private String msg;// 信息提示
	private String sms_id;// 验证码ID
	private Info info; // 用户信息
	private String aid; // 准技师ID(注册)
	private String mobile; // 手机号(注册)

	private String page;// 当前页数
	private String per;// 每页显示条数
	private String nums;// 总数量
	private String pages;// 总页数
	private String all_onums;// 所有订单总数（已完成的订单 flag=5）
	private String now_onums;// 本月订单总数（已完成的订单 flag=5）
	private List<OrderItem> list;// 订单列表

	private OrderInfo order_info;//订单信息
	
	private ArrayList<Province> province;//省份
	private ArrayList<City> city;//省份
	private ArrayList<Area> area;//地区
	private ArrayList<Distance> distance;//获取服务距离列表
	private LocInfo apos_info;//定点位置信息
	private ArrayList<LocArea> apos_list;//区域列表

	private String be_time;// 服务前照片上传时间
	private List<ImgInfo> be_images;// 服务前照片列表

	private String af_time;// 服务后照片上传时间
	private List<ImgInfo> af_images;// 服务后照片列表

	private List<DateInfo> date_list;
	
	private List<TimeInfo> time_list;

	private List<Msg> msg_list;//我的消息列表

	
	public List<Msg> getMsg_list() {
		return msg_list;
	}


	public void setMsg_list(List<Msg> msg_list) {
		this.msg_list = msg_list;
	}


	public LocInfo getApos_info() {
		return apos_info;
	}
	

	public void setApos_info(LocInfo apos_info) {
		this.apos_info = apos_info;
	}

	public ArrayList<LocArea> getApos_list() {
		return apos_list;
	}

	public void setApos_list(ArrayList<LocArea> apos_list) {
		this.apos_list = apos_list;
	}

	public ArrayList<Distance> getDistance() {
		return distance;
	}

	public void setDistance(ArrayList<Distance> distance) {
		this.distance = distance;
	}

	public ArrayList<Province> getProvince() {
		return province;
	}

	public void setProvince(ArrayList<Province> province) {
		this.province = province;
	}

	public ArrayList<City> getCity() {
		return city;
	}

	public void setCity(ArrayList<City> city) {
		this.city = city;
	}

	public ArrayList<Area> getArea() {
		return area;
	}

	public void setArea(ArrayList<Area> area) {
		this.area = area;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

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

	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getAll_onums() {
		return all_onums;
	}

	public void setAll_onums(String all_onums) {
		this.all_onums = all_onums;
	}

	public String getNow_onums() {
		return now_onums;
	}

	public void setNow_onums(String now_onums) {
		this.now_onums = now_onums;
	}

	public List<OrderItem> getList() {
		return list;
	}

	public void setList(List<OrderItem> list) {
		this.list = list;
	}

	public OrderInfo getOrder_info() {
		return order_info;
	}

	public void setOrder_info(OrderInfo order_info) {
		this.order_info = order_info;
	}

	public String getBe_time() {
		return be_time;
	}

	public void setBe_time(String be_time) {
		this.be_time = be_time;
	}

	public List<ImgInfo> getBe_images() {
		return be_images;
	}

	public void setBe_images(List<ImgInfo> be_images) {
		this.be_images = be_images;
	}

	public String getAf_time() {
		return af_time;
	}

	public void setAf_time(String af_time) {
		this.af_time = af_time;
	}

	public List<ImgInfo> getAf_images() {
		return af_images;
	}

	public void setAf_images(List<ImgInfo> af_images) {
		this.af_images = af_images;
	}


	public List<DateInfo> getDate_list() {
		return date_list;
	}


	public void setDate_list(List<DateInfo> date_list) {
		this.date_list = date_list;
	}


	public List<TimeInfo> getTime_list() {
		return time_list;
	}


	public void setTime_list(List<TimeInfo> time_list) {
		this.time_list = time_list;
	}

	
	
}
