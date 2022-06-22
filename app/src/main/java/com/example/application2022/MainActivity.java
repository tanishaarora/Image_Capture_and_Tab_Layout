package com.example.application2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.application2022.activity.DatePickerActivity;
import com.example.application2022.activity.ImageCaptureActivity;
import com.example.application2022.fragment.MultipleTab;

public class MainActivity extends AppCompatActivity {

    Button btn_datePicker;
    Button btn_imageCapture;
    Button btn_tablayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_datePicker = (Button) findViewById(R.id.calendar);
        btn_imageCapture = (Button) findViewById(R.id.image);
        btn_tablayout = (Button) findViewById(R.id.btn_tablayout);

        btn_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatePickerActivity.class);
                startActivity(intent);
            }
        });

        btn_imageCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageCaptureActivity.class);
                startActivity(intent);
            }
        });

        btn_tablayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MultipleTab.class);
                startActivity(intent);
            }
        });
    }
}