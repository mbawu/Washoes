package com.cn.washoes.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.model.Order;

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
	private List<Order> orderList;
	public static boolean EVALUATE_SUCCESS = false;
	private String page = "1";
	private String status = "0";
	private static final String ORDER_STATUS_ACCEPT = "1"; // 待服务
	private static final String ORDER_STATUS_WAITING = "2"; // 服务中
	private static final String ORDER_STATUS_FINISH = "3"; // 已完成
	private static final String ORDER_STATUS_CANCEL = "4"; // 已取消

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
		radioGroupType = (RadioGroup) findViewById(R.id.order_list_type);
		radioGroupType
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.order_list_all_radiobtn:
							status = "0";
							break;
						case R.id.order_list_underway_radiobtn:
							status = ORDER_STATUS_WAITING;
							break;
						/*case R.id.order_list_cancel_radiobtn:
							status = ORDER_STATUS_CANCEL;
							break;*/
						}
						getOrder();
					}
				});

		adapter = new OrderListAdapter(this);
		listView.setAdapter(adapter);

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
		RequestWrapper request = new RequestWrapper();
		request.setShowDialog(true);
		// request.setIdentity(MyApplication.identity);
		// request.setStatus(status);
		// request.setPage(page);
		// sendDataByGet(request, NetworkAction.centerF_user_order);

		ConfirmDialog dlg = new ConfirmDialog(this);
		dlg.setTitle("提示");
		dlg.setMessage("请确认您已完成了服务，确定确认吗？");
		dlg.setOkButton("确认", new ConfirmDialog.OnClickListener() {

			@Override
			public void onClick(Dialog dialog, View view) {

			}
		});

		dlg.setCancelButton("暂不确认", new ConfirmDialog.OnClickListener() {

			@Override
			public void onClick(Dialog dialog, View view) {

			}
		});
		dlg.setSigleBtn();
		dlg.show();

		orderList = new ArrayList<Order>();
		orderList.add(new Order());
		orderList.add(new Order());
		adapter.setDataList(orderList);
		adapter.notifyDataSetChanged();

	}

	/**
	 * 解析服务端数据
	 */
	/*
	 * @Override public void showResualt(ResponseWrapper responseWrapper,
	 * NetworkAction requestType) { super.showResualt(responseWrapper,
	 * requestType); if (requestType == NetworkAction.centerF_user_order) {
	 * 
	 * } }
	 */

	/**
	 * 订单操作点击响应事件
	 */
	@Override
	public void onClick(View v) {

	}

	/**
	 * 订单列表适配器
	 * 
	 * @author Administrator
	 * 
	 */
	class OrderListAdapter extends WashoesBaseAdapter<Order> {

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
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

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
