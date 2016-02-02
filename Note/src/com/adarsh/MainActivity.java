package com.adarsh;

import java.io.File;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore.Files;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ListView lv;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv=(ListView) findViewById(R.id.listView1);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				String title=parent.getItemAtPosition(position).toString();
				Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
				Intent in=new Intent(getApplicationContext(), Edit.class);
				in.putExtra("title", title);
				startActivity(in);
			}
		});
		
		Operation();
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				name=parent.getItemAtPosition(position).toString();
				return false;
			}
		});
		
		registerForContextMenu(lv);
	}
	
	
	void Operation(){
		ArrayList<String> filesinfolder=getfiles(getFilesDir().getAbsolutePath()+"/");
		if(filesinfolder==null)
			Toast.makeText(getApplicationContext(), "Its lonley in here..",Toast.LENGTH_LONG).show();
		else
			lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filesinfolder));
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, v.getId(), 0, "Delete");
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
			File fdelete=new File(getFilesDir().getAbsolutePath()+"/"+name);
			if(fdelete.exists()){
				if(fdelete.delete()){
					Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(getApplicationContext(), "Can't Delete It", Toast.LENGTH_LONG).show();
				}
				Operation();
			}
			else
			Toast.makeText(getApplicationContext(), "Can't Find It..", Toast.LENGTH_LONG).show();
		return super.onContextItemSelected(item);
	}
	
	public ArrayList<String> getfiles(String directory){
		ArrayList<String> myFiles=new ArrayList<String>();
		File f=new File(directory);
		f.mkdirs();
		File[] files=f.listFiles();
		if(files.length==0){
			return null;
			}
		else{
			for(int i=0;i<files.length;i++){
				myFiles.add(files[i].getName());
			}
		}
		return myFiles;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add) {
			Intent in=new Intent(getApplicationContext(),Note.class);
			startActivity(in);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
