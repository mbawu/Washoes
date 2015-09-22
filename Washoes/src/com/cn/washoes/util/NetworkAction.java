package com.cn.washoes.util;

/**
 * 网络请求枚举类型
 */
public enum NetworkAction {

	login, // 登录
	code, // 获取验证码
	register, // 注册
	setpwd, // 注册设置密码
	list, // 订单列表
	detail, // 订单详情
	getpwd_next, // 找回/修改密码 -- 下一步
	getpwd_reset, // 找回/修改密码 -- 设置密码
	edit, // 修改手机号
	logout, // 退出登录
	province, // 省份
	city, // 城市
	area, // 地区
	ultu_list, // 图片详情
	distance, // 获取服务距离列表
	pos_in, // 技师服务位置设置
	ultu_upload_v2, // 订单图片上传
	pos_list, // 技师服务位置列表
	confirm_e,//订单确认
	msg_list,//我的消息列表
}
