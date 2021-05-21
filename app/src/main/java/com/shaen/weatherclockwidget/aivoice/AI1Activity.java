package com.shaen.weatherclockwidget.aivoice;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shaen.weatherclockwidget.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AI1Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextView textInput, second;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int MY_DATA_CHECK_CODE = 150;
    private static final int PERMISSON_REQUESTCODE = 0;
    private static final int REQ_TTS_STATUS_CHECK = 1;
    MyAsyncTask task;
    private boolean isNeedCheck = true;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TextToSpeech mTts;
    HashMap<String, String> hashMap = new HashMap<>();
    DatabaseReference reference;
    String imei = null;
    protected String[] needPermissions = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai1);

        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }

        mTts = new TextToSpeech(this, this);
        textInput = (TextView) findViewById(R.id.txtSpeechInput);
        second = (TextView) findViewById(R.id.second);


    }

//        hashMap.put("帥","summer");
//        hashMap.put("美","新垣結依");


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.CHINESE);
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
            } else {
            }
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.CHINESE);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.speech_prompt);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    R.string.speech_not_supported,
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void onResume() {
        super.onResume();
        hashMap.clear();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();

        SharedPreferences pref = getSharedPreferences("ONLY", MODE_PRIVATE);
        pref.edit().putString("IMEI", imei).commit();


        reference = FirebaseDatabase.getInstance().getReference().child(getSharedPreferences("ONLY", MODE_PRIVATE)
                .getString("IMEI", ""));

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        task = new MyAsyncTask();
        task.execute(3);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textInput.setText(String.valueOf(result.get(0)).replaceAll("\\s", ""));

                    if (String.valueOf(result.get(0)).replaceAll("\\s", "").contains("問題") && String.valueOf(result.get(0)).replaceAll("\\s", "").contains("答案")) {
                        String createQA = String.valueOf(result.get(0)).replaceAll("\\s", "").replace("問題", "");
                        String[] qa = createQA.split("答案");

                        if (!qa[0].isEmpty() && !qa[1].isEmpty()) {
                            DatabaseReference myRef = database.getReference().child(getSharedPreferences("ONLY", MODE_PRIVATE)
                                    .getString("IMEI", ""));
                            Map<String, Object> map = new HashMap<String, Object>();
                            String temp_key = myRef.push().getKey();
                            myRef.updateChildren(map);

                            DatabaseReference child_ref = myRef.child(temp_key);
                            Map<String, Object> map2 = new HashMap<>();
                            map2.put("key", qa[0]);
                            map2.put("value", qa[1]);


                            child_ref.updateChildren(map2).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                            Toast.makeText(AI1Activity.this, "設置完成", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            Toast.makeText(AI1Activity.this, "設置失敗", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    if (result.get(0) != "" || result.get(0).trim() != "") {
                        mTts.speak(Answer.getIntence().sentence(String.valueOf(result.get(0)), hashMap), TextToSpeech.QUEUE_ADD, null);
                    }
                }
            }
            break;
            case MY_DATA_CHECK_CODE: {
                if (resultCode != TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    Intent installIntent = new Intent();
                    installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    Log.v("Main", "need intallation");
                    startActivity(installIntent);
                }

            }
            case REQ_TTS_STATUS_CHECK: {
                switch (resultCode) {
                    case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:

                        break;
                    case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
                        //文件已经损坏
                    case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
                        //缺少发音文件
                    case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
                        //数据文件丢失

                        //从新更新TTS数据文件
                        Intent mUpdateData = new Intent();
                        mUpdateData.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                        startActivity(mUpdateData);

                        break;

                    case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
                        //检测失败应该重新检测
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... integers) {

            int n = integers[0];
            int i;
            for (i = n; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int finalI = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        second.setText(String.valueOf(finalI));
                        textInput.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AI1Activity.this.finish();
                                Intent it = new Intent(AI1Activity.this, AI2Activity.class);
                                startActivity(it);
                            }
                        });
                    }
                });
            }
            return "OK";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            promptSpeechInput();
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(mTts!=null){
//            mTts.shutdown();
//        }
//        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if(mTts!=null){
//            mTts.shutdown();
//        }
//        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
    }


    public void append_chat(DataSnapshot ss) {
        String question, anwser;
        Iterator i = ss.getChildren().iterator();
        while (i.hasNext()) {
            question = ((DataSnapshot) i.next()).getValue().toString();
            anwser = ((DataSnapshot) i.next()).getValue().toString();
            hashMap.put(question, anwser);
        }

    }

    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }
}
