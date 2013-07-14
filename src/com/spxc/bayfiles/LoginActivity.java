package com.spxc.bayfiles;

import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;
import com.spxc.bayfiles.prefs.AppPrefs;
import com.spxc.bayfiles.task.JsonAsync;
import com.spxc.bayfiles.task.JsonAsync.JsonListener;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spxc.bayfiles.R;

public class LoginActivity extends SherlockActivity {

	EditText un,pw;
	TextView error, session;
    Button ok, files;
    private ProgressDialog mDialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		getSupportActionBar().hide();
		
		un = (EditText)findViewById(R.id.user);
		pw = (EditText)findViewById(R.id.psw);
		ok = (Button)findViewById(R.id.button1);
		un.setText("spxc");
		pw.setText("mess2005");
		ok.setOnClickListener(new View.OnClickListener() {

		     @Override
		     public void onClick(View v) {
		    	JsonAsync asyncTask = new JsonAsync();
		 		asyncTask.setJsonListener(new JsonListener() {
		 			public void onObjectReturn(JSONObject object) {
		 				handleJsonObject(object);
		 			}
		 		});
		 		asyncTask.execute("http://api.bayfiles.net/v1/account/login/"+un.getText()+"/"+pw.getText());
		     }
		});		
	}
	
	private void handleJsonObject(JSONObject object) {

		try {
			String session = object.getString("session");

			String name = session;
			Context context = getApplicationContext();
			AppPrefs appPrefs = new AppPrefs(context);
			appPrefs.setSessionId(name);
			
				Intent startNewActivityOpen = new Intent(LoginActivity.this, FilesActivity.class);
		    	 startActivityForResult(startNewActivityOpen, 0);
		    	 finish();
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data: " + e.toString());
		}
		}{

		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

}
