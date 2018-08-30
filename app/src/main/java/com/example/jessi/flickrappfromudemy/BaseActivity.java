package com.example.jessi.flickrappfromudemy;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private final String FLICKR_QUERY = "FLICKR_QUERY";
    private final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    void activateToolbar (boolean enablehome){
        Log.d(TAG, "activateToolbar: starts");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
            if(toolbar!=null){
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }
    }

}
