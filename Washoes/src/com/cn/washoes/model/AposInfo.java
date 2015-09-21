package com.cn.washoes.model;

public class AposInfo {
	private String city_id;
	private String area_id;
	private String city_name;
	private String province_name;
	private String address;
	private String province_id;
	private String area_name;

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

	public String getCity_name() {
		if(city_name!= null){
			return city_name.trim();
		}
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public String getAddress() {
		if(address!= null){
			return address.trim();
		}
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince_id() {
		return province_id;
	}

	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

}
