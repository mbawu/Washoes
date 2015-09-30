package com.cn.washoes.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 更新组件
 */
public class DirectEmailComponent {
	private Context context;
	public DirectEmailComponent( Context context) {
		 this.context = context;
	}
	/**
	 * 直接发送信息
	 * @param mailTo
	 * @param subject
	 * @param messageBody
	 */
	public void sendMail(String subject, String messageBody, String to ,String mailName) {
		Session session = createSessionObject();
		try {
			Message message = createMessage(to, subject, messageBody, mailName,session);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 带提示框的发送
	 * 
	 * @param mailTo
	 * @param subject
	 * @param messageBody
	 */
	public void sendMailTask(String mailTo, String subject, String messageBody,String mailName) {
		Session session = createSessionObject();
		try {
			Message message = createMessage(mailTo, subject, messageBody,mailName ,session);
			new SendMailTask().execute(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private Message createMessage(String mailTo, String subject, String messageBody,String mailName, Session session) throws MessagingException,
			UnsupportedEncodingException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("meiling_shop@126.com", mailName));
//		message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo, mailTo));
		InternetAddress[] iaToList = new InternetAddress().parse(mailTo) ;
		message.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
		message.setSubject(subject);
		message.setText(messageBody);
		return message;
	}

	private Session createSessionObject() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.126.com");
		properties.put("mail.smtp.port", "25");
		return Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("meiling_shop@126.com", "gzjxnlurphmkdtqo");
			}
		});
	}

	private class SendMailTask extends AsyncTask<Message, Void, Void> {
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(context, "Please wait", "Sending mail to report the issue.", true, false);
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			progressDialog.dismiss();
		}

		@Override
		protected Void doInBackground(Message... messages) {
			try {
				Transport.send(messages[0]);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}