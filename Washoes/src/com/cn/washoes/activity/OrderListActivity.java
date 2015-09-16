package com.cn.washoes.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.model.OrderItem;
import com.cn.washoes.util.Cst;
import com.cn.washoes.util.NetworkAction;

/**
 * 订单列表界面
 * 
 * @author Administrator
 * 
 */
public class OrderListActivity extends BaseActivity implements
		View.OnClickListener {
	private TopTitleView topTitleView;
	private RadioGroup radioGroupType;
	private TextView nodata;
	private ListView listView;
	private OrderListAdapter adapter;
	private List<OrderItem> orderList = new ArrayList<OrderItem>();

	private LinearLayout layoutOrderNum;
	private TextView textOrderAll;
	private TextView textOrderMonth;

	private TextView textCamareBefore;
	private TextView textCamareAfter;
	private PopupWindow mPopupWindow;

	public static boolean EVALUATE_SUCCESS = false;
	private String page = "1";
	private String status = "2";
	public static final String ORDER_STATUS_WAITING = "2"; // 待服务
	public static final String ORDER_STATUS_WORKING = "4"; // 服务中
	public static final String ORDER_STATUS_FINISH = "5"; // 已完成
	public static final String ORDER_STATUS_CANCEL = "3"; // 已取消
	// public static String aid;
	// public static String seskey;
	private String orderId;

	/**
	 * 界面初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list_layout);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("订单");
		nodata = (TextView) findViewById(R.id.nodataTxt);
		listView = (ListView) findViewById(R.id.listview);
		layoutOrderNum = (LinearLayout) findViewById(R.id.order_num_layout);
		textOrderAll = (TextView) findViewById(R.id.order_text_num_all);
		textOrderMonth = (TextView) findViewById(R.id.order_text_num);
		radioGroupType = (RadioGroup) findViewById(R.id.order_list_type);
		radioGroupType
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.order_list_watting_radiobtn:
							status = ORDER_STATUS_WAITING;
							break;
						case R.id.order_list_working_radiobtn:
							status = ORDER_STATUS_WORKING;
							break;
						case R.id.order_list_finish_radiobtn:
							status = ORDER_STATUS_FINISH;
							break;
						case R.id.order_list_cancel_radiobtn:
							status = ORDER_STATUS_CANCEL;
							break;
						}
						getOrder();
					}
				});

		adapter = new OrderListAdapter(this);
		adapter.setDataList(orderList);
		listView.setAdapter(adapter);
		// MyApplication.getKey(this);
		getOrder();
	}

	/**
	 * 评价成功后重新刷新订单列表
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (EVALUATE_SUCCESS) {
			EVALUATE_SUCCESS = false;
			getOrder();
		}
	}

	/**
	 * 向后台发送订单列表请求
	 */
	private void getOrder() {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		requestWrapper.setAct("list");
		requestWrapper.setPer(Cst.PER + "");
		requestWrapper.setPage(page);
		requestWrapper.setFlag(status);

		if (status == ORDER_STATUS_FINISH && "1".equals(page)) {
			requestWrapper.setIs_onum("1");
		} else {
			requestWrapper.setIs_onum("0");
		}
		sendData(requestWrapper, NetworkAction.order);

		/*
		 * ConfirmDialog dlg = new ConfirmDialog(this); dlg.setTitle("提示");
		 * dlg.setMessage("请确认您已完成了服务，确定确认吗？"); dlg.setOkButton("确认", new
		 * ConfirmDialog.OnClickListener() {
		 * 
		 * @Override public void onClick(Dialog dialog, View view) {
		 * 
		 * } });
		 * 
		 * dlg.setCancelButton("暂不确认", new ConfirmDialog.OnClickListener() {
		 * 
		 * @Override public void onClick(Dialog dialog, View view) {
		 * 
		 * } }); dlg.setSigleBtn(); dlg.show();
		 */

	}

	/**
	 * 解析服务端数据
	 */

	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		super.showResualt(responseWrapper, requestType);
		if (requestType == NetworkAction.order) {
			if (responseWrapper.getList() != null) {
				if ("1".equals(responseWrapper.getPage())) {
					orderList.clear();
				}
				adapter.addDataList(responseWrapper.getList());
			}
			adapter.notifyDataSetChanged();
			if (status == ORDER_STATUS_FINISH) {
				layoutOrderNum.setVisibility(View.VISIBLE);
				textOrderAll.setText(responseWrapper.getAll_onums());
				textOrderMonth.setText(responseWrapper.getNow_onums());
			} else {
				layoutOrderNum.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 订单操作点击响应事件
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.order_item_img_camare) {// 点击拍照
			orderId = v.getTag().toString();
			if (mPopupWindow == null) {
				View popupWindow = getLayoutInflater().inflate(
						R.layout.popwindow_camare, null);
				mPopupWindow = new PopupWindow(popupWindow,
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.camare_popwin_bg));
				mPopupWindow.setFocusable(true);
				mPopupWindow.setOutsideTouchable(true);
				textCamareAfter = (TextView) popupWindow
						.findViewById(R.id.order_camare_after);
				textCamareBefore = (TextView) popupWindow
						.findViewById(R.id.order_camare_before);
				textCamareAfter.setOnClickListener(this);
				textCamareBefore.setOnClickListener(this);
			}

			mPopupWindow.showAsDropDown(v, -120, -200);

		} else if (v.getId() == R.id.order_camare_after) {
			openCamareActivity(OrderCamareActivity.CAMARE_TYPE_AFTER);
		} else if (v.getId() == R.id.order_camare_before) {
			openCamareActivity(OrderCamareActivity.CAMARE_TYPE_BEFORE);
		}

	}

	private void openCamareActivity(int type) {
		mPopupWindow.dismiss();
		Intent intent = new Intent();
		intent.putExtra(OrderCamareActivity.KEY_CAMARE_TYPE, type);
		intent.setClass(this, OrderCamareActivity.class);
		startActivity(intent);

	}

	/**
	 * 订单列表适配器
	 * 
	 * @author Administrator
	 * 
	 */
	class OrderListAdapter extends WashoesBaseAdapter<OrderItem> {

		public OrderListAdapter(Activity activity) {
			super(activity);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.order_item, null);
				viewHolder = new ViewHolder();
				viewHolder.textDate = (TextView) convertView
						.findViewById(R.id.order_item_text_data);
				viewHolder.textTime = (TextView) convertView
						.findViewById(R.id.order_item_text_time);
				viewHolder.textPrice = (TextView) convertView
						.findViewById(R.id.order_item_text_price);
				viewHolder.textUserName = (TextView) convertView
						.findViewById(R.id.order_item_text_username);
				viewHolder.textUserType = (TextView) convertView
						.findViewById(R.id.order_item_text_usertype);
				viewHolder.textPhone = (TextView) convertView
						.findViewById(R.id.order_item_text_phone);
				viewHolder.textID = (TextView) convertView
						.findViewById(R.id.order_item_text_id);
				viewHolder.imgCamare = (ImageView) convertView
						.findViewById(R.id.order_item_img_camare);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			final OrderItem oItem = orderList.get(position);
			viewHolder.textDate.setText(oItem.getServicetime());
			viewHolder.textPrice.setText("￥ " + oItem.getPay_price());
			viewHolder.textUserName.setText(oItem.getRealname());
			viewHolder.textUserType.setText("0".equals(oItem.getUtag()) ? "新用户"
					: "老用户");
			viewHolder.textPhone.setText(oItem.getMobile());
			viewHolder.textID.setText(oItem.getUid());
			viewHolder.imgCamare.setTag(oItem.getOrder_id());
			viewHolder.imgCamare.setOnClickListener(OrderListActivity.this);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(OrderListActivity.this,
							OrderInfoActivity.class);
					intent.putExtra("oid", oItem.getOrder_id());
					OrderListActivity.this.startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView textDate;
			TextView textTime;
			TextView textPrice;
			TextView textUserName;
			TextView textUserType;
			TextView textPhone;
			TextView textID;
			ImageView imgCamare;
		}

	}
}
