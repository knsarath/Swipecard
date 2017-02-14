package com.test.swipecard;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat mGestureDetectorCompat;
    private static final String TAG = "MainActivity";
    private View mView;
    private float m;
    private float next;
    private boolean started;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureDetectorCompat = new GestureDetectorCompat(this, this);
        mView = findViewById(R.id.view);
        mView.setOnTouchListener(new SwipeHandler());


    }



    @Override
    public boolean onDown(MotionEvent e) {
        // Log.d(TAG, "onDown");
        m = 0;
        next = 0;
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //  Log.d(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //   Log.d(TAG, "onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        m = distanceX < 0 ? 1 : -1;

            mView.animate().xBy(m * 105).yBy(m * 55).rotationBy(m * 15);

        return true;
    }




    @Override
    public void onLongPress(MotionEvent e) {
        //  Log.d(TAG, "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        /*Log.d(TAG, "onFling");
        Log.d(TAG, "from " + e1.getRawX() + " to " + e2.getRawX() + "  with velocityX = " + velocityX + " and velocityY=" + velocityY);
 */
        return true;
    }
}
