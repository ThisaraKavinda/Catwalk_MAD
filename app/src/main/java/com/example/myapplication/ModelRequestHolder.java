package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ModelRequestHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView gender;
    public TextView birthday;
    public ImageView image;
    Button view,accept;



    public ModelRequestHolder(View itemView){
        super(itemView);

        name = (TextView)itemView.findViewById(R.id.model_request_name);
        gender = (TextView)itemView.findViewById(R.id.model_request_gender);
        birthday = (TextView)itemView.findViewById(R.id.model_request_birthday);
        image = (ImageView) itemView.findViewById(R.id.model_request_image);
        view = itemView.findViewById(R.id.modelrequestviewbtn1);
        accept = itemView.findViewById(R.id.modelrequestacceptbtn1);








    }

}