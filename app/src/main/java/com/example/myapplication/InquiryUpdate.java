package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Model.Inquiry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class InquiryUpdate extends AppCompatActivity {

    Button updatebtn, cancelbtn;
    TextView title, des;
    ImageView imageView;
    Spinner type;
    RadioGroup radiogroup;
    RadioButton yesRadBtn, noRadBtn;

    String id;
    DatabaseReference database;

    String _id, _title, _about, _des, _imagePath;
    boolean _isSubBef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_update);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        _title = intent.getStringExtra("title");
        _about = intent.getStringExtra("about");
        _des = intent.getStringExtra("des");
        _imagePath = intent.getStringExtra("imagePath");
        _isSubBef = intent.getBooleanExtra("isSubBef", false);

        updatebtn = findViewById(R.id.InquiryEditUpdatebtn);
        cancelbtn = findViewById(R.id.InquiryEditCancelbtn);

        title = findViewById(R.id.InquiryEditTitle);
        des = findViewById(R.id.InquiryEditDes);
        imageView = findViewById(R.id.InquiryEditImageView);
        type = findViewById(R.id.InquiryEditAbout);
        radiogroup = findViewById(R.id.InquiryEditRadGroup);
        yesRadBtn = findViewById(R.id.InquiryEditRadGroupYes);
        noRadBtn = findViewById(R.id.InquiryEditRadGroupNo);

        showDetails();
    }

    private void showDetails() {
        title.setText(_title);
        des.setText(_des);
        boolean subBef = _isSubBef;
        if (subBef) {
            yesRadBtn.setChecked(true);
        } else {
            noRadBtn.setChecked(true);
        }
        String about = _about;
        if (about.equals("Models")) {
            type.setSelection(0);
        } else if (about.equals("Clients")) {
            type.setSelection(1);
        } else if (about.equals("Payments")) {
            type.setSelection(3);
        } else if (about.equals("Application")) {
            type.setSelection(4);
        }
        Picasso.get().load(_imagePath).into(imageView);
    }
}