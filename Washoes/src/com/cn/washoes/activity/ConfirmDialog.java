package com.cn.washoes.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.washoes.R;
/**
 * 操作确认对话框
 * @author Administrator
 *
 */
public class ConfirmDialog extends Dialog {
	public Button btu_ok;
	public Button btu_cancel;
	public TextView msgTextView;
	public TextView titleTextView;


	/**
	 * 界面初始化
	 * @param context 上下文
	 */
	public ConfirmDialog(Context context) {
		super(context, R.style.confirmDialog);
		this.setContentView(R.layout.confirm_dialog);

		btu_ok = (Button) findViewById(R.id.btu_on);
		btu_cancel = (Button) findViewById(R.id.btu_off);
		msgTextView = (TextView) findViewById(R.id.confirm_dialog_msg);
		titleTextView = (TextView) findViewById(R.id.confirm_dialog_title);
	}

	/**
	 * 提示用的dialog
	 * @param context 上下文
	 */
	public ConfirmDialog(Context context,int layoutResID) {
		super(context, R.style.confirmDialog);
		this.setContentView(layoutResID);

		btu_ok = (Button) findViewById(R.id.btu_on);
//		btu_cancel = (Button) findViewById(R.id.btu_off);
		msgTextView = (TextView) findViewById(R.id.confirm_dialog_msg);
		titleTextView = (TextView) findViewById(R.id.confirm_dialog_title);
	}
	
	/**
	 * 设置取消按钮事件
	 * @param resId 显示的资源ID
	 * @param onClickListener 响应监听
	 */
	public void setCancelButton(int resId,
			final OnClickListener onClickListener) {
		btu_cancel.setText(resId);
		btu_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickListener.onClick(ConfirmDialog.this, btu_cancel);
				dismiss();
			}
		});
	}

	/**
	 * 设置取消按钮事件
	 * @param resId 显示的资源
	 * @param onClickListener 响应监听
	 */
	public void setCancelButton(String res,
			final OnClickListener onClickListener) {
		btu_cancel.setText(res);
		btu_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickListener.onClick(ConfirmDialog.this, btu_cancel);
				dismiss();
			}
		});
	}

	/**
	 * 设置确认按钮事件
	 * @param resId 显示的资源的ID
	 * @param onClickListener 响应监听
	 */
	public void setOkButton(int resId,
			final OnClickListener onClickListener) {
		btu_ok.setText(resId);
		btu_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickListener.onClick(ConfirmDialog.this, btu_ok);
				dismiss();
			}
		});
	}
	/**
	 * 设置确认按钮事件
	 * @param resId 显示的资源
	 * @param onClickListener 响应监听
	 */
	public void setOkButton(String res,
			final OnClickListener onClickListener) {
		btu_ok.setText(res);
		btu_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickListener.onClick(ConfirmDialog.this, btu_ok);
				dismiss();
			}
		});
	}

	/**
	 * 设置显示的内容
	 * @param msgResId 显示的资源ID
	 */
	public void setMessage(int msgResId) {
		msgTextView.setText(msgResId);
	}

	/**
	 * 设置显示的内容
	 * @param msgResId 显示的资源
	 */
	public void setMessage(String msg) {
		msgTextView.setText(msg);
	}
	
	/**
	 * 设置标题
	 */
	@Override
	public void setTitle(CharSequence title) {
		titleTextView.setText(title);
	}

	public interface OnClickListener {
		void onClick(Dialog dialog, View view);
	}
	
	public void setSigleBtn(){
		btu_cancel.setVisibility(View.GONE);
	}
	
	
}
