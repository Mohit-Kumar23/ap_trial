package com.hp.appoindone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.thelumiereguy.neumorphicview.views.NeumorphicCardView;

import java.util.Arrays;
import java.util.List;

public class searchViewAdapter extends FirebaseRecyclerAdapter<doctorclass,searchViewAdapter.myviewHolder> {

    private Context context;
    public searchViewAdapter(@NonNull FirebaseRecyclerOptions<doctorclass> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder holder, int position, @NonNull doctorclass model) {
        holder.user_photo.setAnimation(AnimationUtils.loadAnimation(holder.user_photo.getContext(),R.anim.photo_load));
        holder.name.setAnimation(AnimationUtils.loadAnimation(holder.name.getContext(),R.anim.photo_load));
        holder.specialist.setAnimation(AnimationUtils.loadAnimation(holder.specialist.getContext(),R.anim.photo_load));
        holder.area.setAnimation(AnimationUtils.loadAnimation(holder.area.getContext(),R.anim.photo_load));
        holder.ratingBar.setAnimation(AnimationUtils.loadAnimation(holder.ratingBar.getContext(),R.anim.photo_load));
        Glide.with(holder.user_photo.getContext()).load(model.getPurl()).into(holder.user_photo);
        List<String> add = Arrays.asList(model.getAddress().split(","));
        holder.name.setText(model.getName());
        holder.specialist.setText(model.getSpecialist());
        holder.area.setText(add.get(add.size() - 2)+" , "+add.get(add.size() - 1));
        holder.ratingBar.setRating(Float.parseFloat(model.getRating()));
        holder.ncvmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Search)context).onClickCalled(model.getAddress(),model.getContact_no(),model.getHname(),model.getMf(),
                        model.getName(),model.getPurl(),model.getRating(),model.getSat(),model.getSpecialist(),model.getSun(),model.getMon_fri(),
                        model.getSat_sun(),model.getFee());
            }
        });
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_view,parent,false);
        return new searchViewAdapter.myviewHolder(view);
    }


    class myviewHolder extends RecyclerView.ViewHolder{

        NeumorphicCardView neumorphicCardView,ncvmain;
        ImageView user_photo;
        TextView name,specialist,area;
        RatingBar ratingBar;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            ncvmain = itemView.findViewById(R.id.ncvmain);
            neumorphicCardView = itemView.findViewById(R.id.neumorphicCardView);
            user_photo = itemView.findViewById(R.id.user_photo);
            name = itemView.findViewById(R.id.tv_dv_name);
            specialist = itemView.findViewById(R.id.tv_dv_specialist);
            area = itemView.findViewById(R.id.tv_dv_area);
            ratingBar  = itemView.findViewById(R.id.rb_dv_ratingBar);
        }
    }
}
