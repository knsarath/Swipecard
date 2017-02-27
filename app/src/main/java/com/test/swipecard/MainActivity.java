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
    private SwipeSurface mSwipeSurface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeSurface = (SwipeSurface) findViewById(R.id.frm);
        List<CardData> list = getDummyData();
        Log.d(TAG, "Size = " + list.size());
        mSwipeSurface.setAdapter(new SwipeSurface.Adapter(list, R.layout.frame_layout));


        /*Button closeToLeftbutton = (Button) findViewById(R.id.close_left);
        Button closeToRightbutton = (Button) findViewById(R.id.close_right);
        closeToLeftbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mSwipeSurface.swipeOutLeft();
            }
        });
        closeToRightbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeSurface.swipeOUtRight();
            }
        });*/
    }

    private List<CardData> getDummyData() {
        final ArrayList<CardData> cardDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cardDatas.add(new CardData());
        }
        return cardDatas;
    }

    private View getTopCard() {
        for (int i = 0; i < mSwipeSurface.getChildCount(); i++) {
            final View view = mSwipeSurface.getChildAt(i);
            if (!(view instanceof Button))
                return view;
        }
        return null;
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
