package com.test.swipecard;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by sarath on 27/2/17.
 */

public class Util {
    public static int getColor() {
        Random randomGenerator = new Random();
        int red = randomGenerator.nextInt(256);
        int green = randomGenerator.nextInt(256);
        int blue = randomGenerator.nextInt(256);
        int randomColour = Color.rgb(red, green, blue);
        return randomColour;
    }
}
