package com.shaen.weatherclockwidget.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.shaen.weatherclockwidget.R;


public class SplashActivity extends AppCompatActivity {

    TextView textView;
    MyAsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseApp.initializeApp(this);

        task=new MyAsyncTask();
        textView=findViewById(R.id.content);
        task.execute(1);

    }


    public void onPause(){
        super.onPause();
    }
    public void onStop(){super.onStop();finish();}




    public class MyAsyncTask extends AsyncTask<Integer, Integer, String>
    {
        @Override
        protected String doInBackground(Integer... integers) {
            int n = integers[0];
            int i;
            for(i=n;i>=0;i--)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
            return "OK";
        }
        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            Intent it = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(it);
        }
        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }}


    public void onResume(){
        super.onResume();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SplashActivity.this,"naco是大美女",Toast.LENGTH_LONG).show();

            }});

    }
}
