package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Session.SessionManager;
import com.squareup.picasso.Picasso;

public class ClientProfile extends AppCompatActivity {
    SessionManager session;
    EditText cname,cemail,clocation,ccompany,cpassword;
    TextView cmobile;
    ImageView propic,propicup;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        session = new SessionManager(getApplicationContext());

        String name = session.getUserDetails().get("name");
        String email  = session.getUserDetails().get("email");
        String mobile  = session.getUserDetails().get("mobile");
        String location  = session.getUserDetails().get("location");
        String company  = session.getUserDetails().get("company");
        String password  = session.getUserDetails().get("password");
        String image  = session.getUserDetails().get("image");
//        Log.i("info", name + " " + email+ " " + " " + image);

        cname = findViewById(R.id.clientProfileNameInput);
        cemail = findViewById(R.id.clientProfileEmailInput);
        clocation = findViewById(R.id.clientProfileLocInput);
        ccompany = findViewById(R.id.clientProfileComInput);
        cpassword = findViewById(R.id.clientProfilePasswordInput);
        cmobile = findViewById(R.id.clientProfileMobileInput);
        propic = findViewById(R.id.clientpropic2);
        propicup = findViewById(R.id.clientpropic);
        back = findViewById(R.id.clientprofilebackbtn1);

        cname.setText(name);
        cemail.setText(email);
        clocation.setText(location);
        ccompany.setText(company);
        cpassword.setText(password);
        cmobile.setText(mobile);

        Picasso.get().load(image).into(propic);
        Picasso.get().load(image).into(propicup);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientProfile.this,ClientHome.class);
                startActivity(intent);
                finish();
            }
        });

        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientProfile.this,ClientProfile.class);
                startActivity(intent);
                finish();
            }
        });

    }
}