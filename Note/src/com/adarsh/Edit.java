package com.adarsh;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import org.apache.http.message.BufferedHeader;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends Activity {
	
	TextView name;
	EditText content;
	Button save;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		ActionBar actionbar=getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		name=(TextView) findViewById(R.id.textView1);
		content=(EditText) findViewById(R.id.editText1);
		save=(Button) findViewById(R.id.button1);
		String title = getIntent().getExtras().getString("title");
		name.setText(title);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String data=content.getText().toString();
				FileOutputStream fos;
				try {
					fos=openFileOutput(name.getText().toString(), Context.MODE_PRIVATE);
					fos.write(data.getBytes());
					fos.close();
					Toast.makeText(getApplicationContext(), name.getText().toString()+" Saved...", Toast.LENGTH_LONG).show();
					finish();
					
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		
		//Reading file
		StringBuffer sb=new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(openFileInput(title)));
			String inputstring=null;
			while ((inputstring = in.readLine())!= null) {
				sb.append(inputstring+"\n");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		content.setText(sb.toString());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		finish();
		return super.onOptionsItemSelected(item);
	}

}
