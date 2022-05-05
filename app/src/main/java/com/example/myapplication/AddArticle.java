package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddArticle extends AppCompatActivity {
    EditText aTopic;
    EditText aDescription;
    Button addArticle,clearBtn;

    DatabaseReference modelDbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        aTopic = findViewById(R.id.articletopic);
        aDescription = findViewById(R.id.articledescription);
        addArticle = findViewById(R.id.addarticlebtn1);
        clearBtn = findViewById(R.id.cleararticlebtn1);


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

        final ProgressDialog mDialog = new ProgressDialog(AddArticle.this);
        mDialog.setMessage("Please wait....");
        mDialog.show();

       String  str_topic=aTopic.getText().toString();
       String  str_description = aDescription.getText().toString() ;


        modelDbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //check if already available
                if(snapshot.child(str_topic).exists()){
                    mDialog.dismiss();
                    Toast.makeText(AddArticle.this, "Topic Already Available...", Toast.LENGTH_SHORT).show();

                }
                else{
                    mDialog.dismiss();
                    Articles article  = new Articles(str_topic,str_description);
                    modelDbref.child(str_topic).setValue(article);
                    Toast.makeText(AddArticle.this, "Article Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddArticle.this, AddArticle.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}