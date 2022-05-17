package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Session.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientLogin extends AppCompatActivity {

    EditText password,mobile;
    Button login;
    TextView register;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        mobile = findViewById(R.id.centeremail);
        password = findViewById(R.id.centerpassword);
        login = findViewById(R.id.cloginbtn);
        register = findViewById(R.id.cregister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(ClientLogin.this,ClientRegister.class);
                startActivity(reg);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Clientdb = database.getReference("Clients");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mobile.getText().toString())){
                    mobile.setError("Mobile Number is compulsory");
                    return;
                }
                if(TextUtils.isEmpty(password.getText().toString())){
                    password.setError("Password is compulsory");
                    return;
                }

                Clientdb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //check if model already available
                        if (snapshot.child(mobile.getText().toString()).exists()) {
                            //get user information
                            Clients client = snapshot.child(mobile.getText().toString()).getValue(Clients.class);
                           if(client.getStatus().equals("Active")) {
                               if (client.getPassword().equals(password.getText().toString())) {


                                   Toast.makeText(ClientLogin.this, "Login Success", Toast.LENGTH_SHORT).show();

                                   session = new SessionManager(getApplicationContext());
                                   session.createLoginSession(client.getName(), client.getEmail(), client.getMobile(), client.getLocation(), client.getCompany(), client.getPassword(), client.getImageurl(), "client");

                                   Intent mhome = new Intent(ClientLogin.this, ClientHome.class);


                                   startActivity(mhome);
                                   finish();

                               } else {
                                   System.out.println("Failed");

                                   Toast.makeText(ClientLogin.this, "Login failed", Toast.LENGTH_SHORT).show();
                               }
                           }
                            else{
                               Toast.makeText(ClientLogin.this, "Your Request Still Pending Or Rejected", Toast.LENGTH_SHORT).show();


                            }
                        }
                        else {
                            Toast.makeText(ClientLogin.this, "Please Register", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ClientLogin.this, ClientRegister.class);
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