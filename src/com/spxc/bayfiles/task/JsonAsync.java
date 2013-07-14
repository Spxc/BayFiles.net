package com.spxc.bayfiles.task;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class JsonAsync extends AsyncTask<String, Void, JSONObject> {

	public interface JsonListener {
		public void onObjectReturn(JSONObject object);
	}

	public void setJsonListener(JsonListener jsonListener) {
		mListener = jsonListener;
	}

	private JsonListener mListener;
	public static final String TAG = JsonAsync.class.getSimpleName();

	@Override
	protected JSONObject doInBackground(String... params) {
		// The argument passed into tasks are arrays. So you can pass 0 or more
		// arguments, when calling from activity, it's really up to you. They
		// just have to be separated by commas. Like this :
		// new JsonAsync.execute("thisUrl", "thatUrl", "anotherUrl");
		// Although I am only grabbing the first item in the array, by calling
		// params[0] below.

		JSONObject object = null;

		if (params != null && params.length > 0) {
			object = JSONfunctions.getJSONfromURL(params[0]);
		} else {
			Log.e(TAG, "Task needs an argument to be able to retrieve data.");
		}

		// I return the item out of this method, because it is happening of the
		// UI thread, so it can;t update items on the screen. You can work with
		// a database from this method. That is actually recommended.
		return object;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		// This method can touch UI components without throwing an error.
		if (result != null && mListener != null) {
			mListener.onObjectReturn(result);
		}
		super.onPostExecute(result);
	}
}
