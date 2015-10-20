package com.cn.hongwei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.cn.hongwei.BaiduLoction.LocationCallback;
import com.cn.washoes.R;
import com.cn.washoes.activity.MenuTable;
import com.cn.washoes.model.Info;
import com.cn.washoes.person.MessageActivity;
import com.cn.washoes.util.CrashHandler;
import com.cn.washoes.util.Cst;
import com.cn.washoes.util.NetworkAction;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {

	public static MyHttpClient client;// 网络请求的终端
	public static ArrayList<BaseActivity> list;// 记录所有存在的activity
	public static SharedPreferences sp; // 本地存储SharedPreferences
	public static Editor ed; // 本地存储编辑器Editor
	public static NotificationManager mNotificationManager;
	public static boolean loginStat = false;
	public static boolean exit = false;// 是否退出应用
	public static String lng = "0";
	public static String lat = "0";
	public static String address = "";
	public static String detail = "";
	public static int msgId = 0;
	public static int msgType = 0;// 0 打开我的订单列表页面 1打开我的消息页面
	public static int width = 1;
	public static int height = 1;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
		/*
		 * 初始化Volley框架的Http工具类
		 */
		ShareSDK.initSDK(this);
		client = MyHttpClient.getInstance(MyApplication.this
				.getApplicationContext());
		BaiduLoction.getInstance().init(this);
		// AppInfo.init(this);
		// RelayoutTool.initScreenScale(this);
		initImageLoader(this);
		// initTaeSDK();
		list = new ArrayList<BaseActivity>();
		// UpgradeManager.getInstence().init(this);
		// 初始化SharedPreferences
		sp = getSharedPreferences("Washoes", MODE_PRIVATE);
		ed = sp.edit();
		initSharePreferenceData();
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 初始化JPUSH
		JPushInterface.init(getApplicationContext());
		getLocation();

	}

	public static void getKey(BaseActivity activity) {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp(NetworkAction.login.toString());
		requestWrapper.setMobile("18210945364");
		requestWrapper.setPassword("123456");
		activity.sendData(requestWrapper, NetworkAction.login);
	}

	public static void getKey(BaseActivity activity, String mobile, String pwd) {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setOp(NetworkAction.login.toString());
		requestWrapper.setMobile(mobile);
		requestWrapper.setPassword(pwd);
		activity.sendData(requestWrapper, NetworkAction.login);

	}

	private void getLocation() {
		BaiduLoction.getInstance().startLocation();

		BaiduLoction.getInstance().setLocationCallback(new LocationCallback() {

			@Override
			public void locationResult(BDLocation location) {
				address = location.getProvince() + location.getCity()
						+ location.getDistrict();
				detail = location.getStreet() + location.getStreetNumber();
				lng = String.valueOf(location.getLongitude());
				lat = String.valueOf(location.getLatitude());
				HashMap<String, String> pramer = new HashMap<String, String>();
				pramer.put("aid", getInfo().getAid());
				pramer.put("seskey", getInfo().getSeskey());
				pramer.put("op", "artifier");
				pramer.put("act", "jit_gps");
				pramer.put("gps", lng+","+lat);
				Log.i("test", lng+","+lat);
				client.postWithURL(Cst.HOST, pramer,
						NetworkAction.jit_gps,
						new Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject arg0) {
								// TODO Auto-generated method stub

							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								Log.i("test", "GPS出错");

							}
						});
			
			}

		});

	}

	// 获取拼接出来的请求字符串
	public static String getUrl(HashMap<String, String> paramter, String url) {
		Iterator iter = paramter.entrySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			HashMap.Entry entry = (HashMap.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (count == 0)
				url = url + "?" + key + "=" + val;
			else
				url = url + "&" + key + "=" + val;
			count++;
		}
		return url;
	}

	/**
	 * 初始化图片缓存模块，根据实际需要设置必要的选项。
	 * 
	 * @param ctx
	 */
	private void initImageLoader(Context ctx) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				ctx).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheSize(32 * 1024 * 1024)
				.memoryCacheSize(4 * 1024 * 1024).enableLogging().build();
		ImageLoader.getInstance().init(config);
	}

	private void initSharePreferenceData() {
		String infoJson = sp.getString("info", null);
		if (infoJson != null && !"".equals(infoJson)
				&& !"null".equals(infoJson)) {
			info = JsonUtil.fromJson(infoJson, Info.class);

			MyApplication.loginStat = info.isLoginState();
		} else
			info = null;

	};

	// private String guide;
	//
	// public String getGuide() {
	// return guide;
	// }
	//
	// public void setGuide(String guide) {
	// this.guide = guide;
	// ed.putString("guide", guide);
	// ed.commit();
	// }

	private static Info info;// 用户信息保存类

	/**
	 * 设置用户信息
	 * 
	 * @param infoin
	 */
	public static void setInfo(Info infoin) {
		info = infoin;
		ed.putString("info", JsonUtil.toJson(info));
		ed.commit();
	}

	/**
	 * 清除用户信息
	 */
	public static void clearInfo() {
		ed.remove("info");
		ed.commit();
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public static Info getInfo() {
		return info;
	}

	public NotificationManager getmNotificationManager() {
		return mNotificationManager;
	}

	public void setmNotificationManager(NotificationManager mNotificationManager) {
		this.mNotificationManager = mNotificationManager;
	}

	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		/*
		 * 可接受的电话格式有：
		 */
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
		/*
		 * 可接受的电话格式有：
		 */
		String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);

		Pattern pattern2 = Pattern.compile(expression2);
		Matcher matcher2 = pattern2.matcher(inputStr);
		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * 通知栏显示消息
	 * 
	 * @param context
	 *            Context
	 * @param msg
	 *            内容
	 * @param title
	 *            可为空，可为null
	 */
	public static void notifyMsg(Context context, String msg, String title) {
		// 定义通知栏展现的内容信息
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = msg;
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_ALL;
		CharSequence contentTitle = null;
		if (title == null || title.equals(""))
			contentTitle = context.getResources().getString(R.string.app_name);
		else
			contentTitle = title;
		CharSequence contentText = msg;
		Intent notificationIntent = null;
		// if(MenuTable.)
		if (msgType == 0) {
			Intent intent1 = new Intent(Cst.SET_ORDER);
			// 发送广播
			context.sendBroadcast(intent1);
			notificationIntent = new Intent(context, MenuTable.class);

		} else if (msgType == 1) {
			Intent intent1 = new Intent(Cst.SET_PERSON);
			// 发送广播
			context.sendBroadcast(intent1);
			notificationIntent = new Intent(context, MessageActivity.class);

		}
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		// 用mNotificationManager的notify方法通知用户生成标题栏消息通知
		mNotificationManager.notify(msgId, notification);
		// mNotificationManager.cancel(-5);
	}

}
