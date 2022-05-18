package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ClientRegister extends AppCompatActivity {

    EditText name,password,email,mobile,company,location;
    Button reg_btn,upload_btn;
    DatabaseReference modelDbref;
    String str_status="Pending";
    ImageView image;

    TextView clientlogin;
    //vars
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Clients");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;

    String str_name,str_password,str_email,str_mobile,str_company,str_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        name = findViewById(R.id.cname);
        password = findViewById(R.id.cpassword);
        email = findViewById(R.id.cemail);
        mobile = findViewById(R.id.cmobile);
       company = findViewById(R.id.ccompanyname);
        location= findViewById(R.id.clocation);

        reg_btn = findViewById(R.id.cregisterbtn);

        upload_btn=findViewById(R.id.selectmclientpicbtn1);
        image = findViewById(R.id.cimageview);

        clientlogin= findViewById(R.id.clientloginbtn2);

        modelDbref = FirebaseDatabase.getInstance().getReference().child("Models");

       clientlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientRegister.this, ClientLogin.class);
                startActivity(intent);
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sendDataToDb(imageUri);
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            image.setImageURI(imageUri);

        }
    }


    private void sendDataToDb(Uri uri){
//        final ProgressDialog mDialog = new ProgressDialog(ModelRegister.this);
//        mDialog.setMessage("Please wait....");
//        mDialog.show();
//
        if(TextUtils.isEmpty(name.getText().toString())){
            name.setError("Name is compulsory");
            return;
        }
        if(TextUtils.isEmpty(password.getText().toString())){
            password.setError("Password is compulsory");
            return;
        }

        if(password.getText().toString().length()<8){
            password.setError("Password must be more than 8 character");
            return;
        }

        if(TextUtils.isEmpty(email.getText().toString())){
            email.setError("Email is compulsory");
            return;
        }
        if(TextUtils.isEmpty(mobile.getText().toString())){
            mobile.setError("Mobile Number is compulsory");
            return;
        }

        if(mobile.getText().toString().length()!=10){
            mobile.setError("Mobile Number must be 10 character");
            return;
        }

        if(TextUtils.isEmpty(company.getText().toString())){
            company.setError("Company Name  is compulsory");
            return;
        }
        if(TextUtils.isEmpty(location.getText().toString())){
            location.setError("Location is compulsory");
            return;
        }
        if(uri == null){
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
            return;
        }

//
//
        str_name = name.getText().toString();
        str_password = password.getText().toString();
        str_email = email.getText().toString();
        str_mobile = mobile.getText().toString();
        str_company = company.getText().toString();
        str_location = location.getText().toString();



//
//
//
//        modelDbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //check if already available
//                if(snapshot.child(str_email).exists()){
//                    mDialog.dismiss();
//                    Toast.makeText(ModelRegister.this, "Already Available...", Toast.LENGTH_SHORT).show();
//
//                }
//                else{
//                    mDialog.dismiss();
//                    Models model  = new Models(str_name,str_password,str_email,str_mobile,str_birthday,str_status);
//                    System.out.println(str_status);
//                    modelDbref.child(str_email).setValue(model);
//                    Toast.makeText(ModelRegister.this, "Sign up Successfully!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
////////////////////////////////////////////////////////////////////////////////////////////////////

//      Models model  = new Models(str_name,str_password,str_email,str_mobile,str_birthday);
//      modelDbref.push().setValue(model);
//
//        Toast.makeText(ModelRegister.this, "Model Registered", Toast.LENGTH_SHORT).show();

        ////////////////////////////////////////////////////////////////////////////////////////////////////
    modelDbref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.child(mobile.getText().toString()).exists()) {

                Toast.makeText(ClientRegister.this, "This Mobile Number Already Registered", Toast.LENGTH_SHORT).show();

            }else{
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////

                final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
                fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {



                                Clients client = new Clients(str_name,str_password,str_email,str_mobile,str_company,str_location,uri.toString(),str_status);
//                        String modelId = root.push().getKey();
                                root.child(str_mobile).setValue(client);

                                Toast.makeText(ClientRegister.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                image.setImageResource(R.drawable.icons8_female_profile_55);
                                Intent intent = new Intent(ClientRegister.this,ClientLogin.class);
                                startActivity(intent);

                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ClientRegister.this, "Register Failed !!", Toast.LENGTH_SHORT).show();
                    }
                });


  ////////////////////////////////////////////////////////////////////////////////////////////////////////////


            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });




    }

    public void checkMail(View v){

    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}