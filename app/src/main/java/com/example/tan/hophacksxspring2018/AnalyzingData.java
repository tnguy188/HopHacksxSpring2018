package com.example.tan.hophacksxspring2018;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class AnalyzingData extends AppCompatActivity {

    private static String TAG = "AnalyzingData";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzing_data);
        Intent intent = getIntent();
    }

    public void analyze(View view) {
        TextView result = findViewById(R.id.results);
        Context context = getApplicationContext();
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();

        if (!textRecognizer.isOperational()) {
            Log.w(TAG, "Text Recognizer is not yet available");
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
            return;
        }
        ImageView iv = findViewById(R.id.imageView);
        try {
            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
            if (bitmap != null) {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> textBlock = textRecognizer.detect(frame);
                String blocks ="";
                String lines = "";
                String words = "";
                for (int index = 0; index < textBlock.size(); index++) {
                    // Extract scanned materials
                    TextBlock tBlock = textBlock.valueAt(index);
                    blocks = blocks + tBlock.getValue() + "\n" + "\n";
                    for (Text line : tBlock.getComponents()) {
                        lines = lines + line.getValue() + "\n";
                        for (Text element : line.getComponents()) {
                            words = words + element.getValue() + ", ";
                        }
                    }
                }
                if (textBlock.size() == 0) {
                    result.setText("Scan failed: Found nothing");
                } else {
                    result.setText(result.getText() + "Blocks: " + "\n");
                    result.setText(result.getText() + blocks + "\n");
                    result.setText(result.getText() + "---------" + "\n");
                    result.setText(result.getText() + "Lines: " + "\n");
                    result.setText(result.getText() + lines + "\n");
                    result.setText(result.getText() + "---------" + "\n");
                    result.setText(result.getText() + "Words: " + "\n");
                    result.setText(result.getText() + words + "\n");
                    result.setText(result.getText() + "---------" + "\n");

                }
            } else {
                result.setText("Could not set up the detector");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Fail to load image", Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.toString());
        }

    }
}
