package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ModelArticleAdapter extends  RecyclerView.Adapter<ModelArticleViewHolder>{

private final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Context context;
        ArrayList<Articles> list;

public ModelArticleAdapter(Context context, ArrayList<Articles> list) {
        this.context = context;
        this.list = list;
        }

@NonNull
@Override
public ModelArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.activity_model_artcile_view_holder,parent,false);

        return new ModelArticleViewHolder(v);
        }


@Override
public void onBindViewHolder (@NonNull ModelArticleViewHolder holder,int position){
        Articles article=list.get(position);
        holder.topic.setText(article.getTopic());
        holder.description.setText(article.getDescription());



        }

@Override
public int getItemCount(){
        return list.size();

        }




}
