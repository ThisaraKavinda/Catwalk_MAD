package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.Session.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

public class ClientHome extends AppCompatActivity {
    LinearLayout newrequest,adresponse,hiredmodels,adslist, addInquiry, inquiryList;
    ImageView propic;

    SessionManager session;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ClientHome.this, ClientHome.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        String name = session.getUserDetails().get("name");
        String image  = session.getUserDetails().get("image");


        newrequest = findViewById(R.id.new_request_tab);
        adresponse = findViewById(R.id.ads_response_tab);
        hiredmodels = findViewById(R.id.hired_models_tab);
        adslist =findViewById(R.id.ads_list_tab);
        propic = findViewById(R.id.clientpropic);
        addInquiry = findViewById(R.id.client_add_inquiry_tab);
        inquiryList = findViewById(R.id.client_inquiry_list_tab);

//        Intent clienthomeIntent = getIntent();
//
//        String cname = clienthomeIntent.getStringExtra("cname");
//        String cmobile = clienthomeIntent.getStringExtra("cmobile");
//        String cimage =  clienthomeIntent.getStringExtra("cimage");
//        Uri img = Uri.parse(cimage);

        Picasso.get().load(image).into(propic);



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

        addInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alist = new Intent(ClientHome.this,addInquiry.class);
                startActivity(alist);
            }
        });

        inquiryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alist = new Intent(ClientHome.this,InquiryList.class);
                startActivity(alist);
            }
        });

        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientHome.this,ClientProfile.class);
                startActivity(intent);
                finish();
            }
        });
    }
}