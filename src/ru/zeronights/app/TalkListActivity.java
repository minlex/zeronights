package ru.zeronights.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

public class TalkListActivity extends ListActivity   implements  OnClickListener {

	public static final String TRACK = "ru.zeronights.app.TRACK";
	public static final String DAY = "ru.zeronights.app.DAY";
	
	 private static final int SWIPE_MIN_DISTANCE = 120;
	    private static final int SWIPE_MAX_OFF_PATH = 250;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	    private GestureDetector gestureDetector;
	    View.OnTouchListener gestureListener;
	    int current_day = 1;
	    
	public static final String TALK_ID = "ru.zeronights.app.TALK_ID" ;	
	private List<Talk> talkList;
	
	private ViewPager awesomePager;
	private static int NUM_AWESOME_VIEWS = 2;
    private Context cxt;

    private MyArrayAdapter adapter1;
    private MyArrayAdapter adapter2;
    int track;
    int day;
    protected AppPreferences appPrefs;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	appPrefs = new AppPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_list);
        
        Intent intent = getIntent();
        track = intent.getIntExtra(Program.TRACK, 1);   
        day = intent.getIntExtra(Program.DAY, 1);
       
        MySQLiteHelper db = new MySQLiteHelper(this);
        try {
        	db.createDatabase();
        } catch (IOException ioe){
        	throw new Error("Unable to create database");
        }
        
        try {
        	db.openDatabase();
        } catch(SQLiteException sqle){
        	throw sqle;
        }
        
        Log.d("Reading:","Reading talk info...");
        this.talkList = db.getAllTalksOnTrack(track,day);
        List<String> talkNames = new ArrayList<String>();
        for (Talk s: talkList){
        	talkNames.add(s.getHeader());
        }
        
        adapter1 = new MyArrayAdapter(this, talkList, talkNames);
        setListAdapter(adapter1);
        db.close();	
        
        gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        
        ListView v =  (ListView) findViewById(android.R.id.list);

  
       v.setOnTouchListener(gestureListener);
        
    
        
    }
  
    @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        
        int talkId = this.talkList.get(position).getId()-1;
        
        Intent intent = new Intent(this, TalkInfoActivity.class);
        intent.putExtra(TALK_ID, talkId);
        startActivity(intent);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_talk_list, menu);
        return true;
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
    
  
    
    public void switchTabInActivity(int indexTabToSwitchTo){
        Program ParentActivity;
        ParentActivity = (Program) this.getParent();
        ParentActivity.switchTab(indexTabToSwitchTo);
}
    
class MyGestureDetector extends SimpleOnGestureListener {
    	
    	/*final int swipeMinDistance = vc.getScaledTouchSlop();
	    final int swipeThresholdVelocity = vc.getScaledMinimumFlingVelocity();
	    final int swipeMaxOffPath = vc.getScaledTouchSlop();
	    */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                
                	 if (track <4)
                	 {
                		 track=track+1;
                		 switchTabInActivity(track);
                	 }
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(TalkListActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
                	
               	 
               	 if (track > 1)
               		 {
               		 track=track-1;
               		 switchTabInActivity(track);
               		 }
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

}

public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
	View v =null;
    switch (item.getItemId()) {
    case R.id.Russian:
        appPrefs.setLang("ru");
        v = findViewById(android.R.id.tabhost);
        startDay1(v);
        return true;
    case R.id.English:
    	v = findViewById(android.R.id.tabhost);
        appPrefs.setLang("en");
        startDay1(v);
        return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}
	  
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}
		
}
