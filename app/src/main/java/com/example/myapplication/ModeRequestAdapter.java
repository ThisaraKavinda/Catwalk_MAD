package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class ModeRequestAdapter extends RecyclerView.Adapter<ModelRequestHolder>{

    DatabaseReference databaseReference;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    Context context;
    ArrayList<Models> list;

    public ModeRequestAdapter(Context context, ArrayList<Models> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ModelRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.activity_model_request_holder,parent,false);

        return new ModelRequestHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull ModelRequestHolder holder,int position){
        Models models=list.get(position);
        holder.name.setText(models.getName());
        holder.gender.setText("Gender: " +models.getGender());

        ModelRegister mr = new ModelRegister();
        int birth = mr.getAge(models.getBirthday());
        String aString = Integer.toString(birth);
        holder.birthday.setText("Age: " +aString);
        Picasso.get().load(models.getImageurl()).into(holder.image);



        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ModelSingleRegisterRequest.class);
                intent.putExtra("name",models.getName());
                intent.putExtra("age",aString);
                intent.putExtra("mobile",models.getMobile());
                intent.putExtra("email",models.getEmail());
                intent.putExtra("image",models.getImageurl());

                context.startActivity(intent);

            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Models");
                databaseReference.child(models.getMobile()).child("status").setValue("Active");
                Toast.makeText(context, "Model Accepted", Toast.LENGTH_SHORT).show();
//                Toast.makeText(ModeRequestAdapter.this, "Model Accepted", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, ModelRegisterRequests.class);
                context.startActivity(intent);
                

            }
        });



//        holder.delete.setOnClickListener((view -> {
//            AlertDialog.Builder builder=new AlertDialog.Builder(holder.topic.getContext());
//            builder.setTitle("Delete Panel");
//            builder.setMessage("Delete....?");
//
//            builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    FirebaseDatabase.getInstance().getReference().child("Articles")
//                            .child(article.getTopic()).removeValue();
//
//                    Intent intent=new Intent(context, ArticleList.class);
//                    context.startActivity(intent);
//
//                }
//
//
//
//            });
//
//            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });
//            builder.show();
//        }));






    }

    @Override
    public int getItemCount(){
        return list.size();

    }

//    private int getAge(String age){
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int birthyear = Integer.parseInt(age);
//        int mage = 0;
//
//        mage = year-birthyear;
//        System.out.println(mage);
//        return  mage;
//
//    }

}
