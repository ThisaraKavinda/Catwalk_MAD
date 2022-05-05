package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ModelRegister extends AppCompatActivity {

    EditText name,password,email,mobile,birthday;
    Button reg_btn;
    DatabaseReference modelDbref;
    

    String str_name,str_password,str_email,str_mobile,str_birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_register);

        name = findViewById(R.id.mname);
        password = findViewById(R.id.mpassword);
        email = findViewById(R.id.memail);
        mobile = findViewById(R.id.mmobile);
        birthday = findViewById(R.id.mdate);
        reg_btn = findViewById(R.id.mregister);

         modelDbref = FirebaseDatabase.getInstance().getReference().child("Models");

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendDataToDb();
            }
        });
    }



    private void sendDataToDb(){
        final ProgressDialog mDialog = new ProgressDialog(ModelRegister.this);
        mDialog.setMessage("Please wait....");
        mDialog.show();

        str_name = name.getText().toString();
        str_password = password.getText().toString();
        str_email = email.getText().toString();
        str_mobile = mobile.getText().toString();
        str_birthday = birthday.getText().toString();

        modelDbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //check if already available
                if(snapshot.child(str_email).exists()){
                    mDialog.dismiss();
                    Toast.makeText(ModelRegister.this, "Already Available...", Toast.LENGTH_SHORT).show();

                }
                else{
                    mDialog.dismiss();
                    Models model  = new Models(str_name,str_password,str_email,str_mobile,str_birthday);
                    modelDbref.child(str_email).setValue(model);
                    Toast.makeText(ModelRegister.this, "Sign up Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//      Models model  = new Models(str_name,str_password,str_email,str_mobile,str_birthday);
//      modelDbref.push().setValue(model);
//
//        Toast.makeText(ModelRegister.this, "Model Registered", Toast.LENGTH_SHORT).show();



    }

    public void checkMail(View v){

    }
}