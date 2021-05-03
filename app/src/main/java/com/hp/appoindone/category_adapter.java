package com.hp.appoindone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.thelumiereguy.neumorphicview.views.NeumorphicCardView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class category_adapter extends FirebaseRecyclerAdapter<categoryClass,category_adapter.viewHolder> {

    public category_adapter(@NonNull FirebaseRecyclerOptions<categoryClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull categoryClass model) {
        holder.specialistTv.setAnimation(AnimationUtils.loadAnimation(holder.specialistTv.getContext(),R.anim.photo_load));
        holder.specialistImg.setAnimation(AnimationUtils.loadAnimation(holder.specialistImg.getContext(),R.anim.photo_load));
        holder.specialistTv.setText(model.getName());
        Glide.with(holder.specialistImg.getContext()).load(model.getUrl()).into(holder.specialistImg);
    }

    @NonNull
    @Override
    public category_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view,parent,false);
        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        NeumorphicCardView neumorphicCardView;
        ImageView specialistImg;
        TextView specialistTv;
        public viewHolder(@NonNull View itemView){
            super(itemView);
            neumorphicCardView = (NeumorphicCardView)itemView.findViewById(R.id.neumorphicCardView);
            specialistImg = (ImageView)itemView.findViewById(R.id.user_photo);
            specialistTv = (TextView)itemView.findViewById(R.id.tv_cv_specialist);
        }
    }
}
