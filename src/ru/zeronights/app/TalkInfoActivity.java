package ru.zeronights.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class TalkInfoActivity extends Activity {

	protected AppPreferences appPrefs;
	int talkId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_info);

        appPrefs = new AppPreferences(getApplicationContext());
        
        Intent intent = getIntent();
        talkId = intent.getIntExtra(TalkListActivity.TALK_ID,0);
        
        MySQLiteHelper db = new MySQLiteHelper(this);
  
        try {
        	db.openDatabase();
        } catch(SQLiteException sqle){
        	throw sqle;
        }
        
        Talk talk = db.getTalk(talkId);
        
        TextView tv = (TextView) findViewById(R.id.TalkHeader);
        tv.setText(talk.getHeader());
        tv = (TextView) findViewById(R.id.TalkBody);
        tv.setText(talk.getBody());
        tv = (TextView) findViewById(R.id.TalkSpeaker);
        tv.setText(talk.getSpeakers());
        
        db.close();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_talk_info, menu);
        return true;
    }

    
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		View v=null;
		Intent intent = null;
	    switch (item.getItemId()) {
	        case R.id.Russian:
	            appPrefs.setLang("ru");
	            intent = new Intent(this, SpeakerInfo.class);
	            intent.putExtra(TalkListActivity.TALK_ID,talkId);
	            startActivity(intent);
	            return true;
	        case R.id.English:
	        	appPrefs.setLang("en");
	        	intent = new Intent(this, SpeakerInfo.class);
	            intent.putExtra(TalkListActivity.TALK_ID,talkId);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
