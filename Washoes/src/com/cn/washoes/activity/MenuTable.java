package com.cn.washoes.activity;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.cn.washoes.util.Cst;

public class MenuTable extends TabActivity {
	/** Called when the activity is first created. */
	public TabHost tabHost; // 底部菜单栏
	public RadioGroup radioGroup;
	private Resources resources; // 获取资源文件
	private RadioButton orderBtn;
	private RadioButton personBtn;
	private View orderView;
	private View msgView;

	// public int isReceive=-1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu);
		resources = getResources();
		initData();
		registerReceiver(receiver, new IntentFilter(Cst.OPEN_MSG));
		registerReceiver(receiver, new IntentFilter(Cst.OPEN_ORDER));
		registerReceiver(receiver, new IntentFilter(Cst.CLOSE_MSG));
		registerReceiver(receiver, new IntentFilter(Cst.CLOSE_ORDER));
		registerReceiver(receiver, new IntentFilter(Cst.SET_ORDER));
		registerReceiver(receiver, new IntentFilter(Cst.SET_PERSON));
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		MyApplication.width = dm.widthPixels;// 宽度
		MyApplication.height = dm.heightPixels ;//高度
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
		// if(isReceive>0){
		// MenuTable.tabHost.setCurrentTab(isReceive);
		// MenuTable.setOrderChecked();
		// isReceive=-1;
		// }

		if (MyApplication.exit) {
			finish();
			System.exit(0);
		} else {
			getInfo();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
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

	public void openOrderView() {
		if (orderView != null)
			orderView.setVisibility(View.VISIBLE);
	}

	public void closeOrderView() {
		if (orderView != null)
			orderView.setVisibility(View.GONE);
	}

	public void openMsgView() {
		if (msgView != null)
			msgView.setVisibility(View.VISIBLE);
	}

	public void closeMsgView() {
		if (msgView != null)
			msgView.setVisibility(View.GONE);
	}

	public void setOrderChecked() {
		orderBtn.setChecked(true);
	}

	public void setPersonChecked() {
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

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Cst.OPEN_MSG.equals(intent.getAction())) {
				openMsgView();
			} else if (Cst.OPEN_ORDER.equals(intent.getAction())) {
				openOrderView();
			} else if (Cst.CLOSE_MSG.equals(intent.getAction())) {
				Log.i("test", "onReceive-->Cst.CLOSE_MSG");
				closeMsgView();
			} else if (Cst.CLOSE_ORDER.equals(intent.getAction())) {
				closeOrderView();
			}
			else if (Cst.SET_ORDER.equals(intent.getAction())) {
				tabHost.setCurrentTab(1);
				orderBtn.setChecked(true);
			}
			else if (Cst.SET_PERSON.equals(intent.getAction())) {
				tabHost.setCurrentTab(2);
				personBtn.setChecked(true);
			}
		}

	};

	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();

	};
}