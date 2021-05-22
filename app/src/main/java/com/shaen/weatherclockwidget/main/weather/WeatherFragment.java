package com.shaen.weatherclockwidget.main.weather;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.Gson;
import com.shaen.weatherclockwidget.R;
import java.util.ArrayList;
import java.util.List;


public class WeatherFragment extends Fragment {

    ArrayList<String> citys;
    String[] directions = {"北部", "中部", "南部", "東部", "外島"};
    Spinner direction, city;
    String cityname;
    TextView citynameview;
    boolean click=true;
    String desc ="奈子貓奔跑鐘APP天氣資料來源台北市政府OPENDATA，只作參考途，奈子貓奔跑鐘APP致力確保提供的資料準確無誤APP內容如有任何錯漏，奈子貓奔跑鐘APP概不負責。";
    public List<WeatherData.WeatherDataResults> results = null;
    public List<WeatherData.WeatherDataResults> location = null;
    RecyclerView wlistview;
    Activity context;


    public WeatherFragment(){

    }
    @SuppressLint("ValidFragment")
    public WeatherFragment(Activity context) {
        this.context=context;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        wlistview = view.findViewById(R.id.wlistview);
        wlistview.setLayoutManager(new LinearLayoutManager(context));
        citynameview = view.findViewById(R.id.cityname);
        city = view.findViewById(R.id.city);
        direction = view.findViewById(R.id.direction);
        try {
            ArrayAdapter directionAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, directions);
            direction.setAdapter(directionAdapter);
        }catch (Exception e){}
        view.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(click) {
                    AlertDialog.Builder build = new AlertDialog.Builder(context);
                    build.setTitle("免責聲明");
                    build.setMessage(desc);
                    build.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                        }
                    });
                    build.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    build.show();
                }
                    try {
                        citynameview.setText(cityname);
                        location = null;
                        location = new ArrayList<>();

                        for (int i = 0; i < results.size(); i++) {

                            if (cityname.equals(results.get(i).locationName.toString())) {

                                location.add(results.get(i));

                            }
                        }
                    } catch (Exception e) {
                    } finally {
                        WeatherAdapter weatherAdapter = new WeatherAdapter(location, context);
                        wlistview.setAdapter(weatherAdapter);
                        click=false;
                    }
                }});

        direction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        citys = new ArrayList();
                        citys.add("臺北市");
                        citys.add("基隆市");
                        citys.add("新竹縣");
                        citys.add("新竹市");
                        citys.add("桃園市");
                        citys.add("新北市");
                        ArrayAdapter cityAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, citys);
                        city.setAdapter(cityAdapter);
                        break;
                    case 1:
                        citys = new ArrayList();
                        citys.add("臺中市");
                        citys.add("彰化縣");
                        citys.add("雲林縣");
                        citys.add("苗栗縣");
                        citys.add("南投縣");
                        ArrayAdapter cityAdapter1 = new ArrayAdapter(context, android.R.layout.simple_list_item_1, citys);
                        city.setAdapter(cityAdapter1);
                        break;
                    case 2:
                        citys = new ArrayList();
                        citys.add("高雄市");
                        citys.add("臺南市");
                        citys.add("嘉義縣");
                        citys.add("嘉義市");
                        citys.add("屏東縣");
                        ArrayAdapter cityAdapter2 = new ArrayAdapter(context, android.R.layout.simple_list_item_1, citys);
                        city.setAdapter(cityAdapter2);
                        break;
                    case 3:
                        citys = new ArrayList();
                        citys.add("臺東縣");
                        citys.add("花蓮縣");
                        citys.add("宜蘭縣");
                        ArrayAdapter cityAdapter3 = new ArrayAdapter(context, android.R.layout.simple_list_item_1, citys);
                        city.setAdapter(cityAdapter3);
                        break;
                    case 4:
                        citys = new ArrayList();
                        citys.add("金門縣");
                        citys.add("澎湖縣");
                        citys.add("連江縣");
                        ArrayAdapter cityAdapter4 = new ArrayAdapter(context, android.R.layout.simple_list_item_1, citys);
                        city.setAdapter(cityAdapter4);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cityname = citys.get(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

}
