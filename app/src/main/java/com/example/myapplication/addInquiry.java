package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.DAO.DAOInquiry;
import com.example.myapplication.Model.Inquiry;
import com.example.myapplication.Session.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class addInquiry extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference storageReference;
    Uri filePath;
    final int PICK_IMAGE_REQUEST = 22;

    EditText titleEditText, desEditText;
    ImageView selectedImageView;
    RadioGroup radioGroup;
    Spinner spinner;
    Button btnSelect;

    String title, des, value, about;
    Boolean isPreviousSubmitted = false;
    String imgUrl = "";

    SessionManager session;
    String userNumber;
    String userType;

    Context context = this;

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        userNumber = session.getUserDetails().get("mobile");
        userType = session.getUserDetails().get("type");

        titleEditText = (EditText) findViewById(R.id.InquiryTitleInput);
        desEditText = (EditText) findViewById(R.id.InquiryDesInput);
        radioGroup = (RadioGroup) findViewById(R.id.InquiryAddRadioGroup);
        spinner = (Spinner) findViewById(R.id.clientAddModelRequestTypeInput);

        selectedImageView = (ImageView) findViewById(R.id.selectedImgView);
        btnSelect = (Button) findViewById(R.id.InquiryImageInput);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        btnSelect.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Image from here..."),PICK_IMAGE_REQUEST);
        });

        final Button btnSubmit = findViewById(R.id.InquirySubmitbtn);
        DAOInquiry dao = new DAOInquiry();

        btnSubmit.setOnClickListener(v -> {

            if (!validateData()) {
                return;
            }
            title = titleEditText.getText().toString();
            des = desEditText.getText().toString();
            value =((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            if (value.equals("Yes")) {
                isPreviousSubmitted = true;
            }
            about = spinner.getSelectedItem().toString();

            String imgPath = null;

            if (!checkConnectivity()) {
                return;
            }

            if (filePath != null) {

                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                imgPath = "images/" + UUID.randomUUID().toString() + "." + getFileExtension(filePath);
                // Defining the child of storageReference
                StorageReference ref = storageReference.child(imgPath);

                // adding listeners on upload
                // or failure of image
                ref.putFile(filePath)
                        .addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                    {
                                        // Image uploaded successfully
                                        // Dismiss dialog
                                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                imgUrl = uri.toString();
                                                Toast.makeText(getApplicationContext(),"Image Uploaded!!",Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                Inquiry inquiry = new Inquiry(title, isPreviousSubmitted, about, des, imgUrl, userNumber, userType);
                                                dao.add(inquiry).addOnSuccessListener(suc -> {
                                                    Toast.makeText(context, "Added successfully", Toast.LENGTH_LONG).show();
                                                }).addOnFailureListener(er -> {
                                                    Toast.makeText(context, "" + er.getMessage(), Toast.LENGTH_LONG).show();
                                                });
                                                Intent intent = new Intent(getApplicationContext(), ClientHome.class);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {

                                // Error, Image not uploaded
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(
                                new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(
                                            UploadTask.TaskSnapshot taskSnapshot)
                                    {
                                        double progress= (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                    }
                                });
            } else {
                Inquiry inquiry = new Inquiry(title, isPreviousSubmitted, about, des, imgUrl, userNumber, userType);
                dao.add(inquiry).addOnSuccessListener(suc -> {
                    Toast.makeText(context, "Added successfully", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(context, "" + er.getMessage(), Toast.LENGTH_LONG).show();
                });
                Intent intent = new Intent(getApplicationContext(), ClientHome.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
                selectedImageView.setImageBitmap(newBitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkConnectivity() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
            alertBuilder("Please make sure that you have a active internet connection", "Check you internet connectivity");
        }
        return connected;
    }

    private boolean validateData() {
        if (titleEditText.length() == 0 && desEditText.length() == 0 && (value==null || value.length() ==0) ) {
            alertBuilder("Fill the inquiry details", "Fill all the fields");
            return false;
        } else if (titleEditText.length() == 0){
            alertBuilder("Fill the inquiry details", "Enter a title for the inquiry");
            return false;
        } else if (desEditText.length() == 0){
            alertBuilder("Fill the inquiry details", "Enter the description for the inquiry");
            return false;
        } else if (radioGroup.getCheckedRadioButtonId() == -1 ){
            alertBuilder("Fill the inquiry details", "Choose weather you have submit this inquiry before");
            return false;
        } else {
            return true;
        }
    }

    private void alertBuilder(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  ;
        builder.setMessage(message)
                .setCancelable(true)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }
}