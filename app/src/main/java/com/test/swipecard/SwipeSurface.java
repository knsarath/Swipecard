package com.test.swipecard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sarath on 23/2/17.
 */

public class SwipeSurface extends FrameLayout implements SwipeHandler.ViewSwipeOutListener {

    private static final String TAG = "SwipeSurface";
    private SwipeHandler mSwipeHandler = new SwipeHandler();
    private Adapter mAdapter;
    private static final int IN_MEMORY_VIEW_LIMIT = 2;
    private int mCurrentTop;


    public SwipeSurface(Context context) {
        super(context);
        init(null);
    }

    public SwipeSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mSwipeHandler.setViewSwipeOutListener(this);
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        final int count = adapter.getCount();
        if (count > 0 && mCurrentTop < mAdapter.getCount()) {
            for (int i = 0; i < IN_MEMORY_VIEW_LIMIT; i++) {
                final View view = mAdapter.getView(mCurrentTop, null, this);
                addCard(view);
            }
        }
    }

    private void addCard(View view) {
        SwipeItem swipeItem = new SwipeItem(getContext());
        swipeItem.addView(view);
        addView(swipeItem, 0);
        mCurrentTop++;
        swipeItem.setOnTouchListener(mSwipeHandler);
    }

    @Override
    public void onViewSwipedOut(View view) {
        Log.d(TAG, "View swiped out");
        if (mCurrentTop < mAdapter.getCount()) {
            final View newView = mAdapter.getView(mCurrentTop, null, this);
            addCard(newView);
        }
    }


    public void swipeOutLeft() {
        mSwipeHandler.closedToLeft(this.getChildAt(this.getChildCount() - 1));
    }

    public void swipeOUtRight() {
        mSwipeHandler.closedToRight(this.getChildAt(this.getChildCount() - 1));
    }


    public static class Adapter<T> extends BaseAdapter {
        private List<T> mList;
        private int mLayoutResId;

        public Adapter(List<T> list, int layoutResId) {
            mList = list;
            mLayoutResId = layoutResId;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public T getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                Log.d(TAG, "Convertview is null creating new view");
                convertView = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, null, false);
            } else {
                Log.d(TAG, "Convertview is " + convertView + " reusing view");
            }
            final TextView textView = (TextView) convertView.findViewById(R.id.txt);
            textView.setText(position + "");
            return convertView;
        }
    }


    /**
     * Created by sarath on 23/2/17.
     */

    public static class SwipeItem extends FrameLayout {
        public SwipeItem(Context context) {
            super(context);
            setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return true;
        }
    }

}
