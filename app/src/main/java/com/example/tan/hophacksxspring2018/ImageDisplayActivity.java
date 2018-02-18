package com.example.tan.hophacksxspring2018;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class ImageDisplayActivity extends AppCompatActivity {

    private String path;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        /*Toast.makeText(this, "Yay!", Toast.LENGTH_SHORT).show();*/


        path = getIntent().getExtras().getString("path");
        ImageView imageView = (ImageView)findViewById(R.id.bigimage);

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions. inJustDecodeBounds = false;
        bmOptions. inSampleSize = 4;
        bmOptions. inPurgeable = true ;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

        // Display Image
        imageView.setImageBitmap(bitmap);
    }

    public void sendNew (View view) {
        Intent intent = new Intent(getApplicationContext(), AnalyzingData.class);
        intent.putExtra("path", getIntent().getExtras().getString("path"));
        startActivity(intent);
    }


}
