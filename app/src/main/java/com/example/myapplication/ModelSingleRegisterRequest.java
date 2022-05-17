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

public class ModelSingleRegisterRequest extends AppCompatActivity {
     String name,age,mobile,email,image;
     TextView mname,mage,mmobile,memail;
     ImageView mimage;
     Button accept,reject;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_single_register_request);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");
        mobile = intent.getStringExtra("mobile");
        email = intent.getStringExtra("email");
        image = intent.getStringExtra("image");

        mname = findViewById(R.id.model_single_name);
        mage = findViewById(R.id.model_single_age);
        mmobile = findViewById(R.id.model_single_mobile);
        memail = findViewById(R.id.model_single_email);
        mimage = findViewById(R.id.model_single_image);
        accept = findViewById(R.id.modelacceptbtn2);
        reject = findViewById(R.id.modelrejectbtn1);

        mname.setText(name);
        mage.setText(age);
        mmobile.setText(mobile);
        memail.setText(email);

        Picasso.get().load(image).into(mimage);


        databaseReference = FirebaseDatabase.getInstance().getReference("Models");

       accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

             databaseReference.child(mobile).child("status").setValue("Active");
               Toast.makeText(ModelSingleRegisterRequest.this, "Model Accepted", Toast.LENGTH_SHORT).show();
             Intent intent = new Intent(ModelSingleRegisterRequest.this,ModelRegisterRequests.class);
             startActivity(intent);
             finish();



           }
       });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(mobile).child("status").setValue("Rejected");
                Toast.makeText(ModelSingleRegisterRequest.this, "Model Rejected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ModelSingleRegisterRequest.this,ModelRegisterRequests.class);
                startActivity(intent);
                finish();



            }
        });


    }
}