package com.test.swipecard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private View mView;
    private SwipeHandler mSwipeHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeHandler = new SwipeHandler();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frm);
        for (int i = 0; i < 10; i++) {
            final FrameLayout view = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.frame_layout, frameLayout, false);
            view.setBackgroundColor(getColor());
            frameLayout.addView(view);
            view.setOnTouchListener(mSwipeHandler);
        }
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
