package com.cn.washoes.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.model.OrderItem;
import com.cn.washoes.model.Province;
import com.cn.washoes.model.Team;
import com.cn.washoes.util.Cst;
import com.cn.washoes.util.NetworkAction;
import com.example.clander.CalendarActivity;

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
	private List<Team> memberList = new ArrayList<Team>();
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
	public static final String ORDER_STATUS_SEARCH = "6"; // 订单查询
	public static final int SDATE_REQUEST_CODE = 1001;// 获取起始日期
	public static final int EDATE_REQUEST_CODE = 1002;// 获取结束日期
	public static boolean REQ_REFRESH = false;
	private String orderId;

	private boolean isLeader = false;// 是否是组长
	private String sdate;// 下单起始日期
	private String edate;// 下单结束日期
	private LinearLayout searchLayout;// 搜索订单模块
	private TextView searchNum;// 显示搜索模块的订单总数
	private TextView sdateTxt;// 选择起始日期
	private TextView edateTxt;// 选择结束日期
	private TextView searchBtn;// 查询按钮
	private Spinner member;// 组员选择器
	private String aid = "0";
	private MemberAdapter memberAdapter;//组员选择适配器

	/**
	 * 界面初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list_layout);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("订单");
		topTitleView.setBackImageViewVisable(View.GONE);
		nodata = (TextView) findViewById(R.id.nodataTxt);
		memberAdapter = new MemberAdapter(this);
		listView = (ListView) findViewById(R.id.listview);
		layoutOrderNum = (LinearLayout) findViewById(R.id.order_num_layout);
		searchLayout = (LinearLayout) findViewById(R.id.order_search_layout);
		sdateTxt = (TextView) findViewById(R.id.order_sdate);
		edateTxt = (TextView) findViewById(R.id.order_edate);
		searchBtn = (TextView) findViewById(R.id.order_search_btn);
		member = (Spinner) findViewById(R.id.order_member);
		member.setAdapter(memberAdapter);
		member.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				aid = memberList.get(arg2).getAid();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		sdateTxt.setOnClickListener(this);
		edateTxt.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		searchNum = (TextView) findViewById(R.id.order_search_num);
		textOrderAll = (TextView) findViewById(R.id.order_text_num_all);
		textOrderMonth = (TextView) findViewById(R.id.order_text_num);
		radioGroupType = (RadioGroup) findViewById(R.id.order_list_type);
		radioGroupType
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.order_list_search_radiobtn) {
							getMember();
							status = ORDER_STATUS_SEARCH;
							searchLayout.setVisibility(View.VISIBLE);
							// if (sdate == null) {
							// // getOrder();
							// orderList.clear();
							// adapter.notifyDataSetChanged();
							// nodata.setVisibility(View.VISIBLE);
							// // 关闭订单总数
							// layoutOrderNum.setVisibility(View.GONE);
							// } else {
//							nodata.setVisibility(View.GONE);
							searchOrder();
							// }

							return;
						}
						searchLayout.setVisibility(View.GONE);
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
		getCurrentDate();
		registerReceiver(receiver, new IntentFilter(Cst.GET_ORDER));
	}

	
	private void getCurrentDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String str = formatter.format(curDate);
		sdate=str;
		sdateTxt.setText(str);
		edate=str;
		edateTxt.setText(str);
	}
	/**
	 * 如果是组长的时候获取组员列表
	 */
	private void getMember() {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		requestWrapper.setOp("artificer");
		sendData(requestWrapper, NetworkAction.team_list);
	}

	/**
	 * 查询订单
	 */
	private void searchOrder() {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		requestWrapper.setOp("order");
		requestWrapper.setPer("10000");
		requestWrapper.setPage(page);
		requestWrapper.setSdate(sdate);
		requestWrapper.setEdate(edate);
		requestWrapper.setTaid(aid);
		requestWrapper.setShowDialog(true);
		sendData(requestWrapper, NetworkAction.list2);

	}

	/**
	 * 判断是否是组长
	 */
	private void checkInfo() {
		String s = MyApplication.getInfo().getRank_id();
		if (s.equals("2")) {
			isLeader = true;
			findViewById(R.id.order_list_search_radiobtn).setVisibility(
					View.VISIBLE);
			findViewById(R.id.order_list_cancel_radiobtn).setVisibility(
					View.GONE);
		} else {
			isLeader = false;
			findViewById(R.id.order_list_search_radiobtn).setVisibility(
					View.GONE);
			findViewById(R.id.order_list_cancel_radiobtn).setVisibility(
					View.VISIBLE);
		}

	}

	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	};

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Cst.GET_ORDER.equals(intent.getAction())) {
				getOrder();
			}
		}

	};

	/**
	 * 评价成功后重新刷新订单列表
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (REQ_REFRESH) {
			REQ_REFRESH = false;
			getOrder();
		}
	}

	/**
	 * 向后台发送订单列表请求
	 */
	private void getOrder() {
		checkInfo();
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		requestWrapper.setOp("order");
		requestWrapper.setPer("10000");
		requestWrapper.setPage(page);
		if (status != ORDER_STATUS_SEARCH)
			requestWrapper.setFlag(status);
		else
			requestWrapper.setFlag(ORDER_STATUS_FINISH);
		requestWrapper.setShowDialog(true);
		if (status == ORDER_STATUS_FINISH && "1".equals(page)) {
			requestWrapper.setIs_onum("1");
		} else {
			requestWrapper.setIs_onum("0");
		}
		sendData(requestWrapper, NetworkAction.list);
	}

	/**
	 * 解析服务端数据
	 */

	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		super.showResualt(responseWrapper, requestType);

		if (requestType == NetworkAction.list
				|| requestType == NetworkAction.list2) {

			if (responseWrapper.getList() != null) {
				if (requestType == NetworkAction.list2) {
					searchNum.setText(responseWrapper.getList().size() + "");
				}
				// Toast.makeText(OrderListActivity.this,"size-->"+
				// responseWrapper.getList().size(), Toast.LENGTH_SHORT).show();
				if (responseWrapper.getList().size() == 0) {
					nodata.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);

				} else {
					nodata.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				}
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

			checkOrderNum();
		} else if (requestType == NetworkAction.team_list) {
			if (responseWrapper.getTeam_list() != null) {
				if (!memberList.isEmpty())
					memberList.clear();
				memberList = responseWrapper.getTeam_list();
				Team team = new Team();
				team.setAid("0");
				team.setRealname("全部");
				memberList.add(0, team);
				memberAdapter.setDataList(memberList);
				memberAdapter.notifyDataSetChanged();
			}
		}

	}

	@Override
	public void getErrorMsg(NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.getErrorMsg(requestType);
		nodata.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
		Intent intent1 = new Intent(Cst.CLOSE_ORDER);
		// 发送广播
		sendBroadcast(intent1);
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
		// 点击选择起始日期
		else if (v.getId() == R.id.order_sdate) {
			Intent intent = new Intent().setClass(this, CalendarActivity.class);
			intent.putExtra("date", "sdate");
			startActivityForResult(intent, SDATE_REQUEST_CODE);
		}
		// 点击选择结束日期
		else if (v.getId() == R.id.order_edate) {
			Intent intent = new Intent().setClass(this, CalendarActivity.class);
			intent.putExtra("date", "edate");
			startActivityForResult(intent, EDATE_REQUEST_CODE);
		}
		// 点击查询
		else if (v.getId() == R.id.order_search_btn) {
			if (sdate == null) {
				Toast.makeText(OrderListActivity.this, "请选择起始日期",
						Toast.LENGTH_SHORT).show();
				return;
			} else if (edate == null) {
				Toast.makeText(OrderListActivity.this, "请选择结束日期",
						Toast.LENGTH_SHORT).show();
				return;
			}
			searchOrder();

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			if (requestCode == SDATE_REQUEST_CODE) {
				sdate = data.getStringExtra("sdate");
				sdateTxt.setText(sdate);
			} else if (requestCode == EDATE_REQUEST_CODE) {
				edate = data.getStringExtra("edate");
				edateTxt.setText(edate);
			}
		}

	}

	private void openCamareActivity(int type) {
		mPopupWindow.dismiss();
		Intent intent = new Intent();
		intent.putExtra(OrderCamareActivity.KEY_CAMARE_TYPE, type);
		intent.putExtra("order_id", orderId);
		intent.putExtra("flag", status);
		intent.setClass(this, OrderCamareActivity.class);
		startActivity(intent);

	}

	private Spanned getUserTypeHtml(String userType) {

		String html = "";
		if (userType != null) {
			html = "<font color=\"#8ccec4\">" + userType
					+ "/</font><font color=\"#e64626\">评</font>";

		}
		return Html.fromHtml(html);

	}

	/**
	 * 检查是否还有未查看的订单
	 */
	private void checkOrderNum() {
		if (orderList == null)
			return;
		int count = 0;
		for (int i = 0; i < orderList.size(); i++) {
			OrderItem oItemTemp = orderList.get(i);
			if ("0".equals(oItemTemp.getIs_read()))
				count++;
		}

		Intent intent1 = null;
		// 发送广播

		if (count < 1)
			intent1 = new Intent(Cst.CLOSE_ORDER);
		else
			intent1 = new Intent(Cst.OPEN_ORDER);
		sendBroadcast(intent1);
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
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
				viewHolder.viewNoRead = convertView
						.findViewById(R.id.order_item_view_no_read);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			final OrderItem oItem = orderList.get(position);
			if (oItem != null) {
				viewHolder.textDate.setText(oItem.getServicetime());
				viewHolder.textPrice.setText("￥ " + oItem.getPay_price());
				viewHolder.textUserName.setText(oItem.getRealname());
				// 组长的界面
				if (isLeader) {
					viewHolder.textUserType.setText(oItem.getArt_nickname());
				}
				// 组员的界面
				else {
					if (ORDER_STATUS_FINISH.equals(oItem.getFlag())
							&& "1".equals(oItem.getIs_comment())) {
						viewHolder.textUserType.setText(getUserTypeHtml("0"
								.equals(oItem.getUtag()) ? "新用户" : "老用户"));
					} else {
						viewHolder.textUserType.setText("0".equals(oItem
								.getUtag()) ? "新用户" : "老用户");
					}
				}

				viewHolder.textPhone.setText(oItem.getMobile());
				viewHolder.textID.setText(oItem.getOrder_id());

				if ("0".equals(oItem.getIs_read())) {
					viewHolder.viewNoRead.setVisibility(View.VISIBLE);
				} else {
					viewHolder.viewNoRead.setVisibility(View.GONE);
				}

				if (ORDER_STATUS_CANCEL.equals(oItem.getFlag())) {
					viewHolder.imgCamare.setVisibility(View.GONE);
				} else {
					viewHolder.imgCamare.setVisibility(View.VISIBLE);
					viewHolder.imgCamare.setTag(oItem.getOrder_id());
					viewHolder.imgCamare
							.setOnClickListener(OrderListActivity.this);
				}
				viewHolder.imgCamare.setTag(oItem.getOrder_id());
				viewHolder.imgCamare.setOnClickListener(OrderListActivity.this);
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						if (isLeader)
							intent.putExtra("isLeader", isLeader);
						intent.setClass(OrderListActivity.this,
								OrderInfoActivity.class);
						intent.putExtra("oid", oItem.getOrder_id());
						OrderListActivity.this.startActivity(intent);
						if ("0".equals(oItem.getIs_read())) {
							oItem.setIs_read("1");
							adapter.notifyDataSetChanged();
							checkOrderNum();
						}

					}
				});
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
			View viewNoRead;
		}

	}

	/**
	 * 组员适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class MemberAdapter extends WashoesBaseAdapter<Team> {

		public MemberAdapter(Activity activity) {
			super(activity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.country_adapter, null);
				viewHolder = new ViewHolder();
				viewHolder.name = (TextView) convertView
						.findViewById(R.id.textView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			Team team = memberList.get(position);
			viewHolder.name.setText(team.getRealname());
			return convertView;
		}

		class ViewHolder {
			private TextView name;
		}

	}
}
