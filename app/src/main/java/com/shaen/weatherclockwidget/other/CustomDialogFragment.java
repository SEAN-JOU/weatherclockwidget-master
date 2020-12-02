package com.shaen.weatherclockwidget.other;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.other.FinishActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDialogFragment extends DialogFragment {


    Activity context;


    public CustomDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public CustomDialogFragment(Activity activity) {
        this.context = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LayoutInflater inflater = getActivity().getLayoutInflater();//取得片段當前關聯的活動 取得緩存的LayoutInflater用於片段的視圖。
        final View view = inflater.inflate(R.layout.fragment_customdialog/*取得R.layout.fragment_customdialog.xml*/, null);

        AlertDialog.Builder bbb = new AlertDialog.Builder(getActivity()/*取得片段當前關聯的活動*/);
        view.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
                Intent it = new Intent(context, FinishActivity.class);
                startActivity(it);

            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        bbb.setView(view);/*放進畫面*/
//        bbb.setPositiveButton("離開", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                Intent it = new Intent(context,FinishActivity.class);
//                startActivity(it);
//
//
//            }});
//        bbb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//
//
//            }});
        return bbb.create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)/*創建畫面的時候*/ {

        return inflater.inflate(R.layout.fragment_customdialog, container, false);
    }

    public void onDestroy() {
        super.onDestroy();

    }

}
