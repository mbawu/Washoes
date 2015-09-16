package com.cn.washoes.activity;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.hongwei.MyGridView;
import com.cn.hongwei.PhotoActivity;
import com.cn.hongwei.TopTitleView;
import com.cn.washoes.R;
import com.cn.washoes.util.Cst;

public class OrderCamareActivity extends PhotoActivity implements
		View.OnClickListener {

	public static final String KEY_CAMARE_TYPE = "camare_type";
	public static final int CAMARE_TYPE_BEFORE = 0;
	public static final int CAMARE_TYPE_AFTER = 1;
	private TopTitleView topTitleView;
	private final String SELECTE = "select";
	private final int photoCount = 5;
	private LinearLayout rootLayout;

	private CamareView camareViewTemp;

	private List<String> imgPaths = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_camare_layout);
		topTitleView = new TopTitleView(this);
		topTitleView.setTitle("照片");
		topTitleView.setRightBtnText("上传", this);
		rootLayout = (LinearLayout) findViewById(R.id.order_img_root);
		addCamaraViews();
	}

	@Override
	public void onClick(View v) {
		imgPaths.clear();
		int childCount = rootLayout.getChildCount();
		if (childCount > 3) {
			for (int i = 3; i < childCount; i++) {
				CamareView cv = (CamareView) rootLayout.getChildAt(i).getTag();
				List<PhotoPath> imgList = cv.getImgList();
				for (PhotoPath pp : imgList) {
					if (pp.localPath != null && !"".equals(pp.localPath)
							&& !SELECTE.equals(pp.localPath)) {
						imgPaths.add(pp.localPath);
					}
				}
			}
		}
		
		if (imgPaths.size() > 0) {

		} else {

		}

	}

	private void addCamaraViews() {
		String[] type = getResources()
				.getStringArray(R.array.order_camare_type);
		for (String t : type) {
			CamareView img = new CamareView(this, t);

			rootLayout.addView(img.getLayout());
		}
	}

	@Override
	public void uploadImg(String imagePath) {

		if (camareViewTemp != null) {
			PhotoPath temp = new PhotoPath();
			temp.localPath = imagePath;
			camareViewTemp.addImg(temp);
		}
	}

	public void loadImg(ImageView img, String imagePath) {
		try {

			FileInputStream fis = new FileInputStream(new File(imagePath));
			img.setImageBitmap(BitmapFactory.decodeStream(fis));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class PhotoPath {
		String localPath = SELECTE;
		String remoteName;
		String remotePath;
	}

	class CamareView {

		private LinearLayout layout;

		private TextView textType;

		private MyGridView imgGridView;

		private List<PhotoPath> imgList = new ArrayList<OrderCamareActivity.PhotoPath>();

		private ImgAdapter adapter;

		public CamareView(Activity act, String typeName) {
			layout = (LinearLayout) act.getLayoutInflater().inflate(
					R.layout.order_camare_item_layout, rootLayout, false);
			textType = (TextView) layout
					.findViewById(R.id.order_camare_text_name_item);
			imgGridView = (MyGridView) layout
					.findViewById(R.id.order_camare_gridview_img);
			textType.setText(typeName);
			imgList.add(new PhotoPath());
			adapter = new ImgAdapter();
			adapter.setUrlList(imgList);
			adapter.setCamareView(this);
			imgGridView.setAdapter(adapter);
			layout.setTag(this);
		}

		public LinearLayout getLayout() {
			return layout;
		}

		public void addImg(PhotoPath path) {

			imgList.add(imgList.size() - 1, path);
			if (imgList.size() == (photoCount + 1)) {
				imgList.remove(photoCount);
			}
			adapter.notifyDataSetChanged();

		}

		public List<PhotoPath> getImgList() {
			return imgList;
		}

	}

	class ImgAdapter extends BaseAdapter {

		private CamareView camareView;

		private List<PhotoPath> pathList;

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

			final PhotoPath photoPath = pathList.get(position);
			convertView = getLayoutInflater().inflate(
					R.layout.camare_photo_img, null);

			if (!SELECTE.equals(photoPath.localPath)) {
				loadImg((ImageView) convertView, photoPath.localPath);
				convertView.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						pathList.remove(position);

						if (pathList.size() == (photoCount - 1)
								&& !SELECTE.equals(pathList.get(pathList.size() - 1).localPath)) {
							PhotoPath pt = new PhotoPath();
							pathList.add(pt);
						}
						notifyDataSetChanged();
						return true;
					}
				});
			} else {
				((ImageView) convertView)
						.setImageResource(R.drawable.select_photo);
				convertView.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						imgPath = Cst.UPLOAD_TEMP + "/"
								+ System.currentTimeMillis() + ".jpg";

						File vFile = new File(imgPath);
						if (v.getId() != R.id.btn_person_cancel
								&& !vFile.exists()) {
							camareViewTemp = getCamareView();
							imageUri = Uri.parse("file://" + imgPath);
							File vDirPath = vFile.getParentFile();
							vDirPath.mkdirs();
							Uri uri = Uri.fromFile(vFile);
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//
							OrderCamareActivity.this.startActivityForResult(
									intent, PhotoActivity.REQUEST_CODE_CAMERA);
						}

					}
				});

			}

			return convertView;
		}

		public List<PhotoPath> getUrlList() {
			return pathList;
		}

		public void setUrlList(List<PhotoPath> pathList) {
			this.pathList = pathList;
		}

	}

}
