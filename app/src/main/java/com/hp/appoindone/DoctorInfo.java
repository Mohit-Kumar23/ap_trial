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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DoctorInfo extends AppCompatActivity {

    public Button btn_sel_time,btn_sel_date,proceed;
    public TextView tv_date,tv_time;
    private int mYear,mMonth,mDay,mHour,mMinutes;
    RatingBar ratingBar;
    TextView vaddress, vcontact_no, vhname, vmf, vname, vsat, vspecialist, vsun,vcrowd_level;
    ImageView user_photo;
    String address,contact_no,hname,mf,name,purl,rating,sat,specialist,sun,mon_fri,sat_sun,fee;
    ImageView backBtn;
    NeumorphicCardView cardView;
    CardView cardView2;
    doctorclass doctorObj;
    ArcSeekBar seekBar;
    String daySelect;
    boolean proceedBoolean=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        initViews();
        doctorObj = new doctorclass();

/*        address = getIntent().getExtras().getString("address");
        contact_no = getIntent().getExtras().getString("contact_no");
        hname = getIntent().getExtras().getString("hname");
        mf = getIntent().getExtras().getString("mf");
        name = getIntent().getExtras().getString("name");
        purl = getIntent().getExtras().getString("purl");
        rating = getIntent().getExtras().getString("rating");
        sat = getIntent().getExtras().getString("sat");
        specialist = getIntent().getExtras().getString("specialist");
        sun = getIntent().getExtras().getString("sun");
        mon_fri = getIntent().getExtras().getString("mon_fri");
        sat_sun = getIntent().getExtras().getString("sat_sun");
        fee = getIntent().getExtras().getString("fee");

        Glide.with(user_photo.getContext()).load(purl).into(user_photo);
        vaddress.setText(address);
        vcontact_no.setText(contact_no);
        vhname.setText(hname);
        vmf.setText(mf);
        //vname.setText("Name : "+name);
        ratingBar.setRating(Float.parseFloat(rating));
        vsat.setText(sat);
        vspecialist.setText(specialist);
        vsun.setText(sun);*/

        seekBar.setEnabled(false);
       /* seekBar.setProgress(80);
        seekBar.setProgressColor(getResources().getColor(R.color.green,getTheme()));*/
        mon_fri="100";
        sat_sun="23";

        btn_sel_time.setOnClickListener(this::onClick);
        btn_sel_date.setOnClickListener(this::onClick);

        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(DoctorInfo.this,MainActivity.class));
            finish();});

        proceed.setOnClickListener(view -> {
            if ((tv_date.getText().toString().equals("Appointment Date : ")) || (tv_time.getText().toString().equals("Appointment Time : ")))
            {
                Toast.makeText(this, "Please select Data and Time", Toast.LENGTH_LONG).show();
            }
            else{
                if(proceedBoolean){
                    startActivity(new Intent(DoctorInfo.this,confirmAppoint.class));
                }
                else {
                        Toast.makeText(this,"Appointment Not Available at Selected Slot",Toast.LENGTH_LONG).show();
                    }
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
        //vname=(TextView)findViewById(R.id.tv_di_nameTxt);
        vsat=(TextView)findViewById(R.id.textView9);
        vspecialist=(TextView)findViewById(R.id.tv_di_specialistTxt);
        vsun=(TextView)findViewById(R.id.tv_sun_time);
        proceed=(Button)findViewById(R.id.proceed);
        vcrowd_level=(TextView)findViewById(R.id.crowdLevel);
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
                    SimpleDateFormat sdf = new SimpleDateFormat("EE");
                    Date date = new Date(i,i1,i2-1);
                    daySelect = sdf.format(date);
                    Log.i("day",daySelect);
                    computingProgress();
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

    public void computingProgress()
    {
        if(daySelect.toLowerCase().equals("sat") || daySelect.toLowerCase().equals("sun"))
        {
            seekBar.setProgress(Integer.parseInt(sat_sun));
            proceedBoolean = settingSeekBar();
        }
        else
        {
            seekBar.setProgress(Integer.parseInt(mon_fri));
            proceedBoolean = settingSeekBar();
        }

    }

    public boolean settingSeekBar()
    {
        int seekBarValue = seekBar.getProgress();
        if(seekBarValue>=0 && seekBarValue<=20)
        {
            seekBar.setProgressColor(getResources().getColor(R.color.green,getTheme()));
            vcrowd_level.setText("VERY LOW");
            return true;
        }

        else if(seekBarValue>20 && seekBarValue<=40)
        {
            seekBar.setProgressColor(getResources().getColor(R.color.alarmingGreen,getTheme()));
            vcrowd_level.setText("LOW");
            return true;
        }

        else if(seekBarValue>40 && seekBarValue<=60)
        {
            seekBar.setProgressColor(getResources().getColor(R.color.yellow,getTheme()));
            vcrowd_level.setText("MEDIUM");
            return true;
        }

        else if(seekBarValue>60 && seekBarValue<=80)
        {
            seekBar.setProgressColor(getResources().getColor(R.color.orange,getTheme()));
            vcrowd_level.setText("HIGH");
            return true;
        }

        else if(seekBarValue>80 && seekBarValue<100)
        {
            seekBar.setProgressColor(getResources().getColor(R.color.red,getTheme()));
            vcrowd_level.setText("VERY HIGH");
            return true;
        }

        else if(seekBarValue==100)
        {
            seekBar.setProgressColor(getResources().getColor(R.color.red,getTheme()));
            vcrowd_level.setText("NOT AVAILABLE AT ALL");
            return false;
        }
        return false;
    }
}