package com.test.swipecard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SwipeHandler mSwipeHandler;
    private FrameLayout mFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeHandler = new SwipeHandler();
        mFrameLayout = (FrameLayout) findViewById(R.id.frm);
        for (int i = 0; i < 10; i++) {
            final FrameLayout view = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.frame_layout, mFrameLayout, false);
            view.setBackgroundColor(getColor());
            mFrameLayout.addView(view);
            view.setOnTouchListener(mSwipeHandler);
        }

        Button closeToLeftbutton = (Button) findViewById(R.id.close_left);
        Button closeToRightbutton = (Button) findViewById(R.id.close_right);
        closeToLeftbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index = mFrameLayout.getChildCount() - 1;
                final View topCard = mFrameLayout.getChildAt(index);
                mSwipeHandler.closedToLeft(topCard);
            }
        });
        closeToRightbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index = mFrameLayout.getChildCount() - 1;
                final View topCard = mFrameLayout.getChildAt(index);
                mSwipeHandler.closedToRight(topCard);
            }
        });
    }

    private int getColor() {
        Random randomGenerator = new Random();
        int red = randomGenerator.nextInt(256);
        int green = randomGenerator.nextInt(256);
        int blue = randomGenerator.nextInt(256);
        int randomColour = Color.rgb(red, green, blue);
        return randomColour;
    }

}
