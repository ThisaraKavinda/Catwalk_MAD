package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ModeRequestAdapter extends RecyclerView.Adapter<ModelRequestHolder>{

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    Context context;
    ArrayList<Models> list;

    public ModeRequestAdapter(Context context, ArrayList<Models> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ModelRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.activity_model_request_holder,parent,false);

        return new ModelRequestHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull ModelRequestHolder holder,int position){
        Models models=list.get(position);
        holder.name.setText(models.getName());
        holder.mobile.setText(models.getMobile());
        holder.birthday.setText(models.getBirthday());

//        holder.delete.setOnClickListener((view -> {
//            AlertDialog.Builder builder=new AlertDialog.Builder(holder.topic.getContext());
//            builder.setTitle("Delete Panel");
//            builder.setMessage("Delete....?");
//
//            builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    FirebaseDatabase.getInstance().getReference().child("Articles")
//                            .child(article.getTopic()).removeValue();
//
//                    Intent intent=new Intent(context, ArticleList.class);
//                    context.startActivity(intent);
//
//                }
//
//
//
//            });
//
//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
//            builder.show();
//        }));






    }

    @Override
    public int getItemCount(){
        return list.size();

    }

}
