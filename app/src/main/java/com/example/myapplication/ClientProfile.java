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
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ClientProfile extends AppCompatActivity {
    SessionManager session;
    EditText cname,cemail,clocation,ccompany,cpassword;
    TextView cmobile;
    ImageView propic,propicup;
    Button back,upload,update,delete;
    String image;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Clients");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ClientProfile.this, ClientHome.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        session = new SessionManager(getApplicationContext());

        String name = session.getUserDetails().get("name");
        String email  = session.getUserDetails().get("email");
        String mobile  = session.getUserDetails().get("mobile");
        String location  = session.getUserDetails().get("location");
        String company  = session.getUserDetails().get("company");
        String password  = session.getUserDetails().get("password");
        image  = session.getUserDetails().get("image");
//        Log.i("info", name + " " + email+ " " + " " + image);

        cname = findViewById(R.id.clientProfileNameInput);
        cemail = findViewById(R.id.clientProfileEmailInput);
        clocation = findViewById(R.id.clientProfileLocInput);
        ccompany = findViewById(R.id.clientProfileComInput);
        cpassword = findViewById(R.id.clientProfilePasswordInput);
        cmobile = findViewById(R.id.clientProfileMobileInput);
        propic = findViewById(R.id.clientpropic2);
        propicup = findViewById(R.id.clientpropic);
        back = findViewById(R.id.clientprofilebackbtn1);
        upload = findViewById(R.id.clientpicbrowsebtn1);
        update = findViewById(R.id.clientupdatebtn1);
        delete = findViewById(R.id.clientdeletebtn1);

        cname.setText(name);
        cemail.setText(email);
        clocation.setText(location);
        ccompany.setText(company);
        cpassword.setText(password);
        cmobile.setText(mobile);

        Picasso.get().load(image).into(propic);
        Picasso.get().load(image).into(propicup);

        delete.setOnClickListener((view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(cmobile.getContext());
            builder.setTitle("Delete Panel");
            builder.setMessage("Delete....?");

            builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    FirebaseDatabase.getInstance().getReference().child("Clients")
                            .child(mobile).child("status").setValue("Deleted");
                    Toast.makeText(ClientProfile.this, "Your Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClientProfile.this,MainActivity.class);
                    startActivity(intent);
                    finish();


                }



            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }));



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientProfile.this,ClientHome.class);
                startActivity(intent);
                finish();
            }
        });

        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientProfile.this,ClientProfile.class);
                startActivity(intent);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri == null){


                    sendDataC();


                }else {
                    sendDataToDbC(imageUri);
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

    private void sendDataToDbC(Uri uri){
//

        ////////////////////////////////////////////////////////////////////////////////////////////////////

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Clients client = new Clients(cname.getText().toString(),cpassword.getText().toString(), cemail.getText().toString(),cmobile.getText().toString(), ccompany.getText().toString(), clocation.getText().toString(), uri.toString(),"Active");
//                        String modelId = root.push().getKey();
                        root.child(cmobile.getText().toString()).setValue(client);

                        Toast.makeText(ClientProfile.this, "Client Updated Successfully", Toast.LENGTH_SHORT).show();
//                        image.setImageResource(R.drawable.icons8_female_profile_55);
//                        session.logoutUser();

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

                Toast.makeText(ClientProfile.this, "Client Update  Failed", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


    private  void sendDataC(){
        Clients client = new Clients(cname.getText().toString(),cpassword.getText().toString(), cemail.getText().toString(),cmobile.getText().toString(), ccompany.getText().toString(), clocation.getText().toString(), image,"Active");
        root.child(cmobile.getText().toString()).setValue(client)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(ClientProfile.this, "Client Updated Successfully", Toast.LENGTH_SHORT).show();
                        session.logoutUser();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ClientProfile.this, "Client Update Failed", Toast.LENGTH_SHORT).show();



                    }
                });
    }


}