package com.example.jessi.flickrappfromudemy;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData extends AsyncTask<String, Void,   List<Photo>> implements GetRawData.OnDownloadComplete {

    private static final String TAG = "GetFlickrJsonData";

    private List<Photo> mPhotoList = null;
    private String mBaseURL;
    private String mLanguage;
    private boolean mMatchAll;

    private final OnDataAvailable mCallback;
    private boolean runningOnSamethread = false;

    interface OnDataAvailable{
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }

    public GetFlickrJsonData(OnDataAvailable callback, String baseURL, String language, boolean matchAll) {
        Log.d(TAG, "GetFlickrJsonData: called");
        mBaseURL = baseURL;
        mLanguage = language;
        mMatchAll = matchAll;
        mCallback = callback;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts status: "+ status);

        if(status == DownloadStatus.OK){
            mPhotoList = new ArrayList<>();
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");

                for (int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorID = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");
                    //so you get the big version of the photo, b is the big version
                    String link = photoUrl.replaceFirst("_m.", "_b.");

                    Photo photoObject = new Photo(title,author,authorID,link,tags,photoUrl);
                    mPhotoList.add(photoObject);

                    Log.d(TAG, "onDownloadComplete: complete" + photoObject.toString());
                }

            }catch(JSONException jsone){
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data"+jsone.getMessage() );
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        if (runningOnSamethread && mCallback !=null){
            //now inform the caller that processing is done - possibly returning null if there is an error
            mCallback.onDataAvailable(mPhotoList, status);
        }
        Log.d(TAG, "onDownloadComplete: ends");

    }

   void executeOnSameThread(String searchCritica){
       Log.d(TAG, "executeOnSameThread: starts");
       runningOnSamethread = true;
       String destinationUri = createUri(searchCritica, mLanguage, mMatchAll);

       GetRawData getRawData = new GetRawData(this);
       getRawData.execute(destinationUri);
       Log.d(TAG, "executeOnSameThread: ends");
   }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        Log.d(TAG, "onPostExecute: starts");
        if (mCallback != null)
        {
            mCallback.onDataAvailable(mPhotoList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        String destinationUri = createUri(params[0], mLanguage, mMatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        Log.d(TAG, "doInBackground: ends");

        return mPhotoList;
    }

    private String createUri(String searchCriteria, String lang, boolean matchAll){
       Log.d(TAG, "createUri: starts");

//       Uri uri = Uri.parse(mBaseURL);
//       Uri.Builder builder = uri.buildUpon();
//       builder = builder.appendQueryParameter("tags", searchCriteria);
//       builder = builder.appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY");
//       builder = builder.appendQueryParameter("lang", lang);
//       builder = builder.appendQueryParameter("format", "json");
//       builder = builder.appendQueryParameter("nojsoncallback", "1");
//       uri = builder.build(); same as bellow, a longer version

       return Uri.parse(mBaseURL).buildUpon()
               .appendPath("tags")
               .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
               .appendQueryParameter("lang", lang)
               .appendQueryParameter("format", "json")
               .appendQueryParameter("nojsoncallback", "1")
               .build().toString();
   }

}





