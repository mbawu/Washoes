package com.cn.washoes.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import cn.jpush.android.api.JPushInterface;

import com.cn.hongwei.MyApplication;
import com.cn.washoes.R;
import com.cn.washoes.model.Info;
import com.cn.washoes.person.PersonActivity;

public class MenuTable extends TabActivity {
	/** Called when the activity is first created. */
	public static TabHost tabHost; // 底部菜单栏
	public static RadioGroup radioGroup;
	private Resources resources; // 获取资源文件
	private static RadioButton orderBtn;
	private static RadioButton personBtn;
	private static View orderView;
	private static View msgView;

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
		orderView = (View) findViewById(R.id.order_item_view_no_read);
		msgView = (View) findViewById(R.id.msg_item_view_no_read);
		orderBtn = (RadioButton) findViewById(R.id.main_tab_order);
		personBtn = (RadioButton) findViewById(R.id.main_tab_personcenter);
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
				.newTabSpec(resources.getString(R.string.main_menu_person))
				.setIndicator(resources.getString(R.string.main_menu_person))
				.setContent(intent);
		tabHost.addTab(spec);

		// // 登录界面
		// intent = new Intent().setClass(this, PersonLogin.class);
		// spec = tabHost
		// .newTabSpec(resources.getString(R.string.person_login))
		// .setIndicator(resources.getString(R.string.person_login))
		// .setContent(intent);
		// tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
		radioGroup = (RadioGroup) this.findViewById(R.id.main_tab_group);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.main_tab_home:
					tabHost.setCurrentTabByTag(resources
							.getString(R.string.main_menu_home));
					break;
				case R.id.main_tab_order:
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

		if (MyApplication.exit) {
			finish();
			System.exit(0);
		} else {
			orderView = (View) findViewById(R.id.order_item_view_no_read);
			msgView = (View) findViewById(R.id.msg_item_view_no_read);
			getInfo();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	/**
	 * 获取用户信息，判断是否登录过
	 */
	private void getInfo() {
		Info info = MyApplication.getInfo();
		if (info == null) {
			Intent intent = new Intent().setClass(MenuTable.this,
					LoadActivity.class);
			startActivity(intent);
		}
	}

	public static void openOrderView() {
		if (orderView != null)
			orderView.setVisibility(View.VISIBLE);
	}

	public static void closeOrderView() {
		if (orderView != null)
			orderView.setVisibility(View.GONE);
	}

	public static void openMsgView() {
		if (msgView != null)
			msgView.setVisibility(View.VISIBLE);
	}

	public static void closeMsgView() {
		if (msgView != null)
			msgView.setVisibility(View.GONE);
	}
	
	public static void setOrderChecked() {
		orderBtn.setChecked(true);
	}
	public static void setPersonChecked() {
		personBtn.setChecked(true);
	}
	
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}