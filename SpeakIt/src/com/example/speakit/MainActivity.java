package com.example.speakit;

import java.util.HashMap;
import java.util.Locale;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {
	
	Button speakit;
	SeekBar seek_pitch,seek_rate;
	private TextToSpeech tts;
	EditText text;
	double pitch=1.0, rate=1.0;
	String tt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tts=new TextToSpeech(this, this);
		text=(EditText) findViewById(R.id.editText1);
		speakit=(Button) findViewById(R.id.button1);
		seek_pitch=(SeekBar) findViewById(R.id.seekBar1);
		seek_rate=(SeekBar) findViewById(R.id.seekBar2);
		seek_pitch.setMax(100);
		seek_rate.setMax(100);
		seek_pitch.setProgress(50);
		seek_rate.setProgress(50);
		seek_pitch.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				tts.setPitch((float)pitch/50);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				pitch=arg1;
			}
		});
		
		seek_rate.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				tts.setSpeechRate((float)rate/50);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				rate=progress;
			}
		});
		speakit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				speakOut();
			}

		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(tts != null){
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}

	private void speakOut() {
		// TODO Auto-generated method stub
		tts.speak(text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
		
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if(status == TextToSpeech.SUCCESS){
			int result=tts.setLanguage(Locale.US);
			if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
				Toast.makeText(this, "Language Is Not Supported", Toast.LENGTH_LONG).show();
			}else {
				speakit.setEnabled(true);
				speakOut();
			}
		}else {
			Toast.makeText(this, "Initialization Failed", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> map=new HashMap<String, String>();
		tts.synthesizeToFile(text.getText().toString(),map,Environment.getExternalStorageDirectory()+"/"+text.getText().toString().substring(0, 4)+"... "+".mp3");
		map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, tt);
		Toast.makeText(getApplicationContext(), "Audio Saved To "+Environment.getExternalStorageDirectory(), Toast.LENGTH_LONG).show();
		
		return super.onOptionsItemSelected(item);

	}

}
