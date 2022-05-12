package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ModelAdapter extends RecyclerView.Adapter<ModelListViewHolder> {

    Context context;
    ArrayList<Models> list;

    public ModelAdapter(Context context, ArrayList<Models> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ModelListViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.activity_model_item,parent,false);

        return new ModelListViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ModelListViewHolder holder,int position){
        Models model=list.get(position);
        holder.name.setText(model.getName());
        holder.mobile.setText(model.getMobile());
        holder.birthday.setText(model.getBirthday());

    }

    @Override
    public int getItemCount(){
        return list.size();

    }

}
