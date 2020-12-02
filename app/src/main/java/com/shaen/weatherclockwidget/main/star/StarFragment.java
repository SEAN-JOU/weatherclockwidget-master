package com.shaen.weatherclockwidget.main.star;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shaen.weatherclockwidget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarFragment extends Fragment {


    Activity context;
    TextView txv,textView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String sss = "奈子貓奔跑鐘APP星座運勢，只作參考途，"+"\n"+"奈子貓奔跑鐘APP致力確保提供的資料，"+"\n"+"準確無誤APP內容如有任何錯漏，"+"\n"+"奈子貓奔跑鐘APP概不負責";

    public StarFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public StarFragment(Activity context) {
        this.context=context;
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star, container, false);
        txv=view.findViewById(R.id.txv);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Star");
        textView =view.findViewById(R.id.textView);
        textView.setText(sss);
        return view;
    }
    public void onResume(){
        super.onResume();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                txv.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }});

    }
}
