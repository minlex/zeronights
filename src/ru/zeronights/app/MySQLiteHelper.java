package ru.zeronights.app;

import java.io.* ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;


public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "conference_info" ;
	public static final int DATABASE_VERSION = 1;
	public static final String DB_PATH = "/data/data/ru.zeronights.app/databases/" ;
	private static final String TABLE_SPEAKERS = "speakers";
	private static final String TABLE_TALKS = "talks";
	private final Context myContext ;
	private SQLiteDatabase myDataBase; 
	protected AppPreferences appPrefs;
	
	public MySQLiteHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context ;
		appPrefs =  new AppPreferences(context);
	}
	
	public void createDatabase() throws IOException{
		
		boolean dbExist = checkDatabase();
		
		if (!dbExist) {
			this.getReadableDatabase();
			try{
				copyDatabase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
			
		
	}
	
	public boolean checkDatabase(){
		SQLiteDatabase checkDB = null;
		

    	try{
    		String myPath = DB_PATH + DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
	}
	
	  private void copyDatabase() throws IOException{
		  

	    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
	 
	    
	    	String outFileName = DB_PATH + DATABASE_NAME;
	 
	
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	 
	 
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	 
	    }
	  
	  public void openDatabase() throws SQLiteException{
		  
	    	//Open the database
	        String myPath = DB_PATH + DATABASE_NAME;
	    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	    }
	 
	    @Override
		public synchronized void close() {
	 
	    	    if(myDataBase != null)
	    		    myDataBase.close();
	 
	    	    super.close();
	 
		}

	    
		
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	public List<Speaker> getAllSpeakers(){
		List<Speaker> speakerList = new ArrayList<Speaker>();
		
		String selectQuery = "select * from " + TABLE_SPEAKERS;
		Cursor cursor = myDataBase.rawQuery(selectQuery, null);
		Speaker speaker = null;
		if (cursor.moveToFirst()){
			do {
				if (appPrefs.getLang().equals("ru")) 
					speaker = new Speaker(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("ru_name")), cursor.getString(cursor.getColumnIndex("ru_bio")), cursor.getInt(cursor.getColumnIndex("talk")),cursor.getString(cursor.getColumnIndex("photo")));
				else
					speaker = new Speaker(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("bio")), cursor.getInt(cursor.getColumnIndex("talk")),cursor.getString(cursor.getColumnIndex("photo")));

				speakerList.add(speaker);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return speakerList;
	}
	
	public Speaker getSpeaker(int id){
		String query = "select * from "+ TABLE_SPEAKERS + " where  id ="+Integer.toString(id+1); 
		Cursor cursor = myDataBase.rawQuery(query, null);
		Speaker speaker = null;
		if (cursor.moveToFirst()) {
			if (appPrefs.getLang().equals("ru")) 
				speaker = new Speaker(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("ru_name")), cursor.getString(cursor.getColumnIndex("ru_bio")), cursor.getInt(cursor.getColumnIndex("talk")),cursor.getString(cursor.getColumnIndex("photo")));
			else
				speaker = new Speaker(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("bio")), cursor.getInt(cursor.getColumnIndex("talk")), cursor.getString(cursor.getColumnIndex("photo")));

		}
		cursor.close();
		return speaker;
		
	}
	
	public Talk getTalk(int id){
		String query = "select * from " + TABLE_TALKS + " where id = " + Integer.toString(id+1);
		Cursor  cursor = myDataBase.rawQuery(query, null);
		Talk talk = null;
		if (cursor.moveToFirst()) {
			if (appPrefs.getLang().equals("ru")) 
				talk = new Talk(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("ru_name")), cursor.getString(cursor.getColumnIndex("ru_description")), cursor.getString(cursor.getColumnIndex("ru_speakers")),cursor.getInt(cursor.getColumnIndex("type")), cursor.getInt(cursor.getColumnIndex("track")),cursor.getInt(cursor.getColumnIndex("day")),cursor.getString(cursor.getColumnIndex("begintime")));
			else 
				talk = new Talk(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("description")), cursor.getString(cursor.getColumnIndex("speakers")),cursor.getInt(cursor.getColumnIndex("type")), cursor.getInt(cursor.getColumnIndex("track")),cursor.getInt(cursor.getColumnIndex("day")),cursor.getString(cursor.getColumnIndex("begintime")));

		}
		cursor.close();
		return talk;
	}

	public List<Talk> getAllTalksOnTrack(int track){
		String query = "select * from " + TABLE_TALKS + " where track = " + Integer.toString(track) + " or track = 0";
		
		Cursor  cursor = myDataBase.rawQuery(query, null);
		Talk talk = null;
		List<Talk> talkList = new ArrayList<Talk>();
		
		if (cursor.moveToFirst()) {
			do {
				
				if (appPrefs.getLang().equals("ru")) 	
					talk = new Talk(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("ru_name")), cursor.getString(cursor.getColumnIndex("ru_description")), cursor.getString(cursor.getColumnIndex("ru_speakers")),cursor.getInt(cursor.getColumnIndex("type")), cursor.getInt(cursor.getColumnIndex("track")),cursor.getInt(cursor.getColumnIndex("day")),cursor.getString(cursor.getColumnIndex("begintime")));
			else 
					talk = new Talk(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("description")), cursor.getString(cursor.getColumnIndex("speakers")),cursor.getInt(cursor.getColumnIndex("type")), cursor.getInt(cursor.getColumnIndex("track")),cursor.getInt(cursor.getColumnIndex("day")),cursor.getString(cursor.getColumnIndex("begintime")));
			talkList.add(talk);
			} while (cursor.moveToNext());
		}	
		cursor.close();
		Collections.sort(talkList);
		return talkList;
	}
	
	public List<Talk> getAllTalksOnTrack(int track, int day){
	
		String query = "select * from " + TABLE_TALKS + " where (track = " + Integer.toString(track) + " or track = 0) and day = " + Integer.toString(day);
		
		Cursor  cursor = myDataBase.rawQuery(query, null);
		Talk talk = null;
		List<Talk> talkList = new ArrayList<Talk>();
		
		if (cursor.moveToFirst()) {
			do {
			if (appPrefs.getLang().equals("ru")) 	
					talk = new Talk(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("ru_name")), cursor.getString(cursor.getColumnIndex("ru_description")), cursor.getString(cursor.getColumnIndex("ru_speakers")),cursor.getInt(cursor.getColumnIndex("type")), cursor.getInt(cursor.getColumnIndex("track")),cursor.getInt(cursor.getColumnIndex("day")),cursor.getString(cursor.getColumnIndex("begintime")));
			else 
					talk = new Talk(cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("description")), cursor.getString(cursor.getColumnIndex("speakers")),cursor.getInt(cursor.getColumnIndex("type")), cursor.getInt(cursor.getColumnIndex("track")),cursor.getInt(cursor.getColumnIndex("day")),cursor.getString(cursor.getColumnIndex("begintime")));
			talkList.add(talk);
			} while (cursor.moveToNext());
		}	
		cursor.close();
		Collections.sort(talkList);
		return talkList;
	}
}
