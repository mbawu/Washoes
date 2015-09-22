package com.cn.washoes.person;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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
import com.cn.washoes.activity.WashoesBaseAdapter;
import com.cn.washoes.model.Msg;
import com.cn.washoes.model.Province;
import com.cn.washoes.person.LocationActivity.ProvinceAdapter.ViewHolder;
import com.cn.washoes.util.NetworkAction;

/**
 * 我的消息详情页面
 * @author Wu Jiang
 *
 */
public class MessageDetailActivity extends BaseActivity {

	private TopTitleView topTitleView;
	private TextView contentTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_msg_detail);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("我的消息");
		contentTxt=(TextView) findViewById(R.id.msg_content);
		contentTxt.setText(getIntent().getStringExtra("msg"));
	}

	
}
