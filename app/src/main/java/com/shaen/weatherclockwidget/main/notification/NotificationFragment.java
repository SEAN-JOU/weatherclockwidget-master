package com.shaen.weatherclockwidget.main.notification;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.main.notification.adapter.DataAdapter;
import com.shaen.weatherclockwidget.other.ActivityController;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */

public class NotificationFragment extends Fragment {

    ArrayList<DataDetail> pushdatas;
    RecyclerView recyclerView;
    DatabasHelper hhh;
    SwipeRefreshLayout refreshLayout;
    Handler mHandler;
    Activity context;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String value;
    DataAdapter dataAdapter;
    String[] aaa;
    public NotificationFragment(){

    }

    @SuppressLint("ValidFragment")
    public NotificationFragment(Activity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mHandler = new Handler();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Notify");

        return view;
    }


    public void onStart() {
        super.onStart();



    }
    public void onResume(){
        super.onResume();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    value = dataSnapshot.getValue(String.class);
                    pushdatas = new ArrayList<>();
                    Log.d("aaaa", value);
                    if (value.contains(",&,")) {
                        aaa = value.split(",&,");
                    } else {

                    }

                    for (String a : aaa) {
                        NotificationData notificationData = new Gson().fromJson(String.valueOf(a), NotificationData.class);
                        Log.d("aaaa", notificationData.getTitle());
                        pushdatas.add(new DataDetail(notificationData.getTitle(), notificationData.getContent(), notificationData.getVideo(), notificationData.getWeb(), notificationData.getPhoto(), notificationData.getCover()));
                    }


                }
                catch (Exception e){

                }
                finally {
                    recyclerViewset();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //aaaaaaa
      //  recyclerViewset();
    }

    private void recyclerViewset() {

//aaaaaa
//        hhh = new DatabasHelper(ActivityController.mainActivity);
//        pushdatas = hhh.findAll();
//        Collections.reverse(pushdatas);


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        dataAdapter = new DataAdapter(pushdatas, context);
        recyclerView.setAdapter(dataAdapter);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerViewset();
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

    }

}
