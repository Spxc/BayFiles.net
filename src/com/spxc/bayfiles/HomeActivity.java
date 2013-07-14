package com.spxc.bayfiles;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.spxc.bayfiles.adapter.fileObject;
import com.spxc.bayfiles.adapter.fileObjectAdapter;

import com.spxc.bayfiles.R;

public class HomeActivity extends SherlockActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dblist);
        
        ArrayList<fileObject> results = new ArrayList<fileObject>();
        
        fileObject sr1 = new fileObject();
        sr1.setFileName("Test.png");
        sr1.setSize("1024 KB");
        sr1.setSha1("214-555-1234");
        results.add(sr1);
        
        final ListView lv1 = (ListView) findViewById(R.id.listobjects);
        lv1.setAdapter(new fileObjectAdapter(this, results));
        
        lv1.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
          Object o = lv1.getItemAtPosition(position);
          fileObject fullObject = (fileObject)o;
          Toast.makeText(HomeActivity.this, "You have chosen: " + " " + fullObject.getFileName(), Toast.LENGTH_LONG).show();
         }  
        });
    }
}