package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class InquiryUpdate extends AppCompatActivity {

    TextInputEditText title, des;
    RadioGroup radioGroup;
    Spinner spinner;
    ImageView imageView;
    RadioButton yes, no;

    String _Title, _Des, _Type, _ImagePath, _Id;
    Boolean _IsPreviousSubmitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_update);

        title = findViewById(R.id.updateInquiryTitle);
        des = findViewById(R.id.updateInquiryDes);
        radioGroup = findViewById(R.id.updateInquiryRadioGroup);
        spinner = findViewById(R.id.updateInquiryType);
        imageView = findViewById(R.id.updateInquiryImage);
        yes = findViewById(R.id.updateInquiryRadioYes);
        no = findViewById(R.id.updateInquiryRadioNo);

        showAllUserData();
    }

    private void showAllUserData() {

        Intent intent = getIntent();
        _Title = intent.getStringExtra("title");
        _Des = intent.getStringExtra("des");
        _Type = intent.getStringExtra("type");
        _ImagePath = intent.getStringExtra("image");
        _IsPreviousSubmitted = intent.getBooleanExtra("isPreviousSubmitted", false);
        _Id = intent.getStringExtra("id");

        title.setText(_Title);
        des.setText(_Des);
        if (_IsPreviousSubmitted) {
            yes.setChecked(true);
        } else {
            no.setChecked(true);
        }
        switch(_Type) {
            case("Models"):
                spinner.setSelection(0);
                return;
            case ("Clients"):
                spinner.setSelection(1);
                return;
            case ("Payments"):
                spinner.setSelection(2);
                return;
            case ("Application"):
                spinner.setSelection(3);
                return;
            default:
                spinner.setSelection(2);
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child(_ImagePath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageView.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
//        imageView.setImageURI(Uri.parse(_ImagePath));

    }
}