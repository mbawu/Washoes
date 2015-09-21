package com.cn.washoes.model;

/**
 * 区域组选择器
 * @author Wu Jiang
 *
 */
public class AreaSpinner {

	private String areaID;
	private String distance;
	private boolean delete=false; //删除
	public String getAreaID() {
		return areaID;
	}
	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	
}
