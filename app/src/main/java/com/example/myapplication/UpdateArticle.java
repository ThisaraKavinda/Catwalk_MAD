package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class UpdateArticle extends AppCompatActivity {
 String aname,adescription;
   EditText adescriptiontext;
    TextView anametext;
    Button update;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference articleref = database.getReference("Articles");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_article);
        Intent articleIntent = getIntent();
         aname = articleIntent.getStringExtra("aname");
         adescription = articleIntent.getStringExtra("adesription");
         update =findViewById(R.id.updatearticlebtn1);


        anametext = findViewById(R.id.articletopicupdate);
        adescriptiontext = findViewById(R.id.articledescriptionupdate);

        anametext.setText(aname);
        adescriptiontext.setText(adescription);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Articles article = new Articles(aname,adescriptiontext.getText().toString());
                articleref.child(aname).setValue(article)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(UpdateArticle.this, "Article Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdateArticle.this,ArticleList.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(UpdateArticle.this, "Article Updated Failed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdateArticle.this,ArticleList.class);
                                startActivity(intent);


                            }
                        });

            }
        });

    }
}