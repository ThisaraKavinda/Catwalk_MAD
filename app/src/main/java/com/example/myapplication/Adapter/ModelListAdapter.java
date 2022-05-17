package com.example.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.InquiryView;
import com.example.myapplication.Model.Inquiry;
import com.example.myapplication.Models;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ModelListAdapter extends RecyclerView.Adapter<ModelListAdapter.MyViewHolder>{

    Context context;
    ArrayList<Models> modelList;

    public ModelListAdapter(Context context, ArrayList<Models> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {

        final int position = pos;
        Models model = modelList.get(position);
        holder.name.setText(model.getName());
        holder.gender.setText(model.getGender());
        holder.age.setText(model.getBirthday());
        Picasso.get().load(model.getImageurl()).into(holder.modelImage);

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Inquiry theRemovedItem = inquiryList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);  ;
                builder.setMessage("Do you want to delete this model ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                modelList.clear();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query inquiryQuery = ref.child(Models.class.getSimpleName()).orderByChild("mobile").equalTo(model.getMobile());
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

                                Toast.makeText(context.getApplicationContext(),"Model deleted successfully",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(context.getApplicationContext(),"Please press Yes to delete",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Model");
                alert.show();
            }
        });

        holder.viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, InquiryView.class);
                intent.putExtra("id", model.getMobile());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, gender, age;
        Button deletebtn, viewbtn;
        ImageView modelImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.modelListModelName);
            gender = itemView.findViewById(R.id.modelListModelGender);
            age = itemView.findViewById(R.id.modelListModelAge);
            deletebtn = itemView.findViewById(R.id.modelListModelDeletebtn);
            viewbtn = itemView.findViewById(R.id.modelListModelViewbtn);
            modelImage = itemView.findViewById(R.id.modelListmodelImage);
        }
    }

}
