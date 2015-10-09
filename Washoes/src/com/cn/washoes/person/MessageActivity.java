package com.cn.washoes.person;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.activity.MenuTable;
import com.cn.washoes.activity.WashoesBaseAdapter;
import com.cn.washoes.model.Msg;
import com.cn.washoes.model.OrderItem;
import com.cn.washoes.model.Province;
import com.cn.washoes.person.LocationActivity.ProvinceAdapter.ViewHolder;
import com.cn.washoes.util.Cst;
import com.cn.washoes.util.NetworkAction;

/**
 * 我的消息页面
 * 
 * @author Wu Jiang
 * 
 */
public class MessageActivity extends BaseActivity {

	private TopTitleView topTitleView;
	private TextView nodata;
	private ListView listView;
	private MsgAdapter adapter;
	private String content;
	private String msgID;
	private List<Msg> datas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_msg);
		initView();
		getMsg();
		registerReceiver(receiver, new IntentFilter(Cst.GET_MSG));
	}

	/**
	 * 获取消息列表
	 */
	private void getMsg() {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp("artificer");
		requestWrapper.setPage("1");
		requestWrapper.setPer("10000");
		requestWrapper.setShowDialog(true);
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		sendData(requestWrapper, NetworkAction.msg_list);
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("我的消息");
		nodata = (TextView) findViewById(R.id.nodataTxt);
		listView = (ListView) findViewById(R.id.listview);
		adapter = new MsgAdapter(this);
	}

	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if (requestType == NetworkAction.msg_list) {
			datas = responseWrapper.getMsg_list();
			if (datas.size() > 0) {
				listView.setVisibility(View.VISIBLE);
				nodata.setVisibility(View.GONE);
				adapter.setDataList(datas);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				listView.setVisibility(View.GONE);
				nodata.setVisibility(View.VISIBLE);
			}
			checkMsgNum();
		} else if (requestType == NetworkAction.msg_detail) {
			Intent intent = new Intent();
			intent.setClass(MessageActivity.this, MessageDetailActivity.class);
			intent.putExtra("msg", content);
			startActivity(intent);
		}
	}

	/**
	 * 获取消息详情
	 * 
	 * @param msg_id
	 */
	public void getMsgDetail(String msg_id) {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp("artificer");
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		requestWrapper.setShowDialog(true);
		requestWrapper.setMsg_id(msg_id);
		sendData(requestWrapper, NetworkAction.msg_detail);
	}

	/**
	 * 消息适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class MsgAdapter extends WashoesBaseAdapter<Msg> {

		public MsgAdapter(Activity activity) {
			super(activity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.msg_adapter_item, null);
				viewHolder = new ViewHolder();
				viewHolder.layout = (LinearLayout) convertView
						.findViewById(R.id.msg_layout);
				viewHolder.content = (TextView) convertView
						.findViewById(R.id.msg_content);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			final Msg msg = datas.get(position);
			viewHolder.content.setText(msg.getContent());
			if (msg.getIs_read().equals("1"))
				viewHolder.layout.setBackgroundResource(R.drawable.box_disable);
			else
				viewHolder.layout.setBackgroundResource(R.drawable.box_blue);

			viewHolder.layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 打开消息详情页面
					getMsgDetail(msg.getMsg_id());
					content = msg.getContent();
					if (msg.getIs_read().equals("0")) {
						msg.setIs_read("1");
						checkMsgNum();
					}

					notifyDataSetChanged();
				}
			});

			return convertView;
		}

		class ViewHolder {
			private LinearLayout layout;
			private TextView content;
		}

	}

	private void checkMsgNum() {
		if (datas == null)
			return;
		int count = 0;
		for (int i = 0; i < datas.size(); i++) {
			Msg msgTemp = datas.get(i);
			if ("0".equals(msgTemp.getIs_read()))
				count++;
		}
		Log.i("test", "count-->"+count);
		Intent intent1 = null;
		// 发送广播

		if (count < 1)
			intent1 = new Intent(Cst.CLOSE_MSG);
		else
			intent1 = new Intent(Cst.OPEN_MSG);
		sendBroadcast(intent1);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Cst.GET_MSG.equals(intent.getAction())) {
				getMsg();
			}
		}

	};

	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();

	};
}
