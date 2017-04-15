package org.hfoss.contactapp;

import java.io.FileNotFoundException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
	public static final String KEY_NAME = "name";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_MOBILE = "mobile";
	public static final String KEY_HOME = "home";
	public static final String KEY_ROW_ID = "_id";
	
	public static final String PROJECTION[] = {
		KEY_ROW_ID,
		KEY_NAME,
		KEY_ADDRESS,
		KEY_MOBILE,
		KEY_HOME
	};
	
	private static final String TABLE_NAME = "mycontacts";
	private static final String DATABASE_NAME = "contactDB";
	
	private static final String TABLE_CREATE=
		"create table "+ TABLE_NAME + "("+ KEY_ROW_ID
		+" integer primary key autoincrement,"+ 
		KEY_NAME +" text not null,"+
		KEY_ADDRESS + " text not null,"+
		KEY_MOBILE + " text,"+
		KEY_HOME + " text)";
	private static final int DATABASE_VERSION = 1;
	
	private SQLiteDatabase db;
	
	public DBHelper(Context ctx){
		try {
            db = ctx.openDatabase(DATABASE_NAME, null);
        } catch (FileNotFoundException e) {
            try {
                db =
                    ctx.createDatabase(DATABASE_NAME, DATABASE_VERSION, 0,
                        null);
                db.execSQL(TABLE_CREATE);
            } catch (FileNotFoundException e1) {
                db = null;
            }
        }
	}
	
	public void close(){
		db.close();
	}
	
	public void createRow(String name, String address, String mobile, String home)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_ADDRESS, address);
		initialValues.put(KEY_MOBILE, mobile);
		initialValues.put(KEY_HOME, home);
		db.insert(TABLE_NAME, null, initialValues);
	}
	
	public void deleteRow(long rowId){
		db.delete(TABLE_NAME, KEY_ROW_ID+"="+rowId,null);
	}
	
	public Cursor fetchRow(long rowId) throws SQLException{
		Cursor result = db.query(true, TABLE_NAME, PROJECTION, 
				KEY_ROW_ID + "=" + rowId, null, null, null, null);
		if ((result.count() == 0) || !result.first()) {
            throw new SQLException("No note matching ID: " + rowId);
        }
        return result;
	}
	
	public Cursor fetchAllRows(){
		return db.query(TABLE_NAME, PROJECTION, 
				null, null, null, null, null);
	}
	
	public boolean updateRow (long rowId, String name, String address, String mobile, String home){
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name);
		args.put(KEY_ADDRESS, address);
		args.put(KEY_MOBILE, mobile);
		args.put(KEY_HOME, home);
		return db.update(TABLE_NAME, args, KEY_ROW_ID +"="+ rowId, null)>0;
	}
	
}
