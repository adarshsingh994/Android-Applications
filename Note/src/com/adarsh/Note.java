package com.adarsh;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Note extends Activity {
	EditText data,title;
	Button save;
	String name="Untitled";
	DatabaseHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		
		ActionBar actionbar=getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		//Bundle extras=getIntent().getExtras();
		data=(EditText) findViewById(R.id.editText1);
		title=(EditText) findViewById(R.id.editText2);
		db=new DatabaseHelper(this);
		save=(Button) findViewById(R.id.button1);
		title.setFocusableInTouchMode(true);
		title.setFocusable(true);
		title.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(title, InputMethodManager.SHOW_FORCED);

		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean present=false;
				Calendar c=Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yy", Locale.US);
				String content=data.getText().toString();
				String currDate=sdf.format(c.getTime());
				Cursor dd=db.getAllData();
				name=title.getText().toString();
				if(title.getText().toString().equals("")||data.getText().toString().equals(""))
					Toast.makeText(getApplicationContext(), "Complete Your Note First", Toast.LENGTH_LONG).show();
				else{
					if(dd.moveToFirst())
						 do{
							if(name.equals(dd.getString(dd.getColumnIndex("NAME"))))
								present=true;
						}while (dd.moveToNext());
					if(present==true){
						AlertDialog.Builder ab=new AlertDialog.Builder(Note.this);
						ab.setTitle("Warning");
						ab.setMessage("A file with that name already exists");
						ab.setCancelable(true);
						ab.setPositiveButton("Change Name", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								title.setFocusableInTouchMode(true);
								title.setFocusable(true);
								title.requestFocus();
								InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						        imm.showSoftInput(title, InputMethodManager.SHOW_IMPLICIT);
							}
						});
						ab.setNegativeButton("Exit Without Saving", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent in=new Intent(getApplicationContext(), MainActivity.class);
								startActivity(in);
								finish();
							}
						});
						ab.show();
					}
					else{
						if(db.insertData(name, content, currDate)== true){
							Toast.makeText(getApplicationContext(), name+" Saved", Toast.LENGTH_LONG).show();
							Intent in=new Intent(getApplicationContext(), MainActivity.class);
							startActivity(in);
							finish();
						}
						else
							Toast.makeText(getApplicationContext(), "Error Inserting Data", Toast.LENGTH_LONG).show();
					}
				}
				
			}
		});
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if(title.getText().toString().equals("")){
	    		Intent in=new Intent(getApplicationContext(), MainActivity.class);
	    		startActivity(in);
	    		finish();
	    	}
	    	else
	    		showAlert();
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(title.getText().toString().equals("")){
			Intent in=new Intent(getApplicationContext(), MainActivity.class);
			startActivity(in);
			finish();
		}
		else
			showAlert();
		return super.onOptionsItemSelected(item);
	}
	
	public void showAlert(){
		AlertDialog.Builder alert=new AlertDialog.Builder(Note.this);
		alert.setTitle("Really!!");
		alert.setMessage("Don't you wanna save it ?");
		alert.setCancelable(true);
		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getApplicationContext(), MainActivity.class);
				startActivity(in);
				finish();
			}
		});
		alert.show();
	}

}
