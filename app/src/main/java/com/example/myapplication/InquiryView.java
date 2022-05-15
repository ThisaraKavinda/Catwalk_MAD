package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DAO.DAOInquiry;
import com.example.myapplication.Model.Inquiry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class InquiryView extends AppCompatActivity {

    Button updatebtn, deletebtn;
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

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InquiryUpdate.class);
                intent.putExtra("id", _id);
                intent.putExtra("title", _title);
                intent.putExtra("isSubBef", _isSubBef);
                intent.putExtra("about", _about);
                intent.putExtra("des", _des);
                intent.putExtra("imagePath", _imagePath);
                startActivity(intent);
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InquiryView.this);  ;
                builder.setMessage("Do you want to delete this inquiry ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query inquiryQuery = ref.child(Inquiry.class.getSimpleName()).orderByChild("id").equalTo(_id);
                                inquiryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e("TAG", "onCancelled", databaseError.toException());
                                    }
                                });

                                Toast.makeText(getApplicationContext().getApplicationContext(),"Inquiry deleted successfully",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(InquiryView.this, InquiryList.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext().getApplicationContext(),"Please press Yes to delete",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Inquiry");
                alert.show();
            }
        });
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
                    boolean subBef = inquiry.getPreviousSubmitted();
                    if (subBef) {
                        yesRadBtn.setChecked(true);
                    } else {
                        noRadBtn.setChecked(true);
                    }
                    String about = inquiry.getAbout();
                    if(about.equals("Models")) {
                        type.setSelection(0);
                    } else if(about.equals("Clients")) {
                        type.setSelection(1);
                    } else if(about.equals("Payments")) {
                        type.setSelection(3);
                    } else if(about.equals("Application")) {
                        type.setSelection(4);
                    }
                    Picasso.get().load(inquiry.getImgPath()).into(imageView);

                    _id = inquiry.getId();
                    _title = inquiry.getTitle();
                    _des = inquiry.getDescription();
                    _about = inquiry.getAbout();
                    _isSubBef = inquiry.getPreviousSubmitted();
                    _imagePath = inquiry.getImgPath();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("info", "failed");
            }
        });

    }
}