package com.tool.search.id;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IDDBAdapter {
	public static final String KEY_ID 		="_id";
	public final static String KEY_CODE 	= "code";
	public final static String KEY_LOCATION	= "location";
	public final static String KEY_BIRTHDAY	= "birthday";
	public final static String KEY_GENDER 	= "gender";
	
	private String code;
	private String location;
	private String birthday;
	private String gender;
	
	private static final String DATABASE_NAME 	= "IdDb";
	private static final String DATABASE_TABLE	= "IdTab";
	private static final int DATABASE_VERSION 	= 1;
	private static final String DATABASE_CREATE = "create table IdTab (_id integer primary key autoincrement, "
			+ "code text not null,location text not null,birthday text not null,gender text not null);";
	
	public static final String KEY_ALL[] = {
		KEY_ID,KEY_CODE,KEY_LOCATION,KEY_BIRTHDAY,KEY_GENDER
	};
	
	
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	
	public IDDBAdapter(Context context) {
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS phoneNum");
			onCreate(db);
		}
	}
	
	/** ---打开数据库--- */
	public IDDBAdapter open()throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	/** ---关闭数据库--- */
	public void close(){
		DBHelper.close();
	}

	/** ---向数据库中插入一个标题--- */
	public long insertTitle(String content[]) {
		ContentValues initialValues = new ContentValues();
		for(int i=0;i<content.length;i++){
			if(KEY_ALL.length > i + 1)
				initialValues.put(KEY_ALL[i +1], content[i]);
		}
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	/** ---删除一个指定标题--- */
	public boolean deleteTitle(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	}

	/** ---检索所有标题--- */
	public Cursor getAllTitles(){
		return db.query(DATABASE_TABLE, KEY_ALL, null, null, null, null, null);
	}

	/** ---检索一个指定标题--- */
	public Cursor getTitle(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, KEY_ALL, 
				KEY_ID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	/** ---更新一个标题--- */
	public boolean updateTitle(long rowId,String content[]) {
		ContentValues initialValues = new ContentValues();
		for(int i=0;i<content.length;i++){
			if(KEY_ALL.length > i + 1)
				initialValues.put(KEY_ALL[i + 1], content[i]);
		}
		return db.update(DATABASE_TABLE, initialValues, KEY_ID + "=" + rowId, null) > 0;
	}
}
