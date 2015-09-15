package com.cn.washoes.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.washoes.R;
import com.cn.washoes.activity.WashoesBaseAdapter;
import com.cn.washoes.model.Area;
import com.cn.washoes.model.City;
import com.cn.washoes.model.Province;
import com.cn.washoes.util.NetworkAction;

/**
 * 服务位置页面
 * 
 * @author Wu Jiang
 * 
 */
public class LocationActivity extends BaseActivity implements
		OnItemSelectedListener, OnClickListener {

	private Spinner pSpinner;// 显示省份控件
	private ProvinceAdapter pAdapter;// 省份适配器
	private Spinner cSpinner; // 显示城市控件
	private CityAdapter cAdapter;// 城市适配器
	private Spinner aSpinner;// 显示区域控件
	private AreaAdapter aAdapter;// 地区适配器
	private String province_id;// 省级ID
	private String province_name;// 省级名称
	private String city_id;// 市级ID
	private String city_name;// 市级名称
	private String area_id;// 县级ID
	private String area_name;// 县级名称
	private EditText addressTxt;// 详细地址
	private TextView addAreaBtn;// 添加区域按钮
	private TextView deleteAreaBtn;// 删除区域按钮
	
	private LinearLayout layout1;//第一组服务区域
	private Spinner area1;
	private Spinner km1;
	private LinearLayout layout2;//第二组服务区域
	private Spinner area2;
	private Spinner km2;
	private LinearLayout layout3;//第三组服务区域
	private Spinner area3;
	private Spinner km3;
	private LinearLayout layout4;//第四组服务区域
	private Spinner area4;
	private Spinner km4;
	private LinearLayout layout5;//第五组服务区域
	private Spinner area5;
	private Spinner km5;
	private LinearLayout layout6;//按钮容器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_location);
		initView();
		getData();

	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		pSpinner = (Spinner) findViewById(R.id.loc_province);
		cSpinner = (Spinner) findViewById(R.id.loc_city);
		aSpinner = (Spinner) findViewById(R.id.loc_area);
		pSpinner.setOnItemSelectedListener(this);
		cSpinner.setOnItemSelectedListener(this);
		aSpinner.setOnItemSelectedListener(this);
		addressTxt = (EditText) findViewById(R.id.loc_detail);
		addAreaBtn = (TextView) findViewById(R.id.btn_add);
		deleteAreaBtn = (TextView) findViewById(R.id.btn_del);
		addAreaBtn.setOnClickListener(this);
		deleteAreaBtn.setOnClickListener(this);
		
		
		layout1=(LinearLayout) findViewById(R.id.loc_layout1);
		area1=(Spinner) findViewById(R.id.loc_area1);
		km1=(Spinner) findViewById(R.id.loc_km1);
		layout2=(LinearLayout) findViewById(R.id.loc_layout2);
		area2=(Spinner) findViewById(R.id.loc_area2);
		km2=(Spinner) findViewById(R.id.loc_km2);
		layout3=(LinearLayout) findViewById(R.id.loc_layout3);
		area3=(Spinner) findViewById(R.id.loc_area3);
		km3=(Spinner) findViewById(R.id.loc_km3);
		layout4=(LinearLayout) findViewById(R.id.loc_layout4);
		area4=(Spinner) findViewById(R.id.loc_area4);
		km4=(Spinner) findViewById(R.id.loc_km4);
		layout5=(LinearLayout) findViewById(R.id.loc_layout5);
		area5=(Spinner) findViewById(R.id.loc_area5);
		km5=(Spinner) findViewById(R.id.loc_km5);
		layout6=(LinearLayout) findViewById(R.id.loc_layout6);
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp("pos");
		sendData(requestWrapper, NetworkAction.province);
	}

	/**
	 * 重置服务区域
	 */
	private void resetLoc()
	{
		
	}
	
	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if (requestType == NetworkAction.province) {
			pAdapter = new ProvinceAdapter(this);
			pAdapter.setDataList(responseWrapper.getProvince());
			pSpinner.setAdapter(pAdapter);
			pAdapter.notifyDataSetChanged();
		} else if (requestType == NetworkAction.city) {
			cAdapter = new CityAdapter(this);
			cAdapter.setDataList(responseWrapper.getCity());
			cSpinner.setAdapter(cAdapter);
			cAdapter.notifyDataSetChanged();
		} else if (requestType == NetworkAction.area) {
			aAdapter = new AreaAdapter(this);
			aAdapter.setDataList(responseWrapper.getArea());
			aSpinner.setAdapter(aAdapter);
			aAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Object object = parent.getItemAtPosition(position);
		RequestWrapper request = new RequestWrapper();
		request.setOp("pos");
		if (object instanceof Province) {
			province_id = ((Province) object).getProvince_id();
			province_name = ((Province) object).getProvince_name();
			request.setProvince_id(province_id);
			sendData(request, NetworkAction.city);
		}

		else if (object instanceof City) {
			city_id = ((City) object).getCity_id();
			city_name = ((City) object).getCity_name();
			request.setCity_id(city_id);
			sendData(request, NetworkAction.area);
		} else if (object instanceof Area) {
			area_id = ((Area) object).getArea_id();
			area_name = ((Area) object).getArea_name();
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 省份适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class ProvinceAdapter extends WashoesBaseAdapter<Province> {

		public ProvinceAdapter(Activity activity) {
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

	/**
	 * 城市适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class CityAdapter extends WashoesBaseAdapter<City> {

		public CityAdapter(Activity activity) {
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
			City city = getDataList().get(position);
			viewHolder.name.setText(city.getCity_name());
			return convertView;
		}

		class ViewHolder {
			private TextView name;
		}

	}

	/**
	 * 区域适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class AreaAdapter extends WashoesBaseAdapter<Area> {

		public AreaAdapter(Activity activity) {
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
			Area area = getDataList().get(position);
			viewHolder.name.setText(area.getArea_name());
			return convertView;
		}

		class ViewHolder {
			private TextView name;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 添加区域按钮
		case R.id.btn_add:

			break;
		//删除区域按钮
		case R.id.btn_del:

			break;
		}

	}

}
