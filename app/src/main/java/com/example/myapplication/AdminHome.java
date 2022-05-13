package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.text.Layout;
import android.widget.Button;
import android.widget.LinearLayout;

public class AdminHome extends AppCompatActivity {
    LinearLayout addarticlebtn,articlelistbtn,modelrqstbtn;



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

        addarticlebtn = findViewById(R.id.add_article_tab);
        articlelistbtn = findViewById(R.id.list_article_tab);
        modelrqstbtn = findViewById(R.id.model_request_list_tab);

        addarticlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addarticle = new Intent(AdminHome.this,AddArticle.class);
                startActivity(addarticle);
            }
        });

        articlelistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listarticle = new Intent(AdminHome.this,ArticleList.class);
                startActivity(listarticle);
            }
        });

        modelrqstbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modelrequest = new Intent(AdminHome.this,ModelRegisterRequests.class);
                startActivity(modelrequest);
            }
        });

    }
}