package com.cn.washoes.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Looper;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.cn.washoes.R;

public class CrashHandler implements UncaughtExceptionHandler {

	/** Debug Log tag*/ 
	public static final String TAG = "CrashHandler"; 
	/** 是否开启日志输出,在Debug状态下开启, 
	* 在Release状态下关闭以提示程序性能 
	* */ 
	public static final boolean DEBUG = false; 
	/** 系统默认的UncaughtException处理类 */ 
	private Thread.UncaughtExceptionHandler mDefaultHandler; 
	/** CrashHandler实例 */ 
	private static CrashHandler INSTANCE; 
	/** 程序的Context对象 */ 
	private Context mContext; 
	/** 保证只有一个CrashHandler实例 */ 
	private CrashHandler() {} 
	
	/** 获取CrashHandler实例 ,单例模式*/ 
	public static CrashHandler getInstance() { 
		if (INSTANCE == null) { 
			INSTANCE = new CrashHandler(); 
		} 
		return INSTANCE; 
	} 
	
	/** 
	* 初始化,注册Context对象, 
	* 获取系统默认的UncaughtException处理器, 
	* 设置该CrashHandler为程序的默认处理器 
	* @param ctx 
	*/ 
	public void init(Context ctx) { 
		mContext = ctx; 
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler(); 
		Thread.setDefaultUncaughtExceptionHandler(this); 
	} 
	
	/** 
	* 当UncaughtException发生时会转入该函数来处理 
	* 自定义错误处理,收集错误信息 
	* 发送错误报告等操作均在此完成. 
	* 开发者可以根据自己的情况来自定义异常处理逻辑 
	*/ 
	@Override 
	public void uncaughtException(Thread thread, final Throwable ex) { 
//		//使用Toast来显示异常信息 
		new Thread() { 
			@Override 
			public void run() { 
				Looper.prepare(); 
				Toast toast = Toast.makeText(mContext, "程序出错，即将退出，请联系管理员或者稍后重试",Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
//				MsgPrompt.showMsg(mContext, "程序出错啦", msg+"\n点确认退出");
				Log.e("[CrashHandler]", "全局异常发生");
				SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日  HH:mm:ss");   
				Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间   
				String   scanningTime   =   formatter.format(curDate);   
				String errorMsg = CrashHandler.getStackTrace(ex);
				DirectEmailComponent emailComponent = new DirectEmailComponent(mContext);
				String sendMsg = String.format("Android客户端: %s 于  %s 系统崩溃退出,手机型号: %s,系统版本: %s, 错误日志详细信息如下 : %s", 
						mContext.getString(R.string.app_name)+"( 域名:洗豆豆)", scanningTime,android.os.Build.MODEL,android.os.Build.VERSION.RELEASE, errorMsg);
				emailComponent.sendMail("Android客户端异常", sendMsg, "1054774552@qq.com",mContext.getString(R.string.app_name));
				Looper.loop();
			} 
		}.start(); 
		try {
			thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	} 
	
	/**
	 * 获取所有详细的信息
	 * 
	 * @param throwable
	 * @return
	 */
	public static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
}

