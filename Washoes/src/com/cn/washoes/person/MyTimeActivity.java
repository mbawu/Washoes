package com.cn.washoes.person;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.activity.WashoesBaseAdapter;
import com.cn.washoes.model.DateInfo;
import com.cn.washoes.model.TimeInfo;
import com.cn.washoes.util.NetworkAction;

/**
 * 我的时间页面
 * 
 * @author Wu Jiang
 * 
 */
public class MyTimeActivity extends BaseActivity {

	private Button myTimeBtn;
	private TextView preDateCheckBox;
	private TextView nextDateCheckBox;
	private TextView textDate;// 我的时间
	private GridView mGridView;
	private TopTitleView topTitleView;
	private List<DateInfo> dateInfos;
	private List<TimeInfo> timeInfos;
	private int dateIndex = 0;

	private final String state_open = "1"; // 开启
	private final String state_close = "0";// 关闭

	private TimeAdapter adapter;
	private int day = 1;// 表示几天

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_time_layout);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("我的时间");
		myTimeBtn = (Button) findViewById(R.id.my_time_btn);
		preDateCheckBox = (TextView) findViewById(R.id.my_time_pre_cb);
		nextDateCheckBox = (TextView) findViewById(R.id.my_time_next_cb);
		textDate = (TextView) findViewById(R.id.my_time_date_text);
		mGridView = (GridView) findViewById(R.id.my_time_gridview);
		adapter = new TimeAdapter(this);
		mGridView.setAdapter(adapter);
		getDateList();
		// getTimeList();
	}

	/**
	 * 数据解析
	 */
	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		super.showResualt(responseWrapper, requestType);
		if (requestType == NetworkAction.date_list) {
			dateInfos = responseWrapper.getDate_list();
			DateInfo info = dateInfos.get(day);
			textDate.setText(info.getDate_str() + "（" + info.getWeek_str()
					+ "）");
			getTimeList();
		} else if (requestType == NetworkAction.time_list) {
			timeInfos = responseWrapper.getTime_list();
			adapter.setDataList(timeInfos);
			adapter.notifyDataSetChanged();
		} else if (requestType == NetworkAction.time_set_d) {

		} else if (requestType == NetworkAction.time_set_h) {

		}
	}

	/**
	 * 获取日期列表
	 */
	private void getDateList() {

		RequestWrapper requestWrapper = new RequestWrapper();

		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());

		requestWrapper.setOp("artificer");

		requestWrapper.setDays("7");
		requestWrapper.setShowDialog(true);
		sendData(requestWrapper, NetworkAction.date_list);
	}

	/**
	 * 获取该日期内的所有
	 */
	private void getTimeList() {

		RequestWrapper requestWrapper = new RequestWrapper();

		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());

		requestWrapper.setOp("artificer");

		requestWrapper.setDate(dateInfos.get(day).getDate());

		sendData(requestWrapper, NetworkAction.time_list);
	}

	private void setTimeState(String timeId, String flag) {

		RequestWrapper requestWrapper = new RequestWrapper();

		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());

		requestWrapper.setOp("artificer");

		requestWrapper.setTime_id(timeId);

		sendData(requestWrapper, NetworkAction.time_set_h);
	}

	private void setDateState(String date, String flag) {

		RequestWrapper requestWrapper = new RequestWrapper();

		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());

		requestWrapper.setOp("artificer");

		requestWrapper.setDate(date);
		requestWrapper.setFlag(flag);

		sendData(requestWrapper, NetworkAction.time_set_h);
	}

	/**
	 * 时间适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class TimeAdapter extends WashoesBaseAdapter<TimeInfo> {

		public TimeAdapter(Activity activity) {
			super(activity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.time_adapter_item, null);
				viewHolder = new ViewHolder();
				viewHolder.layout = (LinearLayout) convertView
						.findViewById(R.id.time_layout);
				viewHolder.state = (TextView) convertView
						.findViewById(R.id.time_state);
				viewHolder.hour = (TextView) convertView
						.findViewById(R.id.time_hour);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			final TimeInfo time = getDataList().get(position);
			viewHolder.hour.setText(time.getTime_hour());
			// 已失效
			if (time.getIs_server().equals("-1")) {
				viewHolder.state.setText("已失效");
				viewHolder.state.setTextColor(getResources().getColor(
						R.color.blank));
				viewHolder.hour.setTextColor(getResources().getColor(
						R.color.blank));
				viewHolder.layout.setBackgroundColor(getResources().getColor(
						R.color.wihte));
			}
			// 已预约
			else if (time.getIs_server().equals("0")) {
				viewHolder.state.setText("已预约");
				viewHolder.state.setTextColor(getResources().getColor(
						R.color.wihte));
				viewHolder.hour.setTextColor(getResources().getColor(
						R.color.wihte));
				viewHolder.layout.setBackgroundColor(getResources().getColor(
						R.color.yellow));
			}
			// 未预约
			else if (time.getIs_server().equals("1")) {
				viewHolder.state.setText("未预约");
				viewHolder.state.setTextColor(getResources().getColor(
						R.color.blank));
				viewHolder.hour.setTextColor(getResources().getColor(
						R.color.blank));
				viewHolder.layout.setBackgroundColor(getResources().getColor(
						R.color.green));
				viewHolder.layout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						time.setIs_server("-1");
						notifyDataSetChanged();
					}
				});
			}
			// 流失
			else if (time.getIs_server().equals("2")) {
				viewHolder.state.setText("已流失");
				viewHolder.state.setTextColor(getResources().getColor(
						R.color.blank));
				viewHolder.hour.setTextColor(getResources().getColor(
						R.color.blank));
				viewHolder.layout.setBackgroundColor(getResources().getColor(
						R.color.wihte));
				viewHolder.layout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(MyTimeActivity.this, "已流失的时间无法设置",
								Toast.LENGTH_SHORT);

					}
				});
			}

			return convertView;
		}

		class ViewHolder {
			private LinearLayout layout;
			private TextView state;
			private TextView hour;
		}

	}

}
