package com.example.myapplication;


import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ArticleViewHolder extends RecyclerView.ViewHolder  {
    public TextView topic;
    public TextView description;
    public Button delete,update;


    public ArticleViewHolder(View itemView){
        super(itemView);

        topic = (TextView)itemView.findViewById(R.id.article_topic);
        description = (TextView)itemView.findViewById(R.id.article_description);
        update = itemView.findViewById(R.id.articleviewbtn1);
        delete = itemView.findViewById(R.id.articledeletebtn1);



    }

}