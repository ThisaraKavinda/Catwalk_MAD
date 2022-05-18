package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;

public class Adapter_model_post extends FirebaseRecyclerAdapter<class_model_request, Adapter_model_post.myviewholder>
{
    public Adapter_model_post(@NonNull FirebaseRecyclerOptions<class_model_request> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final class_model_request modelRequest)
    {
        holder.title.setText(modelRequest.getTitle());
        holder.description.setText(modelRequest.getDescription());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Delete Post");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Model_Request")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.title.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_client_edit_ad))
                        .setExpanded(true, 1730)
                        .create();

                View myview = dialogPlus.getHolderView();
                EditText Gender,Height, Type, time, Payment,Discription;
                Gender=(EditText)myview.findViewById(R.id.editGender);
                Height=(EditText)myview.findViewById(R.id.editHeight);
                Type=(EditText)myview.findViewById(R.id.editType);
                time=(EditText)myview.findViewById(R.id.editTime);
                Payment=(EditText)myview.findViewById(R.id.editPayment);
                Discription=(EditText)myview.findViewById(R.id.editDiscription);
                TextView pageTitle=(TextView)myview.findViewById(R.id.pageTitle);

                Button back = myview.findViewById(R.id.clientEditAdbackbtn1);
                Button update = myview.findViewById(R.id.update);
                Button admindeletebtn1 = myview.findViewById(R.id.admindeletebtn1);

                Payment.setText("RS."+modelRequest.getPayment()+".00");
                Gender.setText(modelRequest.getGender());
                Height.setText(modelRequest.getHeight());
                time.setText(modelRequest.getTime());
                Type.setText(modelRequest.getType());
                Discription.setText(modelRequest.getDescription());
                pageTitle.setText(modelRequest.getTitle());

                dialogPlus.show();
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogPlus.dismiss();
                    }
                });


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText Gender,Height, Type, time, Payment,Discription;

                        Height=(EditText)myview.findViewById(R.id.editHeight);
                        Type=(EditText)myview.findViewById(R.id.editType);
                        time=(EditText)myview.findViewById(R.id.editTime);
                        Payment=(EditText)myview.findViewById(R.id.editPayment);
                        Discription=(EditText)myview.findViewById(R.id.editDiscription);
                        TextView pageTitle=(TextView)myview.findViewById(R.id.pageTitle);

                        Map<String,Object> map=new HashMap<>();
                        map.put("gender", "Female");
                        map.put("height", Height.getText().toString());
                        map.put("payment", Payment.getText().toString());
                        map.put("time", time.getText().toString());
                        map.put("description", Discription.getText().toString());
                        map.put("type", Type.getText().toString());
                        map.put("clientId", "user");

                        FirebaseDatabase.getInstance().getReference().child("Model_Request")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Model Request Updating Successfully", Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }

                                    private Context getApplicationContext() {
                                        return null;
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }
                });

                admindeletebtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.title.getContext());
                        builder.setTitle("Delete Post");
                        builder.setMessage("Delete...?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Model_Request")
                                        .child(getRef(position).getKey()).removeValue();
                                dialogPlus.dismiss();
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        builder.show();



                    }
                });
            }
        });

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_one_ad_history_post,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView title,description;
        ImageButton delete, view;

        @SuppressLint("WrongViewCast")
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.txtTitle);
            description=(TextView)itemView.findViewById(R.id.txtDescription);
            delete=(ImageButton)itemView.findViewById(R.id.delete);
            view=(ImageButton)itemView.findViewById(R.id.view);



        }
    }
}

