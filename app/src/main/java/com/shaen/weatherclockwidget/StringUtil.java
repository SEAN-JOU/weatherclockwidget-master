package com.shaen.weatherclockwidget;

public class StringUtil {
    public static String noFirstString(String[] stringArray){
        int i = 0;
        String content = "";
        for(String s: stringArray){
            if(i != 0){
                content += s;
            }
            i++;
        }
        return content;
    }
}
