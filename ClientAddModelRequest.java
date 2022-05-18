package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClientAddModelRequest extends AppCompatActivity {

    EditText title,height,payment,time, description;
    Button submit;
    Spinner type , gender;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_model_request);

        title=(EditText)findViewById(R.id.clientAddModelRequestTitleInput);
        height=(EditText)findViewById(R.id.clientAddModelRequestHeightInput);
        payment=(EditText)findViewById(R.id.clientAddModelRequestPaymentInput);
        time=(EditText)findViewById(R.id.clientAddModelRequestTimeInput);
        description=(EditText)findViewById(R.id.clientAddModelRequestDesInput);
        type=(Spinner)findViewById(R.id.clientAddModelRequestTypeInput);
        gender=(Spinner)findViewById(R.id.genderGet);


        submit=(Button)findViewById(R.id.clientAddModelRequestSubmitbtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });
    }

    private void processinsert()
    {


        if(title.getText() == null || title.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter Title", Toast.LENGTH_LONG).show();
        }else if(height.getText() == null || height.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter Height", Toast.LENGTH_LONG).show();
        }else if(payment.getText() == null || payment.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter Payment", Toast.LENGTH_LONG).show();
        }else if(time.getText() == null || time.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter Time", Toast.LENGTH_LONG).show();
        }else if(description.getText() == null || description.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG).show();
        }else if(type.getSelectedItem() == null || type.getSelectedItem().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Select Type", Toast.LENGTH_LONG).show();
        }
        else {


            int random_int = (int) Math.floor(Math.random() * (9999 - 1000 + 1) + 1000);
            Map<String, Object> map = new HashMap<>();
            map.put("id", String.valueOf(random_int));
            map.put("title", title.getText().toString());
            map.put("gender", gender.getSelectedItem().toString());
            map.put("height", height.getText().toString());
            map.put("payment", payment.getText().toString());
            map.put("time", time.getText().toString());
            map.put("description", description.getText().toString());
            map.put("type", type.getSelectedItem().toString());
            map.put("clientId", "user");
            FirebaseDatabase.getInstance().getReference().child("Model_Request").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            title.setText("");
                            height.setText("");
                            payment.setText("");
                            time.setText("");
                            description.setText("");
                            Toast.makeText(getApplicationContext(), "Model Adding Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ClientAddModelRequest.this, ClientAdHistory.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }


    public void back(View view) {
        Intent intent = new Intent(ClientAddModelRequest.this, ClientAdHistory.class);
        startActivity(intent);
    }
}