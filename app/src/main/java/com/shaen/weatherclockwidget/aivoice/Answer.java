package com.shaen.weatherclockwidget.aivoice;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by NB004 on 2018/6/1.
 */

public class Answer {

    int randomInt = new Random().nextInt(2) + 1;

    public Answer() {}

    public static Answer getIntence() {
        return new Answer();
    }

    public String sentence(String s, HashMap<String, String> hashMap) {
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            if (s.contains(String.valueOf(entry.getKey()))) {
                return (String) entry.getValue();
            }
        }
        return aaa(randomInt);
    }

    public static String aaa(int sss) {
        if (sss == 1)
            return "請說人話好嗎";
        else {
            return "你咧共蝦米";
        }
    }
}
