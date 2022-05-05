package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder>{

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    Context context;
    ArrayList<Articles> list;

    public ArticleAdapter(Context context, ArrayList<Articles> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.activity_article_view_holder,parent,false);

        return new ArticleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder,int position){
        Articles article=list.get(position);
        holder.topic.setText(article.getTopic());
        holder.description.setText(article.getDescription());




    }

    @Override
    public int getItemCount(){
        return list.size();

    }


}
