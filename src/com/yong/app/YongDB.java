package com.yong.app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class YongDB {

	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "Yong";

	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;

	private static YongDB yongDB;

	private SQLiteDatabase db;

	/**
	 * 将构造方法私有化
	 */
	private YongDB(Context context) {
		YongOpenHelper dbHelper = new YongOpenHelper(context,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 获取YongDB的实例。
	 */
	public synchronized static YongDB getInstance(Context context) {
		if (yongDB == null) {
			yongDB = new YongDB(context);
		}
		return yongDB;
	}

	/**
	 * 将hanzi实例存储到数据库。
	 */
	public void saveHanzi(Yong hanzi) {
		if (hanzi != null) {
			ContentValues values = new ContentValues();
			values.put("hanziUTF", hanzi.getmHanziUTF());
			values.put("hanzi_code", hanzi.getmPathString());
			db.insert("hanzi", null, values);
		}
	}

	/**
	 * 依据utf码从数据库读取汉字。
	 */
	public Yong loadHanzi(String hanziUTF) {
		Cursor cursor = db.rawQuery("SELECT * FROM Hanzi WHERE hanziUTF = ?", new String[]{hanziUTF});
		while (cursor.moveToNext()) {
			String hanzi = cursor.getString(cursor.getColumnIndex("Hanzi"));
			String pathString = cursor.getString(cursor.getColumnIndex("PathString"));
			int fivetype = cursor.getInt(cursor.getColumnIndex("Fivetype"));
			Yong yong = new Yong(hanzi, hanziUTF, fivetype, pathString);
			return yong;
		}
		return null;
	}
}