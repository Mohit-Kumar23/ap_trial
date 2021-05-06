package com.hp.appoindone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.thelumiereguy.neumorphicview.views.NeumorphicCardView;
import com.thelumiereguy.neumorphicview.views.NeumorphicConstraintLayout;

import java.util.Arrays;
import java.util.List;

public class mostrecent_adapter extends FirebaseRecyclerAdapter<doctorclass,mostrecent_adapter.viewHolder> {
    public mostrecent_adapter(@NonNull FirebaseRecyclerOptions<doctorclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull doctorclass model) {
        holder.user_photo.setAnimation(AnimationUtils.loadAnimation(holder.user_photo.getContext(),R.anim.photo_load));
        holder.name.setAnimation(AnimationUtils.loadAnimation(holder.name.getContext(),R.anim.photo_load));
        holder.specialist.setAnimation(AnimationUtils.loadAnimation(holder.specialist.getContext(),R.anim.photo_load));
        holder.area.setAnimation(AnimationUtils.loadAnimation(holder.area.getContext(),R.anim.photo_load));
        holder.ratingbar.setAnimation(AnimationUtils.loadAnimation(holder.ratingbar.getContext(),R.anim.photo_load));
        Glide.with(holder.user_photo.getContext()).load(model.getPurl()).into(holder.user_photo);
        List<String> add = Arrays.asList(model.getAddress().split(","));
        holder.name.setText(model.getName());
        holder.specialist.setText(model.getSpecialist());
        holder.area.setText(add.get(add.size() - 2)+" , "+add.get(add.size() - 1));
        holder.ratingbar.setRating(Float.parseFloat(model.getRating()));
        holder.ncvmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) v.getContext()).onClickCalled(model.getAddress(),model.getContact_no(),model.getHname(),model.getMf(),
                        model.getName(),model.getPurl(),model.getRating(),model.getSat(),model.getSpecialist(),model.getSun(),model.getMon_fri(),
                        model.getSat_sun(),model.getFee());
            }
        });
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_view,parent,false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        ImageView user_photo;
        TextView name,specialist,area;
        RatingBar ratingbar;
        CardView cardView;
        NeumorphicCardView neumorphicCardView,ncvmain;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            user_photo = (ImageView)itemView.findViewById(R.id.user_photo);
            name = (TextView)itemView.findViewById(R.id.tv_dv_name);
            specialist = (TextView)itemView.findViewById(R.id.tv_dv_specialist);
            area = (TextView)itemView.findViewById(R.id.tv_dv_area);
            ratingbar = (RatingBar)itemView.findViewById(R.id.rb_dv_ratingBar);
            ncvmain = (NeumorphicCardView) itemView.findViewById(R.id.ncvmain);
            neumorphicCardView = (NeumorphicCardView) itemView.findViewById(R.id.neumorphicCardView);
            cardView = (CardView) itemView.findViewById(R.id.cardView2);
        }
    }
}
