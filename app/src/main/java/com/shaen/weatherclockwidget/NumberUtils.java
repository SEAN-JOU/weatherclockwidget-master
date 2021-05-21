package com.shaen.weatherclockwidget;

public class NumberUtils {


    public static boolean isNumber(String string) {
        char[] characters = string.toCharArray();
        int number = 0;
        for (char c : characters) {
            try {
                number = Integer.valueOf(c) + number;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
