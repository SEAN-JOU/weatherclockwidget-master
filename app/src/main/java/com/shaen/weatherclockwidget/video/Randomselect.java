package com.shaen.weatherclockwidget.video;


import java.util.HashSet;
import java.util.Random;


public class Randomselect extends Random {

    String sss;
    HashSet<Integer> integerHashSet;

   void Randomselect(){

       Random rrr = new Random();
      integerHashSet=new HashSet<>();

       while(integerHashSet.size() < 6)
       {
           int randomInt =rrr.nextInt(14)+1;
           if (!integerHashSet.contains(randomInt)) {
               integerHashSet.add(randomInt);
           }}
        sss= String.valueOf(integerHashSet);

   }}
