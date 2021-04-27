package com.hp.appoindone;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

public class userinfo extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton rbmale,rbfemale,rbother;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initviews();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_ui_male :  rbmale.setTextColor(Color.parseColor("#303030"));
                                            rbfemale.setTextColor(Color.parseColor("#50303030"));
                                            rbother.setTextColor(Color.parseColor("#50303030"));
                                            break;
                    case R.id.rb_ui_female : rbmale.setTextColor(Color.parseColor("#50303030"));
                                             rbfemale.setTextColor(Color.parseColor("#303030"));
                                             rbother.setTextColor(Color.parseColor("#50303030"));
                                             break;
                    case R.id.rb_ui_other : rbmale.setTextColor(Color.parseColor("#50303030"));
                                            rbfemale.setTextColor(Color.parseColor("#50303030"));
                                            rbother.setTextColor(Color.parseColor("#303030"));
                                            break;
                }
            }
        });
    }

    private void initviews() {
        radioGroup = findViewById(R.id.radiogroup);
        rbmale = findViewById(R.id.rb_ui_male);
        rbfemale = findViewById(R.id.rb_ui_female);
        rbother = findViewById(R.id.rb_ui_other);
    }
}
