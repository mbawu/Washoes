package com.cn.washoes.model;

import java.util.List;

public class ServiceInfo {
	private String server_name;
	private String duration;
	private String price;
	private String project_name;
	private String category_name;
	private String buy_num;
	private String project_id;
	private String default_img;
	private String category_id;
	private List<SS_Info> ss_info;

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getBuy_num() {
		return buy_num;
	}

	public void setBuy_num(String buy_num) {
		this.buy_num = buy_num;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getDefault_img() {
		return default_img;
	}

	public void setDefault_img(String default_img) {
		this.default_img = default_img;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public List<SS_Info> getSs_info() {
		return ss_info;
	}

	public void setSs_info(List<SS_Info> ss_info) {
		this.ss_info = ss_info;
	}

}
