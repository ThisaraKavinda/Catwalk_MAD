package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClientRequestAdapter extends RecyclerView.Adapter<ClientRequestHolder>{

    DatabaseReference databaseReference;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    Context context;
    ArrayList<Clients> list;

    public ClientRequestAdapter(Context context, ArrayList<Clients> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ClientRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.activity_client_request_holder,parent,false);

        return new ClientRequestHolder(v);
    }

    @Override
    public void onBindViewHolder (@NonNull ClientRequestHolder holder,int position){
        Clients clients=list.get(position);
        holder.name.setText(clients.getName());
        holder.location.setText(clients.getLocation());
        holder.company.setText(clients.getCompany());
        Picasso.get().load(clients.getImageurl()).into(holder.image);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ClientSingleRegisterRequest.class);
                intent.putExtra("name",clients.getName());
                intent.putExtra("location",clients.getLocation());
                intent.putExtra("company",clients.getCompany());
                intent.putExtra("mobile",clients.getMobile());
                intent.putExtra("email",clients.getEmail());
                intent.putExtra("image",clients.getImageurl());

                context.startActivity(intent);

            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Clients");
                databaseReference.child(clients.getMobile()).child("status").setValue("Active");
                Toast.makeText(context, "Client Accepted", Toast.LENGTH_SHORT).show();
//                Toast.makeText(ModeRequestAdapter.this, "Model Accepted", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, ClientRegisterRequests.class);
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
}
