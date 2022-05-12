package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddArticle extends AppCompatActivity {
    EditText aTopic;
    EditText aDescription;
    Button addArticle,clearBtn,back;

    DatabaseReference modelDbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        aTopic = findViewById(R.id.articletopic);
        aDescription = findViewById(R.id.articledescription);
        addArticle = findViewById(R.id.addarticlebtn1);
        clearBtn = findViewById(R.id.cleararticlebtn1);
        back = findViewById(R.id.addarticlebackbtn1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddArticle.this, AdminHome.class));

            }
        });
        modelDbref = FirebaseDatabase.getInstance().getReference().child("Articles");

        addArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sendArticle();

            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearArticle();
            }
        });



    }

    private void clearArticle(){

        aTopic.setText("");
        aDescription.setText("");
    }

    private void sendArticle(){

        String str_topic=aTopic.getText().toString();
        String str_Description=aDescription.getText().toString();

        Articles articles  = new Articles(str_topic,str_Description);



       FirebaseDatabase.getInstance().getReference().child("Articles").child(str_topic).setValue(articles)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       aTopic.setText("");
                       aDescription.setText("");
                       Toast.makeText(AddArticle.this, "Added Success", Toast.LENGTH_SHORT).show();
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {

                       Toast.makeText(AddArticle.this, "Added Failed", Toast.LENGTH_SHORT).show();


                   }
               });



    }
}