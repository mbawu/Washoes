package com.cn.washoes.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.CarImageView;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.model.Info;
import com.cn.washoes.util.Cst;
import com.cn.washoes.util.NetworkAction;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * 首页
 * 
 * @author Administrator
 * 
 */
public class HomeActivity extends BaseActivity {

	private TopTitleView topTitleView;
	private CarImageView imageView;// 头像
	private ImageView twoCodeImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("首页");
		topTitleView.setBackImageViewVisable(View.GONE);
		imageView = (CarImageView) findViewById(R.id.home_head_img);
		twoCodeImg = (ImageView) findViewById(R.id.home_twocode_img);
		try {
			getOrder();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Info info = MyApplication.getInfo();
		if (info != null) {
			imageView.loadImage(info.getPhoto());
			twoCodeImg.setImageBitmap(generateQRCode(info.getQrcode_url()));
		}
	}

	private Bitmap bitMatrix2Bitmap(BitMatrix matrix) {
		int w = matrix.getWidth();
		int h = matrix.getHeight();
		int[] rawData = new int[w * h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int color = Color.WHITE;
				if (matrix.get(i, j)) {
					color = Color.BLACK;
				}
				rawData[i + (j * w)] = color;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
		bitmap.setPixels(rawData, 0, w, 0, 0, w, h);
		return bitmap;
	}

	private Bitmap generateQRCode(String content) {
		try {
			QRCodeWriter writer = new QRCodeWriter();
			// MultiFormatWriter writer = new MultiFormatWriter();
			BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE,
					500, 500);
			return bitMatrix2Bitmap(matrix);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 向后台发送订单列表请求
	 */
	private void getOrder() {
		RequestWrapper requestWrapper = new RequestWrapper();
		requestWrapper.setAid(MyApplication.getInfo().getAid());
		requestWrapper.setSeskey(MyApplication.getInfo().getSeskey());
		requestWrapper.setOp("order");
		requestWrapper.setPer("1");
		requestWrapper.setPage("1");
		requestWrapper.setFlag("1");
//		requestWrapper.setIs_onum("0");
		sendData(requestWrapper, NetworkAction.list);
	}
	
	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		// TODO Auto-generated method stub
		super.showResualt(responseWrapper, requestType);
		if(responseWrapper!=null)
		{
			Intent intent=new Intent();
			//检查订单菜单提示状态
			if(responseWrapper.getUnread_num().equals("0"))
			{
				intent.setAction(Cst.CLOSE_ORDER);
			}
			else
			{
				intent.setAction(Cst.OPEN_ORDER);
			}
			sendBroadcast(intent);
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
}
