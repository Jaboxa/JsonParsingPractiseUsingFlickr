package com.example.jessi.flickrappfromudemy;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable, RecyclerItemClickListener.OnRecyclerClickListener {

    private static final String TAG = "MainActivity";
    private FlickrercyclerViewAdapter mFlickrercyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolbar(false);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        mFlickrercyclerViewAdapter = new FlickrercyclerViewAdapter(new ArrayList<Photo>(),this);
        recyclerView.setAdapter(mFlickrercyclerViewAdapter);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        GetFlickrJsonData getFlickrJsonData= new GetFlickrJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
        //getFlickrJsonData.executeOnSameThread("beach, sun");
        getFlickrJsonData.execute("beach,sun");
        Log.d(TAG, "onResume: ends");
       
    }

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status){
        Log.d(TAG, "onDataAvailable: starts");
        if (status == DownloadStatus.OK){
            mFlickrercyclerViewAdapter.loadNewData(data);
        }else
        {
            //download or processing failed
            Log.e(TAG, "onDataAvailable: failed with status " + status );
        }
        Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    public void OnItemClick(View view, int position) {
        Log.d(TAG, "OnItemClick: starts");

        Toast.makeText(MainActivity.this, "Normal tap at position" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemLongClick(View view, int position) {
        Log.d(TAG, "OnItemLongClick: starts");
        Toast.makeText(MainActivity.this, "Long tap at position" + position, Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(this,PhotoDetailActivity.class);
//        intent.putExtra(PHOTO_TRANSFER, mFlickrercyclerViewAdapter.getPhoto(position));
//        startActivity(intent);

        //crashes here, not sure why, it will not inflate, something with the two photo detail xml files. One shoudl inflate in teh other and they dont
    }
}
