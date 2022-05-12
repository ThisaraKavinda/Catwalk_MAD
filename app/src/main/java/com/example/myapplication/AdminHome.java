package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AdminHome extends AppCompatActivity {
    LinearLayout addarticlebtn,articlelistbtn,modelrqstbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

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