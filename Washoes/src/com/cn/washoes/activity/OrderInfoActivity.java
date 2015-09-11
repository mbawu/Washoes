package com.cn.washoes.activity;

import android.os.Bundle;
import android.util.Log;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.model.OrderInfo;
import com.cn.washoes.util.NetworkAction;

public class OrderInfoActivity extends BaseActivity {

	private TopTitleView topTitleView;
	private String oid;
	private OrderInfo orderInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_info_layout);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("订单详情");
		oid = getIntent().getStringExtra("oid");
		getOrderInfo();
	}

	private void getOrderInfo() {

		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setAid(OrderListActivity.aid);
		requestWrapper.setSeskey(OrderListActivity.seskey);
		requestWrapper.setAct("detail");
		requestWrapper.setOrder_id(oid);

		sendData(requestWrapper, NetworkAction.order);
	}

	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		super.showResualt(responseWrapper, requestType);
		if (requestType == NetworkAction.order) {
			orderInfo = responseWrapper.getOrder_info();
			if (orderInfo != null) {
				Log.i("d", orderInfo.getServicetime());

			}

		}
	}
}
