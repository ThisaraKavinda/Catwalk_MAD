package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ClientHome extends AppCompatActivity {
    LinearLayout newrequest,adresponse,hiredmodels,adslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        newrequest = findViewById(R.id.new_request_tab);
        adresponse = findViewById(R.id.ads_response_tab);
        hiredmodels = findViewById(R.id.hired_models_tab);
        adslist =findViewById(R.id.ads_list_tab);


        newrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newr = new Intent(ClientHome.this,ClientAddModelRequest.class);
                startActivity(newr);
            }
        });

        adresponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent res = new Intent(ClientHome.this,ModelHistory.class);
                startActivity(res);
            }
        });

        hiredmodels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hire = new Intent(ClientHome.this,ClientViewHistory.class);
                startActivity(hire);
            }
        });

        adslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alist = new Intent(ClientHome.this,ClientAdHistory.class);
                startActivity(alist);
            }
        });
    }
}