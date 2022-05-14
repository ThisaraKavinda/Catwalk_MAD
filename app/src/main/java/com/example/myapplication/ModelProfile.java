package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.Session.SessionManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ModelProfile extends AppCompatActivity {
    SessionManager session;
    EditText mname,memail,mbirth,mgender,mpassword;
    TextView mmobile;
    ImageView propic,propicup;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_profile);

        session = new SessionManager(getApplicationContext());

        String name = session.getModelDetails().get("mname");
        String email  = session.getModelDetails().get("memail");
        String mobile  = session.getModelDetails().get("mmobile");
        String gender  = session.getModelDetails().get("mgender");
        String birth  = session.getModelDetails().get("mbirth");
        String password  = session.getModelDetails().get("mpassword");
        String image  = session.getModelDetails().get("mimage");

        propic = findViewById(R.id.modelpropic1);
        propicup = findViewById(R.id.modelpropicup);
        mname = findViewById(R.id.modelProfileNameInput);
        memail = findViewById(R.id.modelProfileEmailInput);
        mgender = findViewById(R.id.modelProfileGenderInput);
        mbirth = findViewById(R.id.modelProfileDobInput);
        mpassword = findViewById(R.id.modelProfilePasswordInput);
        mmobile = findViewById(R.id.modelProfileMobileInput);
        back = findViewById(R.id.modelprofilebackbtn1);

        mname.setText(name);
        memail.setText(email);
        mgender.setText(gender);
        mbirth.setText(birth);
        mpassword.setText(password);
        mmobile.setText(mobile);

        Picasso.get().load(image).into(propic);
        Picasso.get().load(image).into(propicup);

        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModelProfile.this,ModelProfile.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModelProfile.this,ModelHome.class);
                startActivity(intent);
                finish();
            }
        });


    }
}