package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ModelRequestHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView mobile;
    public TextView birthday;



    public ModelRequestHolder(View itemView){
        super(itemView);

        name = (TextView)itemView.findViewById(R.id.model_request_name);
        mobile = (TextView)itemView.findViewById(R.id.model_request_mobile);
        birthday = (TextView)itemView.findViewById(R.id.model_request_birthday);




    }

}