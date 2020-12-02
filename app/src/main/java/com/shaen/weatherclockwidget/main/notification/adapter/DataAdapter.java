package com.shaen.weatherclockwidget.main.notification.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.main.notification.DataContext;
import com.shaen.weatherclockwidget.main.notification.DataDetail;
import com.shaen.weatherclockwidget.main.notification.DatabasHelper;
import com.shaen.weatherclockwidget.main.Main2Activity;

import java.util.List;


/**
 * Created by jou on 2017/12/25.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private DatabasHelper dbHelper;
    List<DataDetail> datapushs;
    Context context;


    public DataAdapter(Context c){
        dbHelper = new DatabasHelper(c);
    }

    public DataAdapter(List<DataDetail> datapushs, Context context){

        this.datapushs=datapushs;
        this.context=context;
    }

    public void add(DataDetail d){
        SQLiteDatabase db = dbHelper.getWritableDatabase();/*數據庫可以被寫入*/
        ContentValues values = new ContentValues();/*Map形式存儲ContentResolver可以處理的一組值*/
        values.put(DataContext.DataTable.TIT,d.getTitle());
        values.put(DataContext.DataTable.CON,d.getContent());
        values.put(DataContext.DataTable.VID,d.getVideo());
        values.put(DataContext.DataTable.WEB,d.getWeb());
        values.put(DataContext.DataTable.PHO,d.getPhoto());
        values.put(DataContext.DataTable.COV,d.getCover());
        db.insert(DataContext.DataTable.FCM,null,values);
        db.close();
    }

    public void delete(int id){

        SQLiteDatabase db = new DatabasHelper(context).getWritableDatabase();
        db.delete(DataContext.DataTable.FCM,"_ID"+"="+id,null);
        db.close();
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, final int position) {

        final DataDetail datapush =datapushs.get(position);

        if(datapush.getTitle() != String.valueOf("null")) {holder.title.setText(datapush.getTitle());}
        if(datapush.getCover() != String.valueOf("null")){
            try{
            Glide.with(context).load(datapush.getCover()).into(holder.imv);}
            catch (Exception e){}}

        holder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent it = new Intent(context, Main2Activity.class);
                    it.putExtra("hhh", datapush);
                    context.startActivity(it);
                }catch (Exception e){

                }
            }});

        holder.linearlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                delete(position);
                return false;
            }
        });

     }

    @Override
    public int getItemCount() {
        return datapushs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,content;
        public ImageView imv;
        public LinearLayout linearlayout;


        public ViewHolder(View itemView) {
            super(itemView);
            linearlayout=itemView.findViewById(R.id.linearlayout);
            imv = itemView.findViewById(R.id.imv);
            content = itemView.findViewById(R.id.content);
            title = itemView.findViewById(R.id.title);
        }}}
