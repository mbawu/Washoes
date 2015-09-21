package com.cn.washoes.person;

import java.util.ArrayList;

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
import android.widget.ListView;
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
import com.cn.washoes.model.LocArea;
import com.cn.washoes.model.LocInfo;
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

	// private AreaAdapter noneAdapter;// 默认未添加适配器
	private KMAdapter kmAdapter;// 默认未添加公里数适配器

	private LinearLayout layout6;// 按钮容器

	private String province_id;// 省级ID
	private String province_name;// 省级名称
	private String city_id;// 市级ID
	private String city_name;// 市级名称
	private String area_id;// 县级ID
	private String area_name;// 县级名称
	private EditText addressTxt;// 详细地址
	private TextView addAreaBtn;// 添加区域按钮
	private TextView deleteAreaBtn;// 删除区域按钮
	private Spinner pSpinner;// 显示省份控件
	private ProvinceAdapter pAdapter;// 省份适配器
	private Spinner cSpinner; // 显示城市控件
	private CityAdapter cAdapter;// 城市适配器
	private Spinner aSpinner;// 显示区域控件
	private LinearLayout createLayout;// 创建服务位置时候的整个layout
	private AreaAdapter aAdapter;// 地区适配器
	private LinearLayout loadLayout;// 放置服务位置的layout
	private TextView loadAddress;// 位置地址
	private TopTitleView topTitleView;// 标题栏
	private LocInfo info;// 位置信息
	private ArrayList<LocArea> areaList;// 未编辑时候设置的区域的数据集合
	private ShowAreaAdapter showAreaAdapter;
	private ListView showListView;// 未编辑时候显示区域的listview
	private ListView areaListView;// 编辑时候显示区域的listview
	private ArrayList<Area> areaData;// 所有的区域的集合
	private ArrayList<Distance> disList;
	private AreaLVAdapter areaLVAdapter;// 编辑时候的区域
	private boolean loadArea = false;// 是否是加载区域的时候，如果是的话则在选择城市的时候不刷新区域

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_location);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("服务位置");

		initViewNew();
		// getData();
		getLocation();
	}

	/**
	 * 初始化界面
	 */
	private void initViewNew() {
		areaListView = (ListView) findViewById(R.id.area_listview);
		loadLayout = (LinearLayout) findViewById(R.id.load_layout);
		createLayout = (LinearLayout) findViewById(R.id.create_layout);
		showListView = (ListView) findViewById(R.id.loc_show_area);
		loadAddress = (TextView) findViewById(R.id.load_address);
		pSpinner = (Spinner) findViewById(R.id.loc_province);
		cSpinner = (Spinner) findViewById(R.id.loc_city);
		aSpinner = (Spinner) findViewById(R.id.loc_area);
		pSpinner.setOnItemSelectedListener(this);
		cSpinner.setOnItemSelectedListener(this);
		aSpinner.setOnItemSelectedListener(this);
		addressTxt = (EditText) findViewById(R.id.loc_detail);
		addAreaBtn = (TextView) findViewById(R.id.btn_add);
		deleteAreaBtn = (TextView) findViewById(R.id.btn_del);
		aAdapter = new AreaAdapter(this);
		kmAdapter = new KMAdapter(this);
		areaLVAdapter = new AreaLVAdapter(this);
		areaList = new ArrayList<LocArea>();
		layout6 = (LinearLayout) findViewById(R.id.loc_layout6);
		addAreaBtn.setOnClickListener(this);
		deleteAreaBtn.setOnClickListener(this);
	}

	/**
	 * 保存服务位置方法
	 */
	private void saveLoc() {
		if (addressTxt.getText().toString().equals("")) {
			Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
			return;
		}
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp("artificer");
		requestWrapper.setProvince_id(province_id);
		requestWrapper.setProvince_name(province_name);
		requestWrapper.setCity_id(city_id);
		requestWrapper.setCity_name(city_name);
		requestWrapper.setArea_id(area_id);
		requestWrapper.setArea_name(area_name);
		requestWrapper.setAddress(addressTxt.getText().toString());
		requestWrapper.setPos_json(getJson());
		Log.i("test", "json-->" + getJson());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		sendData(requestWrapper, NetworkAction.pos_in);
	}

	/**
	 * 拼接字符串
	 */
	private String getJson() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i = 0; i < areaList.size(); i++) {
			LocArea area = areaList.get(i);
			builder.append("{");
			builder.append("\"area_id\":" + area.getArea_id() + ",");
			builder.append("\"distance\":" + area.getDistance());
			builder.append("}");
			if (i != areaList.size() - 1)
				builder.append(",");
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
	private void resetArea() {
		if (loadArea) {
			return;
		}

		areaList.clear();
		LocArea area = new LocArea();
		areaList.add(area);
		areaLVAdapter.notifyDataSetChanged();
	}

	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if (requestType == NetworkAction.province) {
			ArrayList<Province> pList = responseWrapper.getProvince();
			pAdapter = new ProvinceAdapter(this);
			pAdapter.setDataList(pList);
			pSpinner.setAdapter(pAdapter);
			if (loadArea)
				for (int i = 0; i < pList.size(); i++) {
					if (info.getProvince_id().equals(
							pList.get(i).getProvince_id())) {
						pSpinner.setSelection(i);
					}
				}
			pAdapter.notifyDataSetChanged();
			resetArea();
		} else if (requestType == NetworkAction.city) {
			ArrayList<City> cList = responseWrapper.getCity();
			cAdapter = new CityAdapter(this);
			cAdapter.setDataList(cList);
			cSpinner.setAdapter(cAdapter);
			if (loadArea)
				for (int i = 0; i < cList.size(); i++) {
					if (info.getCity_id().equals(cList.get(i).getCity_id())) {
						cSpinner.setSelection(i);
					}
				}
			cAdapter.notifyDataSetChanged();
			resetArea();
		} else if (requestType == NetworkAction.area) {

			areaData = responseWrapper.getArea();
			aAdapter.setDataList(areaData);
			aSpinner.setAdapter(aAdapter);
			if (loadArea)
				for (int i = 0; i < areaData.size(); i++) {
					if (info.getArea_id().equals(areaData.get(i).getArea_id())) {
						aSpinner.setSelection(i);
					}
				}

			aAdapter.notifyDataSetChanged();
			areaLVAdapter.notifyDataSetChanged();
			loadArea = false;
		} else if (requestType == NetworkAction.distance) {
			// kmAdapter = new KMAdapter(this);
			disList = responseWrapper.getDistance();
			kmAdapter.setDataList(disList);
			kmAdapter.notifyDataSetChanged();
			areaLVAdapter.notifyDataSetChanged();
		}
		// 设置服务位置成功
		else if (requestType == NetworkAction.pos_in) {
			Toast.makeText(this, responseWrapper.getMsg(), Toast.LENGTH_SHORT)
					.show();
			getLocation();
			createLayout.setVisibility(View.GONE);
			topTitleView.setRightBtnText("修改", new OnClickListener() {

				@Override
				public void onClick(View v) {
					changeLoc();

				}
			});

		}
		// 获取服务位置并且有服务位置的时候
		else if (requestType == NetworkAction.pos_list) {
			createLayout.setVisibility(View.GONE);
			loadLayout.setVisibility(View.VISIBLE);
			// 显示定位信息
			info = responseWrapper.getApos_info();
			String address = info.getProvince_name() + info.getCity_name()
					+ info.getArea_name() + info.getAddress();
			loadAddress.setText(address);
			// 显示区域信息
			areaList = responseWrapper.getApos_list();
			showAreaAdapter = new ShowAreaAdapter(this);
			showAreaAdapter.setDataList(areaList);
			showAreaAdapter.notifyDataSetChanged();
			showListView.setAdapter(showAreaAdapter);
			topTitleView.setRightBtnText("修改", new OnClickListener() {

				@Override
				public void onClick(View v) {
					changeLoc();

				}

			});
		}
	}

	/**
	 * 修改服务位置
	 */
	private void changeLoc() {
		loadArea = true;
		createLayout.setVisibility(View.VISIBLE);
		loadLayout.setVisibility(View.GONE);
		topTitleView.setRightBtnText("保存", new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveLoc();

			}

		});
		areaLVAdapter.setDataList(areaList);
		areaListView.setAdapter(areaLVAdapter);
		areaLVAdapter.notifyDataSetChanged();
		if(info!=null)
			addressTxt.setText(info.getAddress());
		getData();
	}

	@Override
	public void getErrorMsg(NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.getErrorMsg(requestType);
		// 如果获取服务位置失败或者没有服务位置的时候
		if (requestType == NetworkAction.pos_list) {
			createLayout.setVisibility(View.VISIBLE);
			loadLayout.setVisibility(View.GONE);
			// resetLoc();

			topTitleView.setRightBtnText("保存", new OnClickListener() {

				@Override
				public void onClick(View v) {
					saveLoc();

				}

			});
			LocArea area = new LocArea();
			areaList.add(area);
			areaLVAdapter.setDataList(areaList);
			areaListView.setAdapter(areaLVAdapter);
			getData();

		}
	}

	/**
	 * 获取服务地址信息
	 */
	private void getLocation() {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp("artificer");
		requestWrapper.setPage("1");
		requestWrapper.setPer("100");
		requestWrapper.setPos("1");
		requestWrapper.setShowDialog(true);
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

		private String area_id = null;// 县级ID
		private Spinner spinner;

		public Spinner getSpinner() {
			return spinner;
		}

		public void setSpinner(Spinner spinner) {
			this.spinner = spinner;
		}

		public String getArea_id() {
			return area_id;
		}

		public void setArea_id(String area_id) {
			this.area_id = area_id;
		}

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
			if (area.getArea_id().equals(area_id) && spinner != null) {
				spinner.setSelection(position);
			}
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
	class AreaLVAdapter extends WashoesBaseAdapter<LocArea> {

		public AreaLVAdapter(Activity activity) {
			super(activity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.area_adapter_item, null);
				viewHolder = new ViewHolder();
				viewHolder.item_area = (Spinner) convertView
						.findViewById(R.id.item_area);
				viewHolder.item_area_delete = (TextView) convertView
						.findViewById(R.id.item_area_delete);
				viewHolder.item_km = (Spinner) convertView
						.findViewById(R.id.item_km);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			final AreaAdapter aAdapter = new AreaAdapter(LocationActivity.this);
			aAdapter.setArea_id(areaList.get(position).getArea_id());
			aAdapter.setSpinner(viewHolder.item_area);
			aAdapter.setDataList(areaData);
			viewHolder.item_area.setAdapter(aAdapter);

			final KMAdapter kmAdapter = new KMAdapter(LocationActivity.this);
			kmAdapter.setDistance(areaList.get(position).getDistance());
			kmAdapter.setSpinner(viewHolder.item_km);
			kmAdapter.setDataList(disList);
			viewHolder.item_km.setAdapter(kmAdapter);

			if (areaList.get(position).isDelete())
				viewHolder.item_area_delete.setVisibility(View.VISIBLE);
			else
				viewHolder.item_area_delete.setVisibility(View.GONE);

			viewHolder.item_area_delete
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (areaList.size() == 1) {
								Toast.makeText(LocationActivity.this,
										"至少需要一个服务区域，无法删除", Toast.LENGTH_SHORT)
										.show();
								return;
							}
							areaList.remove(position);

							notifyDataSetChanged();
						}
					});

			viewHolder.item_km
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							areaList.get(position).setDistance(
									disList.get(arg2).getDistance());
							kmAdapter.setDistance(null);
							kmAdapter.setSpinner(null);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});

			viewHolder.item_area
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							areaList.get(position).setArea_id(
									areaData.get(arg2).getArea_id());
							areaList.get(position).setArea_name(
									areaData.get(arg2).getArea_name());
							aAdapter.setArea_id(null);
							aAdapter.setSpinner(null);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});
			return convertView;
		}

		class ViewHolder {
			private Spinner item_area;
			private TextView item_area_delete;
			private Spinner item_km;
		}

	}

	/**
	 * 公里数适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class KMAdapter extends WashoesBaseAdapter<Distance> {

		private String distance;
		private Spinner spinner;

		public String getDistance() {
			return distance;
		}

		public void setDistance(String distance) {
			this.distance = distance;
		}

		public Spinner getSpinner() {
			return spinner;
		}

		public void setSpinner(Spinner spinner) {
			this.spinner = spinner;
		}

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

			if (distance.getDistance().equals(this.distance) && spinner != null) {
				spinner.setSelection(position);
			}
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
	class ShowAreaAdapter extends WashoesBaseAdapter<LocArea> {

		public ShowAreaAdapter(Activity activity) {
			super(activity);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.show_area_item, null);
				viewHolder = new ViewHolder();
				viewHolder.areaTxt = (TextView) convertView
						.findViewById(R.id.area_text);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			LocArea areaItem = areaList.get(position);
			String context = areaItem.getArea_name() + "  "
					+ areaItem.getDistance() + "公里以内";
			viewHolder.areaTxt.setText(context);
			return convertView;
		}

		class ViewHolder {
			private TextView areaTxt;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 添加区域按钮
		case R.id.btn_add:
			deleteAreaBtn.setText("删除");
			for (int i = 0; i < areaList.size(); i++) {
				LocArea areaItem = areaList.get(i);
				areaItem.setDelete(false);
			}
			LocArea area = new LocArea();
			areaList.add(area);
			Log.i("test", "areaList.size()-->" + areaList.size());
			areaLVAdapter.notifyDataSetChanged();
			break;
		// 删除区域按钮
		case R.id.btn_del:
			if (deleteAreaBtn.getText().toString().equals("删除")) {
				deleteAreaBtn.setText("取消");
				for (int i = 0; i < areaList.size(); i++) {
					LocArea areaItem = areaList.get(i);
					areaItem.setDelete(true);
				}
			}

			else {
				deleteAreaBtn.setText("删除");
				for (int i = 0; i < areaList.size(); i++) {
					LocArea areaItem = areaList.get(i);
					areaItem.setDelete(false);
				}
			}

			areaLVAdapter.notifyDataSetChanged();
			break;

		}

	}

}
