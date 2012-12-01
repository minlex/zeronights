package ru.zeronights.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SpeakerListActivity extends ListActivity  implements  OnClickListener  {

	public static final String SPEAKER_ID = "ru.zeronights.app.SPEAKER_ID" ;
	protected AppPreferences appPrefs;

	   
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_list);

        appPrefs = new AppPreferences(getApplicationContext());
        
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
        
        Log.d("Reading:","Reading speaker info...");
        List<Speaker> speakerList = db.getAllSpeakers();
        List<String> speakerNames = new ArrayList<String>();
        for (Speaker s: speakerList){
        	speakerNames.add(s.getName());
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.simple_list_item_1,R.id.Speaker, speakerNames );
        setListAdapter(adapter);
        db.close();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        
        Intent intent = new Intent(this, SpeakerInfo.class);
        intent.putExtra(SPEAKER_ID, position);
        startActivity(intent);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_speaker_list, menu);
        return true;
    }
    
    public void startProgram(View view){
    	Intent intent = new Intent(this, Program.class);
    	startActivity(intent);
    }
    
    public void startSpeakerList(View view){
    	Intent intent = new Intent(this, SpeakerListActivity.class);
    	startActivity(intent);
    }
    
    public void startDay1(View view){
    	Intent intent = new Intent(this, Program.class);
    	intent.putExtra(Program.DAY, 1);
    	startActivity(intent);
    }
    
    public void startDay2(View view){
    	Intent intent = new Intent(this, Program.class);
    	intent.putExtra(Program.DAY, 2);
    	startActivity(intent);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		View v = null;
	    switch (item.getItemId()) {
	    case R.id.Russian:
            appPrefs.setLang("ru");
            v = findViewById(android.R.id.list);
            startSpeakerList(v);
            return true;
        case R.id.English:
        	v = findViewById(android.R.id.list);
            appPrefs.setLang("en");
            startSpeakerList(v);
            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
  


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	}
