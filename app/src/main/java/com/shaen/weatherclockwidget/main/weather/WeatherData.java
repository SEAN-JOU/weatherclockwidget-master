package com.shaen.weatherclockwidget.main.weather;

import java.io.Serializable;
import java.util.ArrayList;


public class WeatherData {


  public WeatherDataResult result;


    public class WeatherDataResult implements Serializable {
          public int offset;
          public int limit;
          public int count;
          public String sort;
          public ArrayList<WeatherDataResults> results;
    }

    public class WeatherDataResults implements Serializable{
        public String _id;
        public String locationName;
        public String startTime;
        public String endTime;
        public String parameterName1;
        public String parameterValue1;
        public String parameterName2;
        public String parameterUnit2;
        public String parameterName3;
        public String parameterUnit3;
    }



}
