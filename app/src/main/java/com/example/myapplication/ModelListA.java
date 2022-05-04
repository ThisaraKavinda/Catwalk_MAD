package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Display;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModelListA extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference models;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model_list_a);

        database = FirebaseDatabase.getInstance();
        models = database.getReference("Models");

       recyclerView = (RecyclerView) findViewById(R.id.recycler_model);
       recyclerView.setHasFixedSize(true);
       layoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);

      loadMenu();
    }

    private void loadMenu(){
        FirebaseRecyclerAdapter<Models, ModelListViewHolder> adapter = new FirebaseRecyclerAdapter<Models, ModelListViewHolder>(Models.class,R.layout.activity_model_item,ModelListViewHolder.class,models) {
            @Override
            protected void populateViewHolder(ModelListViewHolder modelListViewHolder, Models models, int i) {
                modelListViewHolder.name.setText(models.getName());
                modelListViewHolder.mobile.setText(models.getMobile());
                modelListViewHolder.birthday.setText(models.getBirthday());

            }
        };
        recyclerView.setAdapter(adapter);
    }
}