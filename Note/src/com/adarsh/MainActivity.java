package com.adarsh;

import java.util.ArrayList;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ListView lv;
	DatabaseHelper db;
	String name;
	TextView noData;
	Button add;
	InterstitialAd advert;
	ArrayList<String> al;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv=(ListView) findViewById(R.id.listView1);
		advert=new InterstitialAd(this);
		advert.setAdUnitId("ca-app-pub-4742321996142991/7247782067");
		advert.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				// TODO Auto-generated method stub
				requestNewInterstitial();
				super.onAdClosed();
			}
		});
			requestNewInterstitial();
		add=(Button) findViewById(R.id.button1);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getApplicationContext(),Note.class);
				startActivity(in);
				finish();
			}
		});
		noData=(TextView) findViewById(R.id.noData);
		al=new ArrayList<String>();
		db=new DatabaseHelper(this);
		lv.setAdapter(new CustomAdapter(this, al));
		Cursor res=db.getAllData();
		if(res.getCount()==0){
			noData.setVisibility(View.VISIBLE);
			lv.setVisibility(View.INVISIBLE);
		}
		
		else
			while(res.moveToNext()){
				al.add(res.getString(res.getColumnIndex("NAME")));
			}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				String title=parent.getItemAtPosition(position).toString();
				Intent in=new Intent(getApplicationContext(), Edit.class);
				in.putExtra("title", title);
				startActivity(in);
				finish();
			}
		});
		
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
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if(advert.isLoaded()){
	    		advert.show();
	    		finish();
	    	}
	    	else
	    		
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	public void requestNewInterstitial(){
		AdRequest adRequest = new AdRequest.Builder()
	              .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	              .build();

	    advert.loadAd(adRequest);
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle(name);
		menu.add(0, v.getId(), 0, "Delete?");
	}
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(db.deleteData(name)==0)
			Toast.makeText(getApplicationContext(), "Unable To Delete", Toast.LENGTH_LONG).show();
		else{
			Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
			onCreate(null);
		}
		return super.onContextItemSelected(item);
	}

}
