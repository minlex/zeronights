package ru.zeronights.app;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteException;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SpeakerInfo extends Activity {

	protected AppPreferences appPrefs;
	int speakerId;
	public final String PHOTO_PATH = "/data/data/ru.zeronights.app/speaker_photo/";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_info);
        
        appPrefs = new AppPreferences(getApplicationContext());
        
        Intent intent = getIntent();
        speakerId = intent.getIntExtra(SpeakerListActivity.SPEAKER_ID,0);
        
        MySQLiteHelper db = new MySQLiteHelper(this);
      /*  try {
        	db.createDatabase();
        } catch (IOException ioe){
        	throw new Error("Unable to create database");
        }
        */
        try {
        	db.openDatabase();
        } catch(SQLiteException sqle){
        	throw sqle;
        }
        
        Speaker speaker = db.getSpeaker(speakerId);
        
        TextView tv = (TextView) findViewById(R.id.SpeakerName);
        tv.setText(speaker.getName());
        tv = (TextView) findViewById(R.id.SpeakerBio);
        tv.setText(speaker.getBio());
        tv = (TextView) findViewById(R.id.SpeakerTalk);
        int talkId = speaker.getTalk();
        Talk talk = db.getTalk(talkId-1);
        tv.setPaintFlags(tv.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        tv.setText(talk.getHeader());
        tv.setTag(talkId);
        
        ImageView iv = (ImageView) findViewById(R.id.SpeakerPhoto);
        String photo = speaker.getPhoto();
       
        //Drawable d  = Drawable.createFromPath("ru/zeronights/app/zalipukha.png");
      //  Drawable d  = Drawable.createFromPath("drawable/zalipukha");
        InputStream is=null;
		try {
			is = getResources().getAssets().open("speaker_photo/"+photo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Drawable d = Drawable.createFromStream(is, null);
        iv.setImageDrawable(d); 
        
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_speaker_info, menu);
        return true;
    }
    
    public void startTalk(View view){
    	int talkId = (Integer) view.getTag();
    	Intent intent = new Intent(this, TalkInfoActivity.class);
    	intent.putExtra(TalkListActivity.TALK_ID, talkId-1);
    	startActivity(intent);
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		View v=null;
		Intent intent = null;
	    switch (item.getItemId()) {
	        case R.id.Russian:
	            appPrefs.setLang("ru");
	            intent = new Intent(this, SpeakerInfo.class);
	            intent.putExtra(SpeakerListActivity.SPEAKER_ID, speakerId);
	            startActivity(intent);
	            return true;
	        case R.id.English:
	        	appPrefs.setLang("en");
	        	intent = new Intent(this, SpeakerInfo.class);
	            intent.putExtra(SpeakerListActivity.SPEAKER_ID, speakerId);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
