package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.myapplication.Model.Inquiry;
import com.example.myapplication.R;
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return inquiryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, des, type;
        Button deletebtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.inquiryViewListTitle);
            des = itemView.findViewById(R.id.inquiryViewListDes);
            type = itemView.findViewById(R.id.inquiryViewListType);
            deletebtn = itemView.findViewById(R.id.inquiryViewListDeletebtn);
        }
    }
}
