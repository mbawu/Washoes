package com.cn.washoes.activity;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cn.hongwei.BaseActivity;
import com.cn.hongwei.MyApplication;
import com.cn.hongwei.MyGridView;
import com.cn.hongwei.MyImageView;
import com.cn.hongwei.RequestWrapper;
import com.cn.hongwei.ResponseWrapper;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.activity.OrderCamareActivity.CamareView;
import com.cn.washoes.model.ImgInfo;
import com.cn.washoes.util.NetworkAction;

public class ImgDetailActivity extends BaseActivity {
	private TopTitleView topTitleView;

	private TextView textDataAfter;
	private TextView textDataBefore;
	private MyGridView beGridView;
	private MyGridView afGridView;
	private String order_id;
	private List<ImgInfo> be_images;// 服务前照片列表
	private List<ImgInfo> af_images;// 服务后照片列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.img_detail_layout);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("照片");
		beGridView = (MyGridView) findViewById(R.id.order_camare_gridview_before);
		afGridView = (MyGridView) findViewById(R.id.order_camare_gridview_after);
		textDataAfter = (TextView) findViewById(R.id.order_camare_txt_after);
		textDataBefore = (TextView) findViewById(R.id.order_camare_txt_before);
		order_id = getIntent().getStringExtra("order_id");
		RequestWrapper request = new RequestWrapper();
		request.setAid(MyApplication.getInfo().getAid());
		request.setSeskey(MyApplication.getInfo().getSeskey());
		request.setOp("order");
		request.setOrder_id(order_id);
		sendData(request, NetworkAction.ultu_list);
	}

	@Override
	public void showResualt(ResponseWrapper responseWrapper,
			NetworkAction requestType) {
		super.showResualt(responseWrapper, requestType);
		if (requestType == NetworkAction.ultu_list) {
			af_images = responseWrapper.getAf_images();
			be_images = responseWrapper.getBe_images();
			textDataAfter.setText(responseWrapper.getAf_time());
			textDataBefore.setText(responseWrapper.getBe_time());
			if (af_images != null && af_images.size() > 0) {
				ImgAdapter adapter = new ImgAdapter();
				adapter.setUrlList(af_images);
				afGridView.setAdapter(adapter);
			}
			if (be_images != null && be_images.size() > 0) {
				ImgAdapter adapter = new ImgAdapter();
				adapter.setUrlList(be_images);
				beGridView.setAdapter(adapter);
			}
		}
	}

	class ImgAdapter extends BaseAdapter {

		private CamareView camareView;

		private List<ImgInfo> pathList;

		@Override
		public int getCount() {

			return (pathList == null) ? 0 : pathList.size();
		}

		@Override
		public Object getItem(int position) {

			return pathList.get(position);
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		public CamareView getCamareView() {
			return camareView;
		}

		public void setCamareView(CamareView camareView) {
			this.camareView = camareView;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {

			convertView = getLayoutInflater().inflate(
					R.layout.camare_photo_img, null);
			final ImgInfo imgInfo = pathList.get(position);
			final MyImageView imgView = (MyImageView) convertView;
			if (imgInfo.getFile_path() != null
					&& !"".equals(imgInfo.getFile_path())) {
				convertView.postDelayed(new Runnable() {

					@Override
					public void run() {
						imgView.loadImage(imgInfo.getFile_path());
					}
				}, 100);

			}

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra("images", (Serializable) pathList);
					intent.putExtra("index", position);
					intent.setClass(ImgDetailActivity.this,
							ImageShowActivity.class);
					startActivity(intent);
				}
			});

			return convertView;
		}

		public List<ImgInfo> getUrlList() {
			return pathList;
		}

		public void setUrlList(List<ImgInfo> pathList) {
			this.pathList = pathList;
		}

	}
}
