package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ClientRequestHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView location;
    public TextView company;
    public ImageView image;
    Button view,accept;



    public ClientRequestHolder(View itemView){
        super(itemView);

        name = (TextView)itemView.findViewById(R.id.client_request_name);
        location = (TextView)itemView.findViewById(R.id.client_request_location);
        company = (TextView)itemView.findViewById(R.id.client_request_company);
        image = (ImageView) itemView.findViewById(R.id.client_request_image);
        view = itemView.findViewById(R.id.clientrequestviewbtn1);
        accept = itemView.findViewById(R.id.clientrequestacceptbtn1);








    }
}