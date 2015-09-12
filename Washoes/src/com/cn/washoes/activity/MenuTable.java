package com.cn.washoes.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import cn.jpush.android.api.JPushInterface;

import com.cn.washoes.R;
import com.cn.washoes.person.PersonActivity;

public class MenuTable extends TabActivity {
	/** Called when the activity is first created. */
	public static TabHost tabHost; // 底部菜单栏
	public static RadioGroup radioGroup;
	private Resources resources; //获取资源文件

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu);
		resources = getResources();
		initData();
	}

	
	private void initData() {
		tabHost = this.getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		// 首页菜单
		intent = new Intent().setClass(this, HomeActivity.class);
		spec = tabHost.newTabSpec(resources.getString(R.string.main_menu_home))
				.setIndicator(resources.getString(R.string.main_menu_home))
				.setContent(intent);
		tabHost.addTab(spec);
		// 订单
		intent = new Intent().setClass(this, OrderListActivity.class);
		spec = tabHost
				.newTabSpec(resources.getString(R.string.main_menu_order))
				.setIndicator(resources.getString(R.string.main_menu_order))
				.setContent(intent);
		tabHost.addTab(spec);


		// 个人中心菜单
		intent = new Intent().setClass(this, PersonActivity.class);
		spec = tabHost
				.newTabSpec(
						resources.getString(R.string.main_menu_person))
				.setIndicator(
						resources.getString(R.string.main_menu_person))
				.setContent(intent);
		tabHost.addTab(spec);

//		// 登录界面
//					intent = new Intent().setClass(this, PersonLogin.class);
//					spec = tabHost
//							.newTabSpec(resources.getString(R.string.person_login))
//							.setIndicator(resources.getString(R.string.person_login))
//							.setContent(intent);
//					tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
		radioGroup = (RadioGroup) this
				.findViewById(R.id.main_tab_group);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.main_tab_home:
					tabHost.setCurrentTabByTag(resources
							.getString(R.string.main_menu_home));
					break;
				case R.id.main_tab_search:
					tabHost.setCurrentTabByTag(resources
							.getString(R.string.main_menu_order));
					break;
				case R.id.main_tab_personcenter:
					tabHost.setCurrentTabByTag(resources
							.getString(R.string.main_menu_person));
					break;
				default:
					break;
				}
			}
		});
	}
	@Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


}