package ru.zeronights.app;


import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

public class Program extends TabActivity   implements  OnClickListener {

	public static final String TRACK = "ru.zeronights.app.TRACK";
	public static final String DAY = "ru.zeronights.app.DAY";
	String lang;
	protected AppPreferences appPrefs;
	    int current_day = 1;
	    TabHost tabHost;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        
        appPrefs = new AppPreferences(getApplicationContext());
        if (appPrefs.getLang().equals(null)){
        	String lang = Locale.getDefault().getDisplayLanguage();
        	if (lang.equals(null))
        		lang = "ru";
        	appPrefs.setLang(lang); 
        }
        Intent intent = getIntent();
        int day = intent.getIntExtra(Program.DAY, 1);
        current_day = day;
        Resources res = getResources(); 
        tabHost = getTabHost();  
        TabHost.TabSpec spec;  
     
       
        intent = new Intent().setClass(this, TalkListActivity.class);
        intent.putExtra(TRACK, 1);
        intent.putExtra(DAY, day);

       
        spec = tabHost.newTabSpec("track1").setIndicator("Track 1",
                          res.getDrawable(R.drawable.ic_tab))
                      .setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, TalkListActivity.class);
        intent.putExtra(TRACK, 2); 
        intent.putExtra(DAY, day);
        		
        spec = tabHost.newTabSpec("track2").setIndicator("Track 2",
                          res.getDrawable(R.drawable.ic_tab))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, TalkListActivity.class);
        intent.putExtra(TRACK, 3);
        intent.putExtra(DAY, day);
        
        spec = tabHost.newTabSpec("workshop1").setIndicator("Workshop 1",
                          res.getDrawable(R.drawable.ic_tab))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, TalkListActivity.class);
        intent.putExtra(TRACK, 4);
        intent.putExtra(DAY, day);
        
        spec = tabHost.newTabSpec("workshop2").setIndicator("Workshop 2",
                          res.getDrawable(R.drawable.ic_tab))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        /*
        tabHost.setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 35;
        		 tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 35;
        				 tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 35;
        						 tabHost.getTabWidget().getChildAt(3).getLayoutParams().height = 35;
        */
      
       
    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_program, menu);
        return true;
    }
    
    public void startProgram(View view){
    	Intent intent = new Intent(this, Program.class);
    	startActivity(intent);
    }
    
    public void startDay1(View view){
    	Intent intent = new Intent(this, Program.class);
    	intent.putExtra(DAY, 1);
    	startActivity(intent);
    }
    
    public void startDay2(View view){
    	Intent intent = new Intent(this, Program.class);
    	intent.putExtra(DAY, 2);
    	startActivity(intent);
    }
    public void startSpeakerList(View view){
    	Intent intent = new Intent(this, SpeakerListActivity.class);
    	startActivity(intent);
    }
    
   
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}
	
	public void switchTab(int tab){
        tabHost.setCurrentTab(tab-1);
}
		

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		View v=null;
	    switch (item.getItemId()) {
	        case R.id.Russian:
	            appPrefs.setLang("ru");
	            v = findViewById(android.R.id.tabhost);
	            startProgram(v);
	            return true;
	        case R.id.English:
	        	v = findViewById(android.R.id.tabhost);
	            appPrefs.setLang("en");
	            startProgram(v);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	}
