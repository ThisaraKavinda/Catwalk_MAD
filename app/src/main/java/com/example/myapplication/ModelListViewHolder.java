package com.example.myapplication;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ModelListViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView mobile;
    public TextView birthday;

    public ModelListViewHolder(View itemView){
        super(itemView);

        name = (TextView)itemView.findViewById(R.id.model_name);
        mobile = (TextView)itemView.findViewById(R.id.model_mobile);
        birthday = (TextView)itemView.findViewById(R.id.model_birthday);


    }

}
