package com.hp.appoindone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.marcinmoskala.arcseekbar.ArcSeekBar;

import java.util.Calendar;

public class DoctorInfo extends AppCompatActivity {

    public Button btn_sel_time,btn_sel_date;
    public TextView tv_date,tv_time;
    private int mYear,mMonth,mDay,mHour,mMinutes;
    ImageView backBtn;

    ArcSeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        initViews();
        seekBar = (ArcSeekBar)findViewById(R.id.seekBar);
        seekBar.setEnabled(false);
        seekBar.setProgress(80);
        seekBar.setProgressColor(getResources().getColor(R.color.green,getTheme()));

        btn_sel_time.setOnClickListener(this::onClick);
        btn_sel_date.setOnClickListener(this::onClick);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorInfo.this,MainActivity.class));
            }
        });
    }

    public void initViews()
    {
        btn_sel_date=(Button)findViewById(R.id.btn_di_selectDate);
        btn_sel_time=(Button)findViewById(R.id.btn_di_selectTime);
        tv_date=(TextView)findViewById(R.id.tv_di_appointDate);
        tv_time=(TextView)findViewById(R.id.tv_di_appointTime);
        backBtn=(ImageView)findViewById(R.id.iv_btn_di_back);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view)
    {   final Calendar cal = Calendar.getInstance();
        if(view==btn_sel_date)
        {
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    tv_date.setText("Appointment Date : "+i2+"/"+(i1+1)+"/"+i);
                }
            },mYear,mMonth,mDay);

            datePickerDialog.show();

        }

        if(view == btn_sel_time)
        {
            mHour = cal.get(Calendar.HOUR_OF_DAY);
            mMinutes = cal.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    tv_time.setText("Appointment Time : "+i+":"+i1);
                }
            },mHour,mMinutes,true);

            timePickerDialog.show();
        }

    }
}