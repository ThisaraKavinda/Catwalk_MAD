package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

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
    public void onBindViewHolder (@NonNull ArticleViewHolder holder,int position){
        Articles article=list.get(position);
        holder.topic.setText(article.getTopic());
        holder.description.setText(article.getDescription());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(context, UpdateArticle.class);
            intent.putExtra("aname",article.getTopic());
            intent.putExtra("adesription",article.getDescription());
            context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener((view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(holder.topic.getContext());
            builder.setTitle("Delete Panel");
            builder.setMessage("Delete....?");

            builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    FirebaseDatabase.getInstance().getReference().child("Articles")
                            .child(article.getTopic()).removeValue();

                    Intent intent=new Intent(context, ArticleList.class);
                    context.startActivity(intent);

                }



            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }));






    }

    @Override
    public int getItemCount(){
        return list.size();

    }


}
