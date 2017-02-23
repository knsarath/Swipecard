package com.test.swipecard;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SwipeHandler mSwipeHandler;
    private SwipeSurface mSwipeSurface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeHandler = new SwipeHandler();
        mSwipeSurface = (SwipeSurface) findViewById(R.id.frm);
        List<CardData> list = getDummyData();
        Log.d(TAG, "Size = " + list.size());
        mSwipeSurface.setAdapter(new SwipeSurface.Adapter(list, R.layout.frame_layout));


        Button closeToLeftbutton = (Button) findViewById(R.id.close_left);
        Button closeToRightbutton = (Button) findViewById(R.id.close_right);
        closeToLeftbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View topCard = getTopCard();
                mSwipeHandler.closedToLeft(topCard);
            }
        });
        closeToRightbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View topCard = getTopCard();
                mSwipeHandler.closedToRight(topCard);
            }
        });
    }

    private List<CardData> getDummyData() {
        final ArrayList<CardData> cardDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cardDatas.add(new CardData());
        }
        return cardDatas;
    }

    private View getTopCard() {
        final int index = mSwipeSurface.getChildCount() - 1;
        final View view = mSwipeSurface.getChildAt(index);

            return view;

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
