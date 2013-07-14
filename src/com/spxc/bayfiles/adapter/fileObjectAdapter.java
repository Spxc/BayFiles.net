package com.spxc.bayfiles.adapter;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spxc.bayfiles.R;

public class fileObjectAdapter extends BaseAdapter {
	 private static ArrayList<fileObject> searchArrayList;
	 
	 private LayoutInflater mInflater;

	 public fileObjectAdapter(Context context, ArrayList<fileObject> results) {
	  searchArrayList = results;
	  mInflater = LayoutInflater.from(context);
	 }

	 public int getCount() {
	  return searchArrayList.size();
	 }

	 public Object getItem(int position) {
	  return searchArrayList.get(position);
	 }

	 public long getItemId(int position) {
	  return position;
	 }

	 public View getView(int position, View convertView, ViewGroup parent) {
	  ViewHolder holder;
	  if (convertView == null) {
	   convertView = mInflater.inflate(R.layout.dbitems, null);
	   holder = new ViewHolder();
	   holder.txtFileName = (TextView) convertView.findViewById(R.id.size);
	   holder.txtSize = (TextView) convertView.findViewById(R.id.filename);

	   convertView.setTag(holder);
	  } else {
	   holder = (ViewHolder) convertView.getTag();
	  }
	  
	  holder.txtFileName.setText(searchArrayList.get(position).getFileName());
	  holder.txtSize.setText(searchArrayList.get(position).getSize());

	  return convertView;
	 }

	 static class ViewHolder {
	  TextView txtFileName;
	  TextView txtSize;
	 }
	 public void filter(String charText) {
	        charText = charText.toLowerCase(Locale.getDefault());
	        searchArrayList.clear();
	        if (charText.length() == 0) {
	        	searchArrayList.addAll(searchArrayList);
	        } 
	        else
	        {
	            for (fileObject wp : searchArrayList) 
	            {
	                if (wp.getFileName().toLowerCase(Locale.getDefault()).contains(charText)) 
	                {
	                	searchArrayList.add(wp);
	                }
	            }
	        }
	        notifyDataSetChanged();
	    }
	}