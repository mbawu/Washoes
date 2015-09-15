package com.cn.washoes.activity;

import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.model.OrderAddress;
import com.cn.washoes.model.OrderInfo;
import com.cn.washoes.model.SS_Info;
import com.cn.washoes.util.NetworkAction;

public class OrderInfoActivity extends BaseActivity {

	private TopTitleView topTitleView;
	private String oid;
	private OrderInfo orderInfo;

	private TextView textDate;
	private TextView textPrice;
	private TextView textUserName;
	private TextView textUserType;
	private TextView textPhone;
	private TextView textAddress;

	private LinearLayout layoutColorService;

	// private LinearLayout layoutColorService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_info_layout);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("订单详情");
		oid = getIntent().getStringExtra("oid");

		textDate = (TextView) findViewById(R.id.order_item_text_data);
		textPrice = (TextView) findViewById(R.id.order_item_text_price);
		textUserName = (TextView) findViewById(R.id.order_item_text_username);
		textUserType = (TextView) findViewById(R.id.order_item_text_usertype);
		textPhone = (TextView) findViewById(R.id.order_item_text_phone);
		textAddress = (TextView) findViewById(R.id.order_item_text_address);

		layoutColorService = (LinearLayout) findViewById(R.id.order_info_color_layout);

		getOrderInfo();
	}

	private void getOrderInfo() {

		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp("order");
		requestWrapper.setAid(OrderListActivity.aid);
		requestWrapper.setSeskey(OrderListActivity.seskey);
//		requestWrapper.setAct("detail");
		requestWrapper.setOrder_id(oid);

		sendData(requestWrapper, NetworkAction.detail);
	}

	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		super.showResualt(responseWrapper, requestType);
		if (requestType == NetworkAction.detail) {
			orderInfo = responseWrapper.getOrder_info();
			if (orderInfo != null) {

				textDate.setText(orderInfo.getServicetime());
				textPrice.setText(orderInfo.getPay_price());
				OrderAddress address = orderInfo.getInfo();
				if (address != null) {
					textUserName.setText(address.getRealname());
					textUserType
							.setText("0".equals(orderInfo.getUtag()) ? "新用户"
									: "老用户");
					textPhone.setText(address.getMobile());
					textAddress.setText(address.getPosition()
							+ address.getAddress());

					if (orderInfo.getList() != null
							&& orderInfo.getList().size() > 0) {
						for (com.cn.washoes.model.ServiceInfo sInfo : orderInfo
								.getList()) {
							if ("1".equals(sInfo.getCategory_id())) {// 洗护上色
								setDataColorService(sInfo.getProject_name(),
										sInfo.getSs_info());
								/*setDataColorService(sInfo.getProject_name(),
										sInfo.getSs_info());
								setDataColorService(sInfo.getProject_name(),
										sInfo.getSs_info());*/
							} else if ("3".equals(sInfo.getCategory_id())) {// 修理
								setDataRepairService(sInfo.getProject_name(),
										sInfo.getSs_info());
							}
						}

					}
				}

			}

		}
	}

	private void setDataColorService(String typeName, List<SS_Info> ss_Infos) {
		if (ss_Infos != null && ss_Infos.size() > 0) {
			LinearLayout cServiceLayout = (LinearLayout) getLayoutInflater()
					.inflate(R.layout.order_service_item, null);

			TextView textType = (TextView) cServiceLayout
					.findViewById(R.id.service_item_type);
			textType.setText(typeName);

			SS_Info ss_info = ss_Infos.get(0);
			TextView textServiceItem = (TextView) cServiceLayout
					.findViewById(R.id.service_item_0);
			textServiceItem.setText(getServiceHtml(ss_info.getSs_name(),
					ss_info.getSs_buy_num()));

			if (ss_Infos.size() > 1) {
				ss_info = ss_Infos.get(1);
				textServiceItem = (TextView) cServiceLayout
						.findViewById(R.id.service_item_1);
				textServiceItem.setText(getServiceHtml(ss_info.getSs_name(),
						ss_info.getSs_buy_num()));
			}
			if (layoutColorService.getChildCount() != 0) {
				layoutColorService.addView(getLayoutInflater().inflate(
						R.layout.border_view, layoutColorService,false));
			}
			layoutColorService.addView(cServiceLayout);

		}

	}

	private void setDataRepairService(String typeName, List<SS_Info> ss_Infos) {

	}

	private Spanned getServiceHtml(String sName, String num) {

		String html = "";
		if (sName != null) {
			html = "<font color=\"#969696\">" + sName + "</font>";
			if (num != null) {
				html = html + "<font color=\"#e64626\">  X" + num + "</font>";
			}
		}
		return Html.fromHtml(html);

	}

}
