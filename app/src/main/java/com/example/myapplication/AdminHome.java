package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminHome extends AppCompatActivity {
    LinearLayout addarticlebtn,articlelistbtn,modelrqstbtn, inquiriesbtn, modelList, clientList,clientrqsbtn,logout;
    TextView modelcount,clientcount;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminHome.this, AdminHome.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addarticlebtn = findViewById(R.id.add_article_tab);
        articlelistbtn = findViewById(R.id.list_article_tab);
        modelrqstbtn = findViewById(R.id.model_request_list_tab);
        clientrqsbtn = findViewById(R.id.client_request_list_tab);
        inquiriesbtn = findViewById(R.id.list_inquiries_tab);
        modelList = findViewById(R.id.model_list_tab);
        clientList = findViewById(R.id.client_list_tab);
        modelcount = findViewById(R.id.modelcount);
        clientcount = findViewById(R.id.clientcount);
        logout = findViewById(R.id.admin_logout_tab);

        getmodelcount();
        getclientcount();

        addarticlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addarticle = new Intent(AdminHome.this,AddArticle.class);
                startActivity(addarticle);
            }
        });

        articlelistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listarticle = new Intent(AdminHome.this,ArticleList.class);
                startActivity(listarticle);
            }
        });

        modelrqstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modelrequest = new Intent(AdminHome.this,ModelRegisterRequests.class);
                startActivity(modelrequest);
            }
        });

        inquiriesbtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), InquiryList.class));
        });

        modelList.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ModelList.class));
        });

        clientList.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ClientsList.class));
        });

        clientrqsbtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ClientRegisterRequests.class));
        });
        logout.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });






    }


    private  void getmodelcount(){

        DatabaseReference mdatabaseref = FirebaseDatabase.getInstance().getReference();
        mdatabaseref.child("Models").orderByChild("status").equalTo("Pending").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = (int)snapshot.getChildrenCount();
                String mcount = String.valueOf(count);
                modelcount.setText("("+mcount+")");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private  void getclientcount(){

        DatabaseReference mdatabaseref = FirebaseDatabase.getInstance().getReference();
        mdatabaseref.child("Clients").orderByChild("status").equalTo("Pending").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = (int)snapshot.getChildrenCount();
                String ccount = String.valueOf(count);
                clientcount.setText("("+ccount+")");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}