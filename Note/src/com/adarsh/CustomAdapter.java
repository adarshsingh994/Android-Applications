package com.adarsh;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<String> {
	DatabaseHelper db;

	public CustomAdapter(Context context, ArrayList<String> name) {
		super(context, R.layout.customlistview, name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String datademo="No data...";
		String datedemo="Date";
		String title=getItem(position);
		db=new DatabaseHelper(getContext());
		Cursor res = db.getData(title);
		if(res.moveToFirst()){
			datademo=res.getString(res.getColumnIndex("DATA"));	
			if(datademo.length()>15)
				datademo=datademo.substring(0, 15)+"...";
		}
		if(res.moveToFirst())
			datedemo=res.getString(res.getColumnIndex("TIME"));
		
		LayoutInflater inflator=LayoutInflater.from(getContext());
		View customView=inflator.inflate(R.layout.customlistview, parent, false);
		TextView name = (TextView) customView.findViewById(R.id.textView1);
		TextView data = (TextView) customView.findViewById(R.id.textView2);
		TextView date = (TextView) customView.findViewById(R.id.textView3);
		ImageView image=(ImageView) customView.findViewById(R.id.imageView1);
		name.setText(title);
		data.setText(datademo);
		date.setText(datedemo);
		image.setImageResource(R.drawable.note);
		return customView;
	}
}
