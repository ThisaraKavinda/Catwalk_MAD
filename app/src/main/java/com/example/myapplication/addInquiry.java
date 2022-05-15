package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    String imgUrl;

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
            title = titleEditText.getText().toString();
            des = desEditText.getText().toString();
            value =((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            if (value.equals("Yes")) {
                isPreviousSubmitted = true;
            }
            about = spinner.getSelectedItem().toString();

            String imgPath = null;

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
                                                Inquiry inquiry = new Inquiry(title, isPreviousSubmitted, about, des, imgUrl);
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
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
                selectedImageView.setImageBitmap(newBitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}