package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ClientSingleRegisterRequest extends AppCompatActivity {
    String name,location,mobile,email,image,company;
    TextView cname,clocation,cmobile,cemail,ccompany;
    ImageView cimage;
    Button accept,reject;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_single_register_request);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        company = intent.getStringExtra("company");
        mobile = intent.getStringExtra("mobile");
        email = intent.getStringExtra("email");
        image = intent.getStringExtra("image");
        location = intent.getStringExtra("location");

        cname = findViewById(R.id.client_single_name);
        clocation = findViewById(R.id.client_single_location);
        cmobile = findViewById(R.id.client_single_mobile);
        cemail = findViewById(R.id.client_single_email);
        cimage = findViewById(R.id.client_single_image);
        ccompany = findViewById(R.id.client_single_company);
        accept = findViewById(R.id.clientacceptbtn2);
        reject = findViewById(R.id.clientrejectbtn1);

        cname.setText(name);
        clocation.setText(location);
        cmobile.setText(mobile);
        cemail.setText(email);
        ccompany.setText(company);

        Picasso.get().load(image).into(cimage);


        databaseReference = FirebaseDatabase.getInstance().getReference("Clients");

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(mobile).child("status").setValue("Active");
                Toast.makeText(ClientSingleRegisterRequest.this, "Client Accepted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ClientSingleRegisterRequest.this,ClientRegisterRequests.class);
                startActivity(intent);
                finish();



            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(mobile).child("status").setValue("Rejected");
                Toast.makeText(ClientSingleRegisterRequest.this, "Client Rejected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ClientSingleRegisterRequest.this,ClientRegisterRequests.class);
                startActivity(intent);
                finish();



            }
        });



    }
}