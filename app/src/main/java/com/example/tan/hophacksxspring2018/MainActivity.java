package com.example.tan.hophacksxspring2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendNew (View view) {
        Intent intent = new Intent(this, AnalyzingData.class);
        startActivity(intent);
    }
}
