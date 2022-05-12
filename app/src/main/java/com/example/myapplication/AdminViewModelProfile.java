package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AdminViewModelProfile extends AppCompatActivity {

    EditText name, password, mobile, email, company, location;
    Button selectImagebtn, updatebtn, deletebtn;

    String _NAME, _Mobile, _EMAIL, _PASSWORD, _COMPANY, _LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_model_profile);

        name = findViewById(R.id.modelProfileNameInput);
        password = findViewById(R.id.modelProfilePasswordInput);
        mobile = findViewById(R.id.modelProfileMobileInput);
        email = findViewById(R.id.modelProfileEmailInput);
        company = findViewById(R.id.modelProfileDobInput);
        location = findViewById(R.id.modelProfileHeightInput);

        selectImagebtn = findViewById(R.id.clientpicbrowsebtn1);
        updatebtn = findViewById(R.id.clientupdatebtn1);
        deletebtn = findViewById(R.id.clientdeletebtn1);

        showModelData();
    }

    private void showModelData() {

    }
}