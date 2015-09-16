package com.cn.washoes.person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.activity.WashoesBaseAdapter;
import com.cn.washoes.model.Area;
import com.cn.washoes.model.City;
import com.cn.washoes.model.Distance;
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

	
	private LinearLayout createLayout;//创建服务位置时候的整个layout
	private Spinner pSpinner;// 显示省份控件
	private ProvinceAdapter pAdapter;// 省份适配器
	private Spinner cSpinner; // 显示城市控件
	private CityAdapter cAdapter;// 城市适配器
	private Spinner aSpinner;// 显示区域控件
	private AreaAdapter aAdapter;// 地区适配器
	private AreaAdapter noneAdapter;// 默认未添加适配器
	private KMAdapter kmAdapter;// 默认未添加公里数适配器
	private String province_id;// 省级ID
	private String province_name;// 省级名称
	private String city_id;// 市级ID
	private String city_name;// 市级名称
	private String area_id;// 县级ID
	private String area_name;// 县级名称
	private EditText addressTxt;// 详细地址
	private TextView addAreaBtn;// 添加区域按钮
	private TextView deleteAreaBtn;// 删除区域按钮

	private LinearLayout layout1;// 第一组服务区域
	private Spinner area1;
	private String area1_id;
	private String km1_distance;
	private Spinner km1;
	// private TextView delete1;
	private LinearLayout layout2;// 第二组服务区域
	private Spinner area2;
	private String area2_id;
	private String km2_distance;
	private Spinner km2;
	private TextView delete2;
	private LinearLayout layout3;// 第三组服务区域
	private Spinner area3;
	private String area3_id;
	private String km3_distance;
	private Spinner km3;
	private TextView delete3;
	private LinearLayout layout4;// 第四组服务区域
	private Spinner area4;
	private String area4_id;
	private String km4_distance;
	private Spinner km4;
	private TextView delete4;
	private LinearLayout layout5;// 第五组服务区域
	private Spinner area5;
	private String area5_id;
	private String km5_distance;
	private Spinner km5;
	private TextView delete5;
	private LinearLayout layout6;// 按钮容器
	private int areaNum = 1;
	private boolean deleteModule = false;

	private TopTitleView topTitleView;// 标题栏
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_location);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("服务位置");
		createLayout=(LinearLayout) findViewById(R.id.create_layout);
		initViewNew();
		getData();

	}

	/**
	 * 初始化界面
	 */
	private void initViewNew() {
		createLayout.setVisibility(View.VISIBLE);
		topTitleView.setRightBtnText("保存", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveLoc();
				
			}

		
		});
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
		// 区域默认值设置
		noneAdapter = new AreaAdapter(this);
		ArrayList<Area> defaultList = new ArrayList<Area>();
		Area area = new Area();
		area.setArea_id("1001");
		area.setArea_name("未添加");
		defaultList.add(area);
		noneAdapter.setDataList(defaultList);
		// 公里数适配器设置默认值
		kmAdapter = new KMAdapter(this);
		Distance distance = new Distance();
		distance.setDistance("0");
		distance.setDistance_name("未添加");
		ArrayList<Distance> defaultList2 = new ArrayList<Distance>();
		defaultList2.add(distance);
		kmAdapter.setDataList(defaultList2);

		layout1 = (LinearLayout) findViewById(R.id.loc_layout1);
		area1 = (Spinner) findViewById(R.id.loc_area1);
		km1 = (Spinner) findViewById(R.id.loc_km1);
		// delete1= (TextView) findViewById(R.id.loc_area1_delete);
		layout2 = (LinearLayout) findViewById(R.id.loc_layout2);
		area2 = (Spinner) findViewById(R.id.loc_area2);
		km2 = (Spinner) findViewById(R.id.loc_km2);
		delete2 = (TextView) findViewById(R.id.loc_area2_delete);
		layout3 = (LinearLayout) findViewById(R.id.loc_layout3);
		area3 = (Spinner) findViewById(R.id.loc_area3);
		km3 = (Spinner) findViewById(R.id.loc_km3);
		delete3 = (TextView) findViewById(R.id.loc_area3_delete);
		layout4 = (LinearLayout) findViewById(R.id.loc_layout4);
		area4 = (Spinner) findViewById(R.id.loc_area4);
		km4 = (Spinner) findViewById(R.id.loc_km4);
		delete4 = (TextView) findViewById(R.id.loc_area4_delete);
		layout5 = (LinearLayout) findViewById(R.id.loc_layout5);
		area5 = (Spinner) findViewById(R.id.loc_area5);
		km5 = (Spinner) findViewById(R.id.loc_km5);
		delete5 = (TextView) findViewById(R.id.loc_area5_delete);
		layout6 = (LinearLayout) findViewById(R.id.loc_layout6);
		area1.setOnItemSelectedListener(this);
		km1.setOnItemSelectedListener(this);
		area2.setOnItemSelectedListener(this);
		km2.setOnItemSelectedListener(this);
		area3.setOnItemSelectedListener(this);
		km3.setOnItemSelectedListener(this);
		area4.setOnItemSelectedListener(this);
		km4.setOnItemSelectedListener(this);
		area5.setOnItemSelectedListener(this);
		km5.setOnItemSelectedListener(this);
		delete2.setOnClickListener(this);
		delete3.setOnClickListener(this);
		delete4.setOnClickListener(this);
		delete5.setOnClickListener(this);
		
		resetLoc();
	}

	/**
	 * 保存服务位置方法
	 */
	private void saveLoc() {
		if(addressTxt.getText().toString().equals(""))
		{
			Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
			return;
		}
		RequestWrapper requestWrapper=new RequestWrapper();
		requestWrapper.setOp("artificer");
		requestWrapper.setProvince_id(province_id);
		requestWrapper.setProvince_name(province_name);
		requestWrapper.setCity_id(city_id);
		requestWrapper.setCity_name(city_name);
		requestWrapper.setArea_id(area_id);
		requestWrapper.setArea_name(area_name);
		requestWrapper.setAddress(addressTxt.getText().toString());
		requestWrapper.setPos_json(getJson());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		sendData(requestWrapper, NetworkAction.pos_in);
	}
	/**
	 * 拼接字符串
	 */
	private String getJson()
	{
		StringBuilder builder=new StringBuilder();
		builder.append("[");
		if(area1_id!=null)
		{
			builder.append("{");
			builder.append("\"area_id\":"+area1_id+",");
			builder.append("\"distance\":"+km1_distance);
			builder.append("}");
			
		}
		if(area2_id!=null)
		{
			builder.append(",");
			builder.append("{");
			builder.append("\"area_id\":"+area2_id+",");
			builder.append("\"distance\":"+km2_distance);
			builder.append("}");
		}
		if(area3_id!=null)
		{
			builder.append(",");
			builder.append("{");
			builder.append("\"area_id\":"+area3_id+",");
			builder.append("\"distance\":"+km3_distance);
			builder.append("}");
			
		}
		if(area4_id!=null)
		{
			builder.append(",");
			builder.append("{");
			builder.append("\"area_id\":"+area4_id+",");
			builder.append("\"distance\":"+km4_distance);
			builder.append("}");
			
		}
		if(area5_id!=null)
		{
			builder.append(",");
			builder.append("{");
			builder.append("\"area_id\":"+area5_id+",");
			builder.append("\"distance\":"+km5_distance);
			builder.append("}");
		}
		builder.append("]");
		return builder.toString();
	}
	
	
	/**
	 * 获取数据
	 */
	private void getData() {
		// 获取城市数据
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp("pos");
		sendData(requestWrapper, NetworkAction.province);
		// 获取公里数
		sendData(requestWrapper, NetworkAction.distance);
	}

	/**
	 * 重置服务区域
	 */
	private void resetLoc() {
		deleteAreaBtn.setText("删除");
		deleteModule = false;
		delete2.setVisibility(View.GONE);
		delete3.setVisibility(View.GONE);
		delete4.setVisibility(View.GONE);
		delete5.setVisibility(View.GONE);
		areaNum = 1;
		layout1.setVisibility(View.VISIBLE);
		area1.setAdapter(noneAdapter);
		km1.setAdapter(kmAdapter);
		layout2.setVisibility(View.GONE);
		area2.setAdapter(noneAdapter);
		km2.setAdapter(kmAdapter);
		delete2.setVisibility(View.GONE);
		layout3.setVisibility(View.GONE);
		area3.setAdapter(noneAdapter);
		km3.setAdapter(kmAdapter);
		delete3.setVisibility(View.GONE);
		layout4.setVisibility(View.GONE);
		area4.setAdapter(noneAdapter);
		km4.setAdapter(kmAdapter);
		delete4.setVisibility(View.GONE);
		layout5.setVisibility(View.GONE);
		area5.setAdapter(noneAdapter);
		km5.setAdapter(kmAdapter);
		delete5.setVisibility(View.GONE);
		layout6.setVisibility(View.VISIBLE);
		addAreaBtn.setVisibility(View.VISIBLE);
		deleteAreaBtn.setVisibility(View.INVISIBLE);
		noneAdapter.notifyDataSetChanged();
		kmAdapter.notifyDataSetChanged();
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
			resetLoc();
		} else if (requestType == NetworkAction.city) {
			cAdapter = new CityAdapter(this);
			cAdapter.setDataList(responseWrapper.getCity());
			cSpinner.setAdapter(cAdapter);
			cAdapter.notifyDataSetChanged();
			resetLoc();
		} else if (requestType == NetworkAction.area) {
			aAdapter = new AreaAdapter(this);
			aAdapter.setDataList(responseWrapper.getArea());
			aSpinner.setAdapter(aAdapter);
			area1.setAdapter(aAdapter);
			area2.setAdapter(aAdapter);
			area3.setAdapter(aAdapter);
			area4.setAdapter(aAdapter);
			area5.setAdapter(aAdapter);
			aAdapter.notifyDataSetChanged();
		} else if (requestType == NetworkAction.distance) {
			kmAdapter = new KMAdapter(this);
			kmAdapter.setDataList(responseWrapper.getDistance());
			km1.setAdapter(kmAdapter);
			km2.setAdapter(kmAdapter);
			km3.setAdapter(kmAdapter);
			km4.setAdapter(kmAdapter);
			km5.setAdapter(kmAdapter);
			kmAdapter.notifyDataSetChanged();
		}
		//设置服务位置成功
		else if(requestType == NetworkAction.pos_in)
		{
			Toast.makeText(this, responseWrapper.getMsg(), Toast.LENGTH_SHORT).show();
			getLocation();
			createLayout.setVisibility(View.GONE);
			topTitleView.setRightBtnText("修改", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}
		//获取服务位置
		else if(requestType == NetworkAction.pos_list)
		{
			
		}
	}

	/**
	 * 获取服务地址信息
	 */
	private void getLocation()
	{
		RequestWrapper requestWrapper=new RequestWrapper();
		requestWrapper.setOp("artificer");
		requestWrapper.setPage("1");
		requestWrapper.setPer("1");
		requestWrapper.setPos("1");
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		sendData(requestWrapper, NetworkAction.pos_list);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Object object = parent.getItemAtPosition(position);
		RequestWrapper request = new RequestWrapper();
		request.setOp("pos");
		switch (parent.getId()) {

		case R.id.loc_province:
			province_id = ((Province) object).getProvince_id();
			province_name = ((Province) object).getProvince_name();
			request.setProvince_id(province_id);
			sendData(request, NetworkAction.city);
			break;
		case R.id.loc_city:
			city_id = ((City) object).getCity_id();
			city_name = ((City) object).getCity_name();
			request.setCity_id(city_id);
			sendData(request, NetworkAction.area);
			break;
		case R.id.loc_area:
			area_id = ((Area) object).getArea_id();
			area_name = ((Area) object).getArea_name();
			break;
		case R.id.loc_area1:
			area1_id=((Area) object).getArea_id();
			break;
		case R.id.loc_area2:
			area2_id=((Area) object).getArea_id();
			break;
		case R.id.loc_area3:
			area3_id=((Area) object).getArea_id();
			break;
		case R.id.loc_area4:
			area4_id=((Area) object).getArea_id();
			break;
		case R.id.loc_area5:
			area5_id=((Area) object).getArea_id();
			break;
		case R.id.loc_km1:
			km1_distance=((Distance) object).getDistance();
			break;
		case R.id.loc_km2:
			km2_distance=((Distance) object).getDistance();
			break;
		case R.id.loc_km3:
			km3_distance=((Distance) object).getDistance();
			break;
		case R.id.loc_km4:
			km4_distance=((Distance) object).getDistance();
			break;
		case R.id.loc_km5:
			km5_distance=((Distance) object).getDistance();
			break;
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

	/**
	 * 区域适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class KMAdapter extends WashoesBaseAdapter<Distance> {

		public KMAdapter(Activity activity) {
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
			Distance distance = getDataList().get(position);
			viewHolder.name.setText(distance.getDistance_name());
			return convertView;
		}

		class ViewHolder {
			private TextView name;
		}

	}

	/**
	 * 检查哪个控件被关闭了按顺序往下打开
	 */
	private void checkLayout() {
		if (layout2.getVisibility() != View.VISIBLE)
			layout2.setVisibility(View.VISIBLE);
		else if (layout3.getVisibility() != View.VISIBLE)
			layout3.setVisibility(View.VISIBLE);
		else if (layout4.getVisibility() != View.VISIBLE)
			layout4.setVisibility(View.VISIBLE);
		else if (layout5.getVisibility() != View.VISIBLE)
			layout5.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 添加区域按钮
		case R.id.btn_add:

			// 在删除模式下无法添加
			if (!deleteModule) {
					checkLayout();
					areaNum++;
			} else {
				Toast.makeText(this, "请先取消删除模式", Toast.LENGTH_SHORT).show();
			}

			checkBtn();
			break;
		// 删除区域按钮
		case R.id.btn_del:
			if (deleteAreaBtn.getText().toString().equals("删除")) {
				deleteAreaBtn.setText("取消");
				deleteModule = true;
				delete2.setVisibility(View.VISIBLE);
				delete3.setVisibility(View.VISIBLE);
				delete4.setVisibility(View.VISIBLE);
				delete5.setVisibility(View.VISIBLE);
			} else {
				deleteAreaBtn.setText("删除");
				deleteModule = false;
				delete2.setVisibility(View.GONE);
				delete3.setVisibility(View.GONE);
				delete4.setVisibility(View.GONE);
				delete5.setVisibility(View.GONE);
			}

			break;
		// 删除区域组2
		case R.id.loc_area2_delete:
			areaNum--;
			layout2.setVisibility(View.GONE);
			delete2.setVisibility(View.GONE);
			area2.setSelection(0);
			km2.setSelection(0);
			area2_id=null;
			checkBtn();
			break;
		// 删除区域组3
		case R.id.loc_area3_delete:
			areaNum--;
			delete3.setVisibility(View.GONE);
			layout3.setVisibility(View.GONE);
			area3.setSelection(0);
			km3.setSelection(0);
			area3_id=null;
			checkBtn();
			break;
		// 删除区域组4
		case R.id.loc_area4_delete:
			areaNum--;
			delete4.setVisibility(View.GONE);
			layout4.setVisibility(View.GONE);
			area4.setSelection(0);
			km4.setSelection(0);
			area4_id=null;
			checkBtn();
			break;
		// 删除区域组5
		case R.id.loc_area5_delete:
			areaNum--;
			delete5.setVisibility(View.GONE);
			layout5.setVisibility(View.GONE);
			area5.setSelection(0);
			km5.setSelection(0);
			area5_id=null;
			checkBtn();
			break;
		}

	}

	/**
	 * 检查添加和删除按钮的状态
	 */
	private void checkBtn() {
		if (areaNum == 1) {
			addAreaBtn.setVisibility(View.VISIBLE);
			deleteAreaBtn.setVisibility(View.INVISIBLE);
			deleteAreaBtn.setText("删除");
			deleteModule = false;
		} else if (areaNum > 1 && areaNum < 5) {
			deleteAreaBtn.setVisibility(View.VISIBLE);
			addAreaBtn.setVisibility(View.VISIBLE);
		} else if (areaNum == 5) {
			deleteAreaBtn.setVisibility(View.VISIBLE);
			addAreaBtn.setVisibility(View.INVISIBLE);
		}
	}
}
