package com.hp.appoindone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class category extends AppCompatActivity {
    String category;
    RecyclerView recyclerView;
    categoryViewAdapter categoryviewAdapter;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        imageView = findViewById(R.id.iv_btn_c_back);
        imageView.setOnClickListener(v -> {
            startActivity(new Intent(com.hp.appoindone.category.this,MainActivity.class));
        });
        category = getIntent().getStringExtra("category");
        recyclerView = findViewById(R.id.category_recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        FirebaseRecyclerOptions<doctorclass> options1 =
                new FirebaseRecyclerOptions.Builder<doctorclass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("doctor").orderByChild("specialist").equalTo(category), doctorclass.class)
                        .build();
        categoryviewAdapter = new categoryViewAdapter(options1);
        recyclerView.setAdapter(categoryviewAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryviewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        categoryviewAdapter.stopListening();
    }
}