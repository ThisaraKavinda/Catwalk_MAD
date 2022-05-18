package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class ClientAdHistory extends AppCompatActivity {
    RecyclerView recview;
    Adapter_model_post adapter;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_ad_history);


        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<class_model_request> options =
                new FirebaseRecyclerOptions.Builder<class_model_request>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Model_Request").orderByChild("clientId")
                                .equalTo("user"), class_model_request.class)
                        .build();

        adapter=new Adapter_model_post(options);
        recview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void addNewPost(View view) {
        Intent intent = new Intent(ClientAdHistory.this, ClientAddModelRequest.class);
        startActivity(intent);
    }
}