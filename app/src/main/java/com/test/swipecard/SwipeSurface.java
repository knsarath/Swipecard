package com.test.swipecard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by sarath on 23/2/17.
 */

public class SwipeSurface extends FrameLayout {

    private SwipeHandler mSwipeHandler = new SwipeHandler();
    private Adapter mAdapter;

    public SwipeSurface(Context context) {
        super(context);
        init(null);

    }

    public SwipeSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

    }

    public void setAdapter(Adapter adapter) {
        final int count = adapter.getCount();

        for (int i = 0; i < count; i++) {
            SwipeItem swipeItem = new SwipeItem(getContext());
            swipeItem.addView(adapter.getView(i, null, this));
            addView(swipeItem);
            swipeItem.setOnTouchListener(mSwipeHandler);
        }
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
            final View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, null, false);

            return view;
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
