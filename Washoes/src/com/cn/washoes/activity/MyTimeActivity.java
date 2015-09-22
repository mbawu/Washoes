package com.cn.washoes.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.model.DateInfo;
import com.cn.washoes.model.TimeInfo;
import com.cn.washoes.util.NetworkAction;

public class MyTimeActivity extends BaseActivity {

	private Button myTimeBtn;
	private CheckBox preDateCheckBox;
	private CheckBox nextDateCheckBox;
	private TextView textDate;
	private GridView mGridView;
	private TopTitleView topTitleView;
	private List<DateInfo> dateInfos;
	private List<TimeInfo> timeInfos;
	private int dateIndex = 0;

	private final String state_open = "1"; // 开启
	private final String state_close = "0";// 关闭

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_time_layout);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("我的时间");
		myTimeBtn = (Button) findViewById(R.id.my_time_btn);
		preDateCheckBox = (CheckBox) findViewById(R.id.my_time_pre_cb);
		nextDateCheckBox = (CheckBox) findViewById(R.id.my_time_next_cb);
		textDate = (TextView) findViewById(R.id.my_time_date_text);
		mGridView = (GridView) findViewById(R.id.my_time_gridview);
		getDateList();
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
			getTimeList();
		} else if (requestType == NetworkAction.time_list) {
			timeInfos = responseWrapper.getTime_list();
		} else if (requestType == NetworkAction.time_set_d) {

		} else if (requestType == NetworkAction.time_set_h) {

		}
	}

	private void getDateList() {

		RequestWrapper requestWrapper = new RequestWrapper();

		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());

		requestWrapper.setOp("artificer");

		requestWrapper.setDays("7");

		sendData(requestWrapper, NetworkAction.date_list);
	}

	private void getTimeList() {

		RequestWrapper requestWrapper = new RequestWrapper();

		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());

		requestWrapper.setOp("artificer");

		requestWrapper.setDate(dateInfos.get(0).getDate());

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
}
