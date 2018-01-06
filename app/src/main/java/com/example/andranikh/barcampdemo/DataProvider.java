package com.example.andranikh.barcampdemo;

import com.example.andranikh.barcampdemo.decorated_mode.DecoratedItem;
import com.example.andranikh.barcampdemo.simple_mode.SimpleItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by andranikh on 5/27/17.
 */

public class DataProvider {

    private static List<SimpleItem> simpleItems;
    private static List<DecoratedItem> decoratedItems;

    private static Random random;

    public static int[] avatars = {
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7,
            R.drawable.img_8,
            R.drawable.img_9,
            R.drawable.img_10,
            R.drawable.img_11,
            R.drawable.img_12,
            R.drawable.img_13,
            R.drawable.img_14,
            R.drawable.img_15,
            R.drawable.img_16,
    };

    static {
        random = new Random();

        simpleItems = new ArrayList<>();
        decoratedItems = new ArrayList<>();

        for (int i = 0; i < 100; i++){
            simpleItems.add(new SimpleItem(i));
        }


        for (int i = 0; i < avatars.length; i++){
            decoratedItems.add(new DecoratedItem(String.format("%skm", random.nextInt(100)), avatars[i]));

            if(i % 5 == 0){
                decoratedItems.add(new DecoratedItem(String.format("%skm", random.nextInt(100)), -1));
            }
        }

    }

    public static List<SimpleItem> getSimpleItems(){
        return simpleItems;
    }

    public static List<DecoratedItem> getDecoratedItems(){
        return decoratedItems;
    }
}
