package com.spxc.bayfiles;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

import com.spxc.bayfiles.adapter.fileObject;
import com.spxc.bayfiles.adapter.fileObjectAdapter;
import com.spxc.bayfiles.prefs.AppPrefs;

import com.spxc.bayfiles.R;

public class FilesActivity extends SherlockActivity {

	EditText editsearch;
	fileObjectAdapter adapter;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dblist);
		
		 final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
		            R.layout.actionbar,
		            null);
		
		ActionBar abs = getSupportActionBar();
		abs.setDisplayHomeAsUpEnabled(false);
		abs.setTitle("BAYFILES");
		abs.setIcon(R.drawable.abs_logo);
		abs.setDisplayShowCustomEnabled(true);
	    abs.setCustomView(actionBarLayout);

	    final Button actionBarSent = (Button) findViewById(R.id.action_bar_sent);
	    actionBarSent.setText("Sent");
	    
		Context context = getApplicationContext();
		 AppPrefs appPrefs = new AppPrefs(context);
		 String sessionId = appPrefs.getSessionId();
		 
		String response = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		ResponseHandler <String> resonseHandler = new BasicResponseHandler();
		HttpPost postMethod = new HttpPost("http://api.bayfiles.net/v1/account/files?session=" + sessionId);
		
		try	{
			JSONObject json = new JSONObject();
                     
		       postMethod.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
		       postMethod.setHeader( "Content-Type", "application/json" );
		       response = httpClient.execute(postMethod,resonseHandler);
		       JSONObject request = new JSONObject(response);
		       ArrayList<fileObject> objectList = new ArrayList<fileObject>();
		       for (Iterator<?> keyIterator = request.keys(); keyIterator.hasNext(); ) {
		           String key = (String) keyIterator.next();
		           JSONObject object = request.optJSONObject(key);

		           if (object != null) {
		        	   long l = Long.parseLong(object.getString("size"));
		        	   String size = readableFileSize(l);
		        	   
		        	   fileObject obj = new fileObject();
		        	   
		        	   obj.setFileId(key);
		        	   obj.setFileName(object.getString("filename"));
		        	   obj.setSize(size);
		        	   obj.setInfoToken(object.getString("infoToken"));
		        	   obj.setDeleteToken(object.getString("deleteToken"));
		        	   obj.setSha1(object.getString("sha1"));
		        	   objectList.add(obj);

		           }
		       }	    
		       final ListView lv1 = (ListView) findViewById(R.id.listobjects);
		       
	           lv1.setAdapter(new fileObjectAdapter(this, objectList));
	           
	           lv1.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
	             Object o = lv1.getItemAtPosition(position);
	             fileObject fullObject = (fileObject)o;
	             Toast.makeText(FilesActivity.this, "You have chosen: " + " " + fullObject.getFileName(), Toast.LENGTH_LONG).show();
	            }  
	           });
		}
		catch(Exception e)
		{      
			e.printStackTrace();
			Log.d("log_tag", "Error: " + e.toString());

		}
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Get the options menu view from menu.xml in menu folder
        getSupportMenuInflater().inflate(R.menu.menu, menu);
 
        // Locate the EditText in menu.xml
        editsearch = (EditText) menu.findItem(R.id.menu_search).getActionView();
 
        // Capture Text in EditText
        editsearch.addTextChangedListener(textWatcher);
 
        // Show the search menu item in menu.xml
        MenuItem menuSearch = menu.findItem(R.id.menu_search);
 
        menuSearch.setOnActionExpandListener(new OnActionExpandListener() {
 
            // Menu Action Collapse
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Empty EditText to remove text filtering
                editsearch.setText("");
                editsearch.clearFocus();
                return true;
            }
 
            // Menu Action Expand
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Focus on EditText
                editsearch.requestFocus();
 
                // Force the keyboard to show on EditText focus
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                return true;
            }
        });
 
        // Show the settings menu item in menu.xml
        MenuItem menuSettings = menu.findItem(R.id.menu_settings);
 
        // Capture menu item clicks
        menuSettings.setOnMenuItemClickListener(new OnMenuItemClickListener() {
 
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Auto-generated method stub
                // Do something here
                Toast.makeText(getApplicationContext(), "Nothing here!",
                        Toast.LENGTH_LONG).show();
                return false;
            }
 
        });
 
        return true;
    }
    
    private TextWatcher textWatcher = new TextWatcher() {
    	 
        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            String text = editsearch.getText().toString()
                    .toLowerCase(Locale.getDefault());
            adapter.filter(text);
        }
 
        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
            // TODO Auto-generated method stub
 
        }
 
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
            // TODO Auto-generated method stub
 
        }
 
    };
    
    public static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
