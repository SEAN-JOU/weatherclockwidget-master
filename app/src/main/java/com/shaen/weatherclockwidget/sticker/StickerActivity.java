package com.shaen.weatherclockwidget.sticker;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.util.FileUtil;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.StickerView;
import java.io.File;


public class StickerActivity extends AppCompatActivity implements View.OnClickListener {


    StickerView stickerView;
    static final int gallery_request = 101;
    ImageView imvTakePhoto;
    ImageButton run, expect, happy, plus, love, sad, confidence, use2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("貼圖製作");


        stickerView = (StickerView) findViewById(R.id.sticker_view);
        imvTakePhoto = (ImageView) findViewById(R.id.imvTakePhoto);
        run = (ImageButton) findViewById(R.id.run);
        expect = (ImageButton) findViewById(R.id.expect);
        happy = (ImageButton) findViewById(R.id.happy);
        plus = (ImageButton) findViewById(R.id.plus);
        love = (ImageButton) findViewById(R.id.love);
        sad = (ImageButton) findViewById(R.id.sad);
        confidence = (ImageButton) findViewById(R.id.confidence);
        use2 = (ImageButton) findViewById(R.id.use2);

        run.setOnClickListener(this);
        expect.setOnClickListener(this);
        happy.setOnClickListener(this);
        plus.setOnClickListener(this);
        love.setOnClickListener(this);
        sad.setOnClickListener(this);
        confidence.setOnClickListener(this);
        use2.setOnClickListener(this);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add) {
            Intent galleryview = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryview, gallery_request);
        } else if (item.getItemId() == R.id.save) {

            File file = FileUtil.getNewFile(StickerActivity.this, "");

            if (file != null) {
                stickerView.save(file);
                Toast.makeText(StickerActivity.this, "save", Toast.LENGTH_SHORT).show();

            } else {

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == gallery_request && resultCode == RESULT_OK) {

            String uri = data.getData().toString();
            imvTakePhoto.setImageURI(Uri.parse(uri));

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.run:
                setStickerView(R.drawable.run);
                break;
            case R.id.expect:
                setStickerView(R.drawable.expect);
                break;
            case R.id.happy:
                setStickerView(R.drawable.happy);
                break;
            case R.id.plus:
                setStickerView(R.drawable.plus);
                break;
            case R.id.love:
                setStickerView(R.drawable.love);
                break;
            case R.id.sad:
                setStickerView(R.drawable.sad);
                break;
            case R.id.confidence:
                setStickerView(R.drawable.confidence);
                break;
            case R.id.use2:
                setStickerView(R.drawable.use2);
                break;
        }
    }


    public void setStickerView(int p) {
        Drawable drawable = getResources().getDrawable(p,null);
        stickerView.addSticker(new DrawableSticker(drawable));
    }
}
