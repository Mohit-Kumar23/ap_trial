package com.hp.appoindone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.raw.navigation_to_destinaiton,
            R.raw.live_status,
            R.raw.emergency_appointment,
            R.raw.probable_appointment,
    };

    public String[] slide_text ={
            "NAVIGATION TO\nDESTINATION",
            "LIVE\nSTATUS",
            "EMERGENCY\nAPPOINTMENT",
            "PROBABLE\nAPPOINTMENT"
    };


    @Override
    public int getCount() {
        return slide_text.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, final int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slidelayout,container,false);
        final TextView slide_textView= view.findViewById(R.id.slide_text);
        final LottieAnimationView slide_lottie = view.findViewById(R.id.slide_images);
        slide_lottie.setAnimation(slide_images[position]);
        slide_textView.setText(slide_text[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
