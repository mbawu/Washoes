package com.cn.washoes.activity;

import java.util.List;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.CarImageView;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.model.ComInfo;
import com.cn.washoes.model.ImgInfo;
import com.cn.washoes.model.OrderAddress;
import com.cn.washoes.model.OrderInfo;
import com.cn.washoes.model.SS_Info;
import com.cn.washoes.util.NetworkAction;

public class OrderInfoActivity extends BaseActivity {

	private TopTitleView topTitleView;
	private String oid;
	private OrderInfo orderInfo;

	private TextView textId;

	private TextView textDate;
	private TextView textPrice;
	private TextView textUserName;
	private TextView textUserType;
	private TextView textPhone;
	private TextView textAddress;

	private LinearLayout layoutColorService;

	private TextView textHeel;

	private LinearLayout layoutPic;

	private TextView textEvaZy;
	private TextView textEvaTd;
	private TextView textEvaSs;
	private TextView textEvaContent;

	private TextView textConfirm;
	private LinearLayout layoutPicH;
	private LinearLayout layoutEva;
	private LinearLayout layoutCall;
	private LinearLayout layoutDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_info_layout);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("订单详情");
		oid = getIntent().getStringExtra("oid");
		textId = (TextView) findViewById(R.id.order_info_id);
		textDate = (TextView) findViewById(R.id.order_item_text_data);
		textPrice = (TextView) findViewById(R.id.order_item_text_price);
		textUserName = (TextView) findViewById(R.id.order_item_text_username);
		textUserType = (TextView) findViewById(R.id.order_item_text_usertype);
		textPhone = (TextView) findViewById(R.id.order_item_text_phone);
		textAddress = (TextView) findViewById(R.id.order_item_text_address);

		layoutColorService = (LinearLayout) findViewById(R.id.order_info_color_layout);
		textHeel = (TextView) findViewById(R.id.order_info_service_heel);
		layoutPic = (LinearLayout) findViewById(R.id.order_info_pic_layout);

		textEvaZy = (TextView) findViewById(R.id.order_info_eva_zy);
		textEvaTd = (TextView) findViewById(R.id.order_info_eva_td);
		textEvaSs = (TextView) findViewById(R.id.order_info_eva_ss);
		textEvaContent = (TextView) findViewById(R.id.order_info_eva_content);

		textConfirm = (TextView) findViewById(R.id.order_info_confirm_btn);
		layoutCall = (LinearLayout) findViewById(R.id.order_info_call_layout);
		layoutDetail = (LinearLayout) findViewById(R.id.order_detail_layout);
		layoutPicH = (LinearLayout) findViewById(R.id.order_info_picH_layout);
		layoutEva = (LinearLayout) findViewById(R.id.order_info_eva_layout);
		textId.setText("ID  " + oid);

		getOrderInfo();
	}

	private void getOrderInfo() {

		RequestWrapper requestWrapper = new RequestWrapper();

		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());

		requestWrapper.setOp("order");

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
				textPrice.setText("￥ " + orderInfo.getPay_price());
				OrderAddress address = orderInfo.getInfo();
				if (address != null && address.getMobile() != null) {
					textUserName.setText(address.getRealname());
					textUserType
							.setText("0".equals(orderInfo.getUtag()) ? "新用户"
									: "老用户");
					textPhone.setText(address.getMobile());
					textAddress.setText(address.getPosition()
							+ address.getAddress());
				}

				if (orderInfo.getList() != null
						&& orderInfo.getList().size() > 0) {
					for (com.cn.washoes.model.ServiceInfo sInfo : orderInfo
							.getList()) {
						if ("3".equals(sInfo.getCategory_id())) {// 修理
							setDataRepairService(sInfo.getProject_name(),
									sInfo.getSs_info());
						} else {
							setDataColorService(sInfo);
							/*
							 * setDataColorService(sInfo.getProject_name(),
							 * sInfo.getSs_info());
							 * setDataColorService(sInfo.getProject_name(),
							 * sInfo.getSs_info());
							 */
						}
					}

				}

				if ("1408".equals(oid)) {
					orderInfo.setFlag(OrderListActivity.ORDER_STATUS_WAITING);
				} else if ("1407".equals(oid)) {
					orderInfo.setFlag(OrderListActivity.ORDER_STATUS_WORKING);
				}

				if (OrderListActivity.ORDER_STATUS_WAITING.equals(orderInfo
						.getFlag())) {
					layoutDetail.setVisibility(View.VISIBLE);
				} else if (OrderListActivity.ORDER_STATUS_WORKING
						.equals(orderInfo.getFlag())) {
					initPic(orderInfo);
					textConfirm.setVisibility(View.VISIBLE);

				} else if (OrderListActivity.ORDER_STATUS_FINISH
						.equals(orderInfo.getFlag())) {
					initPic(orderInfo);
					initEva(orderInfo);
				} else if (OrderListActivity.ORDER_STATUS_CANCEL
						.equals(orderInfo.getFlag())) {
					layoutCall.setVisibility(View.VISIBLE);
				}

			}

		}
	}

	private void initPic(OrderInfo orderInfo) {
		layoutPicH.setVisibility(View.VISIBLE);
		if (orderInfo.getUltu_def() != null
				&& orderInfo.getUltu_def().size() > 0) {

			for (ImgInfo item : orderInfo.getUltu_def()) {
				if (item.getFile_path() != null) {
					CarImageView img = (CarImageView) getLayoutInflater()
							.inflate(R.layout.order_camare_item_img, layoutPic,
									false);
					img.loadImage(item.getFile_path());
					layoutPic.addView(img);
					if (layoutPic.getChildCount() >= 3) {
						break;
					}
				}
			}
		}
	}

	private void initEva(OrderInfo orderInfo) {
		layoutEva.setVisibility(View.VISIBLE);
		if (orderInfo.getCom_info() != null
				&& orderInfo.getCom_info().getComment_id() != null
				&& !"".equals(orderInfo.getCom_info().getComment_id())) {
			ComInfo cInfo = orderInfo.getCom_info();
			textEvaZy.setText(getString(R.string.order_info_zy,
					cInfo.getPro_stars()));
			textEvaTd.setText(getString(R.string.order_info_td,
					cInfo.getAtt_stars()));
			textEvaSs.setText(getString(R.string.order_info_ss,
					cInfo.getPun_stars()));
			textEvaContent.setText(cInfo.getComments());

		}
	}

	private void setDataColorService(com.cn.washoes.model.ServiceInfo info) {

		findViewById(R.id.xh).setVisibility(View.VISIBLE);
		LinearLayout cServiceLayout = (LinearLayout) getLayoutInflater()
				.inflate(R.layout.order_service_item, layoutColorService, false);
		TextView textType = (TextView) cServiceLayout
				.findViewById(R.id.service_item_type);
		textType.setText(info.getProject_name());

		TextView textServiceItem = (TextView) cServiceLayout
				.findViewById(R.id.service_item_0);
		textServiceItem.setText(getServiceHtml(info.getServer_name(),
				info.getBuy_num()));

		if (info.getSs_info() != null && info.getSs_info().size() > 0) {

			SS_Info ss_info = info.getSs_info().get(0);

			textServiceItem = (TextView) cServiceLayout
					.findViewById(R.id.service_item_1);
			textServiceItem.setText(getServiceHtml(ss_info.getSs_name(),
					ss_info.getSs_buy_num()));

			if (layoutColorService.getChildCount() != 0) {
				layoutColorService.addView(getLayoutInflater().inflate(
						R.layout.border_view, layoutColorService, false));
			}
			layoutColorService.addView(cServiceLayout);

		}

	}

	private void setDataRepairService(String typeName, List<SS_Info> ss_Infos) {
		findViewById(R.id.xL).setVisibility(View.VISIBLE);
		if (ss_Infos == null || ss_Infos.size() == 0) {
			textHeel.setText(getServiceHtml("鞋跟", "0"));
		} else {
			textHeel.setText(getServiceHtml(ss_Infos.get(0).getSs_name(),
					ss_Infos.get(0).getSs_buy_num()));
		}
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
