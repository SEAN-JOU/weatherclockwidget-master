package com.shaen.weatherclockwidget.filedata;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.filedata.adapter.SpinnerAdapter;



public class BlankFragment extends DialogFragment {

    Spinner sss;
    View view;
    EditText ett;
    AlertDialog yyy;
    Qwe qqq;
    SpinnerAdapter adapter;
    private int flag1 = 100;


    public interface Qwe {
        void add(Coffee coffee);

        void update(Coffee coffee);

        void remove();
    }


    public BlankFragment() {
    }

    @SuppressLint("ValidFragment")


    public BlankFragment(int flag1) {
        this.flag1 = flag1;
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        inithandler();
        initview();
        initspinner();
        if (flag1 == 100) {
            initdialog();
        } else {
            initdialog1();
        }
        return yyy;
    }


    void inithandler() {
        try {
            qqq = (Qwe) getActivity();
        } catch (FException f) {
        }
    }

    void initview() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_blank, null);
    }

    void initspinner() {
        sss = (Spinner) view.findViewById(R.id.sss);
        Activity aaa = getActivity();
        adapter = new SpinnerAdapter(aaa);
        sss.setAdapter(adapter);
        sss.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) aaa);
        int position = 0;
        sss.setSelection(position);
    }

    void initdialog() {

        AlertDialog.Builder bbb = new AlertDialog.Builder(getActivity());
        bbb.setView(view);
        bbb.setPositiveButton("new", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Coffee coffee = getCoffee();
                qqq.add(coffee);
            }
        });
        bbb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        yyy = bbb.create();
    }

    void initdialog1() {

        AlertDialog.Builder bbb = new AlertDialog.Builder(getActivity());
        bbb.setView(view);
        bbb.setPositiveButton("edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Coffee coffee = getCoffee();
                qqq.update(coffee);
            }
        });
        bbb.setNeutralButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                qqq.remove();

            }
        });
        bbb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }});
        yyy = bbb.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    private Coffee getCoffee() {
        int position = sss.getSelectedItemPosition();
        String title = adapter.getCoffeetitle().getString(position);
        ett = (EditText) view.findViewById(R.id.ett);
        String price = null;

        try {
            price =ett.getText().toString();
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "  ", Toast.LENGTH_LONG).show();
        }

        int resource_id = adapter.resourceid()[position];
        return new Coffee(title, price, resource_id);

    }
}





