package com.hp.appoindone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marcinmoskala.arcseekbar.ArcSeekBar;
import com.thelumiereguy.neumorphicview.views.NeumorphicCardView;

import java.util.Calendar;

public class DoctorInfo extends AppCompatActivity {

    public Button btn_sel_time,btn_sel_date,proceed;
    public TextView tv_date,tv_time;
    private int mYear,mMonth,mDay,mHour,mMinutes;
    RatingBar ratingBar;
    TextView vaddress, vcontact_no, vhname, vmf, vname, vsat, vspecialist, vsun;
    ImageView user_photo;
    String address,contact_no,hname,mf,name,purl,rating,sat,specialist,sun;
    ImageView backBtn;
    NeumorphicCardView cardView;
    CardView cardView2;

    ArcSeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        initViews();
        address = getIntent().getExtras().getString("address");
        contact_no = getIntent().getExtras().getString("contact_no");
        hname = getIntent().getExtras().getString("hname");
        mf = getIntent().getExtras().getString("mf");
        name = getIntent().getExtras().getString("name");
        purl = getIntent().getExtras().getString("purl");
        rating = getIntent().getExtras().getString("rating");
        sat = getIntent().getExtras().getString("sat");
        specialist = getIntent().getExtras().getString("specialist");
        sun = getIntent().getExtras().getString("sun");
        Glide.with(user_photo.getContext()).load(purl).into(user_photo);
        vaddress.setText(address);
        vcontact_no.setText(contact_no);
        vhname.setText(hname);
        vmf.setText(mf);
        vname.setText("Name : "+name);
        ratingBar.setRating(Float.parseFloat(rating));
        vsat.setText(sat);
        vspecialist.setText(specialist);
        vsun.setText(sun);
        seekBar.setEnabled(false);
        seekBar.setProgress(80);
        seekBar.setProgressColor(getResources().getColor(R.color.green,getTheme()));

        btn_sel_time.setOnClickListener(this::onClick);
        btn_sel_date.setOnClickListener(this::onClick);

        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(DoctorInfo.this,MainActivity.class));
            finish();});
        proceed.setOnClickListener(view -> {
            if((tv_date.getText()!="Appointment Date : ") && (tv_time.getText()!="Appointment Time : ")){
                Log.i("heet",String.valueOf(tv_date.getText()));
                Log.i("heet",String.valueOf(name));
                startActivity(new Intent(DoctorInfo.this,userinfo.class));
                finish();

            }else{
                Toast.makeText(this,"Please select Data and Time",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initViews()
    {

        seekBar = (ArcSeekBar)findViewById(R.id.seekBar);
        cardView = (NeumorphicCardView)findViewById(R.id.neumorphicCardView);
        cardView2 = (CardView)findViewById(R.id.cardView2);
        btn_sel_date=(Button)findViewById(R.id.btn_di_selectDate);
        btn_sel_time=(Button)findViewById(R.id.btn_di_selectTime);
        tv_date=(TextView)findViewById(R.id.tv_di_appointDate);
        tv_time=(TextView)findViewById(R.id.tv_di_appointTime);
        backBtn=(ImageView)findViewById(R.id.iv_btn_di_back);
        user_photo=(ImageView)findViewById(R.id.user_photo);
        ratingBar=(RatingBar)findViewById(R.id.rtBar_di);
        vaddress=(TextView)findViewById(R.id.tv_di_locationTxt);
        vcontact_no=(TextView)findViewById(R.id.tv_di_contactTxt);
        vhname=(TextView)findViewById(R.id.tv_di_hospitalTxt);
        vmf=(TextView)findViewById(R.id.tv_mon_fri_time);
        vname=(TextView)findViewById(R.id.tv_di_nameTxt);
        vsat=(TextView)findViewById(R.id.textView9);
        vspecialist=(TextView)findViewById(R.id.tv_di_specialistTxt);
        vsun=(TextView)findViewById(R.id.tv_sun_time);
        proceed=(Button)findViewById(R.id.proceed);
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