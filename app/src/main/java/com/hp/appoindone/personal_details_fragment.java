package com.hp.appoindone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class personal_details_fragment extends Fragment {

    String email,emailName,first_name,last_name,add,area,city,phone_no,dob,pincode,gender;
    MaterialTextView efirst_name, elast_name, eemail, ephone_no, edob, egender, eadd, earea, ecity, epincode;
    public personal_details_fragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_details_fragment, container, false);
        efirst_name = view.findViewById(R.id.tiet_pdf_first_name);
        elast_name = view.findViewById(R.id.tiet_pdf_last_name);
        eemail = view.findViewById(R.id.tiet_pdf_email);
        ephone_no = view.findViewById(R.id.tiet_pdf_phoneno);
        edob = view.findViewById(R.id.tiet_pdf_dob);
        egender = view.findViewById(R.id.tiet_pdf_gender);
        eadd = view.findViewById(R.id.tiet_pdf_add);
        earea = view.findViewById(R.id.tiet_pdf_area);
        ecity = view.findViewById(R.id.tiet_pdf_city);
        epincode = view.findViewById(R.id.tiet_pdf_pincode);
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        emailName = email.substring(0,email.indexOf('@'));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(emailName);
        Log.i("heetdr",String.valueOf(databaseReference));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    first_name = String.valueOf(snapshot.child("first_name").getValue());
                    last_name = String.valueOf(snapshot.child("last_name").getValue());
                    dob = String.valueOf(snapshot.child("dob").getValue());
                    add = String.valueOf(snapshot.child("add").getValue());
                    area = String.valueOf(snapshot.child("area").getValue());
                    city = String.valueOf(snapshot.child("city").getValue());
                    pincode = String.valueOf(snapshot.child("pincode").getValue());
                    gender = String.valueOf(snapshot.child("gender").getValue());
                    phone_no = String.valueOf(snapshot.child("phone_no").getValue());
                    email = String.valueOf(snapshot.child("email").getValue());
                    efirst_name.setText(first_name);
                    elast_name.setText(last_name);
                    edob.setText(dob);
                    eadd.setText(add);
                    earea.setText(area);
                    ecity.setText(city);
                    epincode.setText(pincode);
                    egender.setText(gender);
                    ephone_no.setText(phone_no);
                    eemail.setText(email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }
}