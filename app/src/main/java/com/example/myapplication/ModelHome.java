package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.Session.SessionManager;
import com.squareup.picasso.Picasso;

public class ModelHome extends AppCompatActivity {
     LinearLayout articles;
    ImageView propic;

    SessionManager session;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ModelHome.this, ModelHome.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_home);

        articles = findViewById(R.id.read_article_tab);
        propic = findViewById(R.id.modelhomepropic);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        String name = session.getModelDetails().get("mname");
        String image  = session.getModelDetails().get("mimage");


        Picasso.get().load(image).into(propic);


        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModelHome.this,ModelArticleList.class);
                startActivity(intent);
            }
        });


        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModelHome.this,ModelProfile.class);
                startActivity(intent);
                finish();
            }
        });
    }
}