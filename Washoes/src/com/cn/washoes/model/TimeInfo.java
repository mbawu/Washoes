package com.cn.washoes.model;

public class TimeInfo {

	private String is_server;//时间ID
	private String time_hour;//时间
	private String time_id;//服务状态（-1已失效 0已预约 1未预约 2流失）

	public String getIs_server() {
		return is_server;
	}

	public void setIs_server(String is_server) {
		this.is_server = is_server;
	}

	public String getTime_hour() {
		return time_hour;
	}

	public void setTime_hour(String time_hour) {
		this.time_hour = time_hour;
	}

	public String getTime_id() {
		return time_id;
	}

	public void setTime_id(String time_id) {
		this.time_id = time_id;
	}

}
