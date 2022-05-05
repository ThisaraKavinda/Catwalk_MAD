package com.example.myapplication;


import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ArticleViewHolder extends RecyclerView.ViewHolder  {
    public TextView topic;
    public TextView description;


    public ArticleViewHolder(View itemView){
        super(itemView);

        topic = (TextView)itemView.findViewById(R.id.article_topic);
        description = (TextView)itemView.findViewById(R.id.article_description);



    }

}