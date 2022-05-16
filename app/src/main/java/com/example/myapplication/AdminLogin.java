package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Model.Admin;
import com.example.myapplication.Session.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    EditText mobile, password;
    Button login;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mobile = findViewById(R.id.adminLoginMobile);
        password = findViewById(R.id.adminLoginPassword);
        login = findViewById(R.id.adminLoginSubmitbtn);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Clientdb = database.getReference("Admins");

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
                            Admin admin = snapshot.child(mobile.getText().toString()).getValue(Admin.class);
//                            Log.i("info", admin.getPassword());
//                            Log.i("info", password.getText().toString());
                            if (admin.getPassword().equals(password.getText().toString())) {

                                Toast.makeText(AdminLogin.this, "Login Success", Toast.LENGTH_SHORT).show();

                                session = new SessionManager(getApplicationContext());
                                session.createLoginSession(admin.getName(),"",admin.getMobile(),"","","",admin.getImageUrl(),"admin");
                                Intent mhome = new Intent(AdminLogin.this, AdminHome.class);
                                startActivity(mhome);
                                finish();

                            } else {
                                System.out.println("Failed");
                                Toast.makeText(AdminLogin.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(AdminLogin.this, "Admin detailsaren't available in the database", Toast.LENGTH_SHORT).show();
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