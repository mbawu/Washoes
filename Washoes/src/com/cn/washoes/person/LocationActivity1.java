package com.cn.washoes.person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
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
import com.cn.washoes.model.AreaSpinner;
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
public class LocationActivity1 extends BaseActivity implements
		OnItemSelectedListener, OnClickListener {
	// 加载服务地址
	private LinearLayout loadLayout;// 放置服务位置的layout
	private TextView loadAddress;// 位置地址
	private TextView loadArea1;// 区域1
	private TextView loadArea2;// 区域2
	private TextView loadArea3;// 区域3
	private TextView loadArea4;// 区域4
	private TextView loadArea5;// 区域5

	// 新建服务地址
	private LinearLayout createLayout;// 创建服务位置时候的整个layout
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

	private LinearLayout layout6;// 按钮容器
	private int areaNum = 1;
	private boolean deleteModule = false;

	private TopTitleView topTitleView;// 标题栏
	private LocInfo info;
	private ArrayList<LocArea> areas;
	private ListView listView;
	private ArrayList<Area> areaList;
	private ArrayList<Distance> disList;
	private ArrayList<LocArea> listviewData;
	private ListViewAdatper listviewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_location2);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("服务位置");
		createLayout = (LinearLayout) findViewById(R.id.create_layout);
		initViewNew();
		// getData();
		getLocation();
	}

	/**
	 * 初始化界面
	 */
	private void initViewNew() {
		// 加载服务位置
		loadLayout = (LinearLayout) findViewById(R.id.load_layout);
		loadAddress = (TextView) findViewById(R.id.load_address);
		loadArea1 = (TextView) findViewById(R.id.load_area1);
		loadArea2 = (TextView) findViewById(R.id.load_area2);
		loadArea3 = (TextView) findViewById(R.id.load_area3);
		loadArea4 = (TextView) findViewById(R.id.load_area4);
		loadArea5 = (TextView) findViewById(R.id.load_area5);
		// 新建服务位置
		// createLayout.setVisibility(View.VISIBLE);
		listView = (ListView) findViewById(R.id.area_listview);
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

		// 如果不是初次创建而是修改的情况下,初始化区域数据
		if (areas != null) {
			// areaNum=areas.size();
			// if(areaNum==2)
			// {
			// layout2.setVisibility(View.VISIBLE);
			// area2.setVisibility(View.VISIBLE);
			// km1.setVisibility(View.VISIBLE);
			// }
			// else if(areaNum==3)
			// {
			//
			// }
		} else {
			deleteAreaBtn.setText("删除");
			deleteModule = false;

			layout6.setVisibility(View.VISIBLE);
			addAreaBtn.setVisibility(View.VISIBLE);
			deleteAreaBtn.setVisibility(View.INVISIBLE);
			noneAdapter.notifyDataSetChanged();
			kmAdapter.notifyDataSetChanged();
		}
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
			if (info != null)
				for (int i = 0; i < pList.size(); i++) {
					if (info.getProvince_id() == pList.get(i).getProvince_id()) {
						pSpinner.setSelection(i);
					}
				}
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
			areaList = responseWrapper.getArea();
			aAdapter.setDataList(areaList);
			aSpinner.setAdapter(aAdapter);

			aAdapter.notifyDataSetChanged();
		} else if (requestType == NetworkAction.distance) {
			kmAdapter = new KMAdapter(this);
			disList = responseWrapper.getDistance();
			kmAdapter.setDataList(disList);
			kmAdapter.notifyDataSetChanged();
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
			info = responseWrapper.getApos_info();
			String address = info.getProvince_name() + info.getCity_name()
					+ info.getArea_name() + info.getAddress();
			loadAddress.setText(address);
			areas = responseWrapper.getApos_list();
			

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
		createLayout.setVisibility(View.VISIBLE);
		loadLayout.setVisibility(View.GONE);
		topTitleView.setRightBtnText("保存", new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveLoc();

			}

		});
		getData();
	}

	@Override
	public void getErrorMsg(NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.getErrorMsg(requestType);
		// 如果获取服务位置失败或者没有服务位置的时候
		if (requestType == NetworkAction.pos_list) {
			createLayout.setVisibility(View.VISIBLE);
//			resetLoc();
			loadLayout.setVisibility(View.GONE);
			getData();
			topTitleView.setRightBtnText("保存", new OnClickListener() {

				@Override
				public void onClick(View v) {
					saveLoc();

				}

			});
		}
	}

	/**
	 * 获取服务地址信息
	 */
	private void getLocation() {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp("artificer");
		requestWrapper.setPage("1");
		requestWrapper.setPer("1");
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 添加区域按钮
		case R.id.btn_add:
			LocArea areaNew=new LocArea();
			listviewData.add(areaNew);
			listviewAdapter.notifyDataSetChanged();

//			checkBtn();
			break;
		// 删除区域按钮
		case R.id.btn_del:
		

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

	/**
	 * 区域适配器
	 * 
	 * @author Wu Jiang
	 * 
	 */
	class ListViewAdatper extends WashoesBaseAdapter<AreaSpinner> {

		public ListViewAdatper(Activity activity) {
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

			AreaAdapter aAdapter = new AreaAdapter(LocationActivity1.this);
			aAdapter.setDataList(areaList);
			viewHolder.item_area.setAdapter(aAdapter);

			KMAdapter kmAdapter = new KMAdapter(LocationActivity1.this);
			kmAdapter.setDataList(disList);
			viewHolder.item_km.setAdapter(kmAdapter);

			if (listviewData.get(position).isDelete())
				viewHolder.item_area_delete.setVisibility(View.VISIBLE);
			else
				viewHolder.item_area_delete.setVisibility(View.GONE);
			
			viewHolder.item_area_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					listviewData.remove(position);
					
					notifyDataSetChanged();
				}
			});

			viewHolder.item_km
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							listviewData.get(position).setDistance(
									disList.get(arg2).getDistance());

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
							listviewData.get(position).setArea_id(
									areaList.get(arg2).getArea_id());
							listviewData.get(position).setArea_name(
									areaList.get(arg2).getArea_name());

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

}
