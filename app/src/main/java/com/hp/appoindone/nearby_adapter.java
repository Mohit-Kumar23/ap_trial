package com.hp.appoindone;

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

public class nearby_adapter extends FirebaseRecyclerAdapter<doctorclass,nearby_adapter.viewHolder> {
    public nearby_adapter(@NonNull FirebaseRecyclerOptions<doctorclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull nearby_adapter.viewHolder holder, int position, @NonNull doctorclass model) {
        holder.neumorphicCardView.setAnimation(AnimationUtils.loadAnimation(holder.neumorphicCardView.getContext(),R.anim.photo_load));
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
    }

    @NonNull
    @Override
    public nearby_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_view,parent,false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        ImageView user_photo;
        TextView name,specialist,area;
        RatingBar ratingbar;
        NeumorphicCardView neumorphicCardView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            user_photo = (ImageView)itemView.findViewById(R.id.specialist_photo);
            name = (TextView)itemView.findViewById(R.id.tv_dv_name);
            specialist = (TextView)itemView.findViewById(R.id.tv_dv_specialist);
            area = (TextView)itemView.findViewById(R.id.tv_dv_area);
            ratingbar = (RatingBar)itemView.findViewById(R.id.rb_dv_ratingBar);
            neumorphicCardView = (NeumorphicCardView) itemView.findViewById(R.id.neumorphicCardView);
        }
    }
}
