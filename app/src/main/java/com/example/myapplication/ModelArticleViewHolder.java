package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ModelArticleViewHolder extends RecyclerView.ViewHolder  {

    public TextView topic;
    public TextView description;


    public ModelArticleViewHolder(View itemView){
        super(itemView);

        topic = (TextView)itemView.findViewById(R.id.model_article_topic);
        description = (TextView)itemView.findViewById(R.id.model_article_description);




    }
}