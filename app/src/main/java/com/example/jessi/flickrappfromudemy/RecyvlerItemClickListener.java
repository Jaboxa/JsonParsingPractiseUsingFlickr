package com.example.jessi.flickrappfromudemy;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";

    interface OnRecyclerClickListener {
        void OnItemClick(View view, int position);
        void OnItemLongClick(View view, int position);
    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        mListener = listener;
        mDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (childView !=null && mListener !=null){
                    Log.d(TAG, "onSingleTapUp: calling Listner.onItemClick");
                    mListener.OnItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (childView !=null && mListener !=null){
                    Log.d(TAG, "onLongPress: calling Listner.onItemLongClick");
                    mListener.OnItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");

        if (mDetector !=null){
            boolean result = mDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent(): returned" + result);
            return result;
        }else {
            Log.d(TAG, "onInterceptTouchEvent(): returned false ");
            return false;
        }

    }
}
