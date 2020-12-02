package com.shaen.weatherclockwidget.main.information;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.AttributeSet;

import com.shaen.weatherclockwidget.R;

/**
 * Created by NB004 on 2018/5/25.
 */

public class SuperButton  extends LinearLayout {

    ImageView imv;
    TextView txv;
    LayoutInflater inflater;
    Context context;

    public SuperButton(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context=context;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.super_button, this);
        imv=findViewById(R.id.imv);
        txv=findViewById(R.id.content);

    }

    public Bitmap getImageView(){

        return drawableToBitmap(imv.getDrawable());
    }

    public String getText(){
        return txv.getText().toString();
    }

    public void setImage(Bitmap bitmap){

        imv.setImageBitmap(bitmap);
    }
    public void setText(String s){

        txv.setText(s);
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
