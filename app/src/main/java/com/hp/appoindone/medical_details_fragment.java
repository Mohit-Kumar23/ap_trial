package com.hp.appoindone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.StringCharacterIterator;

public class medical_details_fragment extends Fragment {

    MaterialTextView heightv,weightv,bgv,specsv,alcoholv,smokingv,allergyv,surgeryv;
    String email,emailName,height,weight,bg,specs,alcohol,smoking,allergy,surgery;

    public medical_details_fragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medical_details_fragment, container, false);
        heightv = view.findViewById(R.id.tiet_mdf_height);
        weightv = view.findViewById(R.id.tiet_mdf_weight);
        bgv = view.findViewById(R.id.tiet_mdf_bg);
        specsv = view.findViewById(R.id.tiet_mdf_specs);
        alcoholv = view.findViewById(R.id.tiet_mdf_alcohol);
        smokingv = view.findViewById(R.id.tiet_mdf_smoking);
        allergyv = view.findViewById(R.id.tiet_mdf_allergy);
        surgeryv = view.findViewById(R.id.tiet_mdf_surgery);
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        emailName = email.substring(0,email.indexOf('@'));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(emailName);
        Log.i("heetdr",String.valueOf(databaseReference));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    height = String.valueOf(snapshot.child("height").getValue());
                    weight = String.valueOf(snapshot.child("weight").getValue());
                    bg = String.valueOf(snapshot.child("bg").getValue());
                    specs = String.valueOf(snapshot.child("specs").getValue());
                    alcohol = String.valueOf(snapshot.child("alcoholperiod").getValue());
                    smoking = String.valueOf(snapshot.child("smokingperiod").getValue());
                    allergy = String.valueOf(snapshot.child("alergicdetails").getValue());
                    surgery = String.valueOf(snapshot.child("surgerydetails").getValue());
                    heightv.setText(height);
                    weightv.setText(weight);
                    bgv.setText(bg);
                    if(specs.equals("false")){
                        specsv.setText("No");
                    }else{
                        specsv.setText("Yes");
                    }
                    if(alcohol.equals("Period")){
                        alcoholv.setText("No");
                    }
                    else{
                        alcoholv.setText(alcohol);
                    }
                    if(smoking.equals("Period")){
                        smokingv.setText("No");
                    }else{
                        smokingv.setText(smoking);
                    }
                    if(allergy.equals("")){
                        allergyv.setText("No");
                    }else{
                        allergyv.setText(allergy);
                    }
                    if(surgery.equals("")){
                        surgeryv.setText("No");
                    }else{
                        surgeryv.setText(surgery);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }
}