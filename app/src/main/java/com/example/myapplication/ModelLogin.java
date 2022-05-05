package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModelLogin extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_login);

        email = findViewById(R.id.enteremail);
        password = findViewById(R.id.enterpassword);
        login = findViewById(R.id.loginbtn);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(ModelLogin.this,ModelRegister.class);
                startActivity(reg);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Modeldb = database.getReference("Models");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Modeldb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //check if model already available
                        if (snapshot.child(email.getText().toString()).exists()) {

                            //get user information
                            Models model = snapshot.child(email.getText().toString()).getValue(Models.class);
                            if (model.getPassword().equals(password.getText().toString())) {
                                Toast.makeText(ModelLogin.this, "Login Success", Toast.LENGTH_SHORT).show();

                                Intent mhome = new Intent(ModelLogin.this, ArticleList.class);

                                startActivity(mhome);
                                finish();

                            } else {
                                System.out.println("Failed");

                                Toast.makeText(ModelLogin.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(ModelLogin.this, "Please Register", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ModelLogin.this, ModelRegister.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });




    }
}