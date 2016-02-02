package com.adarsh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Note extends Activity {
	EditText data,title;
	Button save;
	String name="Untitled";
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
		save=(Button) findViewById(R.id.button1);

		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content=data.getText().toString();
			    name=title.getText().toString();
				try {
					File myfile=new File(getFilesDir().getAbsolutePath()+"/"+name);
					myfile.createNewFile();
					FileOutputStream fout=new FileOutputStream(myfile);
					OutputStreamWriter osw=new OutputStreamWriter(fout);
					osw.append(content);
					osw.close();
					fout.close();
					Toast.makeText(getApplicationContext(), name+" Saved", Toast.LENGTH_LONG).show();
					Intent in=new Intent(getApplicationContext(), MainActivity.class);
					startActivity(in);
					finish();
					
				} catch (FileNotFoundException e) {
					Toast.makeText(getApplicationContext(), e.getMessage()+"1", Toast.LENGTH_LONG).show();
					// TODO: handle exception
				}
				catch (IOException e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), e.getMessage()+"2", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		Intent in=new Intent(getApplicationContext(), MainActivity.class);
		startActivity(in);
		super.onBackPressed();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		finish();
		Intent in=new Intent(getApplicationContext(), MainActivity.class);
		startActivity(in);
		return super.onOptionsItemSelected(item);
	}

}
