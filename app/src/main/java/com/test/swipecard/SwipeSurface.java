package com.test.swipecard;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
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
    private Adapter mAdapter;
    private static final int IN_MEMORY_VIEW_LIMIT = 3;
    private int mCurrentTop;
    private static String ADAPTER_POSITION = "current_top";
    private static int CARD_STACK_MARGIN = 20;
    ;


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
        mAdapter = adapter;
        final int count = adapter.getCount();
        removeAllViews();
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
        for (int i = 0; i < getChildCount(); i++) {
            final LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getChildAt(i).setLayoutParams(params);
            params.setMargins(0, CARD_STACK_MARGIN * (IN_MEMORY_VIEW_LIMIT - i), 0, 0);
        }
        mCurrentTop++;
        swipeItem.setOnTouchListener(new SwipeHandler(this));
    }

    @Override
    public void onViewSwipedOut(View view) {
        removeView(view);
        if (mCurrentTop < mAdapter.getCount()) {
            final View newView = mAdapter.getView(mCurrentTop, null, this);
            addCard(newView);
        }
    }


    public void swipeOutLeft() {
        new SwipeHandler(this).closedToLeft(this.getChildAt(this.getChildCount() - 1));
    }

    public void swipeOUtRight() {
        new SwipeHandler(this).closedToRight(this.getChildAt(this.getChildCount() - 1));
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, null, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.card_num);
            textView.setText("CARD " + (position + 1));
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


    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.CURRENT_ADAPTER_POSITION = this.mCurrentTop;
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mCurrentTop = ss.CURRENT_ADAPTER_POSITION;
    }


    private static class SavedState extends BaseSavedState {
        int CURRENT_ADAPTER_POSITION;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.CURRENT_ADAPTER_POSITION = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.CURRENT_ADAPTER_POSITION);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }


}
