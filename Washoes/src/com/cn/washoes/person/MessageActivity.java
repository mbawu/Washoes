package com.cn.washoes.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.activity.WashoesBaseAdapter;
import com.cn.washoes.model.Province;
import com.cn.washoes.person.LocationActivity.ProvinceAdapter.ViewHolder;

/**
 * 我的消息页面
 * @author Wu Jiang
 *
 */
public class MessageActivity extends BaseActivity {

	private TopTitleView topTitleView;
	private TextView nodata;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person);
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("我的消息");
		nodata = (TextView) findViewById(R.id.nodataTxt);
		listView = (ListView) findViewById(R.id.listview);
	}
	
	
	
	/**
	 * 省份适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class MsgAdapter extends WashoesBaseAdapter<Province> {

		public MsgAdapter(Activity activity) {
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
			Province province = getDataList().get(position);
			viewHolder.name.setText(province.getProvince_name());
			return convertView;
		}

		class ViewHolder {
			private TextView name;
		}

	}
}
