package com.geek.geekmall.control;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

import com.geek.geekmall.Constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsContent extends ContentObserver {
	public static final String SMS_URI_INBOX = "content://sms/inbox";
	private String smsContent = "";
	private EditText verifyText = null;
	private String phone;
	private Context context;

	public SmsContent(Context context, Handler handler,
					  EditText verifyText, String phone) {
		super(handler);
		this.verifyText = verifyText;
		this.phone = phone;
		this.context = context;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Cursor cursor = null;// 光标
		// 读取收件箱中指定号码的短信
//		cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[] {
//				"_id", "address", "body", "read" }, "address=? and read=?",
//				new String[] { phone, "0" }, "date desc");
		cursor = context.getContentResolver().query(Uri.parse(SMS_URI_INBOX), new String[] {
				 "address", "body" }, "address=? ",
				new String[] { Constant.SMS}, "date desc");
		if (cursor != null) {// 如果短信为未读模式
			if (cursor.moveToFirst()) {
				String smsbody = cursor
						.getString(cursor.getColumnIndex("body"));
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(smsbody.toString());
				smsContent = m.replaceAll("").trim().toString();
				verifyText.setText(smsContent);
			}
		}
		cursor.close();
	}
}
