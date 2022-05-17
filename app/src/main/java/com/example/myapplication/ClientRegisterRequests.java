package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ClientRegisterRequests extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Clients> list;
    Button back;
    String status="Pending";


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference databaseReference;
    ClientRequestAdapter adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ClientRegisterRequests.this, AdminHome.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register_requests);

        recyclerView = findViewById(R.id.client_request_recycler);
        databaseReference = FirebaseDatabase.getInstance().getReference("Clients");

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClientRequestAdapter(this, list);
        recyclerView.setAdapter(adapter);
//        back = findViewById(R.id.modelsregisterrqstbackbtn1);
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ModelRegisterRequests.this, AdminHome.class));
//                finish();
//            }
//        });

        databaseReference.orderByChild("status").equalTo("Pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Clients clients = dataSnapshot.getValue(Clients.class);

                    list.add(clients);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }







}