package com.shaen.weatherclockwidget.video;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.shaen.weatherclockwidget.R;

import java.util.HashMap;
import java.util.HashSet;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    Button playPauseBtn;
    boolean isPlaying = false;
    Button n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14;
    HashMap<String, String> number = new HashMap<>();
    TextView choosenumber,Timer,txv;
    Randomselect arr;
    String str = null;
    StringBuilder string = new StringBuilder();
    MyAsyncTask task;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        String videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aaa).toString();
        videoView.setVideoPath(videoPath);
        choosenumber = (TextView) findViewById(R.id.choosenumber);
        txv=(TextView)findViewById(R.id.txv);
        Timer = (TextView) findViewById(R.id.Timer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);/*螢幕永久維持開啟*/
        playPauseBtn = findViewById(R.id.btn);

        execute();
        playing();


        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPlaying){
                    Timercul();
                    execute();
                    playing();
                }else{
                    stoping();
                }
            }});


        n1 = (Button) findViewById(R.id.n1);
        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n1.getText());
                determine(aaa);
            }});

        n2 = (Button) findViewById(R.id.n2);
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n2.getText());
                determine(aaa);
            }});
        n3 = (Button) findViewById(R.id.n3);
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n3.getText());
                determine(aaa);
            }
        });
        n4 = (Button) findViewById(R.id.n4);
        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n4.getText());
                determine(aaa);
            }
        });
        n5 = (Button) findViewById(R.id.n5);
        n5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n5.getText());
                determine(aaa);
            }
        });
        n6 = (Button) findViewById(R.id.n6);
        n6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n6.getText());
                determine(aaa);
            }
        });
        n6 = (Button) findViewById(R.id.n6);
        n6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n6.getText());
                determine(aaa);
            }
        });
        n7 = (Button) findViewById(R.id.n7);
        n7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n7.getText());
                determine(aaa);
            }});
        n8 = (Button) findViewById(R.id.n8);
        n8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n8.getText());
                determine(aaa);
            }
        });
        n9 = (Button) findViewById(R.id.n9);
        n9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n9.getText());
                determine(aaa);
            }
        });
        n10 = (Button) findViewById(R.id.n10);
        n10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n10.getText());
                determine(aaa);
            }
        });
        n11 = (Button) findViewById(R.id.n11);
        n11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n11.getText());
                determine(aaa);
            }
        });
        n12 = (Button) findViewById(R.id.n12);
        n12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n12.getText());
                determine(aaa);
            }
        });
        n13 = (Button) findViewById(R.id.n13);
        n13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n13.getText());
                determine(aaa);
            }
        });
        n14 = (Button) findViewById(R.id.n14);
        n14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aaa = String.valueOf(n14.getText());
                determine(aaa);
             /*   ggg=new BlankFragment();
                ggg.show(getSupportFragmentManager(),"B");*/
            }});


    }

    public void determine(String aaa) {

        if (number.size() < 6) {
            if (!number.containsKey(aaa)) {
                number.put(aaa, aaa);
                choosenumber.setText(number.values().toString());
            }
            else if (number.containsKey(aaa)) {
                number.remove(aaa);
                choosenumber.setText(number.values().toString());
            }
        }
        else if (number.containsKey(aaa)) {
            number.remove(aaa);
            choosenumber.setText(number.values().toString());
        }
        else {
            Toast.makeText(VideoActivity.this, "輸入太多了 ", Toast.LENGTH_SHORT).show();
        }

    }

    public void playing(){

        videoView.start();
        playPauseBtn.setText(R.string.pause);
        isPlaying = true;

    }

    public void stoping(){

        videoView.pause();
        playPauseBtn.setText(R.string.play);
        isPlaying  = false;

    }

    public void execute(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j <= 20; j++) {
                    Log.d("123", j + "");
                    if (j == 10) {
                        Timercul();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arr =new Randomselect();
                                arr.Randomselect();
                                txv.setText(arr.sss);


                                if(arr.sss != null) {
                                    DataHelper helper = new DataHelper(VideoActivity.this);
                                    SQLiteDatabase db = helper.getWritableDatabase();
                                    ContentValues cv = new ContentValues();
                                    cv.put("name", arr.sss);
                                    db.insert("phone", null, cv);
                                    Cursor c = db.query("phone", new String[] {"name"}, null, null, null, null, null);

                                    if(c.moveToFirst()){

                                        do{
                                            str = c.getString(0);

                                        }while (c.moveToNext());

                                    }

                                    if(str!= null){

                                        Log.d("11111111111",str);
                                        string.append(str + "\n");
                                        Log.d("1111",string.toString());
                                    }

                                }
                                determine("");
                                pairlottery(arr.integerHashSet);
                            }});}
                    if(j == 20){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txv.setText("");
                                choosenumber.setText("");
                                number.clear();
                            }});}
                    try {Thread.sleep(1000);}
                    catch (InterruptedException ex) {}}}
        }).start();
    }

    public void pairlottery(HashSet<Integer> aaa){


        determine("");
        int hhh=0;
        String yyy=aaa.toString().replace("["," ").replace("]","");
        String[] ary = yyy.split(",");

        for(int i= 0;i<6;i++){
            Log.d("99999999",ary[i].trim());

            if(number.containsValue(ary[i].trim())){
                hhh++;
            }}

        if( hhh >= 3 ){
            Toast.makeText(VideoActivity.this,"恭喜",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(VideoActivity.this,"下次請努力",Toast.LENGTH_SHORT).show();
        }}

    public void Timercul() {
        task = new MyAsyncTask();
        task.execute(20);
    }


    public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... integers) {
            int n = integers[0];
            for (int i = n; i >= 0; i--) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (this.isCancelled() == true) {
                    break;
                }
            }
            return "OK";
        }
    }}

