package com.cn.washoes.model;

public class Team {
	private String aid;//技师ID
	private String rank_id;//技师头衔（1组员 2组长）
	private String realname;//技师姓名
	private String nickname;//技师昵称
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getRank_id() {
		return rank_id;
	}
	public void setRank_id(String rank_id) {
		this.rank_id = rank_id;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
