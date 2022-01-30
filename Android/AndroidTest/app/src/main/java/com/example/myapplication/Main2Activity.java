package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.UiAutomation;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myView = findViewById(R.id.constraintLayout);
        myView.setOnTouchListener(new OnSwipeTouchListener(Main2Activity.this) {
            public void onSwipeRight () {
                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
