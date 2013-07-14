package com.spxc.bayfiles.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONfunctions {

	public static JSONObject getJSONfromURL(String url){
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		
		//http post
	    try{
	    	HttpClient httpclient = new DefaultHttpClient();
	    	HttpPost httppost = new HttpPost(url);

	    	try {
	    	    // Add your data
	    	    /*List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	    	    nameValuePairs.add(new BasicNameValuePair("key", "stianxxs"));
	    	    nameValuePairs.add(new BasicNameValuePair("secret", "mhfgpammv9f94ddayh8GSweji"));
	    	    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); */

	    	    // Execute HTTP Post Request
	    	    HttpResponse response = httpclient.execute(httppost);
	    	    //HttpResponse response = httpclient.execute(httppost);
	    	    HttpEntity httpEntity = response.getEntity();
	    	    is = httpEntity.getContent();

	    	} catch (ClientProtocolException e) {
	    	    // TODO Auto-generated catch block
	    	} catch (IOException e) {
	    	    // TODO Auto-generated catch block
	    	}

	    }catch(Exception e){
	            Log.e("log_tag", "Error in http connection "+e.toString());
	    }
	    
	  //convert response to string
	    try{
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                    sb.append(line + "\n");
	            }
	            is.close();
	            result=sb.toString();
	    }catch(Exception e){
	            Log.e("log_tag", "Error converting result "+e.toString());
	    }
	    
	    try{
	    	
            jArray = new JSONObject(result);            
	    }catch(JSONException e){
	            Log.e("log_tag", "Error parsing data "+e.toString());
	    }
    
	    return jArray;
	}
}
