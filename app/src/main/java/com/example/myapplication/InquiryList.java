package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Adapter.MyAdapter;
import com.example.myapplication.DAO.DAOInquiry;
import com.example.myapplication.Model.Inquiry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class  InquiryList extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Inquiry> inquiryList;
    DatabaseReference database;
    Context context;
    TextView total;

    int modelInquiries = 0;
    int clietInquiries = 0;
    int paymentInquiries = 0;
    int applicationInquires = 0;
    int totalInquires = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_list);
        context = getApplicationContext();

        recyclerView = findViewById(R.id.inquiryViewList);
        database = FirebaseDatabase.getInstance().getReference(Inquiry.class.getSimpleName());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        total = findViewById(R.id.inquiryListTotal);

        inquiryList = new ArrayList<>();
        myAdapter = new MyAdapter(this, inquiryList);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
//            ProgressDialog progressDialog = new ProgressDialog(context);
//            progressDialog.setTitle("Loading...");
//            progressDialog.show();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Inquiry inquiry = dataSnapshot.getValue(Inquiry.class);
                    inquiryList.add(inquiry);
                    total.setText(totalInquires);
                }
                myAdapter.notifyDataSetChanged();
            }
//            progressDialog.dismiss();
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void calculateInquiries (Inquiry inquiry) {
        if(inquiry.getAbout().equals("Models")) {
            modelInquiries += 1;
        } else if(inquiry.getAbout().equals("Clients")) {
            clietInquiries += 1;
        } else if(inquiry.getAbout().equals("Payements")) {
            paymentInquiries += 1;
        } else if(inquiry.getAbout().equals("Application")) {
            applicationInquires += 1;
        }
        totalInquires++;
    }
}