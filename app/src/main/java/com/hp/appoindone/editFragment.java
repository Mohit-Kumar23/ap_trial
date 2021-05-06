package com.hp.appoindone;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class editFragment extends Fragment {
    String emailName,email,first_names,last_names,dobs,adds,areas,citys,pincodes,heights,weights,allergydiscriptions,alcoholperiods,alcohols,allergys,surgerydiscriptions,smokingperiods,smokings,surgerys,specss,genders,bgs;
    TextInputLayout dobl,adiscription,sdiscription;
    final String[] phone_no = new String[1];
    TextInputEditText first_name;
    TextInputEditText last_name;
    TextInputEditText dob;
    TextInputEditText add;
    TextInputEditText area;
    TextInputEditText city;
    TextInputEditText pincode;
    TextInputEditText height;
    TextInputEditText weight;
    TextInputEditText allergydiscription;
    TextInputEditText surgerydiscription;
    private int mYear,mMonth,mDay;
    Spinner bg,alcoholperiod,smokingperiod,gender;
    MaterialCheckBox alcohol,smoking,allergy,surgery;
    SwitchMaterial specs;
    private String purl=null;
    Button save;

    public editFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        initviews(view);
        return view;
    }

    public void initviews(View view){
        save = view.findViewById(R.id.button_save);
        first_name = view.findViewById(R.id.tiet_pdf_first_name);
        last_name = view.findViewById(R.id.tiet_pdf_last_name);
        dob = view.findViewById(R.id.tiet_pdf_dob);
        dobl = view.findViewById(R.id.til_pdf_dob);
        add = view.findViewById(R.id.tiet_pdf_add);
        area = view.findViewById(R.id.tiet_pdf_area);
        city = view.findViewById(R.id.tiet_pdf_city);
        pincode = view.findViewById(R.id.tiet_pdf_pincode);
        height = view.findViewById(R.id.tiet_mdf_height);
        weight = view.findViewById(R.id.tiet_mdf_weight);
        allergydiscription = view.findViewById(R.id.tiet_mdf_allergydiscription);
        surgerydiscription = view.findViewById(R.id.tiet_mdf_surgerydiscription);
        adiscription = view.findViewById(R.id.til_mdf_allergydiscription);
        sdiscription = view.findViewById(R.id.til_mdf_surgerydiscription);
        gender = view.findViewById(R.id.spinner_mdf_gender);
        bg = view.findViewById(R.id.spinner_mdf_bg);
        alcoholperiod = view.findViewById(R.id.spinner_mdf_drink);
        smokingperiod = view.findViewById(R.id.spinner_mdf_smoking);
        alcohol = view.findViewById(R.id.checkbox_mdf_drink);
        smoking = view.findViewById(R.id.checkbox_mdf_smoking);
        allergy = view.findViewById(R.id.checkbox_mdf_allergy);
        surgery = view.findViewById(R.id.checkbox_mdf_post_surgery);
        specs = view.findViewById(R.id.switch_mdf_spectacle);
        alcoholperiod.setEnabled(false);
        smokingperiod.setEnabled(false);
        adiscription.setEnabled(false);
        sdiscription.setEnabled(false);
        dob.setEnabled(false);
        dob.setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    dob.setText(+i2+"/"+(i1+1)+"/"+i);
                }
            },mYear,mMonth,mDay);
            datePickerDialog.show();
        });
        dobl.setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();
            mYear = cal.get(Calendar.YEAR);
            mMonth = cal.get(Calendar.MONTH);
            mDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    dob.setText(+i2+"/"+(i1+1)+"/"+i);
                }
            },mYear,mMonth,mDay);
            datePickerDialog.show();
        });
        alcohol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(alcohol.isChecked()){
                    alcoholperiod.setEnabled(true);
                }else{
                    alcoholperiod.setEnabled(false);
                }
            }
        });
        smoking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(smoking.isChecked()){
                    smokingperiod.setEnabled(true);
                }else{
                    smokingperiod.setEnabled(false);
                }
            }
        });
        allergy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(allergy.isChecked()){
                    adiscription.setEnabled(true);
                }else{
                    adiscription.setEnabled(false);
                }
            }
        });
        surgery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(surgery.isChecked()){
                    sdiscription.setEnabled(true);
                }else{
                    sdiscription.setEnabled(false);
                }
            }
        });
        save.setOnClickListener(v -> {
            update();
            ((MainActivity)getContext()).onClickSave();
        });
    }

    public void update(){
        first_names = String.valueOf(first_name.getText());
        last_names = String.valueOf(last_name.getText());
        dobs = String.valueOf(dob.getText());
        adds = String.valueOf(add.getText());
        areas = String.valueOf(area.getText());
        citys = String.valueOf(city.getText());
        pincodes = String.valueOf(pincode.getText());
        heights = String.valueOf(height.getText());
        weights = String.valueOf(weight.getText());
        allergydiscriptions = String.valueOf(allergydiscription.getText());
        surgerydiscriptions = String.valueOf(surgerydiscription.getText());
        genders = gender.getSelectedItem().toString();
        bgs = bg.getSelectedItem().toString();
        alcoholperiods = alcoholperiod.getSelectedItem().toString();
        smokingperiods = smokingperiod.getSelectedItem().toString();
        alcohols = String.valueOf(alcohol.isChecked());
        smokings = String.valueOf(smoking.isChecked());
        allergys = String.valueOf(allergy.isChecked());
        surgerys = String.valueOf(surgery.isChecked());
        specss = String.valueOf(specs.isChecked());
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        emailName = email.substring(0,email.indexOf('@'));
        Log.i("heetf",String.valueOf(email));
        Log.i("heetf",String.valueOf(emailName));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user");
        Query user = databaseReference.orderByChild("email").equalTo(email);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    phone_no[0] = String.valueOf(snapshot.child(emailName).child("phone_no").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Log.i("heetf",String.valueOf(phone_no[0]));
        Log.i("heetf",String.valueOf(email));
        Log.i("heetf",String.valueOf(first_names));
        Log.i("heetf",String.valueOf(last_names));
        Log.i("heetf",String.valueOf(adds));
        Log.i("heetf",String.valueOf(alcohols));
        Log.i("heetf",String.valueOf(alcoholperiods));
        Log.i("heetf",String.valueOf(allergys));
        Log.i("heetf",String.valueOf(allergydiscriptions));
        Log.i("heetf",String.valueOf(areas));
        Log.i("heetf",String.valueOf(bgs));
        Log.i("heetf",String.valueOf(citys));
        Log.i("heetf",String.valueOf(dobs));
        Log.i("heetf",String.valueOf(genders));
        Log.i("heetf",String.valueOf(heights));
        Log.i("heetf",String.valueOf(surgerys));
        Log.i("heetf",String.valueOf(smokings));
        Log.i("heetf",String.valueOf(smokingperiods));
        Log.i("heetf",String.valueOf(specss));
        Log.i("heetf",String.valueOf(weights));
        Log.i("heetf",String.valueOf(purl));
        Log.i("heetf",String.valueOf(surgerydiscriptions));
        Log.i("heetf",String.valueOf(pincodes));
        FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootnode.getReference("user");
        userclass adduser = new userclass(phone_no[0],email,first_names,last_names,adds,alcohols,alcoholperiods,allergys,allergydiscriptions,areas,bgs,citys,dobs,genders,heights,surgerys,smokings,smokingperiods,specss,weights,purl,surgerydiscriptions,pincodes);
        reference.child(emailName).setValue(adduser);

    }
}