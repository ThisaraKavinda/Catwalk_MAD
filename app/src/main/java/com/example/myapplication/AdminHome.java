package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHome extends AppCompatActivity {

    CardView modelRequests, modelList, clientRequests, clientList, adsRequest, addArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        modelRequests = findViewById(R.id.modelHomeModelRequests);
        modelList = findViewById(R.id.modelHomeModelList);
        clientRequests = findViewById(R.id.modelHomeClientRequests);
        clientList = findViewById(R.id.modelHomeClientList);
        adsRequest = findViewById(R.id.modelHomeAdsRequest);
        addArticle = findViewById(R.id.modelHomeAddArticle);

        modelRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModelRequestActivity.class);
                startActivity(intent);
            }
        });

        modelList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ModelList.class);
                startActivity(intent);
            }
        });
    }
}