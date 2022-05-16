package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAO.DAOInquiry;
import com.example.myapplication.InquiryList;
import com.example.myapplication.InquiryView;
import com.example.myapplication.Model.Inquiry;
import com.example.myapplication.R;
import com.example.myapplication.Session.SessionManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Inquiry> inquiryList;

    public MyAdapter(Context context, ArrayList<Inquiry> inquiryList) {
        this.context = context;
        this.inquiryList = inquiryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.inquiry_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {

        final int position = pos;
        Inquiry inquiry = inquiryList.get(position);
        holder.title.setText(inquiry.getTitle());
        holder.des.setText(inquiry.getDescription());
        holder.type.setText(inquiry.getAbout());

        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Inquiry theRemovedItem = inquiryList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);  ;
                builder.setMessage("Do you want to delete this inquiry ?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                inquiryList.clear();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query inquiryQuery = ref.child(Inquiry.class.getSimpleName()).orderByChild("id").equalTo(inquiry.getId());
                                inquiryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.e("TAG", "onCancelled", databaseError.toException());
                                    }
                                });

                                Toast.makeText(context.getApplicationContext(),"Inquiry deleted successfully",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(context.getApplicationContext(),"Please press Yes to delete",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });

        holder.viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, InquiryView.class);
                intent.putExtra("id", inquiry.getId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return inquiryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, des, type;
        Button deletebtn, viewbtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.inquiryViewListTitle);
            des = itemView.findViewById(R.id.inquiryViewListDes);
            type = itemView.findViewById(R.id.inquiryViewListType);
            deletebtn = itemView.findViewById(R.id.inquiryViewListDeletebtn);
            viewbtn = itemView.findViewById(R.id.inquiryViewListViewbtn);
        }
    }
}
