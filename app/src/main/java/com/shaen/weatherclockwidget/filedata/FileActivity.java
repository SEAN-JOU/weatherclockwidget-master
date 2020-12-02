package com.shaen.weatherclockwidget.filedata;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.filedata.adapter.ListAdapter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileActivity extends AppCompatActivity implements BlankFragment.Qwe,AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener {


    FloatingActionButton fab;
    static final String TAG="MainActivity";
    ListView listView;
    List<Coffee> mcoffeelist = new ArrayList<>();
    private  static final  String Filename = "coffeelist.ser";
    public int indexcoffee;

    public List<Coffee> getMcoffeelist()
    {return mcoffeelist;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlankFragment ddd=new BlankFragment(100);
                ddd.show(getSupportFragmentManager(),"BlankFragment");}});

        initListView();
    }

    private  void initListView(){
        listView =(ListView)findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(new ListAdapter(this));
        listView.setOnItemClickListener(this);

    }



    @Override
    public void add(Coffee coffee) {

        FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.fab);
        Snackbar.make(fab,""+coffee,Snackbar.LENGTH_LONG).setAction("Action",null).show();
        Log.d(TAG,""+coffee);
        mcoffeelist.add(coffee);
        ListAdapter listAdapter =(ListAdapter)listView.getAdapter();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void update(Coffee coffee) {
        mcoffeelist.set(indexcoffee,coffee);
        ((ListAdapter)listView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void remove() {
        mcoffeelist.remove(indexcoffee);
        ((ListAdapter)listView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG,"onItemSelected, i ="+i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.fab);
        Snackbar.make(fab,""+i,Snackbar.LENGTH_LONG).setAction("Action",null).show();
        BlankFragment delete=new BlankFragment(101);
        delete.show(getSupportFragmentManager(),"BlankFragment");
        indexcoffee = i;


    }
    private void save(){
        ObjectOutputStream oos =null;
        try{try{

            FileOutputStream fos = openFileOutput(Filename, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mcoffeelist);
            Log.d(TAG,"存檔成功");
        }finally {
            if(oos != null){
                oos.close();
            }
        }
        }catch (IOException e){

            Log.d(TAG,e.toString());
        }}



    private void restore(){
        FileInputStream fis = null;
        try {try{
            fis = openFileInput(Filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            mcoffeelist = (List) ois.readObject();
            Log.d(TAG,"讀檔案成功");
        }finally {
            if( fis !=null){
                fis.close();
            }
        }
        }catch (IOException  | ClassNotFoundException e){

            Log.d(TAG,e.toString());

        }
    }
    protected void onStart(){
        super.onStart();

        restore();
        ((ListAdapter)listView.getAdapter()).notifyDataSetChanged();
    }
    protected  void onStop(){

        super.onStop();
        save();
    }}
