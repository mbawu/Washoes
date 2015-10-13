package com.cn.hongwei;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.cn.washoes.R;
import com.cn.washoes.activity.MenuTable;
import com.cn.washoes.model.Info;
import com.cn.washoes.person.PersonActivity;
import com.cn.washoes.util.Cst;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	// public void notifyMsg(Context context, String msg) {
	// NotificationManager mNotificationManager =
	// MyApplication.mNotificationManager;
	// // 定义通知栏展现的内容信息
	// int icon = R.drawable.ic_launcher;
	// CharSequence tickerText = msg;
	// long when = System.currentTimeMillis();
	// Notification notification = new Notification(icon, tickerText, when);
	// notification.flags = Notification.FLAG_AUTO_CANCEL;
	// notification.defaults |= Notification.DEFAULT_ALL;
	// CharSequence contentTitle = context.getResources().getString(
	// R.string.app_name);
	// CharSequence contentText = msg;
	// Intent notificationIntent = new Intent(context, MenuTable.class);
	// PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
	// notificationIntent, 0);
	// notification.setLatestEventInfo(context, contentTitle, contentText,
	// contentIntent);
	//
	// // 用mNotificationManager的notify方法通知用户生成标题栏消息通知
	// mNotificationManager.notify(1, notification);
	// // mNotificationManager.cancel(-5);
	// }

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction()
				+ ", extras: " + printBundle(bundle));
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			// send the Registration Id to your server...

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			String message = bundle.getString(JPushInterface.EXTRA_EXTRA);
			String content = null;// 消息内容
			String title = null;// 消息标题
			Log.i("test", "message-->" + message);
			MyApplication.msgId++;
			JSONObject ob;
			try {
				ob = new JSONObject(message);
				content = ob.getString("content");
				title = ob.getString("title");
				JSONObject filter = ob.getJSONObject("jwhere");
				String isAll = filter.getString("is_all");
				// 发给部分用户
				if (isAll.equals("0")) {
					Info info = MyApplication.getInfo();
					// 如果登录过的话才需要显示消息，否则收不到任何消息，前提是后台发送的不是针对所有用户的消息
					if (info != null) {
						JSONArray userArray = filter.getJSONArray("aid");
						for (int i = 0; i < userArray.length(); i++) {
							// 如果在选择的用户列表里有本地所保存的用户信息则进行推送
							if (info.getAid().equals(userArray.getString(i))) {
								String isOrder = filter.getString("is_order");
								
								// 订单相关推送
								if (isOrder.equals("1")) {
									MyApplication.msgType=0;
									String isNew = filter.getString("is_new");
									// 如果是新订单的话显示未读图标
									if (isNew.equals("1")) {
										Intent intent1 = new Intent(
												Cst.OPEN_ORDER);
										// 发送广播
										context.sendBroadcast(intent1);
									}
									// 接收到消息推送以后通知改变消息数量
									Intent mIntent = new Intent(
											Cst.GET_ORDER);
									// 发送广播
									context.sendBroadcast(mIntent);

								}
								// 消息推送
								else if (isOrder.equals("0")) {
									MyApplication.msgType=1;
									Intent intent1 = new Intent(
											Cst.OPEN_MSG);
									// 发送广播
									context.sendBroadcast(intent1);
									// 接收到消息推送以后通知改变消息数量
									Intent mIntent = new Intent(Cst.GET_MSG);
									// 发送广播
									context.sendBroadcast(mIntent);

								}
								MyApplication
										.notifyMsg(context, content, title);

							}
						}

					}

				}
				// 发给所有用户
				else {
					MyApplication.notifyMsg(context, content, title);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(context, "推送消息格式出错，请后台人员修正", Toast.LENGTH_SHORT)
						.show();
			}

			// MyApplication.notifyMsg(context, content,title);
			// // 接收到消息推送以后通知改变消息数量
			// Intent mIntent = new Intent(Cst.GET_RECEIVE);
			// // 发送广播
			// context.sendBroadcast(mIntent);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			String message = bundle.getString(JPushInterface.EXTRA_ALERT);
			// notifyMsg(context,message);
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			// String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			// notifyMsg(context,message);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			// Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			// //打开自定义的Activity
			// Intent i = new Intent(context, MessageActivity.class);
			// MyApplication.list.get(0).startActivity(i);
		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
				.getAction())) {
			Log.d(TAG,
					"[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
							+ bundle.getString(JPushInterface.EXTRA_EXTRA));
			// 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
			// 打开一个网页等..

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
				.getAction())) {
			boolean connected = intent.getBooleanExtra(
					JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[MyReceiver]" + intent.getAction()
					+ " connected state change to " + connected);
		} else {
			Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

}
