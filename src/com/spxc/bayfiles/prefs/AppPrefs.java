package com.spxc.bayfiles.prefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class AppPrefs {
 private static final String USER_PREFS = "USER_PREFS";
 private SharedPreferences appSharedPrefs;
 private SharedPreferences.Editor prefsEditor;
 private String sessionId = "sessionId";
 private String fileId = "fileId";
 
public AppPrefs(Context context){
 this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
 this.prefsEditor = appSharedPrefs.edit();
 }
public String getFileId() {
 return appSharedPrefs.getString(fileId, "unknown");
 }
 
public void setFileId( String _user_id) {
 prefsEditor.putString(fileId, _user_id).commit();
}
public String getSessionId() {
 return appSharedPrefs.getString(sessionId, "unkown");
 }
 
 public void setSessionId( String _user_name) {
 prefsEditor.putString(sessionId, _user_name).commit();
 }
 
}
