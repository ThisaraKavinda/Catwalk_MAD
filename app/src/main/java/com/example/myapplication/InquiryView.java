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

import com.example.myapplication.DAO.DAOInquiry;
import com.example.myapplication.Model.Inquiry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class InquiryView extends AppCompatActivity {

    Button updatebtn, deletebtn;
    TextView title, des;
    ImageView imageView;
    Spinner type;
    RadioGroup radiogroup;
    RadioButton yesRadBtn, noRadBtn;

    String id;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_view);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        updatebtn = findViewById(R.id.inquiryViewUpdatebtn);
        deletebtn = findViewById(R.id.inquiryViewDeletebtn);

        title = findViewById(R.id.inquiryViewTitle);
        des = findViewById(R.id.inquiryViewDes);
        imageView = findViewById(R.id.inquiryViewImage);
        type = findViewById(R.id.inquiryViewType);
        radiogroup = findViewById(R.id.inquiryViewSubBef);
        yesRadBtn = findViewById(R.id.inquiryViewSubBefYes);
        noRadBtn = findViewById(R.id.inquiryViewSubBefNo);

        showDetails();
    }

    private void showDetails() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query inquiryQuery = ref.child(Inquiry.class.getSimpleName()).orderByChild("id").equalTo(id);
        inquiryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                    Log.i("info", appleSnapshot.getValue(Inquiry.class).getTitle());
                    Inquiry inquiry = appleSnapshot.getValue(Inquiry.class);
                    title.setText(inquiry.getTitle());
                    des.setText(inquiry.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("info", "failed");
            }
        });

    }
}