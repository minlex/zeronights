package ru.zeronights.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {
	private static final String APP_SHARED_PREFS = "ru.zeronights.PREFS"; //  Name of the file -.xml
    private SharedPreferences appSharedPrefs;
    private Editor prefsEditor;

    public AppPreferences(Context context)
    {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
     
    }

    public String getLang() {
        return appSharedPrefs.getString("lang", "");
    }

    public void setLang(String text) {
        prefsEditor.putString("lang", text);
        prefsEditor.commit();
    }
}
