package com.cn.washoes.model;

public class DateInfo {
	private String week_str;//周几（中文）
	private String date;//完整日期
	private String date_str;//格式化日期
	private String week;//周几（阿拉伯数字）
	public String getWeek_str() {
		return week_str;
	}
	public void setWeek_str(String week_str) {
		this.week_str = week_str;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate_str() {
		return date_str;
	}
	public void setDate_str(String date_str) {
		this.date_str = date_str;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	
	

}
