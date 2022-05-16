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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Inquiry;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class InquiryUpdate extends AppCompatActivity {

    Button editbtn, cancelbtn, updateImgbtn;
    TextView title, des;
    ImageView imageView;
    Spinner type;
    RadioGroup radiogroup;
    RadioButton yesRadBtn, noRadBtn;

    String id;
    DatabaseReference database;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri filePath;
    final int PICK_IMAGE_REQUEST = 22;

    String _id, _title, _about, _des, _imagePath, value;
    boolean _isSubBef;
    String key;

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

//        storageReference = storage.getReference();

        editbtn = findViewById(R.id.InquiryEditUpdatebtn);
        cancelbtn = findViewById(R.id.InquiryEditCancelbtn);
        updateImgbtn = findViewById(R.id.InquiryEditUpdateImgbtn);

        title = findViewById(R.id.InquiryEditTitle);
        des = findViewById(R.id.InquiryEditDes);
        imageView = findViewById(R.id.InquiryEditImageView);
        type = findViewById(R.id.InquiryEditAbout);
        radiogroup = findViewById(R.id.InquiryEditRadGroup);
        yesRadBtn = findViewById(R.id.InquiryEditRadGroupYes);
        noRadBtn = findViewById(R.id.InquiryEditRadGroupNo);

        showDetails();

        Log.i("info", id);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("info", "update");
                _title = title.getText().toString();
                _des = des.getText().toString();
                value =((RadioButton)findViewById(radiogroup.getCheckedRadioButtonId())).getText().toString();
                if (value.equals("InquiryEditRadGroupYes")) {
                    _isSubBef = true;
                }
                _about = type.getSelectedItem().toString();

                if (!checkConnectivity()) {
                    return;
                }
                if (!validateData()) {
                    return;
                }

                Query inquiryQuery = FirebaseDatabase.getInstance().getReference("Inquiry").orderByChild("id").equalTo(id);
                Log.i("info", "out of the loop");
                inquiryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        System.out.println("/////////////////");
//                        System.out.println(dataSnapshot.getKey());
//                        System.out.println("/////////////////");
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            Inquiry inquiry = appleSnapshot.getValue(Inquiry.class);
                            key = appleSnapshot.getKey();
                        }

                        String imgPath = null;
                        if (filePath != null) {
                            Log.i("info", "filePath is not null");
                            ProgressDialog progressDialog = new ProgressDialog(InquiryUpdate.this);
                            progressDialog.setTitle("Uploading...");
                            progressDialog.show();

                            Log.i("info", "filePath is not null 2");

                            imgPath = "images/" + UUID.randomUUID().toString() + "." + getFileExtension(filePath);
                            Log.i("info", imgPath);
                            StorageReference ref = storageReference.child(imgPath);

                            Log.i("info", "filePath is not null 3");

                            ref.putFile(filePath)
                                    .addOnSuccessListener(
                                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                                {
                                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            _imagePath = uri.toString();
                                                            database = FirebaseDatabase.getInstance().getReference("Inquiry").child(key);
                                                            HashMap<String, Object> hashMap = new HashMap<>();
                                                            hashMap.put("about", _about);
                                                            hashMap.put("description", _des);
                                                            hashMap.put("imgPath", _imagePath);
                                                            hashMap.put("previousSubmitted", _isSubBef);
                                                            hashMap.put("title", _title);
                                                            database.updateChildren(hashMap);
                                                            Toast.makeText(getApplicationContext(),"Successfully updated the inquiry",Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getApplicationContext(), InquiryList.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                                }
                                            })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
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
                            database = FirebaseDatabase.getInstance().getReference("Inquiry").child(key);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("about", _about);
                            hashMap.put("description", _des);
                            hashMap.put("imgPath", _imagePath);
                            hashMap.put("previousSubmitted", _isSubBef);
                            hashMap.put("title", _title);
                            database.updateChildren(hashMap);
                            Toast.makeText(getApplicationContext(),"Successfully updated the inquiry",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), InquiryList.class);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i("info", "failed");
                    }
                });


            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InquiryUpdate.this, InquiryList.class));
            }
        });

        updateImgbtn.setOnClickListener(v -> {
            Intent intent2 = new Intent();
            intent2.setType("image/*");
            intent2.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent2,"Select Image from here..."),PICK_IMAGE_REQUEST);
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
                Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 600, 600, false);
                imageView.setImageBitmap(newBitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cr.getType(uri));
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
        if (_title.length() == 0 && _des.length() == 0 && (value==null || value.length() ==0) ) {
            alertBuilder("Fill the inquiry details", "Fill all the fields");
            return false;
        } else if (_title.length() == 0){
            alertBuilder("Fill the inquiry details", "Enter a title for the inquiry");
            return false;
        } else if (_des.length() == 0){
            alertBuilder("Fill the inquiry details", "Enter the description for the inquiry");
            return false;
        } else if (value==null || value.length() ==0){
            alertBuilder("Fill the inquiry details", "Choose weather you have submit this inquiry before");
            return false;
        } else {
            return true;
        }
    }

    private void alertBuilder(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());  ;
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