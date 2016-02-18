package com.cn.washoes.person;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.washoes.R;
import com.cn.washoes.model.Info;

public class ShopActivity extends BaseActivity {

	private WebView myWebView;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop);
		Info info = MyApplication.getInfo();
		if (info != null) {
			url="http://shop.xidoudou.com.cn/mobile/?app=1&username="+info.getUsername()+"&password="+info.getPwd();
			myWebView= (WebView) findViewById(R.id.webview);
			myWebView.setClickable(true);
			myWebView.getSettings().setJavaScriptEnabled(true);
			myWebView.setWebViewClient(new myWebViewClient());
//			Toast.makeText(this,url, 2000).show();
			myWebView.loadUrl(url);
		}
		
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
	
	private class myWebViewClient extends WebViewClient {
		 
	    @Override
	 
	public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
	 
	myWebView.loadUrl(url);
	 
	return true;
	 
	}
	 
	 
	}
}
