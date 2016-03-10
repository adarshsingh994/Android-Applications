package com.adarsh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, "NOTE", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE NOTES(NAME VARCHAR, DATA VARCHAR, TIME VARCHAR);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS NOTES");
		db.close();
	}
	
	public Boolean insertData(String name, String data, String time){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put("NAME", name);
		cv.put("DATA", data);
		cv.put("TIME", time);
		long result=db.insert("NOTES", null, cv);
		if(result == -1)
			return false;
		else
			return true;
					
	}
	
	public Cursor getData(String name){
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor res=db.rawQuery("SELECT * FROM NOTES WHERE NAME=?", new String[] {name});
		return res;
	}
	
	public Cursor getAllData(){
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor res=db.rawQuery("SELECT * FROM NOTES", null);
		return res;
	}
	
	public Boolean updateData(String name, String data){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put("NAME", name);
		cv.put("DATA", data);
		db.update("NOTES", cv, "NAME=?", new String[] {name});
		return true;
		
	}
	
	public Integer deleteData(String name){
		SQLiteDatabase db=this.getWritableDatabase();
		return db.delete("NOTES", "NAME=?", new String[] {name});
	}

}
