package com.adarsh;

import com.google.android.gms.ads.AdView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends Activity {
	
	TextView name;
	EditText content;
	Button save;
	DatabaseHelper db;
	String title,cont;
	boolean yesno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		Toast.makeText(getApplicationContext(), "Happy Editing :-)", Toast.LENGTH_LONG).show();
		AdView av=(AdView) findViewById(R.id.adView);
		com.google.android.gms.ads.AdRequest request = new com.google.android.gms.ads.AdRequest.Builder().build();
		av.loadAd(request);
		ActionBar actionbar=getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		name=(TextView) findViewById(R.id.textView1);
		content=(EditText) findViewById(R.id.editText1);
		title= getIntent().getExtras().getString("title");
		actionbar.setTitle(title);
		db=new DatabaseHelper(this);
		
		
		//Reading file
		Cursor dat = db.getData(title);
		if(dat.moveToFirst())
		content.setText(dat.getString(dat.getColumnIndex("DATA")));
		cont=content.getText().toString();
		
		content.setFocusableInTouchMode(true);
		content.setFocusable(true);
		content.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(content, InputMethodManager.SHOW_FORCED);
	}
	
	//Back Button Pressed
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if(cont.equals(content.getText().toString())){
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
				if(cont.equals(content.getText().toString())){
					Log.d("Strings", "cont "+cont);
					Log.d("Strings", "content "+content.getText().toString());
					Intent in=new Intent(getApplicationContext(), MainActivity.class);
					startActivity(in);
					finish();
				}
				else
		    		showAlert();
		return super.onOptionsItemSelected(item);
	}
	
	public void showAlert(){
		AlertDialog.Builder ab=new AlertDialog.Builder(this);
		ab.setTitle("Confirmation");
		ab.setMessage("You made changes, do you want to save it?");
		ab.setCancelable(true);
		ab.setPositiveButton("yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String data=content.getText().toString();
				if(db.updateData(title, data)){
					Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
					Intent in=new Intent(getApplicationContext(), MainActivity.class);
					startActivity(in);
					finish();
				}
				else
					Toast.makeText(getApplicationContext(), "Error Updating", Toast.LENGTH_LONG).show();
			}
		});
		ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getApplicationContext(), MainActivity.class);
				startActivity(in);
				finish();
			}
		});
		AlertDialog alert=ab.create();
		alert.show();
	}

}
