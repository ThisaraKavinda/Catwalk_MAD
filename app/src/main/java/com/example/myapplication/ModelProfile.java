package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Session.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ModelProfile extends AppCompatActivity {
    SessionManager session;
    EditText mname,memail,mbirth,mgender,mpassword;
    TextView mmobile;
    ImageView propic,propicup;
    Button back,update,upload;
    String image;
    DatabaseReference modelDbref;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Models");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ModelProfile.this, ModelHome.class));
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_profile);

        session = new SessionManager(getApplicationContext());

        String name = session.getModelDetails().get("mname");
        String email  = session.getModelDetails().get("memail");
        String mobile  = session.getModelDetails().get("mmobile");
        String gender  = session.getModelDetails().get("mgender");
        String birth  = session.getModelDetails().get("mbirth");
        String password  = session.getModelDetails().get("mpassword");
         image  = session.getModelDetails().get("mimage");

        propic = findViewById(R.id.modelpropic1);
        propicup = findViewById(R.id.modelpropicup);
        mname = findViewById(R.id.modelProfileNameInput);
        memail = findViewById(R.id.modelProfileEmailInput);
        mgender = findViewById(R.id.modelProfileGenderInput);
        mbirth = findViewById(R.id.modelProfileDobInput);
        mpassword = findViewById(R.id.modelProfilePasswordInput);
        mmobile = findViewById(R.id.modelProfileMobileInput);
        back = findViewById(R.id.modelprofilebackbtn1);
        update = findViewById(R.id.modelupdatebtn1);
        upload = findViewById(R.id.modelProfilepicbrowsebtn1);

        mname.setText(name);
        memail.setText(email);
        mgender.setText(gender);
        mbirth.setText(birth);
        mpassword.setText(password);
        mmobile.setText(mobile);

        Picasso.get().load(image).into(propic);
        Picasso.get().load(image).into(propicup);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });

        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModelProfile.this,ModelProfile.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModelProfile.this,ModelHome.class);
                startActivity(intent);
                finish();
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(imageUri == null){


                   sendData();


                }else {
                   sendDataToDb(imageUri);
               }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            propicup.setImageURI(imageUri);

        }
    }

    private void sendDataToDb(Uri uri){
//

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Models model = new Models(mname.getText().toString(), mpassword.getText().toString(), memail.getText().toString(),mmobile.getText().toString(), mbirth.getText().toString(),"Active",mgender.getText().toString(),uri.toString());
//                        String modelId = root.push().getKey();
                        root.child(mmobile.getText().toString()).setValue(model);

                        Toast.makeText(ModelProfile.this, "Model Updated Successfully", Toast.LENGTH_SHORT).show();
//                        image.setImageResource(R.drawable.icons8_female_profile_55);
                        session.logoutUser();
                        Intent intent = new Intent(ModelProfile.this,MainActivity.class);
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

                Toast.makeText(ModelProfile.this, "Model Update  Failed", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


    private  void sendData(){
        Models model = new Models(mname.getText().toString(), mpassword.getText().toString(), memail.getText().toString(),mmobile.getText().toString(), mbirth.getText().toString(),"Active",mgender.getText().toString(),image);
        root.child(mmobile.getText().toString()).setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(ModelProfile.this, "Model Updated Successfully", Toast.LENGTH_SHORT).show();
                        session.logoutUser();
                        Intent intent = new Intent(ModelProfile.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ModelProfile.this, "Model Update Failed", Toast.LENGTH_SHORT).show();



                    }
                });
    }
}