package com.shaen.weatherclockwidget.aivoice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shaen.weatherclockwidget.R;

import java.util.HashMap;
import java.util.Map;

public class AI2Activity extends AppCompatActivity {


    EditText question, anwser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button set;
    String temp_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai2);

        question = (EditText) findViewById(R.id.question);
        anwser = (EditText) findViewById(R.id.anwser);
        set = (Button) findViewById(R.id.set);


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (question.getText().toString().isEmpty()) {
                    Toast.makeText(AI2Activity.this, "問題不得為空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (anwser.getText().toString().isEmpty()) {
                    Toast.makeText(AI2Activity.this, "回答不得為空", Toast.LENGTH_SHORT).show();
                    return;
                }


                DatabaseReference myRef = database.getReference().child(getSharedPreferences("ONLY", MODE_PRIVATE)
                        .getString("IMEI", ""));
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = myRef.push().getKey();
                myRef.updateChildren(map);

                DatabaseReference child_ref = myRef.child(temp_key);
                Map<String, Object> map2 = new HashMap<>();
                map2.put("key", question.getText().toString());
                map2.put("value",anwser.getText().toString());
                child_ref.updateChildren(map2).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                Toast.makeText(AI2Activity.this, "設置完成", Toast.LENGTH_SHORT).show();
                AI2Activity.this.finish();


                startActivity(new Intent(AI2Activity.this,AI1Activity.class));
            }
        }); }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AI2Activity.this.finish();
        Intent it = new Intent(AI2Activity.this, AI1Activity.class);
        startActivity(it);
    }
}
