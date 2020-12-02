package com.sean.utils;

import android.content.Context;

import android.os.Handler;
import android.os.Message;
import com.google.gson.Gson;
import com.sean.utils.okhttp.SystemCode;
import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by NB004 on 2018/5/6.
 */

public class OkhttpUtils {



    private static final String TYPE_NAME_PREFIX = "class ";
    public static OkhttpUtils okhttpUtils;
    public static OkHttpClient getokhttp;
    public static OkHttpClient postokhttp;



    public OkhttpUtils(){}


    public static OkhttpUtils getInstance(){
        okhttpUtils = null;
        if(okhttpUtils == null){
            return new OkhttpUtils();
        }
        return okhttpUtils;
    }


    public interface Builder {
        FormBody.Builder formbody=new FormBody.Builder();
    }

    public static void getmethod(final Handler handler, String url)throws Exception{


        getokhttp = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url).build();

        Call call = getokhttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Message m = new Message();
                m.what = SystemCode.OkhttpFailure;
                handler.sendMessage(m);

            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                String string= response.body().string();
                Message m = new Message();
                m.what = SystemCode.OkhttpSuccess;
                m.obj =string;
                handler.sendMessage(m);
            }});
    }

    public static void postmethod(RequestBody formBody, final Handler handler, final String url)throws Exception{

        postokhttp = new OkHttpClient.Builder().build();

        Request request= new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = postokhttp.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message m = new Message();
                m.what = SystemCode.OkhttpFailure;
                handler.sendMessage(m);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message m = new Message();
                m.what = SystemCode.OkhttpSuccess;
                m.obj=response.body().string();
                handler.sendMessage(m);
            }
        });
    }

    public static Object interpretJson(String s, Class<?> object){

        return new Gson().fromJson(s,object);}

    public static  Class<?> getClass(Type type)
            throws ClassNotFoundException {
        String className = getClassName(type);
        if (className==null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }
    public static String getClassName(Type type) {
        if (type==null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        return className;
    }


}
