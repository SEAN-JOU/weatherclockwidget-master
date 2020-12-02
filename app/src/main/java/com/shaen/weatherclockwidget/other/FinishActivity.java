package com.shaen.weatherclockwidget.other;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shaen.weatherclockwidget.R;



public class FinishActivity extends AppCompatActivity {

    TextView textView;
    MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);


        textView=findViewById(R.id.content);

    }



    public void onStart(){
        super.onStart();

        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(1);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(FinishActivity.this,"sean是大帥哥",Toast.LENGTH_LONG).show();

            }});
    }


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

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            FinishActivity.this.finish();

        }


        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }}
}
