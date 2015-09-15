package com.cn.washoes.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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
 * @author Wu Jiang
 *
 */
public class LocationActivity extends BaseActivity implements OnItemSelectedListener{

	
	private Spinner pSpinner;//显示省份控件
	private ProvinceAdapter pAdapter;//省份适配器
	private Spinner cSpinner; //显示城市控件
	private CityAdapter cAdapter;//城市适配器
	private Spinner aSpinner;//显示区域控件
	private AreaAdapter aAdapter;//地区适配器
	private String province_id;
	private String city_id;
	private String area_id;
	
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
		pSpinner=(Spinner) findViewById(R.id.loc_province);
		cSpinner=(Spinner) findViewById(R.id.loc_city);
		aSpinner=(Spinner) findViewById(R.id.loc_area);
		pSpinner.setOnItemSelectedListener(this);
		cSpinner.setOnItemSelectedListener(this);
		aSpinner.setOnItemSelectedListener(this);
		
	}


	/**
	 * 获取数据
	 */
	private void getData()
	{
		RequestWrapper requestWrapper=new RequestWrapper();
		requestWrapper.setOp("pos");
		sendData(requestWrapper, NetworkAction.province);
	}
	
	
	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if(requestType==NetworkAction.province)
		{
			pAdapter=new ProvinceAdapter(this);
			pAdapter.setDataList(responseWrapper.getProvince());
			pSpinner.setAdapter(pAdapter);
			pAdapter.notifyDataSetChanged();
		}
	}
	
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Object object = parent.getItemAtPosition(position);
		RequestWrapper request=new RequestWrapper();
		request.setOp("pos");
		if (object instanceof Province)
		{
			province_id = ((Province) object).getProvince_id();
			request.setProvince_id(province_id);
			sendData(request, NetworkAction.city);
		}
		
		else if (object instanceof City)
		{
			city_id = ((City) object).getCity_id();
			request.setCity_id(city_id);
			sendData(request, NetworkAction.area);
		}
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 省份适配器
	 * @author Wu Jiang
	 *
	 */
	class ProvinceAdapter extends WashoesBaseAdapter<Province>
	{

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
			Province province=getDataList().get(position);
			viewHolder.name.setText(province.getProvince_name());
			return convertView;
		}
		
		class ViewHolder
		{
			private TextView name;
		}
		
	}
	
	/**
	 * 城市适配器
	 * @author Wu Jiang
	 *
	 */
	class CityAdapter extends WashoesBaseAdapter<City>
	{

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
			City city=getDataList().get(position);
			viewHolder.name.setText(city.getCity_name());
			return convertView;
		}
		
		class ViewHolder
		{
			private TextView name;
		}
		
	}
	
	
	/**
	 * 区域适配器
	 * @author Wu Jiang
	 *
	 */
	class AreaAdapter extends WashoesBaseAdapter<Area>
	{

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
			Area area=getDataList().get(position);
			viewHolder.name.setText(area.getArea_name());
			return convertView;
		}
		
		class ViewHolder
		{
			private TextView name;
		}
		
	}


}
