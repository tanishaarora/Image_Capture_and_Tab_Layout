package com.example.application2022.activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application2022.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerActivity extends AppCompatActivity {

    TextView txt_calendar;
    Button btn_calendar;
    DatePickerDialog datePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datepicker);

        final Calendar calendar = Calendar.getInstance();
  //      calendar.setTime(new Date());
        final int[] date = {calendar.get(Calendar.DAY_OF_MONTH)};
        final int[] month = {calendar.get(Calendar.MONTH)};
        final int[] year = {calendar.get(Calendar.YEAR)};


        txt_calendar = (TextView) findViewById(R.id.txt_calendar);
        btn_calendar = (Button) findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePicker = new DatePickerDialog(DatePickerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int y, int m, int d) {

                        date[0] = d;
                        month[0] = m;
                        year[0] = y;

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.DATE, date[0]);
                        calendar1.set(Calendar.MONTH, month[0]);
                        calendar1.set(Calendar.YEAR, year[0]);

                        Date strDate = calendar1.getTime();
                        SimpleDateFormat format = new SimpleDateFormat(" MM / dd / yyyy ");
                        String selectedDate = format.format(strDate);
                        txt_calendar.setText(selectedDate);
                        //txt_calendar.setText((month[0] + 1) + " / " + date[0] + " / " + year[0]);
                    }
                }, year[0], month[0], date[0]);

                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePicker.show();

            }

        });
    }
}


    /*    Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.MONTH, month);


        txt_calendar.setText(strDate);*/

            //     String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
            //   txt_calendar.setText(selectedDate);
